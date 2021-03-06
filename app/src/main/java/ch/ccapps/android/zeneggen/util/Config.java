package ch.ccapps.android.zeneggen.util;

import ch.ccapps.android.zeneggen.BuildConfig;

public class Config {


    /**
     * Events
     */
    public static final String IF_FUTURE_EVENTS = BuildConfig.IFBASE_STRING + "/mobile/events/future";
    public static final String IF_FUTURE_PAST_EVENTS = BuildConfig.IFBASE_STRING + "/mobile/events/futurepast";

    /**
     * Profile
     */
    public static final String IF_REGISTER_WITH_PIC = "/mobile/appuser/registerWithPic";
    public static final String IF_REGISTER = "/mobile/appuser/register";

    /**
     * Hotels
     */
    public static final String IF_HOTELS = BuildConfig.IFBASE_STRING + "/mobile/listHotels";
    public static final String IF_HOTELS_IMAGES = BuildConfig.IFBASE_STRING + "/webapp/hotels/image/";

    /**
     * Event participation
     */
    public static final String IF_PARTICIPATE_EVENT = BuildConfig.IFBASE_STRING + "/mobile/event/{eventid}/participate";
    public static final String IF_NOT_PARTICIPATE_EVENT = BuildConfig.IFBASE_STRING + "/mobile/event/{eventid}/notParticipate";
    public static final String IF_RESET_PARTICIPATE_EVENT = BuildConfig.IFBASE_STRING + "/mobile/appuser/resetEventParticip";
    public static final String IF_EVENTS_IMAGES = BuildConfig.IFBASE_STRING + "/webapp/events/image/";

    /**
     * Car Sharing Mobile
     */
    public static final String IF_CREATE_CARSHARE = BuildConfig.IFBASE_STRING + "/mobile/carshare/create";
    public static final String IF_MY_RIDES = BuildConfig.IFBASE_STRING + "/mobile/carshare/opensearches";
    public static final String IF_MY_DRIVES = BuildConfig.IFBASE_STRING + "/mobile/carshare/opendrives";
    public static final String IF_SEND_CARSHARE_MESSAGE = BuildConfig.IFBASE_STRING + "/mobile/carshare/sendmessage";
    // join?? public static final String IF_SEND_CARSHARE_MESSAGE = BuildConfig.IFBASE_STRING + "/mobile/carshare/sendmessage";

    /**
     * MarketWall
     */
    public static final String IF_MARKETWALL_CREATE = BuildConfig.IFBASE_STRING + "/mobile/marketwall/create";
    public static final String IF_MARKETWALL_ANNOUNCES = BuildConfig.IFBASE_STRING + "/mobile/marketwall/announces";
    public static final String IF_MARKETWALL_DELETE = BuildConfig.IFBASE_STRING + "/mobile/marketwall/delete";
    public static final String IF_MARKETWALL_IMAGE = BuildConfig.IFBASE_STRING + "/mobile/marketwall/image/";

}