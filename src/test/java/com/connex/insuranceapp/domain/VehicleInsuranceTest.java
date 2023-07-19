package com.connex.insuranceapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.connex.insuranceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleInsuranceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleInsurance.class);
        VehicleInsurance vehicleInsurance1 = new VehicleInsurance();
        vehicleInsurance1.setId(1L);
        VehicleInsurance vehicleInsurance2 = new VehicleInsurance();
        vehicleInsurance2.setId(vehicleInsurance1.getId());
        assertThat(vehicleInsurance1).isEqualTo(vehicleInsurance2);
        vehicleInsurance2.setId(2L);
        assertThat(vehicleInsurance1).isNotEqualTo(vehicleInsurance2);
        vehicleInsurance1.setId(null);
        assertThat(vehicleInsurance1).isNotEqualTo(vehicleInsurance2);
    }
}
