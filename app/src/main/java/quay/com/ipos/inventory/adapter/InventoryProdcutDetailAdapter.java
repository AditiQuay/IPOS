package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GRNProductDetailModel;
import quay.com.ipos.listeners.EdittClickListener;

/**
 * Created by niraj.kumar on 6/21/2018.
 */

public class InventoryProdcutDetailAdapter extends RecyclerView.Adapter<InventoryProdcutDetailAdapter.ItemViewHolder> {
    private static final String TAG = InventoryProdcutDetailAdapter.class.getSimpleName();
    public Context mContext;
    private List<GRNProductDetailModel> list;
    EdittClickListener edittClickListener;


    public InventoryProdcutDetailAdapter(Context mContext, List<GRNProductDetailModel> list, EdittClickListener edittClickListener) {
        this.mContext = mContext;
        this.list = list;
        this.edittClickListener = edittClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inventory_product_item, parent, false);

        return new InventoryProdcutDetailAdapter.ItemViewHolder(view, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final GRNProductDetailModel grnProductDetailModel = list.get(position);
        holder.batchNumber.setText(grnProductDetailModel.getNumber());

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
        holder.qty.setText(grnProductDetailModel.getQty() + "");

        holder.checkBox1.setChecked(grnProductDetailModel.isSelected());
        //set a tag for position
        holder.checkBox1.setTag(grnProductDetailModel);
        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                GRNProductDetailModel grnProductDetailModel1 = (GRNProductDetailModel) cb.getTag();
                grnProductDetailModel1.setSelected(cb.isChecked());
                grnProductDetailModel.setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox1;
        private TextView batchNumber;
        private EditText qty;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ItemViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            checkBox1 = itemView.findViewById(R.id.checkBox1);
            batchNumber = itemView.findViewById(R.id.batchNumber);
            qty = itemView.findViewById(R.id.qty);


            //magic code for editText
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.qty.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private ItemViewHolder holder;

        public void updatePosition(int position, ItemViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (holder.qty.getText().hashCode() == charSequence.hashCode()) {
                GRNProductDetailModel grnProductDetailModel = list.get(position);
                int qty = 0;
                if (charSequence.toString().length() > 0)
                    qty = Integer.parseInt(charSequence.toString());
                grnProductDetailModel.setQty(qty);
                list.set(position, grnProductDetailModel);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }

}
