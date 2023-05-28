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
//import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginController implements Initializable {

  @FXML
  private TextField textUser;

  @FXML
  private PasswordField textPassword;

  @FXML
  private Button loginButton;

  @FXML
  private Button registerButton;

  Connection connection = null;
  PreparedStatement preparedStatement = null;
  ResultSet resultSet = null;

  public LoginController() {
    try {
      connection = connectionUtil.connectdb();
    } catch (SQLException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void handleLoginAction(ActionEvent event) throws IOException {
    String email = textUser.getText();
    String password = textPassword.getText();
    String sql = "SELECT * FROM users WHERE username = ? and password = ?";

    alertMessage alert = new alertMessage();

    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      resultSet = preparedStatement.executeQuery();
      if (!resultSet.next()) {
        alert.errorMessage("Invalid Username or Password");
      } else {
        alert.successMessage("LOGIN SUCCESSFUL");
//        Node source = (Node) event.getSource();

        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
//        primaryStage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
        primaryStage.setScene(scene);
        primaryStage.show();
      }

    } catch (SQLException e) {
      alert.errorMessage("An error occured: " + e.getMessage());
    }
  }

  @FXML
  private void handleRegisterAction(ActionEvent event) throws IOException {
//    Node source = (Node) event.getSource();
    Stage primaryStage = (Stage) registerButton.getScene().getWindow();

    Stage stage = (Stage) primaryStage.getScene().getWindow();
//    stage.close();

    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Register.fxml")));
    stage.setScene(scene);
    stage.show();
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
        try {
          handleLoginAction(new ActionEvent());
        } catch (IOException ex) {
          Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });

  }
}
