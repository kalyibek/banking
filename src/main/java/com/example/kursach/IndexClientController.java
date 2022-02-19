package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.kursach.models.Client;
import com.example.kursach.classes.Credit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class IndexClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab credits_tab;

    @FXML
    private Tab transactions_tab;

    @FXML
    private TableView<Credit> credits_table;

    @FXML
    private TableColumn<Credit, String> currency_column;

    @FXML
    private TableColumn<Credit, Date> date_column;

    @FXML
    private TableColumn<Credit, Float> amount_column;

    @FXML
    private TableColumn<Credit, Button> buttons_column;

    @FXML
    private ComboBox<String> clients_combo_box;

    @FXML
    private ToggleGroup currency;

    @FXML
    private Button submit_transaction_button;

    @FXML
    private TextField sum_field;

    @FXML
    private RadioButton usd_radio;

    @FXML
    private RadioButton kgs_radio;

    @FXML
    private Button exit_button;

    @FXML
    private Button request_for_credit_button;

    @FXML
    private Label first_name_label;

    @FXML
    private Label login_label;

    @FXML
    private Label kgs_money_label;

    @FXML
    private Label usd_money_label;

    @FXML
    private TabPane menu_tab;


    DataBaseHandler dbHandler = new DataBaseHandler();
    Client this_client = new Client();

    ObservableList<Credit> credits_list = FXCollections.observableArrayList();
    ObservableList<String> names_list = FXCollections.observableArrayList();


    @FXML
    public void initData(Client client) throws SQLException, ClassNotFoundException {

        initClient(client);
        refresh_client_data();

        ResultSet clients_names = dbHandler.getClientsNames();
        initClientNames(clients_names);


        ResultSet credits_result = dbHandler.getCreditByDebtor(this_client);
        show_table_credits(credits_result);


        exit_button.setOnAction(actionEvent -> {
            load_window("hello-view.fxml");
            exit_button.getScene().getWindow().hide();
        });

        request_for_credit_button.setOnAction(actionEvent -> {
            load_request_window("request-view.fxml", this_client);
            request_for_credit_button.getScene().getWindow().hide();
        });


        submit_transaction_button.setOnAction(actionEvent -> {
            String receiver_name = clients_combo_box.getValue();
            System.out.println(receiver_name);
            float sum = Float.parseFloat(sum_field.getText().trim());
            System.out.println(sum);
            String currency;
            if (kgs_radio.isSelected()) {
                currency = kgs_radio.getText().trim();
            } else {
                currency = usd_radio.getText().trim();
            }

            dbHandler.updateClientsCheck(
                    this_client,
                    receiver_name,
                    sum,
                    currency
            );
            showAlert("Success", "Transaction made!");
            refresh_transaction_tab();
            refresh_client_data();
        });
    }


    private void initClientNames(ResultSet names) throws SQLException {

        names_list.clear();

        while (names.next()) {

            String name = names.getString(1);
            names_list.add(name);
        }

        clients_combo_box.setItems(names_list);
    }


    private void show_table_credits(ResultSet credits) throws SQLException {

        credits_list.clear(); // ОЧИСТКА СПИСКА КРЕДИТОВ

        while (credits.next()) {

            Float amount = credits.getFloat(1);
            String currency = credits.getString(2);
            Date credit_date = credits.getDate(3);
            String debtor = credits.getString(4);

            Credit credit = new Credit(amount, currency, credit_date, debtor);
            credit.setRepay(new Button("action"));

            credits_list.add(credit);

        }

        amount_column.setCellValueFactory(new PropertyValueFactory<Credit, Float>("amount"));
        currency_column.setCellValueFactory(new PropertyValueFactory<Credit, String>("currency"));
        date_column.setCellValueFactory(new PropertyValueFactory<Credit, Date>("date"));
        buttons_column.setCellValueFactory(new PropertyValueFactory<Credit, Button>("repay"));

        credits_table.setItems(credits_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ КРЕДИТОВ
    }


    private void initClient(Client client) {

        this_client = new Client(
                client.getFirst_name(),
                client.getLast_name(),
                client.getUser_name(),
                client.getPassword(),
                client.getMoney_som(),
                client.getMoney_dollar()
        );
    }

    public void refresh_client_data() {
        first_name_label.setText(this_client.getFirst_name());
        login_label.setText(this_client.getUser_name());
        kgs_money_label.setText(this_client.getMoney_som() + " KGS");
        usd_money_label.setText(this_client.getMoney_dollar() + " USD");
    }

    public void refresh_transaction_tab() {
        clients_combo_box.setValue("");
        kgs_radio.setSelected(false);
        usd_radio.setSelected(false);
        sum_field.setText("");
    }

    private void showAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void load_request_window(String url, Client client) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloController.class.getResource(url));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestForCreditController controller = loader.getController();
        controller.initData(client);

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private void load_window(String url) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(IndexClientController.class.getResource(url));

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

