package ch.ccapps.android.zeneggen.model.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.ccapps.android.zeneggen.model.db.DateConverter;

/**
 * Created by celineheldner on 08.09.15.
 */
@SuppressWarnings("DefaultFileTemplate")
@Entity
@TypeConverters(DateConverter.class)
public class Event implements Serializable{

    @PrimaryKey
    private long eventId;
    private Date startDate;
    private Date endDate;
    private String link;
    private String title;

    private String description;

    @Nullable
    String organizedBy;
    @Nullable
    String organizerPhone;
    @Nullable
    String organizerEmail;

    private boolean dayOnlyEvent;

    private String location;


    String imageName;

    public int anonymParticipants = 0;


    public Event(){}

    public Event(long id, String location, String title, String description, Date start,
                 int nbrParticip, String organizedByName, String orgnizerPhone, List<Participant> participants){
        this.location = location;
        this.title = title;
        this.description = description;
        this.startDate = start;
        this.anonymParticipants = nbrParticip;
        this.eventId = id;
        this.organizedBy = organizedByName;
        this.organizerPhone = orgnizerPhone;
    }

    public Event(long id, String location, String title, String description, Date start, int nbrParticip){
       this.location = location;
        this.title = title;
        this.description = description;
        this.startDate = start;
        this.anonymParticipants = nbrParticip;
        this.eventId = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @org.jetbrains.annotations.Nullable
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


    public int getAnonymParticipants() {
        return anonymParticipants;
    }

    public void setAnonymParticipants(int anonymParticipants) {
        this.anonymParticipants = anonymParticipants;
    }

    @Nullable
    public String getOrganizerPhone() {
        return organizerPhone;
    }

    public void setOrganizerPhone(String organizerPhone) {
        this.organizerPhone = organizerPhone;
    }

    @Nullable
    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isDayOnlyEvent() {
        return dayOnlyEvent;
    }

    public void setDayOnlyEvent(boolean dayOnlyEvent) {
        this.dayOnlyEvent = dayOnlyEvent;
    }

    @NonNull
    public static List<Event> initialEvents(){
        List<Event> events = new ArrayList<>();
        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant("Shawna",1));
        participants.add(new Participant("Nick",2));
        participants.add(new Participant("Percy",3));
        events.add(new Event(1L,"Mehrzweckhalle","Unihockey Turnier", "", new Date(),3));
        events.add(new Event(2L,"Burgerhaus","SSC GV", "GV des Ski und Sport Clubs." +
                " Die GV hat dieses Jahr das Thema Doping." +
                " Die GV findet um 19:00 im Burgerhaus statt.", new Date(),0,"SSC Zeneggen","079 837 55 78",participants));
        events.add(new Event(3L,"Gemeindebüro","Abstimmungen", "", new Date(),10));
        events.add(new Event(4L,"Bistro","Cocktailparty", "", new Date(),1));
        return events;
    }

}
