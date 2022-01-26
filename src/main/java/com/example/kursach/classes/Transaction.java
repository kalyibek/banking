package com.example.kursach.classes;

import java.sql.Date;

public class Transaction {

    private String country;
    private String city;
    private float amount;
    private String currency;
    private String sender;
    private String receiver;
    private String code;
    private Date date;

    public Transaction(String country, String city, float amount, String currency, String sender, String receiver, String code, Date date) {

        this.country = country;
        this.city = city;
        this.amount = amount;
        this.currency = currency;
        this.sender = sender;
        this.receiver = receiver;
        this.code = code;
        this.date = date;
    }

    public Transaction() {}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
