package ch.ccapps.android.zeneggen.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.model.AppUserMobile;
import ch.ccapps.android.zeneggen.model.CarShareMobile;
import ch.ccapps.android.zeneggen.util.ObjectSerializer;

/**
 * Created by celineheldner on 11.08.15.
 */
public class CarShareLocalStore {

    private static List<CarShareMobile> hotels;

    private static final String CARSHARE_PREFS = "CARSHARE_STORE";
    private static final String CARSHARE_KEY = "CARSHARE_KEY";

    @NonNull
    public static HashMap<String, HashMap<String, List<CarShareMobile>>> orderedCarSharesFromList(@NonNull List<CarShareMobile> carshares,
                                                                                                  AppUser mUser, Context ctxt){
        HashMap<String, HashMap<String, List<CarShareMobile>>> adapterData = new HashMap<String, HashMap<String, List<CarShareMobile>>>();
        for (CarShareMobile carShare : carshares){
            String myTrips = ctxt.getString(R.string.carshare_my_trips);
            String otherTrips = ctxt.getString(R.string.carshare_other_trips);
            if (mUser == null){
                adapterData.put(otherTrips, new HashMap<String, List<CarShareMobile>>());
            } else {
                AppUserMobile mUserM = mUser.createAppUserMobile();
                if (!carShare.getParticipants().contains(mUserM) && ! carShare.getDrivers().contains(mUserM)){
                    adapterData.put(otherTrips, new HashMap<String, List<CarShareMobile>>());
                } else {
                    adapterData.put(myTrips, new HashMap<String, List<CarShareMobile>>());
                }
            }

        }
        return adapterData;
    }

    @NonNull
    private static HashMap<String, HashMap<String, List<CarShareMobile>>> getDebugData(AppUser mUser,Context ctxt){
        return orderedCarSharesFromList(CarShareMobile.defaultCarShares(),mUser,ctxt);
    }

    public static void saveCarShares(Context ctxt, HashMap<String, HashMap<String, List<CarShareMobile>>> carshares){
        SharedPreferences preferences = ctxt.getSharedPreferences(CARSHARE_PREFS, Context.MODE_PRIVATE);
        try {
            String serializedHotels = ObjectSerializer.serialize(carshares);
            preferences.edit().putString(CARSHARE_KEY,serializedHotels).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, HashMap<String, List<CarShareMobile>>> retrieveCarShares(Context ctxt){
        SharedPreferences preferences = ctxt.getSharedPreferences(CARSHARE_PREFS, Context.MODE_PRIVATE);
        String carshareSerialize = preferences.getString(CARSHARE_KEY,null);
        HashMap<String, HashMap<String, List<CarShareMobile>>> carshares = null;
        if (carshareSerialize != null){
            try {
                carshares = (HashMap<String, HashMap<String, List<CarShareMobile>>>)ObjectSerializer.deserialize(carshareSerialize);
            } catch (Exception e) {
                Log.e("HotelLocalStore","Error fetching Hotel Local Store",e);
                e.printStackTrace();
            }
        } else {
            carshares = getDebugData(UserLocalStore.retrieveUser(ctxt), ctxt);
            saveCarShares(ctxt,carshares);
        }

        return carshares;
    }


}
