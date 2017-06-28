package ch.ccapps.android.zeneggen.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by celineheldner on 02.11.16.
 */

public class AppUser implements Serializable{
    //Used to identify others.
    private long id;
    private String email;

    //Used for registration and identification
    private String mobileUuid = UUID.randomUUID().toString();

    private String firstName;
    private String lastName;
    private String profileName;

    private String phoneNumber;


    private String secret;

    @JsonIgnore
    private boolean registered = false;

    public AppUser(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileUuid() {
        return mobileUuid;
    }

    public void setMobileUuid(String mobileUuid) {
        this.mobileUuid = mobileUuid;
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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public AppUserMobile createAppUserMobile(){
        AppUserMobile appUser = new AppUserMobile();
        appUser.setEmail(this.email);
        appUser.setProfileName(this.profileName);
        appUser.setFirstName(this.firstName);
        appUser.setLastName(this.lastName);
        appUser.setPhoneNumber(this.phoneNumber);

        return appUser;

    }

    public static AppUser createFakeUser(){
        AppUser user = new AppUser();
        user.setEmail("test@test.ch");
        user.setPhoneNumber("Testingxxx");
        return user;
    }

    public String writeToJson() {
        ObjectMapper om = new ObjectMapper();
        String userJson = "json creation did not work"+ this;
        try {
            userJson = om.writeValueAsString(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userJson;
    }
}
