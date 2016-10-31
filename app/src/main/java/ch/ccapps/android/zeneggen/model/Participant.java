package ch.ccapps.android.zeneggen.model;

/**
 * Created by celineheldner on 24.08.16.
 */

public class Participant {
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
}
