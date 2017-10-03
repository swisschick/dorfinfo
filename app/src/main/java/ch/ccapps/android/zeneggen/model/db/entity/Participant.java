package ch.ccapps.android.zeneggen.model.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by celineheldner on 24.08.16.
 */

@Entity
public class Participant implements Serializable{

    @PrimaryKey
    private long participantId;

    private String profileName;
    //private String mobileUuid;


    public Participant(){}

    @Ignore
    public Participant(String user, long id){
        this.profileName = user;
        this.participantId = id;
        //this.mobileUuid = user.getMobileUuid();
    }

    protected Participant(Parcel in) {
        profileName = in.readString();
    }


    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(long participantId) {
        this.participantId = participantId;
    }
}
