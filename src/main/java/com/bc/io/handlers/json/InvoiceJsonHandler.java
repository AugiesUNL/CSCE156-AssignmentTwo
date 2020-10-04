package com.bc.io.handlers.json;

import com.bc.DataConverter;
import com.bc.data.Invoice;
import com.bc.data.containers.Invoices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InvoiceJsonHandler {
    private static final File INVOICES_JSON_FILE = new File("./data/Invoices.json");

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); //defaults should be fine

    /**
     * Saves the invoices to a json file
     */
    public void saveInvoicesToJson() {
        System.out.println("Saving to Invoices.json...");
        List<Invoice> invoices = DataConverter.getIoManager().getInvoiceDatHandler().getInvoicesFromDat();
        try { //writing to file
            FileWriter fileWriter = new FileWriter(INVOICES_JSON_FILE);
            gson.toJson(new Invoices(invoices), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
