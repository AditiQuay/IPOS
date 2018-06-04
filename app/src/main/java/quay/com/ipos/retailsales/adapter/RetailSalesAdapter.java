package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.MyAdapterTags;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.ui.WrapContentLinearLayoutManager;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;



/**
 * Created by aditi.bhuranda on 17-04-2018.
 */

public class RetailSalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterListener {
    private boolean onBind;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private MainActivity mainActivity;
    // private OnLoadMoreListener mOnLoadMoreListener;
    private AdapterListener listener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<ProductSearchResult.Datum> mDataset;
    CompoundButton.OnCheckedChangeListener mCheckedChangeListener;
    TextWatcher mTextWatcher;
    MyAdapterTags myAdapterTags;
 //   public RecyclerView mRecyclerView;
    public RetailSalesAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                              ArrayList<ProductSearchResult.Datum> questionList, CompoundButton.OnCheckedChangeListener mCheckedChangeListener, AdapterListener listener,MyAdapterTags myAdapterTags) {
        this.mOnClickListener = mClickListener;
        this.mContext = ctx;
        this.mDataset = questionList;
    //    this.mRecyclerView = mRecycler;
        this.mCheckedChangeListener = mCheckedChangeListener;
        this. listener = listener;
        mainActivity = (MainActivity) mContext;
        this.myAdapterTags = myAdapterTags;

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

    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onRowClicked(int position, int value) {

        notifyItemRangeRemoved(value,IPOSApplication.mProductListResult.size());
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName, tvItemWeight, tvItemRate, tvItemPrice, tvMinus,tvPlus;
        public TextView tvTotalPrice, tvDiscount, tvDiscountPrice,tvOTCDiscountPrice,tvDiscountedPrice;
        public ImageView imvInfo,imvProduct,imvClear;
        public LinearLayout llDiscount,llOTCDiscount,llEvent;
        public CheckBox chkDiscount,chkItem,chkOTCDiscount;
        public EditText etQtySelected;
        public RecyclerView mRecyclerView;


        public UserViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.recycleView);

            llEvent = itemView.findViewById(R.id.llEvent);
            llOTCDiscount = itemView.findViewById(R.id.llOTCDiscount);
            tvOTCDiscountPrice = itemView.findViewById(R.id.tvOTCDiscountPrice);
            tvDiscountedPrice = itemView.findViewById(R.id.tvDiscountedPrice);
            chkOTCDiscount = itemView.findViewById(R.id.chkOTCDiscount);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvItemWeight =  itemView.findViewById(R.id.tvItemWeight);
            tvItemRate =  itemView.findViewById(R.id.tvItemRate);
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
            chkItem = itemView.findViewById(R.id.chkItem);
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
            onBind = true;
            ProductSearchResult.Datum str = mDataset.get(position);
            if(IPOSApplication.isRefreshed) {
                str = mDataset.get(0);
                IPOSApplication.isRefreshed=false;
            }
            Log.e("UserViewHolder","UserViewHolder-------------------------"+position);
            myAdapterTags.onRowClicked(position,0, Constants.DISCOUNT+"");
            AppLog.e(RetailSalesAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            final UserViewHolder  userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvItemName.setText(str.getSProductName());
            userViewHolder.tvItemWeight.setText(str.getSProductWeight() + " gm");
            userViewHolder.tvItemRate.setText(str.getSProductStock()+"");
            userViewHolder.tvItemPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getSalesPrice());
            userViewHolder.etQtySelected.setText(str.getQty()+"");
            ImageLoader.getInstance().displayImage(str.getProductImage(),userViewHolder.imvProduct);
            onBind = false;

            if(str.isItemSelected())
                userViewHolder.chkItem.setVisibility(View.VISIBLE);
            else
                userViewHolder.chkItem.setVisibility(View.GONE);

            if(str.isOTCselected())
                userViewHolder.chkItem.setChecked(true);
            else
                userViewHolder.chkItem.setChecked(false);


            if(str.isDiscSelected()) {
                userViewHolder.chkOTCDiscount.setChecked(true);
                userViewHolder.llOTCDiscount.setVisibility(View.VISIBLE);
                userViewHolder.tvOTCDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getOTCDiscount());
            }
            else {
                userViewHolder.llOTCDiscount.setVisibility(View.GONE);
            }

            Double totalPrice=(str.getSalesPrice()*str.getQty());
//            str.setTotalPrice(totalPrice);

            if(str.getIsDiscount()) {
//                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getSDiscountPrice());
//                userViewHolder.tvDiscount.setText(str.getSDiscountName());
//                Double discount = (str.getSDiscountPrice()*totalPrice)/100;
//
//                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+discount+"");
//                userViewHolder.llDiscount.setVisibility(View.VISIBLE);
            }else {
//                userViewHolder.llDiscount.setVisibility(View.GONE);
            }


            userViewHolder.llDiscount.setVisibility(View.GONE);
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



            if(str.isFreeItem()){
                userViewHolder.llEvent.setVisibility(View.GONE);
                userViewHolder.tvTotalPrice.setText(mContext.getResources().getString(R.string.Rs) +" " + str.getSProductPrice());
            }else {
                userViewHolder.llEvent.setVisibility(View.VISIBLE);
                userViewHolder.tvTotalPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+totalPrice);
            }
//            IPOSApplication.mProductList.set(position,str);
//            AppLog.e(RetailSalesAdapter.class.getSimpleName(), "IPOSApplication.mProductList: "+ Util.getCustomGson().toJson(IPOSApplication.mProductList));

            userViewHolder.tvMinus.setOnClickListener(mOnClickListener);
            userViewHolder.tvMinus.setTag(position);

            userViewHolder.tvPlus.setOnClickListener(mOnClickListener);
            userViewHolder.tvPlus.setTag(position);

            userViewHolder.chkItem.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkItem.setTag(position);

            userViewHolder.chkDiscount.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkDiscount.setTag(position);

            userViewHolder.chkOTCDiscount.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkOTCDiscount.setTag(position);


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


            userViewHolder.mRecyclerView.setHasFixedSize(true);
//            WrapContentLinearLayoutManager mLayoutManager = new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

            userViewHolder.mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//            userViewHolder.mRecyclerView.setLayoutManager(mLayoutManager);
//            DiscountListAdapter itemListDataAdapter = new DiscountListAdapter(mContext,userViewHolder.mRecyclerView, mDataset.get(position).getDiscount());

            DiscountListAdapter itemListDataAdapter = new DiscountListAdapter(mContext, userViewHolder.mRecyclerView, mDataset.get(userViewHolder.getAdapterPosition()).getDiscount(), mDataset.get(userViewHolder.getAdapterPosition()),userViewHolder.getAdapterPosition(),listener);
            userViewHolder.mRecyclerView.setAdapter(itemListDataAdapter);
            //setDiscountAdapter(userViewHolder);
        }
        else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true); }

    }

   /* public void setDiscountAdapter(UserViewHolder userViewHolder){
        if(itemListDataAdapter!=null)
            itemListDataAdapter.notifyDataSetChanged();
        else {
            if(userViewHolder!=null) {
                itemListDataAdapter = new DiscountListAdapter(mContext, userViewHolder.mRecyclerView, mDataset.get(userViewHolder.getAdapterPosition()).getDiscount(), mDataset.get(userViewHolder.getAdapterPosition()),userViewHolder.getAdapterPosition());
                userViewHolder.mRecyclerView.setAdapter(itemListDataAdapter);
            }
        }
    }*/

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