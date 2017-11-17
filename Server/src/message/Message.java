package message;

import game.*;
import server.*;

public abstract class Message {
    private User user;

    public abstract void execute();
    public Message sign(User user){
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
