package ch.ccapps.android.zeneggen.adapter.model;

import android.view.View;

/**
 * Created by celineheldner on 11.08.15.
 */
public class SectionData<T> {
    private T data;
    private View.OnClickListener clickListener;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
