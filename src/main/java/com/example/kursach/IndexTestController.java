package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.kursach.classes.*;
import com.example.kursach.models.Client;
import com.example.kursach.models.RequestForCredit;
import com.example.kursach.models.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class IndexTestController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane menu_tab;

    @FXML
    private Tab show_clients_tab;

    @FXML
    private Tab show_credits_tab;

    @FXML
    private Tab show_transactions_tab;

    @FXML
    private Tab show_credit_requests_tab;

    @FXML
    private TableView<Credit> credits_table;

    @FXML
    private TableColumn<Credit, Float> amount_column;

    @FXML
    private TableColumn<Credit, String> currency_column;

    @FXML
    private TableColumn<Credit, Date> date_column;

    @FXML
    private TableColumn<Credit, String> debtor_column;

    @FXML
    private Button exit_button;

    @FXML
    private TableView<Client> clients_table;

    @FXML
    private TableColumn<Client, String> first_name_column;

    @FXML
    private TableColumn<Client, String> last_name_column;

    @FXML
    private TableColumn<Client, Float> money_dollar_column;

    @FXML
    private TableColumn<Client, Float> money_som_column;

    @FXML
    private TableView<Transaction> transaction_table;

    @FXML
    private TableColumn<Transaction, Float> transaction_amount_column;

    @FXML
    private TableColumn<Transaction, String> transaction_city_column;

    @FXML
    private TableColumn<Transaction, String> transaction_code_column;

    @FXML
    private TableColumn<Transaction, String> transaction_country_column;

    @FXML
    private TableColumn<Transaction, String> transaction_currency_column;

    @FXML
    private TableColumn<Transaction, String> transaction_receiver_column;

    @FXML
    private TableColumn<Transaction, String> transaction_sender_column;

    @FXML
    private TableColumn<Transaction, Date> transaction_date_column;

    @FXML
    private TableView<MonthAndTransactionSum> each_month_transactions_sum_table;

    @FXML
    private TableColumn<MonthAndTransactionSum, String> month_column;

    @FXML
    private TableColumn<MonthAndTransactionSum, Float> sum_by_month_column;

    @FXML
    private TableView<QuarterAndTransactionSum> each_quarter_transactions_sum_table;

    @FXML
    private TableColumn<QuarterAndTransactionSum, Integer> quarter_column;

    @FXML
    private TableColumn<QuarterAndTransactionSum, Float> sum_by_quarter_column;

    @FXML
    private TableView<CountryAndTransactionSum> each_country_transactions_sum_table;

    @FXML
    private TableColumn<CountryAndTransactionSum, String> country_column;

    @FXML
    private TableColumn<CountryAndTransactionSum, Float> sum_by_country_column;

    @FXML
    private TableView<CityAndTransactionSum> each_city_transactions_sum_table;

    @FXML
    private TableColumn<CityAndTransactionSum, String> city_column;

    @FXML
    private TableColumn<CityAndTransactionSum, Float> sum_by_city_column;

    @FXML
    private TableView<RequestForCredit> credit_requests_table;

    @FXML
    private TableColumn<RequestForCredit, String> request_address_column;

    @FXML
    private TableColumn<RequestForCredit, String> request_city_column;

    @FXML
    private TableColumn<RequestForCredit, String> request_creditor_column;

    @FXML
    private TableColumn<RequestForCredit, String> request_currency_column;

    @FXML
    private TableColumn<RequestForCredit, Float> request_sum_column;

    @FXML
    private TableColumn<RequestForCredit, String> request_trusted_person_column;

    @FXML
    private TableColumn<RequestForCredit, Button> accept_button_column;

    @FXML
    private TableColumn<RequestForCredit, Button> reject_button_column;

    @FXML
    private Button refresh_button;

    @FXML
    private Button search_button;

    @FXML
    private TextField search_field;

    @FXML
    private Button max_button;

    @FXML
    private Button min_button;

    @FXML
    private Button make_transaction_button;

    @FXML
    private Button transactions_statistic_button;

    ObservableList<Client> clients_list = FXCollections.observableArrayList();
    ObservableList<Credit> credits_list = FXCollections.observableArrayList();
    ObservableList<Transaction> transactions_list = FXCollections.observableArrayList();
    ObservableList<MonthAndTransactionSum> month_and_transaction_sum_list = FXCollections.observableArrayList();
    ObservableList<QuarterAndTransactionSum> quarter_and_transaction_sum_list = FXCollections.observableArrayList();
    ObservableList<CountryAndTransactionSum> country_and_transaction_sum_list = FXCollections.observableArrayList();
    ObservableList<CityAndTransactionSum> city_and_transaction_sum_list = FXCollections.observableArrayList();
    ObservableList<RequestForCredit> credit_requests_list = FXCollections.observableArrayList();

    DataBaseHandler dbHandler = new DataBaseHandler();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        // ------ Вывод таблицы всех клиентов ------- //
        ResultSet clients_all_main = dbHandler.getClient();
        show_table_clients(clients_all_main);


        // ------ Вывод таблицы кредитов ------- //
        ResultSet credits_all_main = dbHandler.getCredits();
        show_table_credits(credits_all_main);


        // ------ Вывод таблицы транзакций ------- //
        each_month_transactions_sum_table.setVisible(false);
        each_quarter_transactions_sum_table.setVisible(false);
        each_country_transactions_sum_table.setVisible(false);
        each_city_transactions_sum_table.setVisible(false);
        transaction_table.setVisible(true);
        ResultSet transactions_all_main = dbHandler.getTransactions();
        show_table_transactions(transactions_all_main);


        // ------ Вывод таблицы запросов на кредит ------- //
        ResultSet requests = dbHandler.selectCreditRequests();
        show_table_requests(requests);


        // ------ Настройка кнопки выхода ------- //
        exit_button.setOnAction(actionEvent -> {
            load_window("hello-view.fxml");
            exit_button.getScene().getWindow().hide();
        });


        // ------ Настройка кнопки refresh ------- //
        refresh_button.setOnAction(actionEvent -> {
            try {
                if (show_clients_tab.isSelected()) {
                    clients_refresh();
                } else if (show_credits_tab.isSelected()) {
                    credits_refresh();
                } else if (show_transactions_tab.isSelected()) {
                    transactions_refresh();
                } else if (show_credit_requests_tab.isSelected()) {
                    requests_refresh();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        // ------ Настойка кнопки поиска ------- //
        search_button.setOnAction(actionEvent -> {
            try {
                if (show_clients_tab.isSelected()) {

                    Client client = new Client();
                    client.setFirst_name(search_field.getText());
                    ResultSet client_search = dbHandler.getClientSearch(client);
                    show_table_clients(client_search);
                } else if (show_credits_tab.isSelected()) {

                    Client client = new Client();
                    client.setFirst_name(search_field.getText());
                    ResultSet credit_search = dbHandler.getCreditSearch(client);
                    show_table_credits(credit_search);
                } else if (show_transactions_tab.isSelected()) {

                    Transaction transaction = new Transaction();
                    transaction.setReceiver(search_field.getText());
                    ResultSet transaction_search = dbHandler.getTransactionSearch(transaction);
                    show_table_transactions(transaction_search);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        // ------ Настойка кнопки max ------- //
        max_button.setOnAction(actionEvent -> {
            try {
                if (select_currency_alert().get().getText().equals("KGS")) {
                    ResultSet credit_max_kgs = dbHandler.getMaxCredit("KGS");
                    show_table_credits(credit_max_kgs);
                } else {
                    ResultSet credit_max_usd = dbHandler.getMaxCredit("USD");
                    show_table_credits(credit_max_usd);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        // ------ Настойка кнопки min ------- //
        min_button.setOnAction(actionEvent -> {
            try {
                if (select_currency_alert().get().getText().equals("KGS")) {
                    ResultSet credit_min_kgs = dbHandler.getMinCredit("KGS");
                    show_table_credits(credit_min_kgs);
                } else {
                    ResultSet credit_min_usd = dbHandler.getMinCredit("USD");
                    show_table_credits(credit_min_usd);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        // ------ Настойка кнопки make transaction ------- //
        make_transaction_button.setOnAction(actionEvent -> {
            load_window("transaction-make-view.fxml");
            make_transaction_button.getScene().getWindow().hide();
        });


        // ------ Настойка кнопки показа статистики ------- //
        transactions_statistic_button.setOnAction(actionEvent -> {
            show_sum_statistics_tables();
        });
    }

    private void show_table_clients(ResultSet clients) throws SQLException {

        clients_list.clear(); // ОЧИСТКА СПИСКА КЛИЕНТОВ

        while (clients.next()) {

            String first_name = clients.getString(2);
            String last_name = clients.getString(3);
            float money_som = clients.getFloat(6);
            float money_dollar = clients.getFloat(7);

            clients_list.add(new Client(first_name, last_name, null, null, money_som, money_dollar));
        }

        first_name_column.setCellValueFactory(new PropertyValueFactory<Client, String>("first_name"));
        last_name_column.setCellValueFactory(new PropertyValueFactory<Client, String>("last_name"));
        money_som_column.setCellValueFactory(new PropertyValueFactory<Client, Float>("money_som"));
        money_dollar_column.setCellValueFactory(new PropertyValueFactory<Client, Float>("money_dollar"));

        clients_table.setItems(clients_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ КЛИЕНТОВ
    }

    private void show_table_credits(ResultSet credits) throws SQLException {

        credits_list.clear(); // ОЧИСТКА СПИСКА КРЕДИТОВ

        while (credits.next()) {

            Float amount = credits.getFloat(1);
            String currency = credits.getString(2);
            Date credit_date = credits.getDate(3);
            String debtor = credits.getString(4);

            credits_list.add(new Credit(amount, currency, credit_date, debtor));
        }

        amount_column.setCellValueFactory(new PropertyValueFactory<Credit, Float>("amount"));
        currency_column.setCellValueFactory(new PropertyValueFactory<Credit, String>("currency"));
        date_column.setCellValueFactory(new PropertyValueFactory<Credit, Date>("date"));
        debtor_column.setCellValueFactory(new PropertyValueFactory<Credit, String>("debtor"));

        credits_table.setItems(credits_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ КРЕДИТОВ
    }


    private void show_table_transactions(ResultSet transactions) throws SQLException {

        transactions_list.clear(); // ОЧИСТКА СПИСКА ТРАНЗАКЦИЙ

        while (transactions.next()) {

            String country = transactions.getString(2);
            String city = transactions.getString(3);
            float amount = transactions.getFloat(4);
            String currency = transactions.getString(5);
            String sender = transactions.getString(6);
            String receiver = transactions.getString(7);
            String code = transactions.getString(8);
            Date transaction_date = transactions.getDate(9);

            transactions_list.add(new Transaction(country, city, amount, currency, sender, receiver, code, transaction_date));
        }

        transaction_country_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("country"));
        transaction_city_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("city"));
        transaction_amount_column.setCellValueFactory(new PropertyValueFactory<Transaction, Float>("amount"));
        transaction_currency_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("currency"));
        transaction_sender_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("sender"));
        transaction_receiver_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("receiver"));
        transaction_code_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("code"));
        transaction_date_column.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("date"));

        transaction_table.setItems(transactions_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ ТРАНЗАКЦИЙ
    }


    private void show_table_each_month_transactions() {

        month_and_transaction_sum_list.clear(); // ОЧИСТКА СПИСКА ТРАНЗАКЦИЙ ЗА КАЖДЫЙ МЕСЯЦ

        for (int month = 1; month <= 12; month++) {
            float result = dbHandler.getTransactionSumByMonth(month);
            month_and_transaction_sum_list.add(new MonthAndTransactionSum(month, result));
        }

        month_column.setCellValueFactory(new PropertyValueFactory<MonthAndTransactionSum, String>("month"));
        sum_by_month_column.setCellValueFactory(new PropertyValueFactory<MonthAndTransactionSum, Float>("sum"));

        each_month_transactions_sum_table.setItems(month_and_transaction_sum_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ ТРАНЗАКЦИЙ ЗА КАЖДЫЙ МЕСЯЦ
    }


    private void show_table_each_quarter_transactions() {

        quarter_and_transaction_sum_list.clear(); // ОЧИСТКА СПИСКА ТРАНЗАКЦИЙ ЗА КАЖДЫЙ КВАРТАЛ

        for (int quarter = 1; quarter <= 4; quarter++) {
            float result = dbHandler.getTransactionSumByQuarter(quarter);
            quarter_and_transaction_sum_list.add(new QuarterAndTransactionSum(quarter, result));
        }

       quarter_column.setCellValueFactory(new PropertyValueFactory<QuarterAndTransactionSum, Integer>("quarter"));
       sum_by_quarter_column.setCellValueFactory(new PropertyValueFactory<QuarterAndTransactionSum, Float>("sum"));

       each_quarter_transactions_sum_table.setItems(quarter_and_transaction_sum_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ ТРАНЗАКЦИЙ ЗА КАЖДЫЙ КВАРТАЛ
    }


    private void show_table_each_country_transactions(ResultSet sums_by_country) throws SQLException {

        country_and_transaction_sum_list.clear();

        while (sums_by_country.next()) {
            String country = sums_by_country.getString(1);
            float sum = sums_by_country.getFloat(2);

            country_and_transaction_sum_list.add(new CountryAndTransactionSum(country, sum));
        }

        country_column.setCellValueFactory(new PropertyValueFactory<CountryAndTransactionSum, String>("country"));
        sum_by_country_column.setCellValueFactory(new PropertyValueFactory<CountryAndTransactionSum, Float>("sum"));

        each_country_transactions_sum_table.setItems(country_and_transaction_sum_list);
    }


    private void show_table_each_city_transactions(ResultSet sums_by_city) throws SQLException {

        city_and_transaction_sum_list.clear();

        while (sums_by_city.next()) {
            String city = sums_by_city.getString(1);
            float sum = sums_by_city.getFloat(2);

            city_and_transaction_sum_list.add(new CityAndTransactionSum(city, sum));
        }

        city_column.setCellValueFactory(new PropertyValueFactory<CityAndTransactionSum, String>("city"));
        sum_by_city_column.setCellValueFactory(new PropertyValueFactory<CityAndTransactionSum, Float>("sum"));

        each_city_transactions_sum_table.setItems(city_and_transaction_sum_list);
    }


    private void show_table_requests(ResultSet requests) throws SQLException {

        credit_requests_list.clear();

        while (requests.next()) {
            float sum = requests.getFloat(1);
            String currency = requests.getString(2);
            String trusted_person = requests.getString(3);
            String address = requests.getString(4);
            String city = requests.getString(5);
            String user_name = requests.getString(6);
            String creditor_name = requests.getString(7);

            RequestForCredit request = new RequestForCredit();
            request.setCreditor(creditor_name);
            request.setSum(sum);
            request.setCurrency(currency);
            request.setTrusted_person(trusted_person);
            request.setAddress(address);
            request.setCity(city);
            request.setCreditor_user_name(user_name);

            Button button_accept = new Button("Accept");
            button_accept.setOnAction(actionEvent -> {
                dbHandler.insertCredit(request);
                dbHandler.deleteRequest(request);
                try {
                    requests_refresh();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            request.setButton_accept(button_accept);

            Button button_reject = new Button("Reject");
            button_reject.setOnAction(actionEvent -> {
                dbHandler.deleteRequest(request);
                try {
                    requests_refresh();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            request.setButton_reject(button_reject);


            credit_requests_list.add(request);
        }

        request_creditor_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, String>("creditor"));
        request_sum_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, Float>("sum"));
        request_currency_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, String>("currency"));
        request_trusted_person_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, String>("trusted_person"));
        request_address_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, String>("address"));
        request_city_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, String>("city"));
        accept_button_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, Button>("button_accept"));
        reject_button_column.setCellValueFactory(new PropertyValueFactory<RequestForCredit, Button>("button_reject"));

        credit_requests_table.setItems(credit_requests_list);
    }


    private void show_sum_statistics_tables() {

        transaction_table.setVisible(false);
        each_month_transactions_sum_table.setVisible(true);
        show_table_each_month_transactions();
        each_quarter_transactions_sum_table.setVisible(true);
        show_table_each_quarter_transactions();

        each_country_transactions_sum_table.setVisible(true);
        ResultSet sums_by_country = dbHandler.getTransactionsSumByCountry();
        try {
            show_table_each_country_transactions(sums_by_country);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        each_city_transactions_sum_table.setVisible(true);
        ResultSet sums_by_city = dbHandler.getTransactionsSumsByCity();
        try {
            show_table_each_city_transactions(sums_by_city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void clients_refresh() throws SQLException {

        ResultSet clients_all = dbHandler.getClient();
        show_table_clients(clients_all);
    }

    private void credits_refresh() throws SQLException, ClassNotFoundException {

        ResultSet credits_all = dbHandler.getCredits();
        show_table_credits(credits_all);
    }

    private void transactions_refresh() throws SQLException {

        ResultSet transactions_all = dbHandler.getTransactions();
        show_table_transactions(transactions_all);
        transaction_table.setVisible(true);
        each_month_transactions_sum_table.setVisible(false);
        each_quarter_transactions_sum_table.setVisible(false);
        each_country_transactions_sum_table.setVisible(false);
        each_city_transactions_sum_table.setVisible(false);
    }

    public void requests_refresh() throws SQLException {
        ResultSet requests_all = dbHandler.selectCreditRequests();
        show_table_requests(requests_all);
    }

    private Optional<ButtonType> select_currency_alert() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Select");
        alert.setHeaderText("Choose the currency:");

        ButtonType kgs = new ButtonType("KGS");
        ButtonType usd = new ButtonType("USD");

        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(kgs, usd);
        Optional<ButtonType> option = alert.showAndWait();

        return option;
    }

    private void load_window(String url) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(IndexTestController.class.getResource(url));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
