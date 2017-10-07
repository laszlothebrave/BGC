package message;

import game.Engine;
import game.Game;
import server.*;
import java.io.Serializable;

public class MsgPrivateMessage implements Message, Serializable {
    private UserName userName;
    private String message;

    public MsgPrivateMessage() {}

    public MsgPrivateMessage(String userName, String message) {
        this.userName = new UserName(userName);
        this.message = message;
    }

    public UserName getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
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
