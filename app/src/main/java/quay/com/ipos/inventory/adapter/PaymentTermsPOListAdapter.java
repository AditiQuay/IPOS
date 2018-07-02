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

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.MyListenerPaymentTerms;
import quay.com.ipos.utility.Util;


public class PaymentTermsPOListAdapter extends RecyclerView.Adapter<PaymentTermsPOListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<POPaymentTerms> stringArrayList;
    private OnItemSelecteListener mListener;
    private MyListenerPaymentTerms myListener;
    private boolean onBind;
    public PaymentTermsPOListAdapter(Context mContext, ArrayList<POPaymentTerms> stringArrayList,MyListenerPaymentTerms myListener) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;
        this.myListener=myListener;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.po_payment_terms, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SurveyViewHolder holder, final int position) {
        onBind = true;


        holder.tvQty.setText(stringArrayList.get(position).getPoPaymentTermsDetail());
        holder.percent.setText(stringArrayList.get(position).getPoPaymentTermsPer()+"");
        holder.tvGst.setText(stringArrayList.get(position).getPoPaymentTermsInvoiceDue());


        holder.percent.setEnabled(true);
        onBind = false;
        holder.tvGst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!onBind) {
                    if (Util.validateString(holder.percent.getText().toString())) {

                        myListener.onRowClickedPaymentTerms(position, Double.parseDouble(holder.percent.getText().toString()), holder.tvGst.getText().toString());
                    } else {
                        myListener.onRowClickedPaymentTerms(position, 0, holder.tvGst.getText().toString());

                    }
                }
            }
        });
        holder.percent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!onBind) {
                    if (Util.validateString(holder.percent.getText().toString())) {

                        myListener.onRowClickedPaymentTerms(position, Double.parseDouble(holder.percent.getText().toString()), holder.tvGst.getText().toString());
                    } else {
                        myListener.onRowClickedPaymentTerms(position, 0, holder.tvGst.getText().toString());

                    }
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

        private TextView tvQty;

        private EditText percent,tvGst;
        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvQty = itemView.findViewById(R.id.tvQty);
            percent = itemView.findViewById(R.id.percent);
            tvGst = itemView.findViewById(R.id.tvGst);



        }
    }
}
