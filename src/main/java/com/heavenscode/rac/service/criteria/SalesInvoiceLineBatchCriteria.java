package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.SalesInvoiceLineBatch} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.SalesInvoiceLineBatchResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sales-invoice-line-batches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceLineBatchCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter lineId;

    private LongFilter batchLineId;

    private LongFilter itemId;

    private StringFilter code;

    private LongFilter batchId;

    private StringFilter batchCode;

    private InstantFilter txDate;

    private InstantFilter manufactureDate;

    private InstantFilter expiredDate;

    private DoubleFilter qty;

    private BigDecimalFilter cost;

    private BigDecimalFilter price;

    private StringFilter notes;

    private LongFilter lmu;

    private InstantFilter lmd;

    private BooleanFilter nbt;

    private BooleanFilter vat;

    private LongFilter addedById;

    private Boolean distinct;

    public SalesInvoiceLineBatchCriteria() {}

    public SalesInvoiceLineBatchCriteria(SalesInvoiceLineBatchCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.lineId = other.optionalLineId().map(LongFilter::copy).orElse(null);
        this.batchLineId = other.optionalBatchLineId().map(LongFilter::copy).orElse(null);
        this.itemId = other.optionalItemId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.batchId = other.optionalBatchId().map(LongFilter::copy).orElse(null);
        this.batchCode = other.optionalBatchCode().map(StringFilter::copy).orElse(null);
        this.txDate = other.optionalTxDate().map(InstantFilter::copy).orElse(null);
        this.manufactureDate = other.optionalManufactureDate().map(InstantFilter::copy).orElse(null);
        this.expiredDate = other.optionalExpiredDate().map(InstantFilter::copy).orElse(null);
        this.qty = other.optionalQty().map(DoubleFilter::copy).orElse(null);
        this.cost = other.optionalCost().map(BigDecimalFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(BigDecimalFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(LongFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.nbt = other.optionalNbt().map(BooleanFilter::copy).orElse(null);
        this.vat = other.optionalVat().map(BooleanFilter::copy).orElse(null);
        this.addedById = other.optionalAddedById().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SalesInvoiceLineBatchCriteria copy() {
        return new SalesInvoiceLineBatchCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getLineId() {
        return lineId;
    }

    public Optional<LongFilter> optionalLineId() {
        return Optional.ofNullable(lineId);
    }

    public LongFilter lineId() {
        if (lineId == null) {
            setLineId(new LongFilter());
        }
        return lineId;
    }

    public void setLineId(LongFilter lineId) {
        this.lineId = lineId;
    }

    public LongFilter getBatchLineId() {
        return batchLineId;
    }

    public Optional<LongFilter> optionalBatchLineId() {
        return Optional.ofNullable(batchLineId);
    }

    public LongFilter batchLineId() {
        if (batchLineId == null) {
            setBatchLineId(new LongFilter());
        }
        return batchLineId;
    }

    public void setBatchLineId(LongFilter batchLineId) {
        this.batchLineId = batchLineId;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public Optional<LongFilter> optionalItemId() {
        return Optional.ofNullable(itemId);
    }

    public LongFilter itemId() {
        if (itemId == null) {
            setItemId(new LongFilter());
        }
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getBatchId() {
        return batchId;
    }

    public Optional<LongFilter> optionalBatchId() {
        return Optional.ofNullable(batchId);
    }

    public LongFilter batchId() {
        if (batchId == null) {
            setBatchId(new LongFilter());
        }
        return batchId;
    }

    public void setBatchId(LongFilter batchId) {
        this.batchId = batchId;
    }

    public StringFilter getBatchCode() {
        return batchCode;
    }

    public Optional<StringFilter> optionalBatchCode() {
        return Optional.ofNullable(batchCode);
    }

    public StringFilter batchCode() {
        if (batchCode == null) {
            setBatchCode(new StringFilter());
        }
        return batchCode;
    }

    public void setBatchCode(StringFilter batchCode) {
        this.batchCode = batchCode;
    }

    public InstantFilter getTxDate() {
        return txDate;
    }

    public Optional<InstantFilter> optionalTxDate() {
        return Optional.ofNullable(txDate);
    }

    public InstantFilter txDate() {
        if (txDate == null) {
            setTxDate(new InstantFilter());
        }
        return txDate;
    }

    public void setTxDate(InstantFilter txDate) {
        this.txDate = txDate;
    }

    public InstantFilter getManufactureDate() {
        return manufactureDate;
    }

    public Optional<InstantFilter> optionalManufactureDate() {
        return Optional.ofNullable(manufactureDate);
    }

    public InstantFilter manufactureDate() {
        if (manufactureDate == null) {
            setManufactureDate(new InstantFilter());
        }
        return manufactureDate;
    }

    public void setManufactureDate(InstantFilter manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public InstantFilter getExpiredDate() {
        return expiredDate;
    }

    public Optional<InstantFilter> optionalExpiredDate() {
        return Optional.ofNullable(expiredDate);
    }

    public InstantFilter expiredDate() {
        if (expiredDate == null) {
            setExpiredDate(new InstantFilter());
        }
        return expiredDate;
    }

    public void setExpiredDate(InstantFilter expiredDate) {
        this.expiredDate = expiredDate;
    }

    public DoubleFilter getQty() {
        return qty;
    }

    public Optional<DoubleFilter> optionalQty() {
        return Optional.ofNullable(qty);
    }

    public DoubleFilter qty() {
        if (qty == null) {
            setQty(new DoubleFilter());
        }
        return qty;
    }

    public void setQty(DoubleFilter qty) {
        this.qty = qty;
    }

    public BigDecimalFilter getCost() {
        return cost;
    }

    public Optional<BigDecimalFilter> optionalCost() {
        return Optional.ofNullable(cost);
    }

    public BigDecimalFilter cost() {
        if (cost == null) {
            setCost(new BigDecimalFilter());
        }
        return cost;
    }

    public void setCost(BigDecimalFilter cost) {
        this.cost = cost;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public Optional<BigDecimalFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public BigDecimalFilter price() {
        if (price == null) {
            setPrice(new BigDecimalFilter());
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public Optional<StringFilter> optionalNotes() {
        return Optional.ofNullable(notes);
    }

    public StringFilter notes() {
        if (notes == null) {
            setNotes(new StringFilter());
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public LongFilter getLmu() {
        return lmu;
    }

    public Optional<LongFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public LongFilter lmu() {
        if (lmu == null) {
            setLmu(new LongFilter());
        }
        return lmu;
    }

    public void setLmu(LongFilter lmu) {
        this.lmu = lmu;
    }

    public InstantFilter getLmd() {
        return lmd;
    }

    public Optional<InstantFilter> optionalLmd() {
        return Optional.ofNullable(lmd);
    }

    public InstantFilter lmd() {
        if (lmd == null) {
            setLmd(new InstantFilter());
        }
        return lmd;
    }

    public void setLmd(InstantFilter lmd) {
        this.lmd = lmd;
    }

    public BooleanFilter getNbt() {
        return nbt;
    }

    public Optional<BooleanFilter> optionalNbt() {
        return Optional.ofNullable(nbt);
    }

    public BooleanFilter nbt() {
        if (nbt == null) {
            setNbt(new BooleanFilter());
        }
        return nbt;
    }

    public void setNbt(BooleanFilter nbt) {
        this.nbt = nbt;
    }

    public BooleanFilter getVat() {
        return vat;
    }

    public Optional<BooleanFilter> optionalVat() {
        return Optional.ofNullable(vat);
    }

    public BooleanFilter vat() {
        if (vat == null) {
            setVat(new BooleanFilter());
        }
        return vat;
    }

    public void setVat(BooleanFilter vat) {
        this.vat = vat;
    }

    public LongFilter getAddedById() {
        return addedById;
    }

    public Optional<LongFilter> optionalAddedById() {
        return Optional.ofNullable(addedById);
    }

    public LongFilter addedById() {
        if (addedById == null) {
            setAddedById(new LongFilter());
        }
        return addedById;
    }

    public void setAddedById(LongFilter addedById) {
        this.addedById = addedById;
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
        final SalesInvoiceLineBatchCriteria that = (SalesInvoiceLineBatchCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(lineId, that.lineId) &&
            Objects.equals(batchLineId, that.batchLineId) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(code, that.code) &&
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(batchCode, that.batchCode) &&
            Objects.equals(txDate, that.txDate) &&
            Objects.equals(manufactureDate, that.manufactureDate) &&
            Objects.equals(expiredDate, that.expiredDate) &&
            Objects.equals(qty, that.qty) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(price, that.price) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(nbt, that.nbt) &&
            Objects.equals(vat, that.vat) &&
            Objects.equals(addedById, that.addedById) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            lineId,
            batchLineId,
            itemId,
            code,
            batchId,
            batchCode,
            txDate,
            manufactureDate,
            expiredDate,
            qty,
            cost,
            price,
            notes,
            lmu,
            lmd,
            nbt,
            vat,
            addedById,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceLineBatchCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLineId().map(f -> "lineId=" + f + ", ").orElse("") +
            optionalBatchLineId().map(f -> "batchLineId=" + f + ", ").orElse("") +
            optionalItemId().map(f -> "itemId=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalBatchId().map(f -> "batchId=" + f + ", ").orElse("") +
            optionalBatchCode().map(f -> "batchCode=" + f + ", ").orElse("") +
            optionalTxDate().map(f -> "txDate=" + f + ", ").orElse("") +
            optionalManufactureDate().map(f -> "manufactureDate=" + f + ", ").orElse("") +
            optionalExpiredDate().map(f -> "expiredDate=" + f + ", ").orElse("") +
            optionalQty().map(f -> "qty=" + f + ", ").orElse("") +
            optionalCost().map(f -> "cost=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalNbt().map(f -> "nbt=" + f + ", ").orElse("") +
            optionalVat().map(f -> "vat=" + f + ", ").orElse("") +
            optionalAddedById().map(f -> "addedById=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
