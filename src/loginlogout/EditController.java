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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController implements Initializable {

  private String user_id;
  private String username;
  private String password;

  @FXML
  private TextField textUser;
  @FXML
  private PasswordField textPassword;
  @FXML
  private PasswordField textConfirmPassword;
  @FXML
  private Button editUserButton;
  @FXML
  private Button cancelButton;

  public void setUserData(String user_id, String username, String password) {
    this.user_id = user_id;
    this.username = username;
    this.password = password;
    setTextFieldDefault();
  }

  private void setTextFieldDefault() {
    textUser.setText(username);
    textPassword.setText(password);
  }

  Connection connection = null;
  PreparedStatement preparedStatement = null;
  ResultSet resultSet = null;

  public EditController() {
    try {
      connection = connectionUtil.connectdb();
    } catch (SQLException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void handleEditAction(ActionEvent event) throws IOException {
    alertMessage alert = new alertMessage();

    String newUsername = textUser.getText();
    String newPassword = textPassword.getText();
    String confirmPassword = textConfirmPassword.getText();

    if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
      alert.errorMessage("Please fill in all fields.");
      return;
    }

    if (!newPassword.equals(confirmPassword)) {
      alert.errorMessage("New password and confirm password does not match");
      return;
    }

    try {
      String updateQuery = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";
      preparedStatement = connection.prepareStatement(updateQuery);
      preparedStatement.setString(1, newUsername);
      preparedStatement.setString(2, newPassword);
      preparedStatement.setString(3, user_id);
      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        alert.successMessage("User data updated successfully!");

        username = newUsername;
        password = newPassword;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent homeRoot = loader.load();
        HomeController homeController = loader.getController();
        homeController.setUserData(user_id, username, password); // Pass the user data to HomeController
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.setScene(new Scene(homeRoot));
        primaryStage.show();

      } else {
        alert.errorMessage("Failed to update user data.");
      }

    } catch (SQLException e) {
      alert.errorMessage("An error occured: " + e.getMessage());
    }
  }

  @FXML
  private void handleCancelAction(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
    Parent homeRoot = loader.load();
    HomeController homeController = loader.getController();
    homeController.setUserData(user_id, username, password); // Pass the user data to HomeController
    Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
    primaryStage.setScene(new Scene(homeRoot));
    primaryStage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

}
