package com.nhnacademy.certificate.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "birth_death_report_resident")
@NoArgsConstructor
public class BirthDeathReportResident {

    @EmbeddedId
    private Pk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_serial_number")
    private Resident targetResident;

    @MapsId("residentSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_resident_serial_number")
    private Resident reporterResident;

    @Column(name = "birth_death_report_date", nullable = false)
    private LocalDateTime birthDeathReportDate;

    @Column(name = "birth_report_qualifications_code", length = 20)
    private String birthReportQualificationsCode;

    @Column(name = "death_report_qualifications_code", length = 20)
    private String deathReportQualificationsCode;

    @Column(name = "email_address", length = 50)
    private String emailAddress;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {

        @Column(name = "birth_death_type_code")
        private String birthDeathTypeCode;

        @Column(name = "report_resident_serial_number")
        private Long residentSerialNumber;
    }

    @Builder
    public BirthDeathReportResident(Pk pk, Resident targetResident, LocalDateTime birthDeathReportDate,
                                    String birthReportQualificationsCode, String deathReportQualificationsCode,
                                    String emailAddress, String phoneNumber) {
        this.pk = pk;
        this.targetResident = targetResident;
        this.birthDeathReportDate = birthDeathReportDate;
        this.birthReportQualificationsCode = birthReportQualificationsCode;
        this.deathReportQualificationsCode = deathReportQualificationsCode;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}