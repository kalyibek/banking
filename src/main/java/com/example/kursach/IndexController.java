package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IndexController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private Button search_client_button;

    @FXML
    private Button transactions_button;

    ObservableList<Client> clients_list = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        // ------ Вывод таблицы клиентов ------- //
        show_table_clients();

        // ------ Настройка кнопки выхода ------- //
        exit_button.setOnAction(actionEvent -> {
            load_window("hello-view.fxml");
            exit_button.getScene().getWindow().hide();
        });


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

    private void show_table_clients() {

        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet result_clients = dbHandler.getClient();

        try {
            while (result_clients.next()) {

                String first_name = result_clients.getString(2);
                String last_name = result_clients.getString(3);
                String money_som = result_clients.getString(6);
                String money_dollar = result_clients.getString(7);

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
}

