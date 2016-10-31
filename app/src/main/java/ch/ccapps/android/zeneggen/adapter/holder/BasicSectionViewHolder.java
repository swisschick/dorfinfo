package ch.ccapps.android.zeneggen.adapter.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import ch.ccapps.android.zeneggen.R;

/**
 * Created by celineheldner on 31.10.16.
 */

public class BasicSectionViewHolder extends ViewHolder<String>{

    TextView mHeader;

    public BasicSectionViewHolder(@NonNull View view) {
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
