package ch.ccapps.android.zeneggen.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import ch.ccapps.android.zeneggen.model.User;

/**
 * Created by celineheldner on 06.07.17.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Participant.class,
                parentColumns = "participantId",
                childColumns = "participant_id"),

        @ForeignKey(entity = Event.class,
                parentColumns = "eventId",
                childColumns = "event_id")})
public class EventParticipation {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name="event_id")
    public long eventId;

    @ColumnInfo(name="participant_id")
    public long participantId;


    public EventParticipation(){}

    @Ignore
    public EventParticipation(long eventId, long participantId){
        super();
        this.eventId = eventId;
        this.participantId = participantId;
    }

}
