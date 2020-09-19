package com.bc.io.handlers.json;

import com.bc.DataConverter;
import com.bc.data.Product;
import com.bc.data.containers.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProductsJsonHandler {
    private static final File PRODUCTS_JSON_FILE = new File("./data/Products.json");

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); //defaults should be fine

    /**
     * Saves the products to a json file
     */
    public void saveProductsToJson() {
        System.out.println("Saving to Products.json...");
        List<Product> products = DataConverter.getIoManager().getProductDatHandler().getProductsFromDat();
        try { //writing to file
            FileWriter fileWriter = new FileWriter(PRODUCTS_JSON_FILE);
            gson.toJson(new Products(products), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
