package com.bc.model;

import java.util.Arrays;
import java.util.List;

/**
 * POJO representing an Address for easier object initialization
 */
public class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String country;

    public Address(String street, String city, String state, String zip, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /**
     * Converts a String into its Address Object
     *
     * @param string the string to convert
     * @return the {@link Address} representation of the String
     */
    public static Address fromString(String string) {
        List<String> addressData = Arrays.asList(string.split(","));
        return new Address(
                addressData.get(0).trim(), //The dat files aren't uniform in spacing
                addressData.get(1).trim(),
                addressData.get(2).trim(),
                addressData.get(3).trim(),
                addressData.get(4).trim()
        );
    }

    /**
     * Converts the Address to a String
     * 1 Blue Jays Way
     * Toronto, ON Canada M5V 1J1
     *
     * @return the String representation of the Address
     */
    public String toString() {
        return street + "\n        " + city + ", " + state + " " + country + " " + zip;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }
}
