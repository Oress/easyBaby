package com.bshop.payment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "PMT_ID")
    private Integer id;

    @Column(name = "PMT_TYPE")
    private PaymentType type;

    @Column(name = "PMT_CRD_NUM")
    private String cardNumber;

    @Column(name = "PMT_TRNSCN")
    private String transaction;

    @Column(name = "PMT_DONE")
    private Boolean done = Boolean.FALSE;

    public Payment() {
    }

    public Payment(Integer id, PaymentType type, String cardNumber, String transaction, Boolean done) {
        this.id = id;
        this.type = type;
        this.cardNumber = cardNumber;
        this.transaction = transaction;
        this.done = done;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}