package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Inventorybatches} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.InventorybatchesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventorybatches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventorybatchesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter itemid;

    private StringFilter code;

    private InstantFilter txdate;

    private FloatFilter cost;

    private FloatFilter price;

    private FloatFilter costwithoutvat;

    private FloatFilter pricewithoutvat;

    private StringFilter notes;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private IntegerFilter lineid;

    private InstantFilter manufacturedate;

    private InstantFilter expiredate;

    private FloatFilter quantity;

    private InstantFilter addeddate;

    private FloatFilter costtotal;

    private FloatFilter returnprice;

    private Boolean distinct;

    public InventorybatchesCriteria() {}

    public InventorybatchesCriteria(InventorybatchesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.itemid = other.optionalItemid().map(IntegerFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.txdate = other.optionalTxdate().map(InstantFilter::copy).orElse(null);
        this.cost = other.optionalCost().map(FloatFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(FloatFilter::copy).orElse(null);
        this.costwithoutvat = other.optionalCostwithoutvat().map(FloatFilter::copy).orElse(null);
        this.pricewithoutvat = other.optionalPricewithoutvat().map(FloatFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.lineid = other.optionalLineid().map(IntegerFilter::copy).orElse(null);
        this.manufacturedate = other.optionalManufacturedate().map(InstantFilter::copy).orElse(null);
        this.expiredate = other.optionalExpiredate().map(InstantFilter::copy).orElse(null);
        this.quantity = other.optionalQuantity().map(FloatFilter::copy).orElse(null);
        this.addeddate = other.optionalAddeddate().map(InstantFilter::copy).orElse(null);
        this.costtotal = other.optionalCosttotal().map(FloatFilter::copy).orElse(null);
        this.returnprice = other.optionalReturnprice().map(FloatFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InventorybatchesCriteria copy() {
        return new InventorybatchesCriteria(this);
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

    public IntegerFilter getItemid() {
        return itemid;
    }

    public Optional<IntegerFilter> optionalItemid() {
        return Optional.ofNullable(itemid);
    }

    public IntegerFilter itemid() {
        if (itemid == null) {
            setItemid(new IntegerFilter());
        }
        return itemid;
    }

    public void setItemid(IntegerFilter itemid) {
        this.itemid = itemid;
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

    public InstantFilter getTxdate() {
        return txdate;
    }

    public Optional<InstantFilter> optionalTxdate() {
        return Optional.ofNullable(txdate);
    }

    public InstantFilter txdate() {
        if (txdate == null) {
            setTxdate(new InstantFilter());
        }
        return txdate;
    }

    public void setTxdate(InstantFilter txdate) {
        this.txdate = txdate;
    }

    public FloatFilter getCost() {
        return cost;
    }

    public Optional<FloatFilter> optionalCost() {
        return Optional.ofNullable(cost);
    }

    public FloatFilter cost() {
        if (cost == null) {
            setCost(new FloatFilter());
        }
        return cost;
    }

    public void setCost(FloatFilter cost) {
        this.cost = cost;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public Optional<FloatFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public FloatFilter price() {
        if (price == null) {
            setPrice(new FloatFilter());
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public FloatFilter getCostwithoutvat() {
        return costwithoutvat;
    }

    public Optional<FloatFilter> optionalCostwithoutvat() {
        return Optional.ofNullable(costwithoutvat);
    }

    public FloatFilter costwithoutvat() {
        if (costwithoutvat == null) {
            setCostwithoutvat(new FloatFilter());
        }
        return costwithoutvat;
    }

    public void setCostwithoutvat(FloatFilter costwithoutvat) {
        this.costwithoutvat = costwithoutvat;
    }

    public FloatFilter getPricewithoutvat() {
        return pricewithoutvat;
    }

    public Optional<FloatFilter> optionalPricewithoutvat() {
        return Optional.ofNullable(pricewithoutvat);
    }

    public FloatFilter pricewithoutvat() {
        if (pricewithoutvat == null) {
            setPricewithoutvat(new FloatFilter());
        }
        return pricewithoutvat;
    }

    public void setPricewithoutvat(FloatFilter pricewithoutvat) {
        this.pricewithoutvat = pricewithoutvat;
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

    public IntegerFilter getLmu() {
        return lmu;
    }

    public Optional<IntegerFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public IntegerFilter lmu() {
        if (lmu == null) {
            setLmu(new IntegerFilter());
        }
        return lmu;
    }

    public void setLmu(IntegerFilter lmu) {
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

    public IntegerFilter getLineid() {
        return lineid;
    }

    public Optional<IntegerFilter> optionalLineid() {
        return Optional.ofNullable(lineid);
    }

    public IntegerFilter lineid() {
        if (lineid == null) {
            setLineid(new IntegerFilter());
        }
        return lineid;
    }

    public void setLineid(IntegerFilter lineid) {
        this.lineid = lineid;
    }

    public InstantFilter getManufacturedate() {
        return manufacturedate;
    }

    public Optional<InstantFilter> optionalManufacturedate() {
        return Optional.ofNullable(manufacturedate);
    }

    public InstantFilter manufacturedate() {
        if (manufacturedate == null) {
            setManufacturedate(new InstantFilter());
        }
        return manufacturedate;
    }

    public void setManufacturedate(InstantFilter manufacturedate) {
        this.manufacturedate = manufacturedate;
    }

    public InstantFilter getExpiredate() {
        return expiredate;
    }

    public Optional<InstantFilter> optionalExpiredate() {
        return Optional.ofNullable(expiredate);
    }

    public InstantFilter expiredate() {
        if (expiredate == null) {
            setExpiredate(new InstantFilter());
        }
        return expiredate;
    }

    public void setExpiredate(InstantFilter expiredate) {
        this.expiredate = expiredate;
    }

    public FloatFilter getQuantity() {
        return quantity;
    }

    public Optional<FloatFilter> optionalQuantity() {
        return Optional.ofNullable(quantity);
    }

    public FloatFilter quantity() {
        if (quantity == null) {
            setQuantity(new FloatFilter());
        }
        return quantity;
    }

    public void setQuantity(FloatFilter quantity) {
        this.quantity = quantity;
    }

    public InstantFilter getAddeddate() {
        return addeddate;
    }

    public Optional<InstantFilter> optionalAddeddate() {
        return Optional.ofNullable(addeddate);
    }

    public InstantFilter addeddate() {
        if (addeddate == null) {
            setAddeddate(new InstantFilter());
        }
        return addeddate;
    }

    public void setAddeddate(InstantFilter addeddate) {
        this.addeddate = addeddate;
    }

    public FloatFilter getCosttotal() {
        return costtotal;
    }

    public Optional<FloatFilter> optionalCosttotal() {
        return Optional.ofNullable(costtotal);
    }

    public FloatFilter costtotal() {
        if (costtotal == null) {
            setCosttotal(new FloatFilter());
        }
        return costtotal;
    }

    public void setCosttotal(FloatFilter costtotal) {
        this.costtotal = costtotal;
    }

    public FloatFilter getReturnprice() {
        return returnprice;
    }

    public Optional<FloatFilter> optionalReturnprice() {
        return Optional.ofNullable(returnprice);
    }

    public FloatFilter returnprice() {
        if (returnprice == null) {
            setReturnprice(new FloatFilter());
        }
        return returnprice;
    }

    public void setReturnprice(FloatFilter returnprice) {
        this.returnprice = returnprice;
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
        final InventorybatchesCriteria that = (InventorybatchesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(itemid, that.itemid) &&
            Objects.equals(code, that.code) &&
            Objects.equals(txdate, that.txdate) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(price, that.price) &&
            Objects.equals(costwithoutvat, that.costwithoutvat) &&
            Objects.equals(pricewithoutvat, that.pricewithoutvat) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(lineid, that.lineid) &&
            Objects.equals(manufacturedate, that.manufacturedate) &&
            Objects.equals(expiredate, that.expiredate) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(addeddate, that.addeddate) &&
            Objects.equals(costtotal, that.costtotal) &&
            Objects.equals(returnprice, that.returnprice) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            itemid,
            code,
            txdate,
            cost,
            price,
            costwithoutvat,
            pricewithoutvat,
            notes,
            lmu,
            lmd,
            lineid,
            manufacturedate,
            expiredate,
            quantity,
            addeddate,
            costtotal,
            returnprice,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventorybatchesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalItemid().map(f -> "itemid=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalTxdate().map(f -> "txdate=" + f + ", ").orElse("") +
            optionalCost().map(f -> "cost=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalCostwithoutvat().map(f -> "costwithoutvat=" + f + ", ").orElse("") +
            optionalPricewithoutvat().map(f -> "pricewithoutvat=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalLineid().map(f -> "lineid=" + f + ", ").orElse("") +
            optionalManufacturedate().map(f -> "manufacturedate=" + f + ", ").orElse("") +
            optionalExpiredate().map(f -> "expiredate=" + f + ", ").orElse("") +
            optionalQuantity().map(f -> "quantity=" + f + ", ").orElse("") +
            optionalAddeddate().map(f -> "addeddate=" + f + ", ").orElse("") +
            optionalCosttotal().map(f -> "costtotal=" + f + ", ").orElse("") +
            optionalReturnprice().map(f -> "returnprice=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
