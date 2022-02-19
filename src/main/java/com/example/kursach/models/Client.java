package com.example.kursach.models;


public class Client {

    private String first_name;
    private String last_name;
    private String user_name;
    private String password;
    private float money_som;
    private float money_dollar;


    public Client(String first_name, String last_name, String user_name,
                  String password, float money_som, float money_dollar) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.password = password;
        this.money_som = money_som;
        this.money_dollar = money_dollar;
//        if (!money_som.equals("")) {
//            this.money_som = Float.parseFloat(money_som);
//        } else {
//            this.money_som = 0;
//        }
//        if (!money_dollar.equals("")) {
//            this.money_dollar = Float.parseFloat(money_dollar);
//        } else {
//            this.money_dollar = 0;
//        }
    }

    public Client() {}

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String firstName) {
        this.first_name = firstName;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String lastName) {
        this.last_name = lastName;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String userName) {
        this.user_name = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passWord) {
        this.password = passWord;
    }

    public float getMoney_som() {
        return money_som;
    }

    public void setMoney_som(float moneySom) {
        this.money_som = moneySom;
    }

    public float getMoney_dollar() {
        return money_dollar;
    }

    public void setMoney_dollar(float moneyDollar) {
        this.money_dollar = moneyDollar;
    }
}
