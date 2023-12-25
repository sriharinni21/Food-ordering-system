package com.project.ezcuisine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class RegisterController {

    @FXML
    private TextField nameTextField;
    private Connection connect;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phonenumber;

    @FXML
    private Button nextButton;

    @FXML
    private CheckBox showPasswordCheckBox;

    private PreparedStatement prepare;

    @FXML
    public void initialize() {
        showPasswordCheckBox.setOnAction(this::handleShowPasswordCheckBox);
    }

    private void handleShowPasswordCheckBox(ActionEvent actionEvent) {
        boolean showPassword = showPasswordCheckBox.isSelected();

        if (showPassword) {
            passwordField.setPromptText(passwordField.getText());
            passwordField.setText("");
        } else {
            passwordField.setPromptText("Enter your password");
        }

        passwordField.setDisable(showPassword);
    }

    @FXML
    private void Register() throws SQLException {
        connect = database.connectDB();

        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String address = addressTextField.getText();
        String phoneNumberText = phonenumber.getText();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty() || phoneNumberText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fields are empty. Kindly fill in all the details.");
            alert.showAndWait();
            return;
        }

        try {
            int phno = Integer.parseInt(phoneNumberText);

            String insertQuery = "INSERT INTO login(username, email, password, address, phno) VALUES (?, ?, ?, ?, ?)";
            prepare = connect.prepareStatement(insertQuery);
            prepare.setString(1, name);
            prepare.setString(2, email);
            prepare.setString(3, password);
            prepare.setString(4, address);
            prepare.setInt(5, phno);

            int queryResult = prepare.executeUpdate();

            if (queryResult == 1) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText(null);
                successAlert.setContentText("Successfully registered!");
                successAlert.showAndWait();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userDashboard.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid phone number. Please enter a valid number.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (prepare != null) {
                prepare.close();
            }
        }
    }

    @FXML
    public void comeBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) (((Button) actionEvent.getSource()).getScene().getWindow());

        Parent loginView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));

        Scene scene = new Scene(loginView);
        stage.setTitle("EZCuisine");
        stage.setScene(scene);
    }
}
