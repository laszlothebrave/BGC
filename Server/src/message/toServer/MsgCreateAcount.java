package message.toServer;

import Database.AccountManager;
import Database.PasswordHash;
import Database.UserExceptions.CreateUserExcaptions.CreateUserException;
import message.Message;
import server.Server;

import java.io.Serializable;

public class MsgCreateAcount implements Message, Serializable {

    private String login;
    private String passwordHash;
    private String email;

    public MsgCreateAcount(String login, String password, String email) {
        this.login = login;
        this.passwordHash = new PasswordHash(password).toString();
        this.email = email;
    }

    @Override
    public void execute() {
        try {
            Server.accountManager.createAccount(login, passwordHash, email);
        } catch (CreateUserException e) {
            e.printStackTrace();
        }
    }
}
