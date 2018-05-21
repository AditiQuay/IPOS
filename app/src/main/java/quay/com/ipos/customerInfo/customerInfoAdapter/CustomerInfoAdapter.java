package quay.com.ipos.customerInfo.customerInfoAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerInfoModal;
import quay.com.ipos.listeners.MyListener;

import quay.com.ipos.productCatalogue.productModal.SearchedItemModal;
import quay.com.ipos.realmbean.RealmCustomerInfoModal;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoAdapter.ItemViewholder> implements Filterable {
    private ArrayList<RealmCustomerInfoModal> customerInfoModals;

    private Context mContext;
    private MyListener listener;
    private ArrayList<RealmCustomerInfoModal> mFilteredList;

    public CustomerInfoAdapter(Context context, ArrayList<RealmCustomerInfoModal> customerInfoModals, MyListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.customerInfoModals = customerInfoModals;
        mFilteredList = customerInfoModals;
    }

    @Override
    public CustomerInfoAdapter.ItemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_info_items, parent, false);

        return new CustomerInfoAdapter.ItemViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerInfoAdapter.ItemViewholder holder, int position) {
        RealmCustomerInfoModal customerInfoModal = mFilteredList.get(position);
        holder.textViewUserName.setText(customerInfoModal.getCustomer_name());
        holder.textViewMob.setText(customerInfoModal.getCustomer_phone());
        holder.textViewEmail.setText(customerInfoModal.getCustomer_email());

        try {
            JSONObject jsonObject = new JSONObject(customerInfoModal.getLast_billing());
            String last_billing_date = jsonObject.getString(Constants.KEY_LAST_BILLING_DATE.trim());
            String last_billing_amount = jsonObject.getString(Constants.KEY_LAST_BILLING_AMOUNT.trim());
            holder.textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + last_billing_date + " | " + mContext.getResources().getString(R.string.Rs) + " " + last_billing_amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.textViewCake.setText(mContext.getResources().getString(R.string.text_birthday) + customerInfoModal.getCustomer_bday() + ")");
        holder.textViewProName.setText(customerInfoModal.getCustomer_points() + mContext.getResources().getString(R.string.text_points));


    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {

                    mFilteredList = customerInfoModals;
                } else {
                    ArrayList<RealmCustomerInfoModal> filteredList = new ArrayList<>();
                    for (RealmCustomerInfoModal customerInfoModal : customerInfoModals) {

                        if (customerInfoModal.getCustomer_phone().toLowerCase().contains(charString) || customerInfoModal.getCustomer_name().toLowerCase().contains(charString)) {

                            filteredList.add(customerInfoModal);
                        }
                    }
                    mFilteredList = filteredList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<RealmCustomerInfoModal>) results.values;
                notifyDataSetChanged();
            }
        };
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
