package com.heavenscode.rac.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite key for SalesInvoiceLineBatch.
 */
public class SalesInvoiceLineBatchId implements Serializable {

    private Long id;
    private Long lineId;
    private Long batchLineId;

    // Default constructor
    public SalesInvoiceLineBatchId() {}

    // Constructor with fields
    public SalesInvoiceLineBatchId(Long id, Long lineId, Long batchLineId) {
        this.id = id;
        this.lineId = lineId;
        this.batchLineId = batchLineId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getBatchLineId() {
        return batchLineId;
    }

    public void setBatchLineId(Long batchLineId) {
        this.batchLineId = batchLineId;
    }

    // hashCode and equals (required for @IdClass)
    @Override
    public int hashCode() {
        return Objects.hash(id, lineId, batchLineId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SalesInvoiceLineBatchId other = (SalesInvoiceLineBatchId) obj;
        return Objects.equals(id, other.id) && Objects.equals(lineId, other.lineId) && Objects.equals(batchLineId, other.batchLineId);
    }
}
