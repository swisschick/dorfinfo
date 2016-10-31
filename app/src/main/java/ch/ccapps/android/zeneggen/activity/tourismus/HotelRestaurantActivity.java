package ch.ccapps.android.zeneggen.activity.tourismus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.CheeseListFragment;
import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionTabBarActivity;
import ch.ccapps.android.zeneggen.adapter.SectionRecyclerAdapter;
import ch.ccapps.android.zeneggen.adapter.holder.ViewHolder;
import ch.ccapps.android.zeneggen.fragment.HotelListFragment;
import ch.ccapps.android.zeneggen.model.Hotel;
import ch.ccapps.android.zeneggen.task.HttpGetTask;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.util.HotelLocalStore;

public class HotelRestaurantActivity extends ActionTabBarActivity implements HttpGetTask.HttpGetCallback<List<Hotel>>{

    private static final String TAG = HotelRestaurantActivity.class.getSimpleName();

    private HashMap<String, HashMap<String, List<Hotel>>> sectionedHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_hotel_restaurant);
        HashMap<String, String> params = new HashMap<>();
        HttpGetTask<List<Hotel>> httpTask =
                new HttpGetTask<List<Hotel>>(this, Config.IF_HOTELS,params);
        httpTask.execute();
        sectionedHotels =  HotelLocalStore.retrieveHotels(this);

    }

    @Override
    protected void setupViewPager() {
        adapter.removeAllFragments();
        sectionedHotels =  HotelLocalStore.retrieveHotels(this);
        Log.d(TAG,"sec hotles"+sectionedHotels);
        for (String hoteltype : sectionedHotels.keySet()){
            adapter.addFragment(HotelListFragment.newInstance(sectionedHotels.get(hoteltype)), hoteltype);
        }

    }

    @Override
    public void onReceivedResult(List<Hotel> result) {
        if (result != null){
            sectionedHotels = HotelLocalStore.orderedHotelDataFromList(result);
            HotelLocalStore.saveHotels(this,sectionedHotels);
            setupViewPager();
        } else {
            Log.e(TAG,"Hotel List from server was null");
        }
    }

    @Override
    public void onReceivedError(String errorCode, String errorMessage, String errorTitle) {
        Log.e(TAG,"Error when trying to fetch hotel data:"+errorCode+", "+errorMessage+", "+errorTitle);
    }
}
