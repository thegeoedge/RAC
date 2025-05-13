package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.EmpFunctions} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.EmpFunctionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emp-functions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpFunctionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private IntegerFilter functionId;

    private StringFilter functionName;

    private IntegerFilter moduleId;

    private Boolean distinct;

    public EmpFunctionsCriteria() {}

    public EmpFunctionsCriteria(EmpFunctionsCriteria other) {
        this.functionId = other.optionalFunctionId().map(IntegerFilter::copy).orElse(null);
        this.functionName = other.optionalFunctionName().map(StringFilter::copy).orElse(null);
        this.moduleId = other.optionalModuleId().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmpFunctionsCriteria copy() {
        return new EmpFunctionsCriteria(this);
    }

    public IntegerFilter getFunctionId() {
        return functionId;
    }

    public Optional<IntegerFilter> optionalFunctionId() {
        return Optional.ofNullable(functionId);
    }

    public IntegerFilter functionId() {
        if (functionId == null) {
            setFunctionId(new IntegerFilter());
        }
        return functionId;
    }

    public void setFunctionId(IntegerFilter functionId) {
        this.functionId = functionId;
    }

    public StringFilter getFunctionName() {
        return functionName;
    }

    public Optional<StringFilter> optionalFunctionName() {
        return Optional.ofNullable(functionName);
    }

    public StringFilter functionName() {
        if (functionName == null) {
            setFunctionName(new StringFilter());
        }
        return functionName;
    }

    public void setFunctionName(StringFilter functionName) {
        this.functionName = functionName;
    }

    public IntegerFilter getModuleId() {
        return moduleId;
    }

    public Optional<IntegerFilter> optionalModuleId() {
        return Optional.ofNullable(moduleId);
    }

    public IntegerFilter moduleId() {
        if (moduleId == null) {
            setModuleId(new IntegerFilter());
        }
        return moduleId;
    }

    public void setModuleId(IntegerFilter moduleId) {
        this.moduleId = moduleId;
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
        final EmpFunctionsCriteria that = (EmpFunctionsCriteria) o;
        return (
            Objects.equals(functionId, that.functionId) &&
            Objects.equals(functionName, that.functionName) &&
            Objects.equals(moduleId, that.moduleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionId, functionName, moduleId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpFunctionsCriteria{" +
            optionalFunctionId().map(f -> "functionId=" + f + ", ").orElse("") +
            optionalFunctionName().map(f -> "functionName=" + f + ", ").orElse("") +
            optionalModuleId().map(f -> "moduleId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
