package message;

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
    public void execute() {
        System.out.println("MsgServerAnnouncement:  " + info);
    }
}
