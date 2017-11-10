package database;

import java.sql.*;

public class Mysql {

    private static String hostAdress = "37.233.99.37";
    private static String port = "3306";
    private static String database = "board_games_console";
    private static String user = "root";
    private static String password = "root";
    private static Statement statement;
    private static Connection connection;
    private static boolean connected = false;

    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + hostAdress + ":" + port + "/" + database + "", user, password);
            statement = connection.createStatement();
            connected = true;
        } catch (ClassNotFoundException ignored) {
        } catch (SQLException e) {
            try {
                connection.close();
                statement.close();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public static int executeUpdate(String query) throws SQLException {
        return statement.executeUpdate(query);
    }
}
