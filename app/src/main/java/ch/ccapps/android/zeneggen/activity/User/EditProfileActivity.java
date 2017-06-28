package ch.ccapps.android.zeneggen.activity.User;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.task.HttpUpdateUserProfile;
import ch.ccapps.android.zeneggen.util.http.callback.HttpUpdateUserProfileCallbackImpl;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

public class EditProfileActivity extends ActionBarActivity {

    private AppUser user;
    private AppUser userToBeUpdated;
    private static final int REGISTER_INTENT = 3;
    private static final int SELECT_PHOTO = 22;

    private CircleImageView profilePic;


    private EditText emailET, profileNameET, phoneNbrET;
    private EditText firstNameET;
    private EditText lastNameET;

    ProgressDialog progress;

    private ScrollView scrollview;

    int sw, sh, left, bottom;

    private static final String TAG = EditProfileActivity.class.getSimpleName();


    private HttpUpdateUserProfile.HttpUpdateUserProfileCallback updateProfileCallback;

    private void showSpinner(){
        progress.setTitle(getString(R.string.edit_profile_save_profile_load_title));
        progress.setMessage(getString(R.string.edit_profile_save_profile_load_message));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

    }

    private void dismissSpinner(){
        progress.dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_profile);

        setTitle(R.string.title_profile);

        progress = new ProgressDialog(this);

        updateProfileCallback = new HttpUpdateUserProfileCallbackImpl(this,progress);

        profilePic = (CircleImageView) findViewById(R.id.profile_pic);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Bitmap profilePict = UserLocalStore.retrieveProfilePic(this);
        if (profilePict != null) {
            profilePic.setImageBitmap(profilePict);
        } else {
            profilePic.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_user).color(R.color.yellow).colorRes(R.color.colorPrimary).sizeDp(80));
        }

        emailET = (EditText) findViewById(R.id.et_email);
        profileNameET = (EditText) findViewById(R.id.et_profile_name);
        phoneNbrET = (EditText) findViewById(R.id.et_phone_number);
        firstNameET = (EditText) findViewById(R.id.et_firstname);
        lastNameET = (EditText) findViewById(R.id.et_lastname);

        scrollview = (ScrollView) findViewById(R.id.editProfileScrollview);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        sw = dm.widthPixels;
        sh = dm.heightPixels;


        user = UserLocalStore.retrieveUser(this);

        userToBeUpdated = new AppUser();
        userToBeUpdated.setMobileUuid(user.getMobileUuid());

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
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
        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    profilePic.setImageBitmap(selectedImage);
                    UserLocalStore.saveProfilePic(selectedImage, this);

                    //TODO: UserLocalStore.saveProfilePic and send it using MultipartHelper...post
                } catch (FileNotFoundException e) {
                    Log.e(TAG, "Profile image not found", e);
                    e.printStackTrace();
                }

            }
        }
    }

    public void onEditProfileImage(View v) {
        Intent intent = new Intent(v.getContext(), EditProfilePicActivity.class);
        startActivity(intent);
    }


    private void displayUserProfile() {
        emailET.setText(user.getEmail());
        profileNameET.setText(user.getProfileName());
        phoneNbrET.setText(user.getPhoneNumber());
        lastNameET.setText(user.getLastName());
        firstNameET.setText(user.getFirstName());
    }

    public AlertDialog buildEmailProfileNotFilledDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.register_email_and_profile_cannot_be_empty)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
        ;
        // Create the AlertDialog object and return it
        return builder.create();
    }


    public void onFinishedEditing(View view) {
        //Email and ProfileName are required!
        if (emailET.getText().toString().isEmpty() || profileNameET.getText().toString().isEmpty()){
            AlertDialog dialog = buildEmailProfileNotFilledDialog();
            dialog.show();
        } else {
            userToBeUpdated.setEmail(emailET.getText().toString());
            userToBeUpdated.setFirstName(firstNameET.getText().toString());
            userToBeUpdated.setLastName(lastNameET.getText().toString());
            userToBeUpdated.setPhoneNumber(phoneNbrET.getText().toString());
            userToBeUpdated.setProfileName(profileNameET.getText().toString());



            new HttpUpdateUserProfile(updateProfileCallback).sendSaveProfileRequest(this, userToBeUpdated);
            showSpinner();
        }
    }
}
