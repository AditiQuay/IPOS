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
import java.util.Timer;
import java.util.TimerTask;

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

public class RetailSalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterListener,MyAdapterTags {
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
        this.myAdapterTags = myAdapterTags;

    }

    @Override
    public void onRowClicked(int position) {
        if(position==23){
            listener.onRowClicked(position);
        }
    }

    @Override
    public void onRowClicked(int position, int value, String tag) {

    }

    @Override
    public void onRowClicked(int position, int value, String tag, int parentPosition) {

    }

    @Override
    public void onRowClicked(int position, int value) {

        notifyItemRangeRemoved(value,IPOSApplication.mProductListResult.size());
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName,  tvItemRate, tvItemPrice, tvMinus,tvPlus,tvPoint,tvTotalPoints;
        public TextView tvTotalPrice, tvOTCDiscountPrice,tvDiscountedPrice,tvFreeItems;
        public ImageView imvInfo,imvProduct,imvClear,imvOffer;
        public LinearLayout llOTCDiscount,llEvent,llTotalPoints,llPoints,llInnerItem,llStock,llName;
        public CheckBox chkItem,chkOTCDiscount;
        public EditText etQtySelected;
        public RecyclerView mRecyclerView;


        public UserViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.recycleView);

            llEvent = itemView.findViewById(R.id.llEvent);
            llName = itemView.findViewById(R.id.llName);
            llStock = itemView.findViewById(R.id.llStock);
            llInnerItem = itemView.findViewById(R.id.llInnerItem);
            llOTCDiscount = itemView.findViewById(R.id.llOTCDiscount);
            tvOTCDiscountPrice = itemView.findViewById(R.id.tvOTCDiscountPrice);
            tvDiscountedPrice = itemView.findViewById(R.id.tvDiscountedPrice);
            chkOTCDiscount = itemView.findViewById(R.id.chkOTCDiscount);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
//            tvItemWeight =  itemView.findViewById(R.id.tvItemWeight);
            tvItemRate =  itemView.findViewById(R.id.tvItemRate);
            tvItemPrice =  itemView.findViewById(R.id.tvItemPrice);
            tvFreeItems =  itemView.findViewById(R.id.tvFreeItems);
            imvInfo =  itemView.findViewById(R.id.imvInfo);
            imvProduct =  itemView.findViewById(R.id.imvProduct);
            imvClear =  itemView.findViewById(R.id.imvClear);
            imvOffer =  itemView.findViewById(R.id.imvOffer);
            tvMinus =  itemView.findViewById(R.id.tvMinus);
            etQtySelected =  itemView.findViewById(R.id.etQtySelected);
            tvPlus =  itemView.findViewById(R.id.tvPlus);
            tvPoint =  itemView.findViewById(R.id.tvPoint);
            tvTotalPrice =  itemView.findViewById(R.id.tvTotalPrice);
            tvTotalPoints =  itemView.findViewById(R.id.tvTotalPoints);
            llTotalPoints =  itemView.findViewById(R.id.llTotalPoints);
            llPoints =  itemView.findViewById(R.id.llPoints);

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
            myAdapterTags.onRowClicked(position,0, Constants.DISCOUNT+"");
            final UserViewHolder  userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvItemName.setText(str.getSProductName() + " " +str.getSProductWeight() + " gm");
//            userViewHolder.tvItemWeight.setText(str.getSProductWeight() + " gm");
            userViewHolder.tvItemRate.setText(str.getSProductStock()+"");
            userViewHolder.tvItemPrice.setText(Util.getIndianNumberFormat(str.getSalesPrice()+""));
            userViewHolder.etQtySelected.setText(str.getQty()+"");

            ImageLoader.getInstance().displayImage(str.getProductImage(),userViewHolder.imvProduct);

            Double totalPrice=(str.getSalesPrice()*str.getQty());

            if(str.getPoints()!=null && !str.getPoints().equals("")){
                userViewHolder.tvPoint.setText(str.getPoints() +" Pts");
                double points = getTotalPoints(str,totalPrice,str.getSalesPrice(),str.getQty());
                userViewHolder.tvTotalPoints.setText((int)points+" Pts");
                str.setTotalPoints(points);
                IPOSApplication.mProductListResult.set(position,str);
            }

