package ch.ccapps.android.zeneggen.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.List;

import ch.ccapps.android.zeneggen.CheeseListFragment;
import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.fragment.HotelListFragment;

public class ActionTabBarActivity extends AppCompatActivity {

    protected Adapter adapter;
    private final int HOME_KEY = 11;

    private final static String TAG = ActionTabBarActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_tab_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(new IconDrawable(this, Iconify.IconValue.fa_arrow_left).colorRes(R.color.white).actionBarSize());
        ab.setDisplayHomeAsUpEnabled(true);

        adapter = new Adapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager();
        }
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, HOME_KEY, 0, "").setIcon(new IconDrawable(this, Iconify.IconValue.fa_home).colorRes(R.color.white).actionBarSize())
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    protected void setupViewPager() {

        adapter.addFragment(new HotelListFragment(), "Test Menu");
        adapter.addFragment(new CheeseListFragment(), "Test");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            case HOME_KEY:
                Intent intent1 = new Intent(this, HomeActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public void removeAllFragments(){
            mFragments.clear();
            mFragmentTitles.clear();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
