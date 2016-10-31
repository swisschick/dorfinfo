package ch.ccapps.android.zeneggen.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.adapter.model.SectionData;
import ch.ccapps.android.zeneggen.model.Hotel;

/**
 * Created by celineheldner on 11.08.15.
 */
public class HotelLocalStore {

    private static List<Hotel> hotels;

    private static final String HOTEL_PREFS = "HOTEL_STORE";
    private static final String HOTEL_KEY = "HOTEL_KEY";

    @NonNull
    public static HashMap<String, HashMap<String, List<Hotel>>> orderedHotelDataFromList(@NonNull List<Hotel> hotels){
        HashMap<String, HashMap<String, List<Hotel>>> adapterData = new HashMap<String, HashMap<String, List<Hotel>>>();
        for (Hotel hotel : hotels){
            for (String type : hotel.getType()){
                if (!adapterData.containsKey(type)){
                    adapterData.put(type, new HashMap<String, List<Hotel>>());
                }
                if (!adapterData.get(type).containsKey(hotel.getLocation())){
                    adapterData.get(type).put(hotel.getLocation(), new ArrayList<Hotel>());
                }
                adapterData.get(type).get(hotel.getLocation()).add(hotel);
            }
        }
        return adapterData;
    }

    @NonNull
    private static HashMap<String, HashMap<String, List<Hotel>>> getDebugData(){
        return orderedHotelDataFromList(Hotel.showHotels());
    }

    public static void saveHotels(Context ctxt,HashMap<String, HashMap<String, List<Hotel>>> hotels){
        SharedPreferences preferences = ctxt.getSharedPreferences(HOTEL_PREFS, Context.MODE_PRIVATE);
        try {
            String serializedHotels = ObjectSerializer.serialize(hotels);
            preferences.edit().putString(HOTEL_KEY,serializedHotels).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, HashMap<String, List<Hotel>>> retrieveHotels(Context ctxt){
        SharedPreferences preferences = ctxt.getSharedPreferences(HOTEL_PREFS, Context.MODE_PRIVATE);
        String hotelSerialized = preferences.getString(HOTEL_KEY,null);
        HashMap<String, HashMap<String, List<Hotel>>> hotels = null;
        if (hotelSerialized != null){
            try {
                hotels = (HashMap<String, HashMap<String, List<Hotel>>>)ObjectSerializer.deserialize(hotelSerialized);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            hotels = getDebugData();
            saveHotels(ctxt,hotels);
        }

        return hotels;
    }


}
