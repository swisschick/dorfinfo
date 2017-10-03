package ch.ccapps.android.zeneggen.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.model.db.entity.Participant;
import ch.ccapps.dorfapp.EventCommon;
import ch.ccapps.dorfapp.ParticipantCommon;

/**
 * Created by celineheldner on 02.10.17.
 */

public class EventMobile implements Serializable {

    private Event event;
    private List<Participant> participants = new ArrayList<>();

    public EventMobile(EventCommon eventCommon){
        this.event = new Event();
        event.setAnonymParticipants(eventCommon.getAnonymParticipants());
        event.setDayOnlyEvent(eventCommon.isDayOnlyEvent());
        event.setDescription(eventCommon.getDescription());
        event.setEndDate(eventCommon.getEndDate());
        event.setEventId(eventCommon.getEventId());
        event.setImageName(eventCommon.getImageName());
        event.setLink(eventCommon.getLink());
        event.setLocation(eventCommon.getLocation());
        event.setOrganizedBy(eventCommon.getOrganizedBy());
        event.setOrganizerEmail(eventCommon.getOrganizerEmail());
        event.setOrganizerPhone(eventCommon.getOrganizerPhone());
        event.setStartDate(eventCommon.getStartDate());
        event.setTitle(eventCommon.getTitle());

        for (ParticipantCommon partCom : eventCommon.getParticipants()){
            participants.add(new Participant(partCom.getProfileName(),partCom.getParticipantId()));
        }
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
