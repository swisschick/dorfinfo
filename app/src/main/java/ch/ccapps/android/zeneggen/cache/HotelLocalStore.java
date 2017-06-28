package ch.ccapps.android.zeneggen.cache;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.adapter.model.SectionData;
import ch.ccapps.android.zeneggen.model.Hotel;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.util.ImageCache;
import ch.ccapps.android.zeneggen.util.ObjectSerializer;

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

    public static void saveHotelImages(HashMap<String, HashMap<String, List<Hotel>>> hotels,  final Context ctxt){
        for (HashMap<String, List<Hotel>> hotelmap : hotels.values()){
            for (List<Hotel> hotellist : hotelmap.values()) {
                for (final Hotel hotel : hotellist) {
                    if (ImageCache.findImageFile(hotel.getImageName(), ImageCache.ImageType.HOTEL, ctxt) == null) {
                        ImageLoader.getInstance().loadImage(Config.IF_HOTELS_IMAGES + hotel.getImageName(), new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {}

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {}

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                ImageCache.saveImageFileToCache(loadedImage, hotel.getImageName(), ImageCache.ImageType.HOTEL, ctxt);
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {}
                        });

                    }
                }
            }
        }
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
                Log.e("HotelLocalStore","Error fetching Hotel Local Store",e);
                e.printStackTrace();
            }
        } else {
            hotels = getDebugData();
            saveHotels(ctxt,hotels);
        }

        return hotels;
    }


}
