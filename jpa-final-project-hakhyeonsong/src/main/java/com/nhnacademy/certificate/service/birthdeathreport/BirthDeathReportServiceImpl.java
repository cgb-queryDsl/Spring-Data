package com.nhnacademy.certificate.service.birthdeathreport;

import com.nhnacademy.certificate.dto.web.BirthCertificateResponse;
import com.nhnacademy.certificate.dto.web.DeathCertificateResponse;
import com.nhnacademy.certificate.dto.birth.BirthReportEditRequest;
import com.nhnacademy.certificate.dto.birth.BirthReportRegisterRequest;
import com.nhnacademy.certificate.dto.birth.BirthReportResponse;
import com.nhnacademy.certificate.dto.death.DeathReportEditRequest;
import com.nhnacademy.certificate.dto.death.DeathReportRegisterRequest;
import com.nhnacademy.certificate.dto.death.DeathReportResponse;
import com.nhnacademy.certificate.entity.BirthDeathReportResident;
import com.nhnacademy.certificate.entity.Relationship;
import com.nhnacademy.certificate.entity.Resident;
import com.nhnacademy.certificate.exception.BirthDeathReportNotFoundException;
import com.nhnacademy.certificate.exception.FamilyCodeNotFoundException;
import com.nhnacademy.certificate.exception.ResidentNotFoundException;
import com.nhnacademy.certificate.repository.birthdeath.BirthDeathReportResidentRepository;
import com.nhnacademy.certificate.repository.resident.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BirthDeathReportServiceImpl implements BirthDeathReportService {

    private final ResidentRepository residentRepository;
    private final BirthDeathReportResidentRepository birthDeathReportRepository;

    @Transactional(readOnly = true)
    @Override
    public BirthCertificateResponse getBirthCertification(Long serialNumber) {
        BirthCertificateResponse birthCertification = birthDeathReportRepository.getBirthCertification(serialNumber);
        isExistBirthCertification(birthCertification);
        return birthCertification;
    }

    private void isExistBirthCertification(BirthCertificateResponse birthCertification) {
        if (birthCertification == null) {
            throw new BirthDeathReportNotFoundException("출생 신고서를 찾을 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public DeathCertificateResponse getDeathCertification(Long serialNumber) {
        DeathCertificateResponse deathCertification = birthDeathReportRepository.getDeathCertification(serialNumber);
        isExistDeathCertification(deathCertification);
        return deathCertification;
    }

    private void isExistDeathCertification(DeathCertificateResponse deathCertification) {
        if (deathCertification == null) {
            throw new BirthDeathReportNotFoundException("사망 신고서를 찾을 수 없습니다.");
        }
    }

    @Override
    public BirthReportResponse birthRegister(Long serialNumber, BirthReportRegisterRequest request) {
        Resident targetResident = getResident(request.getTargetSerialNumber());
        targetResident.setBirthDate(request.getBirthDate());
        targetResident.setBirthPlaceCode(request.getBirthPlaceCode());

        String birthReportQualificationsCode = convertRelationShip(request.getBirthReportQualificationsCode());

        BirthDeathReportResident birthReport = extractBirthReport(serialNumber, request, targetResident, birthReportQualificationsCode);

        birthDeathReportRepository.save(birthReport);

        return BirthReportResponse.of(birthReport);
    }

    private BirthDeathReportResident extractBirthReport(Long serialNumber, BirthReportRegisterRequest request, Resident targetResident, String birthReportQualificationsCode) {
        return BirthDeathReportResident.builder()
                .pk(new BirthDeathReportResident.Pk("출생", serialNumber))
                .targetResident(targetResident)
                .birthDeathReportDate(request.getBirthDeathReportDate())
                .birthReportQualificationsCode(birthReportQualificationsCode)
                .emailAddress(request.getEmailAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    @Override
    public BirthReportResponse birthEdit(Long serialNumber, Long targetSerialNumber, BirthReportEditRequest request) {
        Resident targetResident = getResident(targetSerialNumber);
        updateResidentBirthInfo(request, targetResident);

        BirthDeathReportResident birthReport = getBirthReport(serialNumber);

        String birthReportQualificationsCode = convertRelationShip(request.getBirthReportQualificationsCode());

        updateBirthReport(request, birthReport, birthReportQualificationsCode);

        birthDeathReportRepository.save(birthReport);
        residentRepository.save(targetResident);

        return BirthReportResponse.of(birthReport);
    }

    private void updateResidentBirthInfo(BirthReportEditRequest request, Resident targetResident) {
        targetResident.setBirthDate(request.getBirthDate());
        targetResident.setBirthPlaceCode(request.getBirthPlaceCode());
    }

    private void updateBirthReport(BirthReportEditRequest request, BirthDeathReportResident birthReport, String birthReportQualificationsCode) {
        birthReport.setBirthDeathReportDate(request.getBirthDeathReportDate());
        birthReport.setBirthReportQualificationsCode(birthReportQualificationsCode);
        birthReport.setEmailAddress(request.getEmailAddress());
        birthReport.setPhoneNumber(request.getPhoneNumber());
    }

    @Override
    public void birthDelete(Long serialNumber, Long targetSerialNumber) {
        BirthDeathReportResident.Pk id = new BirthDeathReportResident.Pk("출생", serialNumber);
        birthDeathReportRepository.deleteById(id);
    }

    @Override
    public DeathReportResponse deathRegister(Long serialNumber, DeathReportRegisterRequest request) {
        Resident targetResident = getResident(request.getTargetSerialNumber());
        targetResident.setDeathPlaceAddress(request.getDeathPlaceAddress());
        targetResident.setDeathPlaceCode(request.getDeathPlaceCode());
        targetResident.setDeathDate(request.getDeathDate());

        String deathReportQualificationsCode = convertRelationShip(request.getDeathReportQualificationsCode());

        BirthDeathReportResident deathReport = extractDeathReport(serialNumber, request, targetResident, deathReportQualificationsCode);

        birthDeathReportRepository.save(deathReport);
        residentRepository.save(targetResident);

        return DeathReportResponse.of(deathReport);
    }

    private BirthDeathReportResident extractDeathReport(Long serialNumber, DeathReportRegisterRequest request, Resident targetResident, String deathReportQualificationsCode) {
        return BirthDeathReportResident.builder()
                .pk(new BirthDeathReportResident.Pk("사망", serialNumber))
                .targetResident(targetResident)
                .birthDeathReportDate(request.getBirthDeathReportDate())
                .deathReportQualificationsCode(deathReportQualificationsCode)
                .emailAddress(request.getEmailAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    @Override
    public DeathReportResponse deathEdit(Long serialNumber, Long targetSerialNumber, DeathReportEditRequest request) {
        Resident targetResident = getResident(targetSerialNumber);
        updateResidentDeathInfo(request, targetResident);

        BirthDeathReportResident deathReport = getDeathReport(serialNumber);

        String deathReportQualificationsCode = convertRelationShip(request.getDeathReportQualificationsCode());

        updateDeathReportInfo(request, deathReport, deathReportQualificationsCode);

        birthDeathReportRepository.save(deathReport);
        residentRepository.save(targetResident);

        return DeathReportResponse.of(deathReport);
    }

    private void updateDeathReportInfo(DeathReportEditRequest request, BirthDeathReportResident deathReport, String deathReportQualificationsCode) {
        deathReport.setBirthDeathReportDate(request.getBirthDeathReportDate());
        deathReport.setDeathReportQualificationsCode(deathReportQualificationsCode);
        deathReport.setEmailAddress(request.getEmailAddress());
        deathReport.setPhoneNumber(request.getPhoneNumber());
    }

    private void updateResidentDeathInfo(DeathReportEditRequest request, Resident targetResident) {
        targetResident.setDeathPlaceAddress(request.getDeathPlaceAddress());
        targetResident.setDeathPlaceCode(request.getDeathPlaceCode());
        targetResident.setDeathDate(request.getDeathDate());
    }

    @Override
    public void deathDelete(Long serialNumber, Long targetSerialNumber) {
        Resident targetResident = getResident(targetSerialNumber);
        deleteDeathInfo(targetResident);

        BirthDeathReportResident.Pk id = new BirthDeathReportResident.Pk("사망", serialNumber);
        birthDeathReportRepository.deleteById(id);

        residentRepository.save(targetResident);
    }

    private void deleteDeathInfo(Resident targetResident) {
        targetResident.setDeathPlaceAddress(null);
        targetResident.setDeathPlaceCode(null);
        targetResident.setDeathDate(null);
    }

    private Resident getResident(Long serialNumber) {
        Optional<Resident> optionalResident = residentRepository.findById(serialNumber);
        if (optionalResident.isEmpty()) {
            throw new ResidentNotFoundException("해당하는 주민이 없습니다.");
        }
        return optionalResident.get();
    }

    private BirthDeathReportResident getBirthReport(Long serialNumber) {
        BirthDeathReportResident.Pk id = new BirthDeathReportResident.Pk("출생", serialNumber);
        Optional<BirthDeathReportResident> optionalBirthDeathReportResident = birthDeathReportRepository.findById(id);
        if (optionalBirthDeathReportResident.isEmpty()) {
            throw new BirthDeathReportNotFoundException("출생 신고서를 찾을 수 없습니다.");
        }
        return optionalBirthDeathReportResident.get();
    }

    private BirthDeathReportResident getDeathReport(Long serialNumber) {
        BirthDeathReportResident.Pk id = new BirthDeathReportResident.Pk("사망", serialNumber);
        Optional<BirthDeathReportResident> optionalBirthDeathReportResident = birthDeathReportRepository.findById(id);
        if (optionalBirthDeathReportResident.isEmpty()) {
            throw new BirthDeathReportNotFoundException("사망 신고서를 찾을 수 없습니다.");
        }
        return optionalBirthDeathReportResident.get();
    }

    private String convertRelationShip(String relationShip) {
        for (Relationship value : Relationship.values()) {
            if (relationShip.equals(value.getRelationship()))
                return value.toString();
        }
        throw new FamilyCodeNotFoundException("입력된 가족 관계 코드와 매칭되는 가족 관계 코드가 없습니다.");
    }
}
