package com.nhnacademy.certificate.service.resident;

import com.nhnacademy.certificate.dto.resident.ResidentRequest;
import com.nhnacademy.certificate.dto.resident.ResidentResponse;
import com.nhnacademy.certificate.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResidentService {
    Page<Resident> getResidents(Pageable pageable);
    ResidentResponse register(ResidentRequest request);
    ResidentResponse edit(ResidentRequest request, Long serialNumber);
    ResidentResponse findBySerialNumber(Long serialNumber);
}
