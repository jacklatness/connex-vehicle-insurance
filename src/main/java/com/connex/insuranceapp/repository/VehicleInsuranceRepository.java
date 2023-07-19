package com.connex.insuranceapp.repository;

import com.connex.insuranceapp.domain.VehicleInsurance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VehicleInsurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleInsuranceRepository extends JpaRepository<VehicleInsurance, Long> {}
