package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Transactions.
 */
@Entity
@Table(name = "transactions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "accountid")
    private Integer accountId;

    @Column(name = "accountcode")
    private String accountCode;

    @Column(name = "debit")
    private Double debit;

    @Column(name = "credit")
    private Double credit;

    @Column(name = "date")
    private Instant date;

    @Column(name = "refdoc")
    private String refDoc;

    @Column(name = "refid")
    private Integer refId;

    @Lob
    @Column(name = "subid")
    private String subId;

    @Column(name = "source")
    private String source;

    @Column(name = "paymenttermid")
    private Integer paymentTermId;

    @Column(name = "paymenttermname")
    private String paymentTermName;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transactions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public Transactions accountId(Integer accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return this.accountCode;
    }

    public Transactions accountCode(String accountCode) {
        this.setAccountCode(accountCode);
        return this;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Double getDebit() {
        return this.debit;
    }

    public Transactions debit(Double debit) {
        this.setDebit(debit);
        return this;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public Transactions credit(Double credit) {
        this.setCredit(credit);
        return this;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Instant getDate() {
        return this.date;
    }

    public Transactions date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getRefDoc() {
        return this.refDoc;
    }

    public Transactions refDoc(String refDoc) {
        this.setRefDoc(refDoc);
        return this;
    }

    public void setRefDoc(String refDoc) {
        this.refDoc = refDoc;
    }

    public Integer getRefId() {
        return this.refId;
    }

    public Transactions refId(Integer refId) {
        this.setRefId(refId);
        return this;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public String getSubId() {
        return this.subId;
    }

    public Transactions subId(String subId) {
        this.setSubId(subId);
        return this;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSource() {
        return this.source;
    }

    public Transactions source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPaymentTermId() {
        return this.paymentTermId;
    }

    public Transactions paymentTermId(Integer paymentTermId) {
        this.setPaymentTermId(paymentTermId);
        return this;
    }

    public void setPaymentTermId(Integer paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public String getPaymentTermName() {
        return this.paymentTermName;
    }

    public Transactions paymentTermName(String paymentTermName) {
        this.setPaymentTermName(paymentTermName);
        return this;
    }

    public void setPaymentTermName(String paymentTermName) {
        this.paymentTermName = paymentTermName;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public Transactions lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public Transactions lmd(Instant lmd) {
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
        if (!(o instanceof Transactions)) {
            return false;
        }
        return getId() != null && getId().equals(((Transactions) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transactions{" +
            "id=" + getId() +
            ", accountId=" + getAccountId() +
            ", accountCode='" + getAccountCode() + "'" +
            ", debit=" + getDebit() +
            ", credit=" + getCredit() +
            ", date='" + getDate() + "'" +
            ", refDoc='" + getRefDoc() + "'" +
            ", refId=" + getRefId() +
            ", subId='" + getSubId() + "'" +
            ", source='" + getSource() + "'" +
            ", paymentTermId=" + getPaymentTermId() +
            ", paymentTermName='" + getPaymentTermName() + "'" +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            "}";
    }
}
