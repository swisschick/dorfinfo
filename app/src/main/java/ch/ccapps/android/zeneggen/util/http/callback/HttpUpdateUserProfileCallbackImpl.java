package ch.ccapps.android.zeneggen.util.http.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.User.EditProfileActivity;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.model.User;
import ch.ccapps.android.zeneggen.task.HttpUpdateUserProfile;
import okhttp3.ResponseBody;

/**
 * Created by celineheldner on 06.06.17.
 */

public class HttpUpdateUserProfileCallbackImpl implements HttpUpdateUserProfile.HttpUpdateUserProfileCallback{

    private Activity activity;
    ProgressDialog progress;

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    public HttpUpdateUserProfileCallbackImpl(Activity activity, ProgressDialog progress){
        this.activity = activity;
        this.progress = progress;
    }


    @Override
    public void onReceivedResult(MobileResponse<String> responseBody) {
        Log.i(TAG, "received Result for user:"+responseBody.getResult());
        dismissSpinner();
        Toast.makeText(activity, R.string.edit_profile_successcully_saved,Toast.LENGTH_LONG).show();
        activity.finish();
    }

    @Override
    public void onReceivedError(String  error) {
        dismissSpinner();
        Log.i(TAG, "error!!");
    }

    @Override
    public void onCouldNotSend() {
        dismissSpinner();
        Toast.makeText(activity, R.string.edit_profile_error_saving_profile,Toast.LENGTH_LONG).show();
        Log.i(TAG, "Error no connection");
    }


    private void dismissSpinner(){
        progress.dismiss();
    }
}
