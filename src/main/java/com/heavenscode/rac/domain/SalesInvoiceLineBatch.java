package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A SalesInvoiceLineBatch.
 */
@Entity
@Table(name = "salesinvoicelinebatches")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceLineBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE depending on your DB
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "lineid")
    private Long lineId;

    @Column(name = "batchlineid")
    private Long batchLineId;

    @Column(name = "itemid")
    private Long itemId;

    @Column(name = "code")
    private String code;

    @Column(name = "batchid")
    private Long batchId;

    @Column(name = "batchcode")
    private String batchCode;

    @Column(name = "txdate")
    private Instant txDate;

    @Column(name = "manufacturedate")
    private Instant manufactureDate;

    @Column(name = "expireddate")
    private Instant expiredDate;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "cost", precision = 21, scale = 2)
    private BigDecimal cost;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "notes")
    private String notes;

    @Column(name = "lmu")
    private Long lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "nbt")
    private Boolean nbt;

    @Column(name = "vat")
    private Boolean vat;

    @Column(name = "addedbyid")
    private Long addedById;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesInvoiceLineBatch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineId() {
        return this.lineId;
    }

    public SalesInvoiceLineBatch lineId(Long lineId) {
        this.setLineId(lineId);
        return this;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getBatchLineId() {
        return this.batchLineId;
    }

    public SalesInvoiceLineBatch batchLineId(Long batchLineId) {
        this.setBatchLineId(batchLineId);
        return this;
    }

    public void setBatchLineId(Long batchLineId) {
        this.batchLineId = batchLineId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public SalesInvoiceLineBatch itemId(Long itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getCode() {
        return this.code;
    }

    public SalesInvoiceLineBatch code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getBatchId() {
        return this.batchId;
    }

    public SalesInvoiceLineBatch batchId(Long batchId) {
        this.setBatchId(batchId);
        return this;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchCode() {
        return this.batchCode;
    }

    public SalesInvoiceLineBatch batchCode(String batchCode) {
        this.setBatchCode(batchCode);
        return this;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Instant getTxDate() {
        return this.txDate;
    }

    public SalesInvoiceLineBatch txDate(Instant txDate) {
        this.setTxDate(txDate);
        return this;
    }

    public void setTxDate(Instant txDate) {
        this.txDate = txDate;
    }

    public Instant getManufactureDate() {
        return this.manufactureDate;
    }

    public SalesInvoiceLineBatch manufactureDate(Instant manufactureDate) {
        this.setManufactureDate(manufactureDate);
        return this;
    }

    public void setManufactureDate(Instant manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Instant getExpiredDate() {
        return this.expiredDate;
    }

    public SalesInvoiceLineBatch expiredDate(Instant expiredDate) {
        this.setExpiredDate(expiredDate);
        return this;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Double getQty() {
        return this.qty;
    }

    public SalesInvoiceLineBatch qty(Double qty) {
        this.setQty(qty);
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public SalesInvoiceLineBatch cost(BigDecimal cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public SalesInvoiceLineBatch price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNotes() {
        return this.notes;
    }

    public SalesInvoiceLineBatch notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getLmu() {
        return this.lmu;
    }

    public SalesInvoiceLineBatch lmu(Long lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Long lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public SalesInvoiceLineBatch lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Boolean getNbt() {
        return this.nbt;
    }

    public SalesInvoiceLineBatch nbt(Boolean nbt) {
        this.setNbt(nbt);
        return this;
    }

    public void setNbt(Boolean nbt) {
        this.nbt = nbt;
    }

    public Boolean getVat() {
        return this.vat;
    }

    public SalesInvoiceLineBatch vat(Boolean vat) {
        this.setVat(vat);
        return this;
    }

    public void setVat(Boolean vat) {
        this.vat = vat;
    }

    public Long getAddedById() {
        return this.addedById;
    }

    public SalesInvoiceLineBatch addedById(Long addedById) {
        this.setAddedById(addedById);
        return this;
    }

    public void setAddedById(Long addedById) {
        this.addedById = addedById;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesInvoiceLineBatch)) {
            return false;
        }
        return getId() != null && getId().equals(((SalesInvoiceLineBatch) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceLineBatch{" +
            "id=" + getId() +
            ", lineId=" + getLineId() +
            ", batchLineId=" + getBatchLineId() +
            ", itemId=" + getItemId() +
            ", code='" + getCode() + "'" +
            ", batchId=" + getBatchId() +
            ", batchCode='" + getBatchCode() + "'" +
            ", txDate='" + getTxDate() + "'" +
            ", manufactureDate='" + getManufactureDate() + "'" +
            ", expiredDate='" + getExpiredDate() + "'" +
            ", qty=" + getQty() +
            ", cost=" + getCost() +
            ", price=" + getPrice() +
            ", notes='" + getNotes() + "'" +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            ", nbt='" + getNbt() + "'" +
            ", vat='" + getVat() + "'" +
            ", addedById=" + getAddedById() +
            "}";
    }
}
