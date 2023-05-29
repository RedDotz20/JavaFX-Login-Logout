package loginlogout;

import connection.connectionUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController implements Initializable {

  @FXML
  private Button logoutButton;
  @FXML
  private Label headerText;

  private String user_id;
  private String username;
  private String password;

  private void navigateToLoginWindow() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
    Parent loginRoot = loader.load();
    LoginController loginController = loader.getController();
    loginController.setHomeController(this); // Pass the HomeController instance to the LoginController

    Stage primaryStage = (Stage) logoutButton.getScene().getWindow();
    primaryStage.setScene(new Scene(loginRoot));
    primaryStage.show();
  }

  public void setUserData(String user_id, String username, String password) {
    this.user_id = user_id;
    this.username = username;
    this.password = password;
    updateHeaderText();
  }

  private void updateHeaderText() {
    String firstCharacter = username.substring(0, 1).toUpperCase();
    String remainingCharacters = username.substring(1).toLowerCase();
    String formattedUsername = firstCharacter + remainingCharacters;
    headerText.setText("Welcome, " + formattedUsername + "!");
  }

  @FXML
  private void handleLogoutAction(ActionEvent event) throws IOException {
    alertMessage alert = new alertMessage();
    alert.successMessage("LOGOUT SUCCESSFUL");

    Stage primaryStage = (Stage) logoutButton.getScene().getWindow();
    Stage stage = (Stage) primaryStage.getScene().getWindow();
    stage.close();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
    Parent loginRoot = loader.load();
    LoginController loginController = loader.getController();
    loginController.setHomeController(this); // Pass the HomeController instance to the LoginController

    Stage loginStage = new Stage();
    loginStage.setScene(new Scene(loginRoot));
    loginStage.show();
  }

  @FXML
  private void handleDeleteAction(ActionEvent event) throws IOException, SQLException {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Delete Confirmation");
    alert.setContentText("Are you sure you want to Delete?");

    // Create the buttons
    ButtonType buttonTypeDelete = new ButtonType("Delete");
    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

    // Set the buttons in the alert
    alert.getButtonTypes().setAll(buttonTypeDelete, buttonTypeCancel);

    // Show the alert and wait for button click
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == buttonTypeDelete) {
      // Delete action
      deleteAction();
    } else {
      // Cancel action
      cancelAction();
    }
  }

  Connection connection = null;
  PreparedStatement preparedStatement = null;
  ResultSet resultSet = null;

  public HomeController() {
    try {
      connection = connectionUtil.connectdb();
    } catch (SQLException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void deleteAction() throws SQLException, IOException {
    String deleteUserQuery = "DELETE FROM users WHERE user_id = ?";
    preparedStatement = connection.prepareStatement(deleteUserQuery);
    preparedStatement.setString(1, user_id);
    int rowsAffected = preparedStatement.executeUpdate();

    alertMessage alert = new alertMessage();

    if (rowsAffected > 0) {
      alert.successMessage("User deleted successfully");
      navigateToLoginWindow();
    } else {
      alert.errorMessage("User not found or already deleted");
    }
  }

  private void cancelAction() {}

  @Override
  public void initialize(URL location, ResourceBundle rb) {

  }
}
