package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.PrintViewResult;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 02-07-2018.
 */

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    // private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    static Context mContext;
    ArrayList<PrintViewResult.PaymentsDetail> mDataset;


    public PaymentAdapter(Context ctx,
                          ArrayList<PrintViewResult.PaymentsDetail> questionList) {
        this.mContext = ctx;
        this.mDataset = questionList;

    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPaidByCash,  tvPaidByCard, tvTenderChanges, tvCardNumber, tvExpiryDate, tvCardType;
        public LinearLayout llPaidByCash,llPaidByCard;
        public UserViewHolder(View itemView) {
            super(itemView);
            tvCardNumber =  itemView.findViewById(R.id.tvCardNumber);
            tvCardType =  itemView.findViewById(R.id.tvCardType);
            tvTenderChanges =  itemView.findViewById(R.id.tvTenderChanges);
            tvPaidByCash =  itemView.findViewById(R.id.tvPaidByCash);
            tvPaidByCard =  itemView.findViewById(R.id.tvPaidByCard);
            tvExpiryDate =  itemView.findViewById(R.id.tvExpiryDate);
            llPaidByCash =  itemView.findViewById(R.id.llPaidByCash);
            llPaidByCard =  itemView.findViewById(R.id.llPaidByCard);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.print_view_payment_item, parent, false);
            return new PaymentAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new PaymentAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PaymentAdapter.UserViewHolder) {
            onBind = true;
            try {
                PrintViewResult.PaymentsDetail str = mDataset.get(position);
                PaymentAdapter.UserViewHolder userViewHolder = (PaymentAdapter.UserViewHolder) holder;
                if(str.getModeOfPayment().equalsIgnoreCase("cash")){
                    userViewHolder.tvPaidByCash.setText(Util.getIndianNumberFormat(str.getAmount()+""));
                    userViewHolder.tvTenderChanges.setText(Util.getIndianNumberFormat(str.getReturnValue()+""));
                    userViewHolder.llPaidByCash.setVisibility(View.VISIBLE);
                }
                if(str.getModeOfPayment().equalsIgnoreCase("card")){
                    userViewHolder.tvCardNumber.setText(str.getCardNo()+"");
                    userViewHolder.tvCardType.setText(str.getCardType()+"");
                    userViewHolder.tvExpiryDate.setText(str.getExpiryDate()+"");
                    userViewHolder.tvPaidByCard.setText(Util.getIndianNumberFormat(str.getAmount()+""));
                    userViewHolder.llPaidByCard.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){

            }

            onBind = false;

        }
        else if (holder instanceof PaymentAdapter.LoadingViewHolder) {
            PaymentAdapter.LoadingViewHolder loadingViewHolder = (PaymentAdapter.LoadingViewHolder) holder;
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