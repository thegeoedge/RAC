package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A EmpRoleFunctionPermission.
 */
@Entity
@Table(name = "emp_rolefunctionpermission")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpRoleFunctionPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "functionid")
    private Integer functionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public EmpRoleFunctionPermission id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public EmpRoleFunctionPermission roleId(Integer roleId) {
        this.setRoleId(roleId);
        return this;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFunctionId() {
        return this.functionId;
    }

    public EmpRoleFunctionPermission functionId(Integer functionId) {
        this.setFunctionId(functionId);
        return this;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpRoleFunctionPermission)) {
            return false;
        }
        return getId() != null && getId().equals(((EmpRoleFunctionPermission) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpRoleFunctionPermission{" +
            "id=" + getId() +
            ", roleId=" + getRoleId() +
            ", functionId=" + getFunctionId() +
            "}";
    }
}
