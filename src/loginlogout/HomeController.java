package loginlogout;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController implements Initializable {

  @FXML
  private Button logoutButton;
  @FXML
  private Label headerText;
  private String username;

  public void setUserData(String username) {
    this.username = username;
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

  @Override
  public void initialize(URL location, ResourceBundle rb) {

  }
}
