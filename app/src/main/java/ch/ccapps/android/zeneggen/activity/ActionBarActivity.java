package ch.ccapps.android.zeneggen.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import ch.ccapps.android.zeneggen.R;

public class ActionBarActivity extends AppCompatActivity {

    private FrameLayout container;

    private final int HOME_KEY = 11;

    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        container = (FrameLayout) findViewById(R.id.container);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(new IconDrawable(this, Iconify.IconValue.fa_arrow_left).colorRes(R.color.white).actionBarSize());
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void setActionBarContentView(int resId){
        LayoutInflater.from(this).inflate(resId, container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, HOME_KEY, 0, "").setIcon(new IconDrawable(this, Iconify.IconValue.fa_home).colorRes(R.color.white).actionBarSize())
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);*/
                finish();
                return true;
            case HOME_KEY:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
