package quay.com.ipos.customerInfo.customerInfoAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoAdapter.ItemViewholder> {
    private static final String TAG = CustomerInfoAdapter.class.getSimpleName();
    private ArrayList<CustomerModel> customerModelList;
    private Context mContext;
    private MyListener listener;
    private InFoListener inFoListener;

    public interface InFoListener {
        void onInfoListener(int position);
    }

    public CustomerInfoAdapter(Context context, ArrayList<CustomerModel> customerInfoModals, MyListener listener, InFoListener inFoListener) {
        this.mContext = context;
        this.listener = listener;
        this.customerModelList = customerInfoModals;
        this.inFoListener = inFoListener;
    }

    @Override
    public CustomerInfoAdapter.ItemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_info_items, parent, false);

        return new CustomerInfoAdapter.ItemViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomerInfoAdapter.ItemViewholder holder,  int position) {
        final CustomerModel customerInfoModal = customerModelList.get(position);
        if (!TextUtils.isEmpty(customerInfoModal.getCustomerName())){
            holder.textViewUserName.setText(customerInfoModal.getCustomerName());
        }else {
            String firstName = customerInfoModal.getCustomerFirstName();
            String lastName = customerInfoModal.getCustomerLastName();
            String finalName = firstName +" "+lastName;
            holder.textViewUserName.setText(finalName);

        }
        holder.textViewMob.setText(customerInfoModal.getCustomerPhone());

        if (TextUtils.isEmpty(customerInfoModal.getCustomerEmail())){
            holder.textViewEmail.setText("NA");
        }else {
            holder.textViewEmail.setText(customerInfoModal.getCustomerEmail());
        }

        String date1 = Util.getFormattedDates(customerInfoModal.getLastBillingDate(), Constants.format12, Constants.format13);

        if (TextUtils.isEmpty(customerInfoModal.getLastBillingDate())) {
            holder.textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + " " + " | " + mContext.getResources().getString(R.string.Rs) + " " + customerInfoModal.getLastBillingAmount());
        } else {
            holder.textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + date1 + " | " + mContext.getResources().getString(R.string.Rs) + " " + customerInfoModal.getLastBillingAmount());
        }
        if (NetUtil.isNetworkAvailable(mContext)) {
            if (!TextUtils.isEmpty(customerInfoModal.getCustomerImage())){
                Picasso.get().load(customerInfoModal.getCustomerImage()).into(holder.imageViewProfileDummy);
            }else {
                holder.imageViewProfileDummy.setImageResource(R.drawable.placeholder);
            }

        } else {
            Picasso.get()
                    .load(customerInfoModal.getCustomerImage())
                    .placeholder(R.drawable.placeholder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.imageViewProfileDummy, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
        }



//        Picasso.get().load(customerInfoModal.getCustomerImage()).placeholder(R.drawable.placeholder).into(holder.imageViewProfileDummy);


//        try {
//            JSONObject jsonObject = new JSONObject(customerInfoModal.get());
//            String last_billing_date = jsonObject.getString(Constants.KEY_LAST_BILLING_DATE.trim());
//            String last_billing_amount = jsonObject.getString(Constants.KEY_LAST_BILLING_AMOUNT.trim());
//            holder.textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + last_billing_date + " | " + mContext.getResources().getString(R.string.Rs) + " " + last_billing_amount);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



        if (customerInfoModal.getCustomerBday().equalsIgnoreCase("null")|| TextUtils.isEmpty(customerInfoModal.getCustomerBday())){
            holder.textViewCake.setText(mContext.getResources().getString(R.string.text_birthday) + "NA" + ")");
        }else {
            String bDate = Util.getFormattedDates(customerInfoModal.getCustomerBday(), Constants.formatDate, Constants.format14);
            holder.textViewCake.setText(mContext.getResources().getString(R.string.text_birthday) + bDate + ")");
        }

        holder.textViewProName.setText(customerInfoModal.getCustomerPoints() + mContext.getResources().getString(R.string.text_points));
        Log.e("CustomerInforAdapter", "Recent Orders" + customerInfoModal.getRecentOrders());
        // apply click events
        applyClickEvents(holder, position);

    }

    private void applyClickEvents(final ItemViewholder holder, int position) {
        holder.rLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRowClicked(holder.getAdapterPosition());
            }
        });
        holder.imageViewProfileDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRowClicked(holder.getAdapterPosition());
            }
        });
        holder.imageViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inFoListener.onInfoListener(holder.getAdapterPosition());
            }
        });
//        holder.textViewUserName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inFoListener.onInfoListener(holder.getAdapterPosition());
//
//            }
//        });
//        holder.textViewMob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inFoListener.onInfoListener(holder.getAdapterPosition());
//
//            }
//        });
//        holder.textViewEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inFoListener.onInfoListener(holder.getAdapterPosition());
//
//            }
//        });
//        holder.textViewBill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inFoListener.onInfoListener(holder.getAdapterPosition());
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return customerModelList.size();
    }


    public class ItemViewholder extends RecyclerView.ViewHolder {
        private TextView textViewUserName, textViewMob, textViewEmail, textViewBill, textViewCake, textViewProName;
        private CircleImageView imageViewProfileDummy;
        private RelativeLayout rLayoutContent;
        private ImageView imageViewInfo;

        public ItemViewholder(View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewMob = itemView.findViewById(R.id.textViewMob);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewBill = itemView.findViewById(R.id.textViewBill);
            textViewCake = itemView.findViewById(R.id.textViewCake);
            textViewProName = itemView.findViewById(R.id.textViewProName);
            rLayoutContent = itemView.findViewById(R.id.rLayoutContent);
            imageViewInfo = itemView.findViewById(R.id.imageViewInfo);
            imageViewProfileDummy = itemView.findViewById(R.id.imageViewProfileDummy);

            FontUtil.applyTypeface(textViewUserName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewMob, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewEmail, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewBill, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewCake, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewProName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        }
    }
}
