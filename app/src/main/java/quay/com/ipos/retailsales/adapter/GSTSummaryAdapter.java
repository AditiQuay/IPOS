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

public class GSTSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    // private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    static Context mContext;
    ArrayList<PrintViewResult.GstSummary> mDataset;


    public GSTSummaryAdapter(Context ctx,
                             ArrayList<PrintViewResult.GstSummary> questionList) {
        this.mContext = ctx;
        this.mDataset = questionList;

    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSGSTRate,  tvSGSTValue, tvSGSTItemValue, tvCGSTRate, tvCGSTItemValue, tvCGSTValue;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvSGSTValue =  itemView.findViewById(R.id.tvSGSTValue);
            tvSGSTItemValue =  itemView.findViewById(R.id.tvSGSTItemValue);
            tvSGSTRate =  itemView.findViewById(R.id.tvSGSTRate);
            tvCGSTRate =  itemView.findViewById(R.id.tvCGSTRate);
            tvCGSTItemValue =  itemView.findViewById(R.id.tvCGSTItemValue);
            tvCGSTValue =  itemView.findViewById(R.id.tvCGSTValue);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.print_view_gst_summary_item, parent, false);
            return new GSTSummaryAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new GSTSummaryAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GSTSummaryAdapter.UserViewHolder) {
            onBind = true;
            PrintViewResult.GstSummary str = mDataset.get(position);
            GSTSummaryAdapter.UserViewHolder userViewHolder = (GSTSummaryAdapter.UserViewHolder) holder;
            userViewHolder.tvSGSTValue.setText(Util.getIndianNumberFormat( str.getSGSTValue()+""));
            userViewHolder.tvSGSTItemValue.setText(Util.getIndianNumberFormat(str.getTotalPrice()+""));
            userViewHolder.tvSGSTRate.setText("SGST "+str.getSGSTRate()+" %");
            userViewHolder.tvCGSTRate.setText("CGST "+str.getCGSTRate()+" %");
            userViewHolder.tvCGSTItemValue.setText(Util.getIndianNumberFormat( str.getTotalPrice()+""));
            userViewHolder.tvCGSTValue.setText( Util.getIndianNumberFormat(str.getCGSTValue()+""));
            onBind = false;

        }
        else if (holder instanceof GSTSummaryAdapter.LoadingViewHolder) {
            GSTSummaryAdapter.LoadingViewHolder loadingViewHolder = (GSTSummaryAdapter.LoadingViewHolder) holder;
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