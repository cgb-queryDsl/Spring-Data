package com.nhnacademy.certificate.dto.household;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.certificate.entity.HouseholdCompositionResident;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseholdMemberResponse {
    private Long householdResidentSerialNumber;
    private Long householdSerialNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDateTime reportDate;
    private String householdRelationshipCode;
    private String householdCompositionChangeReasonCode;

    public static HouseholdMemberResponse of(HouseholdCompositionResident householdComposition) {
        return HouseholdMemberResponse.builder()
                .householdResidentSerialNumber(householdComposition.getResident().getResidentSerialNumber())
                .householdSerialNumber(householdComposition.getHousehold().getHouseholdSerialNumber())
                .reportDate(householdComposition.getReportDate())
                .householdRelationshipCode(householdComposition.getHouseholdRelationshipCode())
                .householdCompositionChangeReasonCode(householdComposition.getHouseholdCompositionChangeReasonCode())
                .build();
    }
}
