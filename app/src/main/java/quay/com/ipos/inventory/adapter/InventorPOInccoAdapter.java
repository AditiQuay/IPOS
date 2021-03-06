package quay.com.ipos.inventory.adapter;

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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnInccoTermsModel;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class InventorPOInccoAdapter extends RecyclerView.Adapter<InventorPOInccoAdapter.ItemView> {
    private static final String TAG = InventorPOInccoAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<GrnInccoTermsModel> grnInccoTermsModels;
    private OnCalculateTotalIncoTermsListener incoTermsListener;
    private boolean onBind;
    private ItemView itemViewO;

    public InventorPOInccoAdapter(Context mContext, ArrayList<GrnInccoTermsModel> grnInccoTermsModels, OnCalculateTotalIncoTermsListener incoTermsListener) {
        this.mContext = mContext;
        this.grnInccoTermsModels = grnInccoTermsModels;
        this.incoTermsListener = incoTermsListener;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_inco_terms_item, parent, false);
        return new ItemView(view, new InventorPOInccoAdapter.MyCustomEditTextListener(), new InventorPOInccoAdapter.MyCustomCheckBoxListener());
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemView holder, int position) {
        this.onBind = true;

        this.itemViewO=holder;
        GrnInccoTermsModel grnInccoTermsModel = grnInccoTermsModels.get(position);
        holder.tvDetailName.setText(grnInccoTermsModel.grnIncoDetail);
        holder.myCustomEditTextListener.updatePosition(position, holder);
        if (grnInccoTermsModels.get(holder.getAdapterPosition()).grnPayAmount==0){
            holder.tvPayAmount.setText("");
            holder.tvPayAmount.setHint("0");
        }else {
            holder.tvPayAmount.setText(grnInccoTermsModels.get(holder.getAdapterPosition()).grnPayAmount + "");
        }


        holder.myCustomCheckBoxListener.updatePosition(position, holder);
        holder.sender.setChecked(grnInccoTermsModel.grnPayBySender);
        holder.reciver.setChecked(grnInccoTermsModel.grnPayByReceiver);
        this.onBind = false;
        holder.delete.setVisibility(View.VISIBLE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grnInccoTermsModels.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),grnInccoTermsModels.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return grnInccoTermsModels.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {
        private TextView tvDetailName;
        private EditText tvPayAmount;
        RadioButton sender, reciver;
        private ImageView delete;
        public MyCustomEditTextListener myCustomEditTextListener;
        private MyCustomCheckBoxListener myCustomCheckBoxListener;

        public ItemView(View itemView, InventorPOInccoAdapter.MyCustomEditTextListener myCustomEditTextListener, InventorPOInccoAdapter.MyCustomCheckBoxListener myCustomCheckBoxListener) {
            super(itemView);
            tvDetailName = itemView.findViewById(R.id.tvDetailName);
            tvPayAmount = itemView.findViewById(R.id.tvPayAmount);
            sender = itemView.findViewById(R.id.sender);
            reciver = itemView.findViewById(R.id.reciver);
            delete=itemView.findViewById(R.id.delete);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.myCustomCheckBoxListener = myCustomCheckBoxListener;

            tvPayAmount.addTextChangedListener(this.myCustomEditTextListener);

            sender.setOnCheckedChangeListener(myCustomCheckBoxListener);
            reciver.setOnCheckedChangeListener(myCustomCheckBoxListener);

        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private ItemView holder;

        public void updatePosition(int position, ItemView holder) {
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

                if (itemViewO.tvPayAmount != null) {
                  // if (itemViewO.tvPayAmount.getText().hashCode() == charSequence.hashCode()) {
                        String editStr = charSequence.toString();
                        if (editStr.isEmpty()) {
                            editStr = "0.0";
                        }
                        grnInccoTermsModels.get(position).grnPayAmount = Double.parseDouble(editStr);
                    //}
                }else {
                    String editStr = charSequence.toString();
                    if (editStr.isEmpty()) {
                        editStr = "0.0";
                    }
                    grnInccoTermsModels.get(position).grnPayAmount = Double.parseDouble(editStr);
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
        private InventorPOInccoAdapter.ItemView holder;

        public void updatePosition(int position, InventorPOInccoAdapter.ItemView holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!onBind) {
                try {

                    switch (compoundButton.getId()) {
                        case R.id.sender:
                            grnInccoTermsModels.get(position).grnPayBySender = b;
                            if (Util.validateString(holder.tvPayAmount.getText().toString()))
                            grnInccoTermsModels.get(position).grnPayAmount = Double.parseDouble(holder.tvPayAmount.getText().toString());
                            grnInccoTermsModels.get(position).grnPayByReceiver = !grnInccoTermsModels.get(position).grnPayBySender;
                            Log.i(TAG, "Notify" + "holder.sender");
                            notifyItemChanged(position);
                            break;
                        case R.id.reciver:
                            grnInccoTermsModels.get(position).grnPayByReceiver = b;
                            if (Util.validateString(holder.tvPayAmount.getText().toString()))
                                grnInccoTermsModels.get(position).grnPayAmount = Double.parseDouble(holder.tvPayAmount.getText().toString());
                            grnInccoTermsModels.get(position).grnPayBySender = !grnInccoTermsModels.get(position).grnPayByReceiver;
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
            for (GrnInccoTermsModel incoTerms : grnInccoTermsModels) {
                total += incoTerms.grnPayAmount;
            }


            incoTermsListener.funIncoTermsTotalCount(total);
        }
    }

    public interface OnCalculateTotalIncoTermsListener {
        void funIncoTermsTotalCount(double totalIncoTerms);
    }
}
