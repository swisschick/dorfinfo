package ch.ccapps.android.zeneggen.task;

import android.os.AsyncTask;
import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.HashMap;

import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.util.HttpHelper;


/**
 * Created by celineheldner on 17.08.16.
 */
public class HttpGetTask<R> extends AsyncTask<Void, Void, MobileResponse<R>> {
    private HttpHelper hh;
    private HashMap<String, String> parameters;
    private HttpGetCallback callback;

    private final static String TAG = HttpGetTask.class.getSimpleName();

    public HttpGetTask(HttpGetCallback<R> cb, String url, HashMap<String, String> params) {
        this.hh = new HttpHelper(url);
        this.callback = cb;
        this.parameters = params;
    }

    @Override
    protected MobileResponse<R> doInBackground(Void... params) {

        String response = hh.doGet(parameters);
        return parseResponse(response);
    }

    @Override
    protected void onPostExecute(MobileResponse<R> result) {
        if (callback != null) {
            if (result.isSuccess()){
                callback.onReceivedResult(result.getResult());
            } else {
                callback.onReceivedError(result.getErrorCode(),result.getErrorMessage(),result.getErrorTitle());
            }
        }
    }

    private MobileResponse<R> parseResponse(String json){
        MobileResponse<R> events = new MobileResponse<R>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            events = mapper.readValue(json, new TypeReference<MobileResponse<R>>(){});
        } catch (Exception e) {
            Log.e(HttpGetTask.class.getSimpleName(),"error parsing",e);
            e.printStackTrace();
        }
        return events;
    }

    public interface HttpGetCallback<R> {
        public void onReceivedResult(R result);
        public void onReceivedError(String errorCode, String errorMessage, String errorTitle);
    }
}
