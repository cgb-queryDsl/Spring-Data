package com.nhnacademy.certificate.dto.web;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class CertificateIssueResponse {
    private Long residentSerialNumber;
    private Long certificateConfirmationNumber;
    private String name;
    private String residentRegistrationNumber;
    private String certificateTypeCode;
    private LocalDateTime certificateIssueDate;

    @QueryProjection
    public CertificateIssueResponse(Long residentSerialNumber, Long certificateConfirmationNumber, String name, String residentRegistrationNumber,
                                    String certificateTypeCode, LocalDateTime certificateIssueDate) {
        this.residentSerialNumber = residentSerialNumber;
        this.certificateConfirmationNumber = certificateConfirmationNumber;
        this.name = name;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.certificateTypeCode = certificateTypeCode;
        this.certificateIssueDate = certificateIssueDate;
    }
}