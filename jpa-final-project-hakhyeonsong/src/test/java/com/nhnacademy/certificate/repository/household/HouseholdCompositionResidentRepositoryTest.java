package com.nhnacademy.certificate.repository.household;

import com.nhnacademy.certificate.config.RootConfig;
import com.nhnacademy.certificate.config.WebConfig;
import com.nhnacademy.certificate.dto.web.HouseholdMemberViewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class HouseholdCompositionResidentRepositoryTest {

    @Autowired
    HouseholdCompositionResidentRepository repository;

    @Test
    @DisplayName("1번 주민이 속한 세대의 세대주는 존재하지 않는다.")
    void findByResidentSerialNumber_fail() throws Exception {
        //given
        long residentSerialNumber = 1L;

        //when
        Long householdSerialNumber = repository.findByResidentSerialNumber(residentSerialNumber);

        //then
        assertThat(householdSerialNumber).isNull();
    }

    @Test
    @DisplayName("1번 세대의 구성원 모두를 불러온다.")
    void getAllHouseholdMember_success() throws Exception {
        //given
        long householdSerialNumber = 1L;

        //when
        List<HouseholdMemberViewResponse> members = repository.getAllMembers(householdSerialNumber);

        //then
        assertThat(members).hasSize(4);
    }
}