package loginlogout;

import connection.connectionUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

  @FXML
  private TextField textUser;
  @FXML
  private TextField textPassword;
  @FXML
  private TextField textConfirmPassword;
  @FXML
  private Button registerUserButton;
  @FXML
  private Button cancelButton;

  Connection connection = null;
  PreparedStatement preparedStatement = null;
  ResultSet resultSet = null;

  public RegisterController() {
    try {
      connection = connectionUtil.connectdb();
    } catch (SQLException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void handleRegisterAction(ActionEvent event) throws IOException {
    String username = textUser.getText();
    String password = textPassword.getText();
    String confirmPassword = textConfirmPassword.getText();
    String registerQuery
            = "INSERT INTO users (username, password)\n"
            + "SELECT ?, ?\n"
            + "WHERE NOT EXISTS (\n"
            + "   SELECT 1 FROM users WHERE username = ?\n"
            + ");";

    alertMessage alert = new alertMessage();

    try {
      if (username.isEmpty() || password.isEmpty()) {
        alert.errorMessage("Invalid Credentials, Please Try Again");
      } else if (!password.equals(confirmPassword)) {
        alert.errorMessage("Password does not match.");
      } else {
        preparedStatement = connection.prepareStatement(registerQuery);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, username);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 0) {
          alert.errorMessage("Username already exists. Please try a different username.");
        } else {
          alert.successMessage("Your new user account has been registered successfully.");
          Stage primaryStage = (Stage) registerUserButton.getScene().getWindow();
          Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
          primaryStage.setScene(scene);
          primaryStage.show();
        }
      }
    } catch (SQLException e) {
      alert.errorMessage("An error occurred: " + e.getMessage());
    }
  }

  @FXML
  private void handleCancelAction(ActionEvent event) throws IOException {
    Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    Platform.runLater(() -> textUser.requestFocus());

    textUser.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        textPassword.requestFocus();
      }
    });

    textPassword.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        textConfirmPassword.requestFocus();
      }
    });

    textConfirmPassword.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        try {
          handleRegisterAction(new ActionEvent());
        } catch (IOException ex) {
          Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });
  }
}
