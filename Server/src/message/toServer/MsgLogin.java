package message.toServer;

import message.Message;

import java.io.Serializable;

public class MsgLogin implements Message, Serializable {

    private String login;
    private String password;

    public MsgLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public void execute() {

    }
}
