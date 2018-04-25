package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    ArrayList<ProductList.Datum> mDataset;
    RecyclerView mRecyclerView;
    CompoundButton.OnCheckedChangeListener mCheckedChangeListener;

    public RetailSalesAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                              ArrayList<ProductList.Datum> questionList, CompoundButton.OnCheckedChangeListener mCheckedChangeListener) {
        mOnClickListener = mClickListener;
        mContext = ctx;
        mDataset = questionList;
        mRecyclerView = mRecycler;
        this.mCheckedChangeListener = mCheckedChangeListener;

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
        public TextView tvItemName, tvItemWeight, tvItemRate, tvItemPrice, tvClear;
        public TextView tvTotalPrice, tvDiscount, tvDiscountPrice,tvOTCDiscountPrice;
        public ImageView imvInfo,imvProduct,imvMinus,imvPlus;
        public LinearLayout llDiscount,llOTCDiscount;
        public CheckBox chkDiscount,chkItem,chkOTCDiscount;
        public EditText etQtySelected;

        public UserViewHolder(View itemView) {
            super(itemView);
            llOTCDiscount = itemView.findViewById(R.id.llOTCDiscount);
            tvOTCDiscountPrice = itemView.findViewById(R.id.tvOTCDiscountPrice);
            chkOTCDiscount = itemView.findViewById(R.id.chkOTCDiscount);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvItemWeight =  itemView.findViewById(R.id.tvItemWeight);
            tvItemRate =  itemView.findViewById(R.id.tvItemRate);
            tvItemPrice =  itemView.findViewById(R.id.tvItemPrice);
            imvInfo =  itemView.findViewById(R.id.imvInfo);
            imvProduct =  itemView.findViewById(R.id.imvProduct);
            tvClear =  itemView.findViewById(R.id.tvClear);
            imvMinus =  itemView.findViewById(R.id.imvMinus);
            etQtySelected =  itemView.findViewById(R.id.etQtySelected);
            imvPlus =  itemView.findViewById(R.id.imvPlus);
            tvTotalPrice =  itemView.findViewById(R.id.tvTotalPrice);
            chkDiscount =  itemView.findViewById(R.id.chkDiscount);
            tvDiscount =  itemView.findViewById(R.id.tvDiscount);
            tvDiscountPrice =  itemView.findViewById(R.id.tvDiscountPrice);
            llDiscount = itemView.findViewById(R.id.llDiscount);
            chkItem = itemView.findViewById(R.id.chkItem);

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
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                        parent, false); return new LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RetailSalesAdapter.UserViewHolder) {
            ProductList.Datum str = mDataset.get(position);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvItemName.setText(str.getSProductName());
            userViewHolder.tvItemWeight.setText(str.getSProductWeight() + " gm");
            userViewHolder.tvItemRate.setText(str.getSProductPoints());
            userViewHolder.tvItemPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getSProductPrice());
            userViewHolder.etQtySelected.setText(str.getQty()+"");


            if(str.isItemSelected())
                userViewHolder.chkItem.setVisibility(View.VISIBLE);
            else
                userViewHolder.chkItem.setVisibility(View.GONE);

            if(str.isOTCselected())
                userViewHolder.chkItem.setChecked(true);
            else
                userViewHolder.chkItem.setChecked(false);

            if(str.isDiscSelected()) {
                userViewHolder.llOTCDiscount.setVisibility(View.VISIBLE);
                userViewHolder.tvOTCDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getOTCDiscount());
            }
            else
                userViewHolder.llOTCDiscount.setVisibility(View.GONE);

            Double totalPrice=(Double.parseDouble(str.getSProductPrice())*str.getQty());
            str.setTotalPrice(totalPrice);
            if(str.getIsDiscount()) {
                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getSDiscountPrice());
                userViewHolder.tvDiscount.setText(str.getSDiscountName());

                Double discount = Double.parseDouble(str.getSDiscountPrice())*totalPrice/100;
                str.setDiscount(discount);
                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+discount+"");
                userViewHolder.llDiscount.setVisibility(View.VISIBLE);
            }else {
                str.setTotalPrice(totalPrice);
                str.setDiscount(0.0);
                userViewHolder.llDiscount.setVisibility(View.GONE);
            }


            userViewHolder.tvTotalPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+totalPrice);
            IPOSApplication.mProductList.set(position,str);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(), "IPOSApplication.mProductList: "+ Util.getCustomGson().toJson(IPOSApplication.mProductList));

            userViewHolder.imvMinus.setOnClickListener(mOnClickListener);
            userViewHolder.imvMinus.setTag(position);

            userViewHolder.imvPlus.setOnClickListener(mOnClickListener);
            userViewHolder.imvPlus.setTag(position);

            userViewHolder.chkItem.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkItem.setTag(position);

            userViewHolder.chkDiscount.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkDiscount.setTag(position);
        }
        else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
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