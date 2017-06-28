package ch.ccapps.android.zeneggen.task;

import android.content.Context;
import android.util.Log;

import ch.ccapps.android.zeneggen.BuildConfig;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.model.User;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.util.http.HttpApi;
import ch.ccapps.android.zeneggen.util.http.ZeneggenRestClient;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by celineheldner on 24.03.17.
 */

public class HttpUpdateUserProfile {

    private HttpUpdateUserProfileCallback callback;
    private AppUser userToBeUpdated;


    private static final String TAG = HttpUpdateUserProfile.class.getSimpleName();

    public HttpUpdateUserProfile(HttpUpdateUserProfileCallback callback) {
        this.callback = callback;
    }

    public void sendSaveProfileRequest(final Context context, AppUser user){
        Log.i(TAG,"sending post request to update user:"+ Config.IF_REGISTER);
        userToBeUpdated = user;

        HttpApi api = ZeneggenRestClient.getClient();

        Call<MobileResponse<String>> call = api.updateUser(user);
        call.enqueue(new Callback<MobileResponse<String>>() {

            @Override
            public void onResponse(Call<MobileResponse<String>> call, retrofit2.Response<MobileResponse<String>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG,"response was successful"+response.message());
                    if (callback != null){
                        if (response.body().isSuccess()){
                            UserLocalStore.saveUser(context,userToBeUpdated);
                            callback.onReceivedResult(response.body());
                        } else {
                            callback.onReceivedError(response.body().getErrorMessage());
                        }

                    }
                } else {
                    Log.i(TAG,"response was not successful"+response.toString());
                    if (callback != null){
                        callback.onReceivedError(response.errorBody().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<MobileResponse<String>> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Failure!");
                Log.e(TAG, "Exception: " + t.getMessage());
                if (callback != null){
                    callback.onCouldNotSend();
                }
            }
        });
    }

    public interface HttpUpdateUserProfileCallback {
        void onReceivedResult(MobileResponse<String> responseBody);
        void onReceivedError(String errorMessage);
        void onCouldNotSend();
    }
}
