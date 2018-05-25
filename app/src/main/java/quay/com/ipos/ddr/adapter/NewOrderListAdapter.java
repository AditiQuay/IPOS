package quay.com.ipos.ddr.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.retailsales.adapter.DiscountListAdapter;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;


public class NewOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;
    private AdapterListener listener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<OrderList.Datum> mDataset;
    RecyclerView mRecyclerView;
    CompoundButton.OnCheckedChangeListener mCheckedChangeListener;
    TextWatcher mTextWatcher;

    public NewOrderListAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                               ArrayList<OrderList.Datum> questionList, CompoundButton.OnCheckedChangeListener mCheckedChangeListener, AdapterListener listener) {
        this.mOnClickListener = mClickListener;
        this.mContext = ctx;
        this.mDataset = questionList;
        this.mRecyclerView = mRecycler;
        this.mCheckedChangeListener = mCheckedChangeListener;
        this. listener = listener;
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
        public TextView tvItemName,  tvItemPrice, tvMinus,tvPlus;
        public TextView tvTotalPrice, tvDiscount, tvDiscountPrice;
        public ImageView imvInfo,imvProduct,imvClear;
        public LinearLayout llDiscount;
        public CheckBox chkDiscount;
        public EditText etQtySelected;
        public RecyclerView mUserRecyclerView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mUserRecyclerView = itemView.findViewById(R.id.recycleView);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvItemPrice =  itemView.findViewById(R.id.tvItemPrice);
            imvInfo =  itemView.findViewById(R.id.imvInfo);
            imvProduct =  itemView.findViewById(R.id.imvProduct);
            imvClear =  itemView.findViewById(R.id.imvClear);
            tvMinus =  itemView.findViewById(R.id.tvMinus);
            etQtySelected =  itemView.findViewById(R.id.etQtySelected);
            tvPlus =  itemView.findViewById(R.id.tvPlus);
            tvTotalPrice =  itemView.findViewById(R.id.tvTotalPrice);
            chkDiscount =  itemView.findViewById(R.id.chkDiscount);
            tvDiscount =  itemView.findViewById(R.id.tvDiscount);
            tvDiscountPrice =  itemView.findViewById(R.id.tvDiscountPrice);
            llDiscount = itemView.findViewById(R.id.llDiscount);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.show_new_order_items, parent, false);
            return new NewOrderListAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new NewOrderListAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewOrderListAdapter.UserViewHolder) {
            onBind = true;
            final OrderList.Datum str = mDataset.get(position);
            AppLog.e(NewOrderListAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            final NewOrderListAdapter.UserViewHolder userViewHolder = (NewOrderListAdapter.UserViewHolder) holder;
            userViewHolder.tvItemName.setText(str.getSProductName());
            userViewHolder.tvItemPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getSProductPrice());
            userViewHolder.etQtySelected.setText(str.getQty()+"");
            onBind = false;


            Double totalPrice=(Double.parseDouble(str.getSProductPrice())*str.getQty());
//            str.setTotalPrice(totalPrice);

            if(str.getIsDiscount()) {
                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getSDiscountPrice());
                userViewHolder.tvDiscount.setText(str.getSDiscountName());
//                str.setDiscItemSelected(true);
                Double discount = (Double.parseDouble(str.getSDiscountPrice())*totalPrice)/100;
//                str.setDiscount(discount);

                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+discount+"");
                userViewHolder.llDiscount.setVisibility(View.VISIBLE);
            }else {
//                str.setDiscItemSelected(false);
//                str.setTotalPrice(totalPrice);
//                str.setDiscount(0.0);
                userViewHolder.llDiscount.setVisibility(View.GONE);
            }
            if(str.isDiscItemSelected()) {
                userViewHolder.chkDiscount.setChecked(true);
                userViewHolder.tvDiscount.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                userViewHolder.tvDiscountPrice.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }else {
                userViewHolder.chkDiscount.setChecked(false);

                userViewHolder.tvDiscount.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                userViewHolder.tvDiscountPrice.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
//            if(str.isEdited()){
//                userViewHolder.etQtySelected.setEnabled(true);
//            }else {
//                userViewHolder.etQtySelected.setEnabled(false);
//            }

            userViewHolder.tvTotalPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+totalPrice);
//            IPOSApplication.mProductList.set(position,str);
//            AppLog.e(RetailSalesAdapter.class.getSimpleName(), "IPOSApplication.mProductList: "+ Util.getCustomGson().toJson(IPOSApplication.mProductList));

            userViewHolder.tvMinus.setOnClickListener(mOnClickListener);
            userViewHolder.tvMinus.setTag(position);

            userViewHolder.tvPlus.setOnClickListener(mOnClickListener);
            userViewHolder.tvPlus.setTag(position);


            userViewHolder.chkDiscount.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkDiscount.setTag(position);



            userViewHolder.imvClear.setOnClickListener(mOnClickListener);
            userViewHolder.imvClear.setTag(position);
            userViewHolder.etQtySelected.setTag(position);
            userViewHolder.etQtySelected.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!onBind) {
                        if (!charSequence.toString().isEmpty()) {
                            if (Integer.parseInt(charSequence.toString())<1) {
                                listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1+""));
                            }else {
                                listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(charSequence.toString()));
                            }

                        }else {
                            listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1+""));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            userViewHolder.mUserRecyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            userViewHolder.mUserRecyclerView.setLayoutManager(mLayoutManager);
            DiscountNewOrderListAdapter itemListDataAdapter = new DiscountNewOrderListAdapter(mContext,userViewHolder.mUserRecyclerView, mDataset.get(position).getDiscount());
            userViewHolder.mUserRecyclerView.setAdapter(itemListDataAdapter);
        }
        else if (holder instanceof NewOrderListAdapter.LoadingViewHolder) {
            NewOrderListAdapter.LoadingViewHolder loadingViewHolder = (NewOrderListAdapter.LoadingViewHolder) holder;
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
