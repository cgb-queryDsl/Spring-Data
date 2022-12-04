package com.nhnacademy.certificate.repository.birthdeath;

import com.nhnacademy.certificate.dto.web.BirthCertificateResponse;
import com.nhnacademy.certificate.dto.web.DeathCertificateResponse;
import com.nhnacademy.certificate.dto.web.QBirthCertificateResponse;
import com.nhnacademy.certificate.dto.web.QDeathCertificateResponse;
import com.nhnacademy.certificate.entity.BirthDeathReportResident;
import com.nhnacademy.certificate.entity.QBirthDeathReportResident;
import com.nhnacademy.certificate.entity.QResident;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BirthDeathReportResidentRepositoryImpl extends QuerydslRepositorySupport implements BirthDeathReportResidentCustom {
    public BirthDeathReportResidentRepositoryImpl() {
        super(BirthDeathReportResident.class);
    }

    @Override
    public BirthCertificateResponse getBirthCertification(Long targetSerialNumber) {
        QBirthDeathReportResident bdrr = QBirthDeathReportResident.birthDeathReportResident;
        QResident targetResident = QResident.resident;
        QResident reporterResident = QResident.resident;

        return from(bdrr)
                .select(new QBirthCertificateResponse(
                        bdrr.birthDeathReportDate,
                        bdrr.targetResident.name,
                        targetResident.genderCode,
                        targetResident.birthPlaceCode,
                        targetResident.registrationBaseAddress,
                        bdrr.birthDeathReportDate,
                        bdrr.reporterResident.name,
                        reporterResident.residentRegistrationNumber,
                        bdrr.birthReportQualificationsCode,
                        bdrr.emailAddress,
                        bdrr.phoneNumber
                ))
                .innerJoin(bdrr.targetResident, targetResident)
                .innerJoin(bdrr.reporterResident, reporterResident)
                .where(bdrr.targetResident.residentSerialNumber.eq(targetSerialNumber)
                        .and(bdrr.pk.birthDeathTypeCode.eq("출생")))
                .fetchOne();
    }

    @Override
    public DeathCertificateResponse getDeathCertification(Long targetSerialNumber) {
        QBirthDeathReportResident bdrr = QBirthDeathReportResident.birthDeathReportResident;
        QResident targetResident = QResident.resident;
        QResident reporterResident = QResident.resident;

        return from(bdrr)
                .select(new QDeathCertificateResponse(
                        bdrr.birthDeathReportDate,
                        bdrr.targetResident.name,
                        bdrr.targetResident.residentRegistrationNumber,
                        bdrr.targetResident.deathDate,
                        bdrr.targetResident.deathPlaceCode,
                        bdrr.targetResident.deathPlaceAddress,
                        reporterResident.name,
                        reporterResident.residentRegistrationNumber,
                        bdrr.deathReportQualificationsCode,
                        bdrr.emailAddress,
                        bdrr.phoneNumber
                ))
                .innerJoin(bdrr.targetResident, targetResident)
                .innerJoin(bdrr.reporterResident, reporterResident)
                .where(bdrr.targetResident.residentSerialNumber.eq(targetSerialNumber)
                        .and(bdrr.pk.birthDeathTypeCode.eq("사망")))
                .fetchOne();
    }
}
