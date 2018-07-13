package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.MyListenerOnitemClick;
import quay.com.ipos.utility.Util;


public class ItemsDetailsPOEditListAdapter extends RecyclerView.Adapter<ItemsDetailsPOEditListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<POItemDetail> stringArrayList;
    private OnItemSelecteListener mListener;
    MyListenerOnitemClick myListener;
    private boolean onBind;
    public ItemsDetailsPOEditListAdapter(Context mContext, ArrayList<POItemDetail> stringArrayList,MyListenerOnitemClick myListener) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;
        this.myListener=myListener;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.po_item_edit, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SurveyViewHolder holder, int position) {
        onBind = true;


        holder.tvPoNumber.setText(stringArrayList.get(position).getTitle());
        if (Util.validateString(stringArrayList.get(position).getPoItemAmount()+""))
        holder.tvAmount.setText(mContext.getResources().getString(R.string.Rs)+" "+Util.indianNumberFormat(Util.numberFormat(Util.round(stringArrayList.get(position).getPoItemAmount(),2)))+"");
        if (Util.validateString(stringArrayList.get(position).getPoItemIGSTValue()+""))
        holder.tvGst.setText(mContext.getResources().getString(R.string.Rs)+" "+Util.indianNumberFormat(Util.numberFormat(Util.round(stringArrayList.get(position).getPoItemIGSTValue(),2)))+"");
        if (stringArrayList.get(position).getPoItemQty()==0){
            holder.tvQty.setText("");
            holder.tvQty.setHint("0");
        }else {
            holder.tvQty.setText(stringArrayList.get(position).getPoItemQty()+"");
            holder.tvQty.setSelection(holder.tvQty.getText().length());
        }
        if (stringArrayList.get(position).getPoItemUnitPrice()==0){
            holder.price.setText("");
            holder.price.setHint("0");
        }else {
            holder.price.setText(((int)stringArrayList.get(position).getPoItemUnitPrice())+"");
            holder.price.setSelection(holder.price.getText().length());
        }
        onBind = false;

        final Timer[] timer = new Timer[1];
        final Timer[] timer1 = new Timer[1];
        holder.tvQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!onBind) {
                  /*  timer[0] = new Timer();
                    timer[0].schedule(new TimerTask() {
                        @Override
                        public void run() {*/
                    if (Util.validateString(holder.price.getText().toString()) && Util.validateString(holder.tvQty.getText().toString())) {
                        myListener.onRowClickedOnItem(holder.getAdapterPosition(), Integer.parseInt(holder.tvQty.getText().toString()), Double.parseDouble(holder.price.getText().toString()));
                    } else if (Util.validateString(holder.tvQty.getText().toString())) {
                        myListener.onRowClickedOnItem(holder.getAdapterPosition(), Integer.parseInt(holder.tvQty.getText().toString()), 0);

                    } else if (Util.validateString(holder.price.getText().toString())) {
                        myListener.onRowClickedOnItem(holder.getAdapterPosition(), 0, Double.parseDouble(holder.price.getText().toString()));

                    }
                  /*      }
                    }, 600);*/
                }
            }
        });

        holder.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!onBind) {
                  /*  timer1[0] = new Timer();
                    timer1[0].schedule(new TimerTask() {
                        @Override
                        public void run() {*/

                    if (Util.validateString(holder.price.getText().toString())  && Util.validateString(holder.tvQty.getText().toString()) ) {
                        myListener.onRowClickedOnItem(holder.getAdapterPosition(), Integer.parseInt(holder.tvQty.getText().toString()), Double.parseDouble(holder.price.getText().toString()));
                    }else if (Util.validateString(holder.tvQty.getText().toString()) ){
                        myListener.onRowClickedOnItem(holder.getAdapterPosition(), Integer.parseInt(holder.tvQty.getText().toString()), 0);

                    }else if (Util.validateString(holder.price.getText().toString()) ){
                        myListener.onRowClickedOnItem(holder.getAdapterPosition(), 0,  Double.parseDouble(holder.price.getText().toString()));

                    }
                     /*   }
                    }, 600);*/
                }
            }
        });


    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName,tvAmount,tvGst,tvPoNumber;

        private EditText tvQty,price;
        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvQty = itemView.findViewById(R.id.tvQty);
            price = itemView.findViewById(R.id.price);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvGst = itemView.findViewById(R.id.tvGst);
            tvPoNumber = itemView.findViewById(R.id.tvPoNumber);


        }
    }
}
