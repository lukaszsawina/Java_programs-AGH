import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/test"; // Zmień na odpowiedni adres i port
        String user = "lukis";
        String password = "Okokiok-01";

        // Proba nawiązania połączenia
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Połączono z bazą danych!");

            // Tutaj możesz wykonywać operacje na bazie danych

        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
