/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.ccapps.android.zeneggen.activity.tourismus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.model.Event;
import ch.ccapps.android.zeneggen.util.Config;

public class EventDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT = "event_extra";

    private Bundle bundle;
    private Event mEvent;

    private TextView contactTV;
    private TextView telTV;
    private TextView descrTV;
    private TextView locationTV;
    private TextView linkTV;
    private TextView dateTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();

        if (bundle != null){
            mEvent = (Event)bundle.getSerializable(EXTRA_EVENT);
            final String eventTitle = mEvent.getTitle();

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(eventTitle);
            setupUI();
        }



    }

    private void setupUI(){
        FloatingActionButton pa_ab = (FloatingActionButton)findViewById(R.id.participate_ab);
        pa_ab.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_check).colorRes(R.color.white));
        FloatingActionButton dont_pa_ab  = (FloatingActionButton)findViewById(R.id.dont_participate_ab);
        dont_pa_ab.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_circle_o_notch).colorRes(R.color.white).sizeDp(40));

        contactTV = (TextView) findViewById(R.id.event_organisor);
        descrTV = (TextView) findViewById(R.id.event_description_tv);
        locationTV = (TextView) findViewById(R.id.event_location);
        telTV = (TextView) findViewById(R.id.tel);
        linkTV = (TextView) findViewById(R.id.link);
        dateTV = (TextView) findViewById(R.id.event_time_tv);

        String eventTime = createEventTimeText();
        dateTV.setText(eventTime);

        showIfNotNull(mEvent.getLocation(),R.id.locationCard,locationTV);
        showIfNotNull("Tel:"+mEvent.getOrganizerPhone(),R.id.tel_layout,telTV);
        showIfNotNull(mEvent.getLink(),R.id.link_layout,linkTV);

        String contactString = "";
        if(mEvent.getOrganizedBy() != null){
            contactString += mEvent.getOrganizedBy();
        }
        if (mEvent.getOrganizerEmail() != null){
            contactString += "\n"+ mEvent.getOrganizerEmail()+" "+ mEvent.getLocation();
        }
        contactTV.setText(contactString);
        descrTV.setText(mEvent.getDescription());

        loadBackdrop();
    }

    private String createEventTimeText(){
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        dateString = sdf.format(mEvent.getStartDate());

        SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdfdatetime = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(mEvent.getStartDate());
        int startDay = calStart.get(Calendar.DAY_OF_YEAR);

        if (mEvent.getEndDate() != null){

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(mEvent.getStartDate());
            int endDay = calEnd.get(Calendar.DAY_OF_YEAR);


            if (!mEvent.isDayOnlyEvent() && ( startDay != endDay )){
                dateString += sdftime.format(mEvent.getStartDate());
                dateString += "\n";
                dateString += sdfdatetime.format(mEvent.getEndDate());
            } else if (!mEvent.isDayOnlyEvent() && (startDay == endDay)){
                dateString += "\n";
                dateString += sdftime.format(mEvent.getStartDate());
                dateString += " - "+sdftime.format(mEvent.getEndDate());
            } else if (mEvent.isDayOnlyEvent()) {
                dateString += " - "+sdf.format(mEvent.getEndDate());
            }
        }

        return  dateString;
    }

    private void showIfNotNull(String text, int layoutId, TextView textView){
        if (text!= null && !text.isEmpty()){
            textView.setText(text);
            findViewById(layoutId).setVisibility(View.VISIBLE);
        } else {
            findViewById(layoutId).setVisibility(View.GONE);
        }
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        DrawableTypeRequest<String> drawableTypeReq = Glide.with(this).load(Config.IF_EVENTS_IMAGES + mEvent.getImageName());
        drawableTypeReq.centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    public void onTelClick(View view){
        Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(mEvent.getOrganizerPhone())));
        startActivity(i);
    }

    public void onLinkClick(View view){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(mEvent.getLink()));
        startActivity(i);
    }

    public void onParticipateClicked(){

    }

    public void onNotParticipateClicked(){

    }
}
