package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.EmpRoles} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.EmpRolesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emp-roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpRolesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private IntegerFilter roleId;

    private StringFilter roleName;

    private Boolean distinct;

    public EmpRolesCriteria() {}

    public EmpRolesCriteria(EmpRolesCriteria other) {
        this.roleId = other.optionalRoleId().map(IntegerFilter::copy).orElse(null);
        this.roleName = other.optionalRoleName().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmpRolesCriteria copy() {
        return new EmpRolesCriteria(this);
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

    public StringFilter getRoleName() {
        return roleName;
    }

    public Optional<StringFilter> optionalRoleName() {
        return Optional.ofNullable(roleName);
    }

    public StringFilter roleName() {
        if (roleName == null) {
            setRoleName(new StringFilter());
        }
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
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
        final EmpRolesCriteria that = (EmpRolesCriteria) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(roleName, that.roleName) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpRolesCriteria{" +
            optionalRoleId().map(f -> "roleId=" + f + ", ").orElse("") +
            optionalRoleName().map(f -> "roleName=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
