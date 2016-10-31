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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

public class TabSectionListFragment<T> extends Fragment implements SectionRecyclerAdapter.ViewHolderCreator, SectionRecyclerAdapter.SectionAdapterClickListener<T> {
    @Nullable
    private HashMap<String, List<T>> itemMap;
    private int fragmentListLayoutId;
    private TabSectionListInterface<T> tabSecInterface;

    private static final String LIST_LAYOUT_KEY = "LIST_LAYOUT_ID";

    public interface CustomSectionListInterface<T>{
        @NonNull
        public TabSectionListInterface<T> getTabSectionListInterface();
    }

    @NonNull
    public static TabSectionListFragment newInstance(HashMap<String, List<Object>> items,
                                                     int fragmentListLayout) {
       TabSectionListFragment fragment = new TabSectionListFragment();
        Bundle b = new Bundle();
        b.putSerializable("items", items);
        b.putInt(LIST_LAYOUT_KEY,fragmentListLayout);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            tabSecInterface = ((CustomSectionListInterface<T>)activity).getTabSectionListInterface();
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" You must implement CustomSectionListInterface");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        this.tabSecInterface = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemMap = (HashMap<String, List<T>>)getArguments().getSerializable("items");
            fragmentListLayoutId = getArguments().getInt(LIST_LAYOUT_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                fragmentListLayoutId, container, false);
        setupRecyclerView(rv);

        return rv;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        List<String> sections = new ArrayList<String>();
        for (String section : itemMap.keySet()){
            if (section.equals("Zeneggen")){
                sections.add(0,section);
            } else {
                sections.add(section);
            }
        }
        SectionRecyclerAdapter<T> hotelRecAdapter = new SectionRecyclerAdapter<>(itemMap,sections,this,this);
        recyclerView.setAdapter(hotelRecAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(TabSectionListFragment.this.getActivity(), null));

    }

    public void updateData(){}

    @NonNull
    private List<String> getRandomSublist(@NonNull String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    @Nullable
    @Override
    public ViewHolder createViewHolderFor(@NonNull ViewGroup parent, int type) {
        if (type == SectionRecyclerAdapter.DATA_TYPE){
            return tabSecInterface.createViewHolderForData(parent);
            /*View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);*/
            //view.setBackgroundResource(mBackground);
            //return new HotelRestViewHolder(view);
        } else if (type == SectionRecyclerAdapter.SECTION_TYPE) {
            return tabSecInterface.createViewHolderForSection(parent);
            /*View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_section_header_item, parent, false);
            return new SectionViewHolder(view);*/
        } else {
            return null;
        }

    }

    @Override
    public void onSectionClicked(String section) {

    }

    @Override
    public void onItemInSecRecViewClicked(T dataobject) {
        tabSecInterface.onItemInSecRecViewClicked(dataobject);
        /*
        Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
        intent.putExtra(HotelDetailActivity.EXTRA_HOTEL, (Parcelable)dataobject);
        getActivity().startActivity(intent);*/
    }


    public interface TabSectionListInterface<R> {
        public ViewHolder createViewHolderForSection(@NonNull ViewGroup parent);
        public ViewHolder<R> createViewHolderForData(@NonNull ViewGroup parent);
        public void onItemInSecRecViewClicked(R dataobject);
    }

}
