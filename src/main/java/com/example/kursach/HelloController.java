package com.example.kursach;

import com.example.kursach.models.Client;
import com.example.kursach.models.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton client_radio_button;

    @FXML
    private RadioButton worker_radio_button;

    @FXML
    private Button login_button;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button registration_button;

    @FXML
    private ImageView background_image;


    @FXML
    void initialize() {

        // ------- Настройка радио кнопок сотрудника и клиента ------- //
        worker_radio_button.setOnAction(actionEvent -> {
            client_radio_button.setSelected(false);
        });

        client_radio_button.setOnAction(actionEvent -> {
            worker_radio_button.setSelected(false);
        });


        // ------- Настройка кнопки входа в аккаунт ------- //
        login_button.setOnAction(actionEvent -> {
            String login_text = login_field.getText().trim();
            String password_text = password_field.getText().trim();

            if ((!login_text.equals("") && !password_text.equals("")) &&
                    (worker_radio_button.isSelected() || client_radio_button.isSelected())) {

                login_button.getScene().getWindow().hide();

                if (worker_radio_button.isSelected()) {
                    login_worker(login_text, password_text);
                } else {
                    login_client(login_text, password_text);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Пожалуйста, заполните поля.");
                alert.showAndWait();
            }
        });

        registration_button.setOnAction(actionEvent -> {
            registration_button.getScene().getWindow().hide();
            load_window("app.fxml");
        });
    }

    private void login_worker(String login_text, String password_text) {

        DataBaseHandler dbHandler = new DataBaseHandler();
        Worker worker = new Worker();
        worker.setUser_name(login_text);
        worker.setPassword(password_text);
        ResultSet result_worker = dbHandler.getWorker(worker);

        int counter_workers = 0;

        try {
            while (result_worker.next()) {
                counter_workers++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (counter_workers >= 1) {
            load_window("index_test.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            load_window("hello-view.fxml");
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Неверный логин или пароль, пожалуйста попробуйте еще.");
            alert.showAndWait();
        }
    }

    private void login_client(String login_text, String password_text) {

        DataBaseHandler dbHandler = new DataBaseHandler();
        Client client = new Client();
        client.setUser_name(login_text);
        client.setPassword(password_text);
        Client client_result = dbHandler.getClientLogin(client);

        System.out.println("login uaaa:" + client_result.getFirst_name() + "\n");

        if (client_result.getUser_name() != null) {
            load_client_window("index-client-view.fxml", client_result);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            load_window("hello-view.fxml");
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Неверный логин или пароль, пожалуйста попробуйте еще.");
            alert.showAndWait();
        }

    }

    private void load_window(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloController.class.getResource(url));

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

}
