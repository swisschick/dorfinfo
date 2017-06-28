package ch.ccapps.android.zeneggen.model;

import java.util.Date;

/**
 * Created by celineheldner on 21.06.16.
 */

public class CarShareMessageMobile {

    private AppUserMobile sender;

    private Date sendingDate;
    private String message;

    public AppUserMobile getSender() {
        return sender;
    }

    public void setSender(AppUserMobile sender) {
        this.sender = sender;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
