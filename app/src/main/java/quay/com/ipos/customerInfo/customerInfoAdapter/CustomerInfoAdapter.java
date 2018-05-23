package quay.com.ipos.customerInfo.customerInfoAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.db.CustomerListDB;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.realmbean.RealmCustomerInfoModal;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoAdapter.ItemViewholder> {
    private ArrayList<CustomerModel> customerModelList;
    private Context mContext;
    private MyListener listener;

    public CustomerInfoAdapter(Context context, ArrayList<CustomerModel> customerInfoModals, MyListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.customerModelList = customerInfoModals;
    }

    @Override
    public CustomerInfoAdapter.ItemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_info_items, parent, false);

        return new CustomerInfoAdapter.ItemViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomerInfoAdapter.ItemViewholder holder, final int position) {
        final CustomerModel customerInfoModal = customerModelList.get(position);
        holder.textViewUserName.setText(customerInfoModal.getCustomerName());
        holder.textViewMob.setText(customerInfoModal.getCustomerPhone());
        holder.textViewEmail.setText(customerInfoModal.getCustomerEmail());
        holder.textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + customerInfoModal.getLastBillingDate() + " | " + mContext.getResources().getString(R.string.Rs) + " " + customerInfoModal.getLastBillingAmount());



//        Picasso.get().load(customerInfoModal.getCustomerImage())
//                .networkPolicy(NetworkPolicy.OFFLINE)
//                .into(holder.imageViewProfileDummy, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
////                        Picasso.get().load(customerInfoModal.getCustomerImage()).into(holder.imageViewProfileDummy);
//                    }
//                });
//        try {
//            JSONObject jsonObject = new JSONObject(customerInfoModal.get());
//            String last_billing_date = jsonObject.getString(Constants.KEY_LAST_BILLING_DATE.trim());
//            String last_billing_amount = jsonObject.getString(Constants.KEY_LAST_BILLING_AMOUNT.trim());
//            holder.textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + last_billing_date + " | " + mContext.getResources().getString(R.string.Rs) + " " + last_billing_amount);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        holder.textViewCake.setText(mContext.getResources().getString(R.string.text_birthday) + customerInfoModal.getCustomerBday() + ")");
        holder.textViewProName.setText(customerInfoModal.getCustomerPoints() + mContext.getResources().getString(R.string.text_points));

        Log.e("CustomerInforAdapter","Recent Orders"+customerInfoModal.getRecentOrders());

    }

    @Override
    public int getItemCount() {
        return customerModelList.size();
    }


    public class ItemViewholder extends RecyclerView.ViewHolder {
        private TextView textViewUserName, textViewMob, textViewEmail, textViewBill, textViewCake, textViewProName;
        private CircleImageView imageViewProfileDummy;

        public ItemViewholder(View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewMob = itemView.findViewById(R.id.textViewMob);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewBill = itemView.findViewById(R.id.textViewBill);
            textViewCake = itemView.findViewById(R.id.textViewCake);
            textViewProName = itemView.findViewById(R.id.textViewProName);


            imageViewProfileDummy = itemView.findViewById(R.id.imageViewProfileDummy);

            FontUtil.applyTypeface(textViewUserName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewMob, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewEmail, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewBill, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewCake, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewProName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.animateView(v);
                    listener.onRowClicked(getAdapterPosition());
                }
            });

        }
    }
}
