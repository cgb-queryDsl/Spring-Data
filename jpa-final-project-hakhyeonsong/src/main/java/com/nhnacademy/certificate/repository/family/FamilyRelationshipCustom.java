package com.nhnacademy.certificate.repository.family;

import com.nhnacademy.certificate.dto.web.FamilyResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface FamilyRelationshipCustom {
    List<FamilyResponse> findMyFamily(Long residentSerialNumber);
}
