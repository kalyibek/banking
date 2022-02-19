package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.kursach.models.Client;
import com.example.kursach.models.RequestForCredit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RequestForCreditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address_field;

    @FXML
    private TextField amount_field;

    @FXML
    private Button back_button;

    @FXML
    private TextField city_field;

    @FXML
    private ToggleGroup currency;

    @FXML
    private RadioButton kgs_radio;

    @FXML
    private Button submit_request_button;

    @FXML
    private TextField trusted_person_field;

    @FXML
    private RadioButton usd_radio;


    DataBaseHandler dbHandler = new DataBaseHandler();
    Client this_client = new Client();;

    @FXML
    void initData(Client client) {

        initClient(client);


        back_button.setOnAction(actionEvent -> {
            load_client_window("index-client-view.fxml", this_client);
            back_button.getScene().getWindow().hide();
        });

        submit_request_button.setOnAction(actionEvent -> {

            String sum_text = amount_field.getText().trim();
            String trusted_person_text = trusted_person_field.getText();
            String address_text = address_field.getText();
            String city_text = city_field.getText();
            String currency;
            if (kgs_radio.isSelected()) { currency = "KGS"; }
            else { currency = "USD"; }

            boolean isFilled = (!sum_text.equals("") && !trusted_person_text.equals("") &&
                    !address_text.equals("") && !city_text.equals("") &&
                    (kgs_radio.isSelected() || usd_radio.isSelected()));

            if (isFilled) {
                float sum = Float.parseFloat(sum_text);
                RequestForCredit request = new RequestForCredit(
                        sum,
                        trusted_person_text,
                        address_text,
                        city_text,
                        currency,
                        this_client.getUser_name()
                );

                dbHandler.insertRequest(request);
                showAlert("Success", "Request made, wait until it will be accepted.");
                load_client_window("index-client-view.fxml", this_client);
                submit_request_button.getScene().getWindow().hide();
            } else {
                showAlert("ERROR", "Please fill the fields.");
            }
        });

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

    private void showAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void load_client_window(String url, Client client) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloController.class.getResource(url));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IndexClientController controller = loader.getController();
        try {
            controller.initData(client);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private void load_window(String url) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RequestForCreditController.class.getResource(url));

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
