package com.bshop.productChar.model;

public enum ProductCharType{
    Str("Str"),
    Number("Number"),
    Date("Date"),
    Dimentions("Dimentions"),
    Interval("Interval");

    private String value;

    ProductCharType(String value) {
        this.value = value;
    }
}