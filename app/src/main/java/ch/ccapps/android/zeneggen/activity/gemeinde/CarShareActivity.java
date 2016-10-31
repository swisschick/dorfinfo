package ch.ccapps.android.zeneggen.activity.gemeinde;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionTabBarActivity;
import ch.ccapps.android.zeneggen.fragment.HotelListFragment;
import ch.ccapps.android.zeneggen.util.HotelLocalStore;

public class CarShareActivity extends ActionTabBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_car_share);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void setupViewPager() {
        adapter.removeAllFragments();
        /*sectionedHotels =  HotelLocalStore.retrieveHotels(this);
        Log.d(TAG,"sec hotles"+sectionedHotels);
        for (String hoteltype : sectionedHotels.keySet()){
            adapter.addFragment(HotelListFragment.newInstance(sectionedHotels.get(hoteltype)), hoteltype);
        }*/

    }

}
