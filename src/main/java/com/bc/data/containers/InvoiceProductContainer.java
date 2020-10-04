package com.bc.data.containers;

import com.bc.data.InvoiceProductData;
import com.bc.data.products.Product;

public class InvoiceProductContainer {
    private final Product product;
    private final InvoiceProductData invoiceProductData;

    public InvoiceProductContainer(Product product, InvoiceProductData invoiceProductData) {
        this.product = product;
        this.invoiceProductData = invoiceProductData;
    }

    public Product getProduct() {
        return product;
    }

    public InvoiceProductData getInvoiceProductData() {
        return invoiceProductData;
    }
}
