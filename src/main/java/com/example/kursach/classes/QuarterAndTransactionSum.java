package com.example.kursach.classes;

public class QuarterAndTransactionSum {

    private int quarter;
    private float sum;

    public QuarterAndTransactionSum(int quarter, float sum) {
        this.quarter = quarter;
        this.sum = sum;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
