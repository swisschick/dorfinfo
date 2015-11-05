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
import ch.ccapps.android.zeneggen.util.HotelLocalStore;

public class HotelRestaurantActivity extends ActionTabBarActivity {

    private static final String TAG = HotelRestaurantActivity.class.getSimpleName();

    private HashMap<String, HashMap<String, List<Hotel>>> sectionedHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_hotel_restaurant);
        sectionedHotels =  HotelLocalStore.getDebugData();

    }

    @Override
    protected void setupViewPager() {
        sectionedHotels =  HotelLocalStore.getDebugData();
        Log.d(TAG,"sec hotles"+sectionedHotels);
        for (String hoteltype : sectionedHotels.keySet()){
            adapter.addFragment(HotelListFragment.newInstance(sectionedHotels.get(hoteltype)), hoteltype);
        }

    }

}
