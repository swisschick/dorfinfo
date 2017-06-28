package ch.ccapps.android.zeneggen.activity.carshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.activity.ActionTabBarActivity;

public class CreateCarShareActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_create_car_share);
    }
}
