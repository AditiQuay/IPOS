package quay.com.ipos.pss_order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.pss_order.modal.OrderCentreModal;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 08-05-2018.
 */

public class OrderCentreAccListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private boolean onBind;
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        // private OnLoadMoreListener mOnLoadMoreListener;
        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        View.OnClickListener mOnClickListener;
        static Context mContext;
        ArrayList<OrderCentreModal> mDataset;
        RecyclerView mRecyclerView;

        public OrderCentreAccListAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                                         ArrayList<OrderCentreModal> orderList) {
            this.mOnClickListener = mClickListener;
            this.mContext = ctx;
            this.mDataset = orderList;
            this.mRecyclerView = mRecycler;
            // final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
            // mRecyclerView.getLayoutManager();
            // mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
            // {
            // @Override
            // public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // super.onScrolled(recyclerView, dx, dy);
            //
            // totalItemCount = linearLayoutManager.getItemCount();
            // lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            //
            // if (!isLoading && totalItemCount <= (lastVisibleItem +
            // visibleThreshold)) {
            // if (mOnLoadMoreListener != null) {
            // mOnLoadMoreListener.onLoadMore();
            // }
            // isLoading = true;
            // }
            // }
            // });
        }


    class UserViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle,  tvOrderNumber, tvBillingDate,tvItemNo;
            public TextView tvQty, tvTotalPrice, tvETADate;

            public UserViewHolder(View itemView) {
                super(itemView);
                tvTitle =  itemView.findViewById(R.id.tvTitle);
                tvOrderNumber =  itemView.findViewById(R.id.tvOrderNumber);
                tvBillingDate =  itemView.findViewById(R.id.tvBillingDate);
                tvETADate =  itemView.findViewById(R.id.tvETADate);
                tvItemNo =  itemView.findViewById(R.id.tvItemNo);
                tvTotalPrice =  itemView.findViewById(R.id.tvTotalPrice);
                tvQty =  itemView.findViewById(R.id.tvQty);
            }
        }


        @Override
        public int getItemViewType(int position) {
            return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.order_centre_list_item, parent, false);
                return new OrderCentreAccListAdapter.UserViewHolder(view);
            }
            else if (viewType == VIEW_TYPE_LOADING) {
                View view =
                        LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                                parent, false); return new OrderCentreAccListAdapter.LoadingViewHolder(view); }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof OrderCentreAccListAdapter.UserViewHolder) {
                OrderCentreModal str = mDataset.get(position);
                AppLog.e(OrderCentreAccListAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
                OrderCentreAccListAdapter.UserViewHolder userViewHolder = (OrderCentreAccListAdapter.UserViewHolder) holder;
                userViewHolder.tvQty.setText("Qty "+str.getItemQty());
                userViewHolder.tvBillingDate.setText(""+str.getModifiedDate());
                userViewHolder.tvETADate.setText("Eta "+str.getEtaDate());
                userViewHolder.tvItemNo.setText("Items "+str.getTotalItem());
                userViewHolder.tvOrderNumber.setText(""+str.getRequestCode());
                userViewHolder.tvTotalPrice.setText(mContext.getString(R.string.Rs)+" "+Util.indianNumberFormat(str.getOrderValue()));
            }
            else if (holder instanceof OrderCentreAccListAdapter.LoadingViewHolder) {
                OrderCentreAccListAdapter.LoadingViewHolder loadingViewHolder = (OrderCentreAccListAdapter.LoadingViewHolder) holder;
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
