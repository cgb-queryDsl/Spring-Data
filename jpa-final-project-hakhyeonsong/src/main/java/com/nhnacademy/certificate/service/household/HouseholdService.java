package com.nhnacademy.certificate.service.household;

import com.nhnacademy.certificate.dto.web.HouseholdMemberViewResponse;
import com.nhnacademy.certificate.dto.household.*;
import com.nhnacademy.certificate.entity.HouseholdMovementAddress;

import java.time.LocalDateTime;
import java.util.List;

public interface HouseholdService {
    HouseholdResponse registerHousehold(HouseholdRegisterRequest request);
    void deleteHousehold(Long householdSerialNumber);
    HouseholdMemberResponse registerHouseholdMember(Long householdSerialNumber, HouseholdMemberRegisterRequest request);
    void deleteHouseholdMember(Long householdSerialNumber, Long householdResidentSerialNumber);
    HouseholdMovementResponse registerHouseholdMovement(Long householdSerialNumber, HouseholdMovementRegisterRequest request);
    HouseholdMovementResponse editHouseholdMovement(Long householdSerialNumber, LocalDateTime reportDate, HouseholdMovementEditRequest request);
    void deleteHouseholdMovement(LocalDateTime reportDate, Long householdSerialNumber);
    List<HouseholdMemberViewResponse> getHouseholdMembers(Long householdSerialNumber);
    Long getSerialNumber(Long serialNumber);
    List<HouseholdMovementAddress> getMovementAddresses(Long householdSerialNumber);
}
