package com.example.kursach;

import java.sql.*;

public class DataBaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbname;
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);

        return dbConnection;
    }

    public void registrate_worker(Worker worker) {

        String insert = "INSERT INTO " + Const.WORKER_TABLE + "(" +
                Const.WORKERS_FIRSTNAME + "," + Const.WORKERS_LASTNAME +
                "," + Const.WORKERS_USERNAME + "," + Const.WORKERS_PASSWORD + ")" +
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, worker.getFirst_name());
            prSt.setString(2, worker.getLast_name());
            prSt.setString(3, worker.getUser_name());
            prSt.setString(4, worker.getPassword());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void registrate_client(Client client) {

        String insert = "INSERT INTO " + Const.CLIENT_TABLE + "(" +
                Const.CLIENTS_FIRSTNAME + "," + Const.CLIENTS_LASTNAME +
                "," + Const.CLIENTS_USERNAME + "," + Const.CLIENTS_PASSWORD +
                "," + Const.CLIENTS_MONEYSOM + "," + Const.CLIENTS_MONEYDOLLAR + ")" +
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, client.getFirst_name());
            prSt.setString(2, client.getLast_name());
            prSt.setString(3, client.getUser_name());
            prSt.setString(4, client.getPassword());
            prSt.setFloat(5, client.getMoney_som());
            prSt.setFloat(6, client.getMoney_dollar());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getWorker(Worker worker) {

        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.WORKER_TABLE + " WHERE " +
                Const.WORKERS_USERNAME + "=? AND " + Const.WORKERS_PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, worker.getUser_name());
            prSt.setString(2, worker.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ResultSet getClient() {

        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.CLIENT_TABLE;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ResultSet getClientSearch(Client client) {

        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLIENTS_FIRSTNAME + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, client.getFirst_name());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ResultSet getCredits() throws SQLException, ClassNotFoundException {

        ResultSet resSet;

        String select = "SELECT " + Const.CREDITS_AMOUNT + "," +  Const.CREDITS_CURRENCY + "," + Const.CREDITS_DATE +
                "," + " CONCAT(" + Const.CLIENTS_FIRSTNAME + ", ' '," + Const.CLIENTS_LASTNAME + ") AS debtor FROM " +
                Const.CLIENT_TABLE + " INNER JOIN " + Const.CREDIT_TABLE + " ON " + Const.CLIENTS_ID + "=" + Const.CREDITS_CLIENT_ID;

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();

        return resSet;
    }

    public ResultSet getCreditSearch(Client client) throws SQLException, ClassNotFoundException {

        ResultSet resSet;

        String select = "SELECT " + Const.CREDITS_AMOUNT + "," +  Const.CREDITS_CURRENCY + "," + Const.CREDITS_DATE +
                "," + " CONCAT(" + Const.CLIENTS_FIRSTNAME + ", ' '," + Const.CLIENTS_LASTNAME + ") AS debtor FROM " +
                Const.CLIENT_TABLE + " INNER JOIN " + Const.CREDIT_TABLE + " ON " + Const.CLIENTS_ID + "=" + Const.CREDITS_CLIENT_ID +
                " WHERE " + Const.CLIENTS_FIRSTNAME + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, client.getFirst_name());
        resSet = prSt.executeQuery();

        return resSet;
    }


    public ResultSet getMaxCredit(String currency) throws SQLException, ClassNotFoundException {

        ResultSet resSet;

        String select = "SELECT " + Const.CREDITS_AMOUNT + "," +  Const.CREDITS_CURRENCY + "," + Const.CREDITS_DATE +
                "," + " CONCAT(" + Const.CLIENTS_FIRSTNAME + ", ' '," + Const.CLIENTS_LASTNAME + ") AS debtor FROM " +
                Const.CLIENT_TABLE + " INNER JOIN " + Const.CREDIT_TABLE + " ON " + Const.CLIENTS_ID + "=" +
                Const.CREDITS_CLIENT_ID + " WHERE " + Const.CREDITS_AMOUNT + " IN (SELECT MAX(" + Const.CREDITS_AMOUNT +
                ") FROM " + Const.CREDIT_TABLE + " WHERE " + Const.CREDITS_CURRENCY + "=?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, currency);
        resSet = prSt.executeQuery();

        return resSet;
    }


    public ResultSet getMinCredit(String currency) throws SQLException, ClassNotFoundException {

        ResultSet resSet;

        String select = "SELECT " + Const.CREDITS_AMOUNT + "," +  Const.CREDITS_CURRENCY + "," + Const.CREDITS_DATE +
                "," + " CONCAT(" + Const.CLIENTS_FIRSTNAME + ", ' '," + Const.CLIENTS_LASTNAME + ") AS debtor FROM " +
                Const.CLIENT_TABLE + " INNER JOIN " + Const.CREDIT_TABLE + " ON " + Const.CLIENTS_ID + "=" +
                Const.CREDITS_CLIENT_ID + " WHERE " + Const.CREDITS_AMOUNT + " IN (SELECT MIN(" + Const.CREDITS_AMOUNT +
                ") FROM " + Const.CREDIT_TABLE + " WHERE " + Const.CREDITS_CURRENCY + "=?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, currency);
        resSet = prSt.executeQuery();

        return resSet;
    }


    public void insertTransaction(Transaction transaction) {

        String insert = "INSERT INTO " + Const.TRANSACTION_TABLE + "(" + Const.TRANSACTION_COUNTRY + "," +
                Const.TRANSACTION_CITY + "," + Const.TRANSACTION_AMOUNT + "," + Const.TRANSACTION_CURRENCY + "," +
                Const.TRANSACTION_SENDER + "," + Const.TRANSACTION_RECEIVER + "," + Const.TRANSACTION_CODE +
                ") VALUES(?,?,?,?,?,?,?)";

        try {

            float amount_including_commission = transaction.getAmount();
            if (transaction.getCountry().equalsIgnoreCase("kyrgyzstan")) {
                amount_including_commission -= transaction.getAmount() / 100 * 1.5;
            } else {
                amount_including_commission -= transaction.getAmount() / 100 * 5;
            }

            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, transaction.getCountry());
            prSt.setString(2, transaction.getCity());
            prSt.setFloat(3, amount_including_commission);
            prSt.setString(4, transaction.getCurrency());
            prSt.setString(5, transaction.getSender());
            prSt.setString(6, transaction.getReceiver());
            prSt.setString(7,transaction.getCode());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTransactions() {

        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.TRANSACTION_TABLE;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ResultSet getTransactionSearch(Transaction transaction) {

        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.TRANSACTION_TABLE + " WHERE " + Const.TRANSACTION_RECEIVER + " LIKE ?";

        try {
            String search_str = "%" + transaction.getReceiver() + "%";
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, search_str);

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

}
