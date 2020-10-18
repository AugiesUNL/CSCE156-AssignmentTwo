package com.bc.io.utils;

import com.bc.data.Customer;
import com.bc.data.Person;
import com.bc.data.products.Product;

import java.util.List;

public class Utils {

    /**
     * Iterate over a list of Strings and perform .trim on all them
     *
     * @param list the list to be trimmed
     */
    public static void trimContents(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).trim());
        }
    }

    /**
     * Returns a person with the given code
     *
     * @param people the list of people to check
     * @param code   the code to check for
     * @return the Person containing the code
     */
    public static Person getPersonWithCode(List<Person> people, String code) {
        for (Person person : people) {
            if (person.getCode().equals(code)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Returns a customer with the given code
     *
     * @param customers the list of customers to check
     * @param code      the code to check for
     * @return the Customer containing the code
     */
    public static Customer getCustomerWithCode(List<Customer> customers, String code) {
        for (Customer customer : customers) {
            if (customer.getCode().equals(code)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Returns a product with the given code
     *
     * @param products the list of products to check
     * @param code     the code to check for
     * @return the Product containing the code
     */
    public static Product getProductWithCode(List<Product> products, String code) {
        for (Product product : products) {
            if (product.getCode().equals(code)) {
                return product;
            }
        }
        return null;
    }
}
