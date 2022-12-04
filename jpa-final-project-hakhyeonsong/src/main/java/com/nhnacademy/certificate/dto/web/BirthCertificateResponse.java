package com.nhnacademy.certificate.dto.web;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BirthCertificateResponse {
    private LocalDateTime reportDate;
    private String targetName;
    private String genderCode;
    private String birthPlaceCode;
    private String registrationBaseAddress;
    private LocalDateTime birthDate;
    private String reporterName;
    private String reporterResidentRegistrationNumber;
    private String reportQualificationsCode;
    private String emailAddress;
    private String phoneNumber;

    @QueryProjection
    public BirthCertificateResponse(LocalDateTime reportDate, String targetName, String genderCode,
                                    String birthPlaceCode, String registrationBaseAddress,
                                    LocalDateTime birthDate, String reporterName, String reporterResidentRegistrationNumber,
                                    String reportQualificationsCode, String emailAddress, String phoneNumber) {
        this.reportDate = reportDate;
        this.targetName = targetName;
        this.genderCode = genderCode;
        this.birthPlaceCode = birthPlaceCode;
        this.registrationBaseAddress = registrationBaseAddress;
        this.birthDate = birthDate;
        this.reporterName = reporterName;
        this.reporterResidentRegistrationNumber = reporterResidentRegistrationNumber;
        this.reportQualificationsCode = reportQualificationsCode;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}
