package ch.ccapps.android.zeneggen.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yasargil on 30.06.15.
 */
public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {
    // each data item is just a string in this case

    public ViewHolder(@NonNull View view) {
        super(view);
    }

    public abstract void bindData(T data);

    public abstract void bindClickListener(View.OnClickListener listener);

}
