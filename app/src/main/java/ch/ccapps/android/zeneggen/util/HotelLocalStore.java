package ch.ccapps.android.zeneggen.util;

import android.content.Intent;
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
    public static HashMap<String, HashMap<String, List<Hotel>>> getDebugData(){
        return orderedHotelDataFromList(Hotel.showHotels());
    }


}
