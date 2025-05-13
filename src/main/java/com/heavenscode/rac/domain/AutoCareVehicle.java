package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A AutoCareVehicle.
 */
@Entity
@Table(name = "autocarevehicle")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutoCareVehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "customerid")
    private Integer customerId;

    @Column(name = "customername")
    private String customerName;

    @Column(name = "customertel")
    private String customerTel;

    @Column(name = "vehiclenumber")
    private String vehicleNumber;

    @Column(name = "brandid")
    private Integer brandId;

    @Column(name = "brandname")
    private String brandName;

    @Column(name = "model")
    private String model;

    @Column(name = "millage")
    private Double millage;

    @Column(name = "manufactureyear")
    private String manufactureYear;

    @Column(name = "lastservicedate")
    private Instant lastServiceDate;

    @Column(name = "nextservicedate")
    private Instant nextServiceDate;

    @Column(name = "description")
    private String description;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AutoCareVehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public AutoCareVehicle customerId(Integer customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public AutoCareVehicle customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return this.customerTel;
    }

    public AutoCareVehicle customerTel(String customerTel) {
        this.setCustomerTel(customerTel);
        return this;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getVehicleNumber() {
        return this.vehicleNumber;
    }

    public AutoCareVehicle vehicleNumber(String vehicleNumber) {
        this.setVehicleNumber(vehicleNumber);
        return this;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Integer getBrandId() {
        return this.brandId;
    }

    public AutoCareVehicle brandId(Integer brandId) {
        this.setBrandId(brandId);
        return this;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public AutoCareVehicle brandName(String brandName) {
        this.setBrandName(brandName);
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModel() {
        return this.model;
    }

    public AutoCareVehicle model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getMillage() {
        return this.millage;
    }

    public AutoCareVehicle millage(Double millage) {
        this.setMillage(millage);
        return this;
    }

    public void setMillage(Double millage) {
        this.millage = millage;
    }

    public String getManufactureYear() {
        return this.manufactureYear;
    }

    public AutoCareVehicle manufactureYear(String manufactureYear) {
        this.setManufactureYear(manufactureYear);
        return this;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Instant getLastServiceDate() {
        return this.lastServiceDate;
    }

    public AutoCareVehicle lastServiceDate(Instant lastServiceDate) {
        this.setLastServiceDate(lastServiceDate);
        return this;
    }

    public void setLastServiceDate(Instant lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public Instant getNextServiceDate() {
        return this.nextServiceDate;
    }

    public AutoCareVehicle nextServiceDate(Instant nextServiceDate) {
        this.setNextServiceDate(nextServiceDate);
        return this;
    }

    public void setNextServiceDate(Instant nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }

    public String getDescription() {
        return this.description;
    }

    public AutoCareVehicle description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public AutoCareVehicle lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public AutoCareVehicle lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutoCareVehicle)) {
            return false;
        }
        return getId() != null && getId().equals(((AutoCareVehicle) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutoCareVehicle{" +
            "id=" + getId() +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerTel='" + getCustomerTel() + "'" +
            ", vehicleNumber='" + getVehicleNumber() + "'" +
            ", brandId=" + getBrandId() +
            ", brandName='" + getBrandName() + "'" +
            ", model='" + getModel() + "'" +
            ", millage=" + getMillage() +
            ", manufactureYear='" + getManufactureYear() + "'" +
            ", lastServiceDate='" + getLastServiceDate() + "'" +
            ", nextServiceDate='" + getNextServiceDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            "}";
    }
}
