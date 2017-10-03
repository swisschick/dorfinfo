package ch.ccapps.android.zeneggen.activity.tourismus;

import android.content.Intent;
import android.os.Bundle;
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
import ch.ccapps.android.zeneggen.model.EventMobile;
import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.model.db.AppDatabase;
import ch.ccapps.android.zeneggen.task.HttpGetListTask;
import ch.ccapps.android.zeneggen.util.Config;
import ch.ccapps.dorfapp.EventCommon;

public class Events2Activity extends ActionTabBarActivity
        implements HttpGetListTask.HttpGetCallback<EventCommon>,
        TabSectionListFragment.CustomSectionListInterface<EventMobile>,
        TabSectionListFragment.TabSectionListInterface<EventMobile> {

    private static final String TAG = Events2Activity.class.getSimpleName();

    private HashMap<String, HashMap<String, List<EventMobile>>> sectionedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_events);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        HashMap<String, String> params = new HashMap<>();
        params.put("maxNbrFuture","3");
        params.put("maxNbrPast","3");
        HttpGetListTask<EventCommon> httpTask =
                new HttpGetListTask<>(this, Config.IF_FUTURE_PAST_EVENTS, params, EventCommon.class);
        httpTask.execute();

        //TODO: sectionedEvents = EventLocalStore.retrieveEvents(this);
        Log.d(TAG, "on create events: " + sectionedEvents);

    }

    @Override
    protected void setupViewPager() {
        adapter.removeAllFragments();
        //TODO: sectionedEvents = EventLocalStore.retrieveEvents(this);
        Log.d(TAG, "setup view pager sec events:" + sectionedEvents);
        for (String hoteltype : sectionedEvents.keySet()) {
            adapter.addFragment(TabSectionListFragment.newInstance(hoteltype, R.layout.fragment_hotel_list), hoteltype);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onReceivedResult(List<EventCommon> result) {
        Log.e(TAG,"received result:"+result);
        if (result != null) {
            //TODO:sectionedEvents = EventLocalStore.orderedEventFromLocalStore(result);
            Log.e(TAG,"received result:"+ sectionedEvents);

            AppDatabase.getDatabaseInstance(this).eventModel().insertOrReplaceEvents((Event[])result.toArray());
            //TODO: EventLocalStore.saveEvents(this, sectionedEvents);
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
    public ViewHolder<EventMobile> createViewHolderForData(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        //view.setBackgroundResource(mBackground);
        return new Event2ViewHolder(view);

    }

    @Override
    public void onItemInSecRecViewClicked(EventMobile dataobject) {
        Log.i(TAG,"eventtype is:"+dataobject);
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EXTRA_EVENT,dataobject);
        startActivity(intent);
    }

    @Override
    public HashMap<String, List<EventMobile>> fillWithItemMap(String hoteltype) {
        Log.i(TAG,"eventtype is:"+hoteltype);
        return sectionedEvents.get(hoteltype);
    }

    @NonNull
    @Override
    public TabSectionListFragment.TabSectionListInterface<EventMobile> getTabSectionListInterface() {
        return this;
    }



}
