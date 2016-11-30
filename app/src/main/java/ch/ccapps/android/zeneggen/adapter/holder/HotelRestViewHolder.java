package ch.ccapps.android.zeneggen.adapter.holder;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.adapter.holder.ViewHolder;
import ch.ccapps.android.zeneggen.model.Hotel;
import ch.ccapps.android.zeneggen.util.ImageCache;

/**
 * Created by celineheldner on 31.10.16.
 */

public class HotelRestViewHolder extends ViewHolder<Hotel> {

    public Hotel mHotel;

    @NonNull
    public final View mView;
    @NonNull
    public final ImageView mImageView;
    @NonNull
    public final TextView mTextView;
    @NonNull
    public final TextView mSubText;

    public HotelRestViewHolder(@NonNull View view) {
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
    public void bindData(@NonNull Hotel data) {
        mHotel = data;
        Bitmap hotelFile = ImageCache.findImageFileInCache(mHotel.getImageName(), ImageCache.ImageType.HOTEL,mView.getContext());
        if (hotelFile != null){
            Glide.with(mImageView.getContext())
                    .load(convertBitmapToByteArray(hotelFile))
                    .fitCenter()
                    .into(mImageView);
        }

        mTextView.setText(data.getName());
        mSubText.setText(mView.getContext().getString(R.string.phone) + " " + mHotel.getPhonenumber());
    }

    @Override
    public void bindClickListener(View.OnClickListener listener) {
        this.mView.setOnClickListener(listener);
    }

    public byte[] convertBitmapToByteArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}
