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
}
