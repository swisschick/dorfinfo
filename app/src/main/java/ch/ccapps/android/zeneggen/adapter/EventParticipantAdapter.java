package ch.ccapps.android.zeneggen.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.model.Menu;
import ch.ccapps.android.zeneggen.model.User;

/**
 * Created by celineheldner on 04.08.15.
 */
public class EventParticipantAdapter extends RecyclerView.Adapter<EventParticipantAdapter.ViewHolder> {
    private List<User> users;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_event_participant_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = getValueAt(position);
        holder.mTextView.setText(user.getNickName());


       /* holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                Context context = v.getContext();
            }
        });*/
    }

    public User getValueAt(int position) {
        return users.get(position);
    }

    public EventParticipantAdapter(List<User> items) {
        users = items;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        public final View mView;
        @NonNull
        public final TextView mTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.usernameTV);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
}
