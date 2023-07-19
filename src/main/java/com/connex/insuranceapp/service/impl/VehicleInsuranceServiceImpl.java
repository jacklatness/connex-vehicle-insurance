package com.connex.insuranceapp.service.impl;

import com.connex.insuranceapp.domain.VehicleInsurance;
import com.connex.insuranceapp.repository.VehicleInsuranceRepository;
import com.connex.insuranceapp.service.VehicleInsuranceService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleInsurance}.
 */
@Service
@Transactional
public class VehicleInsuranceServiceImpl implements VehicleInsuranceService {

    private final Logger log = LoggerFactory.getLogger(VehicleInsuranceServiceImpl.class);

    private final VehicleInsuranceRepository vehicleInsuranceRepository;

    public VehicleInsuranceServiceImpl(VehicleInsuranceRepository vehicleInsuranceRepository) {
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
    }

    @Override
    public VehicleInsurance save(VehicleInsurance vehicleInsurance) {
        log.debug("Request to save VehicleInsurance : {}", vehicleInsurance);
        return vehicleInsuranceRepository.save(vehicleInsurance);
    }

    @Override
    public VehicleInsurance update(VehicleInsurance vehicleInsurance) {
        log.debug("Request to update VehicleInsurance : {}", vehicleInsurance);
        return vehicleInsuranceRepository.save(vehicleInsurance);
    }

    @Override
    public Optional<VehicleInsurance> partialUpdate(VehicleInsurance vehicleInsurance) {
        log.debug("Request to partially update VehicleInsurance : {}", vehicleInsurance);

        return vehicleInsuranceRepository
            .findById(vehicleInsurance.getId())
            .map(existingVehicleInsurance -> {
                if (vehicleInsurance.getName() != null) {
                    existingVehicleInsurance.setName(vehicleInsurance.getName());
                }
                if (vehicleInsurance.getAge() != null) {
                    existingVehicleInsurance.setAge(vehicleInsurance.getAge());
                }
                if (vehicleInsurance.getDriving_experience() != null) {
                    existingVehicleInsurance.setDriving_experience(vehicleInsurance.getDriving_experience());
                }
                if (vehicleInsurance.getDriver_record() != null) {
                    existingVehicleInsurance.setDriver_record(vehicleInsurance.getDriver_record());
                }
                if (vehicleInsurance.getClaims() != null) {
                    existingVehicleInsurance.setClaims(vehicleInsurance.getClaims());
                }
                if (vehicleInsurance.getCar_value() != null) {
                    existingVehicleInsurance.setCar_value(vehicleInsurance.getCar_value());
                }
                if (vehicleInsurance.getAnnual_mileage() != null) {
                    existingVehicleInsurance.setAnnual_mileage(vehicleInsurance.getAnnual_mileage());
                }
                if (vehicleInsurance.getInsurance_history() != null) {
                    existingVehicleInsurance.setInsurance_history(vehicleInsurance.getInsurance_history());
                }

                return existingVehicleInsurance;
            })
            .map(vehicleInsuranceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleInsurance> findAll() {
        log.debug("Request to get all VehicleInsurances");
        return vehicleInsuranceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleInsurance> findOne(Long id) {
        log.debug("Request to get VehicleInsurance : {}", id);
        return vehicleInsuranceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleInsurance : {}", id);
        vehicleInsuranceRepository.deleteById(id);
    }
}
