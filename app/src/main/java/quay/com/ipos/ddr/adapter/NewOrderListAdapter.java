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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import quay.com.ipos.R;
import quay.com.ipos.ddr.modal.DiscountModal;
import quay.com.ipos.enums.RetailSalesEnum;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.MyCheckedChangedListener;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.utility.Util;


public class NewOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyCheckedChangedListener {
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
    ArrayList<RealmNewOrderCart> mDataset;
    RecyclerView mRecyclerView;
    CompoundButton.OnCheckedChangeListener mCheckedChangeListener;
    TextWatcher mTextWatcher;
    MyCheckedChangedListener myCheckedChangedListener;

    public NewOrderListAdapter(Context ctx, View.OnClickListener mClickListener, RecyclerView mRecycler,
                               ArrayList<RealmNewOrderCart> questionList, CompoundButton.OnCheckedChangeListener mCheckedChangeListener, AdapterListener listener,MyCheckedChangedListener myCheckedChangedListener) {
        this.mOnClickListener = mClickListener;
        this.mContext = ctx;
        this.mDataset = questionList;
        this.mRecyclerView = mRecycler;
        this.mCheckedChangeListener = mCheckedChangeListener;
        this. listener = listener;
        this.myCheckedChangedListener=myCheckedChangedListener;
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
    public void onDiscount(DiscountModal discountModal, int position, boolean b, String productId, String productCode) {

        myCheckedChangedListener.onDiscount(discountModal,position, b, productId, productCode);

    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName,  tvItemPrice, tvMinus,tvPlus,tvCheckStock;
        public TextView tvTotalPrice, tvDiscount, tvDiscountPrice,tvItemStockAvailabilty,tvPoints;
        public ImageView imvInfo,imvProduct,imvClear;
        public LinearLayout llDiscount;
        public CheckBox chkDiscount;
        public EditText etQtySelected;
        public RecyclerView mUserRecyclerView;
        public ImageView imvOffer;
        public TextView tvTotalPoints;
        public LinearLayout llAddMinus;

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
            tvItemStockAvailabilty=itemView.findViewById(R.id.tvItemStockAvailabilty);
            imvOffer=itemView.findViewById(R.id.imvOffer);
            tvCheckStock=itemView.findViewById(R.id.tvCheckStock);
            tvPoints=itemView.findViewById(R.id.tvPoints);
            tvTotalPoints=itemView.findViewById(R.id.tvTotalPoints);
            llAddMinus=itemView.findViewById(R.id.llAddMinus);
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
            final RealmNewOrderCart realmNewOrderCart = mDataset.get(position);
            Realm realm=Realm.getDefaultInstance();
            RealmNewOrderCart str=realm.where(RealmNewOrderCart.class).equalTo(RetailSalesEnum.iProductModalId.toString(),realmNewOrderCart.getiProductModalId()).findFirst();
            final NewOrderListAdapter.UserViewHolder userViewHolder = (NewOrderListAdapter.UserViewHolder) holder;

            if (str!=null) {
                if (Util.validateString(str.getsProductName()))
                    userViewHolder.tvItemName.setText(str.getsProductName());
                userViewHolder.tvItemPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + str.getsProductPrice());
                userViewHolder.etQtySelected.setText(str.getQty() + "");
                userViewHolder.tvItemStockAvailabilty.setText(str.getsProductStock().substring(0, 1).toUpperCase() + str.getsProductStock().substring(1).toLowerCase());
                userViewHolder.tvPoints.setText(str.getPoints() + " Pts.");
                userViewHolder.tvTotalPoints.setText(str.getTotalPoints() + " Pts.");

                if (str.isFreeItem()) {
                    userViewHolder.imvOffer.setVisibility(View.GONE);
                    userViewHolder.tvTotalPrice.setText(" Free " + str.getQty()+" * "+mContext.getResources().getString(R.string.Rs) +" "+str.getsProductPrice());

                    userViewHolder.tvTotalPrice.setPaintFlags(userViewHolder.tvTotalPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    userViewHolder.tvCheckStock.setVisibility(View.GONE);
                    userViewHolder.llAddMinus.setVisibility(View.GONE);
                } else {
                    if (str.isDiscount()) {
                        userViewHolder.imvOffer.setVisibility(View.VISIBLE);
                    } else {
                        userViewHolder.imvOffer.setVisibility(View.GONE);
                    }
                    userViewHolder.tvTotalPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + str.getTotalPrice());

                    if (str.isCheckStock()) {
                        userViewHolder.tvCheckStock.setVisibility(View.VISIBLE);
                    } else {
                        userViewHolder.tvCheckStock.setVisibility(View.GONE);
                    }
                    userViewHolder.llAddMinus.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(str.getProductImage()).into(userViewHolder.imvProduct);


                onBind = false;


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
                        if (!onBind) {
                           /* if (!charSequence.toString().isEmpty()) {
                                if (Integer.parseInt(charSequence.toString()) < 1) {
                                    listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1 + ""));
                                } else {
                                    listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(charSequence.toString()));
                                }

                            } else {
                                listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1 + ""));
                            }*/
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!onBind) {
                            if (!editable.toString().isEmpty()) {
                                if (Integer.parseInt(editable.toString()) < 1) {
                                    listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1 + ""));
                                } else {
                                    listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(editable.toString()));
                                }

                            } else {
                                listener.onRowClicked(userViewHolder.getAdapterPosition(), Integer.parseInt(1 + ""));
                            }
                        }
                    }
                });
                //     Gson gson = new GsonBuilder().create();
                ///     NewOrderProductsResult.DataBean.DiscountBean discountBean=  gson.fromJson(mDataset.get(position).getDiscount(),NewOrderProductsResult.DataBean.DiscountBean.class);


                    ArrayList<DiscountModal> discounts = new ArrayList<>();
                    // discounts.add(discountBean);
                if (!str.isFreeItem()) {
                    try {
                        JSONArray array = new JSONArray(realmNewOrderCart.getDiscount());
                        for (int k = 0; k < array.length(); k++) {
                            JSONObject jsonObject = array.optJSONObject(k);
                            if (jsonObject.has("discountTotal") && jsonObject.optInt("discountTotal") > 0) {
                                DiscountModal discountBean = new DiscountModal();
                                discountBean.setDiscountTotal(jsonObject.optInt("discountTotal"));
                                discountBean.setRule(jsonObject.optString("rule"));
                                discountBean.setsDiscountDisplayName(jsonObject.optString("sDiscountDisplayName"));
                                discountBean.setsDiscountName(jsonObject.optString("sDiscountName"));
                                discounts.add(discountBean);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                userViewHolder.mUserRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                userViewHolder.mUserRecyclerView.setLayoutManager(mLayoutManager);
                DiscountNewOrderAdapter itemListDataAdapter = new DiscountNewOrderAdapter(mContext, userViewHolder.mUserRecyclerView, discounts,this,str.getiProductModalId(),str.getProductCode());
                userViewHolder.mUserRecyclerView.setAdapter(itemListDataAdapter);
            }
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
