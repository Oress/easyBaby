package com.bshop.addressSetup.model;

import javax.persistence.*;

@Entity(name = "ADDRESS_SETUP")
public class AddressSetup {
    public static final String HOUSE_SEPARATOR = ",";

    @Id
    @GeneratedValue
    @Column(name = "ADR_S_ID")
    private Integer id;

    @Column(name = "ADR_S_CITY")
    private String city;

    @Column(name = "ADR_S_NAME")
    private String name;

    @Lob
    @Column(name = "ADR_S_HOUSE")
    private String houses;

    public AddressSetup() {
    }

    public AddressSetup(Integer id, String city, String name, String houses) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.houses = houses;
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

    public String getHouses() {
        return houses;
    }

    public void setHouses(String houses) {
        this.houses = houses;
    }
}
