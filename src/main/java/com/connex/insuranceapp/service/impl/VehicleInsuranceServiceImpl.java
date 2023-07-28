package com.connex.insuranceapp.service.impl;

import com.connex.insuranceapp.domain.VehicleInsurance;
import com.connex.insuranceapp.repository.VehicleInsuranceRepository;
import com.connex.insuranceapp.service.VehicleInsuranceService;
import java.lang.Double;
import java.math.BigDecimal;
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
    public Double calculate(VehicleInsurance vehicleInsurance) {
        log.debug("Request to calculate VehicleInsurance : {}", vehicleInsurance);

        Double ageFactor, drivingExpFactor, driverRecFactor, claimsFactor, carValueFactor, mileageFactor, insuranceHistoryFactor;

        ageFactor = calculateAgeFactor(vehicleInsurance.getAge());
        drivingExpFactor = calculateDrivingExperienceFactor(vehicleInsurance.getDriving_experience());
        driverRecFactor = calculateDriverRecordFactor(vehicleInsurance.getDriver_record());
        claimsFactor = calculateClaimsFactor(vehicleInsurance.getClaims());
        carValueFactor = calculateCarValueFactor(vehicleInsurance.getCar_value());
        mileageFactor = calculatMileageFactor(vehicleInsurance.getAnnual_mileage());
        insuranceHistoryFactor = calculatInsuranceHistoryFactor(vehicleInsurance.getInsurance_history());

        log.debug("calculateAgeFactor : {}", ageFactor);
        log.debug("calculateDrivingExperienceFactor : {}", drivingExpFactor);
        log.debug("calculateDriverRecordFactor : {}", driverRecFactor);
        log.debug("calculateClaimsFactor : {}", claimsFactor);
        log.debug("calculateCarValueFactor : {}", carValueFactor);
        log.debug("calculatMileageFactor : {}", mileageFactor);
        log.debug("calculatInsuranceHistoryFactor : {}", insuranceHistoryFactor);

        log.debug(
            "Product of Factors : {}",
            (ageFactor * drivingExpFactor * driverRecFactor * claimsFactor * carValueFactor * mileageFactor * insuranceHistoryFactor)
        );

        return (ageFactor * drivingExpFactor * driverRecFactor * claimsFactor * carValueFactor * mileageFactor * insuranceHistoryFactor);
    }

    private Double calculateAgeFactor(Integer age) {
        Double factor = 0.0;

        if (age < 25) {
            factor = 1.3;
        } else if (age >= 25 && age < 40) {
            factor = 1.0;
        } else if (age >= 40 && age < 70) {
            factor = 0.9;
        } else if (age >= 70) {
            factor = 1.0;
        }

        return factor;
    }

    private Double calculateDrivingExperienceFactor(Integer drivingExperience) {
        Double factor = 0.0;

        if (drivingExperience < 2) {
            factor = 1.5;
        } else if (drivingExperience >= 2 && drivingExperience < 5) {
            factor = 1.3;
        } else if (drivingExperience >= 5 && drivingExperience < 10) {
            factor = 1.0;
        } else if (drivingExperience >= 10) {
            factor = 0.9;
        }

        return factor;
    }

    private Double calculateDriverRecordFactor(Integer driverRecord) {
        Double factor = 0.0;

        if (driverRecord.equals(0)) {
            factor = 1.0;
        } else if (driverRecord.equals(1)) {
            factor = 1.1;
        } else if (driverRecord.equals(2) || driverRecord.equals(3)) {
            factor = 1.3;
        } else if (driverRecord > 3) {
            factor = 1.0;
        }

        return factor;
    }

    private Double calculateClaimsFactor(Integer claims) {
        Double factor = 0.0;

        if (claims.equals(0)) {
            factor = 0.9;
        } else if (claims.equals(1)) {
            factor = 1.2;
        } else if (claims.equals(2) || claims.equals(3)) {
            factor = 1.5;
        } else if (claims > 3) {
            factor = 1.0;
        }

        return factor;
    }

    private Double calculateCarValueFactor(BigDecimal carValue) {
        Double factor = 0.0;
        BigDecimal v30, v60, v100, v150, v200;

        v30 = new BigDecimal(30000);
        v60 = new BigDecimal(60000);
        v100 = new BigDecimal(100000);
        v150 = new BigDecimal(150000);
        v200 = new BigDecimal(200000);

        if (carValue.compareTo(v30) < 0) {
            factor = 0.8;
        } else if (carValue.compareTo(v30) >= 0 && carValue.compareTo(v60) < 0) {
            factor = 1.0;
        } else if (carValue.compareTo(v60) >= 0 && carValue.compareTo(v100) < 0) {
            factor = 1.2;
        } else if (carValue.compareTo(v100) >= 0 && carValue.compareTo(v150) < 0) {
            factor = 1.5;
        } else if (carValue.compareTo(v150) >= 0 && carValue.compareTo(v200) < 0) {
            factor = 2.0;
        } else if (carValue.compareTo(v200) >= 0) {
            factor = 1.0;
        }

        return factor;
    }

    private Double calculatMileageFactor(BigDecimal annualMileage) {
        Double factor = 0.0;
        BigDecimal v20, v30, v50;

        v20 = new BigDecimal(20000);
        v30 = new BigDecimal(30000);
        v50 = new BigDecimal(50000);

        if (annualMileage.compareTo(v20) < 0) {
            factor = 0.9;
        } else if (annualMileage.compareTo(v20) >= 0 && annualMileage.compareTo(v30) < 0) {
            factor = 1.0;
        } else if (annualMileage.compareTo(v30) >= 0 && annualMileage.compareTo(v50) < 0) {
            factor = 1.1;
        } else if (annualMileage.compareTo(v50) >= 0) {
            factor = 1.3;
        }

        return factor;
    }

    private Double calculatInsuranceHistoryFactor(Integer insuranceHistory) {
        Double factor = 0.0;

        if (insuranceHistory.equals(0)) {
            factor = 1.2;
        } else if (insuranceHistory <= 2) {
            factor = 1.1;
        } else if (insuranceHistory > 2) {
            factor = 1.0;
        }

        return factor;
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
                if (vehicleInsurance.getBirthdate() != null) {
                    existingVehicleInsurance.setBirthdate(vehicleInsurance.getBirthdate());
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
                if (vehicleInsurance.getCar_category() != null) {
                    existingVehicleInsurance.setCar_category(vehicleInsurance.getCar_category());
                }
                if (vehicleInsurance.getCar_make() != null) {
                    existingVehicleInsurance.setCar_make(vehicleInsurance.getCar_make());
                }
                if (vehicleInsurance.getCar_model() != null) {
                    existingVehicleInsurance.setCar_model(vehicleInsurance.getCar_model());
                }
                if (vehicleInsurance.getCar_year() != null) {
                    existingVehicleInsurance.setCar_year(vehicleInsurance.getCar_year());
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
