package com.nhnacademy.certificate.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "household_composition_resident")
@NoArgsConstructor
public class HouseholdCompositionResident {

    @EmbeddedId
    private Pk pk;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "household_relationship_code", nullable = false, length = 20)
    private String householdRelationshipCode;

    @Column(name = "household_composition_change_reason_code", nullable = false, length = 20)
    private String householdCompositionChangeReasonCode;

    @MapsId("householdSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_serial_number")
    private Household household;

    @MapsId("residentSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_serial_number")
    private Resident resident;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "household_serial_number")
        private Long householdSerialNumber;

        @Column(name = "resident_serial_number")
        private Long residentSerialNumber;
    }

    @Builder
    public HouseholdCompositionResident(Pk pk, LocalDateTime reportDate,
                                        String householdRelationshipCode,
                                        String householdCompositionChangeReasonCode,
                                        Household household, Resident resident) {
        this.pk = pk;
        this.reportDate = reportDate;
        this.householdRelationshipCode = householdRelationshipCode;
        this.householdCompositionChangeReasonCode = householdCompositionChangeReasonCode;
        this.household = household;
        this.resident = resident;
    }
}
