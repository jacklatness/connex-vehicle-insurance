package com.connex.insuranceapp.web.rest;

import com.connex.insuranceapp.domain.VehicleInsurance;
import com.connex.insuranceapp.repository.VehicleInsuranceRepository;
import com.connex.insuranceapp.service.VehicleInsuranceService;
import com.connex.insuranceapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.connex.insuranceapp.domain.VehicleInsurance}.
 */
@RestController
@RequestMapping("/api")
public class VehicleInsuranceResource {

    private final Logger log = LoggerFactory.getLogger(VehicleInsuranceResource.class);

    private static final String ENTITY_NAME = "vehicleInsurance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleInsuranceService vehicleInsuranceService;

    private final VehicleInsuranceRepository vehicleInsuranceRepository;

    public VehicleInsuranceResource(
        VehicleInsuranceService vehicleInsuranceService,
        VehicleInsuranceRepository vehicleInsuranceRepository
    ) {
        this.vehicleInsuranceService = vehicleInsuranceService;
        this.vehicleInsuranceRepository = vehicleInsuranceRepository;
    }

    /**
     * {@code POST  /vehicle-insurances} : Create a new vehicleInsurance.
     *
     * @param vehicleInsurance the vehicleInsurance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleInsurance, or with status {@code 400 (Bad Request)} if the vehicleInsurance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-insurances")
    public ResponseEntity<VehicleInsurance> createVehicleInsurance(@Valid @RequestBody VehicleInsurance vehicleInsurance)
        throws URISyntaxException {
        log.debug("REST request to save VehicleInsurance : {}", vehicleInsurance);
        if (vehicleInsurance.getId() != null) {
            throw new BadRequestAlertException("A new vehicleInsurance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleInsurance result = vehicleInsuranceService.save(vehicleInsurance);
        return ResponseEntity
            .created(new URI("/api/vehicle-insurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-insurances/:id} : Updates an existing vehicleInsurance.
     *
     * @param id the id of the vehicleInsurance to save.
     * @param vehicleInsurance the vehicleInsurance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleInsurance,
     * or with status {@code 400 (Bad Request)} if the vehicleInsurance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleInsurance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-insurances/{id}")
    public ResponseEntity<VehicleInsurance> updateVehicleInsurance(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleInsurance vehicleInsurance
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleInsurance : {}, {}", id, vehicleInsurance);
        if (vehicleInsurance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleInsurance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleInsuranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleInsurance result = vehicleInsuranceService.update(vehicleInsurance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vehicleInsurance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-insurances/:id} : Partial updates given fields of an existing vehicleInsurance, field will ignore if it is null
     *
     * @param id the id of the vehicleInsurance to save.
     * @param vehicleInsurance the vehicleInsurance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleInsurance,
     * or with status {@code 400 (Bad Request)} if the vehicleInsurance is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleInsurance is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleInsurance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-insurances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VehicleInsurance> partialUpdateVehicleInsurance(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleInsurance vehicleInsurance
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleInsurance partially : {}, {}", id, vehicleInsurance);
        if (vehicleInsurance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleInsurance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleInsuranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleInsurance> result = vehicleInsuranceService.partialUpdate(vehicleInsurance);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vehicleInsurance.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-insurances} : get all the vehicleInsurances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleInsurances in body.
     */
    @GetMapping("/vehicle-insurances")
    public List<VehicleInsurance> getAllVehicleInsurances() {
        log.debug("REST request to get all VehicleInsurances");
        return vehicleInsuranceService.findAll();
    }

    /**
     * {@code GET  /vehicle-insurances/:id} : get the "id" vehicleInsurance.
     *
     * @param id the id of the vehicleInsurance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleInsurance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-insurances/{id}")
    public ResponseEntity<VehicleInsurance> getVehicleInsurance(@PathVariable Long id) {
        log.debug("REST request to get VehicleInsurance : {}", id);
        Optional<VehicleInsurance> vehicleInsurance = vehicleInsuranceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleInsurance);
    }

    /**
     * {@code GET  /vehicle-insurances/calculate/:id} : get the "id" vehicleInsurance.
     *
     * @param id the id of the vehicleInsurance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleInsurance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-insurances/calculate/{id}")
    public Double calculateVehicleInsurance(@PathVariable Long id) {
        log.debug("REST request to get VehicleInsurance : {}", id);
        Optional<VehicleInsurance> vehicleInsurance = vehicleInsuranceService.findOne(id);
        return vehicleInsuranceService.calculate(vehicleInsurance.get());
    }

    /**
     * {@code DELETE  /vehicle-insurances/:id} : delete the "id" vehicleInsurance.
     *
     * @param id the id of the vehicleInsurance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-insurances/{id}")
    public ResponseEntity<Void> deleteVehicleInsurance(@PathVariable Long id) {
        log.debug("REST request to delete VehicleInsurance : {}", id);
        vehicleInsuranceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
