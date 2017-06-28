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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.model.Hotel;
import ch.ccapps.android.zeneggen.util.Config;

public class HotelDetailActivity extends AppCompatActivity {

    public static final String EXTRA_HOTEL = "hotel_extra";
    private Hotel mHotel;
    private TextView contactTV;
    private TextView telTV;
    private TextView descrTV;
    private TextView linkTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        mHotel = intent.getParcelableExtra(EXTRA_HOTEL);
        final String hotelName = mHotel.getName();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(hotelName);

        FloatingActionButton fab  = (FloatingActionButton)findViewById(R.id.floating_send_email);
        fab.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_send).colorRes(R.color.white).sizeDp(40));

        contactTV = (TextView) findViewById(R.id.event_organisor);
        descrTV = (TextView) findViewById(R.id.event_description_tv);
        telTV = (TextView) findViewById(R.id.tel);
        linkTV = (TextView) findViewById(R.id.link);
        if (mHotel.getPhonenumber() != null){
            telTV.setText(getString(R.string.phone)+" "+mHotel.getPhonenumber());
        } else {
            findViewById(R.id.tel_layout).setVisibility(View.GONE);
        }

        if (mHotel.getLink() != null){
            linkTV.setText(mHotel.getLink());
        } else {
            findViewById(R.id.link_layout).setVisibility(View.GONE);
        }


        String contactString = "";
        //contactString += getString(R.string.phone)+" "+mCarShare.getPhonenumber();
        if(mHotel.getAddress() != null){
            contactString += mHotel.getAddress();
        }
        if (mHotel.getPlz() != null){
            contactString += "\n"+mHotel.getPlz()+" "+mHotel.getLocation();
        }
        contactTV.setText(contactString);
        descrTV.setText(mHotel.getDescription());

        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Config.IF_HOTELS_IMAGES + mHotel.getImageName()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    public void onTelClick(View view){
        Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(mHotel.getPhonenumber())));
        startActivity(i);
    }

    public void onLinkClick(View view){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(mHotel.getLink()));
        startActivity(i);
    }
}
