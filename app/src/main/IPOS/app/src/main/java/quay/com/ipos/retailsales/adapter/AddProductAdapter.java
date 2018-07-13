package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.ProductListResult;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

import static quay.com.ipos.application.IPOSApplication.mProductListResult;

/**
 * Created by aditi.bhuranda on 23-04-2018.
 */

public class AddProductAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public static boolean onBind = true;
    public static boolean onPressed = false;
    // private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<ProductSearchResult.Datum> mDataset;
    RecyclerView mRecyclerView;

    public AddProductAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                              ArrayList<ProductSearchResult.Datum> questionList) {
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
        public TextView tvItemName, tvItemWeight, tvStock, tvAdd,tvOfferDetail,tvUnitPrice,tvPoints;
        public ImageView imvProduct;
        private RelativeLayout llAdd;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvPoints =  itemView.findViewById(R.id.tvPoints);
            tvItemWeight =  itemView.findViewById(R.id.tvItemWeight);
            tvStock =  itemView.findViewById(R.id.tvStock);
            tvAdd =  itemView.findViewById(R.id.tvAdd);
            tvOfferDetail =  itemView.findViewById(R.id.tvOfferDetail);
            tvUnitPrice =  itemView.findViewById(R.id.tvUnitPrice);
            imvProduct =  itemView.findViewById(R.id.imvProduct);
            llAdd=itemView.findViewById(R.id.llAdd);
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
            final UserViewHolder userViewHolder = new UserViewHolder(view);
            userViewHolder.llAdd.setOnClickListener(mOnClickListener);
            userViewHolder.llAdd.setTag( userViewHolder.getAdapterPosition());
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
            // TODO: Implement this method
//            holder.setIsRecyclable(false);
            onBind=true;
            ProductSearchResult.Datum str = mDataset.get(position);
//            AppLog.e(AddProductAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            AddProductAdapter.UserViewHolder userViewHolder = (AddProductAdapter.UserViewHolder) holder;
//            if(!onPressed) {
                userViewHolder.tvItemName.setText(str.getSProductName());
                userViewHolder.tvItemWeight.setText(str.getSProductWeight() + " ");
                userViewHolder.tvStock.setText(str.getSProductStock() + "");
                ImageLoader.getInstance().displayImage(str.getProductImage(), userViewHolder.imvProduct);

                if (str.isAdded()) {
                    userViewHolder.tvAdd.setText("Added");
                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                } else {
                    userViewHolder.tvAdd.setText("Add");
                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_drawable);
                }

                userViewHolder.tvUnitPrice.setText(Util.getIndianNumberFormat(str.getSalesPrice() + ""));
                if (str.getDiscount().size() > 1) {
                    userViewHolder.tvOfferDetail.setText(str.getDiscount().size() + " offers available");
                } else if (str.getDiscount().size() == 1) {
                    userViewHolder.tvOfferDetail.setText(str.getDiscount().get(0).getSDiscountName());
                } else {
                    userViewHolder.tvOfferDetail.setText("");
//                userViewHolder.tvOfferDetail.setVisibility(View.GONE);
                }
                userViewHolder.tvPoints.setText(str.getPoints() + "");
//            }else {
//                if (str.isAdded()) {
//                    userViewHolder.tvAdd.setText("Added");
//                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_rectangle_light_gray);
//                } else {
//                    userViewHolder.tvAdd.setText("Add");
//                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_drawable);
//                }
//                onPressed=false;
//            }
            onBind = false;
            userViewHolder.llAdd.setTag( position);


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