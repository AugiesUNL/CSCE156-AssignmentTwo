package com.bc;

import com.bc.managers.IoManager;

public class DataConverter {
    private static final IoManager ioManager = new IoManager();

    public static void main(String[] args) {
        ioManager.getPersonJsonHandler().savePeopleToJson();
        ioManager.getCustomerJsonHandler().saveCustomersToJson();
        ioManager.getProductsJsonHandler().saveProductsToJson();
        ioManager.getInvoiceJsonHandler().saveInvoicesToJson();
    }

    public static IoManager getIoManager() {
        return ioManager;
    }
}