package ch.ccapps.android.zeneggen.task;

import android.content.Context;
import android.util.Log;

import java.io.File;

import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.util.http.HttpApi;
import ch.ccapps.android.zeneggen.util.http.ZeneggenRestClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by celineheldner on 24.03.17.
 */

public class HttpUpdateUserProfileAndPic {

    private HttpUpdateUserProfileAndPicCallback callback;


    private static final String TAG = HttpUpdateUserProfileAndPic.class.getSimpleName();

    public HttpUpdateUserProfileAndPic(HttpUpdateUserProfileAndPicCallback callback) {
        this.callback = callback;
    }

    public void sendSaveProfileRequest(Context context, AppUser user) {
        Log.i(TAG, "sending multipart request:" + Config.IF_REGISTER_WITH_PIC);


        HttpApi api = ZeneggenRestClient.getClient();

        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        File file = UserLocalStore.retrieveProfilePicFile(context); //new File("/storage/emulated/0/Pictures/MyApp/test.png");

        //MultipartBody.Part body = prepareFilePart("photo", fileUri);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        MultipartBody.Part part = MultipartBody.Part.createFormData("profileImage", file.getName(), requestBody);
        // RequestBody.create(MediaTypef)
        Log.i(TAG, "sending multipart with pic:" + Config.IF_REGISTER_WITH_PIC);


        Call call = api.updateUser(part, user);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "response was successful" + response.message());
                    callback.onReceivedResult(response.body());
                } else {
                    Log.d(TAG, "response was not successful" + response.toString());
                    callback.onReceivedError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Exception: " + t.getMessage());
                callback.onCouldNotSend();
            }
        });

    }

    public interface HttpUpdateUserProfileAndPicCallback<R> {
        void onReceivedResult(ResponseBody responseBody);

        void onReceivedError(ResponseBody error);

        void onCouldNotSend();
    }
}
