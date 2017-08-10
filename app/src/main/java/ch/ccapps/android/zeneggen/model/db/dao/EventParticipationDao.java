package ch.ccapps.android.zeneggen.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import ch.ccapps.android.zeneggen.model.db.entity.EventParticipation;
import ch.ccapps.android.zeneggen.model.db.entity.Participant;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by celineheldner on 06.07.17.
 */
@Dao
public abstract class EventParticipationDao {

    public abstract List<Participant> findParticipantForEvent(long eventId);

    @Insert(onConflict = REPLACE)
    public abstract void insertOrReplaceEventparticipation(EventParticipation... participants);

    @Query("DELETE FROM EventParticipation WHERE EventParticipation.event_id LIKE :eventId")
    public abstract void deleteAllFromEvent(long eventId);


    public void replaceParticipantsForEvent(List<Participant> participants, long eventId) {
        insertOrReplaceParticipant((Participant[]) participants.toArray());

        List<EventParticipation> eventParticipations = new ArrayList<>();
        for (Participant part : participants) {
            eventParticipations.add(new EventParticipation(eventId, part.getParticipantId()));
        }
        deleteAllFromEvent(eventId);
        insertOrReplaceEventparticipation((EventParticipation[]) eventParticipations.toArray());
    }

    public void addParticipantForEvent(Participant participant, long eventId) {
        insertOrReplaceParticipant(participant);
        EventParticipation eventParticipation = new EventParticipation(eventId, participant.getParticipantId());
        insertOrReplaceEventparticipation(eventParticipation);
    }

    @Insert(onConflict = REPLACE)
    public abstract void insertOrReplaceParticipant(Participant... participants);
}
