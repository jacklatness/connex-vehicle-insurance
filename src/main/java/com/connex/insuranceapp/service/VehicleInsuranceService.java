package com.connex.insuranceapp.service;

import com.connex.insuranceapp.domain.VehicleInsurance;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VehicleInsurance}.
 */
public interface VehicleInsuranceService {
    /**
     * Save a vehicleInsurance.
     *
     * @param vehicleInsurance the entity to save.
     * @return the persisted entity.
     */
    VehicleInsurance save(VehicleInsurance vehicleInsurance);

    /**
     * Updates a vehicleInsurance.
     *
     * @param vehicleInsurance the entity to update.
     * @return the persisted entity.
     */
    VehicleInsurance update(VehicleInsurance vehicleInsurance);

    /**
     * Partially updates a vehicleInsurance.
     *
     * @param vehicleInsurance the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VehicleInsurance> partialUpdate(VehicleInsurance vehicleInsurance);

    /**
     * Get all the vehicleInsurances.
     *
     * @return the list of entities.
     */
    List<VehicleInsurance> findAll();

    /**
     * Get the "id" vehicleInsurance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VehicleInsurance> findOne(Long id);

    /**
     * Delete the "id" vehicleInsurance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
