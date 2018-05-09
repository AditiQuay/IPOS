package quay.com.ipos.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MessageDialogFragment extends DialogFragment {
    private int mCallType = -1;

    public interface MessageDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog,int mCallType);
        public void onDialogNegetiveClick(DialogFragment dialog,int mCallType);
    }

    private String yesButton;
    private String noButton;
    private String mTitle;
    private String mMessage;
    private MessageDialogListener mListener;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public static MessageDialogFragment newInstance(String title, String message,String yesButton,String noButton, MessageDialogListener listener,int mCallType) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        fragment.mTitle = title;
        fragment.mMessage = message;
        fragment.mListener = listener;
        fragment.mCallType = mCallType;
        fragment.yesButton = yesButton;
        fragment.noButton = noButton;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mMessage)
                .setTitle(mTitle).setCancelable(false);

        if(yesButton!=null)
        builder.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(mListener != null) {
                    mListener.onDialogPositiveClick(MessageDialogFragment.this,mCallType);
                }
            }
        });

        if(noButton!=null)
        builder.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(mListener != null) {
                    mListener.onDialogNegetiveClick(MessageDialogFragment.this,mCallType);
                }
            }
        });

        return builder.create();
    }
}
