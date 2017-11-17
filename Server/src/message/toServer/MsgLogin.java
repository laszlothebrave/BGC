package message.toServer;

import database.PasswordHash;
import database.UserExceptions.IncorrectLoginDataException;
import message.Message;
import message.UserName;
import server.Server;

import java.io.Serializable;

public class MsgLogin extends Message implements Serializable {

    private String login;
    private String password;

    public MsgLogin(String login, String password) {
        this.login = login;
        this.password = new PasswordHash(password).toString();
    }

    @Override
    public void execute() {
        try {
            Server.accountManager.login(login, password);
            Server.userList.remove(getUser().getUserName());
            getUser().setUserName(new UserName(login));
            Server.userList.add(getUser().getUserName(), getUser());
        } catch (IncorrectLoginDataException e) {
            e.printStackTrace();
        }

    }
}
