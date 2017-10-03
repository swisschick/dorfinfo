package ch.ccapps.android.zeneggen.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.util.ImageCache;
import ch.ccapps.android.zeneggen.util.ObjectSerializer;

/**
 * Created by celineheldner on 11.08.15.
 */
public class EventLocalStore {

    private static List<Event> events;

    private static final String EVENT_STORE = "EVENT_STORE";
    private static final String EVENT_KEY = "EVENT_KEY";
    private Date date;

    @NonNull
    public static HashMap<String, HashMap<String, List<Event>>> orderedEventFromLocalStore(@NonNull List<Event> events) {
        HashMap<String, HashMap<String, List<Event>>> adapterData = new HashMap<String, HashMap<String, List<Event>>>();
        for (Event event : events) {
            Date date = event.getStartDate();
            String type = date.after(new Date()) ? "kommende" : "vorbei";
            if (!adapterData.containsKey(type)) {
                adapterData.put(type, new HashMap<String, List<Event>>());
            }
            if (!adapterData.get(type).containsKey(event.getLocation())) {

                adapterData.get(type).put(event.getLocation(), new ArrayList<Event>());
            }
            adapterData.get(type).get(event.getLocation()).add(event);
        }
        return adapterData;
    }

    @NonNull
    private static HashMap<String, HashMap<String, List<Event>>> getDebugData() {
        return orderedEventFromLocalStore(Event.initialEvents());
    }

    public static void saveHotelImages(HashMap<String, HashMap<String, List<Event>>> events, final Context ctxt) {
        for (HashMap<String, List<Event>> hotelmap : events.values()) {
            for (List<Event> eventList : hotelmap.values()) {
                for (final Event event : eventList) {
                    if (ImageCache.findImageFile(event.getImageName(), ImageCache.ImageType.EVENT, ctxt) == null) {
                        ImageLoader.getInstance().loadImage(Config.IF_FUTURE_EVENTS + event.getImageName(), new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                ImageCache.saveImageFileToCache(loadedImage, event.getImageName(), ImageCache.ImageType.HOTEL, ctxt);
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {
                            }
                        });
                    }
                }
            }
        }
    }

    public static void saveEvents(Context ctxt, HashMap<String, HashMap<String, List<Event>>> hotels) {
        SharedPreferences preferences = ctxt.getSharedPreferences(EVENT_STORE, Context.MODE_PRIVATE);
        try {
            String serializedHotels = ObjectSerializer.serialize(hotels);
            preferences.edit().putString(EVENT_KEY, serializedHotels).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, HashMap<String, List<Event>>> retrieveEvents(Context ctxt) {
        SharedPreferences preferences = ctxt.getSharedPreferences(EVENT_STORE, Context.MODE_PRIVATE);
        String eventsSerialized = preferences.getString(EVENT_KEY, null);
        HashMap<String, HashMap<String, List<Event>>> events = new HashMap<>();
        if (eventsSerialized != null) {
            try {
                events = (HashMap<String, HashMap<String, List<Event>>>) ObjectSerializer.deserialize(eventsSerialized);
            } catch (Exception e) {
                Log.e("EventLocalStore", "Error fetching Event Local Store", e);
                e.printStackTrace();
            }
        } else {
            events = getDebugData();
            saveEvents(ctxt, events);
        }

        return events;
    }


}
