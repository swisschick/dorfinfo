package ch.ccapps.android.zeneggen.activity.tourismus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionTabBarActivity;
import ch.ccapps.android.zeneggen.adapter.holder.BasicSectionViewHolder;
import ch.ccapps.android.zeneggen.adapter.holder.HotelRestViewHolder;
import ch.ccapps.android.zeneggen.adapter.holder.ViewHolder;
import ch.ccapps.android.zeneggen.fragment.TabSectionListFragment;
import ch.ccapps.android.zeneggen.model.Hotel;
import ch.ccapps.android.zeneggen.task.HttpGetListTask;
import ch.ccapps.android.zeneggen.task.HttpGetTask;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.android.zeneggen.cache.HotelLocalStore;

public class HotelRestaurantActivity extends ActionTabBarActivity
        implements HttpGetListTask.HttpGetCallback<Hotel>,
        TabSectionListFragment.CustomSectionListInterface<Hotel>,
        TabSectionListFragment.TabSectionListInterface<Hotel> {

    private static final String TAG = HotelRestaurantActivity.class.getSimpleName();

    private HashMap<String, HashMap<String, List<Hotel>>> sectionedHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_hotel_restaurant);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        HashMap<String, String> params = new HashMap<>();
        HttpGetListTask<Hotel> httpTask =
                new HttpGetListTask<Hotel>(this, Config.IF_HOTELS, params, Hotel.class);
        httpTask.execute();

        sectionedHotels = HotelLocalStore.retrieveHotels(this);
        Log.d(TAG, "sec hotels" + sectionedHotels);

    }

    @Override
    protected void setupViewPager() {
        adapter.removeAllFragments();
        sectionedHotels = HotelLocalStore.retrieveHotels(this);
        Log.d(TAG, "sec hotles" + sectionedHotels);
        for (String hoteltype : sectionedHotels.keySet()) {
            adapter.addFragment(TabSectionListFragment.newInstance(hoteltype, R.layout.fragment_hotel_list), hoteltype);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onReceivedResult(List<Hotel> result) {
        Log.e(TAG,"received result:"+result);
        if (result != null) {
            sectionedHotels = HotelLocalStore.orderedHotelDataFromList(result);
            Log.e(TAG,"received result:"+sectionedHotels);
            HotelLocalStore.saveHotels(this, sectionedHotels);
            //HotelLocalStore.saveHotelImages(sectionedHotels, this);
            setupViewPager();
        } else {
            Log.e(TAG, "Hotel List from server was null");
        }
    }

    @Override
    public void onReceivedError(String errorCode, String errorMessage, String errorTitle) {
        Log.e(TAG, "Error when trying to fetch hotel data:" + errorCode + ", " + errorMessage + ", " + errorTitle);
    }

    @Override
    public ViewHolder createViewHolderForSection(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_section_header_item, parent, false);
        return new BasicSectionViewHolder(view);
    }

    @Override
    public ViewHolder<Hotel> createViewHolderForData(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        //view.setBackgroundResource(mBackground);
        return new HotelRestViewHolder(view);

    }

    @Override
    public void onItemInSecRecViewClicked(Hotel dataobject) {
        Intent intent = new Intent(this, HotelDetailActivity.class);
        intent.putExtra(HotelDetailActivity.EXTRA_HOTEL, (Parcelable) dataobject);
        startActivity(intent);
    }

    @Override
    public HashMap<String, List<Hotel>> fillWithItemMap(String hoteltype) {
        Log.i(TAG,"hoteltype is:"+hoteltype);
        return sectionedHotels.get(hoteltype);
    }

    @NonNull
    @Override
    public TabSectionListFragment.TabSectionListInterface<Hotel> getTabSectionListInterface() {
        return this;
    }
}
