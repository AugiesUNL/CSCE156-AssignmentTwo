package com.bc.model;

import java.util.Map;

/**
 * A class to hold information pertaining to the Invoice object
 */
public class Invoice {
    private final String code;
    private final Person owner;
    private final Customer customer;
    private final Map<Product, InvoiceProductData> products;

    /**
     *invoice constructor
     * @param code string value pertaining to ordering of invoices
     * @param owner person object attached to invoice
     * @param customer customer of the invoice
     * @param products product items that make up the invoice
     */
    public Invoice(String code, Person owner, Customer customer, Map<Product, InvoiceProductData> products) {
        this.code = code;
        this.owner = owner;
        this.customer = customer;
        this.products = products;
    }

    /**
     * a method to get invoice code
     * @return invoice code string
     */
    public String getCode() {
        return code;
    }

    /**
     * a method to get customer object associated with the invoice
     * @return a customer object
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * a method to get a map of the products associated with the invoice
     * @return a map of products
     */
    public Map<Product, InvoiceProductData> getProducts() {
        return products;
    }

    /**
     * a method to get the type of customer associated with the invoice
     * @return boolean value of if a customer is a business
     */
    public boolean isBusinessCustomer() {
        return customer.getType() == 'B';
    }

    /**
     * a method to get a boolean result pertaining to if an invoice has a towing, repair, and rental product
     * @return boolean value of if an invoice has towing,repair,rental
     */
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

    /**
     * a method to get a boolean result pertaining to whether or not a customer is eligible for the loyal customer discount
     * @return boolean value of whether a customer is loyal
     */
    public boolean isLoyalCustomer() {
        return customer.getType() == 'P' && customer.getContact().getEmails().size() > 1;
    }

    /**
     * to string method
     * @return invoice as a string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
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
        double accountFee = isBusinessCustomer() ? 75.5 : 0;
        if (accountFee > 0) {
            stringBuilder.append(String.format("%nBusiness Account Fee: $%.2f", accountFee));
        }
        double loyalCustomerDiscount = isLoyalCustomer() ? -.05 * total : 0;
        if (loyalCustomerDiscount != 0) {
            stringBuilder.append(String.format("%nLoyal Customer Discount (5%% OFF): -$%.2f", (-1 * loyalCustomerDiscount)));
        }
        stringBuilder.append(String.format("%nGRAND TOTAL: $%.2f", (total + loyalCustomerDiscount + accountFee)));
        stringBuilder.append("\n\n        THANK YOU FOR DOING BUSINESS WITH US!\n\n\n");
        return stringBuilder.toString();
    }

    /**
     * to string method for a towing entry
     * @param towing to reference the towing in the invoice
     * @param invoiceProductData data pertaining to the towing
     * @return towing entry as a string
     */
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

    /**
     * to string method for the concession entry
     * @param concession to reference the concession in the invoice
     * @param invoiceProductData data pertaining to the concession
     * @return concession entry as a string
     */
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

    /**
     * to string method for the rental entry
     * @param rental to reference the rental in the invoice
     * @param invoiceProductData data pertaining to the rental
     * @return rental entry as a string
     */
    public String getRentalStringEntry(Rental rental, InvoiceProductData invoiceProductData) {
        double subtotal = rental.getSubtotal(invoiceProductData);
        double discount = rental.getDiscount(invoiceProductData, this);
        double taxes = rental.getTaxes(invoiceProductData, this);
        double total = rental.getTotal(invoiceProductData, this);
        return String.format(
                "  %-12s%-60s$%-12.2f$%-12.2f$%-12.2f$%-10.2f%n",
                rental.getCode(),
                String.format(
                        "%s (%.2f days @ $%.2f/day) (+ $%.2f cleaning fee, $%.2f deposit refund)",
                        rental.getLabel(),
                        invoiceProductData.getDaysRented(),
                        rental.getDailyCost(),
                        rental.getCleaningFee(),
                        -1*rental.getDeposit()
                ),
                subtotal,
                discount,
                taxes,
                total
        );
    }

    /**
     * to string method for the repair entry
     * @param repair to reference the repair in the invoice
     * @param invoiceProductData data pertaining to the repair
     * @return repair entry as a string
     */
    public String getRepairStringEntry(Repair repair, InvoiceProductData invoiceProductData) {
        double subtotal = repair.getSubtotal(invoiceProductData);
        double discount = repair.getDiscount(invoiceProductData, this);
        double taxes = repair.getTaxes(invoiceProductData, this);
        double total = repair.getTotal(invoiceProductData, this);
        return String.format(
                "  %-12s%-60s$%-12.2f$%-12.2f$%-12.2f$%-10.2f%n",
                repair.getCode(),
                String.format(
                        "%s (%.2f hours of labor @ $%.2f/hour%n(+ $%.2f for parts)",
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

    /**
     * a method to get the tax rate of a certain invoice
     * @return the invoice tax rate
     */
    public double getTaxRate() {
        return isBusinessCustomer() ? .0425 : .08;
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
        double total = 0;
        for (Map.Entry<Product, InvoiceProductData> productEntry : products.entrySet()) { //I use this instead of getInvoiceTotal to avoid stackOverflow
            total+=productEntry.getKey().getTotal(productEntry.getValue(),this);
        }
        double loyalCustomerDiscount = isLoyalCustomer() ? -.05 * total : 0;
        for (Map.Entry<Product, InvoiceProductData> entry : products.entrySet()) {
            discount += entry.getKey().getDiscount(entry.getValue(), this);
        }
        return discount+loyalCustomerDiscount;
    }
    /**
     * method to calculate invoice fees for invoice summary
     * @return the invoice fee
     */
    public double getInvoiceFees() {

        return !isBusinessCustomer() ? 0 : 75.5;

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

    /**
     * method to get the person object that is the owner of the business of the company in the invoice
     * @return the owner of the business
     */
    public Person getOwner()
    {
        return this.owner;
    }
}
