package ch.ccapps.android.zeneggen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.tourismus.EventActivity;
import ch.ccapps.android.zeneggen.adapter.holder.EventViewHolder;
import ch.ccapps.android.zeneggen.model.Event;

/**
 * Created by celineheldner on 04.08.15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<Event> events;

    public EventAdapter(List<Event> items) {
        events = items;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_event_item, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        final Event event = getValueAt(position);
        holder.bindData(event);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EventActivity.class);
                intent.putExtra(EventActivity.EVENT_KEY,event);
                context.startActivity(intent);
            }
        });
    }

    public Event getValueAt(int position) {
        return events.get(position);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

}
