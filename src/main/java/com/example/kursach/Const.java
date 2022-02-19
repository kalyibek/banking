package com.example.kursach;

import java.util.Stack;

public class Const {

    public static final String WORKER_TABLE = "workers";

    public static final String WORKERS_ID = "idworkers";
    public static final String WORKERS_FIRSTNAME = "firstname";
    public static final String WORKERS_LASTNAME = "lastname";
    public static final String WORKERS_USERNAME = "username";
    public static final String WORKERS_PASSWORD = "password";

    public static final String CLIENT_TABLE = "clients";

    public static final String CLIENTS_ID = "idclients";
    public static final String CLIENTS_FIRSTNAME = "firstname";
    public static final String CLIENTS_LASTNAME = "lastname";
    public static final String CLIENTS_USERNAME = "username";
    public static final String CLIENTS_PASSWORD = "password";
    public static final String CLIENTS_MONEYSOM = "moneysom";
    public static final String CLIENTS_MONEYDOLLAR = "moneydollar";

    public static final String CREDIT_TABLE = "credits";

    public static final String CREDIT_ID = "idcredits";
    public static final String CREDITS_AMOUNT = "sum";
    public static final String CREDITS_CURRENCY = "currency";
    public static final String CREDITS_DATE = "credit_date";
    public static final String CREDITS_CLIENT_ID = "client_id";

    public static final String TRANSACTION_TABLE = "transactions";

    public static final String TRANSACTION_ID = "idtransactions";
    public static final String TRANSACTION_COUNTRY = "country";
    public static final String TRANSACTION_CITY = "city";
    public static final String TRANSACTION_AMOUNT = "sum";
    public static final String TRANSACTION_CURRENCY = "currency";
    public static final String TRANSACTION_SENDER = "sender";
    public static final String TRANSACTION_RECEIVER = "receiver";
    public static final String TRANSACTION_CODE = "code";
    public static final String TRANSACTION_DATE = "date";

    public static final String CREDIT_REQUEST_TABLE = "credit_requests";

    public static final String REQUEST_ID = "idrequest";
    public static final String REQUEST_SUM = "sum";
    public static final String REQUEST_TRUSTED_PERSON = "trusted_person";
    public static final String REQUEST_ADDRESS = "address";
    public static final String REQUEST_CITY = "city";
    public static final String REQUEST_CURRENCY = "currency";
    public static final String REQUEST_CREDITOR_ID = "creditor_username";

}
