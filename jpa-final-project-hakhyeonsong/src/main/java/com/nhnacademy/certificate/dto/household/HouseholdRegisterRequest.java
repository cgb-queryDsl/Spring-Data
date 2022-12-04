package com.nhnacademy.certificate.dto.household;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HouseholdRegisterRequest {
    private Long householdResidentSerialNumber;
    private Long householdSerialNumber;
    private String householdCompositionReasonCode;
    private String currentHouseMovementAddress;
    private LocalDateTime reportDate;
    private String lastAddressYn;
    private String householdRelationshipCode;
    private String householdCompositionChangeReasonCode;
}
