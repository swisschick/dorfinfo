package ch.ccapps.android.zeneggen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.model.EventMobile;
import ch.ccapps.android.zeneggen.model.MobileResponse;
import ch.ccapps.android.zeneggen.model.db.AppDatabase;
import ch.ccapps.dorfapp.EventCommon;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by celineheldner on 02.10.17.
 */

public class EventTabSectionsViewModel extends AndroidViewModel{

    private static final String TAG = EventTabSectionsViewModel.class.getSimpleName();

    //private final MutableLiveData<HashMap<String, HashMap<String, List<EventMobile>>>> sectionedEvents;

    private AppDatabase appDatabase;

    public EventTabSectionsViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabaseInstance(this.getApplication());

        retrieveDataFromDB();
        retrievDataFromServer();

    }

    private void retrieveDataFromDB(){

    }

    private void retrievDataFromServer(){

    }

    private void makeCall(Call<MobileResponse<EventCommon>> call) {
        call.enqueue(new Callback<MobileResponse<EventCommon>>() {

            @Override
            public void onResponse(Call<MobileResponse<EventCommon>> call, retrofit2.Response<MobileResponse<EventCommon>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG,"response was successful"+response.message());

                } else {
                    Log.i(TAG,"response was not successful"+response.toString());
                }
            }

            @Override
            public void onFailure(Call<MobileResponse<EventCommon>> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Exception: " + t.getMessage());
            }
        });
    }

}
