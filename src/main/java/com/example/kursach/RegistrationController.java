package com.example.kursach;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.kursach.classes.Client;
import com.example.kursach.classes.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton client_radio_button;

    @FXML
    private RadioButton worker_radio_button;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button submit_registration_button;

    @FXML
    private Button back_button;

    @FXML
    void initialize() {

        DataBaseHandler dbHandler = new DataBaseHandler();

        back_button.setOnAction(actionEvent -> {

            back_button.getScene().getWindow().hide();
            load_window("hello-view.fxml");
        });

        worker_radio_button.setOnAction(actionEvent -> {
            client_radio_button.setSelected(false);
        });

        client_radio_button.setOnAction(actionEvent -> {
            worker_radio_button.setSelected(false);
        });

        submit_registration_button.setOnAction(actionEvent -> {

            String first_name_text = first_name_field.getText().trim();
            String last_name_text = last_name_field.getText().trim();
            String login_text = login_field.getText().trim();
            String password_text = password_field.getText().trim();

            if ((!first_name_text.equals("") && !last_name_text.equals("") &&
                !login_text.equals("") && !password_text.equals("")) &&
                    (worker_radio_button.isSelected() || client_radio_button.isSelected())) {

                if (worker_radio_button.isSelected()) {

                    Worker worker = new Worker(first_name_text, last_name_text, login_text, password_text);
                    dbHandler.registrate_worker(worker);
                } else {

                    String money_som = "", money_dollar = "";

                    TextInputDialog dialog_som = new TextInputDialog("");
                    dialog_som.setTitle("Сумма денег.");
                    dialog_som.setHeaderText("Пожалуйста, введите сумму ваших денег KGS.");
                    dialog_som.setContentText("Сом:");
                    Optional<String> result_som = dialog_som.showAndWait();
                    if (result_som.isPresent()) {
                        money_som = result_som.get();
                    }

                    TextInputDialog dialog_dollar = new TextInputDialog("");
                    dialog_dollar.setTitle("Сумма денег.");
                    dialog_dollar.setHeaderText("Пожалуйста, введите сумму ваших денег USD.");
                    dialog_dollar.setContentText("Доллар:");
                    Optional<String> result_dollar = dialog_dollar.showAndWait();
                    if (result_dollar.isPresent()) {
                        money_dollar = result_dollar.get();
                    }

                    Client client = new Client(
                            first_name_text,
                            last_name_text,
                            login_text,
                            password_text,
                            Float.parseFloat(money_som),
                            Float.parseFloat(money_dollar)
                    );

                    dbHandler.registrate_client(client);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Готово.");
                alert.setHeaderText(null);
                alert.setContentText("Регистрация прошла успешно.");
                alert.showAndWait();

                submit_registration_button.getScene().getWindow().hide();
                load_window("hello-view.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Пожалуйста, заполните поля.");
                alert.showAndWait();
            }
        });
    }

    private void load_window(String url) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RegistrationController.class.getResource(url));

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


