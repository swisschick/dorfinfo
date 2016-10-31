package ch.ccapps.android.zeneggen;

import org.codehaus.jackson.JsonParser;
import org.junit.Test;

import java.util.List;

import ch.ccapps.android.zeneggen.model.Event;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.util.JsonParserHelper;

import static org.junit.Assert.assertTrue;

/**
 * Created by celineheldner on 24.07.16.
 */
public class ParseTest {

    @Test
    public void testEventParsing(){
        String json = "{\n" +
                "\"success\": true,\n" +
                "\"errorCode\": null,\n" +
                "\"errorMessage\": null,\n" +
                "\"errorTitle\": null,\n" +
                "\"successMessage\": null,\n" +
                "\"result\": [\n" +
                "{\n" +
                "\"eventId\": 1,\n" +
                "\"startDate\": 1469548800000,\n" +
                "\"endDate\": 1469577600000,\n" +
                "\"link\": \"www.google.ch\",\n" +
                "\"title\": \"TPV-Fest Saas Grund\",\n" +
                "\"description\": \"Oberwalliser Tambouren und pfeiferfest\",\n" +
                "\"organizerPhone\": \"\",\n" +
                "\"location\": \"\",\n" +
                "\"organizedBy\": null,\n" +
                "\"organizerEmail\": \"\",\n" +
                "\"imageName\": \"9c0e40ba30e8603abe7b18d321884f6d.jpg\"," +
                "\"dayOnlyEvent\": true," +
                "\"participants\": [ ]," +
                "\"nonParticipants\": [ ]" +
                "}," +
                "{" +
                "\"eventId\": 2," +
                "\"startDate\": 1469980800000," +
                "\"endDate\": null," +
                "\"link\": \"\"," +
                "\"title\": \"Magusii-Fäscht\",\n" +
                "\"description\": \"Am 08. Juli 2017 findet unser alljährliches Magusiifäscht statt!! Ab 18:00 Uhr gibt es feine Grilladen zu geniessen.\",\n" +
                "\"organizerPhone\": \"\",\n" +
                "\"location\": \"Bistro Zeneggen\",\n" +
                "\"organizedBy\": null,\n" +
                "\"organizerEmail\": \"\",\n" +
                "\"imageName\": \"5c03e97f2be03d0ac5e716a92a96a754.jpg\",\n" +
                "\"dayOnlyEvent\": false,\n" +
                "\"participants\": [ ],\n" +
                "\"nonParticipants\": [ ]\n" +
                "}\n" +
                "]\n" +
                "}";

        MobileResponse<List<Event>> resp = JsonParserHelper.parseEvents(json);

        assertTrue(resp != null);
        assertTrue(resp.getResult() != null);
        System.out.println("events:"+resp.getResult().size());
        assertTrue(resp.getResult().size()==2);
    }
}