//            str.setTotalPrice(totalPrice);

//                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+str.getSDiscountPrice());
//                userViewHolder.tvDiscount.setText(str.getSDiscountName());
//                Double discount = (str.getSDiscountPrice()*totalPrice)/100;
//
//                userViewHolder.tvDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) +" "+discount+"");
//                userViewHolder.llDiscount.setVisibility(View.VISIBLE);



//            userViewHolder.llDiscount.setVisibility(View.GONE);




            if(str.isFreeItem()){
                userViewHolder.tvTotalPoints.setVisibility(View.GONE);
                userViewHolder.tvPoint.setVisibility(View.GONE);
                userViewHolder.llEvent.setVisibility(View.GONE);
                userViewHolder.tvTotalPrice.setText(Util.getIndianNumberFormat( str.getSalesPrice()+""));
                userViewHolder.chkItem.setVisibility(View.GONE);
                userViewHolder.llOTCDiscount.setVisibility(View.GONE);
                userViewHolder.llPoints.setVisibility(View.GONE);
                userViewHolder.llTotalPoints.setVisibility(View.GONE);
                userViewHolder.tvFreeItems.setVisibility(View.VISIBLE);
                userViewHolder.imvClear.setVisibility(View.GONE);
                userViewHolder.imvOffer.setVisibility(View.GONE);
                userViewHolder.tvItemRate.setVisibility(View.GONE);
            }else {
                userViewHolder.imvClear.setVisibility(View.VISIBLE);
                userViewHolder.tvItemRate.setVisibility(View.VISIBLE);
                userViewHolder.imvOffer.setVisibility(View.VISIBLE);
                userViewHolder.tvFreeItems.setVisibility(View.GONE);
                userViewHolder.tvTotalPoints.setVisibility(View.VISIBLE);
                userViewHolder.tvPoint.setVisibility(View.VISIBLE);
                userViewHolder.llEvent.setVisibility(View.VISIBLE);
                userViewHolder.llPoints.setVisibility(View.VISIBLE);
                userViewHolder.llTotalPoints.setVisibility(View.VISIBLE);
                userViewHolder.tvTotalPrice.setText(Util.getIndianNumberFormat(totalPrice+""));

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
                    userViewHolder.tvOTCDiscountPrice.setText("- "+Util.getIndianNumberFormat(str.getOTCDiscount()+""));
                }
                else {
                    userViewHolder.llOTCDiscount.setVisibility(View.GONE);
                }
            }
//            IPOSApplication.mProductList.set(position,str);
//            AppLog.e(RetailSalesAdapter.class.getSimpleName(), "IPOSApplication.mProductList: "+ Util.getCustomGson().toJson(IPOSApplication.mProductList));
            onBind = false;
            userViewHolder.etQtySelected.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                public void onFocusChange(View v, boolean hasFocus){
                    if(!onBind)
                        if ((hasFocus) && (v.isPressed()))
                            ((EditText)v).selectAll();
                }
            });
            userViewHolder.llInnerItem.setOnClickListener(mOnClickListener);
            userViewHolder.llInnerItem.setTag(position);

            userViewHolder.llName.setOnClickListener(mOnClickListener);
            userViewHolder.llName.setTag(position);

            userViewHolder.llStock.setOnClickListener(mOnClickListener);
            userViewHolder.llStock.setTag(position);

            userViewHolder.imvInfo.setOnClickListener(mOnClickListener);
            userViewHolder.imvInfo.setTag(position);

            userViewHolder.tvMinus.setOnClickListener(mOnClickListener);
            userViewHolder.tvMinus.setTag(position);

            userViewHolder.tvPlus.setOnClickListener(mOnClickListener);
            userViewHolder.tvPlus.setTag(position);

            userViewHolder.chkItem.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkItem.setTag(position);


            userViewHolder.chkOTCDiscount.setOnCheckedChangeListener(mCheckedChangeListener);
            userViewHolder.chkOTCDiscount.setTag(position);


            userViewHolder.imvClear.setOnClickListener(mOnClickListener);
            userViewHolder.imvClear.setTag(position);

            userViewHolder.etQtySelected.setTag(position);
            final int[] qty = {0};
            final Timer[] timer = new Timer[1];
            userViewHolder.etQtySelected.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    userViewHolder.etQtySelected.setSelectAllOnFocus(false);
                    userViewHolder.etQtySelected.setSelection(userViewHolder.etQtySelected.getText().length());
                }

                @Override
                public void afterTextChanged(final Editable editable) {

                    if(!onBind) {
                        timer[0] = new Timer();
                        timer[0].schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (!editable.toString().isEmpty()) {
                                    qty[0] = Integer.parseInt(editable.toString());
                                    if (qty[0]<1) {
                                        listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1+""));
                                        qty[0] = 0;
                                    }else {
                                        listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(editable.toString()));
                                    }

                                }else {
                                    listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1+""));
                                    qty[0] = 0;
                                }
                            }
                        }, 600);
                    }
