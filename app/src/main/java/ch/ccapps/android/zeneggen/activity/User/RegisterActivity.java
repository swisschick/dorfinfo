package ch.ccapps.android.zeneggen.activity.User;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.activity.tourismus.HotelRestaurantActivity;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.task.HttpUpdateUserProfile;
import ch.ccapps.android.zeneggen.util.http.callback.HttpUpdateUserProfileCallbackImpl;
import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterActivity extends ActionBarActivity {

    private final int SELECT_PHOTO = 11;

    private EditText emailET;
    private EditText profileNameET;

    private CircleImageView profilePic;

    private ProgressDialog progress;

    private HttpUpdateUserProfile.HttpUpdateUserProfileCallback updateProfileCallback;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_register);
        getSupportActionBar().setTitle(R.string.title_register);

        progress = new ProgressDialog(this);
        updateProfileCallback = new HttpUpdateUserProfileCallbackImpl(this, progress);

        emailET = (EditText)findViewById(R.id.email);
        profileNameET = (EditText)findViewById(R.id.profile_name);
        profilePic = (CircleImageView)findViewById(R.id.profile_pic);
    }

    public void onRegister(View v){
        String email = emailET.getText().toString();
        String profileName = profileNameET.getText().toString();

        boolean emailvalid = email != null && !email.isEmpty();
        boolean profileNameValid = profileName != null && !profileName.isEmpty();
        if (emailvalid && profileNameValid){
            AppUser user = UserLocalStore.retrieveUser(this);
            user.setEmail(email);
            user.setProfileName(profileName);
            new HttpUpdateUserProfile(updateProfileCallback).sendSaveProfileRequest(this, user);
        } else {
            AlertDialog dialog = buildEmailProfileNotFilledDialog();
            dialog.show();
            //Toast.makeText(this, R.string.register_email_and_profile_cannot_be_empty,Toast.LENGTH_LONG).show();
        }

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

    public void onEditProfileImage(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profilePic.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        Log.e(TAG,"Profile image not found",e);
                        e.printStackTrace();
                    }

                }
        }
    }

}
