package ch.ccapps.android.zeneggen.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by celineheldner on 21.06.16.
 */

public class AppUserMobile implements Parcelable, Serializable {

    @NonNull
    private String email;
    private String firstName;
    private String lastName;

    //unique
    @NonNull
    private String profileName;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUserMobile that = (AppUserMobile) o;

        if (!email.equals(that.email)) return false;
        return profileName.equals(that.profileName);

    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + profileName.hashCode();
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUserMobile> CREATOR = new Creator<AppUserMobile>() {
        @Override
        public AppUserMobile createFromParcel(Parcel in) {
            return new AppUserMobile(in);
        }

        @Override
        public AppUserMobile[] newArray(int size) {
            return new AppUserMobile[size];
        }
    };

    public AppUserMobile(){}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(profileName);
        dest.writeString(phoneNumber);
    }

    private AppUserMobile(@NonNull Parcel in) {
        setEmail(in.readString());
        setFirstName(in.readString());
        setLastName(in.readString());
        setProfileName(in.readString());
        setPhoneNumber(in.readString());
    }
}