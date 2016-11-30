package ch.ccapps.android.zeneggen.task;

import android.os.AsyncTask;
import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.model.Hotel;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.util.HttpHelper;


/**
 * Created by celineheldner on 17.08.16.
 */
public class HttpGetListTask<R> extends AsyncTask<Void, Void, MobileResponse<List<R>>> {
    private HttpHelper hh;
    private HashMap<String, String> parameters;
    private HttpGetCallback callback;
    private Class cltype;

    private final static String TAG = HttpGetListTask.class.getSimpleName();

    public HttpGetListTask(HttpGetCallback<R> cb, String url, HashMap<String, String> params, Class cltype) {
        this.hh = new HttpHelper(url);
        this.callback = cb;
        this.cltype = cltype;
        this.parameters = params;
    }

    @Override
    protected MobileResponse<List<R>> doInBackground(Void... params) {

        String response = hh.doGet(parameters);
        return parseResponse(response);
    }

    @Override
    protected void onPostExecute(MobileResponse<List<R>> result) {
        if (callback != null) {
            if (result.isSuccess()){
                ObjectMapper mapper = new ObjectMapper();
                JavaType type = mapper.getTypeFactory().
                        constructCollectionType(List.class, cltype);
                List<R> results = mapper.convertValue(result.getResult(), type);
                callback.onReceivedResult(results);
            } else {
                callback.onReceivedError(result.getErrorCode(),result.getErrorMessage(),result.getErrorTitle());
            }
        }
    }

    private MobileResponse<List<R>> parseResponse(String json){
        MobileResponse<List<R>> events = new MobileResponse<List<R>>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            events = mapper.readValue(json, new TypeReference<MobileResponse<List<R>>>(){});

        } catch (Exception e) {
            events.setSuccess(false);
            Log.e(HttpGetListTask.class.getSimpleName(),"error parsing",e);
            e.printStackTrace();
        }
        return events;
    }

    public interface HttpGetCallback<R> {
        public void onReceivedResult(List<R> result);
        public void onReceivedError(String errorCode, String errorMessage, String errorTitle);
    }
}
