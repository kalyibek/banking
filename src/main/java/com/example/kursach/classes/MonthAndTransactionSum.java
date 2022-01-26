package com.example.kursach.classes;

public class MonthAndTransactionSum {

    private String month;
    private float sum;

    public MonthAndTransactionSum(int month, float sum) {
        initialize_month(month);
        this.sum = sum;
    }

    private void initialize_month(int month) {
        switch (month) {
            case 1 -> this.month = "January";
            case 2 -> this.month = "February";
            case 3 -> this.month = "March";
            case 4 -> this.month = "April";
            case 5 -> this.month = "May";
            case 6 -> this.month = "June";
            case 7 -> this.month = "July";
            case 8 -> this.month = "August";
            case 9 -> this.month = "September";
            case 10 -> this.month = "October";
            case 11 -> this.month = "November";
            case 12 -> this.month = "December";
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
