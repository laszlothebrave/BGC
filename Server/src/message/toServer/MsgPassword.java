package message.toServer;

import game.Engine;
import game.Game;
import message.Message;
import message.UserName;
import server.*;
import java.io.Serializable;

public class MsgPassword implements Serializable, Message {
    private String password;
    private UserName userName;

    public MsgPassword() {}

    public MsgPassword(UserName userName, String password) {
        this.password = password;
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public UserName getUserName() {
        return userName;
    }

    @Override
    public void execute() {

    }
}