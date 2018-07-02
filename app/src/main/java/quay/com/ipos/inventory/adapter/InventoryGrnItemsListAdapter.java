package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.listeners.MyListener;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class InventoryGrnItemsListAdapter extends RecyclerView.Adapter<InventoryGrnItemsListAdapter.ItmeViewHolder> {
    private Context mContext;
    private ArrayList<GrnItemQtyModel> grnItemQtyModels;
    MyListener myListener;
    private boolean onBind;

    public InventoryGrnItemsListAdapter(Context mContext, ArrayList<GrnItemQtyModel> grnItemQtyModels, MyListener myListener) {
        this.mContext = mContext;
        this.grnItemQtyModels = grnItemQtyModels;
        this.myListener = myListener;
    }

    @NonNull
    @Override
    public ItmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_item_details, parent, false);
        return new InventoryGrnItemsListAdapter.ItmeViewHolder(view, new InventoryGrnItemsListAdapter.MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull final ItmeViewHolder holder, int position) {
        this.onBind = true;

        GrnItemQtyModel grnItemQtyModel = grnItemQtyModels.get(position);

        SpannableString content = new SpannableString(grnItemQtyModel.getMaterialName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.tvMaterialName.setText(content);

        holder.tvOpenQty.setText((int) grnItemQtyModel.getOpenQty() + "");
        holder.tvInQty.setText((int) grnItemQtyModel.getInQty() + "");
        holder.tvApQty.setText((int) grnItemQtyModel.getApQty() + "");
        holder.tvBalanceQty.setText((int) grnItemQtyModel.getBalanceQty() + "");

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onRowClicked(holder.getAdapterPosition());
            }
        });

        this.onBind = false;
    }

    @Override
    public int getItemCount() {
        return grnItemQtyModels.size();
    }

    public class ItmeViewHolder extends RecyclerView.ViewHolder {
        private final View item;
        private TextView tvMaterialName, tvOpenQty, tvBalanceQty;
        private EditText tvInQty, tvApQty;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ItmeViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            item = itemView;
            tvMaterialName = itemView.findViewById(R.id.tvMaterialName);
            tvOpenQty = itemView.findViewById(R.id.tvOpenQty);
            tvInQty = itemView.findViewById(R.id.tvInQty);
            tvApQty = itemView.findViewById(R.id.tvApQty);
            tvBalanceQty = itemView.findViewById(R.id.tvBalanceQty);

            this.myCustomEditTextListener = myCustomEditTextListener;
            this.tvInQty.addTextChangedListener(myCustomEditTextListener);
            this.tvApQty.addTextChangedListener(myCustomEditTextListener);


        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private InventoryGrnItemsListAdapter.ItmeViewHolder holder;

        public void updatePosition(int position, InventoryGrnItemsListAdapter.ItmeViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!onBind) {
                try {


                    String text = editable.toString();
                    if (holder.tvInQty.getText().hashCode() == editable.hashCode()) {
                        int inQt = Integer.parseInt(holder.tvInQty.getText().toString());

                        if (inQt != 0) {
                            String openQty = holder.tvOpenQty.getText().toString();
                            String inQty = holder.tvInQty.getText().toString();
                            String appQty = holder.tvApQty.getText().toString();

                            int balanceQty = Integer.parseInt(openQty) - (Integer.parseInt(inQty) + Integer.parseInt(appQty));
                            holder.tvBalanceQty.setText(balanceQty + "");

                        }

                    }
                    if (holder.tvApQty.getText().hashCode() == editable.hashCode()) {
                        int tvAppQty = Integer.parseInt(holder.tvApQty.getText().toString());
                        if (tvAppQty != 0) {
                            String openQty = holder.tvOpenQty.getText().toString();
                            String inQty = holder.tvInQty.getText().toString();
                            String appQty = holder.tvApQty.getText().toString();

                            int balanceQty = Integer.parseInt(openQty) - (Integer.parseInt(inQty) + Integer.parseInt(appQty));
                            holder.tvBalanceQty.setText(balanceQty + "");
                        }

                    }
                } catch (Exception e) {

                }

            }

        }

    }
}
