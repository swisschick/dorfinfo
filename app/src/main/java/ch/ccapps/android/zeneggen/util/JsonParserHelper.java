package ch.ccapps.android.zeneggen.util;

import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import ch.ccapps.android.zeneggen.model.Event;
import ch.ccapps.android.zeneggen.model.MobileResponse;

/**
 * Created by celineheldner on 05.07.16.
 */
public class JsonParserHelper {

    public static MobileResponse<List<Event>> parseEvents(String json){
        MobileResponse<List<Event>> events = new MobileResponse<List<Event>>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            events = mapper.readValue(json, new TypeReference<MobileResponse<List<Event>>>(){});
        } catch (Exception e) {
            //Log.e(JsonParserHelper.class.getSimpleName(),"error parsing",e);
            e.printStackTrace();
        }
        return events;
    }


}
