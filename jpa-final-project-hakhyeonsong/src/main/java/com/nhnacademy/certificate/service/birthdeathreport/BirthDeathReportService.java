package com.nhnacademy.certificate.service.birthdeathreport;

import com.nhnacademy.certificate.dto.web.BirthCertificateResponse;
import com.nhnacademy.certificate.dto.web.DeathCertificateResponse;
import com.nhnacademy.certificate.dto.birth.BirthReportEditRequest;
import com.nhnacademy.certificate.dto.birth.BirthReportRegisterRequest;
import com.nhnacademy.certificate.dto.birth.BirthReportResponse;
import com.nhnacademy.certificate.dto.death.DeathReportEditRequest;
import com.nhnacademy.certificate.dto.death.DeathReportRegisterRequest;
import com.nhnacademy.certificate.dto.death.DeathReportResponse;

public interface BirthDeathReportService {
    BirthCertificateResponse getBirthCertification(Long serialNumber);
    BirthReportResponse birthRegister(Long serialNumber, BirthReportRegisterRequest request);
    BirthReportResponse birthEdit(Long serialNumber, Long targetSerialNumber, BirthReportEditRequest request);
    void birthDelete(Long serialNumber, Long targetSerialNumber);

    DeathCertificateResponse getDeathCertification(Long serialNumber);
    DeathReportResponse deathRegister(Long serialNumber, DeathReportRegisterRequest request);
    DeathReportResponse deathEdit(Long serialNumber, Long targetSerialNumber, DeathReportEditRequest request);
    void deathDelete(Long serialNumber, Long targetSerialNumber);

}
