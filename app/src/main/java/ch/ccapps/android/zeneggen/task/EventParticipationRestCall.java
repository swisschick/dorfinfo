package ch.ccapps.android.zeneggen.task;

import android.util.Log;

import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.util.http.HttpApi;
import ch.ccapps.android.zeneggen.util.http.ZeneggenRestClient;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by celineheldner on 24.03.17.
 */

public class EventParticipationRestCall {


    private HttpApi api;


    private static final String TAG = EventParticipationRestCall.class.getSimpleName();

    public EventParticipationRestCall() {
        this.api = ZeneggenRestClient.getClient();
    }

    public void participateInEvent(String mobileUuid, long eventId,final EventParticipationRestCallback callback){
        Log.i(TAG,"sending put request to participate in Event:"+ Config.IF_PARTICIPATE_EVENT);
        Call<MobileResponse<Event>> call = api.participateEvent(eventId, mobileUuid);
        makeCall(callback, call);
    }

    public void notParticipateInEvent(String mobileUuid, long eventId, final EventParticipationRestCallback callback){
        Log.i(TAG,"sending put request to decline participation in Event:"+ Config.IF_NOT_PARTICIPATE_EVENT);
        Call<MobileResponse<Event>> call = api.notParticipateEvent(eventId, mobileUuid);
        makeCall(callback, call);
    }

    private void makeCall(final EventParticipationRestCallback callback, Call<MobileResponse<Event>> call) {
        call.enqueue(new Callback<MobileResponse<Event>>() {

            @Override
            public void onResponse(Call<MobileResponse<Event>> call, retrofit2.Response<MobileResponse<Event>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG,"response was successful"+response.message());
                    if (callback != null){
                        callback.onReceivedResult(response.body());

                    }
                } else {
                    Log.i(TAG,"response was not successful"+response.toString());
                    if (callback != null){
                        callback.onServerNotReachable();
                    }
                }
            }

            @Override
            public void onFailure(Call<MobileResponse<Event>> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Exception: " + t.getMessage());
                if (callback != null){
                    callback.onCouldNotSend();
                }
            }
        });
    }

    public interface EventParticipationRestCallback {
        void onReceivedResult(MobileResponse<Event> responseBody);
        void onServerNotReachable();
        void onCouldNotSend();
    }
}
