package com.connex.insuranceapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleInsurance.
 */
@Entity
@Table(name = "vehicle_insurance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleInsurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "driving_experience", nullable = false)
    private Integer driving_experience;

    @NotNull
    @Column(name = "driver_record", nullable = false)
    private Integer driver_record;

    @NotNull
    @Column(name = "claims", nullable = false)
    private Integer claims;

    @Column(name = "category")
    private String category;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Integer year;

    @NotNull
    @Column(name = "car_value", precision = 21, scale = 2, nullable = false)
    private BigDecimal car_value;

    @NotNull
    @Column(name = "annual_mileage", precision = 21, scale = 2, nullable = false)
    private BigDecimal annual_mileage;

    @NotNull
    @Column(name = "insurance_history", nullable = false)
    private Integer insurance_history;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VehicleInsurance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public VehicleInsurance name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public VehicleInsurance birthdate(LocalDate birthdate) {
        this.setBirthdate(birthdate);
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getAge() {
        return this.age;
    }

    public VehicleInsurance age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDriving_experience() {
        return this.driving_experience;
    }

    public VehicleInsurance driving_experience(Integer driving_experience) {
        this.setDriving_experience(driving_experience);
        return this;
    }

    public void setDriving_experience(Integer driving_experience) {
        this.driving_experience = driving_experience;
    }

    public Integer getDriver_record() {
        return this.driver_record;
    }

    public VehicleInsurance driver_record(Integer driver_record) {
        this.setDriver_record(driver_record);
        return this;
    }

    public void setDriver_record(Integer driver_record) {
        this.driver_record = driver_record;
    }

    public Integer getClaims() {
        return this.claims;
    }

    public VehicleInsurance claims(Integer claims) {
        this.setClaims(claims);
        return this;
    }

    public void setClaims(Integer claims) {
        this.claims = claims;
    }

    public String getCategory() {
        return this.category;
    }

    public VehicleInsurance category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMake() {
        return this.make;
    }

    public VehicleInsurance make(String make) {
        this.setMake(make);
        return this;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public VehicleInsurance model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return this.year;
    }

    public VehicleInsurance year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getCar_value() {
        return this.car_value;
    }

    public VehicleInsurance car_value(BigDecimal car_value) {
        this.setCar_value(car_value);
        return this;
    }

    public void setCar_value(BigDecimal car_value) {
        this.car_value = car_value;
    }

    public BigDecimal getAnnual_mileage() {
        return this.annual_mileage;
    }

    public VehicleInsurance annual_mileage(BigDecimal annual_mileage) {
        this.setAnnual_mileage(annual_mileage);
        return this;
    }

    public void setAnnual_mileage(BigDecimal annual_mileage) {
        this.annual_mileage = annual_mileage;
    }

    public Integer getInsurance_history() {
        return this.insurance_history;
    }

    public VehicleInsurance insurance_history(Integer insurance_history) {
        this.setInsurance_history(insurance_history);
        return this;
    }

    public void setInsurance_history(Integer insurance_history) {
        this.insurance_history = insurance_history;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleInsurance)) {
            return false;
        }
        return id != null && id.equals(((VehicleInsurance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleInsurance{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", age=" + getAge() +
            ", driving_experience=" + getDriving_experience() +
            ", driver_record=" + getDriver_record() +
            ", claims=" + getClaims() +
            ", category='" + getCategory() + "'" +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            ", year=" + getYear() +
            ", car_value=" + getCar_value() +
            ", annual_mileage=" + getAnnual_mileage() +
            ", insurance_history=" + getInsurance_history() +
            "}";
    }
}
