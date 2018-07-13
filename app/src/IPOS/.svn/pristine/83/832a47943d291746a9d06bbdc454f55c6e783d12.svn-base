package quay.com.ipos.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import quay.com.ipos.R;


public class NetworkErrorDialog {



    //network error dialog
    public static void networkErrorDialogShow(final Context context, final String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(message);
        builder.setCancelable(false);
        builder.setMessage(context.getResources().getString(R.string.internet_connection_error_string));
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });
        builder.show();
    }


    //play service error dialog
    public static void playServiceErrorDialog(final Context context, final String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(message);
        builder.setCancelable(false);
        builder.setMessage(context.getResources().getString(R.string.play_service_error_string));
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });
        builder.show();
    }
}
