package com.nhnacademy.certificate.dto.birth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BirthReportRegisterRequest {
    private Long targetSerialNumber;
    private LocalDateTime birthDeathReportDate;
    private String birthReportQualificationsCode;
    private String emailAddress;
    private String phoneNumber;
    private LocalDateTime birthDate;
    private String birthPlaceCode;
}
