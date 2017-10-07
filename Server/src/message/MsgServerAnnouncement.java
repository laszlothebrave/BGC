package message;

import game.Engine;
import game.Game;
import server.*;
import java.io.Serializable;

public class MsgServerAnnouncement implements  Serializable, Message {
    private String info;

    public MsgServerAnnouncement() {}

    public MsgServerAnnouncement(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
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
