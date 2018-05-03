package quay.com.ipos.customerInfo.customerInfoAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerInfoModal;
import quay.com.ipos.listeners.MyListener;

import quay.com.ipos.productCatalogue.productModal.SearchedItemModal;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoAdapter.ItemViewholder> implements Filterable {
    private ArrayList<CustomerInfoModal> customerInfoModals;

    private Context mContext;
    private MyListener listener;
    private ArrayList<CustomerInfoModal> mFilteredList;

    public CustomerInfoAdapter(Context context, ArrayList<CustomerInfoModal> customerInfoModals, MyListener listener) {
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
        CustomerInfoModal customerInfoModal = mFilteredList.get(position);
        holder.textViewUserName.setText(customerInfoModal.customerName);
        holder.textViewMob.setText(customerInfoModal.customerMobileNumber);
        holder.textViewEmail.setText(customerInfoModal.customerEmail);
        holder.textViewBill.setText("Last Billing " + customerInfoModal.customerBillingDate + " | " + mContext.getResources().getString(R.string.Rs) + " " + customerInfoModal.customerBillingAmount);
        holder.textViewCake.setText("Wish Birthday (" + customerInfoModal.customerBirthDay + ")");
        holder.textViewProName.setText(customerInfoModal.customerPoints + " Pts.");
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
                    ArrayList<CustomerInfoModal> filteredList = new ArrayList<>();
                    for (CustomerInfoModal customerInfoModal : customerInfoModals) {

                        if (customerInfoModal.customerMobileNumber.toLowerCase().contains(charString) || customerInfoModal.customerName.toLowerCase().contains(charString)) {

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
                mFilteredList = (ArrayList<CustomerInfoModal>) results.values;
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
