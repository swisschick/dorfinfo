package ch.ccapps.android.zeneggen.activity.tourismus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.IconTextView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.model.Event;

public class EventActivity extends ActionBarActivity {

    private Event myEvent;
    private Bundle bundle;

    private TextView eventDescrTV;
    private TextView eventDateTV;

    private IconTextView ict_participate;
    private IconTextView ict_not_participate;
    private IconTextView ict_not_sure;


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

            ict_participate = (IconTextView) findViewById(R.id.participate);
            ict_not_participate = (IconTextView) findViewById(R.id.not_participate);
            ict_not_sure = (IconTextView) findViewById(R.id.not_sure);

            ict_not_sure.setBackgroundResource(R.drawable.red_button_active);


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
        }
    }

    public void onParticipateClick(View view){
        ict_not_sure.setBackgroundResource(R.drawable.red_button);
        ict_not_participate.setBackgroundResource(R.drawable.red_button);
        ict_participate.setBackgroundResource(R.drawable.red_button_active);
        ict_not_participate.setTextColor(getResources().getColor(R.color.white));
        ict_not_sure.setTextColor(getResources().getColor(R.color.white));
        ict_participate.setTextColor(getResources().getColor(R.color.textColor));
    }

    public void onNotParticipateClick(View view){
        ict_not_sure.setBackgroundResource(R.drawable.red_button);
        ict_not_participate.setBackgroundResource(R.drawable.red_button_active);
        ict_participate.setBackgroundResource(R.drawable.red_button);
        ict_not_participate.setTextColor(getResources().getColor(R.color.textColor));
        ict_not_sure.setTextColor(getResources().getColor(R.color.white));
        ict_participate.setTextColor(getResources().getColor(R.color.white));
    }

    public void onNotSureClick(View view){
        ict_not_sure.setBackgroundResource(R.drawable.red_button_active);
        ict_not_participate.setBackgroundResource(R.drawable.red_button);
        ict_participate.setBackgroundResource(R.drawable.red_button);
        ict_not_participate.setTextColor(getResources().getColor(R.color.white));
        ict_not_sure.setTextColor(getResources().getColor(R.color.textColor));
        ict_participate.setTextColor(getResources().getColor(R.color.white));
    }
}
