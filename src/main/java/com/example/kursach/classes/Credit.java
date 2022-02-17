package com.example.kursach.classes;


import javafx.scene.control.Button;
import java.sql.Date;

public class Credit {

    private float amount;
    private String currency;
    private Date date;
    private String debtor;
    public Button repay;

    public Credit(float amount, String currency, Date date, String debtor) {
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.debtor = debtor;
    }

    public Credit() {}

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public Button getRepay() {
        return repay;
    }

    public void setRepay(Button repay) {
        this.repay = repay;
        this.repay.setOnAction(actionEvent -> {
            System.out.println("it works");
        });
    }



}
