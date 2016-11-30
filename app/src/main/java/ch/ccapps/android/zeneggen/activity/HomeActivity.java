package ch.ccapps.android.zeneggen.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.User.ProfileActivity;
import ch.ccapps.android.zeneggen.activity.tourismus.TourismumsMain;
import ch.ccapps.android.zeneggen.adapter.DividerItemDecoration;
import ch.ccapps.android.zeneggen.adapter.MenuAdapter;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView sideMenuRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(new IconDrawable(this, Iconify.IconValue.fa_bars).colorRes(R.color.white).actionBarSize());

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_name));

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floating_action_button);
        fab.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_user).colorRes(R.color.white));
        sideMenuRV = (RecyclerView)findViewById(R.id.side_menu);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setupRecyclerView(sideMenuRV);
        loadBackdrop();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        MenuAdapter sideMenuAdapter = new MenuAdapter(ch.ccapps.android.zeneggen.model.Menu.sideMenu(this), true);
        recyclerView.setAdapter(sideMenuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.zeneggen_summer_app).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(@NonNull NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                        if (menuItem.getItemId() == R.id.nav_hotels) {
                            //showDuty(DutyInterface.getActiveDuty(MainActivity.this));
                        } else if (menuItem.getItemId() == R.id.nav_activities) {
                            //showPastDuties();
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void onGemeinde(@NonNull View v){
        Intent intent = new Intent(v.getContext(), TourismumsMain.class);
        intent.putExtra(TourismumsMain.MENU_TYPE,"gemeinde");
        startActivity(intent);
    }

    public void onTourismus(@NonNull View v){
        Intent intent = new Intent(v.getContext(), TourismumsMain.class);
        intent.putExtra(TourismumsMain.MENU_TYPE,"tourismus");
        startActivity(intent);
    }

    public void onProfile(@NonNull View v){
        Intent intent = new Intent(v.getContext(), ProfileActivity.class);
        startActivity(intent);
    }
}
