package com.nhnacademy.certificate.repository.family;

import com.nhnacademy.certificate.dto.web.FamilyResponse;
import com.nhnacademy.certificate.dto.web.QFamilyResponse;
import com.nhnacademy.certificate.entity.FamilyRelationship;
import com.nhnacademy.certificate.entity.QFamilyRelationship;
import com.nhnacademy.certificate.entity.QResident;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class FamilyRelationshipRepositoryImpl extends QuerydslRepositorySupport implements FamilyRelationshipCustom {
    public FamilyRelationshipRepositoryImpl() {
        super(FamilyRelationship.class);
    }

    @Override
    public List<FamilyResponse> findMyFamily(Long residentSerialNumber) {
        QFamilyRelationship familyRelationship = QFamilyRelationship.familyRelationship;
        QResident familyResident = QResident.resident;

        return from(familyRelationship)
                .select(new QFamilyResponse(
                        familyRelationship.familyRelationshipCode,
                        familyResident.name,
                        familyResident.birthDate,
                        familyResident.residentRegistrationNumber,
                        familyResident.genderCode
                ))
                .innerJoin(familyRelationship.familyResident, familyResident)
                .where(familyRelationship.pk.baseResidentSerialNumber.eq(residentSerialNumber))
                .fetch();
    }
}
