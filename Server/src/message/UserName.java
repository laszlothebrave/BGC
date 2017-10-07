package message;

import java.io.Serializable;

public class UserName implements Serializable {
    private String userName;

    public UserName() {}

    public UserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
