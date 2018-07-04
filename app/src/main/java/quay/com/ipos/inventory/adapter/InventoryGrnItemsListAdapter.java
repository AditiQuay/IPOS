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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.listeners.DataUpdateListener;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class InventoryGrnItemsListAdapter extends RecyclerView.Adapter<InventoryGrnItemsListAdapter.ItmeViewHolder> {
    private Context mContext;
    private ArrayList<GrnItemQtyModel> grnItemQtyModels;
    MyListener myListener;
    DataUpdateListener dataUpdateListener;
    private boolean onBind;
    private Timer timer;

    public InventoryGrnItemsListAdapter(Context mContext, ArrayList<GrnItemQtyModel> grnItemQtyModels, MyListener myListener, DataUpdateListener dataUpdateListener) {
        this.mContext = mContext;
        this.grnItemQtyModels = grnItemQtyModels;
        this.myListener = myListener;
        this.dataUpdateListener = dataUpdateListener;
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
        return grnItemQtyModels.size();
    }

    public class ItmeViewHolder extends RecyclerView.ViewHolder {
        private final View item;
        private TextView tvMaterialName, tvOpenQty, tvBalanceQty;
        private EditText tvInQty, tvApQty;
        private ImageView imageViewStatus;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ItmeViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
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
                        if (Util.validateString(holder.tvInQty.getText().toString()) && !holder.tvInQty.getText().toString().equalsIgnoreCase("0")) {
                            String openQty = holder.tvOpenQty.getText().toString();
                            final String inQty = holder.tvInQty.getText().toString();
                            final String appQty = holder.tvApQty.getText().toString();


                            final int balanceQty = Integer.parseInt(openQty) - (Integer.parseInt(inQty) + Integer.parseInt(appQty));
                            //setting data
                            grnItemQtyModels.get(position).setOpenQty(Double.parseDouble(openQty));
                            grnItemQtyModels.get(position).setInQty(Double.parseDouble(inQty));
                            grnItemQtyModels.get(position).setApQty(Double.parseDouble(appQty));
                            grnItemQtyModels.get(position).setBalanceQty(balanceQty);

                            holder.tvBalanceQty.setText(grnItemQtyModels.get(position).getBalanceQty() + "");
                            holder.imageViewStatus.setVisibility(View.VISIBLE);

                            // user typed: start the timer
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    dataUpdateListener.onUpdateData(holder.getAdapterPosition(), Integer.parseInt(inQty), Integer.parseInt(appQty), balanceQty);
                                    // do your actual work here
                                }
                            }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask
                        }

                    }
                    if (holder.tvApQty.getText().hashCode() == editable.hashCode()) {
                        if (Util.validateString(holder.tvApQty.getText().toString()) && !holder.tvApQty.getText().toString().equalsIgnoreCase("0")) {
                            String openQty = holder.tvOpenQty.getText().toString();
                            final String inQty = holder.tvInQty.getText().toString();
                            final String appQty = holder.tvApQty.getText().toString();

                            final int balanceQty = Integer.parseInt(openQty) - (Integer.parseInt(inQty) + Integer.parseInt(appQty));
                            holder.tvBalanceQty.setText(balanceQty + "");

                            //setting data
                            grnItemQtyModels.get(position).setOpenQty(Double.parseDouble(openQty));
                            grnItemQtyModels.get(position).setInQty(Double.parseDouble(inQty));
                            grnItemQtyModels.get(position).setApQty(Double.parseDouble(appQty));
                            grnItemQtyModels.get(position).setBalanceQty(balanceQty);

                            holder.tvBalanceQty.setText(grnItemQtyModels.get(position).getBalanceQty() + "");
                            holder.imageViewStatus.setVisibility(View.VISIBLE);
                            // user typed: start the timer
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    dataUpdateListener.onUpdateData(holder.getAdapterPosition(), Integer.parseInt(inQty), Integer.parseInt(appQty), balanceQty);

                                    // do your actual work here
                                }
                            }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask


                        }

                    }
                    if (holder.tvBalanceQty.getText().hashCode() == editable.hashCode()) {
                        holder.imageViewStatus.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
