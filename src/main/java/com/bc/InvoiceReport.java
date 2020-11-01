package com.bc;

import com.bc.ext.DatabaseObjectLoader;
import com.bc.model.Invoice;
import com.bc.managers.IoManager;

import java.util.List;

public class InvoiceReport {
    public static void main(String[] args) {
        List<Invoice> invoices = DatabaseObjectLoader.getInvoicesFromDatabase();
        if(invoices!=null){
            printSummary(invoices);
            for (Invoice invoice : invoices) {
                System.out.println(invoice.toString());
            }
        }
    }

    /**
     * this method is to print the summary of all the invoices
     * @param invoiceList a list of invoices that needs to be summarized
     */
    public static void printSummary(List<Invoice> invoiceList)
    {
        double subtotal = 0;
        double fees = 0;
        double taxes = 0;
        double discounts = 0;
        double total = 0;
        System.out.println("Executive summary report:");
        System.out.printf("%-10s%-30s%-32s%-12s%-12s%-12s%-12s%-12s%n","Code","Owner","Customer Account","Subtotal", "Discounts","Fees","Taxes","Total");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");


        for (Invoice i : invoiceList)
        {
            subtotal += i.getInvoiceSubtotal();
            fees += i.getInvoiceFees();
            taxes += i.getInvoiceTaxes();
            discounts += i.getInvoiceDiscount();
            total += i.getInvoiceTotal();
            System.out.printf("%-10s%-30s%-32s%-12.2f%-12.2f%-12.2f%-12.2f%-12.2f%n",i.getCode(),i.getOwner().getName(),i.getCustomer().getName(),i.getInvoiceSubtotal(),i.getInvoiceDiscount(),i.getInvoiceFees(),i.getInvoiceTaxes(),i.getInvoiceTotal());
        }
        System.out.println("================================================================================================================================");
        System.out.printf("%-72s%-12.2f%-12.2f%-12.2f%-12.2f%-12.2f%n","TOTALS",subtotal,discounts,fees,taxes,total);
    }
}
/*
Executive Summary Report:
Code      Owner                         Customer Account               Subtotal    Discounts   Fees        Taxes       Total
--------------------------------------------------------------------------------------------------------------------------------
INV001    Battle, Petra                 State of Nebraska             $   368.39  $   -63.00  $    75.50  $    12.98  $   393.87
INV002    Spooner, Kenan                Fenton Industries             $   908.50  $     0.00  $     0.00  $    72.68  $   981.18
INV003    Battle, Petra                 Juno Stephenson               $   120.00  $    -6.48  $     0.00  $     9.60  $   123.12
INV004    Medrano, Wiktoria             Demolition Derby              $  9145.00  $     0.00  $    75.50  $   388.66  $  9609.16
================================================================================================================================
TOTALS
 */