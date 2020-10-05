package com.bc.data.products;

public abstract class Product {
    private final String code;
    private final char type;
    private final String label;

    public Product(String code, char type, String label) {
        this.code = code;
        this.type = type;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public char getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", type=" + type +
                ", label='" + label + '\'' +
                '}';
    }
}
