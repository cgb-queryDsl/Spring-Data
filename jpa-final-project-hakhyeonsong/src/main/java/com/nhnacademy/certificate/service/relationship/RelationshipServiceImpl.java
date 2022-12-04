package com.nhnacademy.certificate.service.relationship;

import com.nhnacademy.certificate.dto.web.FamilyResponse;
import com.nhnacademy.certificate.dto.relationship.RelationshipEditRequest;
import com.nhnacademy.certificate.dto.relationship.RelationshipRegisterRequest;
import com.nhnacademy.certificate.dto.relationship.RelationshipResponse;
import com.nhnacademy.certificate.entity.FamilyRelationship;
import com.nhnacademy.certificate.entity.Relationship;
import com.nhnacademy.certificate.entity.Resident;
import com.nhnacademy.certificate.exception.FamilyCodeNotFoundException;
import com.nhnacademy.certificate.exception.FamilyRelationshipNotFoundException;
import com.nhnacademy.certificate.exception.ResidentNotFoundException;
import com.nhnacademy.certificate.repository.family.FamilyRelationshipRepository;
import com.nhnacademy.certificate.repository.resident.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {

    private final FamilyRelationshipRepository relationshipRepository;
    private final ResidentRepository residentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<FamilyResponse> getFamilyMembers(Long serialNumber) {
        return relationshipRepository.findMyFamily(serialNumber);
    }

    @Override
    public RelationshipResponse register(Long serialNumber, RelationshipRegisterRequest request) {
        Resident baseResident = getResident(serialNumber);

        Long familySerialNumber = request.getFamilySerialNumber();
        Resident familyResident = getResident(familySerialNumber);

        String relationShip = convertRelationShip(request.getRelationShip());

        FamilyRelationship relationship = FamilyRelationship.builder()
                .baseResident(baseResident)
                .familyRelationshipCode(relationShip)
                .familyResident(familyResident)
                .build();

        relationshipRepository.save(relationship);

        return RelationshipResponse.of(relationship);
    }

    @Override
    public RelationshipResponse edit(Long serialNumber, Long familySerialNumber, RelationshipEditRequest request) {
        String relationShip = convertRelationShip(request.getRelationShip());

        FamilyRelationship familyRelationship = getFamilyRelationship(serialNumber, familySerialNumber);
        familyRelationship.setFamilyRelationshipCode(relationShip);

        relationshipRepository.save(familyRelationship);

        return RelationshipResponse.of(familyRelationship);
    }

    @Override
    public void delete(Long serialNumber, Long familySerialNumber) {
        FamilyRelationship.Pk id = new FamilyRelationship.Pk(serialNumber, familySerialNumber);
        relationshipRepository.deleteById(id);
    }

    private Resident getResident(Long serialNumber) {
        Optional<Resident> optionalResident = residentRepository.findById(serialNumber);

        if (optionalResident.isEmpty()) {
            throw new ResidentNotFoundException("해당하는 주민이 없습니다.");
        }
        return optionalResident.get();
    }

    private FamilyRelationship getFamilyRelationship(Long serialNumber, Long familySerialNumber) {
        FamilyRelationship.Pk id = new FamilyRelationship.Pk(serialNumber, familySerialNumber);
        Optional<FamilyRelationship> optionalFamilyRelationship = relationshipRepository.findById(id);

        if (optionalFamilyRelationship.isEmpty()) {
            throw new FamilyRelationshipNotFoundException("해당하는 가족 관계가 없습니다.");
        }
        return optionalFamilyRelationship.get();
    }

    private String convertRelationShip(String relationShip) {
        for (Relationship value : Relationship.values()) {
            if (relationShip.equals(value.getRelationship()))
                return value.toString();
        }
        throw new FamilyCodeNotFoundException("입력된 가족 관계 코드와 매칭되는 가족 관계 코드가 없습니다.");
    }
}
