package ch.ccapps.android.zeneggen.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.model.db.DateConverter;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by celineheldner on 06.07.17.
 */
@Dao
@TypeConverters(DateConverter.class)
public abstract class EventDao {

    @Insert(onConflict = REPLACE)
    public abstract void insertEvent(Event event);

    @Insert(onConflict = REPLACE)
    public abstract void insertOrReplaceEvents(Event... events);

    @Query("SELECT * FROM event WHERE startDate >= :now LIMIT :maxNbr")
    public abstract List<Event> findEventsInFutureWithLimit(Date now, int maxNbr);

    @Query("SELECT * FROM event WHERE startDate < :now LIMIT :maxNbr")
    public abstract List<Event> findEventsInPastWithLimit(Date now, int maxNbr);

    public List<Event> findCloseToNowEvents(){
        Date now = new Date();
        int maxNbrEvents = 10;
        List<Event> events = findEventsInFutureWithLimit(now,maxNbrEvents);
        events.addAll(findEventsInPastWithLimit(now,maxNbrEvents));
        return events;
    }


}
