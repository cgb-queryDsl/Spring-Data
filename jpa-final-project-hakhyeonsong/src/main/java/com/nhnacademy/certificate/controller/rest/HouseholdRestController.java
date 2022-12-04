package com.nhnacademy.certificate.controller.rest;

import com.nhnacademy.certificate.dto.household.*;
import com.nhnacademy.certificate.service.household.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/household")
@RequiredArgsConstructor
public class HouseholdRestController {

    private final HouseholdService householdService;

    // 세대 등록: POST /household
    @PostMapping
    public ResponseEntity<HouseholdResponse> householdRegister(@RequestBody HouseholdRegisterRequest request) {
        HouseholdResponse response = householdService.registerHousehold(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 세대 삭제: DELETE /household/{householdSerialNumber}
    @DeleteMapping("/{householdSerialNumber}")
    public ResponseEntity<String> householdDelete(@PathVariable Long householdSerialNumber) {
        householdService.deleteHousehold(householdSerialNumber);
        return ResponseEntity.ok("household delete success");
    }

    // 세대 구성원 등록: POST /household/{householdSerialNumber}/member
    @PostMapping("/{householdSerialNumber}/member")
    public ResponseEntity<HouseholdMemberResponse> householdMemberRegister(@PathVariable Long householdSerialNumber,
                                                                           @RequestBody HouseholdMemberRegisterRequest request) {
        HouseholdMemberResponse response = householdService.registerHouseholdMember(householdSerialNumber, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 세대 구성원 삭제: DELETE /household/{householdSerialNumber}/member
    @DeleteMapping("/{householdSerialNumber}/member/{householdResidentSerialNumber}")
    public ResponseEntity<String> householdMemberDelete(@PathVariable("householdSerialNumber") Long householdSerialNumber,
                                                        @PathVariable("householdResidentSerialNumber") Long householdResidentSerialNumber) {
        householdService.deleteHouseholdMember(householdSerialNumber, householdResidentSerialNumber);
        return ResponseEntity.ok("household member delete success");
    }

    // 세대 전입 주소 등록: POST /household/{householdSerialNumber}/movement
    @PostMapping("/{householdSerialNumber}/movement")
    public ResponseEntity<HouseholdMovementResponse> householdMovementRegister(@PathVariable Long householdSerialNumber, @RequestBody HouseholdMovementRegisterRequest request) {
        HouseholdMovementResponse response = householdService.registerHouseholdMovement(householdSerialNumber, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 세대 전입 주소 수정: PUT /household/{householdSerialNumber}/movement/{reportDate}
    @PutMapping("/{householdSerialNumber}/movement/{reportDate}")
    public ResponseEntity<HouseholdMovementResponse> householdMovementEdit(@PathVariable("householdSerialNumber") Long householdSerialNumber,
                                                                           @PathVariable("reportDate") String reportDate,
                                                                           @RequestBody HouseholdMovementEditRequest request) {
        LocalDateTime date = convertLocalDateTime(reportDate);
        HouseholdMovementResponse response = householdService.editHouseholdMovement(householdSerialNumber, date, request);
        return ResponseEntity.ok(response);
    }

    private LocalDateTime convertLocalDateTime(String reportDate) {
        LocalDate localDate = LocalDate.parse(reportDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    // 세대 전입 주소 삭제: DELETE /household/{householdSerialNumber}/movement/{reportDate}
    @DeleteMapping("/{householdSerialNumber}/movement/{reportDate}")
    public ResponseEntity<String> householdMovementDelete(@PathVariable("householdSerialNumber") Long householdSerialNumber,
                                                          @PathVariable("reportDate") String reportDate) {
        LocalDateTime date = convertLocalDateTime(reportDate);
        householdService.deleteHouseholdMovement(date, householdSerialNumber);
        return ResponseEntity.ok("household movement address delete success");
    }
}
