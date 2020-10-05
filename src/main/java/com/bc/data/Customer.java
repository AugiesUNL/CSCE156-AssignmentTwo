package com.bc.data;

public class Customer {

    private final String code;
    private final String name;
    private final Person contact;
    private final Address address;
    private final char type;

    public Customer(String code, char type, String name, Person contact, Address address) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Person getContact() {
        return contact;
    }

    public char getType() {
        return type;
    }

    @Override
    public String toString() {
        return
                name /n+ address;
    }
}
