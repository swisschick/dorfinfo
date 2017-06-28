package ch.ccapps.android.zeneggen.activity.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.HomeActivity;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.model.AppUser;

public class ProfileActivity extends AppCompatActivity {

    private AppUser user;
    private static final int REGISTER_INTENT = 3;
    private static final int SELECT_PHOTO = 22;

    private ImageView profilePic;

    private TextView emailTV;
    private TextView profileNameTV;
    private TextView phoneNbrTV;
    private TextView firstLastNameTV;

    private LinearLayout telephone_LL;
    private LinearLayout email_LL;

    private static final String TAG = RegisterActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.title_profile));

        FloatingActionButton fab  = (FloatingActionButton)findViewById(R.id.floating_edit);
        fab.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_edit).colorRes(R.color.white).sizeDp(40));

        profilePic = (ImageView) findViewById(R.id.profile_pic);



        telephone_LL = (LinearLayout) findViewById(R.id.tel_layout);
        email_LL = (LinearLayout) findViewById(R.id.email_layout);
        emailTV = (TextView) findViewById(R.id.email);
        profileNameTV = (TextView) findViewById(R.id.profile_name);
        phoneNbrTV = (TextView) findViewById(R.id.phone_number);
        firstLastNameTV = (TextView) findViewById(R.id.first_lastname);





    }

    @Override
    protected void onResume() {
        super.onResume();
        user  = UserLocalStore.retrieveUser(this);
        if (user.getEmail() == null || user.getEmail().isEmpty()){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, REGISTER_INTENT);
        }
        displayUserProfile();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REGISTER_INTENT) {
            if (resultCode == RESULT_OK) {
                user = UserLocalStore.retrieveUser(this);
                displayUserProfile();
            }
        } else if (requestCode ==  SELECT_PHOTO){
            if(resultCode == RESULT_OK){
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    profilePic.setImageBitmap(selectedImage);
                    UserLocalStore.saveProfilePic(selectedImage, this);

                    //TODO: UserLocalStore.saveProfilePic and send it using MultipartHelper...post
                } catch (FileNotFoundException e) {
                    Log.e(TAG,"Profile image not found",e);
                    e.printStackTrace();
                }

            }
        }
    }

    private final int HOME_KEY = 11;
    //TODO: remove this

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, HOME_KEY, 0, "").setIcon(new IconDrawable(this, Iconify.IconValue.fa_home).colorRes(R.color.white).actionBarSize())
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }


    public void onEditProfileImage(View v){

        Intent intent = new Intent(v.getContext(), EditProfilePicActivity.class);
        startActivity(intent);

    }

    public void onEditProfile(View v){

        Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
        startActivity(intent);

    }

    private void showOrHide(String text, TextView textView, View container){
        if (text != null && !text.isEmpty()){
            textView.setText(text);
        } else {
            container.setVisibility(View.GONE);
        }
    }



    private void displayUserProfile(){
        Bitmap profilePict = UserLocalStore.retrieveProfilePic(this);
        if (profilePict != null){
            profilePic.setImageBitmap(profilePict);
        } else {
            profilePic.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_user).color(R.color.yellow).colorRes(R.color.colorPrimary).sizeDp(80));
        }
        showOrHide(user.getEmail(),emailTV,email_LL);
        showOrHide(user.getPhoneNumber(),phoneNbrTV,telephone_LL);
        profileNameTV.setText(user.getProfileName());
        firstLastNameTV.setText(user.getFirstName()+" "+user.getLastName());
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

}
