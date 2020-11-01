package com.bc.model;

import com.bc.model.Invoice;
import com.bc.model.InvoiceProductData;

public interface Discountable {
    default double getDiscount(InvoiceProductData invoiceProductData, Invoice invoice) {
        return 0;
    }
}
