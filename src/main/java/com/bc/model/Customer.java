package com.bc.model;

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


    /*
    Customer:
	State of Nebraska (Business Account)
	100 W Fake Street
	Albaquerque, NM USA 80706
     */
    @Override
    public String toString() {
        String accountDescription;
        switch ((getType() + "").toLowerCase()) {
            case "b":
                accountDescription = "(Business Account)";
                break;
            case "p":
                accountDescription = "(Personal Account)";
                break;
            default:
                accountDescription = "(Unknown Account)";
                break;
        }
        return String.format("Customer:%n%s %s%n%s", name, accountDescription, address.toString());
    }
}
