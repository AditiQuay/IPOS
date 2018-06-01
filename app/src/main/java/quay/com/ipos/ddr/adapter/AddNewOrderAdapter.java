package quay.com.ipos.ddr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 04-05-2018.
 */

public class AddNewOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    ArrayList<OrderList.Datum> mDataset;
    RecyclerView mRecyclerView;

    public AddNewOrderAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                              ArrayList<OrderList.Datum> questionList,AdapterListener listener) {
        this.mOnClickListener = mClickListener;
        mContext = ctx;
        mDataset = questionList;
        mRecyclerView = mRecycler;
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
                tvMinus,tvPlus,tvOffers,tvReserved,tvAddCart;
        public ImageView imvProduct,imvInfo;
        public EditText etQtySelected;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvItemPrice =  itemView.findViewById(R.id.tvItemPrice);
            tvItemStockAvailabilty =  itemView.findViewById(R.id.tvItemStockAvailabilty);
            tvCheckStock =  itemView.findViewById(R.id.tvCheckStock);
          //  tvMinus =  itemView.findViewById(R.id.tvMinus);
          //  etQtySelected =  itemView.findViewById(R.id.etQtySelected);
          //  tvPlus =  itemView.findViewById(R.id.tvPlus);
          //  tvOffers =  itemView.findViewById(R.id.tvOffers);
         //   tvReserved =  itemView.findViewById(R.id.tvReserved);
            tvAddCart =  itemView.findViewById(R.id.tvAddCart);
         //   imvProduct =  itemView.findViewById(R.id.imvProduct);
            imvInfo = itemView.findViewById(R.id.imvInfo);
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
            return new AddNewOrderAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new AddNewOrderAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddNewOrderAdapter.UserViewHolder) {
            onBind = true;
            OrderList.Datum str = mDataset.get(position);
//            AppLog.e(AddNewOrderAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            final AddNewOrderAdapter.UserViewHolder userViewHolder = (AddNewOrderAdapter.UserViewHolder) holder;
            userViewHolder.tvItemName.setText(str.getSProductName());
            userViewHolder.tvItemPrice.setText(mContext.getResources().getString(R.string.Rs)+" "+str.getSProductPrice());
          //  userViewHolder.etQtySelected.setText(str.getQty()+"");
            onBind = false;

            userViewHolder.tvCheckStock.setOnClickListener(mOnClickListener);
            userViewHolder.tvCheckStock.setTag(position);

           userViewHolder.tvAddCart.setOnClickListener(mOnClickListener);
            userViewHolder.tvAddCart.setTag(position);

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
        }
        else if (holder instanceof AddNewOrderAdapter.LoadingViewHolder) {
            AddNewOrderAdapter.LoadingViewHolder loadingViewHolder = (AddNewOrderAdapter.LoadingViewHolder) holder;
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
