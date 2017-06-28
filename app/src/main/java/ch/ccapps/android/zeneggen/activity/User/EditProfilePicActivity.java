package ch.ccapps.android.zeneggen.activity.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.cache.UserLocalStore;
import ch.ccapps.android.zeneggen.util.http.ProfilePicUpdater;

/**
 * Created by celineheldner on 21.04.17.
 */

public class EditProfilePicActivity extends ActionBarActivity implements ProfilePicUpdater.ProfilePicUpdateCallback {


    private static final int SELECT_PHOTO = 22;

    private ImageView profilePic;

    private static final String TAG = EditProfilePicActivity.class.getSimpleName();

    LinearLayout updatePicDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_edit_profilepic);

        setTitle(R.string.title_profile);

        profilePic = (ImageView) findViewById(R.id.profile_pic);
        updatePicDialog = (LinearLayout) findViewById(R.id.layout_loading);

        drawProfilePic();

    }

    private void drawProfilePic() {
        Bitmap profilePict = UserLocalStore.retrieveProfilePic(this);
        if (profilePict != null){
            profilePic.setImageBitmap(profilePict);
        } else {
            profilePic.setImageDrawable(new IconDrawable(this, Iconify.IconValue.fa_user).color(R.color.yellow).colorRes(R.color.colorPrimary).sizeDp(80));
        }
    }


    public void onEditProfileImage(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    updatePicDialog.setVisibility(View.VISIBLE);
                    //profilePic.setImageBitmap(selectedImage);

                    //TODO: Error handling ... local store also saves picture from server. (Synchronizes data)
                    ProfilePicUpdater picUpdater = new ProfilePicUpdater(this, this);
                    picUpdater.updateProfilePic(selectedImage);

                } catch (FileNotFoundException e) {
                    Log.e(TAG, "Profile image not found", e);
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onSuccess() {
        updatePicDialog.setVisibility(View.GONE);
        drawProfilePic();
    }

    @Override
    public void onFailure(String errorMessage) {
        updatePicDialog.setVisibility(View.GONE);
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }


    public void onDeletePhoto(View view){

    }
}
