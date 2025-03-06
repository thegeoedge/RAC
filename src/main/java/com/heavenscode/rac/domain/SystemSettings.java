package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A SystemSettings.
 */
@Entity
@Table(name = "systemsettings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SystemSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE depending on your DB
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "[key]")
    private String key;

    @Column(name = "lastvalue")
    private String lastValue;

    @Column(name = "nextvalue")
    private String nextValue;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SystemSettings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public SystemSettings key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastValue() {
        return this.lastValue;
    }

    public SystemSettings lastValue(String lastValue) {
        this.setLastValue(lastValue);
        return this;
    }

    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }

    public String getNextValue() {
        return this.nextValue;
    }

    public SystemSettings nextValue(String nextValue) {
        this.setNextValue(nextValue);
        return this;
    }

    public void setNextValue(String nextValue) {
        this.nextValue = nextValue;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public SystemSettings lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public SystemSettings lmd(Instant lmd) {
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
        if (!(o instanceof SystemSettings)) {
            return false;
        }
        return getId() != null && getId().equals(((SystemSettings) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemSettings{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", lastValue='" + getLastValue() + "'" +
            ", nextValue='" + getNextValue() + "'" +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            "}";
    }
}
