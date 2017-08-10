package ch.ccapps.android.zeneggen.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.List;

import ch.ccapps.android.zeneggen.model.db.entity.Event;
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
