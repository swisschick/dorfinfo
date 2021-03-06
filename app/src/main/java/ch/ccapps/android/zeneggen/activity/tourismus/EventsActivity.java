package ch.ccapps.android.zeneggen.activity.tourismus;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.adapter.EventAdapter;
import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.task.HttpGetTask;
import ch.ccapps.android.zeneggen.util.Config;

public class EventsActivity extends ActionBarActivity implements HttpGetTask.HttpGetCallback<List<Event>> {

    private RecyclerView eventsRV;
    private List<Event> eventsList;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_events);
        setTitle(R.string.menu_events);
        HashMap<String, String> params = new HashMap<>();
        HttpGetTask<List<Event>> httpTask =
                new HttpGetTask<List<Event>>(this, Config.IF_FUTURE_EVENTS,params);
        httpTask.execute();

        eventsRV = (RecyclerView) findViewById(R.id.recyclerview_events);

        eventsList = Event.initialEvents();

        setupRecyclerView(eventsRV);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        eventAdapter = new EventAdapter(eventsList);
        recyclerView.setAdapter(eventAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
    }

    @Override
    public void onReceivedResult(List<Event> result) {

    }

    @Override
    public void onReceivedError(String errorCode, String errorMessage, String errorTitle) {

    }

    public void onParticipateClick(View view){}
    public void onNotParticipateClick(View view){}
    public void onNotSureClick(View view){}
}
