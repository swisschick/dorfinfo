package ch.ccapps.android.zeneggen.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.model.AppUserMobile;
import ch.ccapps.android.zeneggen.util.ObjectSerializer;

/**
 * Created by celineheldner on 11.08.15.
 */
public class UserLocalStore {

    private static AppUser _user;

    private static final String USER_STORE = "USER_STORE";
    private static final String USER_KEY = "USER_KEY";


    public static void saveUser(Context ctxt,AppUser mobileUser){
        SharedPreferences preferences = ctxt.getSharedPreferences(USER_STORE, Context.MODE_PRIVATE);
        _user = mobileUser;
        try {
            String serializedHotels = ObjectSerializer.serialize(mobileUser);
            preferences.edit().putString(USER_KEY,serializedHotels).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AppUser retrieveUser(Context ctxt){
        SharedPreferences preferences = ctxt.getSharedPreferences(USER_STORE, Context.MODE_PRIVATE);
        String hotelSerialized = preferences.getString(USER_KEY,null);

        if (_user == null){
            try {
                _user = (AppUser)ObjectSerializer.deserialize(hotelSerialized);
            } catch (Exception e) {
                Log.e("HotelLocalStore","Error fetching Hotel Local Store",e);
                e.printStackTrace();
            }
        }
        if (_user == null){
            AppUser user = AppUser.createFakeUser();
            saveUser(ctxt, user);
        }

        return _user;
    }


}
