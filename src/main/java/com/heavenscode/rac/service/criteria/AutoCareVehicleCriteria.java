package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.AutoCareVehicle} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.AutoCareVehicleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /auto-care-vehicles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutoCareVehicleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter customerId;

    private StringFilter customerName;

    private StringFilter customerTel;

    private StringFilter vehicleNumber;

    private IntegerFilter brandId;

    private StringFilter brandName;

    private StringFilter model;

    private DoubleFilter millage;

    private StringFilter manufactureYear;

    private InstantFilter lastServiceDate;

    private InstantFilter nextServiceDate;

    private StringFilter description;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private Boolean distinct;

    public AutoCareVehicleCriteria() {}

    public AutoCareVehicleCriteria(AutoCareVehicleCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.customerId = other.optionalCustomerId().map(IntegerFilter::copy).orElse(null);
        this.customerName = other.optionalCustomerName().map(StringFilter::copy).orElse(null);
        this.customerTel = other.optionalCustomerTel().map(StringFilter::copy).orElse(null);
        this.vehicleNumber = other.optionalVehicleNumber().map(StringFilter::copy).orElse(null);
        this.brandId = other.optionalBrandId().map(IntegerFilter::copy).orElse(null);
        this.brandName = other.optionalBrandName().map(StringFilter::copy).orElse(null);
        this.model = other.optionalModel().map(StringFilter::copy).orElse(null);
        this.millage = other.optionalMillage().map(DoubleFilter::copy).orElse(null);
        this.manufactureYear = other.optionalManufactureYear().map(StringFilter::copy).orElse(null);
        this.lastServiceDate = other.optionalLastServiceDate().map(InstantFilter::copy).orElse(null);
        this.nextServiceDate = other.optionalNextServiceDate().map(InstantFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AutoCareVehicleCriteria copy() {
        return new AutoCareVehicleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getCustomerId() {
        return customerId;
    }

    public Optional<IntegerFilter> optionalCustomerId() {
        return Optional.ofNullable(customerId);
    }

    public IntegerFilter customerId() {
        if (customerId == null) {
            setCustomerId(new IntegerFilter());
        }
        return customerId;
    }

    public void setCustomerId(IntegerFilter customerId) {
        this.customerId = customerId;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public Optional<StringFilter> optionalCustomerName() {
        return Optional.ofNullable(customerName);
    }

    public StringFilter customerName() {
        if (customerName == null) {
            setCustomerName(new StringFilter());
        }
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getCustomerTel() {
        return customerTel;
    }

    public Optional<StringFilter> optionalCustomerTel() {
        return Optional.ofNullable(customerTel);
    }

    public StringFilter customerTel() {
        if (customerTel == null) {
            setCustomerTel(new StringFilter());
        }
        return customerTel;
    }

    public void setCustomerTel(StringFilter customerTel) {
        this.customerTel = customerTel;
    }

    public StringFilter getVehicleNumber() {
        return vehicleNumber;
    }

    public Optional<StringFilter> optionalVehicleNumber() {
        return Optional.ofNullable(vehicleNumber);
    }

    public StringFilter vehicleNumber() {
        if (vehicleNumber == null) {
            setVehicleNumber(new StringFilter());
        }
        return vehicleNumber;
    }

    public void setVehicleNumber(StringFilter vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public IntegerFilter getBrandId() {
        return brandId;
    }

    public Optional<IntegerFilter> optionalBrandId() {
        return Optional.ofNullable(brandId);
    }

    public IntegerFilter brandId() {
        if (brandId == null) {
            setBrandId(new IntegerFilter());
        }
        return brandId;
    }

    public void setBrandId(IntegerFilter brandId) {
        this.brandId = brandId;
    }

    public StringFilter getBrandName() {
        return brandName;
    }

    public Optional<StringFilter> optionalBrandName() {
        return Optional.ofNullable(brandName);
    }

    public StringFilter brandName() {
        if (brandName == null) {
            setBrandName(new StringFilter());
        }
        return brandName;
    }

    public void setBrandName(StringFilter brandName) {
        this.brandName = brandName;
    }

    public StringFilter getModel() {
        return model;
    }

    public Optional<StringFilter> optionalModel() {
        return Optional.ofNullable(model);
    }

    public StringFilter model() {
        if (model == null) {
            setModel(new StringFilter());
        }
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public DoubleFilter getMillage() {
        return millage;
    }

    public Optional<DoubleFilter> optionalMillage() {
        return Optional.ofNullable(millage);
    }

    public DoubleFilter millage() {
        if (millage == null) {
            setMillage(new DoubleFilter());
        }
        return millage;
    }

    public void setMillage(DoubleFilter millage) {
        this.millage = millage;
    }

    public StringFilter getManufactureYear() {
        return manufactureYear;
    }

    public Optional<StringFilter> optionalManufactureYear() {
        return Optional.ofNullable(manufactureYear);
    }

    public StringFilter manufactureYear() {
        if (manufactureYear == null) {
            setManufactureYear(new StringFilter());
        }
        return manufactureYear;
    }

    public void setManufactureYear(StringFilter manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public InstantFilter getLastServiceDate() {
        return lastServiceDate;
    }

    public Optional<InstantFilter> optionalLastServiceDate() {
        return Optional.ofNullable(lastServiceDate);
    }

    public InstantFilter lastServiceDate() {
        if (lastServiceDate == null) {
            setLastServiceDate(new InstantFilter());
        }
        return lastServiceDate;
    }

    public void setLastServiceDate(InstantFilter lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public InstantFilter getNextServiceDate() {
        return nextServiceDate;
    }

    public Optional<InstantFilter> optionalNextServiceDate() {
        return Optional.ofNullable(nextServiceDate);
    }

    public InstantFilter nextServiceDate() {
        if (nextServiceDate == null) {
            setNextServiceDate(new InstantFilter());
        }
        return nextServiceDate;
    }

    public void setNextServiceDate(InstantFilter nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getLmu() {
        return lmu;
    }

    public Optional<IntegerFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public IntegerFilter lmu() {
        if (lmu == null) {
            setLmu(new IntegerFilter());
        }
        return lmu;
    }

    public void setLmu(IntegerFilter lmu) {
        this.lmu = lmu;
    }

    public InstantFilter getLmd() {
        return lmd;
    }

    public Optional<InstantFilter> optionalLmd() {
        return Optional.ofNullable(lmd);
    }

    public InstantFilter lmd() {
        if (lmd == null) {
            setLmd(new InstantFilter());
        }
        return lmd;
    }

    public void setLmd(InstantFilter lmd) {
        this.lmd = lmd;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AutoCareVehicleCriteria that = (AutoCareVehicleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(customerTel, that.customerTel) &&
            Objects.equals(vehicleNumber, that.vehicleNumber) &&
            Objects.equals(brandId, that.brandId) &&
            Objects.equals(brandName, that.brandName) &&
            Objects.equals(model, that.model) &&
            Objects.equals(millage, that.millage) &&
            Objects.equals(manufactureYear, that.manufactureYear) &&
            Objects.equals(lastServiceDate, that.lastServiceDate) &&
            Objects.equals(nextServiceDate, that.nextServiceDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            customerId,
            customerName,
            customerTel,
            vehicleNumber,
            brandId,
            brandName,
            model,
            millage,
            manufactureYear,
            lastServiceDate,
            nextServiceDate,
            description,
            lmu,
            lmd,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutoCareVehicleCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCustomerId().map(f -> "customerId=" + f + ", ").orElse("") +
            optionalCustomerName().map(f -> "customerName=" + f + ", ").orElse("") +
            optionalCustomerTel().map(f -> "customerTel=" + f + ", ").orElse("") +
            optionalVehicleNumber().map(f -> "vehicleNumber=" + f + ", ").orElse("") +
            optionalBrandId().map(f -> "brandId=" + f + ", ").orElse("") +
            optionalBrandName().map(f -> "brandName=" + f + ", ").orElse("") +
            optionalModel().map(f -> "model=" + f + ", ").orElse("") +
            optionalMillage().map(f -> "millage=" + f + ", ").orElse("") +
            optionalManufactureYear().map(f -> "manufactureYear=" + f + ", ").orElse("") +
            optionalLastServiceDate().map(f -> "lastServiceDate=" + f + ", ").orElse("") +
            optionalNextServiceDate().map(f -> "nextServiceDate=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
