package com.nhnacademy.certificate.repository.household;

import com.nhnacademy.certificate.dto.web.HouseholdMemberViewResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface HouseholdCompositionResidentCustom {
    Long findByResidentSerialNumber(Long serialNumber);
    List<HouseholdMemberViewResponse> getAllMembers(Long householdSerialNumber);
}
