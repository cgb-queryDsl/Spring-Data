package com.nhnacademy.certificate.dto.death;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeathReportRegisterRequest {
    private Long targetSerialNumber;
    private LocalDateTime birthDeathReportDate;
    private String deathReportQualificationsCode;
    private String emailAddress;
    private String phoneNumber;
    private LocalDateTime deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;
}
