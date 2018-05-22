package quay.com.ipos.customerInfo.customerInfoAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.CustomerList;

/**
 * Created by niraj.kumar on 5/21/2018.
 */

public class CustomerRecentOrdersAdapter extends RecyclerView.Adapter<CustomerRecentOrdersAdapter.ViewHolder> {
    private ArrayList<CustomerList.RecentOrder> recentOrders;
    private Context mContext;

    public CustomerRecentOrdersAdapter(Context context, ArrayList<CustomerList.RecentOrder> recentOrders) {
        this.mContext = context;
        this.recentOrders = recentOrders;
    }

    @Override
    public CustomerRecentOrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_recent_orders, parent, false);

        return new CustomerRecentOrdersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerRecentOrdersAdapter.ViewHolder holder, int position) {
        CustomerList.RecentOrder recentOrder = recentOrders.get(position);
        holder.textViewStoreName.setText(recentOrder.getFromStoreName());
        holder.textViewStoreAddress.setText(recentOrder.getStoreCity()+", ".concat(recentOrder.getStoreState()));
        holder.textViewBillingDate.setText(recentOrder.getBillDate());
        holder.textViewBillingAmount.setText(mContext.getResources().getString(R.string.Rs) + " " + recentOrder.getBillPrice());
    }

    @Override
    public int getItemCount() {
        return recentOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewStoreName, textViewStoreAddress, textViewBillingDate, textViewBillingAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewStoreName = itemView.findViewById(R.id.textViewStoreName);
            textViewStoreAddress = itemView.findViewById(R.id.textViewStoreAddress);
            textViewBillingDate = itemView.findViewById(R.id.textViewBillingDate);
            textViewBillingAmount = itemView.findViewById(R.id.textViewBillingAmount);
        }
    }
}
