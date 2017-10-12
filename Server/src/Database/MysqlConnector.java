package Database;

import Database.UserExceptions.IncorrectEmailAdressException;
import Database.UserExceptions.IncorrectLoginDataException;
import org.apache.commons.validator.routines.EmailValidator;


import java.sql.*;

public class MysqlConnector {
    private String hostAdress;
    private String port;
    private String database;
    private String user;
    private String password;
    private Statement statement;

    public MysqlConnector(String hostAdress, String port, String database, String user, String password) {

        this.hostAdress = hostAdress;
        this.database = database;
        this.user = user;
        this.password = password;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + hostAdress + ":" + port + "/" + database + "", user, password);
            statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void addUser(String user, String password, String email) throws SQLException, IncorrectEmailAdressException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IncorrectEmailAdressException();
        }
        PasswordHash hashedPassword = new PasswordHash(password);
        statement.executeUpdate("INSERT INTO users (login,password,email) VALUES ('" + user + "','" + hashedPassword + "','" + email + "')");

    }

    public void login(String login, String password) throws IncorrectLoginDataException {
        try {
            PasswordHash passwordHash = new PasswordHash(password);
            ResultSet correctPassowrd = statement.executeQuery("SELECT password FROM users WHERE login='" + login + "' AND password='" + passwordHash + "'");
            if (!correctPassowrd.isBeforeFirst()) {
                throw new IncorrectLoginDataException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
