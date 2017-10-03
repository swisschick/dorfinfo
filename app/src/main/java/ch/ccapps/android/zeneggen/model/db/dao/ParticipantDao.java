package ch.ccapps.android.zeneggen.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import ch.ccapps.android.zeneggen.model.db.DateConverter;
import ch.ccapps.android.zeneggen.model.db.entity.Participant;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by celineheldner on 06.07.17.
 */

@Dao
@TypeConverters(DateConverter.class)
public abstract class ParticipantDao {

    @Query("SELECT * From Participant " +
            "INNER JOIN EventParticipation ON EventParticipation.participant_id = Participant.participantId " +
            "WHERE EventParticipation.event_id LIKE :eventId")
    public abstract List<Participant> findParticipantsOfEvent(long eventId);


    @Insert(onConflict = REPLACE)
    public abstract void insertOrReplaceParticipant(Participant... participants);

    @Query("SELECT *"
            + "FROM event, participant, eventparticipation "
            + "WHERE eventparticipation.event_id  = event.eventId AND eventparticipation.participant_id = participant.participantId")
    public abstract LiveData<List<EventWithParticipant>> loadEventsWithParticipants();

    public static class EventWithParticipant{
        public long eventId;
        public Date startDate;
        public Date endDate;
        public String link;
        public String title;
        public String description;
        public String organizedBy;
        public String organizerPhone;
        public String organizerEmail;
        public boolean dayOnlyEvent;
        public String location;
        public String imageName;
        public int anonymParticipants = 0;
        public long participantId;
        public String profileName;
    }


}
