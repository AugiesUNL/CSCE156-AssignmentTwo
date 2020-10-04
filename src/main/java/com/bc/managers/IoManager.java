package com.bc.managers;

import com.bc.io.handlers.dat.CustomerDatHandler;
import com.bc.io.handlers.dat.InvoiceDatHandler;
import com.bc.io.handlers.dat.PersonDatHandler;
import com.bc.io.handlers.dat.ProductDatHandler;
import com.bc.io.handlers.json.CustomerJsonHandler;
import com.bc.io.handlers.json.InvoiceJsonHandler;
import com.bc.io.handlers.json.PersonJsonHandler;
import com.bc.io.handlers.json.ProductsJsonHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A manager of all Input/Output operations
 */
public class IoManager {
    private final PersonDatHandler personDatHandler = new PersonDatHandler();
    private final PersonJsonHandler personJsonHandler = new PersonJsonHandler();

    private final CustomerDatHandler customerDatHandler = new CustomerDatHandler();
    private final CustomerJsonHandler customerJsonHandler = new CustomerJsonHandler();

    private final ProductDatHandler productDatHandler = new ProductDatHandler();
    private final ProductsJsonHandler productsJsonHandler = new ProductsJsonHandler();

    private final InvoiceDatHandler invoiceDatHandler = new InvoiceDatHandler();
    private final InvoiceJsonHandler invoiceJsonHandler = new InvoiceJsonHandler();

    public PersonDatHandler getPersonDatHandler() {
        return this.personDatHandler;
    }

    public PersonJsonHandler getPersonJsonHandler() {
        return this.personJsonHandler;
    }

    public CustomerDatHandler getCustomerDatHandler() {
        return this.customerDatHandler;
    }

    public CustomerJsonHandler getCustomerJsonHandler() {
        return this.customerJsonHandler;
    }

    public ProductDatHandler getProductDatHandler() {
        return productDatHandler;
    }

    public ProductsJsonHandler getProductsJsonHandler() {
        return productsJsonHandler;
    }

    public InvoiceDatHandler getInvoiceDatHandler() {
        return invoiceDatHandler;
    }

    public InvoiceJsonHandler getInvoiceJsonHandler() {
        return invoiceJsonHandler;
    }

    public String getContentsAsString(File file) {
        return getContentsAsString(file, false);
    }

    /**
     * Gets the contents of a file as a String
     * @param file the file whose contents needs retrieved
     * @param hasFirstLine whether or not to skip the first line
     * @return a string with the file's contents
     */
    public String getContentsAsString(File file, boolean hasFirstLine) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            if (hasFirstLine) {
                bufferedReader.readLine();
            }
            String nextLine;
            sb.append(bufferedReader.readLine()); //Do one iteration to make \n's easier
            while ((nextLine = bufferedReader.readLine()) != null) {
                sb.append("\n").append(nextLine);
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return sb.toString();
    }
}
