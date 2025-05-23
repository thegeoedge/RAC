package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Billingserviceoptionvalues.
 */
@Entity
@Table(name = "billingserviceoptionvalues")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Billingserviceoptionvalues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "vehicletypeid", nullable = false)
    private Integer vehicletypeid;

    @Column(name = "billingserviceoptionid")
    private Integer billingserviceoptionid;

    @Column(name = "value")
    private Float value;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "lmu")
    private Integer lmu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getVehicletypeid() {
        return this.vehicletypeid;
    }

    public Billingserviceoptionvalues vehicletypeid(Integer vehicletypeid) {
        this.setVehicletypeid(vehicletypeid);
        return this;
    }

    public void setVehicletypeid(Integer vehicletypeid) {
        this.vehicletypeid = vehicletypeid;
    }

    public Integer getBillingserviceoptionid() {
        return this.billingserviceoptionid;
    }

    public Billingserviceoptionvalues billingserviceoptionid(Integer billingserviceoptionid) {
        this.setBillingserviceoptionid(billingserviceoptionid);
        return this;
    }

    public void setBillingserviceoptionid(Integer billingserviceoptionid) {
        this.billingserviceoptionid = billingserviceoptionid;
    }

    public Float getValue() {
        return this.value;
    }

    public Billingserviceoptionvalues value(Float value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public Billingserviceoptionvalues lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public Billingserviceoptionvalues lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Billingserviceoptionvalues)) {
            return false;
        }
        return getVehicletypeid() != null && getVehicletypeid().equals(((Billingserviceoptionvalues) o).getVehicletypeid());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Billingserviceoptionvalues{" +
            
            "vehicletypeid=" + getVehicletypeid() +
            ", billingserviceoptionid=" + getBillingserviceoptionid() +
            ", value=" + getValue() +
            ", lmd='" + getLmd() + "'" +
            ", lmu=" + getLmu() +
            "}";
    }
}
