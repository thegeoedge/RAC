package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SalesInvoiceLineBatchTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesInvoiceLineBatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesInvoiceLineBatch.class);
        SalesInvoiceLineBatch salesInvoiceLineBatch1 = getSalesInvoiceLineBatchSample1();
        SalesInvoiceLineBatch salesInvoiceLineBatch2 = new SalesInvoiceLineBatch();
        assertThat(salesInvoiceLineBatch1).isNotEqualTo(salesInvoiceLineBatch2);

        salesInvoiceLineBatch2.setId(salesInvoiceLineBatch1.getId());
        assertThat(salesInvoiceLineBatch1).isEqualTo(salesInvoiceLineBatch2);

        salesInvoiceLineBatch2 = getSalesInvoiceLineBatchSample2();
        assertThat(salesInvoiceLineBatch1).isNotEqualTo(salesInvoiceLineBatch2);
    }
}