//                    userViewHolder.etQtySelected.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                            if(qty[0] == 0){
////                                listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1+""));
////                                qty[0]=1;
////                            }
//                        }
//                    },1000);

                }
            });
            userViewHolder.etQtySelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((EditText)view).selectAll();
                }
            });

            userViewHolder.mRecyclerView.setHasFixedSize(true);
//            WrapContentLinearLayoutManager mLayoutManager = new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

            userViewHolder.mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//            userViewHolder.mRecyclerView.setLayoutManager(mLayoutManager);
//            DiscountListAdapter itemListDataAdapter = new DiscountListAdapter(mContext,userViewHolder.mRecyclerView, mDataset.get(position).getDiscount());
            if(str.getIsDiscount()) {
                userViewHolder.mRecyclerView.setVisibility(View.VISIBLE);
                userViewHolder.imvOffer.setVisibility(View.VISIBLE);
                DiscountListAdapter itemListDataAdapter = new DiscountListAdapter(mContext, userViewHolder.mRecyclerView, mDataset.get(userViewHolder.getAdapterPosition()).getDiscount(), mDataset.get(userViewHolder.getAdapterPosition()), userViewHolder.getAdapterPosition(), listener,myAdapterTags);
                userViewHolder.mRecyclerView.setAdapter(itemListDataAdapter);
            }else {
                userViewHolder.mRecyclerView.setVisibility(View.GONE);
                userViewHolder.imvOffer.setVisibility(View.GONE);
            }

            if(position==getItemCount()-1){
                listener.onRowClicked(position);

            }
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


    private double getTotalPoints(ProductSearchResult.Datum str, double totalPrice, Double sProductPrice, int qty){
        double totalPoints=0;
//        if (str.getPointsBasedOn().equalsIgnoreCase("M")){
        totalPoints=str.getPoints()*qty;

//        }else if (str.getPointsBasedOn().equalsIgnoreCase("P")){
//            int valuefrom=str.getValueFrom();
//            int valueTo=str.getValueTo();
//            int perPoints=str.getPointsPer();
//            int points=str.getPoints();
//
//            if (totalPrice >= valuefrom && totalPrice <= valueTo){
//                totalPoints=(perPoints * totalPrice) / points;
//            }else if (totalPrice > valueTo){
//                totalPoints=(perPoints * valueTo) / points;
//            }
//
//        }else if (str.getPointsBasedOn().equalsIgnoreCase("V")){
//            int valuefrom=str.getValueFrom();
//            int valueTo=str.getValueTo();
//            int perPoints=str.getPointsPer();
//            int points=str.getPoints();
//
//            if (totalPrice>=valuefrom && totalPrice<=valueTo){
//                totalPoints=perPoints*totalPrice/points;
//            }else if (totalPrice>valueTo){
//                totalPoints=perPoints*valueTo/points;
//            }
//
//        }

        return totalPoints;
    }

// public void setLoaded() {
// isLoading = false;
// }
}