package com.nhnacademy.certificate.repository.household;

import com.nhnacademy.certificate.config.RootConfig;
import com.nhnacademy.certificate.config.WebConfig;
import com.nhnacademy.certificate.entity.HouseholdMovementAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class HouseholdMovementAddressRepositoryTest {

    @Autowired
    HouseholdMovementAddressRepository repository;

    @Test
    @DisplayName("1번 세대의 주소 이전 정보를 불러온다.")
    void findAllByHousehold_HouseholdSerialNumber_success() throws Exception {
        //given
        long householdSerialNumber = 1L;

        //when
        List<HouseholdMovementAddress> list = repository.findAllByHousehold_HouseholdSerialNumberOrderByPk_HouseMovementReportDateDesc(householdSerialNumber);

        //then
        HouseholdMovementAddress householdMovementAddress = list.get(0);
        assertThat(list).hasSize(3);
        assertThat(householdMovementAddress.getHouseMovementAddress()).isEqualTo("경기도 성남시 분당구 대왕판교로 645번길");
        assertThat(householdMovementAddress.getPk().getHouseMovementReportDate().toString()).isEqualTo("2013-03-05T00:00");
    }

    @Test
    @DisplayName("입력된 날짜 정보에 해당하는 주소 이전 정보를 삭제한다.")
    void deleteByReportDate_success() throws Exception {
        //given
        LocalDate localDate = LocalDate.parse("20071031", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDateTime inputTime = LocalDateTime.of(localDate, LocalTime.MIN);

        //when, then
        repository.deleteByReportDate(inputTime);
    }

    @Test
    @DisplayName("1번 세대의 주소 이전 정보를 전부 삭제한다.")
    void test() throws Exception {
        //given
        long householdSerialNumber = 1L;

        //when, then
        repository.deleteAllByHouseholdSerialNumber(householdSerialNumber);

    }
}