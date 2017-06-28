package ch.ccapps.android.zeneggen.task;

import android.os.AsyncTask;

import ch.ccapps.android.zeneggen.util.http.HttpApi;
import okhttp3.RequestBody;


/**
 * Created by celineheldner on 17.08.16.
 */
public class HttpAPITask extends AsyncTask<Void, Void, String> {
    private HttpAPITaskCallback callback;
    private HttpApi httpAPI;
    private RequestBody requestBody;
    private String description;

    private final static String TAG = HttpAPITask.class.getSimpleName();

    public HttpAPITask(HttpAPITaskCallback cb, HttpApi httpAPI,RequestBody body, String descr) {
        this.httpAPI = httpAPI;
        this.requestBody = body;
        this.description = descr;
        this.callback = cb;
    }

    @Override
    public String doInBackground(Void... params) {
        /*Call call = httpAPI.updateUser(requestBody, description);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG,"response was successful"+response.message());
                } else {
                    Log.i(TAG,"response was not successful"+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Failure!");
                Log.e(TAG, "Exception: " + t.getMessage());
                //Toast.makeText(getBaseContext(), ADD_BOOK_FAILED_PLEASE_CHECK_THE_LOG, Toast.LENGTH_SHORT).show();
            }
        });*/
        return "hello";
    }

    @Override
    protected void onPostExecute(String result) {
        if (callback != null) {
            if (result!=null){
                callback.onReceivedResult(result);
            } else {
                callback.onReceivedError(result);
            }
        }
    }


    public interface HttpAPITaskCallback {
        public void onReceivedResult(String result);
        public void onReceivedError(String errorTitle);
    }
}
