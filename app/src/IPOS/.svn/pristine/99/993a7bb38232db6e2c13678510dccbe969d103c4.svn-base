package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import quay.com.ipos.R;
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.pss_order.adapter.DiscountNewOrderListAdapter;
import quay.com.ipos.pss_order.modal.NewOrderProductsResult;

import quay.com.ipos.realmbean.RealmInventoryProducts;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 04-05-2018.
 */

public class InventoryAddNewOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;
    AdapterListener listener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ArrayList<NewOrderProductsResult.DataBean> mDataset;



    public InventoryAddNewOrderAdapter(Context ctx, View.OnClickListener mClickListener,
                                       ArrayList<NewOrderProductsResult.DataBean> questionList, AdapterListener listener) {
        this.mOnClickListener = mClickListener;
        mContext = ctx;
        mDataset = questionList;

        this.listener = listener;
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
        public TextView tvItemName, tvItemPrice, tvItemStockAvailabilty, tvCheckStock,
                tvMinus,tvPlus,tvOffers,tvReserved,tvAddCart,tvPoints,tvStocks;
        public ImageView imvProduct,imvInfo,imvOffer;
        public EditText etQtySelected;
        RecyclerView mRecyclerView;
        public LinearLayout llAdd,llStocks,llRefreshStocks;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvItemPrice =  itemView.findViewById(R.id.tvItemPrice);
            tvItemStockAvailabilty =  itemView.findViewById(R.id.tvItemStockAvailabilty);
            tvCheckStock =  itemView.findViewById(R.id.tvCheckStock);
            imvOffer=itemView.findViewById(R.id.imvOffer);
            tvPoints=itemView.findViewById(R.id.tvPoints);
            mRecyclerView = itemView.findViewById(R.id.recycleView);
          //  tvMinus =  itemView.findViewById(R.id.tvMinus);
          //  etQtySelected =  itemView.findViewById(R.id.etQtySelected);
          //  tvPlus =  itemView.findViewById(R.id.tvPlus);
          //  tvOffers =  itemView.findViewById(R.id.tvOffers);
         //   tvReserved =  itemView.findViewById(R.id.tvReserved);
            tvAddCart =  itemView.findViewById(R.id.tvAddCart);
            llAdd=(LinearLayout) itemView.findViewById(R.id.llAdd);
            imvProduct =  itemView.findViewById(R.id.imvProduct);
            imvInfo = itemView.findViewById(R.id.imvInfo);
            llStocks=itemView.findViewById(R.id.llStocks);
            tvStocks=itemView.findViewById(R.id.tvStocks);
            llRefreshStocks=itemView.findViewById(R.id.llRefreshStocks);

         //   etQtySelected =  itemView.findViewById(R.id.etQtySelected);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.add_new_order_list_item, parent, false);
            return new InventoryAddNewOrderAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new InventoryAddNewOrderAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InventoryAddNewOrderAdapter.UserViewHolder) {
            onBind = true;
            NewOrderProductsResult.DataBean str = mDataset.get(position);

            Realm realm=Realm.getDefaultInstance();
            RealmInventoryProducts realmNewOrderCart=realm.where(RealmInventoryProducts.class).equalTo(NoGetEntityEnums.iProductModalId.toString(),str.getIProductModalId()).findFirst();

            final InventoryAddNewOrderAdapter.UserViewHolder userViewHolder = (InventoryAddNewOrderAdapter.UserViewHolder) holder;

            if (realmNewOrderCart!=null){
                if(realmNewOrderCart.isAdded()){
                    userViewHolder.tvAddCart.setText("Added");
                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_rectangle_light_gray );
                }else {
                    userViewHolder.tvAddCart.setText("Add");
                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_drawable);
                }
            }

            userViewHolder.tvItemName.setText(str.getSProductName());
            Picasso.get().load(str.getProductImage()).into(userViewHolder.imvProduct);
            userViewHolder.tvItemPrice.setText(mContext.getResources().getString(R.string.Rs)+" "+ Util.indianNumberFormat(str.getSProductPrice()));
            userViewHolder.tvItemStockAvailabilty.setText(str.getSProductStock().substring(0,1).toUpperCase()+str.getSProductStock().substring(1).toLowerCase());
            userViewHolder.tvPoints.setText(str.getPoints()+ " Pts.");
            if (str.isIsDiscount()){
                userViewHolder.imvOffer.setVisibility(View.VISIBLE);
            }else {
                userViewHolder.imvOffer.setVisibility(View.GONE);
            }
            if (str.isIsCheckStock()){
                userViewHolder.tvCheckStock.setVisibility(View.VISIBLE);
                if (mDataset.get(holder.getAdapterPosition()).isCheckStockClick()) {
                    userViewHolder.llStocks.setVisibility(View.VISIBLE);
                    userViewHolder.tvStocks.setText(mDataset.get(holder.getAdapterPosition()).getmCheckStock() + "");
                    userViewHolder.tvCheckStock.setVisibility(View.GONE);
                }else {
                    userViewHolder.llStocks.setVisibility(View.GONE);
                    userViewHolder.tvStocks.setText(str.getmCheckStock() + "");
                    userViewHolder.tvCheckStock.setVisibility(View.VISIBLE);
                }

            }else {
                userViewHolder.tvCheckStock.setVisibility(View.GONE);
            }
          //  userViewHolder.etQtySelected.setText(str.getQty()+"");
            onBind = false;



            userViewHolder.llRefreshStocks.setOnClickListener(mOnClickListener);
            userViewHolder.llRefreshStocks.setTag(position);
            userViewHolder.tvCheckStock.setOnClickListener(mOnClickListener);
            userViewHolder.tvCheckStock.setTag(position);

            userViewHolder.tvCheckStock.setOnClickListener(mOnClickListener);
            userViewHolder.tvCheckStock.setTag(position);



           userViewHolder.llAdd.setOnClickListener(mOnClickListener);
            userViewHolder.llAdd.setTag(position);

         /*   userViewHolder.tvMinus.setOnClickListener(mOnClickListener);
            userViewHolder.tvMinus.setTag(position);

            userViewHolder.tvPlus.setOnClickListener(mOnClickListener);
            userViewHolder.tvPlus.setTag(position);*/

            userViewHolder.imvInfo.setOnClickListener(mOnClickListener);
            userViewHolder.imvInfo.setTag(position);

        //    userViewHolder.tvReserved.setOnClickListener(mOnClickListener);
          //  userViewHolder.tvReserved.setTag(position);

     //       userViewHolder.tvOffers.setOnClickListener(mOnClickListener);
      //      userViewHolder.tvOffers.setTag(position);

           /* userViewHolder.etQtySelected.setTag(position);
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
*/

            userViewHolder.mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            userViewHolder.mRecyclerView.setLayoutManager(mLayoutManager);

            ArrayList<NewOrderProductsResult.DataBean.DiscountBean> discountBeans=new ArrayList<>();
            NewOrderProductsResult.DataBean.DiscountBean discountBean=new NewOrderProductsResult.DataBean.DiscountBean();
            discountBean.setSDiscountDisplayName(str.getDiscount().size()+" Offers Applied");
            discountBeans.add(discountBean);
//            DiscountListAdapter itemListDataAdapter = new DiscountListAdapter(mContext,userViewHolder.mRecyclerView, mDataset.get(position).getDiscount());

            quay.com.ipos.pss_order.adapter.DiscountNewOrderListAdapter itemListDataAdapter = new DiscountNewOrderListAdapter(mContext, userViewHolder.mRecyclerView, discountBeans);
            userViewHolder.mRecyclerView.setAdapter(itemListDataAdapter);
        }
        else if (holder instanceof InventoryAddNewOrderAdapter.LoadingViewHolder) {
            InventoryAddNewOrderAdapter.LoadingViewHolder loadingViewHolder = (InventoryAddNewOrderAdapter.LoadingViewHolder) holder;
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
