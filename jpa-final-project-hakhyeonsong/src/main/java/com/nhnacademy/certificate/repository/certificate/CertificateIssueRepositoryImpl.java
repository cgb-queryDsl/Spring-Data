package com.nhnacademy.certificate.repository.certificate;

import com.nhnacademy.certificate.dto.web.CertificateIssueResponse;
import com.nhnacademy.certificate.dto.web.QCertificateIssueResponse;
import com.nhnacademy.certificate.entity.CertificateIssue;
import com.nhnacademy.certificate.entity.QCertificateIssue;
import com.nhnacademy.certificate.entity.QResident;
import com.nhnacademy.certificate.exception.CertificateIssueNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CertificateIssueRepositoryImpl extends QuerydslRepositorySupport implements CertificateIssueCustom {
    public CertificateIssueRepositoryImpl() {
        super(CertificateIssue.class);
    }

    @Override
    public Page<CertificateIssueResponse> findAllByResident(Long residentSerialNumber, Pageable pageable) {
        QCertificateIssue certificateIssue = QCertificateIssue.certificateIssue;
        QResident resident = QResident.resident;

        List<CertificateIssueResponse> certificates = from(certificateIssue)
                .select(new QCertificateIssueResponse(
                        resident.residentSerialNumber,
                        certificateIssue.certificateConfirmationNumber,
                        resident.name,
                        resident.residentRegistrationNumber,
                        certificateIssue.certificateTypeCode,
                        certificateIssue.certificateIssueDate
                ))
                .innerJoin(certificateIssue.resident, resident)
                .where(certificateIssue.resident.residentSerialNumber.eq(residentSerialNumber))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (certificates.isEmpty()) {
            throw new CertificateIssueNotFoundException("대상 주민의 증명서 발급내역이 없습니다.");
        }

        Long totalCount = from(certificateIssue)
                .select(certificateIssue.count())
                .fetchOne();

        return new PageImpl<>(certificates, pageable, totalCount);
    }
}
