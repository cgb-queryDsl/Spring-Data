package com.nhnacademy.certificate.repository.certificate;

import com.nhnacademy.certificate.entity.CertificateIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateIssueRepository extends JpaRepository<CertificateIssue, Long>, CertificateIssueCustom {
}
