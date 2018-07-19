package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.Util;

/**
 * Created by deepak kumar on 23-04-2018.
 */

public class DDRProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public static boolean onBind = true;
    // private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    List<ProductSearchResult.Datum> mDataset;
    RecyclerView mRecyclerView;

    public DDRProductAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                             List<ProductSearchResult.Datum> questionList) {
        mOnClickListener = mClickListener;
        mContext = ctx;
        mDataset = questionList;
        mRecyclerView = mRecycler;

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
            View view = LayoutInflater.from(mContext).inflate(R.layout.ddr_product_list_item, parent, false);
            return new DDRProductAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new DDRProductAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DDRProductAdapter.UserViewHolder) {

            ProductSearchResult.Datum str = mDataset.get(position);
//            AppLog.e(AddProductAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            DDRProductAdapter.UserViewHolder userViewHolder = (DDRProductAdapter.UserViewHolder) holder;
            userViewHolder.tvItemName.setText(str.getSProductName());
            userViewHolder.tvItemWeight.setText(str.getSProductWeight() + " gm");
            userViewHolder.tvStock.setText(str.getSProductStock()+"");
            ImageLoader.getInstance().displayImage(str.getProductImage(),userViewHolder.imvProduct);
            if(str.isAdded()){
                userViewHolder.tvAdd.setText("Added");
                userViewHolder.llAdd.setBackgroundResource(R.drawable.button_rectangle_light_gray );
            }else {
                userViewHolder.tvAdd.setText("Add");
                userViewHolder.llAdd.setBackgroundResource(R.drawable.button_drawable);
            }

            userViewHolder.tvUnitPrice.setText(Util.getIndianNumberFormat(str.getSalesPrice()+""));
            if(str.getDiscount().size()>1){
                userViewHolder.tvOfferDetail.setText(str.getDiscount().size()+ " offers applied");
            }else if(str.getDiscount().size()==1){
                userViewHolder.tvOfferDetail.setText(str.getDiscount().get(0).getSDiscountName());
            }else {
                userViewHolder.tvOfferDetail.setVisibility(View.GONE);
            }
            userViewHolder.tvPoints.setText(str.getPoints()+"");
            onBind = false;
            userViewHolder.llAdd.setOnClickListener(mOnClickListener);
            userViewHolder.llAdd.setTag(position);


        }
        else if (holder instanceof DDRProductAdapter.LoadingViewHolder) {
            DDRProductAdapter.LoadingViewHolder loadingViewHolder = (DDRProductAdapter.LoadingViewHolder) holder;
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