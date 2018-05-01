package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.CustomerResult;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.retailsales.activity.CustomerDetailActivity;
import quay.com.ipos.ui.CircleTransform;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 26-04-2018.
 */

public class CustomerListAdapterNew  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<CustomerResult.Customer> mDataset;
    RecyclerView mRecyclerView;

    public CustomerListAdapterNew(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                             ArrayList<CustomerResult.Customer> questionList) {
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
        public TextView tvName, tvMobileNo, tvEmail, tvBillingDate,tvBillingAmount,tvBirthday,tvRedeemPoints;
        public ImageView imvUserImage;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tvName);
            tvMobileNo =  itemView.findViewById(R.id.tvMobileNo);
            tvEmail =  itemView.findViewById(R.id.tvEmail);
            tvBillingAmount =  itemView.findViewById(R.id.tvBillingAmount);
            tvBillingDate =  itemView.findViewById(R.id.tvBillingDate);
            tvBirthday =  itemView.findViewById(R.id.tvBirthday);
            tvRedeemPoints = itemView.findViewById(R.id.tvRedeemPoints);
            imvUserImage = itemView.findViewById(R.id.imvUserImage);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.customer_list_item, parent, false);
            return new CustomerListAdapterNew.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new CustomerListAdapterNew.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CustomerListAdapterNew.UserViewHolder) {
            CustomerResult.Customer str = mDataset.get(position);
            AppLog.e(CustomerListAdapterNew.class.getSimpleName(), Util.getCustomGson().toJson(str));
            CustomerListAdapterNew.UserViewHolder userViewHolder = (CustomerListAdapterNew.UserViewHolder) holder;
            userViewHolder.tvName.setText(str.getCustomerName());
            userViewHolder.tvBillingDate.setText(str.getLastBilling().getLastBillingDate());
            userViewHolder.tvMobileNo.setText(str.getCustomerPhone());
            userViewHolder.tvBillingAmount.setText(str.getLastBilling().getLastBillingAmount());
            userViewHolder.tvBirthday.setText(str.getCustomerBday());
            userViewHolder.tvEmail.setText(str.getCustomerEmail());
            userViewHolder.tvRedeemPoints.setText(str.getCustomerPoints());
            Glide.with(mContext).load(Constants.UserProfilePic)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(userViewHolder.imvUserImage);
//            ImageLoader.getInstance().displayImage(Constants.UserProfilePic,userViewHolder.imvUserImage);
//            userViewHolder.tvAdd.setOnClickListener(mOnClickListener);
//            userViewHolder.tvAdd.setTag(position);


        }
        else if (holder instanceof CustomerListAdapterNew.LoadingViewHolder) {
            CustomerListAdapterNew.LoadingViewHolder loadingViewHolder = (CustomerListAdapterNew.LoadingViewHolder) holder;
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