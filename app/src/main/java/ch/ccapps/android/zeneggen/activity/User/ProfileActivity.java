package ch.ccapps.android.zeneggen.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.model.AppUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends ActionBarActivity {

    private AppUser user;
    private static final int REGISTER_INTENT = 3;

    private CircleImageView profilePic;

    private TextView emailTV;
    private TextView profileNameTV;
    private TextView phoneNbrTV;
    private TextView firstNameTV;
    private TextView lastNameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_profile);

        setTitle(R.string.title_profile);

        profilePic = (CircleImageView) findViewById(R.id.profile_pic);
        profilePic.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_user).color(R.color.yellow).colorRes(R.color.colorPrimary).sizeDp(80));

        emailTV = (TextView) findViewById(R.id.email);
        profileNameTV = (TextView) findViewById(R.id.profile_name);
        phoneNbrTV = (TextView) findViewById(R.id.phone_number);
        firstNameTV = (TextView) findViewById(R.id.firstname);
        lastNameTV = (TextView) findViewById(R.id.lastname);

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
        }
    }


    private void displayUserProfile(){
        emailTV.setText(user.getEmail());
        profileNameTV.setText(user.getProfileName());
        phoneNbrTV.setText(user.getPhoneNumber());
        lastNameTV.setText(user.getLastName());
        firstNameTV.setText(user.getFirstName());
    }
}
