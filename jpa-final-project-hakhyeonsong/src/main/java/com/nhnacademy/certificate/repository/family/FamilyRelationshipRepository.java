package com.nhnacademy.certificate.repository.family;

import com.nhnacademy.certificate.entity.FamilyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.nhnacademy.certificate.entity.FamilyRelationship.*;

public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, Pk>, FamilyRelationshipCustom {
}
