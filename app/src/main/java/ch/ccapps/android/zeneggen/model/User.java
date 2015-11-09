package ch.ccapps.android.zeneggen.model;

import java.io.Serializable;

/**
 * Created by celineheldner on 09.11.15.
 */
public class User implements Serializable{
    String nickName = "";


    public User(){}

    public User(String name){}

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
