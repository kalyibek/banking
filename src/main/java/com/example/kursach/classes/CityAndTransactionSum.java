package com.example.kursach.classes;

public class CityAndTransactionSum {

    private String city;
    private float sum;

    public CityAndTransactionSum(String city, float sum) {
        this.city = city;
        this.sum = sum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
