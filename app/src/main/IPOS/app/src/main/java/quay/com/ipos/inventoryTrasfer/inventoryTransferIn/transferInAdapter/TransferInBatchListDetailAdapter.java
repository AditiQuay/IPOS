package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInBatchDetailModel;
import quay.com.ipos.listeners.EdittClickListener;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class TransferInBatchListDetailAdapter extends RecyclerView.Adapter<TransferInBatchListDetailAdapter.ItemViewHolder> {
    public Context mContext;
    public List<TransferInBatchDetailModel> transferInBatchDetailModels;
    EdittClickListener edittClickListener;

    public interface NotifyCount {
        void notifyQty();
    }

    TransferInBatchListDetailAdapter.NotifyCount notifyCount;

    public TransferInBatchListDetailAdapter(Context mContext, List<TransferInBatchDetailModel> transferInBatchDetailModels, EdittClickListener edittClickListener, TransferInBatchListDetailAdapter.NotifyCount notifyCount) {
        this.mContext = mContext;
        this.transferInBatchDetailModels = transferInBatchDetailModels;
        this.edittClickListener = edittClickListener;
        this.notifyCount = notifyCount;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inventory_product_item, parent, false);
        return new TransferInBatchListDetailAdapter.ItemViewHolder(view, new TransferInBatchListDetailAdapter.MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final TransferInBatchDetailModel transferInBatchDetailModel = transferInBatchDetailModels.get(position);
        holder.batchNumber.setText(transferInBatchDetailModel.getNumber());

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
        holder.qty.setText(transferInBatchDetailModel.getQty() + "");
        if (transferInBatchDetailModel.getActionTitle().equalsIgnoreCase("Normal".trim()) || transferInBatchDetailModel.getActionTitle().equalsIgnoreCase("Defect".trim())) {
            holder.batchRemark.setVisibility(View.VISIBLE);
            holder.batchRemark.setText(transferInBatchDetailModel.getActionTitle());
        } else {
            holder.batchRemark.setVisibility(View.VISIBLE);
            holder.batchRemark.setText(transferInBatchDetailModel.getActionTitle());
        }
        if (TextUtils.isEmpty(transferInBatchDetailModel.getActionTitle())) {
            holder.batchRemark.setVisibility(View.GONE);
        }
        holder.checkBox1.setChecked(transferInBatchDetailModel.isSelected());
        //set a tag for position
        // holder.checkBox1.setTag(grnProductDetailModel);
        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                /*GRNProductDetailModel grnProductDetailModel1 = (GRNProductDetailModel) cb.getTag();
                grnProductDetailModel1.setSelected(cb.isChecked());*/
                transferInBatchDetailModel.setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return transferInBatchDetailModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox1;
        private TextView batchNumber, batchRemark;
        private EditText qty;
        public TransferInBatchListDetailAdapter.MyCustomEditTextListener myCustomEditTextListener;

        public ItemViewHolder(View itemView, TransferInBatchListDetailAdapter.MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            checkBox1 = itemView.findViewById(R.id.checkBox1);
            batchNumber = itemView.findViewById(R.id.batchNumber);
            batchRemark = itemView.findViewById(R.id.batchRemark);
            qty = itemView.findViewById(R.id.qty);


            //magic code for editText
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.qty.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private TransferInBatchListDetailAdapter.ItemViewHolder holder;

        public void updatePosition(int position, TransferInBatchListDetailAdapter.ItemViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (holder.qty.getText().hashCode() == charSequence.hashCode()) {
                TransferInBatchDetailModel grnProductDetailModel = transferInBatchDetailModels.get(position);
                int qty = 0;
                if (charSequence.toString().length() > 0)
                    qty = Integer.parseInt(charSequence.toString());
                grnProductDetailModel.setQty(qty);
                transferInBatchDetailModels.set(position, grnProductDetailModel);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {
            notifyCount.notifyQty();
        }

    }
}
