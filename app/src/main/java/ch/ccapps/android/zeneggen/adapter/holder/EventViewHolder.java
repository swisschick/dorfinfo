package ch.ccapps.android.zeneggen.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.model.Event;

/**
 * Created by celineheldner on 21.09.15.
 */
public class EventViewHolder extends RecyclerView.ViewHolder  {

    @NonNull
    public final View mView;
    @NonNull
    public final ImageView mImageView;
    @NonNull
    public final TextView eventTitleTV;
    @NonNull
    public final TextView eventDateTV;
    @NonNull
    public final TextView eventLocationTV;
    @NonNull
    public final TextView eventNbrPersonTV;

    public EventViewHolder(@NonNull View view) {
        super(view);
        mView = view;
        mImageView = (ImageView) view.findViewById(R.id.event_image);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        eventTitleTV = (TextView) view.findViewById(R.id.event_title_tv);
        eventDateTV = (TextView) view.findViewById(R.id.event_time_tv);
        eventLocationTV = (TextView) view.findViewById(R.id.event_location_tv);
        eventNbrPersonTV = (TextView) view.findViewById(R.id.nbr_person);
        Log.i("EventViewHolder","eventNbrPersonTV is;"+eventNbrPersonTV);
    }

    public void bindData(@NonNull Event event){
        eventTitleTV.setText(event.getTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        eventDateTV.setText(sdf.format(event.getStartDate()));
        eventLocationTV.setText(event.getLocation());
        eventNbrPersonTV.setText(event.getNbrParticipants()+"");
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " '" + eventTitleTV.getText();
    }
}
