package ch.ccapps.android.zeneggen.task;

import android.os.AsyncTask;


import java.util.HashMap;

import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.util.HttpHelper;
import ch.ccapps.android.zeneggen.util.JsonParserHelper;

/**
 * Created by celineheldner on 27.04.15.
 */
public class FutureEventsTask extends
        AsyncTask<Void, Void, MobileResponse> {

    private static final String TAG = FutureEventsTask.class.getSimpleName();
    final HttpHelper hh;
    private FetchFutureEvents callback;


    public FutureEventsTask(FetchFutureEvents cb) {
        hh = new HttpHelper(Config.IF_FUTURE_EVENTS);
        this.callback = cb;
    }

    @Override
    protected MobileResponse doInBackground(Void... params) {
        String response = hh.doGet(new HashMap<String, String>());
        return JsonParserHelper.parseEvents(response);

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(MobileResponse result) {
        if (callback != null) {
            callback.futureEventsFetched(result);
        }
    }

    public interface FetchFutureEvents {
        public void futureEventsFetched(MobileResponse result);
    }
}
