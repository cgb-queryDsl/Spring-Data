package com.nhnacademy.certificate.dto.household;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HouseholdMemberRegisterRequest {
    private Long householdResidentSerialNumber;
    private String householdCompositionReasonCode;
    private LocalDateTime reportDate;
    private String householdRelationshipCode;
    private String householdCompositionChangeReasonCode;
}