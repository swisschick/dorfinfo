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
import ch.ccapps.android.zeneggen.adapter.holder.Event2ViewHolder;
import ch.ccapps.android.zeneggen.adapter.holder.ViewHolder;
import ch.ccapps.android.zeneggen.cache.EventLocalStore;
import ch.ccapps.android.zeneggen.fragment.TabSectionListFragment;
import ch.ccapps.android.zeneggen.model.Event;
import ch.ccapps.android.zeneggen.task.HttpGetListTask;
import ch.ccapps.android.zeneggen.util.Config;

public class Events2Activity extends ActionTabBarActivity
        implements HttpGetListTask.HttpGetCallback<Event>,
        TabSectionListFragment.CustomSectionListInterface<Event>,
        TabSectionListFragment.TabSectionListInterface<Event> {

    private static final String TAG = Events2Activity.class.getSimpleName();

    private HashMap<String, HashMap<String, List<Event>>> sectionedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_events);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        HashMap<String, String> params = new HashMap<>();
        HttpGetListTask<Event> httpTask =
                new HttpGetListTask<>(this, Config.IF_FUTURE_EVENTS, params, Event.class);
        httpTask.execute();

        sectionedEvents = EventLocalStore.retrieveEvents(this);
        Log.d(TAG, "on create events: " + sectionedEvents);

    }

    @Override
    protected void setupViewPager() {
        adapter.removeAllFragments();
        sectionedEvents = EventLocalStore.retrieveEvents(this);
        Log.d(TAG, "setup view pager sec events:" + sectionedEvents);
        for (String hoteltype : sectionedEvents.keySet()) {
            adapter.addFragment(TabSectionListFragment.newInstance(hoteltype, R.layout.fragment_hotel_list), hoteltype);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onReceivedResult(List<Event> result) {
        Log.e(TAG,"received result:"+result);
        if (result != null) {
            sectionedEvents = EventLocalStore.orderedEventFromLocalStore(result);
            Log.e(TAG,"received result:"+ sectionedEvents);
            EventLocalStore.saveEvents(this, sectionedEvents);
            //HotelLocalStore.saveHotelImages(sectionedEvents, this);
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
    public ViewHolder<Event> createViewHolderForData(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        //view.setBackgroundResource(mBackground);
        return new Event2ViewHolder(view);

    }

    @Override
    public void onItemInSecRecViewClicked(Event dataobject) {
        Log.i(TAG,"eventtype is:"+dataobject);
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EXTRA_EVENT,dataobject);
        startActivity(intent);
    }

    @Override
    public HashMap<String, List<Event>> fillWithItemMap(String hoteltype) {
        Log.i(TAG,"eventtype is:"+hoteltype);
        return sectionedEvents.get(hoteltype);
    }

    @NonNull
    @Override
    public TabSectionListFragment.TabSectionListInterface<Event> getTabSectionListInterface() {
        return this;
    }



}
