package quay.com.ipos.inventoryTrasfer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventoryTrasfer.inventoryTransferOut.transferOutModel.TransferOutListModal;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 08-05-2018.
 */

public class TransferOutPendingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private boolean onBind;
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        // private OnLoadMoreListener mOnLoadMoreListener;
        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        View.OnClickListener mOnClickListener;
        static Context mContext;
        ArrayList<TransferOutListModal> mDataset;
        RecyclerView mRecyclerView;

        public TransferOutPendingAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                                         ArrayList<TransferOutListModal> orderList) {
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
            public TextView   tvOrderNumber, tvBillingDate;
            public TextView tvReceiverAddress, tvTotalPrice, tvSenderAddress;

            public UserViewHolder(View itemView) {
                super(itemView);
                tvOrderNumber =  itemView.findViewById(R.id.tvOrderNumber);
                tvBillingDate =  itemView.findViewById(R.id.tvBillingDate);
                tvSenderAddress =  itemView.findViewById(R.id.tvSenderAddress);
                tvTotalPrice =  itemView.findViewById(R.id.tvTotalPrice);
                tvReceiverAddress =  itemView.findViewById(R.id.tvReceiverAddress);
            }
        }


        @Override
        public int getItemViewType(int position) {
            return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.transfer_out_list_item, parent, false);
                return new TransferOutPendingAdapter.UserViewHolder(view);
            }
            else if (viewType == VIEW_TYPE_LOADING) {
                View view =
                        LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                                parent, false); return new TransferOutPendingAdapter.LoadingViewHolder(view); }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof TransferOutPendingAdapter.UserViewHolder) {
                TransferOutListModal str = mDataset.get(position);
                TransferOutPendingAdapter.UserViewHolder userViewHolder = (TransferOutPendingAdapter.UserViewHolder) holder;
                userViewHolder.tvSenderAddress.setText("Qty "+str.getSenderAddress());
                userViewHolder.tvBillingDate.setText(""+str.getDate());
                userViewHolder.tvReceiverAddress.setText("Items "+str.getReceiverAddress());
                userViewHolder.tvOrderNumber.setText(""+str.getId());
                userViewHolder.tvTotalPrice.setText(mContext.getString(R.string.Rs)+" "+Util.indianNumberFormat(str.getAmount()));
            }
            else if (holder instanceof TransferOutPendingAdapter.LoadingViewHolder) {
                TransferOutPendingAdapter.LoadingViewHolder loadingViewHolder = (TransferOutPendingAdapter.LoadingViewHolder) holder;
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
