package com.example.kursach.classes;

public class CountryAndTransactionSum {

    private String country;
    private float sum;

    public CountryAndTransactionSum(String country, float sum) {
        this.country = country;
        this.sum = sum;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
