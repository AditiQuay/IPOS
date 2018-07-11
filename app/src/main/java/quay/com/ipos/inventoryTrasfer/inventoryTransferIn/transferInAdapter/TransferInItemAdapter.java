package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.InventoryGrnItemsListAdapter;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInItemQtyModel;
import quay.com.ipos.listeners.DataUpdateListener;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 7/10/2018.
 */

public class TransferInItemAdapter extends RecyclerView.Adapter<TransferInItemAdapter.MyView> {
    private ArrayList<TransferInItemQtyModel> transferInItemQtyModels;
    private Context mContext;
    MyListener myListener;
    DataUpdateListener dataUpdateListener;
    private boolean onBind;
    private Timer timer;
    public TransferInItemAdapter(Context context,ArrayList<TransferInItemQtyModel> transferInItemQtyModels, MyListener myListener, DataUpdateListener dataUpdateListener){
        this.mContext = context;
        this.transferInItemQtyModels = transferInItemQtyModels;
        this.myListener = myListener;
        this.dataUpdateListener = dataUpdateListener;
    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_item_details, parent, false);
        return new TransferInItemAdapter.MyView(view, new TransferInItemAdapter.MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position) {
        this.onBind = true;

        TransferInItemQtyModel grnItemQtyModel = transferInItemQtyModels.get(position);

        SpannableString content = new SpannableString(grnItemQtyModel.getMaterialName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.tvMaterialName.setText(content);

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
        holder.tvOpenQty.setText((int) grnItemQtyModel.getOpenQty() + "");


        holder.tvInQty.setText((int) grnItemQtyModel.getInQty() + "");
        holder.tvInQty.setSelection(holder.tvInQty.getText().length());

        holder.tvApQty.setText((int) grnItemQtyModel.getApQty() + "");
        holder.tvApQty.setSelection(holder.tvApQty.getText().length());

        holder.tvBalanceQty.setText((int) grnItemQtyModel.getBalanceQty() + "");

        holder.tvMaterialName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onRowClicked(holder.getAdapterPosition());
            }
        });

        this.onBind = false;
    }

    @Override
    public int getItemCount() {
        return transferInItemQtyModels.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public final View item;
        public TextView tvMaterialName, tvOpenQty, tvBalanceQty;
        public EditText tvInQty, tvApQty;
        public ImageView imageViewStatus;
        public TransferInItemAdapter.MyCustomEditTextListener myCustomEditTextListener;

        public MyView(View itemView, TransferInItemAdapter.MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            item = itemView;
            tvMaterialName = itemView.findViewById(R.id.tvMaterialName);
            tvOpenQty = itemView.findViewById(R.id.tvOpenQty);
            tvInQty = itemView.findViewById(R.id.tvInQty);


            tvApQty = itemView.findViewById(R.id.tvApQty);
            tvApQty.setSelection(tvApQty.getText().length());

            tvBalanceQty = itemView.findViewById(R.id.tvBalanceQty);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);


            this.myCustomEditTextListener = myCustomEditTextListener;
            this.tvInQty.addTextChangedListener(myCustomEditTextListener);
            this.tvApQty.addTextChangedListener(myCustomEditTextListener);
            this.tvBalanceQty.addTextChangedListener(myCustomEditTextListener);


        }
    }
    public class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private TransferInItemAdapter.MyView holder;

        public void updatePosition(int position, TransferInItemAdapter.MyView holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            // user is typing: reset already started timer (if existing)
            if (timer != null) {
                timer.cancel();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!onBind) {
                try {
                    String text = editable.toString();
                    if (holder.tvInQty.getText().hashCode() == editable.hashCode()) {
                        if (Util.validateString(holder.tvInQty.getText().toString())) {
                            String openQty = holder.tvOpenQty.getText().toString();
                            final String inQty = holder.tvInQty.getText().toString();
                            final String appQty = holder.tvApQty.getText().toString();


                            final int balanceQty = Integer.parseInt(openQty) - (Integer.parseInt(inQty) + Integer.parseInt(appQty));
                            //setting data
                            transferInItemQtyModels.get(position).setOpenQty(Double.parseDouble(openQty));
                            transferInItemQtyModels.get(position).setInQty(Double.parseDouble(inQty));
                            transferInItemQtyModels.get(position).setApQty(Double.parseDouble(appQty));
                            transferInItemQtyModels.get(position).setBalanceQty(balanceQty);

                            holder.tvBalanceQty.setText(transferInItemQtyModels.get(position).getBalanceQty() + "");
//                            holder.imageViewStatus.setVisibility(View.VISIBLE);

                            // user typed: start the timer
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
//                                    dataUpdateListener.onUpdateData(holder.getAdapterPosition(), Integer.parseInt(inQty), Integer.parseInt(appQty), balanceQty);
                                    // do your actual work here
                                }
                            }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask
                        }

                    }
                    if (holder.tvApQty.getText().hashCode() == editable.hashCode()) {
                        if (Util.validateString(holder.tvApQty.getText().toString())) {
                            String openQty = holder.tvOpenQty.getText().toString();
                            final String inQty = holder.tvInQty.getText().toString();
                            final String appQty = holder.tvApQty.getText().toString();

                            final int balanceQty = Integer.parseInt(openQty) - (Integer.parseInt(inQty) + Integer.parseInt(appQty));
                            holder.tvBalanceQty.setText(balanceQty + "");

                            //setting data
                            transferInItemQtyModels.get(position).setOpenQty(Double.parseDouble(openQty));
                            transferInItemQtyModels.get(position).setInQty(Double.parseDouble(inQty));
                            transferInItemQtyModels.get(position).setApQty(Double.parseDouble(appQty));
                            transferInItemQtyModels.get(position).setBalanceQty(balanceQty);

                            holder.tvBalanceQty.setText(transferInItemQtyModels.get(position).getBalanceQty() + "");
//                            holder.imageViewStatus.setVisibility(View.VISIBLE);
                            // user typed: start the timer
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
//                                    dataUpdateListener.onUpdateData(holder.getAdapterPosition(), Integer.parseInt(inQty), Integer.parseInt(appQty), balanceQty);

                                    // do your actual work here
                                }
                            }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask


                        }

                    }
                    if (holder.tvBalanceQty.getText().hashCode() == editable.hashCode()) {
//                        holder.imageViewStatus.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
