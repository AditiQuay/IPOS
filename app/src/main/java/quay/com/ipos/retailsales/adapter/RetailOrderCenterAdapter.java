package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.PrintViewResult;
import quay.com.ipos.modal.RetailOrderCenterListResult;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 03-07-2018.
 */

public class RetailOrderCenterAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    // private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    static Context mContext;
    private ArrayList<RetailOrderCenterListResult.ListOrderCenter> mDataset = new ArrayList<>();
    //    private ArrayList<PrintViewResult.PaymentsDetail>  mPaymentDetail;


    public RetailOrderCenterAdapter(Context ctx,
                         ArrayList<RetailOrderCenterListResult.ListOrderCenter> questionList) {
        this.mContext = ctx;
        this.mDataset = questionList;

    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderNumber,  tvPaymentMode, tvCustomerDetails, tvDateTime,tvValue;
        public ImageView imvStatus;

        public UserViewHolder(View itemView) {
            super(itemView);
            imvStatus =  itemView.findViewById(R.id.imvStatus);
            tvValue =  itemView.findViewById(R.id.tvValue);
            tvDateTime =  itemView.findViewById(R.id.tvDateTime);
            tvOrderNumber =  itemView.findViewById(R.id.tvOrderNumber);
            tvPaymentMode =  itemView.findViewById(R.id.tvPaymentMode);
            tvCustomerDetails =  itemView.findViewById(R.id.tvCustomerDetails);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.outbox_item, parent, false);
            return new RetailOrderCenterAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new RetailOrderCenterAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RetailOrderCenterAdapter.UserViewHolder) {
            onBind = true;
            String mobile="";
            String name="";
            RetailOrderCenterListResult.ListOrderCenter str = mDataset.get(position);
            RetailOrderCenterAdapter.UserViewHolder userViewHolder = (RetailOrderCenterAdapter.UserViewHolder) holder;
            userViewHolder.tvOrderNumber.setText(str.getOrderNo()+"");
            userViewHolder.tvValue.setText(mContext.getResources().getString(R.string.Rs)+" "+str.getOrderValue()+"");
            userViewHolder.tvDateTime.setText(str.getOrderDate());
            userViewHolder.tvPaymentMode.setText(str.getPaymentStatus());
            userViewHolder.tvCustomerDetails.setText(str.getMobile()+" - "+str.getCustomerName());
            if(str.getMobile().equalsIgnoreCase("") || str.getMobile().equalsIgnoreCase("NA")){
                mobile = "9999 9999";
            }else
                mobile= str.getMobile();
            if(str.getCustomerName().equalsIgnoreCase("") || str.getCustomerName().equalsIgnoreCase("NA")|| str.getCustomerName().equalsIgnoreCase("NA NA")){
                name = "Default Customer";
            }else
                name= str.getCustomerName();

            userViewHolder.tvCustomerDetails.setText(mobile+" - "+name);
            userViewHolder.imvStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_cart));
            onBind = false;

        }
        else if (holder instanceof RetailOrderCenterAdapter.LoadingViewHolder) {
            RetailOrderCenterAdapter.LoadingViewHolder loadingViewHolder = (RetailOrderCenterAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true); }

    }


    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.load_more_progressBar);
        }
    }
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

}
