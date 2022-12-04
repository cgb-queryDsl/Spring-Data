package com.nhnacademy.certificate.dto.household;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.certificate.entity.HouseholdMovementAddress;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseholdMovementResponse {

    private Long householdSerialNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDateTime houseMovementReportDate;
    private String houseMovementAddress;
    private String lastAddressYn;

    public static HouseholdMovementResponse of(HouseholdMovementAddress movementAddress) {
        return HouseholdMovementResponse.builder()
                .householdSerialNumber(movementAddress.getPk().getHouseholdSerialNumber())
                .houseMovementReportDate(movementAddress.getPk().getHouseMovementReportDate())
                .houseMovementAddress(movementAddress.getHouseMovementAddress())
                .lastAddressYn(movementAddress.getLastAddressYn())
                .build();
    }
}
