package message;

import game.*;
import server.*;

public interface Message {
    void execute(UserList userList, RoomList roomList);
    void execute(Game game);
    void execute(Engine engine);
}
