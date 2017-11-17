package message.toServer;

import database.UserExceptions.CreateUserExcaptions.InvalidConfirmationKeyException;
import message.Message;
import server.Server;

import java.io.Serializable;

public class MsgVerification extends Message implements Serializable{
    public String code;

    public MsgVerification(String code) {
        this.code = code;
    }

    @Override
    public void execute() {
        try {
            Server.accountManager.confirmEmail(getUser().getUserName().getUserName(), code);
        } catch (InvalidConfirmationKeyException e) {
            e.printStackTrace();
        }
    }
}
