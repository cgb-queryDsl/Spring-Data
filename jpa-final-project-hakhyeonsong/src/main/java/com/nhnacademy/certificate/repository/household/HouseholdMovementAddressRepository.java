package com.nhnacademy.certificate.repository.household;

import com.nhnacademy.certificate.entity.HouseholdMovementAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.nhnacademy.certificate.entity.HouseholdMovementAddress.*;

public interface HouseholdMovementAddressRepository extends JpaRepository<HouseholdMovementAddress, Pk> {
    @Transactional
    @Modifying
    @Query("delete from HouseholdMovementAddress hma where hma.pk.householdSerialNumber = :householdSerialNumber")
    void deleteAllByHouseholdSerialNumber(@Param("householdSerialNumber") Long householdSerialNumber);

    @Transactional
    @Modifying
    @Query("delete from HouseholdMovementAddress hma where hma.pk.houseMovementReportDate = :reportDate")
    void deleteByReportDate(@Param("reportDate") LocalDateTime reportDate);

    List<HouseholdMovementAddress> findAllByHousehold_HouseholdSerialNumberOrderByPk_HouseMovementReportDateDesc(Long householdSerialNumber);
}
