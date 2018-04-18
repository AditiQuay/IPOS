package quay.com.ipos.retailsales;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.ui.FontManager;

/**
 * Created by aditi.bhuranda on 17-04-2018.
 */

public class RetailSalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<String> mDataset;
    RecyclerView mRecyclerView;

    public RetailSalesAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                               ArrayList<String> questionList) {
        mOnClickListener = mClickListener;
        mContext = ctx;

        mDataset = questionList;
        mRecyclerView = mRecycler;

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

    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName, tvItemWeight, tvItemRate, tvItemPrice, tvClear,tvQtySelected;
        public TextView tvTotalPrice, tvDiscount, tvDiscountPrice;
        public ImageView imvInfo,imvProduct,imvMinus,imvPlus;
        public CheckBox chkDiscount;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvItemWeight =  itemView.findViewById(R.id.tvItemWeight);
            tvItemRate =  itemView.findViewById(R.id.tvItemRate);
            tvItemPrice =  itemView.findViewById(R.id.tvItemPrice);
            imvInfo =  itemView.findViewById(R.id.imvInfo);
            imvProduct =  itemView.findViewById(R.id.imvProduct);
            tvClear =  itemView.findViewById(R.id.tvClear);
            imvMinus =  itemView.findViewById(R.id.imvMinus);
            tvQtySelected =  itemView.findViewById(R.id.tvQtySelected);
            imvPlus =  itemView.findViewById(R.id.imvPlus);
            tvTotalPrice =  itemView.findViewById(R.id.tvTotalPrice);
            chkDiscount =  itemView.findViewById(R.id.chkDiscount);
            tvDiscount =  itemView.findViewById(R.id.tvDiscount);
            tvDiscountPrice =  itemView.findViewById(R.id.tvDiscountPrice);


            Typeface iconFont = FontManager.getTypeface(mContext, FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(tvClear, iconFont);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.show_product_list_item, parent, false);
            return new RetailSalesAdapter.UserViewHolder(view);
        } /*
			 * else if (viewType == VIEW_TYPE_LOADING) { View view =
			 * LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
			 * parent, false); return new LoadingViewHolder(view); }
			 */
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RetailSalesAdapter.UserViewHolder) {
            String str = mDataset.get(position);

        }
			  else if (holder instanceof LoadingViewHolder) { LoadingViewHolder
			  loadingViewHolder = (LoadingViewHolder) holder;
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