package ch.ccapps.android.zeneggen.adapter;

import android.content.Context;
import android.media.Image;
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
import ch.ccapps.android.zeneggen.model.Hotel;
import ch.ccapps.android.zeneggen.model.Menu;

/**
 * Created by celineheldner on 04.08.15.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<Menu> menuItems;
    private boolean isSideMenu = false;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (isSideMenu){
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_side_left_item, parent, false);
        } else {
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Menu menu = getValueAt(position);
        holder.mTextView.setText(menu.getTitle());
        if (menu.isSubMenu()){
            holder.mTextView.setTextSize(10);
        }

        if (menu.getIconText() == null && menu.getIconResource() == -1){
            holder.mImageView.setVisibility(View.GONE);
            holder.mIconTextView.setVisibility(View.GONE);
        }
        else if(menu.getIconText() != null){
            holder.mIconTextView.setText(menu.getIconText());
            holder.mImageView.setVisibility(View.GONE);
            holder.mIconTextView.setVisibility(View.VISIBLE);
        } else {
            Glide.with(holder.mImageView.getContext())
                .load(menu.getIconResource())
                .fitCenter()
                .into(holder.mImageView);
            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mIconTextView.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(menu.getIntent());
            }
        });
    }

    public Menu getValueAt(int position) {
        return menuItems.get(position);
    }

    public MenuAdapter(List<Menu> items) {
        //context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        //mBackground = mTypedValue.resourceId;
        menuItems = items;
    }

    public MenuAdapter(List<Menu> items, boolean sideMenu) {
        //context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        //mBackground = mTypedValue.resourceId;
        menuItems = items;
        this.isSideMenu = sideMenu;
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final IconTextView mIconTextView;
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.iconview);
            mIconTextView = (IconTextView) view.findViewById(R.id.menu_icon);
            mTextView = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
}
