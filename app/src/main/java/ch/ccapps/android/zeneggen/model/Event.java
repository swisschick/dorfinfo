package ch.ccapps.android.zeneggen.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by celineheldner on 08.09.15.
 */
public class Event implements Serializable{
    String location;
    String eventId;
    Date startDate;
    Date endDate;
    String description;
    String title;
    String organizedBy;
    int nbrParticipants;
    int nbrNotPresant;

    public Event(){}

    public Event(String id, String location, String title, String description, Date start, int nbrParticip){
       this.location = location;
        this.title = title;
        this.description = description;
        this.startDate = start;
        this.nbrParticipants = nbrParticip;
        this.eventId = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getNbrNotPresant() {
        return nbrNotPresant;
    }

    public void setNbrNotPresant(int nbrNotPresant) {
        this.nbrNotPresant = nbrNotPresant;
    }

    public int getNbrParticipants() {
        return nbrParticipants;
    }

    public void setNbrParticipants(int nbrParticipants) {
        this.nbrParticipants = nbrParticipants;
    }

    public String getOrganizedBy() {
        return organizedBy;
    }

    public void setOrganizedBy(String organizedBy) {
        this.organizedBy = organizedBy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static List<Event> initialEvents(){
        List<Event> events = new ArrayList<Event>();
        events.add(new Event("1","Mehrzweckhalle","Unihockey Turnier", "", new Date(),3));
        events.add(new Event("2","Burgerhaus","SSC GV", "GV des Ski und Sport Clubs", new Date(),0));
        events.add(new Event("3","Gemeindebüro","Abstimmungen", "", new Date(),10));
        events.add(new Event("4","Bistro","Cocktailparty", "", new Date(),1));
        return events;
    }
}
