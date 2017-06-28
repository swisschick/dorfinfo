package ch.ccapps.android.zeneggen.activity.carshare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionTabBarActivity;
import ch.ccapps.android.zeneggen.activity.User.RegisterActivity;
import ch.ccapps.android.zeneggen.activity.tourismus.HotelDetailActivity;
import ch.ccapps.android.zeneggen.adapter.holder.BasicSectionViewHolder;
import ch.ccapps.android.zeneggen.adapter.holder.ViewHolder;
import ch.ccapps.android.zeneggen.cache.CarShareLocalStore;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.fragment.CarShareFragment;
import ch.ccapps.android.zeneggen.fragment.TabSectionListFragment;
import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.model.CarShareMobile;
import ch.ccapps.android.zeneggen.task.HttpGetTask;
import ch.ccapps.android.zeneggen.util.Config;

public class CarShareActivity extends ActionTabBarActivity
        implements HttpGetTask.HttpGetCallback<List<CarShareMobile>>,
        TabSectionListFragment.CustomSectionListInterface<CarShareMobile>,
        TabSectionListFragment.TabSectionListInterface<CarShareMobile> {

    private static final int REGISTER_INTENT = 222;

    private static final String TAG = CarShareActivity.class.getSimpleName();

    private HashMap<String, HashMap<String, List<CarShareMobile>>> sectionedCarShares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_car_share);

        HashMap<String, String> params = new HashMap<>();
        HttpGetTask<List<CarShareMobile>> httpTask =
                new HttpGetTask<List<CarShareMobile>>(this, Config.IF_MY_DRIVES, params);
        httpTask.execute();
        sectionedCarShares = CarShareLocalStore.retrieveCarShares(this);
        Log.d(TAG, "sec hotels" + sectionedCarShares);

    }

    @Override
    protected void setupViewPager() {
        adapter.removeAllFragments();
        sectionedCarShares = CarShareLocalStore.retrieveCarShares(this);
        Log.d(TAG, "sec hotles" + sectionedCarShares);
        for (String hoteltype : sectionedCarShares.keySet()) {
            adapter.addFragment(TabSectionListFragment.newInstance(hoteltype, R.layout.fragment_hotel_list), hoteltype);
        }

    }

    private void userIsNotRegistered(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent,REGISTER_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REGISTER_INTENT) {
            if (resultCode == RESULT_OK) {
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        }
    }

    @Override
    public void onReceivedResult(List<CarShareMobile> result) {
        Log.e(TAG,"received result:"+result);
        if (result != null) {
            AppUser appUser = UserLocalStore.retrieveUser(this);
            sectionedCarShares = CarShareLocalStore.orderedCarSharesFromList(result,appUser,this);
            CarShareLocalStore.saveCarShares(this, sectionedCarShares);
            setupViewPager();
        } else {
            Log.e(TAG, "Hotel List from server was null");
        }
    }

    @Override
    public void onReceivedError(String errorCode, String errorMessage, String errorTitle) {
        Log.e(TAG, "Error when trying to fetch CarShareMobile data:" + errorCode + ", " + errorMessage + ", " + errorTitle);
    }

    @Override
    public ViewHolder createViewHolderForSection(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_section_header_item, parent, false);
        return new BasicSectionViewHolder(view);
    }

    @Override
    public ViewHolder<CarShareMobile> createViewHolderForData(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        //view.setBackgroundResource(mBackground);
        return new CarShareFragment.CarShareViewHolder(view);

    }

    @Override
    public void onItemInSecRecViewClicked(CarShareMobile dataobject) {
        Intent intent = new Intent(this, HotelDetailActivity.class);
        intent.putExtra(HotelDetailActivity.EXTRA_HOTEL, (Parcelable) dataobject);
        startActivity(intent);
    }

    @Override
    public HashMap<String, List<CarShareMobile>> fillWithItemMap(String hoteltype) {
        Log.i(TAG,"hoteltype is:"+hoteltype);
        return sectionedCarShares.get(hoteltype);
    }

    @NonNull
    @Override
    public TabSectionListFragment.TabSectionListInterface<CarShareMobile> getTabSectionListInterface() {
        return this;
    }
}
