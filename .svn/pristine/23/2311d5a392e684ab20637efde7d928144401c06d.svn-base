package quay.com.ipos.inventory.adapter;

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
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 04-05-2018.
 */

public class NewOrderPinnedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        // private OnLoadMoreListener mOnLoadMoreListener;

        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        static Context mContext;
        ArrayList<NewOrderPinnedResults.Info> mDataset;
        RecyclerView mRecyclerView;
    View.OnClickListener mOnClickListener;

        public NewOrderPinnedAdapter(Context ctx, RecyclerView mRecycler,
                                     ArrayList<NewOrderPinnedResults.Info> questionList, View.OnClickListener mOnClickListener) {
            mContext = ctx;
            mDataset = questionList;
            mRecyclerView = mRecycler;
            this.mOnClickListener = mOnClickListener;
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
            public TextView textViewChildName;
            public ImageView imvClose;


            public UserViewHolder(View itemView) {
                super(itemView);
                textViewChildName =  itemView.findViewById(R.id.textViewChildName);
                imvClose = itemView.findViewById(R.id.imvClose);
                imvClose.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public int getItemViewType(int position) {
            return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false);
                return new NewOrderPinnedAdapter.UserViewHolder(view);
            }
            else if (viewType == VIEW_TYPE_LOADING) {
                View view =
                        LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                                parent, false); return new NewOrderPinnedAdapter.LoadingViewHolder(view); }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof NewOrderPinnedAdapter.UserViewHolder) {
                NewOrderPinnedResults.Info str = mDataset.get(position);
                AppLog.e(NewOrderPinnedAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
                NewOrderPinnedAdapter.UserViewHolder userViewHolder = (NewOrderPinnedAdapter.UserViewHolder) holder;
                userViewHolder.textViewChildName.setText("Order: "+str.getKey());
                userViewHolder.textViewChildName.setTag(position);
                userViewHolder.imvClose.setOnClickListener(mOnClickListener);
                userViewHolder.imvClose.setTag(position);
                userViewHolder.imvClose.setVisibility(View.VISIBLE);
            }
            else if (holder instanceof NewOrderPinnedAdapter.LoadingViewHolder) {
                NewOrderPinnedAdapter.LoadingViewHolder loadingViewHolder = (NewOrderPinnedAdapter.LoadingViewHolder) holder;
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

// public void setLoaded() {
// isLoading = false;
// }
}
