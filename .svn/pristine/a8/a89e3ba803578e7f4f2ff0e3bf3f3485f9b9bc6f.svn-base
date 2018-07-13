package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerm;

/**
 * Created by deepa.kumar on 6/25/2018.
 */

public class DDRIncoTermsAdapter extends RecyclerView.Adapter<DDRIncoTermsAdapter.ViewHolder> {
    private static final String TAG = DDRIncoTermsAdapter.class.getSimpleName();
    private Context mContext;
    private List<DDRIncoTerm> list;
    private OnCalculateTotalIncoTermsListener incoTermsListener;
    private boolean onBind;

    public DDRIncoTermsAdapter(Context mContext, List<DDRIncoTerm> list, OnCalculateTotalIncoTermsListener incoTermsListener) {
        this.mContext = mContext;
        this.list = list;
        this.incoTermsListener = incoTermsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ddr_adapter_incoterms, parent, false);
        return new ViewHolder(view, new DDRIncoTermsAdapter.MyCustomEditTextListener(), new MyCustomCheckBoxListener());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.onBind = true;
        DDRIncoTerm incoTerms = list.get(position);
        holder.tvDetailName.setText(incoTerms.grnIncoDetail);

        holder.myCustomEditTextListener.updatePosition(position, holder);
        holder.editPayAmount.setText(list.get(holder.getAdapterPosition()).grnPayAmount + "");


        holder.myCustomCheckBoxListener.updatePosition(position, holder);
        holder.sender.setChecked(list.get(holder.getAdapterPosition()).grnPayBySender);
        holder.reciver.setChecked(list.get(holder.getAdapterPosition()).grnPayByReceiver);
        this.onBind = false;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetailName;
        private EditText editPayAmount;
        RadioButton sender;
        RadioButton reciver;
        public MyCustomEditTextListener myCustomEditTextListener;
        private MyCustomCheckBoxListener myCustomCheckBoxListener;

        public ViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener, MyCustomCheckBoxListener myCustomCheckBoxListener) {
            super(itemView);
            tvDetailName = itemView.findViewById(R.id.tvDetailName);
            editPayAmount = itemView.findViewById(R.id.tvPayAmount);
            sender = itemView.findViewById(R.id.sender);
            reciver = itemView.findViewById(R.id.reciver);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.myCustomCheckBoxListener = myCustomCheckBoxListener;

            editPayAmount.addTextChangedListener(this.myCustomEditTextListener);


            sender.setOnCheckedChangeListener(myCustomCheckBoxListener);
            reciver.setOnCheckedChangeListener(myCustomCheckBoxListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try {

                if (holder.editPayAmount != null) {
                    if (holder.editPayAmount.getText().hashCode() == charSequence.hashCode()) {
                        String editStr = charSequence.toString();
                        if (editStr.isEmpty()) {
                            editStr = "0.0";
                        }
                        list.get(position).grnPayAmount = Double.parseDouble(editStr);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            calIncoTerms();
            // no op

        }
    }


    private class MyCustomCheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        private int position;
        private ViewHolder holder;

        public void updatePosition(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!onBind) {
                try {

                    switch (compoundButton.getId()) {
                        case R.id.sender:
                            list.get(position).grnPayBySender = b;
                            list.get(position).grnPayByReceiver = !list.get(position).grnPayBySender;
                            Log.i(TAG, "Notify" + "holder.sender");
                            notifyItemChanged(position);
                            break;
                        case R.id.reciver:
                            list.get(position).grnPayByReceiver = b;
                            list.get(position).grnPayBySender = !list.get(position).grnPayByReceiver;
                            Log.i(TAG, "Notify" + "holder.reciver");
                            notifyItemChanged(position);
                            break;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void calIncoTerms() {
        if (incoTermsListener != null) {
            double total = 0.0;
            for (DDRIncoTerm incoTerms : list) {
                total += incoTerms.grnPayAmount;
            }


            incoTermsListener.funIncoTermsTotalCount(total);
        }
    }

    public interface OnCalculateTotalIncoTermsListener {
        void funIncoTermsTotalCount(double totalIncoTerms);
    }

}
