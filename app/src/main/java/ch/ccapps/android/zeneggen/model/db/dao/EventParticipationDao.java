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

    @Query("select * from EventParticipation where event_id = :eventId")
    public abstract List<EventParticipation> findParticipantForEvent(long eventId);

    @Insert(onConflict = REPLACE)
    public abstract void insertOrReplaceEventparticipation(EventParticipation... participants);

    @Query("DELETE FROM EventParticipation WHERE EventParticipation.event_id LIKE :eventId")
    public abstract void deleteAllFromEvent(long eventId);




    public void replaceParticipantsForEvent(List<Participant> participants, long eventId) {
        EventParticipation[] eventParticipations1 = new EventParticipation[participants.size()];
        for (int i = 0; i < participants.size(); i++) {
            eventParticipations1[i] = new EventParticipation(eventId, participants.get(i).getParticipantId());
        }
        deleteAllFromEvent(eventId);
        insertOrReplaceEventparticipation(eventParticipations1);
    }

    public void addParticipantForEvent(Participant participant, long eventId) {
        EventParticipation eventParticipation = new EventParticipation(eventId, participant.getParticipantId());
        insertOrReplaceEventparticipation(eventParticipation);
    }

}
