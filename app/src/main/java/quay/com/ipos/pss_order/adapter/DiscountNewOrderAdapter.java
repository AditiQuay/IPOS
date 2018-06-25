package quay.com.ipos.pss_order.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.pss_order.modal.DiscountModal;
import quay.com.ipos.listeners.MyCheckedChangedListener;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 26-04-2018.
 */

public class DiscountNewOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;
    private boolean onBind;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    List<DiscountModal> mDataset;
    RecyclerView mRecyclerView;
    MyCheckedChangedListener myCheckedChangedListener;
    String productId,productCode;

    public DiscountNewOrderAdapter(Context ctx, RecyclerView mRecycler,
                                   List<DiscountModal> questionList, MyCheckedChangedListener myCheckedChangedListener, String productId, String productCode) {

        mContext = ctx;
        mDataset = questionList;
        mRecyclerView = mRecycler;
        this.myCheckedChangedListener=myCheckedChangedListener;
        this.productId=productId;
        this.productCode=productCode;

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
        public TextView tvDiscountPrice,tvDiscount;
        public LinearLayout llDiscount;
        public CheckBox chkDiscount;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvDiscountPrice =  itemView.findViewById(R.id.tvDiscountPrice);
            tvDiscount=itemView.findViewById(R.id.tvDiscount);
            llDiscount=itemView.findViewById(R.id.llDiscount);
            chkDiscount=itemView.findViewById(R.id.chkDiscount);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.discount_item, parent, false);
            return new DiscountNewOrderAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new DiscountNewOrderAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DiscountNewOrderAdapter.UserViewHolder) {
            onBind = true;
            final DiscountModal str = mDataset.get(position);

            final DiscountNewOrderAdapter.UserViewHolder userViewHolder = (DiscountNewOrderAdapter.UserViewHolder) holder;
            userViewHolder.tvDiscount.setText(str.getsDiscountDisplayName());
            userViewHolder.tvDiscountPrice.setText("- "+mContext.getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(str.getDiscountTotal())+"");
            if (str.issDiscountStrikeOut()){
                userViewHolder.chkDiscount.setChecked(false);
                userViewHolder.tvDiscountPrice.setPaintFlags(userViewHolder.tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else {
                if (userViewHolder.chkDiscount.isChecked())
                    userViewHolder.chkDiscount.setChecked(false);
                else {
                    userViewHolder.chkDiscount.setChecked(true);
                }
            }
            onBind = false;



            if (position==mDataset.size()-1){
                userViewHolder.llDiscount.setBackgroundResource(R.drawable.rect_bottom_corner_app_trans);
            }else {
                userViewHolder.llDiscount.setBackgroundResource(R.color.coloryPrimary_transulent);
            }

            userViewHolder.chkDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!onBind) {
                        myCheckedChangedListener.onDiscount(str, position, b, productId, productCode);
                    }


                }
            });

        }
        else if (holder instanceof DiscountNewOrderAdapter.LoadingViewHolder) {
            DiscountNewOrderAdapter.LoadingViewHolder loadingViewHolder = (DiscountNewOrderAdapter.LoadingViewHolder) holder;
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