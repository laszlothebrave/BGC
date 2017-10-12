package message.toServer;

import Database.MysqlConnector;
import Database.UserExceptions.IncorrectEmailAdressException;
import message.Message;

import java.sql.SQLException;

public class MsgCreateAcount implements Message {
    String login;
    String passwordHash;
    String email;

    public MsgCreateAcount(String login, String passwordHash, String email) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    @Override
    public void execute() {
        MysqlConnector mysql = new MysqlConnector("37.233.99.37", "3306", "board_games_console", "root", "root");
        try {
            mysql.addUser(login,passwordHash,email);
        } catch (SQLException | IncorrectEmailAdressException e) {
            e.printStackTrace();
        }
    }
}
