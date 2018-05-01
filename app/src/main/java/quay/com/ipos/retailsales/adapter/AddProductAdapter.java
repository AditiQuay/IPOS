package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 23-04-2018.
 */

public class AddProductAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<ProductList.Datum> mDataset;
    RecyclerView mRecyclerView;

    public AddProductAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                              ArrayList<ProductList.Datum> questionList) {
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

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName, tvItemWeight, tvPrice, tvAdd;
        public ImageView imvProduct;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvItemWeight =  itemView.findViewById(R.id.tvItemWeight);
            tvPrice =  itemView.findViewById(R.id.tvPrice);
            tvAdd =  itemView.findViewById(R.id.tvAdd);
            imvProduct =  itemView.findViewById(R.id.imvProduct);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.add_product_list_item, parent, false);
            return new AddProductAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new AddProductAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddProductAdapter.UserViewHolder) {
            ProductList.Datum str = mDataset.get(position);
            AppLog.e(AddProductAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            AddProductAdapter.UserViewHolder userViewHolder = (AddProductAdapter.UserViewHolder) holder;
            userViewHolder.tvItemName.setText(str.getSProductName());
            userViewHolder.tvItemWeight.setText(str.getSProductWeight() + " gm");
            userViewHolder.tvPrice.setText(str.getSProductPoints());

            userViewHolder.tvAdd.setOnClickListener(mOnClickListener);
            userViewHolder.tvAdd.setTag(position);


        }
        else if (holder instanceof AddProductAdapter.LoadingViewHolder) {
            AddProductAdapter.LoadingViewHolder loadingViewHolder = (AddProductAdapter.LoadingViewHolder) holder;
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