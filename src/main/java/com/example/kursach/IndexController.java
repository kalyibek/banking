package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.kursach.classes.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class IndexController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clients_show_button;

    @FXML
    private Button exit_button;

    @FXML
    private TableView<Client> clients_table;

    @FXML
    private TableColumn<Client, String> first_name_column;

    @FXML
    private TableColumn<Client, String> last_name_column;

    @FXML
    private TableColumn<Client, Double> money_dollar_column;

    @FXML
    private TableColumn<Client, Double> money_som_column;

    @FXML
    private TableColumn<Client, Integer> number_column;

    @FXML
    private TextField search_field;

    @FXML
    private Button search_client_button;

    @FXML
    private Button transactions_button;

    @FXML
    private Button credits_button;

    ObservableList<Client> clients_list = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        DataBaseHandler dbHandler = new DataBaseHandler();

        // ------ Настройка кнопки выхода ------- //
        exit_button.setOnAction(actionEvent -> {
            load_window("hello-view.fxml");
            exit_button.getScene().getWindow().hide();
        });

        // ------ Вывод таблицы всех клиентов ------- //
        ResultSet clients_all_main = dbHandler.getClient();
        show_table_clients(clients_all_main);
        clients_show_button.setOnAction(actionEvent -> {
            ResultSet clients_all_from_button = dbHandler.getClient();
            show_table_clients(clients_all_from_button);
        });

        // ------ Вывод таблицы найденных клиентов ------- //
        search_client_button.setOnAction(actionEvent -> {
            Client client = new Client();
            client.setFirst_name(search_field.getText());
            ResultSet client_search = dbHandler.getClientSearch(client);
            show_table_clients(client_search);
        });


        // ------ Вывод таблицы кредитов ------- //
        credits_button.setOnAction((actionEvent -> {
            load_window("index_test.fxml");
            credits_button.getScene().getWindow().hide();
        }));

    }

    private void show_table_clients(ResultSet clients) {

        clients_list.clear(); // ОЧИСТКА СПИСКА КЛИЕНТОВ

        try {
            while (clients.next()) {

                String first_name = clients.getString(2);
                String last_name = clients.getString(3);
                float money_som = clients.getFloat(6);
                float money_dollar = clients.getFloat(7);

                clients_list.add(new Client(first_name, last_name, null, null, money_som, money_dollar));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        first_name_column.setCellValueFactory(new PropertyValueFactory<Client, String>("first_name"));
        last_name_column.setCellValueFactory(new PropertyValueFactory<Client, String>("last_name"));
        money_som_column.setCellValueFactory(new PropertyValueFactory<Client, Double>("money_som"));
        money_dollar_column.setCellValueFactory(new PropertyValueFactory<Client, Double>("money_dollar"));

        clients_table.setItems(clients_list); // ОТОБРАЖЕНИЕ ТАБЛИЦЫ
    }

    private void load_window(String url) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(IndexController.class.getResource(url));

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

