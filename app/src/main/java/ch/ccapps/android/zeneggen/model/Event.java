package ch.ccapps.android.zeneggen.model;

import android.os.Parcel;
import android.os.Parcelable;
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
    @NonNull
    List<Participant> participants = new ArrayList<>();
    int anonymParticipants = 0;

    @NonNull
    List<Participant> nonParticipants = new ArrayList<>();
    int anonymNonParticipants = 0;



    public Event(){}

    public Event(long id, String location, String title, String description, Date start,
                 int nbrParticip, String organizedByName, String orgnizerPhone, List<Participant> participants, List<Participant> nonParticipants){
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

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public int getAnonymParticipants() {
        return anonymParticipants;
    }

    public void setAnonymParticipants(int anonymParticipants) {
        this.anonymParticipants = anonymParticipants;
    }

    public List<Participant> getNonParticipants() {
        return nonParticipants;
    }

    public void setNonParticipants(List<Participant> nonParticipants) {
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
        participants.add(new Participant("Shawna"));
        participants.add(new Participant("Nick"));
        participants.add(new Participant("Percy"));
        List<Participant> nonParticips = new ArrayList<>();
        nonParticips.add(new Participant("Cindy"));
        nonParticips.add(new Participant("Stef"));
        nonParticips.add(new Participant("Kelly"));
        events.add(new Event(1L,"Mehrzweckhalle","Unihockey Turnier", "", new Date(),3));
        events.add(new Event(2L,"Burgerhaus","SSC GV", "GV des Ski und Sport Clubs." +
                " Die GV hat dieses Jahr das Thema Doping." +
                " Die GV findet um 19:00 im Burgerhaus statt.", new Date(),0,"SSC Zeneggen","079 837 55 78",participants,
                nonParticips));
        events.add(new Event(3L,"Gemeindebüro","Abstimmungen", "", new Date(),10));
        events.add(new Event(4L,"Bistro","Cocktailparty", "", new Date(),1));
        return events;
    }

}
