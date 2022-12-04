package com.nhnacademy.certificate.dto.birth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BirthReportEditRequest {
    private LocalDateTime birthDeathReportDate;
    private String birthReportQualificationsCode;
    private String emailAddress;
    private String phoneNumber;
    private LocalDateTime birthDate;
    private String birthPlaceCode;
}
