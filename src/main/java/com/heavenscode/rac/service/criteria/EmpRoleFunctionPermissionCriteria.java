package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.EmpRoleFunctionPermission} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.EmpRoleFunctionPermissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emp-role-function-permissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpRoleFunctionPermissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private IntegerFilter id;

    private IntegerFilter roleId;

    private IntegerFilter functionId;

    private Boolean distinct;

    public EmpRoleFunctionPermissionCriteria() {}

    public EmpRoleFunctionPermissionCriteria(EmpRoleFunctionPermissionCriteria other) {
        this.id = other.optionalId().map(IntegerFilter::copy).orElse(null);
        this.roleId = other.optionalRoleId().map(IntegerFilter::copy).orElse(null);
        this.functionId = other.optionalFunctionId().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmpRoleFunctionPermissionCriteria copy() {
        return new EmpRoleFunctionPermissionCriteria(this);
    }

    public IntegerFilter getId() {
        return id;
    }

    public Optional<IntegerFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public IntegerFilter id() {
        if (id == null) {
            setId(new IntegerFilter());
        }
        return id;
    }

    public void setId(IntegerFilter id) {
        this.id = id;
    }

    public IntegerFilter getRoleId() {
        return roleId;
    }

    public Optional<IntegerFilter> optionalRoleId() {
        return Optional.ofNullable(roleId);
    }

    public IntegerFilter roleId() {
        if (roleId == null) {
            setRoleId(new IntegerFilter());
        }
        return roleId;
    }

    public void setRoleId(IntegerFilter roleId) {
        this.roleId = roleId;
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
        final EmpRoleFunctionPermissionCriteria that = (EmpRoleFunctionPermissionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(functionId, that.functionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, functionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpRoleFunctionPermissionCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRoleId().map(f -> "roleId=" + f + ", ").orElse("") +
            optionalFunctionId().map(f -> "functionId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
