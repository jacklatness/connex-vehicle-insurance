package com.connex.insuranceapp.web.rest;

import static com.connex.insuranceapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.connex.insuranceapp.IntegrationTest;
import com.connex.insuranceapp.domain.VehicleInsurance;
import com.connex.insuranceapp.repository.VehicleInsuranceRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VehicleInsuranceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleInsuranceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Integer DEFAULT_DRIVING_EXPERIENCE = 1;
    private static final Integer UPDATED_DRIVING_EXPERIENCE = 2;

    private static final Integer DEFAULT_DRIVER_RECORD = 1;
    private static final Integer UPDATED_DRIVER_RECORD = 2;

    private static final Integer DEFAULT_CLAIMS = 1;
    private static final Integer UPDATED_CLAIMS = 2;

    private static final BigDecimal DEFAULT_CAR_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CAR_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ANNUAL_MILEAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUAL_MILEAGE = new BigDecimal(2);

    private static final Integer DEFAULT_INSURANCE_HISTORY = 1;
    private static final Integer UPDATED_INSURANCE_HISTORY = 2;

    private static final String ENTITY_API_URL = "/api/vehicle-insurances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleInsuranceRepository vehicleInsuranceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleInsuranceMockMvc;

    private VehicleInsurance vehicleInsurance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleInsurance createEntity(EntityManager em) {
        VehicleInsurance vehicleInsurance = new VehicleInsurance()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .driving_experience(DEFAULT_DRIVING_EXPERIENCE)
            .driver_record(DEFAULT_DRIVER_RECORD)
            .claims(DEFAULT_CLAIMS)
            .car_value(DEFAULT_CAR_VALUE)
            .annual_mileage(DEFAULT_ANNUAL_MILEAGE)
            .insurance_history(DEFAULT_INSURANCE_HISTORY);
        return vehicleInsurance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleInsurance createUpdatedEntity(EntityManager em) {
        VehicleInsurance vehicleInsurance = new VehicleInsurance()
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .driving_experience(UPDATED_DRIVING_EXPERIENCE)
            .driver_record(UPDATED_DRIVER_RECORD)
            .claims(UPDATED_CLAIMS)
            .car_value(UPDATED_CAR_VALUE)
            .annual_mileage(UPDATED_ANNUAL_MILEAGE)
            .insurance_history(UPDATED_INSURANCE_HISTORY);
        return vehicleInsurance;
    }

    @BeforeEach
    public void initTest() {
        vehicleInsurance = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleInsurance() throws Exception {
        int databaseSizeBeforeCreate = vehicleInsuranceRepository.findAll().size();
        // Create the VehicleInsurance
        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleInsurance testVehicleInsurance = vehicleInsuranceList.get(vehicleInsuranceList.size() - 1);
        assertThat(testVehicleInsurance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleInsurance.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testVehicleInsurance.getDriving_experience()).isEqualTo(DEFAULT_DRIVING_EXPERIENCE);
        assertThat(testVehicleInsurance.getDriver_record()).isEqualTo(DEFAULT_DRIVER_RECORD);
        assertThat(testVehicleInsurance.getClaims()).isEqualTo(DEFAULT_CLAIMS);
        assertThat(testVehicleInsurance.getCar_value()).isEqualByComparingTo(DEFAULT_CAR_VALUE);
        assertThat(testVehicleInsurance.getAnnual_mileage()).isEqualByComparingTo(DEFAULT_ANNUAL_MILEAGE);
        assertThat(testVehicleInsurance.getInsurance_history()).isEqualTo(DEFAULT_INSURANCE_HISTORY);
    }

    @Test
    @Transactional
    void createVehicleInsuranceWithExistingId() throws Exception {
        // Create the VehicleInsurance with an existing ID
        vehicleInsurance.setId(1L);

        int databaseSizeBeforeCreate = vehicleInsuranceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setName(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setAge(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDriving_experienceIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setDriving_experience(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDriver_recordIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setDriver_record(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClaimsIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setClaims(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCar_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setCar_value(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnnual_mileageIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setAnnual_mileage(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInsurance_historyIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInsuranceRepository.findAll().size();
        // set the field null
        vehicleInsurance.setInsurance_history(null);

        // Create the VehicleInsurance, which fails.

        restVehicleInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleInsurances() throws Exception {
        // Initialize the database
        vehicleInsuranceRepository.saveAndFlush(vehicleInsurance);

        // Get all the vehicleInsuranceList
        restVehicleInsuranceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleInsurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].driving_experience").value(hasItem(DEFAULT_DRIVING_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].driver_record").value(hasItem(DEFAULT_DRIVER_RECORD)))
            .andExpect(jsonPath("$.[*].claims").value(hasItem(DEFAULT_CLAIMS)))
            .andExpect(jsonPath("$.[*].car_value").value(hasItem(sameNumber(DEFAULT_CAR_VALUE))))
            .andExpect(jsonPath("$.[*].annual_mileage").value(hasItem(sameNumber(DEFAULT_ANNUAL_MILEAGE))))
            .andExpect(jsonPath("$.[*].insurance_history").value(hasItem(DEFAULT_INSURANCE_HISTORY)));
    }

    @Test
    @Transactional
    void getVehicleInsurance() throws Exception {
        // Initialize the database
        vehicleInsuranceRepository.saveAndFlush(vehicleInsurance);

        // Get the vehicleInsurance
        restVehicleInsuranceMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleInsurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleInsurance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.driving_experience").value(DEFAULT_DRIVING_EXPERIENCE))
            .andExpect(jsonPath("$.driver_record").value(DEFAULT_DRIVER_RECORD))
            .andExpect(jsonPath("$.claims").value(DEFAULT_CLAIMS))
            .andExpect(jsonPath("$.car_value").value(sameNumber(DEFAULT_CAR_VALUE)))
            .andExpect(jsonPath("$.annual_mileage").value(sameNumber(DEFAULT_ANNUAL_MILEAGE)))
            .andExpect(jsonPath("$.insurance_history").value(DEFAULT_INSURANCE_HISTORY));
    }

    @Test
    @Transactional
    void getNonExistingVehicleInsurance() throws Exception {
        // Get the vehicleInsurance
        restVehicleInsuranceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVehicleInsurance() throws Exception {
        // Initialize the database
        vehicleInsuranceRepository.saveAndFlush(vehicleInsurance);

        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();

        // Update the vehicleInsurance
        VehicleInsurance updatedVehicleInsurance = vehicleInsuranceRepository.findById(vehicleInsurance.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVehicleInsurance are not directly saved in db
        em.detach(updatedVehicleInsurance);
        updatedVehicleInsurance
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .driving_experience(UPDATED_DRIVING_EXPERIENCE)
            .driver_record(UPDATED_DRIVER_RECORD)
            .claims(UPDATED_CLAIMS)
            .car_value(UPDATED_CAR_VALUE)
            .annual_mileage(UPDATED_ANNUAL_MILEAGE)
            .insurance_history(UPDATED_INSURANCE_HISTORY);

        restVehicleInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVehicleInsurance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVehicleInsurance))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
        VehicleInsurance testVehicleInsurance = vehicleInsuranceList.get(vehicleInsuranceList.size() - 1);
        assertThat(testVehicleInsurance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleInsurance.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testVehicleInsurance.getDriving_experience()).isEqualTo(UPDATED_DRIVING_EXPERIENCE);
        assertThat(testVehicleInsurance.getDriver_record()).isEqualTo(UPDATED_DRIVER_RECORD);
        assertThat(testVehicleInsurance.getClaims()).isEqualTo(UPDATED_CLAIMS);
        assertThat(testVehicleInsurance.getCar_value()).isEqualByComparingTo(UPDATED_CAR_VALUE);
        assertThat(testVehicleInsurance.getAnnual_mileage()).isEqualByComparingTo(UPDATED_ANNUAL_MILEAGE);
        assertThat(testVehicleInsurance.getInsurance_history()).isEqualTo(UPDATED_INSURANCE_HISTORY);
    }

    @Test
    @Transactional
    void putNonExistingVehicleInsurance() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();
        vehicleInsurance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleInsurance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleInsurance() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();
        vehicleInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleInsurance() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();
        vehicleInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleInsuranceWithPatch() throws Exception {
        // Initialize the database
        vehicleInsuranceRepository.saveAndFlush(vehicleInsurance);

        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();

        // Update the vehicleInsurance using partial update
        VehicleInsurance partialUpdatedVehicleInsurance = new VehicleInsurance();
        partialUpdatedVehicleInsurance.setId(vehicleInsurance.getId());

        partialUpdatedVehicleInsurance.age(UPDATED_AGE).driver_record(UPDATED_DRIVER_RECORD).claims(UPDATED_CLAIMS);

        restVehicleInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleInsurance))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
        VehicleInsurance testVehicleInsurance = vehicleInsuranceList.get(vehicleInsuranceList.size() - 1);
        assertThat(testVehicleInsurance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleInsurance.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testVehicleInsurance.getDriving_experience()).isEqualTo(DEFAULT_DRIVING_EXPERIENCE);
        assertThat(testVehicleInsurance.getDriver_record()).isEqualTo(UPDATED_DRIVER_RECORD);
        assertThat(testVehicleInsurance.getClaims()).isEqualTo(UPDATED_CLAIMS);
        assertThat(testVehicleInsurance.getCar_value()).isEqualByComparingTo(DEFAULT_CAR_VALUE);
        assertThat(testVehicleInsurance.getAnnual_mileage()).isEqualByComparingTo(DEFAULT_ANNUAL_MILEAGE);
        assertThat(testVehicleInsurance.getInsurance_history()).isEqualTo(DEFAULT_INSURANCE_HISTORY);
    }

    @Test
    @Transactional
    void fullUpdateVehicleInsuranceWithPatch() throws Exception {
        // Initialize the database
        vehicleInsuranceRepository.saveAndFlush(vehicleInsurance);

        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();

        // Update the vehicleInsurance using partial update
        VehicleInsurance partialUpdatedVehicleInsurance = new VehicleInsurance();
        partialUpdatedVehicleInsurance.setId(vehicleInsurance.getId());

        partialUpdatedVehicleInsurance
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .driving_experience(UPDATED_DRIVING_EXPERIENCE)
            .driver_record(UPDATED_DRIVER_RECORD)
            .claims(UPDATED_CLAIMS)
            .car_value(UPDATED_CAR_VALUE)
            .annual_mileage(UPDATED_ANNUAL_MILEAGE)
            .insurance_history(UPDATED_INSURANCE_HISTORY);

        restVehicleInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleInsurance))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
        VehicleInsurance testVehicleInsurance = vehicleInsuranceList.get(vehicleInsuranceList.size() - 1);
        assertThat(testVehicleInsurance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleInsurance.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testVehicleInsurance.getDriving_experience()).isEqualTo(UPDATED_DRIVING_EXPERIENCE);
        assertThat(testVehicleInsurance.getDriver_record()).isEqualTo(UPDATED_DRIVER_RECORD);
        assertThat(testVehicleInsurance.getClaims()).isEqualTo(UPDATED_CLAIMS);
        assertThat(testVehicleInsurance.getCar_value()).isEqualByComparingTo(UPDATED_CAR_VALUE);
        assertThat(testVehicleInsurance.getAnnual_mileage()).isEqualByComparingTo(UPDATED_ANNUAL_MILEAGE);
        assertThat(testVehicleInsurance.getInsurance_history()).isEqualTo(UPDATED_INSURANCE_HISTORY);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleInsurance() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();
        vehicleInsurance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleInsurance() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();
        vehicleInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleInsurance() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInsuranceRepository.findAll().size();
        vehicleInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInsurance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleInsurance in the database
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleInsurance() throws Exception {
        // Initialize the database
        vehicleInsuranceRepository.saveAndFlush(vehicleInsurance);

        int databaseSizeBeforeDelete = vehicleInsuranceRepository.findAll().size();

        // Delete the vehicleInsurance
        restVehicleInsuranceMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleInsurance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleInsurance> vehicleInsuranceList = vehicleInsuranceRepository.findAll();
        assertThat(vehicleInsuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
