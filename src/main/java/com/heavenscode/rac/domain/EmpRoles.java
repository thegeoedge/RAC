package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A EmpRoles.
 */
@Entity
@Table(name = "emp_roles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "roleid", nullable = false)
    private Integer roleId;

    @Column(name = "rolename")
    private String roleName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getRoleId() {
        return this.roleId;
    }

    public EmpRoles roleId(Integer roleId) {
        this.setRoleId(roleId);
        return this;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public EmpRoles roleName(String roleName) {
        this.setRoleName(roleName);
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpRoles)) {
            return false;
        }
        return getRoleId() != null && getRoleId().equals(((EmpRoles) o).getRoleId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpRoles{" +
            "roleId=" + getRoleId() +
            ", roleName='" + getRoleName() + "'" +
            "}";
    }
}
