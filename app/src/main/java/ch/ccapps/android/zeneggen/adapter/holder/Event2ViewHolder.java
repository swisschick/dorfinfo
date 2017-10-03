package ch.ccapps.android.zeneggen.adapter.holder;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.io.ByteArrayOutputStream;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.model.EventMobile;
import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.util.Config;

/**
 * Created by celineheldner on 31.10.16.
 */

public class Event2ViewHolder extends ViewHolder<EventMobile> {

    public EventMobile mEvent;

    @NonNull
    public final View mView;
    @NonNull
    public final ImageView mImageView;
    @NonNull
    public final TextView mTextView;
    @NonNull
    public final TextView mSubText;

    public Event2ViewHolder(@NonNull View view) {
        super(view);
        mView = view;
        mImageView = (ImageView) view.findViewById(R.id.avatar);
        mTextView = (TextView) view.findViewById(R.id.hotel_menu_title);
        mSubText = (TextView) view.findViewById(R.id.hotel_submenu_title);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " '" + mTextView.getText();
    }

    @Override
    public void bindData(@NonNull EventMobile data) {
        mEvent = data;
        if (mEvent.getEvent().getImageName() == null || mEvent.getEvent().getImageName().isEmpty()) {
            mImageView.setImageDrawable(new IconDrawable(mImageView.getContext(), Iconify.IconValue.fa_calendar).color(R.color.yellow).colorRes(R.color.colorPrimary).sizeDp(80));
        } else {
            Glide.with(mImageView.getContext())
                    .load(Config.IF_EVENTS_IMAGES + mEvent.getEvent().getImageName())
                    .fitCenter()
                    .into(mImageView);
        }


        mTextView.setText(data.getEvent().getTitle());
        mSubText.setText(mEvent.getEvent().getDescription());
    }

    @Override
    public void bindClickListener(View.OnClickListener listener) {
        this.mView.setOnClickListener(listener);
    }

    public byte[] convertBitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}
