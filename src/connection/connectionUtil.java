package connection;

import java.sql.*;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class connectionUtil {

  public static Connection connectdb() throws SQLException {
    Properties properties = new Properties();
    try {
      FileInputStream inputStream = new FileInputStream(".env");
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String dbHost = properties.getProperty("DB_HOST");
    String dbUser = properties.getProperty("DB_USER");
    String dbPassword = properties.getProperty("DB_PASSWORD");
    String url = "jdbc:mysql://" + dbHost + "/java-fx-app";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
      return conn;
    } catch (ClassNotFoundException e) {
      JOptionPane.showMessageDialog(null, e);
      return null;
    }
  }
}
