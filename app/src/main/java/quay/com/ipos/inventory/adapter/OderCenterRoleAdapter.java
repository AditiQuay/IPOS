package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 08-06-2018.
 */

public class OderCenterRoleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private boolean onBind;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<OrderList.Datum> mDataset;
    RecyclerView mRecyclerView;

    public OderCenterRoleAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                                  ArrayList<OrderList.Datum> orderList) {
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
            return new OderCenterRoleAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new OderCenterRoleAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OrderCentreListAdapter.UserViewHolder) {
            OrderList.Datum str = mDataset.get(position);
            AppLog.e(OrderCentreListAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            OrderCentreListAdapter.UserViewHolder userViewHolder = (OrderCentreListAdapter.UserViewHolder) holder;
            userViewHolder.tvQty.setText("Qty "+str.getQty());
        }
        else if (holder instanceof OrderCentreListAdapter.LoadingViewHolder) {
            OrderCentreListAdapter.LoadingViewHolder loadingViewHolder = (OrderCentreListAdapter.LoadingViewHolder) holder;
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
