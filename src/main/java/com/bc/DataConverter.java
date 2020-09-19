package com.bc;

import com.bc.managers.IoManager;

public class DataConverter {
    private static IoManager ioManager;

    public static void main(String[] args) {
        ioManager = new IoManager();
        ioManager.getPersonJsonHandler().savePeopleToJson();
        ioManager.getCustomerJsonHandler().saveCustomersToJson();
        ioManager.getProductJsonHandler().saveProductsToJson();
    }

    public static IoManager getIoManager() {
        return ioManager;
    }
}
