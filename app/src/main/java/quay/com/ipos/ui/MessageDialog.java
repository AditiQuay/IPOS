package quay.com.ipos.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 21-05-2018.
 */

public class MessageDialog extends Dialog
    {

        private int mCallType = -1;

        public interface MessageDialogListener {
            public void onDialogPositiveClick(Dialog dialog, int mCallType);
            public void onDialogNegetiveClick(Dialog dialog,int mCallType);
        }

        private String yesButton;
        private String noButton;
        private String mTitle;
        private String mMessage;
        private MessageDialogListener mListener;
        Context mContext;


        public MessageDialog(Context mContext, String title, String message, String yesButton, String noButton, MessageDialogListener listener, final int mCallType)
        {
            super(mContext);
            dismiss();
            this.mContext = mContext;
            this.mTitle = title;
            this.mMessage = message;
            this.mListener = listener;
            this.mCallType = mCallType;
            this.yesButton = yesButton;
            this.noButton = noButton;


       //     ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(mContext,);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View dialogView = inflater.inflate(R.layout.dialog_message, null);
            TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDialogMessage = dialogView.findViewById(R.id.tvDialogMessage);
            TextView tvMessageOK = dialogView.findViewById(R.id.tvMessageOK);
            TextView tvMessageCancel = dialogView.findViewById(R.id.tvMessageCancel);


                if(Util.validateString(mTitle))
                    tvDialogTitle.setText(mTitle);
                else
                    tvDialogTitle.setVisibility(View.GONE);



                if(Util.validateString(mMessage))
                    tvDialogMessage.setText(mMessage);
                else
                    tvDialogMessage.setVisibility(View.GONE);


            if(yesButton!=null){
                tvMessageOK.setText(yesButton);
            }else {
                tvMessageOK.setVisibility(View.GONE);
            }

            if(noButton!=null){
                tvMessageCancel.setText(noButton);
            }else {
                tvMessageCancel.setVisibility(View.GONE);
            }

            tvMessageOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        mListener.onDialogPositiveClick(MessageDialog.this,mCallType);
                    }
                    dismiss();
                }
            });

            tvMessageCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        mListener.onDialogNegetiveClick(MessageDialog.this,mCallType);
                    }
                    dismiss();
                }
            });
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(dialogView);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setView(dialogView);

            alertDialog.create();
            setCanceledOnTouchOutside(false);


            if (isShowing()) {
                dismiss();
            } else {
                show();
            }

        }

}
