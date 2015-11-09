package ch.ccapps.android.zeneggen.activity.tourismus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.adapter.DividerItemDecoration;
import ch.ccapps.android.zeneggen.adapter.EventAdapter;
import ch.ccapps.android.zeneggen.adapter.SectionRecyclerAdapter;
import ch.ccapps.android.zeneggen.model.Event;
import ch.ccapps.android.zeneggen.model.Hotel;

public class EventsActivity extends ActionBarActivity {

    private RecyclerView eventsRV;
    private List<Event> eventsList;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_events);
        setTitle(R.string.menu_events);

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

}
