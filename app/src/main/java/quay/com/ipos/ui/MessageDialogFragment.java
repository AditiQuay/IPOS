package quay.com.ipos.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import quay.com.ipos.R;

public class MessageDialogFragment extends DialogFragment {
//    private int mCallType = -1;
//
//    public interface MessageDialogListener {
//        public void onDialogPositiveClick(DialogFragment dialog,int mCallType);
//        public void onDialogNegetiveClick(DialogFragment dialog,int mCallType);
//    }
//
//    private String yesButton;
//    private String noButton;
//    private String mTitle;
//    private String mMessage;
//    private MessageDialogListener mListener;
//    Context mContext;
//
//    public void onCreate(Bundle state) {
//        super.onCreate(state);
//        setRetainInstance(true);
//    }
//
//    public static MessageDialogFragment newInstance(Context mContext,String title, String message, String yesButton, String noButton, MessageDialogListener listener, int mCallType) {
//        MessageDialogFragment fragment = new MessageDialogFragment();
//        fragment.mContext = mContext;
//        fragment.mTitle = title;
//        fragment.mMessage = message;
//        fragment.mListener = listener;
//        fragment.mCallType = mCallType;
//        fragment.yesButton = yesButton;
//        fragment.noButton = noButton;
//        return fragment;
//    }
//
//    public MessageDialogFragment(){
//        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.app_custom_dialog_theme);
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View dialogView = inflater.inflate(R.layout.dialog_message, null);
//        TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
//        TextView tvDialogMessage = dialogView.findViewById(R.id.tvDialogMessage);
//        TextView tvMessageOK = dialogView.findViewById(R.id.tvMessageOK);
//        TextView tvMessageCancel = dialogView.findViewById(R.id.tvMessageCancel);
//
//        if(mTitle!=null){
//            if(mTitle.trim().equalsIgnoreCase(""))
//                tvDialogTitle.setText(mTitle);
//            else
//                tvDialogTitle.setVisibility(View.GONE);
//        }else
//            tvDialogTitle.setVisibility(View.GONE);
//
//        if(mMessage!=null){
//            if(mMessage.trim().equalsIgnoreCase(""))
//                tvDialogMessage.setText(mMessage);
//            else
//                tvDialogMessage.setVisibility(View.GONE);
//        }else
//            tvDialogMessage.setVisibility(View.GONE);
//
//        if(yesButton!=null){
//            tvMessageOK.setText(yesButton);
//        }else {
//            tvMessageOK.setVisibility(View.GONE);
//        }
//
//        if(noButton!=null){
//            tvMessageCancel.setText(noButton);
//        }else {
//            tvMessageCancel.setVisibility(View.GONE);
//        }
//
//        tvMessageOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mListener != null) {
//                    mListener.onDialogPositiveClick(MessageDialogFragment.this,mCallType);
//                }
//            }
//        });
//
//        tvMessageCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mListener != null) {
//                    mListener.onDialogNegetiveClick(MessageDialogFragment.this,mCallType);
//                }
//            }
//        });
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
//        builder.setView(dialogView);
//        builder.setCancelable(false);
////        builder.setMessage(mMessage)
////                .setTitle(mTitle).setCancelable(false);
//
//        builder.create();
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.app_custom_dialog_theme);
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View dialogView = inflater.inflate(R.layout.dialog_message, null);
//        TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
//        TextView tvDialogMessage = dialogView.findViewById(R.id.tvDialogMessage);
//        TextView tvMessageOK = dialogView.findViewById(R.id.tvMessageOK);
//        TextView tvMessageCancel = dialogView.findViewById(R.id.tvMessageCancel);
//
//        if(mTitle!=null){
//            if(mTitle.trim().equalsIgnoreCase(""))
//                tvDialogTitle.setText(mTitle);
//            else
//                tvDialogTitle.setVisibility(View.GONE);
//        }else
//            tvDialogTitle.setVisibility(View.GONE);
//
//        if(mMessage!=null){
//            if(mMessage.trim().equalsIgnoreCase(""))
//                tvDialogMessage.setText(mMessage);
//            else
//                tvDialogMessage.setVisibility(View.GONE);
//        }else
//            tvDialogMessage.setVisibility(View.GONE);
//
//        if(yesButton!=null){
//            tvMessageOK.setText(yesButton);
//        }else {
//            tvMessageOK.setVisibility(View.GONE);
//        }
//
//        if(noButton!=null){
//            tvMessageCancel.setText(noButton);
//        }else {
//            tvMessageCancel.setVisibility(View.GONE);
//        }
//
//        tvMessageOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mListener != null) {
//                    mListener.onDialogPositiveClick(MessageDialogFragment.this,mCallType);
//                }
//            }
//        });
//
//        tvMessageCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mListener != null) {
//                    mListener.onDialogNegetiveClick(MessageDialogFragment.this,mCallType);
//                }
//            }
//        });
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
//        builder.setView(dialogView);
//        builder.setCancelable(false);
////        builder.setMessage(mMessage)
////                .setTitle(mTitle).setCancelable(false);
//
//        return builder.create();
//    }
}
