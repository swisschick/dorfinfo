package ch.ccapps.android.zeneggen.cache;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.util.ObjectSerializer;

/**
 * Created by celineheldner on 11.08.15.
 */
public class UserLocalStore{

    public static final String PROFILE_PIC = "Profile_pic";
    private static AppUser _user;

    private static final String USER_STORE = "USER_STORE";
    private static final String USER_KEY = "USER_KEY";

    private final static String TAG = UserLocalStore.class.getSimpleName();


    public static void saveUser(Context ctxt,AppUser mobileUser){
        SharedPreferences preferences = ctxt.getSharedPreferences(USER_STORE, Context.MODE_PRIVATE);
        _user = mobileUser;
        try {
            String serializedHotels = ObjectSerializer.serialize(mobileUser);
            preferences.edit().putString(USER_KEY,serializedHotels).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AppUser retrieveUser(Context ctxt){
        SharedPreferences preferences = ctxt.getSharedPreferences(USER_STORE, Context.MODE_PRIVATE);
        String hotelSerialized = preferences.getString(USER_KEY,null);

        if (_user == null){
            try {
                _user = (AppUser)ObjectSerializer.deserialize(hotelSerialized);
            } catch (Exception e) {
                Log.e("UserLocalStore","Error fetching User Local Store",e);
                e.printStackTrace();
            }
        }
        if (_user == null){
            AppUser user = AppUser.createFakeUser();
            saveUser(ctxt, user);
        }

        return _user;
    }

    public static File saveProfilePic(Bitmap bitmap, Context ctxt){
        /*ImageCache.saveImageFile(bitmap, PROFILE_PIC, ImageCache.ImageType.PROFILE,ctxt);
        return ImageCache.findImageFile(PROFILE_PIC,ImageCache.ImageType.PROFILE,ctxt);*/



        ContextWrapper cw = new ContextWrapper(ctxt);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");


        FileOutputStream out = null;
        try {
            //File file = new File("PROFILE.jpg");
            out = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 20, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return mypath;
    }

    public static Bitmap retrieveProfilePic(Context ctxt){
        ContextWrapper cw = new ContextWrapper(ctxt);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mypath);
        } catch (FileNotFoundException e) {
            Log.e(TAG,"error retrieving bitmap to cache:",e);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        return bitmap;

    }

    public static File retrieveProfilePicFile(Context ctxt){
        ContextWrapper cw = new ContextWrapper(ctxt);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        return new File(directory,"profile.jpg");
    }

}
