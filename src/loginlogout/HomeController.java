package loginlogout;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController implements Initializable {

  @FXML
  private Button logoutButton;

  @FXML
  private void handleLogoutAction(ActionEvent event) throws IOException {
    alertMessage alert = new alertMessage();
    alert.successMessage("LOGOUT SUCCESSFUL");

    Stage primaryStage = (Stage) logoutButton.getScene().getWindow();

    Stage stage = (Stage) primaryStage.getScene().getWindow();
    stage.close();

    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle rb) {
//    throw new UnsupportedOperationException("Not supported yet.");
  }

}
