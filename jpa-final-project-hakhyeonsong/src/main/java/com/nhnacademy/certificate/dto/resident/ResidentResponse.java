package com.nhnacademy.certificate.dto.resident;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.certificate.entity.Resident;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentResponse {
    private Long residentSerialNumber;
    private String name;
    private String residentRegistrationNumber;
    private String genderCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime birthDate;
    private String birthPlaceCode;
    private String registrationBaseAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;

    public static ResidentResponse of(Resident resident) {
        return ResidentResponse.builder()
                .residentSerialNumber(resident.getResidentSerialNumber())
                .name(resident.getName())
                .residentRegistrationNumber(resident.getResidentRegistrationNumber())
                .genderCode(resident.getGenderCode())
                .birthDate(resident.getBirthDate())
                .birthPlaceCode(resident.getBirthPlaceCode())
                .registrationBaseAddress(resident.getRegistrationBaseAddress())
                .deathDate(resident.getDeathDate())
                .deathPlaceCode(resident.getDeathPlaceCode())
                .deathPlaceAddress(resident.getDeathPlaceAddress())
                .build();
    }

}
