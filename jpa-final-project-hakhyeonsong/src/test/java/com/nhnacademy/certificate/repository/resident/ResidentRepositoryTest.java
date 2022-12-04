package com.nhnacademy.certificate.repository.resident;

import com.nhnacademy.certificate.config.RootConfig;
import com.nhnacademy.certificate.config.WebConfig;
import com.nhnacademy.certificate.entity.Resident;
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
class ResidentRepositoryTest {

    @Autowired
    ResidentRepository residentRepository;

    @Test
    @DisplayName("주민 번호로 해당 주민을 불러온다.")
    void findById_success() throws Exception {
        //given
        long residentId = 1L;

        //when
        Optional<Resident> optionalResident = residentRepository.findById(residentId);

        //then
        assertThat(optionalResident).isPresent();

        Resident resident = optionalResident.get();
        assertThat(resident.getName()).isEqualTo("남길동");
        assertThat(resident.getGenderCode()).isEqualTo("남");
        assertThat(resident.getBirthPlaceCode()).isEqualTo("자택");
    }
}