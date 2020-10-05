package com.bc.data;

import com.bc.data.containers.InvoiceProductContainer;
import com.bc.data.products.*;

import java.util.List;
import java.util.Map;

public class Invoice {
    private final String code;
    private final Person owner;
    private final Customer customer;
    private final List<InvoiceProductContainer> products;

    public Invoice(String code, Person owner, Customer customer, List<InvoiceProductContainer> products) {
        this.code = code;
        this.owner = owner;
        this.customer = customer;
        this.products = products;
    }

    public String getCode() {
        return code;
    }

    public Person getOwner() {
        return owner;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<InvoiceProductContainer> getProducts() {
        return products;
    }

    @Override
    public String toString(){
    boolean discountTowing = false;
    boolean repairCheck = false;
    boolean rentalCheck = false;
    boolean towingCheck = false;
    boolean discountConcession = false;
    boolean concessionCheck = false;
    boolean loyalCustomerDiscount = false;
        for(InvoiceProductContainer pc : products){
            switch(pc.getProduct().getType()){
                case 'C':
                    Concession c = (Concession)pc.getProduct(); //Now you can use this object to get data about the concession.
                    double concCost = c.getUnitCost() * pc.getInvoiceProductData().getQuantity();
                    concessionCheck = true;
                    break;
                case 'R':
                    Rental r = (Rental)pc.getProduct(); //Now you can use this object to get data about the concession.
                    double rentCost = (r.getDailyCost()*pc.getInvoiceProductData().getDaysRented()) + r.getCleaningFee()+r.getDeposit();
                    rentalCheck = true;
                    break;
                case 'F':
                    Repair rp = (Repair)pc.getProduct(); //Now you can use this object to get data about the concession.
                    double repCost = (rp.getHourlyLaborCost()*pc.getInvoiceProductData().getHoursWorked()) + rp.getPartsCost();
                    repairCheck = true;
                    break;
                case 'T':
                    Towing t = (Towing)pc.getProduct(); //Now you can use this object to get data about the concession.
                    double tw = (t.getCostPerMile()*pc.getInvoiceProductData().getMilesTowed());
                    towingCheck = true;
                    break;
            }
            if (getCustomer().getType() == 'B')
            {
                double tax = 4.25;
                double fee = 75.5;

            }
            if (getCustomer().getType() == 'P')
            {
                double tax = 8;
                double fee = 0;
            }
            if (rentalCheck && repairCheck && towingCheck)
            {
                discountTowing = true;
            }
            if (concessionCheck && repairCheck)
            {
                concessionCheck = true;
            }
            if (getOwner().getNumOfEmails() > 1)
            {
                loyalCustomerDiscount = true;
            }

        }
        return code + "\n" + owner.toString() + "\n" + customer.toString() + "\n Products: \n   Code        Description    \n + Subtotal     Discount     Taxes        Total \n"
                +

    }





    }
    }
}
