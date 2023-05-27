package connection;

import java.sql.*;
import javax.swing.*;

public class connectionUtil {
    public static Connection connectdb() {
        String url = "jdbc:mysql://localhost/java-fx-app";
        String user = "root";
        String password = "admin";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
