package com.bshop.contactInfo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name ="CONTACTINFO")
public class ContactInfo {

    @Id
    @GeneratedValue
    @Column(name = "CTI_ID")
    private Integer id;

    @Column(name = "CTI_FNAME")
    private String firstName;

    @Column(name = "CTI_MNAME")
    private String middleName;

    @Column(name = "CTI_LNAME")
    private String lastName;

    @Column(name = "CTI_PHN1")
    private String phone1;

    @Column(name = "CTI_PHN2")
    private String phone2;

    @Column(name = "CTI_DOB")
    private Date dob;

    @Column(name = "CTI_EMAIL")
    private String email;

    public ContactInfo() {
    }

    public ContactInfo(Integer id, String firstName, String middleName, String lastName, String phone1, String phone2, Date dob, String email) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.dob = dob;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
