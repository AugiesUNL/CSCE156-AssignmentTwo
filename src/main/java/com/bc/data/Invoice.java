package com.bc.data;

import com.bc.data.products.*;

import java.util.Map;

public class Invoice {
    private final String code;
    private final Person owner;
    private final Customer customer;
    private final Map<Product, InvoiceProductData> products;

    public Invoice(String code, Person owner, Customer customer, Map<Product, InvoiceProductData> products) {
        this.code = code;
        this.owner = owner;
        this.customer = customer;
        this.products = products;
    }

    public String getCode() {
        return code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, InvoiceProductData> getProducts() {
        return products;
    }

    public boolean isPersonalCustomer() {
        return customer.getType() == 'P';
    }

    public boolean hasConcessionWithAssociatedRepair() {
        for (Map.Entry<Product, InvoiceProductData> product : products.entrySet()) {
            if (product.getValue().getAssociatedRepair() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTowingRepairRental() {
        boolean hasTowing = false;
        boolean hasRepair = false;
        boolean hasRental = false;
        for (Map.Entry<Product, InvoiceProductData> product : products.entrySet()) {
            switch (product.getKey().getType()) {
                case 'R':
                    hasRental = true;
                    break;
                case 'F':
                    hasRepair = true;
                    break;
                case 'T':
                    hasTowing = true;
                    break;
            }
        }
        return hasTowing && hasRepair && hasRental;
    }

    public boolean isLoyalCustomer() {
        return customer.getContact().getEmails().size() > 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invoice Details:\n");
        stringBuilder.append("=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+\n");
        stringBuilder.append(String.format("Invoice %s%n", code));
        stringBuilder.append("-----------------------------------------\n");
        stringBuilder.append(owner.toString()).append("\n");
        stringBuilder.append(customer.toString()).append("\n");
        stringBuilder.append("Products:\n");
        stringBuilder.append(String.format("  %-12s%-60s%-13s%-13s%-13s%-10s%n", "Code", "Description", "Subtotal", "Discount", "Taxes", "Total"));
        stringBuilder.append("  ------------------------------------------------------------------------------------------------------------------------------------");
        double subtotal = 0;
        double discount = 0;
        double taxes = 0;
        double total = 0;
        for (Map.Entry<Product, InvoiceProductData> productEntry : products.entrySet()) {
            Product product = productEntry.getKey();
            InvoiceProductData invoiceProductData = productEntry.getValue();
            subtotal += product.getSubtotal(invoiceProductData);
            discount += product.getDiscount(invoiceProductData, this);
            taxes += product.getTaxes(invoiceProductData, this);
            total += product.getTotal(invoiceProductData, this);
            switch (product.getType()) {
                case 'R':
                    Rental rental = (Rental) product;
                    stringBuilder.append("\n").append(getRentalStringEntry(rental, invoiceProductData));
                    break;
                case 'F': //repair
                    Repair repair = (Repair) product;
                    stringBuilder.append("\n").append(getRepairStringEntry(repair, invoiceProductData));
                    break;
                case 'C':
                    Concession concession = (Concession) product;
                    stringBuilder.append("\n").append(getConcessionStringEntry(concession, invoiceProductData));
                    break;
                case 'T':
                    Towing towing = (Towing) product;
                    stringBuilder.append("\n").append(getTowingStringEntry(towing, invoiceProductData));
                    break;
            }
        }
        stringBuilder.append("======================================================================================================================================");
        stringBuilder.append(String.format("%n%-74s$%-12.2f$%-12.2f$%-12.2f$%.2f", "Item Totals:", subtotal, discount, taxes, total));
        double accountFee = isPersonalCustomer() ? 0 : 75.5;
        if (accountFee > 0) {
            stringBuilder.append(String.format("%nBusiness Account Fee: $%.2f", accountFee));
        }
        double loyalCustomerDiscount = isLoyalCustomer() ? -1 * .05 * total : 0;
        if (loyalCustomerDiscount != 0) {
            stringBuilder.append(String.format("%nLoyal Customer Discount (5%% OFF): -$%.2f", (-1 * loyalCustomerDiscount)));
        }
        stringBuilder.append(String.format("%nGRAND TOTAL: $%.2f", (total + loyalCustomerDiscount + accountFee)));
        return stringBuilder.toString();
    }

    public String getTowingStringEntry(Towing towing, InvoiceProductData invoiceProductData) {
        double subtotal = towing.getSubtotal(invoiceProductData);
        double discount = towing.getDiscount(invoiceProductData, this);
        double taxes = towing.getTaxes(invoiceProductData, this);
        double total = towing.getTotal(invoiceProductData, this);
        return String.format(
                "  %-12s%-60s$%-12.2f$%-12.2f$%-12.2f$%-10.2f%n",
                towing.getCode(),
                String.format(
                        "%s (%d miles @ $%.2f/mile)",
                        towing.getLabel(),
                        invoiceProductData.getMilesTowed(),
                        towing.getCostPerMile()
                ),
                subtotal,
                discount,
                taxes,
                total
        );
    }

    public String getConcessionStringEntry(Concession concession, InvoiceProductData invoiceProductData) {
        double subtotal = concession.getSubtotal(invoiceProductData);
        double discount = concession.getDiscount(invoiceProductData, this);
        double taxes = concession.getTaxes(invoiceProductData, this);
        double total = concession.getTotal(invoiceProductData, this);
        return String.format(
                "  %-12s%-60s$%-12.2f$%-12.2f$%-12.2f$%-10.2f%n",
                concession.getCode(),
                String.format(
                        "%s (%d units @ $%.2f/unit)",
                        concession.getLabel(),
                        invoiceProductData.getQuantity(),
                        concession.getUnitCost()
                ),
                subtotal,
                discount,
                taxes,
                total
        );
    }

    public String getRentalStringEntry(Rental rental, InvoiceProductData invoiceProductData) {
        double subtotal = rental.getSubtotal(invoiceProductData);
        double discount = rental.getDiscount(invoiceProductData, this);
        double taxes = rental.getTaxes(invoiceProductData, this);
        double total = rental.getTotal(invoiceProductData, this);
        return String.format(
                "  %-12s%-60s$%-12.2f$%-12.2f$%-12.2f$%-10.2f%n",
                rental.getCode(),
                String.format(
                        "%s (%-2d @ $%.2f/day)",
                        rental.getLabel(),
                        invoiceProductData.getDaysRented(),
                        rental.getDailyCost()
                ),
                subtotal,
                discount,
                taxes,
                total
        );
    }

    public String getRepairStringEntry(Repair repair, InvoiceProductData invoiceProductData) {
        double subtotal = repair.getSubtotal(invoiceProductData);
        double discount = repair.getDiscount(invoiceProductData, this);
        double taxes = repair.getTaxes(invoiceProductData, this);
        double total = repair.getTotal(invoiceProductData, this);
        return String.format(
                "  %-12s%-60s$%-12.2f$%-12.2f$%-12.2f$%-10.2f%n",
                repair.getCode(),
                String.format(
                        "%s (%d hours of labor @ $%.2f/hour%n(+ $%.2f for parts)",
                        repair.getLabel(),
                        invoiceProductData.getHoursWorked(),
                        repair.getHourlyLaborCost(),
                        repair.getPartsCost()
                ),
                subtotal,
                discount,
                taxes,
                total
        );
    }

    public double getTaxRate() {
        return isPersonalCustomer() ? .08 : .0425;
    }
    /**
     * method to calculate invoice subtotal for invoice summary
     * @return the invoice subtotal
     */
    public double getInvoiceSubtotal() {
        double subtotal = 0;
        for (Map.Entry<Product, InvoiceProductData> entry : products.entrySet()) {
            subtotal += entry.getKey().getSubtotal(entry.getValue());
        }
        return subtotal;

    }
    /**
     * method to calculate invoice discount for invoice summary
     * @return the invoice discount
     */
    public double getInvoiceDiscount() {
        double discount = 0;
        for (Map.Entry<Product, InvoiceProductData> entry : products.entrySet()) {
            discount += entry.getKey().getDiscount(entry.getValue(), this);
        }
        return discount;

    }
    /**
     * method to calculate invoice fees for invoice summary
     * @return the invoice fee
     */
    public double getInvoiceFees() {

        return isPersonalCustomer() ? 0 : 75.5;

    }
    /**
     * method to calculate invoice taxes for invoice summary
     * @return the invoice taxes
     */
    public double getInvoiceTaxes() {
        double taxes = 0;
        for (Map.Entry<Product, InvoiceProductData> entry : products.entrySet()) {
            taxes += entry.getKey().getTaxes(entry.getValue(), this);
        }
        return taxes;

    }

    /**
     * method to calculate invoice total for invoice summary
     * @return the invoice total
     */
    public double getInvoiceTotal() {
        return getInvoiceSubtotal() + getInvoiceDiscount() + getInvoiceFees() + getInvoiceTaxes();
    }

    public Person getOwner()
    {
        return this.owner;
    }
}
