package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A EmpFunctions.
 */
@Entity
@Table(name = "Emp_Functions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpFunctions implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "functionid", nullable = false)
    private Integer functionId;

    @Column(name = "functionname")
    private String functionName;

    @Column(name = "moduleid")
    private Integer moduleId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getFunctionId() {
        return this.functionId;
    }

    public EmpFunctions functionId(Integer functionId) {
        this.setFunctionId(functionId);
        return this;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public EmpFunctions functionName(String functionName) {
        this.setFunctionName(functionName);
        return this;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Integer getModuleId() {
        return this.moduleId;
    }

    public EmpFunctions moduleId(Integer moduleId) {
        this.setModuleId(moduleId);
        return this;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpFunctions)) {
            return false;
        }
        return getFunctionId() != null && getFunctionId().equals(((EmpFunctions) o).getFunctionId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpFunctions{" +
            "functionId=" + getFunctionId() +
            ", functionName='" + getFunctionName() + "'" +
            ", moduleId=" + getModuleId() +
            "}";
    }
}
