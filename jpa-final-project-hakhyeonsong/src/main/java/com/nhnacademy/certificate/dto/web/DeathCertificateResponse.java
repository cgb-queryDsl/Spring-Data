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
public class DeathCertificateResponse {
    private LocalDateTime reportDate;
    private String targetName;
    private String targetResidentRegistrationNumber;
    private LocalDateTime deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;
    private String reporterName;
    private String reporterResidentRegistrationNumber;
    private String reportQualificationsCode;
    private String emailAddress;
    private String phoneNumber;

    @QueryProjection
    public DeathCertificateResponse(LocalDateTime reportDate, String targetName,
                                    String targetResidentRegistrationNumber, LocalDateTime deathDate,
                                    String deathPlaceCode, String deathPlaceAddress,
                                    String reporterName, String reporterResidentRegistrationNumber,
                                    String reportQualificationsCode, String emailAddress, String phoneNumber) {
        this.reportDate = reportDate;
        this.targetName = targetName;
        this.targetResidentRegistrationNumber = targetResidentRegistrationNumber;
        this.deathDate = deathDate;
        this.deathPlaceCode = deathPlaceCode;
        this.deathPlaceAddress = deathPlaceAddress;
        this.reporterName = reporterName;
        this.reporterResidentRegistrationNumber = reporterResidentRegistrationNumber;
        this.reportQualificationsCode = reportQualificationsCode;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}
