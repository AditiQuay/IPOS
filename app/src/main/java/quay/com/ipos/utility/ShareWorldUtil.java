package quay.com.ipos.utility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import quay.com.ipos.R;

public class ShareWorldUtil {
    private static final String TAG = ShareWorldUtil.class.getSimpleName() ;

    public static void  dialNumber(Application context, String phoneNo) {
        try {


            // Use format with "tel:" and phone number to create phoneNumber.
            String phoneNumber = String.format("tel: %s", phoneNo);
            // Create the intent.
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            // Set the data for the intent as the phone number.
            dialIntent.setData(Uri.parse(phoneNumber));
            // If package resolves to an app, send intent.
            if (dialIntent.resolveActivity(context.getPackageManager()) != null) {
                dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dialIntent);
            } else {
                Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


   public static int REQUEST_IMAGE_CAPTURE = 1;

    public static void dispatchTakePictureIntent(Activity activity ,Fragment fragment,int REQUEST_IMAGE_CAPTURE) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
      /*  Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(activity);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
           // ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "quay.com.ipos.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (fragment == null) {

                    activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }else {
                    fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }
            }
        }*/
    }


    public static Bitmap onCameraResult(int requestCode, int resultCode, Intent data,int REQUEST_IMAGE_CAPTURE) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            return imageBitmap;
           // mImageView.setImageBitmap(imageBitmap);
        }
        return null;
    }
    static String mCurrentPhotoPath;

    private static File createImageFile(Context activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
