package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.example.kursach.classes.PasswordGenerator;
import com.example.kursach.models.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TransactionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amount_field;

    @FXML
    private Button back_button;

    @FXML
    private TextField city_field;

    @FXML
    private TextField country_field;

    @FXML
    private ToggleGroup currency;

    @FXML
    private RadioButton kgs_radio;

    @FXML
    private TextField receiver_field;

    @FXML
    private TextField sender_field;

    @FXML
    private Button submit_transaction_button;

    @FXML
    private RadioButton usd_radio;


    @FXML
    void initialize() {

        DataBaseHandler dbHandler = new DataBaseHandler();

        // ------ Настройка кнопки back ------- //
        back_button.setOnAction(actionEvent -> {
            load_window("index_test.fxml");
            back_button.getScene().getWindow().hide();
        });

        // ------ Настройка кнопки submit ------- //
        submit_transaction_button.setOnAction(actionEvent -> {

            //Объект для дальнейшего создания рандомного пароля
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .build();

            String country_text = country_field.getText().trim();
            String city_text = city_field.getText().trim();
            String amount_text = amount_field.getText().trim();
            String currency_text = "";
            if (usd_radio.isSelected()) { currency_text = usd_radio.getText().trim(); }
            else { currency_text = kgs_radio.getText().trim(); }
            String sender_text = sender_field.getText();
            String receiver_text = receiver_field.getText();
            String code_text = passwordGenerator.generate(8);
            Date transaction_date = new Date(System.currentTimeMillis());

            boolean isAllFilled = !(country_text.equals("") && city_text.equals("") &&
                    amount_text.equals("") && currency_text.equals("") && sender_text.equals("") &&
                    receiver_text.equals("") && code_text.equals("")) &&
                    (kgs_radio.isSelected() || usd_radio.isSelected());

            if (isAllFilled) {

                float amount_including_commission = checkCountry(country_text, amount_text);
                Transaction transaction = new Transaction(country_text, city_text, amount_including_commission,
                                                          currency_text, sender_text, receiver_text, code_text,
                                                          transaction_date);
                dbHandler.insertTransaction(transaction);
                showAlert("Success", "Transaction has made.");
                load_window("index_test.fxml");
                submit_transaction_button.getScene().getWindow().hide();
            } else {
                showAlert("ERROR", "Please, fill the fields.");
            }
        });
    }

    private float checkCountry(String  country, String amount_text) {

        float amount_including_commission;

        if (amount_text.equals("")) {
            amount_including_commission = 0;
        } else {
            amount_including_commission = Float.parseFloat(amount_text);
            if (country.equalsIgnoreCase("kyrgyzstan")) {
                amount_including_commission -= amount_including_commission / 100 * 1.5;
            } else {
                amount_including_commission -= amount_including_commission / 100 * 5;
            }
        }

        return amount_including_commission;
    }

    private void showAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void load_window(String url) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TransactionController.class.getResource(url));

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

