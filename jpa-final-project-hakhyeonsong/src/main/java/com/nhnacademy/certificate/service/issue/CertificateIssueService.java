package com.nhnacademy.certificate.service.issue;

import com.nhnacademy.certificate.dto.web.CertificateIssueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CertificateIssueService {
    Page<CertificateIssueResponse> findAll(Long residentSerialNumber, Pageable pageable);
}
