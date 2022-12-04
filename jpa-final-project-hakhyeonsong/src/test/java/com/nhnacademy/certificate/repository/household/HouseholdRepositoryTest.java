package com.nhnacademy.certificate.repository.household;

import com.nhnacademy.certificate.config.RootConfig;
import com.nhnacademy.certificate.config.WebConfig;
import com.nhnacademy.certificate.entity.Household;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class HouseholdRepositoryTest {

    @Autowired
    HouseholdRepository repository;

    @Test
    @DisplayName("세대 번호로 해당 세대를 불러온다.")
    void findById_success() throws Exception {
        //given
        long householdId = 1L;

        //when
        Optional<Household> optionalHousehold = repository.findById(householdId);

        //then
        assertThat(optionalHousehold).isPresent();
    }
}