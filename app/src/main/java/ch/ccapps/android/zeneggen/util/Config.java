package ch.ccapps.android.zeneggen.util;

import ch.ccapps.android.zeneggen.BuildConfig;

public class Config {


    public static final String IF_BASE = "http://192.168.1.111:9000";

    /**
     * Events
     */
    public static final String IF_FUTURE_EVENTS = BuildConfig.IFBASE_STRING + "/mobile/events/future";

    /**
     * Profile
     */
    public static final String IF_REGISTER = BuildConfig.IFBASE_STRING + "/mobile/appuser/register";

    /**
     * Hotels
     */
    public static final String IF_HOTELS = BuildConfig.IFBASE_STRING + "/mobile/listHotels";

    /**
     * Event participation
     */
    public static final String IF_PARTICIPATE_EVENT = BuildConfig.IFBASE_STRING + "/mobile/appuser/participateEvent";
    public static final String IF_NOT_PARTICIPATE_EVENT = BuildConfig.IFBASE_STRING + "/mobile/appuser/notParticipateEvent";
    public static final String IF_RESET_PARTICIPATE_EVENT = BuildConfig.IFBASE_STRING + "/mobile/appuser/resetEventParticip";

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