package com.bc;

import com.bc.managers.IoManager;

public class DataConverter {
    private static final IoManager ioManager = new IoManager();

    public static void main(String[] args) {
        ioManager.getJsonHandler().savePeopleToJson();
        ioManager.getJsonHandler().saveCustomersToJson();
        ioManager.getJsonHandler().saveProductsToJson();
        ioManager.getJsonHandler().saveInvoicesToJson();
    }

    public static IoManager getIoManager() {
        return ioManager;
    }
}