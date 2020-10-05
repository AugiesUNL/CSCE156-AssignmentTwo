package com.bc.io.handlers.dat;

import com.bc.DataConverter;
import com.bc.data.products.Product;
import com.bc.data.products.Concession;
import com.bc.data.products.Rental;
import com.bc.data.products.Repair;
import com.bc.data.products.Towing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDatHandler {
    private static final File PRODUCTS_DAT_FILE = new File("./data/Products.dat");

    /**
     * Deserializes a product
     *
     * @param productString the String to deserialize
     * @return the product object
     */
    public Product deserialize(String productString) {
        List<String> productData = Arrays.asList(productString.split(";"));

        Product product;
        switch (productData.get(1).toLowerCase()) {
            case "r": //rental
                product = new Rental(
                        productData.get(0),

                        productData.get(2),
                        Double.parseDouble(productData.get(3)),
                        Double.parseDouble(productData.get(4)),
                        Double.parseDouble(productData.get(5))
                );
                break;
            case "f": //repair
                product = new Repair(
                        productData.get(0),

                        productData.get(2),
                        Double.parseDouble(productData.get(3)),
                        Double.parseDouble(productData.get(4))
                );
                break;
            case "c": //concession
                product = new Concession(
                        productData.get(0),
                        productData.get(2),
                        Double.parseDouble(productData.get(3))
                );
                break;
            case "t": //towing
                product = new Towing(
                        productData.get(0),

                        productData.get(2),
                        Double.parseDouble(productData.get(3))
                );
                break;
            default:
                product = null;
                break;
        }
        return product;
    }

    /**
     * Gets all the Products from the Dat file
     * @return the list of the Dat file's products
     */
    public List<Product> getProductsFromDat() {
        System.out.println("Converting Products.dat...");
        List<Product> products = new ArrayList<>();
        String productsString = DataConverter.getIoManager().getContentsAsString(PRODUCTS_DAT_FILE, true);
        for (String productsEntry : productsString.split("\n")) {
            products.add(deserialize(productsEntry));
        }
        System.out.println("Converted!");
        return products;
    }
}
