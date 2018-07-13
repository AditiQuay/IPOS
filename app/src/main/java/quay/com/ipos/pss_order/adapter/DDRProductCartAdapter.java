package quay.com.ipos.pss_order.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.response.DDRNewOrderProductsResult;
import quay.com.ipos.listeners.AdapterListener;

import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 04-05-2018.
 */

public class DDRProductCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  //  private boolean onBind;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;
    AdapterListener listener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    List<DDRNewOrderProductsResult.DataBean> mDataset;
    private OnCartAddListener cartAddListener;
    public interface OnCartAddListener{
       void onCartAdded(int pos, DDRNewOrderProductsResult.DataBean product);
    }


    public DDRProductCartAdapter(Context ctx, View.OnClickListener mClickListener,
                                 List<DDRNewOrderProductsResult.DataBean> questionList, AdapterListener listener, OnCartAddListener cartAddListener) {
        this.mOnClickListener = mClickListener;
        mContext = ctx;
        mDataset = questionList;
        this.listener = listener;
        this.cartAddListener = cartAddListener;
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
            return new DDRProductCartAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new DDRProductCartAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DDRProductCartAdapter.UserViewHolder) {
            //onBind = true;
            final DDRNewOrderProductsResult.DataBean product = mDataset.get(position);
            final DDRProductCartAdapter.UserViewHolder userViewHolder = (DDRProductCartAdapter.UserViewHolder) holder;

          /*  Realm realm=Realm.getDefaultInstance();
            DDRProduct realmNewOrderCart=realm.where(DDRProduct.class).equalTo(NoGetEntityEnums.iProductModalId.toString(),product.getIProductModalId()).findFirst();


            if (realmNewOrderCart!=null){
                if(realmNewOrderCart.isAdded()){
                    userViewHolder.tvAddCart.setText("Added");
                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_rectangle_light_gray );
                }else {
                    userViewHolder.tvAddCart.setText("Add");
                    userViewHolder.llAdd.setBackgroundResource(R.drawable.button_drawable);
                }
            }*/
           if(product.isAdded){
                userViewHolder.tvAddCart.setText("Added");
                userViewHolder.llAdd.setBackgroundResource(R.drawable.button_rectangle_light_gray );
            }else {
                userViewHolder.tvAddCart.setText("Add");
                userViewHolder.llAdd.setBackgroundResource(R.drawable.button_drawable);
            }
            userViewHolder.tvItemName.setText(product.getSProductName());
            Glide.with(mContext).load(product.getProductImage()).into(userViewHolder.imvProduct);
            userViewHolder.tvItemPrice.setText(mContext.getResources().getString(R.string.Rs)+" "+ Util.indianNumberFormat(product.getSProductPrice()));
            String stokAvaialable = product.getSProductStock().substring(0, 1).toUpperCase() + product.getSProductStock().substring(1).toLowerCase();
            Log.i("stokAvaialable", stokAvaialable);
            int intStokeAvaialable=0;
            try {
                intStokeAvaialable = (int)Double.parseDouble(stokAvaialable);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userViewHolder.tvItemStockAvailabilty.setText(intStokeAvaialable+"");
            userViewHolder.tvPoints.setText(product.getPoints()+ " Pts.");
            if (product.isIsDiscount()){
                userViewHolder.imvOffer.setVisibility(View.VISIBLE);
            }else {
                userViewHolder.imvOffer.setVisibility(View.GONE);
            }
            if (product.isIsCheckStock()){
                userViewHolder.tvCheckStock.setVisibility(View.VISIBLE);
                if (mDataset.get(holder.getAdapterPosition()).isCheckStockClick()) {
                    userViewHolder.llStocks.setVisibility(View.VISIBLE);
                    userViewHolder.tvStocks.setText(mDataset.get(holder.getAdapterPosition()).getmCheckStock() + "");
                    userViewHolder.tvCheckStock.setVisibility(View.GONE);
                }else {
                    userViewHolder.llStocks.setVisibility(View.GONE);
                    userViewHolder.tvStocks.setText(product.getmCheckStock() + "");
                    userViewHolder.tvCheckStock.setVisibility(View.VISIBLE);
                }

            }else {
                userViewHolder.tvCheckStock.setVisibility(View.GONE);
            }
          //  userViewHolder.etQtySelected.setText(product.getQty()+"");
            //onBind = false;



            userViewHolder.llRefreshStocks.setOnClickListener(mOnClickListener);
            userViewHolder.llRefreshStocks.setTag(position);
            userViewHolder.tvCheckStock.setOnClickListener(mOnClickListener);
            userViewHolder.tvCheckStock.setTag(position);

            userViewHolder.tvCheckStock.setOnClickListener(mOnClickListener);
            userViewHolder.tvCheckStock.setTag(position);



            userViewHolder.llAdd.setTag(position);
            userViewHolder.llAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (product.isAdded) {
                        Util.showToast("Already Added!");
                    }else {
                        if (cartAddListener != null) {
                            int pos = (int) userViewHolder.llAdd.getTag();
                            Log.i("pos", pos + "");
                            product.isAdded = true;
                            notifyItemChanged(pos);
                            cartAddListener.onCartAdded(pos, product);

                           /* userViewHolder.tvAddCart.setText("Added");
                            userViewHolder.llAdd.setBackgroundResource(R.drawable.button_rectangle_light_gray );
                            */
                        }
                    }
                }
            });

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

            ArrayList<DDRNewOrderProductsResult.DataBean.DiscountBean> discountBeans=new ArrayList<>();
            DDRNewOrderProductsResult.DataBean.DiscountBean discountBean=new DDRNewOrderProductsResult.DataBean.DiscountBean();
            discountBean.setSDiscountDisplayName(product.getDiscount().size()+" Offers Applied");
            discountBeans.add(discountBean);
//            DiscountListAdapter itemListDataAdapter = new DiscountListAdapter(mContext,userViewHolder.mRecyclerView, mDataset.get(position).getDiscount());

            DDRDiscountNewOrderListAdapter itemListDataAdapter = new DDRDiscountNewOrderListAdapter(mContext, userViewHolder.mRecyclerView, discountBeans);
            userViewHolder.mRecyclerView.setAdapter(itemListDataAdapter);
        }
        else if (holder instanceof DDRProductCartAdapter.LoadingViewHolder) {
            DDRProductCartAdapter.LoadingViewHolder loadingViewHolder = (DDRProductCartAdapter.LoadingViewHolder) holder;
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
