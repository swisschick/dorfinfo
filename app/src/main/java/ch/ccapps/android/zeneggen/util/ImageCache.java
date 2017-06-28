package ch.ccapps.android.zeneggen.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by celineheldner on 25.11.16.
 */

public class ImageCache {

    public enum ImageType{HOTEL,PROFILE,EVENT};

    private final static String TAG = ImageCache.class.getSimpleName();

    public static File findImageFile(String uniqueFileName, ImageType type, Context context){
        File imgFile = findInExternalStorage(uniqueFileName,type, context);
        if (imgFile != null){
            return imgFile;
        }
        imgFile = findInInternalStorage(uniqueFileName,type);
        if (imgFile != null){
            return imgFile;
        }
        return null;
    }

    public static boolean saveImageFileToCache(Bitmap btmp, String uniqueFileName, ImageType type, Context ctxt){
        File cacheDir = ctxt.getCacheDir();
        File f = new File(cacheDir, type.name()+"_"+uniqueFileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            btmp.compress(Bitmap.CompressFormat.JPEG,100,fos);
        } catch (FileNotFoundException e) {
            Log.e(TAG,"error saving bitmap to cache:",e);
            return false;
        }
        return true;
    }

    public static Bitmap findImageFileInCache(String uniqueFileNqme, ImageType type, Context ctxt){
        File cacheDir = ctxt.getCacheDir();
        File f = new File(cacheDir, type.name()+"_"+uniqueFileNqme);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            Log.e(TAG,"error retrieving bitmap to cache:",e);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        return bitmap;
    }

    public static boolean saveImageFile(Bitmap file, String uniqueFileName, ImageType type, Context context){

        if (saveToExternalStorage(uniqueFileName,type, context)){
            return true;
        }

        return saveToInternalStorage(uniqueFileName,type, context);
    }

    private static File findInExternalStorage(String uniqueFileName, ImageType type, Context context){
        if (!isExternalStorageReadable()){
            return null;
        }
        //TODO read file from external storage and return
        return null;
    }

    private static File findInInternalStorage(String uniqueFileName, ImageType type){

        return null;
    }

    private static boolean saveToExternalStorage(String uniqueFileName, ImageType type, Context context){
        if (!isExternalStorageWritable()){
            return false;
        }
        File file = new File(context.getExternalFilesDir(null), type.name()+"_"+uniqueFileName);
        if (file.exists()){
            return true;
        }
        Log.i(TAG, "File in external storage: "+type.name()+"_"+uniqueFileName+" could not be created");
        //TODO: write file to external storage
        return false;
    }

    private static boolean saveToInternalStorage(String uniqueFileName, ImageType type, Context context){
        File file = new File(context.getFilesDir(), type.name()+"_"+uniqueFileName);
        if (file.exists()){
            return true;
        }
        Log.i(TAG, "File in internal storage: "+type.name()+"_"+uniqueFileName+" could not be created");
        return false;
    }

    private static boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }




}
