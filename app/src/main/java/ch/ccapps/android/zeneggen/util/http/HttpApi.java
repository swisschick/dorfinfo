package ch.ccapps.android.zeneggen.util.http;

import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.util.Config;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by celineheldner on 11.03.17.
 */

public interface HttpApi {

    @Multipart
    @POST(Config.IF_REGISTER_WITH_PIC)
    Call<ResponseBody> updateUser(@Part MultipartBody.Part photo, @Part("user") AppUser userJson);

    @POST(Config.IF_REGISTER)
    Call<MobileResponse<String>> updateUser(@Body AppUser userJson);

    @PUT(Config.IF_PARTICIPATE_EVENT)
    Call<MobileResponse<Event>> participateEvent(@Path("eventid") long eventId, @Header("mobileUuid") String mobileUuid);

    @PUT(Config.IF_NOT_PARTICIPATE_EVENT)
    Call<MobileResponse<Event>> notParticipateEvent(@Path("eventid") long eventId, @Header("mobileUuid") String mobileUuid);

}
