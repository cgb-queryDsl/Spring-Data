package com.nhnacademy.certificate.repository.birthdeath;

import com.nhnacademy.certificate.entity.BirthDeathReportResident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthDeathReportResidentRepository extends JpaRepository<BirthDeathReportResident, BirthDeathReportResident.Pk>, BirthDeathReportResidentCustom {
}
