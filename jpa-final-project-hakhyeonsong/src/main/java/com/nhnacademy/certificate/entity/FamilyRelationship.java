package com.nhnacademy.certificate.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "family_relationship")
@NoArgsConstructor
public class FamilyRelationship {

    @EmbeddedId
    private Pk pk;

    @Column(name = "family_relationship_code", nullable = false, length = 20)
    private String familyRelationshipCode;

    @MapsId("baseResidentSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_resident_serial_number")
    private Resident baseResident;

    @MapsId("familyResidentSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_resident_serial_number")
    private Resident familyResident;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "base_resident_serial_number")
        private Long baseResidentSerialNumber;

        @Column(name = "family_resident_serial_number")
        private Long familyResidentSerialNumber;
    }

    @Builder
    public FamilyRelationship(String familyRelationshipCode, Resident baseResident, Resident familyResident) {
        this.familyRelationshipCode = familyRelationshipCode;
        this.baseResident = baseResident;
        this.familyResident = familyResident;
        this.pk = new Pk(baseResident.getResidentSerialNumber(), familyResident.getResidentSerialNumber());
    }
}
