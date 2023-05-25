package com.loginlogout.frames;

import com.connection.connectionUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField textUser;

    @FXML
    private PasswordField textPassword;

    Stage dialogStage = new Stage();
    Scene scene;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public FXMLDocumentController() {
        connection = connectionUtil.connectdb();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
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
//                infoBox("Enter Correct Email and Password", "Failed", null);
                alert.errorMessage("Invalid Username or Password");
            } else {
//                infoBox("Login Successfull", "Success", null);
                alert.successMessage("Login Successfull!");
                Node source = (Node) event.getSource();
                dialogStage = (Stage) source.getScene().getWindow();
                dialogStage.close();
                scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLMenu.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
