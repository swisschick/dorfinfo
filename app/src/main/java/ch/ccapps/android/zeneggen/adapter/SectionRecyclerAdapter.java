package ch.ccapps.android.zeneggen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.adapter.holder.ViewHolder;

/**
 * Created by celineheldner on 07.08.15.
 */
public class SectionRecyclerAdapter<T> extends  RecyclerView.Adapter<ViewHolder>{
    public final static int SECTION_TYPE = 1;
    public final static int DATA_TYPE = 2;

    HashMap<String, List<T>> sectionedData;
    List<String> sectionOrder;
    List<Data> data = new ArrayList<>();
    private SectionAdapterClickListener listener;


    private ViewHolderCreator viewHolderCreator;

    public SectionRecyclerAdapter(HashMap<String, List<T>> data, List<String> sections, ViewHolderCreator vhc, SectionAdapterClickListener<T> lstnr){
        this.sectionedData = data;
        this.sectionOrder = sections;
        this.viewHolderCreator = vhc;
        this.listener = lstnr;
        generateData();
    }

    public void generateData(){
        data.clear();
        int section = 0;
        for (final String sectionkey: sectionOrder) {
            data.add(new Data(section, SECTION_TYPE, sectionkey, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SectionRecyclerAdapter.this.listener.onSectionClicked(sectionkey);
                }
            }));
            int i =0;
            for (final T dataInSection : sectionedData.get(sectionkey)){
                data.add(new Data(section, DATA_TYPE, dataInSection, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SectionRecyclerAdapter.this.listener.onItemInSecRecViewClicked(dataInSection);
                    }
                }));
                i++;
            }
            section++;
        }
    }

    public void updateData(HashMap<String,List<T>> data, List<String> sections){
        this.sectionedData = data;
        this.sectionOrder = sections;
        generateData();
        notifyDataSetChanged();
    }


    private static class Data {
        public int section;
        public Object dataObject;
        public int type;

        public View.OnClickListener listener;

        public Data(int section, int type, Object dataObject, View.OnClickListener listener) {
            this.section = section;
            this.type = type;
            this.dataObject = dataObject;
            this.listener = listener;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.viewHolderCreator.createViewHolderFor(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = this.data.get(position);
        holder.bindClickListener(data.listener);
        holder.bindData(data.dataObject);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ViewHolderCreator{
        public ViewHolder createViewHolderFor(ViewGroup parent, int type);
    }

    public interface SectionAdapterClickListener<T>{
        public void onSectionClicked(String section);
        public void onItemInSecRecViewClicked(T dataobject);
    }
}
