package ch.ccapps.android.zeneggen.util.http.callback;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.User.EditProfileActivity;
import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.task.EventParticipationRestCall;

/**
 * Created by celineheldner on 06.06.17.
 */

public class EventParticipationCallbackImpl implements EventParticipationRestCall.EventParticipationRestCallback{

    private Activity activity;

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    public EventParticipationCallbackImpl(Activity activity){
        this.activity = activity;
        //this.progress = progress;
    }


    @Override
    public void onReceivedResult(MobileResponse<Event> responseBody) {
        Log.i(TAG, "received Result for user:"+responseBody.getResult());
        if (responseBody.isSuccess()){
            //TODO: save participants
            //List<Participant> participants = responseBody.getResult().getParticipants();
        }
        Toast.makeText(activity, R.string.event_participation_successful,Toast.LENGTH_LONG).show();
        activity.finish();
    }

    @Override
    public void onServerNotReachable() {
        Toast.makeText(activity, R.string.event_participation_not_successful,Toast.LENGTH_LONG).show();
        Log.i(TAG, "error!!");
    }

    @Override
    public void onCouldNotSend() {
        Toast.makeText(activity, R.string.general_no_network,Toast.LENGTH_LONG).show();
        Log.i(TAG, "Error no connection");
    }

}
