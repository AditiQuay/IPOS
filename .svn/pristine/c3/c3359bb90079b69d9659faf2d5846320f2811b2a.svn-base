package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.PrintViewResult;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 02-07-2018.
 */

public class ItemDetailAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    // private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    static Context mContext;
    ArrayList<PrintViewResult.ItemDetail> mDataset;


    public ItemDetailAdapter(Context ctx,
                             ArrayList<PrintViewResult.ItemDetail> questionList) {
        this.mContext = ctx;
        this.mDataset = questionList;

    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQty,  tvUnitPrice, tvDiscount, tvTotalAmount,tvHSN,tvGST_Perc,tvGSTValue,tvItemName;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvHSN =  itemView.findViewById(R.id.tvHSN);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvTotalAmount =  itemView.findViewById(R.id.tvTotalAmount);
            tvQty =  itemView.findViewById(R.id.tvQty);
            tvUnitPrice =  itemView.findViewById(R.id.tvUnitPrice);
            tvGST_Perc =  itemView.findViewById(R.id.tvGST_Perc);
            tvGSTValue =  itemView.findViewById(R.id.tvGSTValue);
            tvDiscount =  itemView.findViewById(R.id.tvDiscount);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.print_view_item, parent, false);
            return new ItemDetailAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new ItemDetailAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemDetailAdapter.UserViewHolder) {
            onBind = true;
            PrintViewResult.ItemDetail str = mDataset.get(position);
            ItemDetailAdapter.UserViewHolder userViewHolder = (ItemDetailAdapter.UserViewHolder) holder;
            userViewHolder.tvQty.setText(str.getQuantity()+"");
            userViewHolder.tvHSN.setText("HSN "+str.getHsnCode()+"");
            userViewHolder.tvTotalAmount.setText(Util.getIndianNumberFormat(str.getTotalPrice()+""));
            userViewHolder.tvUnitPrice.setText(Util.getIndianNumberFormat(str.getUnitPrice()+""));
            userViewHolder.tvDiscount.setText(Util.getIndianNumberFormat( str.getDiscountValue()+""));
            userViewHolder.tvGST_Perc.setText( str.getIGSTRate()+" %");
            userViewHolder.tvGSTValue.setText(Util.getIndianNumberFormat( str.getIGSTValue()+""));
            userViewHolder.tvItemName.setText(str.getMaterialName());
            onBind = false;

        }
        else if (holder instanceof ItemDetailAdapter.LoadingViewHolder) {
            ItemDetailAdapter.LoadingViewHolder loadingViewHolder = (ItemDetailAdapter.LoadingViewHolder) holder;
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
