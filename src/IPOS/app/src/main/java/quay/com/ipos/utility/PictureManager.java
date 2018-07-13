package quay.com.ipos.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import quay.com.ipos.R;

import static android.app.Activity.RESULT_OK;

public class PictureManager {
    private static final int IMAGE_PICK = 1;
    public static final int IMAGE_CAPTURE = 1212;
    private static final String TAG = PictureManager.class.getSimpleName();
    private static final CharSequence FILENAME_PROFILE = "profile";
    private static final int MY_PERMISSIONS_REQUEST_PICURE_SAVE = 111;
    private Context context;
    private String mCurrentPhotoPath;
    private String mFilePath;
    private Bitmap theBitmap = null;
    private int reqFor;
    private GetPicURLListener listener;

    public PictureManager(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String mFilePath) {
        this.mFilePath = mFilePath;
    }

    private Fragment fragment;


    public void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_PICURE_SAVE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void onClickCamera(String path, int reqFor, GetPicURLListener listener) {


        //  Toast.makeText(context, ""+path, Toast.LENGTH_SHORT).show();
        this.listener = listener;
        setFilePath(path);
        setReqFor(reqFor);
       /* Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity)getContext()).startActivityForResult(cameraIntent, reqCOde);*/
        try {
            dispatchTakePictureIntent(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*Methods for imageCapture */

    public void dispatchTakePictureIntent(String path) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(path);

            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(createImageFile(path));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (fragment == null)

                    ((Activity) getContext()).startActivityForResult(takePictureIntent, IMAGE_CAPTURE);
                else {
                    fragment.startActivityForResult(takePictureIntent, IMAGE_CAPTURE);

                }
            }

        }

    }

    private File createImageFile(String path) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //from gallery
        if (requestCode == IMAGE_PICK) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri resultUri = data.getData();
                    if (resultUri != null) {
                        // updateImage(resultUri.toString());
                        //Date cDate = new Date();
                        // String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                        getBitmapFromUri(resultUri, getFilePath());


                    } else {
                        Toast.makeText(getContext(), "Image not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        //from camera
        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                handleBigCameraPhoto();
            }
        }

    }

    public void getBitmapFromUri(final Object obj, final String mFileName) {


        new AsyncTask<Void, Void, String>() {
            String fileName = mFileName;

            @Override
            protected String doInBackground(Void... params) {
//                Looper.prepare();
                try {
                    if (obj instanceof Bitmap) {
                        theBitmap = (Bitmap) obj;
                    } else {
                        //android.view.Display display = getWindowManager().getDefaultDisplay();
                        int targetW = getContext().getResources().getDimensionPixelSize(R.dimen.profile_pic_width);
                        int targetH = getContext().getResources().getDimensionPixelSize(R.dimen.profile_pic_height);
                        //  int targetW = display.getWidth();
                        //  int targetH = display.getHeight();
                        theBitmap = Glide.
                                with(getContext()).
                                load(obj)
                                .asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).override(targetW, targetH).
                                        into(-1, -1).
                                        get();
                    }
                    if (null != theBitmap) {
                        // The full bitmap should be available here
                        // profilePicture.setImageBitmap(theBitmap);
                        Log.d(TAG, "Image loaded");
                        String filePath = saveToInternalStorage(theBitmap, fileName);
                        return filePath;

                    }
                } catch (final ExecutionException e) {
                    Log.e(TAG, e.getMessage());
                } catch (final InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String filePath) {
                if (null != filePath) {
                    // The full bitmap should be available here
                    // profilePicture.setImageBitmap(theBitmap);
                    Log.d(TAG, "Image loaded");
                    //String filePath = saveToInternalStorage(theBitmap);
                    if (filePath.contains(FILENAME_PROFILE)) {
                        // uploadToServer(filePath);
                    } else {
                        listener.onGetPicURL(filePath, getReqFor());
                        // uploadToServerWeight(filePath);
                    }
                }
            }

        }.execute();

    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            galleryAddPic();
            //Date cDate = new Date();
            //String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
            getBitmapFromUri(mCurrentPhotoPath, getFilePath());
            mCurrentPhotoPath = null;
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public String saveToInternalStorage(Bitmap bitmapImage, String fileName) {
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Images", Context.MODE_PRIVATE);
        // Create imageDir
        //  File mypath = new File(directory, "profile.jpg");
        //  String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //  String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // file = new File(file, path + "_" + timeStamp + ".jpg");
        Log.i("initial size", BitmapCompat.getAllocationByteCount(bitmapImage) + "");

        File mypath = new File(directory, fileName + "_" + timeStamp + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 60, fos);

            Log.i("final size", BitmapCompat.getAllocationByteCount(bitmapImage) + "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //  return directory.getAbsolutePath();
        // return activity.getFilesDir()+File.separator+"profile.jpg";
        return mypath.getAbsolutePath();
    }


    public int getReqFor() {
        return reqFor;
    }

    public void setReqFor(int reqFor) {
        this.reqFor = reqFor;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


    public interface GetPicURLListener {
        void onGetPicURL(String picUrl, int reqFor);
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PICURE_SAVE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
