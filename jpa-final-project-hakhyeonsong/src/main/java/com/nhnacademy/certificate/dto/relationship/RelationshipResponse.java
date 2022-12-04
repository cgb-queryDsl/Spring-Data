package com.nhnacademy.certificate.dto.relationship;

import com.nhnacademy.certificate.entity.FamilyRelationship;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationshipResponse {
    private Long familySerialNumber;
    private String relationShip;

    public static RelationshipResponse of(FamilyRelationship relationship) {
        return RelationshipResponse.builder()
                .familySerialNumber(relationship.getPk().getFamilyResidentSerialNumber())
                .relationShip(relationship.getFamilyRelationshipCode())
                .build();
    }
}
