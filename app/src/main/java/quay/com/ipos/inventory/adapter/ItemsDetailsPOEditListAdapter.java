package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.utility.Util;


public class ItemsDetailsPOEditListAdapter extends RecyclerView.Adapter<ItemsDetailsPOEditListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<POItemDetail> stringArrayList;
    private OnItemSelecteListener mListener;
    MyListener myListener;

    public ItemsDetailsPOEditListAdapter(Context mContext, ArrayList<POItemDetail> stringArrayList,MyListener myListener) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;
        this.myListener=myListener;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.po_item_details, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SurveyViewHolder holder, int position) {



        holder.tvPoNumber.setText(stringArrayList.get(position).getTitle());
        holder.tvAmount.setText(stringArrayList.get(position).getPoItemAmount()+"");
        holder.tvGst.setText(stringArrayList.get(position).getPoItemIGSTValue()+"");
        holder.tvQty.setText(stringArrayList.get(position).getPoItemQty()+"");
        holder.price.setText(stringArrayList.get(position).getPoItemUnitPrice()+"");


        holder.tvQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Util.validateString(holder.tvQty.getText().toString()) && !holder.tvQty.getText().toString().equalsIgnoreCase("0"))
                myListener.onRowClicked(holder.getAdapterPosition(), Integer.parseInt(holder.tvQty.getText().toString()));
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

        private TextView tvQty,textViewName,price,tvAmount,tvGst,tvPoNumber;

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
