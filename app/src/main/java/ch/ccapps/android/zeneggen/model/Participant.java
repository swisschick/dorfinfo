package ch.ccapps.android.zeneggen.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by celineheldner on 24.08.16.
 */

public class Participant implements Serializable, Parcelable{
    private String profileName;
    //private String mobileUuid;


    public Participant(){}


    public Participant(String user){
        this.profileName = user;
        //this.mobileUuid = user.getMobileUuid();
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
