package com.nhnacademy.certificate.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resident")
public class Resident {

    @Id
    @Column(name = "resident_serial_number")
    private Long residentSerialNumber;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "resident_registration_number", nullable = false, length = 300)
    private String residentRegistrationNumber;

    @Column(name = "gender_code", nullable = false, length = 20)
    private String genderCode;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "birth_place_code", nullable = false, length = 20)
    private String birthPlaceCode;

    @Column(name = "registration_base_address", nullable = false, length = 500)
    private String registrationBaseAddress;

    @Column(name = "death_date")
    private LocalDateTime deathDate;

    @Column(name = "death_place_code", length = 20)
    private String deathPlaceCode;

    @Column(name = "death_place_address", length = 500)
    private String deathPlaceAddress;

    @Builder
    public Resident(Long residentSerialNumber, String name, String residentRegistrationNumber, String genderCode, LocalDateTime birthDate, String birthPlaceCode, String registrationBaseAddress, LocalDateTime deathDate, String deathPlaceCode, String deathPlaceAddress) {
        this.residentSerialNumber = residentSerialNumber;
        this.name = name;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.genderCode = genderCode;
        this.birthDate = birthDate;
        this.birthPlaceCode = birthPlaceCode;
        this.registrationBaseAddress = registrationBaseAddress;
        this.deathDate = deathDate;
        this.deathPlaceCode = deathPlaceCode;
        this.deathPlaceAddress = deathPlaceAddress;
    }
}