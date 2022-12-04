package com.nhnacademy.certificate.service.resident;

import com.nhnacademy.certificate.dto.resident.ResidentRequest;
import com.nhnacademy.certificate.dto.resident.ResidentResponse;
import com.nhnacademy.certificate.entity.Resident;
import com.nhnacademy.certificate.exception.ResidentNotFoundException;
import com.nhnacademy.certificate.repository.resident.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<Resident> getResidents(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ResidentResponse register(ResidentRequest request) {
        Resident resident = Resident.builder()
                .residentSerialNumber(request.getResidentSerialNumber())
                .name(request.getName())
                .residentRegistrationNumber(request.getResidentRegistrationNumber())
                .genderCode(request.getGenderCode())
                .birthDate(request.getBirthDate())
                .birthPlaceCode(request.getBirthPlaceCode())
                .registrationBaseAddress(request.getRegistrationBaseAddress())
                .deathDate(request.getDeathDate())
                .deathPlaceCode(request.getDeathPlaceCode())
                .deathPlaceAddress(request.getDeathPlaceAddress())
                .build();

        repository.save(resident);

        return ResidentResponse.of(resident);
    }

    @Override
    public ResidentResponse edit(ResidentRequest request, Long serialNumber) {
        Resident resident = getResident(serialNumber);

        resident.setName(request.getName());
        resident.setResidentRegistrationNumber(request.getResidentRegistrationNumber());
        resident.setGenderCode(request.getGenderCode());
        resident.setBirthDate(request.getBirthDate());
        resident.setBirthPlaceCode(request.getBirthPlaceCode());
        resident.setRegistrationBaseAddress(request.getRegistrationBaseAddress());
        resident.setDeathDate(request.getDeathDate());
        resident.setDeathPlaceCode(request.getDeathPlaceCode());
        resident.setDeathPlaceAddress(request.getDeathPlaceAddress());

        repository.save(resident);

        return ResidentResponse.of(resident);
    }

    private Resident getResident(Long serialNumber) {
        Optional<Resident> optionalResident = repository.findById(serialNumber);
        if (optionalResident.isEmpty()) {
            throw new ResidentNotFoundException("해당하는 주민이 없습니다.");
        }
        return optionalResident.get();
    }

    @Transactional(readOnly = true)
    @Override
    public ResidentResponse findBySerialNumber(Long serialNumber) {
        Resident resident = getResident(serialNumber);
        return ResidentResponse.of(resident);
    }
}
