package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

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

    ObservableList<Client> clients_list = FXCollections.observableArrayList();
    ObservableList<Credit> credits_list = FXCollections.observableArrayList();
    ObservableList<Transaction> transactions_list = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        DataBaseHandler dbHandler = new DataBaseHandler();

        // ------ Вывод таблицы всех клиентов ------- //
        ResultSet clients_all_main = dbHandler.getClient();
        show_table_clients(clients_all_main);


        // ------ Вывод таблицы кредитов ------- //
        ResultSet credits_all_main = dbHandler.getCredits();
        show_table_credits(credits_all_main);


        // ------ Вывод таблицы транзакций ------- //
        ResultSet transactions_all_main = dbHandler.getTransactions();
        show_table_transactions(transactions_all_main);


        // ------ Настройка кнопки выхода ------- //
        exit_button.setOnAction(actionEvent -> {
            load_window("hello-view.fxml");
            exit_button.getScene().getWindow().hide();
        });


        // ------ Настройка кнопки refresh ------- //
        refresh_button.setOnAction(actionEvent -> {
            try {
                if (show_clients_tab.isSelected()) {
                    ResultSet clients_all = dbHandler.getClient();
                    show_table_clients(clients_all);
                } else if (show_credits_tab.isSelected()) {
                    ResultSet credits_all = dbHandler.getCredits();
                    show_table_credits(credits_all);
                } else if (show_transactions_tab.isSelected()) {
                    ResultSet transactions_all = dbHandler.getTransactions();
                    show_table_transactions(transactions_all);
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

    }

    private void show_table_clients(ResultSet clients) throws SQLException {

        clients_list.clear(); // ОЧИСТКА СПИСКА КЛИЕНТОВ

        while (clients.next()) {

            String first_name = clients.getString(2);
            String last_name = clients.getString(3);
            String money_som = clients.getString(6);
            String money_dollar = clients.getString(7);

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
            String amount = transactions.getString(4);
            String currency = transactions.getString(5);
            String sender = transactions.getString(6);
            String receiver = transactions.getString(7);
            String code = transactions.getString(8);

            transactions_list.add(new Transaction(country, city, amount, currency, sender, receiver, code));
        }

        transaction_country_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("country"));
        transaction_city_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("city"));
        transaction_amount_column.setCellValueFactory(new PropertyValueFactory<Transaction, Float>("amount"));
        transaction_currency_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("currency"));
        transaction_sender_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("sender"));
        transaction_receiver_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("receiver"));
        transaction_code_column.setCellValueFactory(new PropertyValueFactory<Transaction, String>("code"));

        transaction_table.setItems(transactions_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ ТРАНЗАКЦИЙ
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
