package ch.ccapps.android.zeneggen.activity.tourismus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import ch.ccapps.android.zeneggen.Cheeses;
import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.adapter.DividerItemDecoration;
import ch.ccapps.android.zeneggen.adapter.MenuAdapter;

public class TourismumsMain extends ActionBarActivity {

    private MenuAdapter adapter;
    private RecyclerView menulistview;

    @NonNull
    public static String MENU_TYPE = "menu_type";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_tourismums_main);



        menulistview = (RecyclerView) findViewById(R.id.tourismus_menu);

        Intent intent = getIntent();
        String type = intent.getStringExtra(MENU_TYPE);
        final ActionBar ab = getSupportActionBar();
        if (type!= null && type.equals("tourismus")){
            adapter = new MenuAdapter(ch.ccapps.android.zeneggen.model.Menu.tourismusMenu(this,false));
            ab.setTitle(getString(R.string.title_activity_tourismums_main));
        } else {
            adapter = new MenuAdapter(ch.ccapps.android.zeneggen.model.Menu.gemeindeMenu(this, false));
            ab.setTitle(getString(R.string.title_activity_gemeinde_main));
        }
        setupRecyclerView(menulistview);

        menulistview.setAdapter(adapter);



    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        //recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
        //        getRandomSublist(Cheeses.sCheeseStrings, 30)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_tourismums_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }
}
