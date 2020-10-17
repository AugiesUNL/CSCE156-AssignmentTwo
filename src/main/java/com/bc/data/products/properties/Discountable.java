package com.bc.data.products.properties;

import com.bc.data.Invoice;
import com.bc.data.InvoiceProductData;

public interface Discountable {
    default double getDiscount(InvoiceProductData invoiceProductData, Invoice invoice) {
        return 0;
    }
}
