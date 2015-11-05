package ch.ccapps.android.zeneggen.activity.tourismus;

import android.os.Bundle;


import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;

public class ViewPointActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_view_point);
        setTitle(R.string.title_activity_view_point);
    }

}
