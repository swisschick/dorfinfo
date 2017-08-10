package ch.ccapps.android.zeneggen.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.ccapps.android.zeneggen.model.db.entity.Participant;

/**
 * Created by celineheldner on 06.07.17.
 */

@Dao
public abstract class ParticipantDao {

    @Query("SELECT * From Participant " +
            "INNER JOIN EventParticipation ON EventParticipation.participantId = Participant.participantId " +
            "WHERE EventParticipation.event_id LIKE :eventId")
    public abstract List<Participant> findParticipantsOfEvent(long eventId);


}
