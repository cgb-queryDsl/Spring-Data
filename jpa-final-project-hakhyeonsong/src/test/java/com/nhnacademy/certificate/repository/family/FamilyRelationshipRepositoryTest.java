package com.nhnacademy.certificate.repository.family;

import com.nhnacademy.certificate.config.RootConfig;
import com.nhnacademy.certificate.config.WebConfig;
import com.nhnacademy.certificate.dto.web.FamilyResponse;
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
class FamilyRelationshipRepositoryTest {

    @Autowired
    FamilyRelationshipRepository repository;

    @Test
    @DisplayName("내 가족 구성원을 불러온다.")
    void findMyFamily_success() throws Exception {
        //given

        //when
        List<FamilyResponse> list = repository.findMyFamily(4L);

        //then
        FamilyResponse response = list.get(0);
        assertThat(list).hasSize(4);
        assertThat(response.getFamilyRelationshipCode()).isEqualTo("부");
        assertThat(response.getName()).isEqualTo("남석환");
        assertThat(response.getBirthDate().toString()).isEqualTo("1954-05-14T17:30");
        assertThat(response.getResidentRegistrationNumber()).isEqualTo("540514-1234562");
        assertThat(response.getGenderCode()).isEqualTo("남");
    }
}