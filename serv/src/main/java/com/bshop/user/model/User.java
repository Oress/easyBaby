package com.bshop.user.model;

import com.bshop.contactInfo.model.ContactInfo;
import com.bshop.order.model.Order;
import com.bshop.preorder.model.Preorder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "USERT")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USR_ID")
    private Integer id;

    @Column(name = "USR_NAME", unique = true)
    private String username;

    @Column(name = "USR_PWD")
    private String pwd;

    @Column(name = "USR_IS_STAFF")
    private Boolean isStaff;

    @OneToOne
    private ContactInfo contactInfo;

    @CreationTimestamp
    private Date registrationDate;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @OneToMany(mappedBy = "recipient")
    private List<Order> orders;

    @OneToMany(mappedBy = "recipient")
    private List<Preorder> preorders;


    public User() {
    }

    public User(Integer id, String username, String pwd, Boolean isStaff, ContactInfo contactInfo, Date registrationDate, List<Authority> authorities, List<Order> orders, List<Preorder> preorders) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.isStaff = isStaff;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
        this.authorities = authorities;
        this.orders = orders;
        this.preorders = preorders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Boolean getStaff() {
        return isStaff;
    }

    public void setStaff(Boolean staff) {
        isStaff = staff;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Preorder> getPreorders() {
        return preorders;
    }

    public void setPreorders(List<Preorder> preorders) {
        this.preorders = preorders;
    }
}
