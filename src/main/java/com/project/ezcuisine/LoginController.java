package com.project.ezcuisine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private PasswordField password;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private Button loginBtn;

    public void initialize() {

        showPasswordCheckBox.setOnAction(this::handleShowPasswordCheckBox);
    }

    public void loginAdmin() {
        String sql = "SELECT * FROM login WHERE username = ? and password = ?";

        connect = database.connectDB();

        try {
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();
            Alert alert;

            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Fields are empty. Kindly fill the details");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully logged in!");
                    alert.showAndWait();

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userDashboard.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    @FXML
    private void handleShowPasswordCheckBox(ActionEvent event) {
        boolean showPassword = showPasswordCheckBox.isSelected();
        password.setDisable(showPassword);


        if (showPassword) {
            password.setPromptText(password.getText());
            password.setText("");


        } else {
            password.setPromptText("Enter your password");
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) (((Button) actionEvent.getSource()).getScene().getWindow());

        Parent loginView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));

        Scene scene = new Scene(loginView);
        stage.setTitle("EZCuisine");
        stage.setScene(scene);
    }
}



