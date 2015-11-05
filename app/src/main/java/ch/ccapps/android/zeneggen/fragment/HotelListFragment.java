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

package ch.ccapps.android.zeneggen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.tourismus.HotelDetailActivity;
import ch.ccapps.android.zeneggen.adapter.DividerItemDecoration;
import ch.ccapps.android.zeneggen.adapter.SectionRecyclerAdapter;
import ch.ccapps.android.zeneggen.adapter.holder.ViewHolder;
import ch.ccapps.android.zeneggen.model.Hotel;

public class HotelListFragment extends Fragment implements SectionRecyclerAdapter.ViewHolderCreator, SectionRecyclerAdapter.SectionAdapterClickListener<Hotel> {
    private HashMap<String, List<Hotel>> mHotels;

    public static HotelListFragment newInstance(HashMap<String, List<Hotel>> hotels) {
        HotelListFragment fragment = new HotelListFragment();
        Bundle b = new Bundle();
        b.putSerializable("hotels", (Serializable) hotels);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHotels = (HashMap<String, List<Hotel>>)getArguments().getSerializable("hotels");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_hotel_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        List<String> sections = new ArrayList<String>();
        for (String section : mHotels.keySet()){
            if (section.equals("Zeneggen")){
                sections.add(0,section);
            } else {
                sections.add(section);
            }
        }
        SectionRecyclerAdapter<Hotel> hotelRecAdapter = new SectionRecyclerAdapter<>(mHotels,sections,this,this);
        recyclerView.setAdapter(hotelRecAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(HotelListFragment.this.getActivity(), null));
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    @Override
    public ViewHolder createViewHolderFor(ViewGroup parent, int type) {
        if (type == SectionRecyclerAdapter.DATA_TYPE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            //view.setBackgroundResource(mBackground);
            return new HotelRestViewHolder(view);
        } else if (type == SectionRecyclerAdapter.SECTION_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_section_header_item, parent, false);
            return new SectionViewHolder(view);
        } else {
            return null;
        }

    }

    @Override
    public void onSectionClicked(String section) {

    }

    @Override
    public void onItemInSecRecViewClicked(Hotel dataobject) {
        Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
        intent.putExtra(HotelDetailActivity.EXTRA_HOTEL, (Parcelable)dataobject);
        getActivity().startActivity(intent);
    }

    public static class SectionViewHolder extends ViewHolder<String>{
        TextView mHeader;

        public SectionViewHolder(View view) {
            super(view);
            mHeader = (TextView)view.findViewById(R.id.header);

        }

        @Override
        public void bindData(String data) {
            mHeader.setText(data);

        }

        @Override
        public void bindClickListener(View.OnClickListener listener) {

        }
    }

    public static class HotelRestViewHolder extends ViewHolder<Hotel> {
        public Hotel mHotel;

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;
        public final TextView mSubText;

        public HotelRestViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(R.id.hotel_menu_title);
            mSubText = (TextView) view.findViewById(R.id.hotel_submenu_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }

        @Override
        public void bindData(Hotel data) {
            mHotel = data;
            Glide.with(mImageView.getContext())
                    .load(data.getDrawableResource())
                    .fitCenter()
                    .into(mImageView);
            mTextView.setText(data.getName());
            mSubText.setText(mView.getContext().getString(R.string.phone) + " " + mHotel.getPhonenumber());
        }

        @Override
        public void bindClickListener(View.OnClickListener listener) {
            this.mView.setOnClickListener(listener);
        }
    }
}
