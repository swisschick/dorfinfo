package ch.ccapps.android.zeneggen.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by celineheldner on 08.09.15.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Event implements Serializable{
    String location;
    String eventId;
    Date startDate;
    Date endDate;
    String description;
    String title;
    @NonNull
    List<User> participants = new ArrayList<>();
    int anonymParticipants = 0;

    @NonNull
    List<User> nonParticipants = new ArrayList<>();
    int anonymNonParticipants = 0;

    @Nullable
    String organizedBy;
    @Nullable
    String organizerPhone;
    @Nullable
    String organizerEmail;

    public Event(){}

    public Event(String id, String location, String title, String description, Date start,
                 int nbrParticip, String organizedByName, String orgnizerPhone, List<User> participants, List<User> nonParticipants){
        this.location = location;
        this.title = title;
        this.description = description;
        this.startDate = start;
        this.anonymParticipants = nbrParticip;
        this.eventId = id;
        this.organizedBy = organizedByName;
        this.organizerPhone = orgnizerPhone;
        this.participants.addAll(participants);
        this.nonParticipants.addAll(nonParticipants);
    }

    public Event(String id, String location, String title, String description, Date start, int nbrParticip){
       this.location = location;
        this.title = title;
        this.description = description;
        this.startDate = start;
        this.anonymParticipants = nbrParticip;
        this.eventId = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
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

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public int getAnonymParticipants() {
        return anonymParticipants;
    }

    public void setAnonymParticipants(int anonymParticipants) {
        this.anonymParticipants = anonymParticipants;
    }

    public List<User> getNonParticipants() {
        return nonParticipants;
    }

    public void setNonParticipants(List<User> nonParticipants) {
        this.nonParticipants = nonParticipants;
    }

    public int getAnonymNonParticipants() {
        return anonymNonParticipants;
    }

    public void setAnonymNonParticipants(int anonymNonParticipants) {
        this.anonymNonParticipants = anonymNonParticipants;
    }

    public int getNbrParticipants(){
        return this.anonymParticipants + this.participants.size();
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

    @NonNull
    public static List<Event> initialEvents(){
        List<Event> events = new ArrayList<>();
        List<User> participants = new ArrayList<>();
        participants.add(new User("Shawna"));
        participants.add(new User("Nick"));
        participants.add(new User("Percy"));
        List<User> nonParticips = new ArrayList<>();
        nonParticips.add(new User("Cindy"));
        nonParticips.add(new User("Stef"));
        nonParticips.add(new User("Kelly"));
        events.add(new Event("1","Mehrzweckhalle","Unihockey Turnier", "", new Date(),3));
        events.add(new Event("2","Burgerhaus","SSC GV", "GV des Ski und Sport Clubs." +
                " Die GV hat dieses Jahr das Thema Doping." +
                " Die GV findet um 19:00 im Burgerhaus statt.", new Date(),0,"SSC Zeneggen","079 837 55 78",participants,
                nonParticips));
        events.add(new Event("3","Gemeindebüro","Abstimmungen", "", new Date(),10));
        events.add(new Event("4","Bistro","Cocktailparty", "", new Date(),1));
        return events;
    }
}
