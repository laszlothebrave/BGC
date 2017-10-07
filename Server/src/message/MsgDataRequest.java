package message;

import game.Engine;
import game.Game;
import server.*;
import java.io.Serializable;

public class MsgDataRequest implements Message, Serializable {

    public void execute(UserList userList, RoomList roomList) {

    }

    @Override
    public void execute(Game game) {

    }

    @Override
    public void execute(Engine engine) {

    }
}
