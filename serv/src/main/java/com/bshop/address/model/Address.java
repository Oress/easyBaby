package com.bshop.address.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue
    @Column(name = "ADR_ID")
    private Integer id;

    @Column(name = "ADR_CITY")
    private String city;

    @Column(name = "ADR_NAME")
    private String name;

    @Column(name = "ADR_HOUSE")
    private String house;

    @Column(name = "ADR_APARTMENT")
    private String apartment;

    public Address() {
    }

    public Address(Integer id, String city, String name, String house, String apartment) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.house = house;
        this.apartment = apartment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}
