package message;

import java.io.Serializable;

public class MsgAnnouncement extends Message implements  Serializable {
    private String info;

    public MsgAnnouncement() {}

    public MsgAnnouncement(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void execute() {
        System.out.println("MsgAnnouncement:  " + info);
    }
}
