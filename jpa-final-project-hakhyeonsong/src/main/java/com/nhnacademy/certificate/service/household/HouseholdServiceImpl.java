package com.nhnacademy.certificate.service.household;

import com.nhnacademy.certificate.dto.household.*;
import com.nhnacademy.certificate.dto.web.HouseholdMemberViewResponse;
import com.nhnacademy.certificate.entity.Household;
import com.nhnacademy.certificate.entity.HouseholdCompositionResident;
import com.nhnacademy.certificate.entity.HouseholdMovementAddress;
import com.nhnacademy.certificate.entity.Resident;
import com.nhnacademy.certificate.exception.HouseholdAddressNotFoundException;
import com.nhnacademy.certificate.exception.HouseholdMemberNotFoundException;
import com.nhnacademy.certificate.exception.HouseholdNotFoundException;
import com.nhnacademy.certificate.exception.ResidentNotFoundException;
import com.nhnacademy.certificate.repository.household.HouseholdCompositionResidentRepository;
import com.nhnacademy.certificate.repository.household.HouseholdMovementAddressRepository;
import com.nhnacademy.certificate.repository.household.HouseholdRepository;
import com.nhnacademy.certificate.repository.resident.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;
    private final ResidentRepository residentRepository;
    private final HouseholdMovementAddressRepository hmaRepository;
    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<HouseholdMemberViewResponse> getHouseholdMembers(Long householdSerialNumber) {
        List<HouseholdMemberViewResponse> householdMembers = householdCompositionResidentRepository.getAllMembers(householdSerialNumber);
        isEmptyHouseholdMember(householdMembers);
        return householdMembers;
    }

    private void isEmptyHouseholdMember(List<HouseholdMemberViewResponse> householdMembers) {
        if (householdMembers.isEmpty()) {
            throw new HouseholdMemberNotFoundException("해당하는 세대의 구성원이 존재하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Long getSerialNumber(Long serialNumber) {
        Long householdNumber = householdCompositionResidentRepository.findByResidentSerialNumber(serialNumber);
        isHouseholdNotExist(householdNumber);
        return householdNumber;
    }

    private void isHouseholdNotExist(Long householdNumber) {
        if (householdNumber == null) {
            throw new HouseholdNotFoundException("해당하는 세대가 없습니다.");
        }
    }

    @Override
    public List<HouseholdMovementAddress> getMovementAddresses(Long householdSerialNumber) {
        List<HouseholdMovementAddress> addressList = hmaRepository.findAllByHousehold_HouseholdSerialNumberOrderByPk_HouseMovementReportDateDesc(householdSerialNumber);
        isEmptyHouseholdMovementAddress(addressList);
        return addressList;
    }

    private void isEmptyHouseholdMovementAddress(List<HouseholdMovementAddress> addressList) {
        if (addressList.isEmpty()) {
            throw new HouseholdAddressNotFoundException("주소 변동 내역이 존재하지 않습니다.");
        }
    }

    @Override
    public HouseholdResponse registerHousehold(HouseholdRegisterRequest request) {
        Resident householdResident = getResident(request.getHouseholdResidentSerialNumber());

        Household household = extractHousehold(request, householdResident);
        householdRepository.save(household);

        HouseholdMovementAddress movementAddress = extractMovementAddress(request, household);
        hmaRepository.save(movementAddress);

        HouseholdCompositionResident compositionResident = extractCompositionResident(request, householdResident, household);
        householdCompositionResidentRepository.save(compositionResident);

        return HouseholdResponse.of(householdResident, household, movementAddress, compositionResident);
    }

    private HouseholdCompositionResident extractCompositionResident(HouseholdRegisterRequest request, Resident householdResident, Household household) {
        return HouseholdCompositionResident.builder()
                    .pk(new HouseholdCompositionResident.Pk(request.getHouseholdSerialNumber(), request.getHouseholdResidentSerialNumber()))
                    .resident(householdResident)
                    .household(household)
                    .reportDate(request.getReportDate())
                    .householdRelationshipCode(request.getHouseholdRelationshipCode())
                    .householdCompositionChangeReasonCode(request.getHouseholdCompositionChangeReasonCode())
                    .build();
    }

    private HouseholdMovementAddress extractMovementAddress(HouseholdRegisterRequest request, Household household) {
        return HouseholdMovementAddress.builder()
                    .pk(new HouseholdMovementAddress.Pk(request.getReportDate(), request.getHouseholdSerialNumber()))
                    .household(household)
                    .houseMovementAddress(request.getCurrentHouseMovementAddress())
                    .lastAddressYn(request.getLastAddressYn())
                    .build();
    }

    private Household extractHousehold(HouseholdRegisterRequest request, Resident householdResident) {
        return Household.builder()
                .householdSerialNumber(request.getHouseholdSerialNumber())
                .householdResident(householdResident)
                .householdCompositionDate(request.getReportDate())
                .householdCompositionReasonCode(request.getHouseholdCompositionReasonCode())
                .currentHouseMovementAddress(request.getCurrentHouseMovementAddress())
                .build();
    }


    @Override
    public void deleteHousehold(Long householdSerialNumber) {
        householdCompositionResidentRepository.deleteAllByHouseholdSerialNumber(householdSerialNumber);
        hmaRepository.deleteAllByHouseholdSerialNumber(householdSerialNumber);
        householdRepository.deleteById(householdSerialNumber);
    }

    @Override
    public HouseholdMemberResponse registerHouseholdMember(Long householdSerialNumber, HouseholdMemberRegisterRequest request) {
        Resident householdMember = getResident(request.getHouseholdResidentSerialNumber());

        Household household = getHousehold(householdSerialNumber);

        HouseholdCompositionResident householdComposition = extractHouseholdComposition(householdSerialNumber, request, householdMember, household);

        householdCompositionResidentRepository.save(householdComposition);

        return HouseholdMemberResponse.of(householdComposition);
    }

    private HouseholdCompositionResident extractHouseholdComposition(Long householdSerialNumber, HouseholdMemberRegisterRequest request, Resident householdMember, Household household) {
        return HouseholdCompositionResident.builder()
                .pk(new HouseholdCompositionResident.Pk(householdSerialNumber, request.getHouseholdResidentSerialNumber()))
                .resident(householdMember)
                .household(household)
                .reportDate(request.getReportDate())
                .householdRelationshipCode(request.getHouseholdRelationshipCode())
                .householdCompositionChangeReasonCode(request.getHouseholdCompositionChangeReasonCode())
                .build();
    }

    @Override
    public void deleteHouseholdMember(Long householdSerialNumber, Long householdResidentSerialNumber) {
        HouseholdCompositionResident.Pk id = new HouseholdCompositionResident.Pk(householdSerialNumber, householdResidentSerialNumber);
        householdCompositionResidentRepository.deleteById(id);
    }

    @Override
    public HouseholdMovementResponse registerHouseholdMovement(Long householdSerialNumber, HouseholdMovementRegisterRequest request) {
        Household household = getHousehold(householdSerialNumber);
        household.setCurrentHouseMovementAddress(request.getHouseMovementAddress());
        householdRepository.save(household);

        List<HouseholdMovementAddress> addresses = hmaRepository.findAllByHousehold_HouseholdSerialNumberOrderByPk_HouseMovementReportDateDesc(householdSerialNumber);
        HouseholdMovementAddress address = addresses.get(0);
        address.setLastAddressYn("N");
        hmaRepository.save(address);

        HouseholdMovementAddress movementAddress = extractHouseholdMovementAddress(householdSerialNumber, request, household);

        hmaRepository.save(movementAddress);

        return HouseholdMovementResponse.of(movementAddress);
    }

    private HouseholdMovementAddress extractHouseholdMovementAddress(Long householdSerialNumber, HouseholdMovementRegisterRequest request, Household household) {
        return HouseholdMovementAddress.builder()
                .pk(new HouseholdMovementAddress.Pk(request.getHouseMovementReportDate(), householdSerialNumber))
                .household(household)
                .houseMovementAddress(request.getHouseMovementAddress())
                .lastAddressYn(request.getLastAddressYn())
                .build();
    }

    @Override
    public HouseholdMovementResponse editHouseholdMovement(Long householdSerialNumber, LocalDateTime reportDate, HouseholdMovementEditRequest request) {
        HouseholdMovementAddress address = getHouseholdMovementAddress(householdSerialNumber, reportDate);
        address.setHouseMovementAddress(request.getHouseMovementAddress());
        address.setLastAddressYn(request.getLastAddressYn());
        hmaRepository.save(address);

        return HouseholdMovementResponse.of(address);
    }

    private HouseholdMovementAddress getHouseholdMovementAddress(Long householdSerialNumber, LocalDateTime reportDate) {
        Optional<HouseholdMovementAddress> optionalAddress = hmaRepository.findById(new HouseholdMovementAddress.Pk(reportDate, householdSerialNumber));
        if (optionalAddress.isEmpty()) {
            throw new HouseholdAddressNotFoundException("없음");
        }
        return optionalAddress.get();
    }

    @Override
    public void deleteHouseholdMovement(LocalDateTime reportDate, Long householdSerialNumber) {
        hmaRepository.deleteByReportDate(reportDate);
        List<HouseholdMovementAddress> addresses = hmaRepository.findAllByHousehold_HouseholdSerialNumberOrderByPk_HouseMovementReportDateDesc(householdSerialNumber);
        HouseholdMovementAddress address = addresses.get(0);
        address.setLastAddressYn("Y");
        String lastAddress = address.getHouseMovementAddress();
        hmaRepository.save(address);

        Household household = getHousehold(householdSerialNumber);
        household.setCurrentHouseMovementAddress(lastAddress);
        householdRepository.save(household);
    }

    private Resident getResident(Long residentSerialNumber) {
        Optional<Resident> optionalResident = residentRepository.findById(residentSerialNumber);
        if (optionalResident.isEmpty()) {
            throw new ResidentNotFoundException("해당하는 주민이 없습니다.");
        }
        return optionalResident.get();
    }

    private Household getHousehold(Long householdSerialNumber) {
        Optional<Household> optionalHousehold = householdRepository.findById(householdSerialNumber);
        if (optionalHousehold.isEmpty()) {
            throw new HouseholdNotFoundException("해당하는 세대가 없습니다.");
        }
        return optionalHousehold.get();
    }
}
