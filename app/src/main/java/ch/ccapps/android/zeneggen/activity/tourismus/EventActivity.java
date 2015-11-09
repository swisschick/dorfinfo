package ch.ccapps.android.zeneggen.activity.tourismus;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.IconTextView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.androidannotations.annotations.EActivity;

import java.text.SimpleDateFormat;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.model.Event;

public class EventActivity extends ActionBarActivity {

    @Nullable
    private Event myEvent;
    private Bundle bundle;

    private TextView eventDescrTV;
    private TextView eventDateTV;
    private TextView eventLocation;
    private TextView organizerName;
    private TextView organizerPhone;
    private TextView participantsTV;

    private LinearLayout eventPhoneLL;

    private IconTextView ict_participate;
    private IconTextView ict_not_participate;
    private IconTextView ict_not_sure;

    private TextView tv_participation;


    public static final String EVENT_KEY="event_to_show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_event);

        bundle = getIntent().getExtras();
        if (bundle != null){
            myEvent = (Event)bundle.getSerializable(EVENT_KEY);

            setTitle(myEvent.getTitle());

            eventDescrTV = (TextView) findViewById(R.id.event_descr_tv);
            eventDateTV = (TextView) findViewById(R.id.event_date_tv);
            eventLocation = (TextView) findViewById(R.id.event_location);
            organizerName = (TextView) findViewById(R.id.event_organizer_name);
            organizerPhone = (TextView) findViewById(R.id.event_organizer_tel);
            eventPhoneLL = (LinearLayout) findViewById(R.id.event_organizer_tel_ll);
            participantsTV = (TextView) findViewById(R.id.participantText);


            ict_participate = (IconTextView) findViewById(R.id.participate);
            ict_not_participate = (IconTextView) findViewById(R.id.not_participate);
            ict_not_sure = (IconTextView) findViewById(R.id.not_sure);

            tv_participation = (TextView) findViewById(R.id.participation_text);

            //ict_not_sure.setBackgroundResource(R.drawable.red_button_active);

            participantsTV.setText(String.format(getString(R.string.event_nbr_participants),""+myEvent.getNbrParticipants()));
            eventDescrTV.setText(myEvent.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String dateString = "";
            if (myEvent.getStartDate() != null){
                dateString += sdf.format(myEvent.getStartDate());
            }
            if (myEvent.getEndDate() != null){
                dateString += "-"+sdf.format(myEvent.getEndDate());
            }
            eventDateTV.setText(dateString);

            eventLocation.setText(myEvent.getLocation());
            organizerName.setText(myEvent.getOrganizedBy());

            if (myEvent.getOrganizerPhone() == null){
                eventPhoneLL.setVisibility(View.GONE);
            } else {
                organizerPhone.setText(myEvent.getOrganizerPhone());
            }
        }
    }


    public void onParticipateClick(View view){
        ict_not_participate.setEnabled(true);
        ict_not_sure.setEnabled(true);
        ict_participate.setEnabled(false);
        tv_participation.setText(getString(R.string.event_yes));
    }

    public void onNotParticipateClick(View view){
        ict_not_participate.setEnabled(false);
        ict_not_sure.setEnabled(true);
        ict_participate.setEnabled(true);
        tv_participation.setText(getString(R.string.event_no));
    }

    public void onNotSureClick(View view){
        ict_not_participate.setEnabled(true);
        ict_not_sure.setEnabled(false);
        ict_participate.setEnabled(true);
        tv_participation.setText(getString(R.string.event_maybe));
    }
}
