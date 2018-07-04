package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
    public void onBindViewHolder(final SurveyViewHolder holder,  int position) {
        onBind = true;


        holder.tvQty.setText(stringArrayList.get(position).getPoPaymentTermsDetail());
        if ( stringArrayList.get(position).getPoPaymentTermsPer()==0){
            holder.percent.setText("");
            holder.percent.setHint("0");
        }else {
            holder.percent.setText(stringArrayList.get(position).getPoPaymentTermsPer()+"");
            holder.percent.setSelection(holder.percent.getText().length());
        }


        if (stringArrayList.get(position).getPoPaymentTermsInvoiceDue().contains("Immediate") ||stringArrayList.get(position).getPoPaymentTermsInvoiceDue().contains("Immeiate")){
            holder.tvGst.setHint("Immediate");
            holder.tvGst.setText("Immediate");
            holder.tvGst.setEnabled(false);
        }else {
            holder.tvGst.setHint("days");
            holder.tvGst.setEnabled(true);
        }

        if (stringArrayList.get(position).getPoPaymentTermsInvoiceDue()!=null && stringArrayList.get(position).getPoPaymentTermsInvoiceDue().equalsIgnoreCase("0 Days")){
            holder.tvGst.setText("");
        }else {
            if (stringArrayList.get(position).getPoPaymentTermsInvoiceDue()!=null && !stringArrayList.get(position).getPoPaymentTermsInvoiceDue().contains("Immediate")) {
                if (Util.validateString(stringArrayList.get(position).getPoPaymentTermsInvoiceDue()))
                    holder.tvGst.setText(stringArrayList.get(position).getPoPaymentTermsInvoiceDue().replace(" ", "") + " days");
                holder.tvGst.setSelection(holder.tvGst.getText().length());
            }
        }



        holder.delete.setVisibility(View.VISIBLE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(),0,"payment");

            }
        });

        holder.percent.setEnabled(true);
        onBind = false;
        final Timer[] timer = new Timer[1];
        final Timer[] timer1 = new Timer[1];
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
                 /*   timer[0] = new Timer();
                    timer[0].schedule(new TimerTask() {
                        @Override
                        public void run() {*/
                            if (Util.validateString(holder.tvGst.getText().toString())) {
                                if (Util.validateString(holder.percent.getText().toString())) {
                                    myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), Integer.parseInt(holder.percent.getText().toString()), holder.tvGst.getText().toString().replaceAll("days",""));

                                } else {
                                    myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, holder.tvGst.getText().toString().replaceAll("days",""));

                                }
                            } else {
                                if (Util.validateString(holder.percent.getText().toString())) {
                                    myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), Integer.parseInt(holder.percent.getText().toString()), "");

                                } else {
                                    myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, "");

                                }
                                //  myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, "");

                            }
                    /*    }
                        }, 600);*/

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
                 /*  timer1[0] = new Timer();
                    timer1[0].schedule(new TimerTask() {
                        @Override
                        public void run() {*/
                    if (Util.validateString(holder.tvGst.getText().toString())) {
                        if (Util.validateString(holder.percent.getText().toString())){
                            myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), Integer.parseInt(holder.percent.getText().toString()), holder.tvGst.getText().toString());

                        }else {
                            myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, holder.tvGst.getText().toString());

                        }
                    } else {
                        if (Util.validateString(holder.percent.getText().toString())){
                            myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), Integer.parseInt(holder.percent.getText().toString()),"");

                        }else {
                            myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, "");

                        }
                        //  myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, "");

                    }
                /*        }
                    }, 600);*/
                 /*   double percentage=0;
                    for (int i=0;i<stringArrayList.size();i++){
                        percentage+=stringArrayList.get(holder.getAdapterPosition()).getPoPaymentTermsPer();
                    }
                    if (percentage<=100) {*/
                 /*   if (Util.validateString(holder.percent.getText().toString())) {

                        myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), Integer.parseInt(holder.percent.getText().toString()), holder.tvGst.getText().toString());
                    } else {
                        myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, holder.tvGst.getText().toString());

                    }*/
                    /*}else {
                        Util.showToast("Total percentage should not be greater than 100%");
                        myListener.onRowClickedPaymentTerms(holder.getAdapterPosition(), 0, holder.tvGst.getText().toString());
                    }*/
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
        private ImageView delete;

        private EditText percent,tvGst;
        private RadioButton radio;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvQty = itemView.findViewById(R.id.tvQty);
            percent = itemView.findViewById(R.id.percent);
            tvGst = itemView.findViewById(R.id.tvGst);
            delete=itemView.findViewById(R.id.delete);



        }
    }
}
