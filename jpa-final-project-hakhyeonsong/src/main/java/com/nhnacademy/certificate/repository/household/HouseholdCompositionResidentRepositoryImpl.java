package com.nhnacademy.certificate.repository.household;

import com.nhnacademy.certificate.dto.web.HouseholdMemberViewResponse;
import com.nhnacademy.certificate.dto.web.QHouseholdMemberViewResponse;
import com.nhnacademy.certificate.entity.HouseholdCompositionResident;
import com.nhnacademy.certificate.entity.QHouseholdCompositionResident;
import com.nhnacademy.certificate.entity.QResident;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class HouseholdCompositionResidentRepositoryImpl extends QuerydslRepositorySupport implements HouseholdCompositionResidentCustom {
    public HouseholdCompositionResidentRepositoryImpl() {
        super(HouseholdCompositionResident.class);
    }

    @Override
    public Long findByResidentSerialNumber(Long serialNumber) {
        QHouseholdCompositionResident hcr = QHouseholdCompositionResident.householdCompositionResident;

        return from(hcr)
                .select(hcr.pk.householdSerialNumber)
                .where(hcr.pk.residentSerialNumber.eq(serialNumber))
                .fetchOne();
    }

    @Override
    public List<HouseholdMemberViewResponse> getAllMembers(Long householdSerialNumber) {
        QHouseholdCompositionResident hcr = QHouseholdCompositionResident.householdCompositionResident;
        QResident resident = QResident.resident;

        return from(hcr)
                .select(new QHouseholdMemberViewResponse(
                        hcr.householdRelationshipCode,
                        resident.name,
                        resident.residentRegistrationNumber,
                        hcr.reportDate,
                        hcr.householdCompositionChangeReasonCode
                ))
                .innerJoin(hcr.resident, resident)
                .where(hcr.pk.householdSerialNumber.eq(householdSerialNumber))
                .fetch();
    }
}
