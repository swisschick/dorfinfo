package ch.ccapps.android.zeneggen.util.http;

import android.content.Context;
import android.graphics.Bitmap;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.task.HttpUpdateUserProfileAndPic;
import ch.ccapps.android.zeneggen.task.HttpUpdateUserProfileAndPic.HttpUpdateUserProfileAndPicCallback;
import okhttp3.ResponseBody;

/**
 * Created by celineheldner on 01.05.17.
 */

public class ProfilePicUpdater{

    private Bitmap imageToBeUpdated;
    private Context context;
    private ProfilePicUpdateCallback callback;



    private HttpUpdateUserProfileAndPicCallback httpUpdateProfileCallback = new HttpUpdateUserProfileAndPicCallback(){

        @Override
        public void onReceivedResult(ResponseBody responseBody) {
            UserLocalStore.saveProfilePic(imageToBeUpdated,context);
            //Todo: error message if there is an error in the responseBody
            callback.onSuccess();
        }

        @Override
        public void onReceivedError(ResponseBody error) {
            //TODO:  better Error message
            callback.onFailure(error.toString());
        }

        @Override
        public void onCouldNotSend() {
            callback.onFailure(context.getString(R.string.network_problem));
        }
    };

    public ProfilePicUpdater(Context context, ProfilePicUpdateCallback callback) {
        this.callback = callback;
        this.context = context;
    }


    public void updateProfilePic(Bitmap bitmap){
        this.imageToBeUpdated = bitmap;
        AppUser user = UserLocalStore.retrieveUser(context);
        new HttpUpdateUserProfileAndPic(httpUpdateProfileCallback).sendSaveProfileRequest(context, user);
    }

    public interface ProfilePicUpdateCallback<R> {
        void onSuccess();
        void onFailure(String errorMessage);
    }



}
