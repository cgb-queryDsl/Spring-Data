package com.nhnacademy.certificate.dto.household;

import lombok.Data;

@Data
public class HouseholdMovementEditRequest {
    private String houseMovementAddress;
    private String lastAddressYn;
}
