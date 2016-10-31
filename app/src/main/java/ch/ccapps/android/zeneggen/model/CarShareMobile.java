package ch.ccapps.android.zeneggen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by celineheldner on 21.06.16.
 */

public class CarShareMobile {

    public enum ShareState{OPEN,CLOSED}

    public List<AppUserMobile> drivers = new ArrayList<AppUserMobile>();
    private Date departure;
    private String fromLoc;
    private String toLoc;
    private String fromDescription;
    private String toDescription;
    private String explanation;

    private boolean isDriver;

    private ShareState state;

    private List<CarShareMessageMobile> messages = new ArrayList<CarShareMessageMobile>();

    public List<AppUserMobile> participants = new ArrayList<AppUserMobile>();


    public List<AppUserMobile> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<AppUserMobile> drivers) {
        this.drivers = drivers;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public String getFromLoc() {
        return fromLoc;
    }

    public void setFromLoc(String fromLoc) {
        this.fromLoc = fromLoc;
    }

    public String getToLoc() {
        return toLoc;
    }

    public void setToLoc(String toLoc) {
        this.toLoc = toLoc;
    }

    public String getFromDescription() {
        return fromDescription;
    }

    public void setFromDescription(String fromDescription) {
        this.fromDescription = fromDescription;
    }

    public String getToDescription() {
        return toDescription;
    }

    public void setToDescription(String toDescription) {
        this.toDescription = toDescription;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public ShareState getState() {
        return state;
    }

    public void setState(ShareState state) {
        this.state = state;
    }

    public List<CarShareMessageMobile> getMessages() {
        return messages;
    }

    public void setMessages(List<CarShareMessageMobile> messages) {
        this.messages = messages;
    }

    public List<AppUserMobile> getParticipants() {
        return participants;
    }

    public void setParticipants(List<AppUserMobile> participants) {
        this.participants = participants;
    }
}
