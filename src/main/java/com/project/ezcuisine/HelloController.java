package com.project.ezcuisine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void openRegister(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)(((Button)actionEvent.getSource()).getScene().getWindow());

        Parent loginView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));

        Scene scene = new Scene(loginView);
        stage.setTitle("Register");
        stage.setScene(scene);
    }

    public void openLogin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)(((Button)actionEvent.getSource()).getScene().getWindow());

        Parent loginView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));

        Scene scene = new Scene(loginView);
        stage.setTitle("Login");
        stage.setScene(scene);
    }
}