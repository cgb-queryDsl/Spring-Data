package com.nhnacademy.certificate.repository.birthdeath;

import com.nhnacademy.certificate.dto.web.BirthCertificateResponse;
import com.nhnacademy.certificate.dto.web.DeathCertificateResponse;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BirthDeathReportResidentCustom {
    BirthCertificateResponse getBirthCertification(Long targetSerialNumber);
    DeathCertificateResponse getDeathCertification(Long targetSerialNumber);
}
