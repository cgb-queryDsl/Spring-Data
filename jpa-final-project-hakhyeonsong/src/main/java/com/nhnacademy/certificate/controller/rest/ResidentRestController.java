package com.nhnacademy.certificate.controller.rest;

import com.nhnacademy.certificate.dto.birth.BirthReportEditRequest;
import com.nhnacademy.certificate.dto.birth.BirthReportRegisterRequest;
import com.nhnacademy.certificate.dto.birth.BirthReportResponse;
import com.nhnacademy.certificate.dto.death.DeathReportEditRequest;
import com.nhnacademy.certificate.dto.death.DeathReportRegisterRequest;
import com.nhnacademy.certificate.dto.death.DeathReportResponse;
import com.nhnacademy.certificate.dto.relationship.RelationshipEditRequest;
import com.nhnacademy.certificate.dto.relationship.RelationshipRegisterRequest;
import com.nhnacademy.certificate.dto.relationship.RelationshipResponse;
import com.nhnacademy.certificate.dto.resident.ResidentRequest;
import com.nhnacademy.certificate.dto.resident.ResidentResponse;
import com.nhnacademy.certificate.service.birthdeathreport.BirthDeathReportService;
import com.nhnacademy.certificate.service.relationship.RelationshipService;
import com.nhnacademy.certificate.service.resident.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/residents")
@RequiredArgsConstructor
public class ResidentRestController {

    private final ResidentService residentService;
    private final RelationshipService relationshipService;
    private final BirthDeathReportService birthDeathReportService;

    // 주민 등록: POST /residents
    @PostMapping
    public ResponseEntity<ResidentResponse> registerResident(@RequestBody ResidentRequest request) {
        ResidentResponse response = residentService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 주민 수정: PUT /residents/{serialNumber}
    @PutMapping("/{serialNumber}")
    public ResponseEntity<ResidentResponse> editResident(@PathVariable Long serialNumber, @RequestBody ResidentRequest request) {
        ResidentResponse response = residentService.edit(request, serialNumber);
        return ResponseEntity.ok(response);
    }

    // 가족 관계 등록: POST /resident/{serialNumber}/relationship
    @PostMapping("/{serialNumber}/relationship")
    public ResponseEntity<RelationshipResponse> registerRelationship(@PathVariable Long serialNumber, @RequestBody RelationshipRegisterRequest request) {
        RelationshipResponse response = relationshipService.register(serialNumber, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 가족 관계 수정
    @PutMapping("/{serialNumber}/relationship/{familySerialNumber}")
    public ResponseEntity<RelationshipResponse> editRelationship(@PathVariable("serialNumber") Long serialNumber,
                                                                 @PathVariable("familySerialNumber") Long familySerialNumber,
                                                                 @RequestBody RelationshipEditRequest request) {
        RelationshipResponse response = relationshipService.edit(serialNumber, familySerialNumber, request);
        return ResponseEntity.ok(response);
    }

    // 가족 관계 삭제
    @DeleteMapping("/{serialNumber}/relationship/{familySerialNumber}")
    public ResponseEntity<String> deleteRelationship(@PathVariable("serialNumber") Long serialNumber,
                                                     @PathVariable("familySerialNumber") Long familySerialNumber) {
        relationshipService.delete(serialNumber, familySerialNumber);
        return ResponseEntity.ok("delete success");
    }

    // 출생 신고서 등록 POST /residents/{serialNumber}/birth
    // 행위자: serialNumber / 출생한 사람: targetSerialNumber
    @PostMapping("/{serialNumber}/birth")
    public ResponseEntity<BirthReportResponse> registerBirthReport(@PathVariable Long serialNumber, @RequestBody BirthReportRegisterRequest request) {
        BirthReportResponse response = birthDeathReportService.birthRegister(serialNumber, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 출생 신고서 수정 PUT /residents/{serialNumber}/birth/{targetSerialNumber}
    @PutMapping("/{serialNumber}/birth/{targetSerialNumber}")
    public ResponseEntity<BirthReportResponse> editBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                               @PathVariable("targetSerialNumber") Long targetSerialNumber,
                                                               @RequestBody BirthReportEditRequest request) {
        BirthReportResponse response = birthDeathReportService.birthEdit(serialNumber, targetSerialNumber, request);
        return ResponseEntity.ok(response);
    }

    // 출생 신고서 삭제 DELETE /residents/{serialNumber}/birth/{targetSerialNumber}
    @DeleteMapping("/{serialNumber}/birth/{targetSerialNumber}")
    public ResponseEntity<String> deleteBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                    @PathVariable("targetSerialNumber") Long targetSerialNumber) {
        birthDeathReportService.birthDelete(serialNumber, targetSerialNumber);
        return ResponseEntity.ok("delete success");
    }

    // 사망 신고서 등록 POST /residents/{serialNumber}/death
    // 행위자: serialNumber / 사망한 사람: targetSerialNumber
    @PostMapping("/{serialNumber}/death")
    public ResponseEntity<DeathReportResponse> registerDeathReport(@PathVariable Long serialNumber, @RequestBody DeathReportRegisterRequest request) {
        DeathReportResponse response = birthDeathReportService.deathRegister(serialNumber, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사망 신고서 수정 PUT /residents/{serialNumber}/death/{targetSerialNumber}
    @PutMapping("/{serialNumber}/death/{targetSerialNumber}")
    public ResponseEntity<DeathReportResponse> editDeathReport(@PathVariable("serialNumber") Long serialNumber,
                                                               @PathVariable("targetSerialNumber") Long targetSerialNumber,
                                                               @RequestBody DeathReportEditRequest request) {
        DeathReportResponse response = birthDeathReportService.deathEdit(serialNumber, targetSerialNumber, request);
        return ResponseEntity.ok(response);
    }

    // 사망 신고서 삭제 DELETE /residents/{serialNumber}/death/{targetSerialNumber}
    @DeleteMapping("/{serialNumber}/death/{targetSerialNumber}")
    public ResponseEntity<String> deleteDeathReport(@PathVariable("serialNumber") Long serialNumber,
                                                    @PathVariable("targetSerialNumber") Long targetSerialNumber) {
        birthDeathReportService.deathDelete(serialNumber, targetSerialNumber);
        return ResponseEntity.ok("delete success");
    }
}
