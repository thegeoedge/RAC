package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.BinCard} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.BinCardResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bin-cards?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BinCardCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter itemID;

    private StringFilter itemCode;

    private StringFilter reference;

    private InstantFilter txDate;

    private DoubleFilter qtyIn;

    private DoubleFilter qtyOut;

    private DoubleFilter price;

    private IntegerFilter lMU;

    private InstantFilter lMD;

    private StringFilter referenceCode;

    private InstantFilter recordDate;

    private LongFilter batchId;

    private LongFilter locationID;

    private StringFilter locationCode;

    private DoubleFilter opening;

    private StringFilter description;

    private StringFilter referenceDoc;

    private Boolean distinct;

    public BinCardCriteria() {}

    public BinCardCriteria(BinCardCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.itemID = other.optionalItemID().map(LongFilter::copy).orElse(null);
        this.itemCode = other.optionalItemCode().map(StringFilter::copy).orElse(null);
        this.reference = other.optionalReference().map(StringFilter::copy).orElse(null);
        this.txDate = other.optionalTxDate().map(InstantFilter::copy).orElse(null);
        this.qtyIn = other.optionalQtyIn().map(DoubleFilter::copy).orElse(null);
        this.qtyOut = other.optionalQtyOut().map(DoubleFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(DoubleFilter::copy).orElse(null);
        this.lMU = other.optionallMU().map(IntegerFilter::copy).orElse(null);
        this.lMD = other.optionallMD().map(InstantFilter::copy).orElse(null);
        this.referenceCode = other.optionalReferenceCode().map(StringFilter::copy).orElse(null);
        this.recordDate = other.optionalRecordDate().map(InstantFilter::copy).orElse(null);
        this.batchId = other.optionalBatchId().map(LongFilter::copy).orElse(null);
        this.locationID = other.optionalLocationID().map(LongFilter::copy).orElse(null);
        this.locationCode = other.optionalLocationCode().map(StringFilter::copy).orElse(null);
        this.opening = other.optionalOpening().map(DoubleFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.referenceDoc = other.optionalReferenceDoc().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BinCardCriteria copy() {
        return new BinCardCriteria(this);
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

    public LongFilter getItemID() {
        return itemID;
    }

    public Optional<LongFilter> optionalItemID() {
        return Optional.ofNullable(itemID);
    }

    public LongFilter itemID() {
        if (itemID == null) {
            setItemID(new LongFilter());
        }
        return itemID;
    }

    public void setItemID(LongFilter itemID) {
        this.itemID = itemID;
    }

    public StringFilter getItemCode() {
        return itemCode;
    }

    public Optional<StringFilter> optionalItemCode() {
        return Optional.ofNullable(itemCode);
    }

    public StringFilter itemCode() {
        if (itemCode == null) {
            setItemCode(new StringFilter());
        }
        return itemCode;
    }

    public void setItemCode(StringFilter itemCode) {
        this.itemCode = itemCode;
    }

    public StringFilter getReference() {
        return reference;
    }

    public Optional<StringFilter> optionalReference() {
        return Optional.ofNullable(reference);
    }

    public StringFilter reference() {
        if (reference == null) {
            setReference(new StringFilter());
        }
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
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

    public DoubleFilter getQtyIn() {
        return qtyIn;
    }

    public Optional<DoubleFilter> optionalQtyIn() {
        return Optional.ofNullable(qtyIn);
    }

    public DoubleFilter qtyIn() {
        if (qtyIn == null) {
            setQtyIn(new DoubleFilter());
        }
        return qtyIn;
    }

    public void setQtyIn(DoubleFilter qtyIn) {
        this.qtyIn = qtyIn;
    }

    public DoubleFilter getQtyOut() {
        return qtyOut;
    }

    public Optional<DoubleFilter> optionalQtyOut() {
        return Optional.ofNullable(qtyOut);
    }

    public DoubleFilter qtyOut() {
        if (qtyOut == null) {
            setQtyOut(new DoubleFilter());
        }
        return qtyOut;
    }

    public void setQtyOut(DoubleFilter qtyOut) {
        this.qtyOut = qtyOut;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public Optional<DoubleFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public DoubleFilter price() {
        if (price == null) {
            setPrice(new DoubleFilter());
        }
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public IntegerFilter getlMU() {
        return lMU;
    }

    public Optional<IntegerFilter> optionallMU() {
        return Optional.ofNullable(lMU);
    }

    public IntegerFilter lMU() {
        if (lMU == null) {
            setlMU(new IntegerFilter());
        }
        return lMU;
    }

    public void setlMU(IntegerFilter lMU) {
        this.lMU = lMU;
    }

    public InstantFilter getlMD() {
        return lMD;
    }

    public Optional<InstantFilter> optionallMD() {
        return Optional.ofNullable(lMD);
    }

    public InstantFilter lMD() {
        if (lMD == null) {
            setlMD(new InstantFilter());
        }
        return lMD;
    }

    public void setlMD(InstantFilter lMD) {
        this.lMD = lMD;
    }

    public StringFilter getReferenceCode() {
        return referenceCode;
    }

    public Optional<StringFilter> optionalReferenceCode() {
        return Optional.ofNullable(referenceCode);
    }

    public StringFilter referenceCode() {
        if (referenceCode == null) {
            setReferenceCode(new StringFilter());
        }
        return referenceCode;
    }

    public void setReferenceCode(StringFilter referenceCode) {
        this.referenceCode = referenceCode;
    }

    public InstantFilter getRecordDate() {
        return recordDate;
    }

    public Optional<InstantFilter> optionalRecordDate() {
        return Optional.ofNullable(recordDate);
    }

    public InstantFilter recordDate() {
        if (recordDate == null) {
            setRecordDate(new InstantFilter());
        }
        return recordDate;
    }

    public void setRecordDate(InstantFilter recordDate) {
        this.recordDate = recordDate;
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

    public LongFilter getLocationID() {
        return locationID;
    }

    public Optional<LongFilter> optionalLocationID() {
        return Optional.ofNullable(locationID);
    }

    public LongFilter locationID() {
        if (locationID == null) {
            setLocationID(new LongFilter());
        }
        return locationID;
    }

    public void setLocationID(LongFilter locationID) {
        this.locationID = locationID;
    }

    public StringFilter getLocationCode() {
        return locationCode;
    }

    public Optional<StringFilter> optionalLocationCode() {
        return Optional.ofNullable(locationCode);
    }

    public StringFilter locationCode() {
        if (locationCode == null) {
            setLocationCode(new StringFilter());
        }
        return locationCode;
    }

    public void setLocationCode(StringFilter locationCode) {
        this.locationCode = locationCode;
    }

    public DoubleFilter getOpening() {
        return opening;
    }

    public Optional<DoubleFilter> optionalOpening() {
        return Optional.ofNullable(opening);
    }

    public DoubleFilter opening() {
        if (opening == null) {
            setOpening(new DoubleFilter());
        }
        return opening;
    }

    public void setOpening(DoubleFilter opening) {
        this.opening = opening;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getReferenceDoc() {
        return referenceDoc;
    }

    public Optional<StringFilter> optionalReferenceDoc() {
        return Optional.ofNullable(referenceDoc);
    }

    public StringFilter referenceDoc() {
        if (referenceDoc == null) {
            setReferenceDoc(new StringFilter());
        }
        return referenceDoc;
    }

    public void setReferenceDoc(StringFilter referenceDoc) {
        this.referenceDoc = referenceDoc;
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
        final BinCardCriteria that = (BinCardCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(itemID, that.itemID) &&
            Objects.equals(itemCode, that.itemCode) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(txDate, that.txDate) &&
            Objects.equals(qtyIn, that.qtyIn) &&
            Objects.equals(qtyOut, that.qtyOut) &&
            Objects.equals(price, that.price) &&
            Objects.equals(lMU, that.lMU) &&
            Objects.equals(lMD, that.lMD) &&
            Objects.equals(referenceCode, that.referenceCode) &&
            Objects.equals(recordDate, that.recordDate) &&
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(locationID, that.locationID) &&
            Objects.equals(locationCode, that.locationCode) &&
            Objects.equals(opening, that.opening) &&
            Objects.equals(description, that.description) &&
            Objects.equals(referenceDoc, that.referenceDoc) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            itemID,
            itemCode,
            reference,
            txDate,
            qtyIn,
            qtyOut,
            price,
            lMU,
            lMD,
            referenceCode,
            recordDate,
            batchId,
            locationID,
            locationCode,
            opening,
            description,
            referenceDoc,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BinCardCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalItemID().map(f -> "itemID=" + f + ", ").orElse("") +
            optionalItemCode().map(f -> "itemCode=" + f + ", ").orElse("") +
            optionalReference().map(f -> "reference=" + f + ", ").orElse("") +
            optionalTxDate().map(f -> "txDate=" + f + ", ").orElse("") +
            optionalQtyIn().map(f -> "qtyIn=" + f + ", ").orElse("") +
            optionalQtyOut().map(f -> "qtyOut=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionallMU().map(f -> "lMU=" + f + ", ").orElse("") +
            optionallMD().map(f -> "lMD=" + f + ", ").orElse("") +
            optionalReferenceCode().map(f -> "referenceCode=" + f + ", ").orElse("") +
            optionalRecordDate().map(f -> "recordDate=" + f + ", ").orElse("") +
            optionalBatchId().map(f -> "batchId=" + f + ", ").orElse("") +
            optionalLocationID().map(f -> "locationID=" + f + ", ").orElse("") +
            optionalLocationCode().map(f -> "locationCode=" + f + ", ").orElse("") +
            optionalOpening().map(f -> "opening=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalReferenceDoc().map(f -> "referenceDoc=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
