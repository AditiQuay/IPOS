package quay.com.ipos.utility;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import quay.com.ipos.R;

public class ShareWorldUtil {
    private static final String TAG = ShareWorldUtil.class.getSimpleName() ;

    public static void  dialNumber(Application context, String phoneNo) {
         // Use format with "tel:" and phone number to create phoneNumber.
        String phoneNumber = String.format("tel: %s", phoneNo);
        // Create the intent.
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the data for the intent as the phone number.
        dialIntent.setData(Uri.parse(phoneNumber));
        // If package resolves to an app, send intent.
        if (dialIntent.resolveActivity(context.getPackageManager()) != null) {
            dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context. startActivity(dialIntent);
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
        }
    }
}
