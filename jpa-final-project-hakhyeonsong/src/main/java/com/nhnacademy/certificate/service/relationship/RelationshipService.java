package com.nhnacademy.certificate.service.relationship;

import com.nhnacademy.certificate.dto.web.FamilyResponse;
import com.nhnacademy.certificate.dto.relationship.RelationshipEditRequest;
import com.nhnacademy.certificate.dto.relationship.RelationshipRegisterRequest;
import com.nhnacademy.certificate.dto.relationship.RelationshipResponse;

import java.util.List;

public interface RelationshipService {
    List<FamilyResponse> getFamilyMembers(Long serialNumber);
    RelationshipResponse register(Long serialNumber, RelationshipRegisterRequest request);
    RelationshipResponse edit(Long serialNumber, Long familySerialNumber, RelationshipEditRequest request);
    void delete(Long serialNumber, Long familySerialNumber);
}
