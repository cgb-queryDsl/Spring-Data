package com.nhnacademy.certificate.repository.birthdeath;

import com.nhnacademy.certificate.config.RootConfig;
import com.nhnacademy.certificate.config.WebConfig;
import com.nhnacademy.certificate.dto.web.BirthCertificateResponse;
import com.nhnacademy.certificate.dto.web.DeathCertificateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class BirthDeathReportResidentRepositoryTest {

    @Autowired
    BirthDeathReportResidentRepository repository;

    @Test
    @DisplayName("7번 주민의 출생신고서를 불러온다.")
    void getBirthCertification_success() throws Exception {
        //given
        long targetSerialNumber = 7L;

        //when
        BirthCertificateResponse birthCertification = repository.getBirthCertification(targetSerialNumber);

        //then
        assertThat(birthCertification.getTargetName()).isEqualTo("남기석");
        assertThat(birthCertification.getGenderCode()).isEqualTo("남");
        assertThat(birthCertification.getBirthDate().toString()).isEqualTo("2012-03-17T00:00");
        assertThat(birthCertification.getBirthPlaceCode()).isEqualTo("병원");
        assertThat(birthCertification.getReporterName()).isEqualTo("남기준");
        assertThat(birthCertification.getReportQualificationsCode()).isEqualTo("부");
    }

    @Test
    @DisplayName("출생신고서가 없는 주민의 경우 null을 반환한다.")
    void getBirthCertification_fail() throws Exception {
        //given
        long targetSerialNumber = 1L;

        //when
        BirthCertificateResponse birthCertification = repository.getBirthCertification(targetSerialNumber);

        //then
        assertThat(birthCertification).isNull();
    }

    @Test
    @DisplayName("1번 주민의 사망신고서를 불러온다.")
    void getDeathCertification_success() throws Exception {
        //given
        long targetSerialNumber = 1L;

        //when
        DeathCertificateResponse deathCertification = repository.getDeathCertification(targetSerialNumber);

        //then
        assertThat(deathCertification.getTargetName()).isEqualTo("남길동");
        assertThat(deathCertification.getTargetResidentRegistrationNumber()).isEqualTo("130914-1234561");
        assertThat(deathCertification.getDeathDate().toString()).isEqualTo("2021-04-29T09:03");
        assertThat(deathCertification.getDeathPlaceCode()).isEqualTo("주택");
        assertThat(deathCertification.getDeathPlaceAddress()).isEqualTo("강원도 고성군 금강산로 290번길");
        assertThat(deathCertification.getReporterName()).isEqualTo("남석환");
    }

    @Test
    @DisplayName("사망 신고서가 없는 주민의 경우 null을 반환한다.")
    void getDeathCertification_fail_returnNull() throws Exception {
        //given
        long targetSerialNumber = 7L;

        //when
        DeathCertificateResponse deathCertification = repository.getDeathCertification(targetSerialNumber);

        //then
        assertThat(deathCertification).isNull();
    }
}