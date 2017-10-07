package message;

import game.Engine;
import game.Game;
import server.*;
import java.io.Serializable;

public class MsgPassword implements Serializable, Message{
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
    public void execute(UserList userList, RoomList roomList) {

    }

    @Override
    public void execute(Game game) {

    }

    @Override
    public void execute(Engine engine) {

    }
}
