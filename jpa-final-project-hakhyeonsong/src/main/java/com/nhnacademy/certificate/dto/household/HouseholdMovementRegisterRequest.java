package com.nhnacademy.certificate.dto.household;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HouseholdMovementRegisterRequest {
    private LocalDateTime houseMovementReportDate;
    private String houseMovementAddress;
    private String lastAddressYn;
}
