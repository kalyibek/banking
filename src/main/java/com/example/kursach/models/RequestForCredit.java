package com.example.kursach.models;

import com.example.kursach.DataBaseHandler;
import com.example.kursach.IndexTestController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class RequestForCredit {

    private String creditor;
    private float sum;
    private String trusted_person;
    private String address;
    private String city;
    private String currency;
    private String creditor_user_name;
    private Button button_accept;
    private Button button_reject;

    public RequestForCredit(float sum, String trusted_person, String address, String city, String currency, String creditor_id) {
        this.sum = sum;
        this.trusted_person = trusted_person;
        this.address = address;
        this.city = city;
        this.currency = currency;
        this.creditor_user_name = creditor_id;
    }

    public RequestForCredit() {}

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getTrusted_person() {
        return trusted_person;
    }

    public void setTrusted_person(String trusted_person) {
        this.trusted_person = trusted_person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreditor_user_name() {
        return creditor_user_name;
    }

    public void setCreditor_user_name(String user_name) {
        this.creditor_user_name = user_name;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public Button getButton_accept() {
        return button_accept;
    }

    public void setButton_accept(Button button_accept) {
        this.button_accept = button_accept;
    }

    public Button getButton_reject() {
        return button_reject;
    }

    public void setButton_reject(Button button_reject) {
        this.button_reject = button_reject;
    }

}
