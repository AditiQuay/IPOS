package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.MyAdapterTags;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;


/**
 * Created by aditi.bhuranda on 26-04-2018.
 */

public class DiscountListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;
    AdapterListener adapterListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    Context mContext;
    ProductSearchResult.Datum datum;
    ArrayList<ProductSearchResult.Discount> mDataset;
    private ArrayList<ProductSearchResult.Rule> rule = new            ArrayList<>();
    RecyclerView mRecyclerView;
    DatabaseHandler databaseHandler;
    public ArrayList<ProductSearchResult.Datum> minDiscount;
    int retailAdapterPosition;
    MyAdapterTags myAdapterTags;


    public DiscountListAdapter(Context ctx, RecyclerView mRecycler,
                               ArrayList<ProductSearchResult.Discount> questionList, ProductSearchResult.Datum str, int adapterPosition, AdapterListener adapterListener, MyAdapterTags myAdapterTags) {
        retailAdapterPosition = adapterPosition;
        mContext = ctx;
        mDataset = questionList;
        mRecyclerView = mRecycler;
        datum = str;
        databaseHandler = new DatabaseHandler(ctx);
        this.adapterListener=adapterListener;
        minDiscount=new ArrayList<>();
        this.myAdapterTags = myAdapterTags;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDiscountPrice,tvDiscount;
        public LinearLayout llDiscount;
        public CheckBox chkDiscount;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvDiscountPrice =  itemView.findViewById(R.id.tvDiscountPrice);
            tvDiscount=itemView.findViewById(R.id.tvDiscount);
            llDiscount = itemView.findViewById(R.id.llDiscount);
            chkDiscount=itemView.findViewById(R.id.chkDiscount);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.discount_item, parent, false);
            return new DiscountListAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new DiscountListAdapter.LoadingViewHolder(view); }

        return null;
    }

    int rulePosition=0;
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DiscountListAdapter.UserViewHolder) {
            onBind = true;
            ProductSearchResult.Discount str = mDataset.get(position);
            final DiscountListAdapter.UserViewHolder userViewHolder = (DiscountListAdapter.UserViewHolder) holder;

            setRules(userViewHolder,str,position);

            final  ProductSearchResult.Discount str1 = mDataset.get(position);
            onBind = false;
            ((UserViewHolder) holder).chkDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isPressed())
                        if (!onBind) {
                            if (b) {
                                userViewHolder.chkDiscount.setChecked(true);
                                setRules((UserViewHolder) holder,str1,position);
                            }else {
                                userViewHolder.chkDiscount.setChecked(false);
                            }

                            if(b)
                                myAdapterTags.onRowClicked(((UserViewHolder) holder).getAdapterPosition(),1,Constants.CHECK_DISCOUNT,retailAdapterPosition);
                            else
                                myAdapterTags.onRowClicked(((UserViewHolder) holder).getAdapterPosition(),0,Constants.CHECK_DISCOUNT,retailAdapterPosition);
                        }
                }
            });

            if(str.getDiscountTotal()<=0.0){
                userViewHolder.chkDiscount.setChecked(false);
                userViewHolder.llDiscount.setVisibility(View.GONE);
            }else {
                userViewHolder.chkDiscount.setChecked(true);
                userViewHolder.llDiscount.setVisibility(View.VISIBLE);
            }


            if(position==getItemCount()-1){
                myAdapterTags.onRowClicked(23);
            }
        }



        else if (holder instanceof DiscountListAdapter.LoadingViewHolder) {
            DiscountListAdapter.LoadingViewHolder loadingViewHolder = (DiscountListAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true); }

    }

    private void setRules(UserViewHolder userViewHolder, ProductSearchResult.Discount str, int position){

        userViewHolder.tvDiscount.setText(str.getSDiscountName());
        if(datum.isFreeItem() || str.getRule().size()==0){


            ((UserViewHolder) userViewHolder).tvDiscountPrice.setText(" - "+mContext.getResources().getString(R.string.Rs) +str.getDiscountTotal()+"");
        }else if(!datum.isFreeItem())
            if(str.getRule()!=null && str.getRule().size()>0) {
                rule = str.getRule();
                Collections.sort(rule, new Comparator<ProductSearchResult.Rule>() {
                    @Override
                    public int compare(ProductSearchResult.Rule lhs, ProductSearchResult.Rule rhs) {
                        int valueSort = 0;
                        valueSort = lhs.getRuleSequence().compareTo(rhs.getRuleSequence());
                        return valueSort;
                    }
                });
                double value = 0.0;

                if (str.isDiscItemSelected()) {

                    if (rule.size() > 0)
                        for (int i = 0; i < rule.size(); i++) {
                            rulePosition = i;
                            if (rule.get(i).getRuleType().equalsIgnoreCase("I")) {
                                value = setOPS(i, rule, datum, userViewHolder);

                                if (value > 0.0) {
                                    ProductSearchResult.Rule mRule = rule.get(i);
                                    mRule.setApplied(true);
                                    rule.set(i, mRule);
                                    str.setRule(rule);
                                    str.setRuleID(rule.get(i).getRuleID());
                                    mDataset.set(((UserViewHolder) userViewHolder).getAdapterPosition(), str);
                                    datum.setDiscount(mDataset);
                                    IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);
//                                            i++;
                                } else {
//                                        break;
                                }
                            }
                            if (rule.get(i).getRuleType().equalsIgnoreCase("D")) {
                                if (IPOSApplication.isClicked) {
                                    int predecessor = rule.get(i).getRuleProdecessors();
                                    for (int h = 0; h < rule.size(); h++) {


                                        if (predecessor == rule.get(h).getRuleID()) {
                                            if (rule.get(h).isApplied()) {
                                                value = setOPS(i, rule, datum, userViewHolder);
                                                if (value > 0.0) {
                                                    ProductSearchResult.Rule mRule = rule.get(h);
                                                    mRule.setApplied(true);
                                                    rule.set(i - 1, mRule);
                                                    str.setRule(rule);
                                                    str.setRuleID(rule.get(i).getRuleID());
                                                    mDataset.set(((UserViewHolder) userViewHolder).getAdapterPosition(), str);
                                                    datum.setDiscount(mDataset);
                                                    IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);

//

//     i++;
                                                } else {
//                                                        break;
                                                    // checkDependentPrecessor(i, value, holder, str, rule, datum, predecessor);
                                                }
                                            } else {
//                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    if (value > 0.0) {
                        ProductSearchResult.Discount mDiscount = mDataset.get(((UserViewHolder) userViewHolder).getAdapterPosition());
                        mDiscount.setDiscountTotal(value);
                        mDataset.set(((UserViewHolder) userViewHolder).getAdapterPosition(), mDiscount);

                        datum.setDiscount(mDataset);
                        IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);
                        ((UserViewHolder) userViewHolder).tvDiscountPrice.setText(" - "+mContext.getResources().getString(R.string.Rs) +value + "");
                    }
                    userViewHolder.chkDiscount.setChecked(true);
                    userViewHolder.tvDiscount.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    userViewHolder.tvDiscountPrice.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                } else {

                    ProductSearchResult.Discount mDiscount = mDataset.get(((UserViewHolder) userViewHolder).getAdapterPosition());
                    if(mDiscount.getDiscountTotal()!=0.0 )
                        mDiscount.setDiscountTotal(value);
                    else {
                        mDiscount.setDiscountTotal(value);
                    }
                    mDataset.set(((UserViewHolder) userViewHolder).getAdapterPosition(), mDiscount);

                    datum.setDiscount(mDataset);
                    IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);

                    ((UserViewHolder) userViewHolder).tvDiscountPrice.setText(" - "+mContext.getResources().getString(R.string.Rs) +mDataset.get(position).getDiscountTotal() + "");

                    userViewHolder.chkDiscount.setChecked(false);

                    userViewHolder.tvDiscount.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    userViewHolder.tvDiscountPrice.setPaintFlags(userViewHolder.tvDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

            }
    }



    private double setOPS(int i, ArrayList<ProductSearchResult.Rule> rule, ProductSearchResult.Datum datum, RecyclerView.ViewHolder holder) {
        Double value =0.0;
        ProductSearchResult.Discount mDiscount = this.mDataset.get(holder.getAdapterPosition());
        if(rule.get(i).getOpsType().equalsIgnoreCase("P"))
        {
            // OPS TYPE if Product
            if(rule.get(i).getPackSize()==0)
            {
                if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("Q"))
                {
                    // Eligibility BasedOn QUANTITY
                    if(this.datum.getQty()>=rule.get(i).getSlabFrom() && this.datum.getQty() <= rule.get(i).getSlabTO() ||this.datum.getQty()>rule.get(i).getSlabTO())
                    {
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getMrp()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getMrp()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getSalesPrice()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getSalesPrice()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getNrv()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getNrv()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getGpl()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getGpl()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }

                    }
                }else  if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("V")){
                    // Eligibility BasedOn VALUE
                    double totalPrice = 0;
                    if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                        totalPrice = this.datum.getMrp()* this.datum.getQty();
                    }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                        totalPrice = this.datum.getSalesPrice()* this.datum.getQty();
                    }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                        totalPrice = this.datum.getNrv()* this.datum.getQty();
                    }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                        totalPrice = this.datum.getGpl()* this.datum.getQty();
                    }

                    double slabFrom = rule.get(i).getSlabFrom();
                    double slabTo = rule.get(i).getSlabTO();
                    if(totalPrice>=slabFrom && totalPrice <= slabTo)
                    {
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }

                    }
                }
            }else if(rule.get(i).getPackSize()>0) {


                // Eligibility BasedOn QUANTITY
//                    if(QuantityCheck>=rule.get(i).getSlabFrom() && QuantityCheck <= rule.get(i).getSlabTO()){
                // Qty in range of SLAB from - SLAB to
                int productCartItem = 0;
                int mFreeOfPackSize = 0;
                int totalQty1 = 0;

                int totalFreePackSize = 0;
                int cartCount = IPOSApplication.mProductListResult.size();
                discountbeforeSorting = new ArrayList<>();
//                if(productCartItem==1){
//                    productCartItem = IPOSApplication.mProductListResult.get(0).getQty();
//                }else {

                if (IPOSApplication.datumSameCode.size() > 0)
                    discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get(this.datum.getProductCode()));
//                for (int l = 0 ; l < discountbeforeSorting.size() ; l++){
                int totalFreeItems = 0;
                ArrayList<ProductSearchResult.Datum> mTotalFreeArr = new ArrayList<>();
                for (int h = 0; h < IPOSApplication.mProductListResult.size(); h++) {

                    if (this.datum.getProductCode().equals(IPOSApplication.mProductListResult.get(h).getProductCode())) {
                        productCartItem++;
                        if(!IPOSApplication.mProductListResult.get(h).isFreeItem()){
                            totalQty1 = totalQty1 + IPOSApplication.mProductListResult.get(h).getQty();
                        }

                        if(this.datum.getIProductModalId().equalsIgnoreCase(IPOSApplication.mProductListResult.get(h).getIProductModalId())) {
                            if (IPOSApplication.mProductListResult.get(h).isFreeItem()) {
                                totalFreeItems++;
                                mTotalFreeArr.add(IPOSApplication.mProductListResult.get(h));
                            }
                        }else {
                            if (IPOSApplication.mProductListResult.get(h).isFreeItem())
                                totalFreeItems++;
                        }
                    }

                }
//                int minusAlreadyFeeItems=totalFreeItems*rule.get(i).getPackSize();
//                totalQty1=totalQty1-minusAlreadyFeeItems;
                AppLog.e(DiscountListAdapter.class.getSimpleName(), "productCartItem: " + productCartItem);
//                }
//                int totalItem =rule.get(i).getSlabFrom() + rule.get(i).getPackSize();
//                }
                if (totalQty1 > 1) {
                    mFreeOfPackSize = totalQty1 / (rule.get(i).getSlabFrom() + rule.get(i).getPackSize());

                    if (mFreeOfPackSize > 0) {
                        totalFreePackSize = mFreeOfPackSize * rule.get(i).getPackSize();
//                        if (mFreeOfPackSize >= IPOSApplication.mProductListResult.get(cartCount).getTotalQty()) {
//                    int QuantityCheck =  this.datum.getQty() /rule.get(i).getPackSize() ;
//                    if(QuantityCheck>=rule.get(i).getSlabFrom() && QuantityCheck <= rule.get(i).getSlabTO())
//                    {
                        // Qty in range of SLAB from - SLAB to
                        if(totalFreePackSize > totalFreeItems) {
                            totalFreeItems = totalFreePackSize-totalFreeItems;
                            if (rule.get(i).getOpsCriteria().equalsIgnoreCase("L")) {
//                            IPOSApplication.minDiscount.clear();
                                value = getLowestValue(rule.get(i), totalFreeItems, holder);

                                mDiscount.setFreeItem(true);
                                isFreeNotApplied = false;
                            } else if (rule.get(i).getOpsCriteria().equalsIgnoreCase("H")) {
                                getHighestValue(rule.get(i));

                                value = getDiscountTypeBaseOn(i) * totalFreeItems;
                                mDiscount.setFreeItem(true);
                                isFreeNotApplied = false;
                            }
                        }else  if(totalFreePackSize < totalFreeItems){
                            totalFreeItems = totalFreeItems-totalFreePackSize;
                            mDiscount.setFreeItem(true);
                            isFreeNotApplied = false;
                            if(mTotalFreeArr.size()>0) {
                                for (int k = 0; k < totalFreeItems; k++) {
                                    if(mTotalFreeArr.get(k).getParentProductID()!=null && !mTotalFreeArr.get(k).getParentProductID().equalsIgnoreCase(""))
                                        if(this.datum.getIProductModalId().equalsIgnoreCase(mTotalFreeArr.get(k).getIProductModalId()))
                                            IPOSApplication.mProductListResult.remove(mTotalFreeArr.get(k));
                                }
                                adapterListener.onRowClicked(-1);
                            }
                        }

                    }else {
                        isFreeNotApplied = true;
                        mDiscount.setFreeItem(false);
                        if(mTotalFreeArr.size()>0){
                            //  for (int k = 0 ; k < mTotalFreeArr.size(); k++){
                            IPOSApplication.mProductListResult.removeAll(mTotalFreeArr);
                            adapterListener.onRowClicked(-1);

                            //  }
                            //   adapterListener.onRowClicked(-1);
                        }

                    }
                }else {
                    isFreeNotApplied = true;
                }
//            }
            }else {
                isFreeNotApplied = true;
            }


        }else if(rule.get(i).getOpsType().equalsIgnoreCase("V")){
            // OPS TYPE if Value
            if(rule.get(i).getPackSize()==0)
            {
                if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("Q")){
                    // Eligibility BasedOn QUANTITY
                    if(this.datum.getQty()>rule.get(i).getSlabFrom() && this.datum.getQty() < rule.get(i).getSlabTO()){
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getMrp()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getMrp()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getSalesPrice()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getSalesPrice()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getNrv()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getNrv()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = ((this.datum.getGpl()*this.datum.getQty())*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = (this.datum.getGpl()*this.datum.getQty())-rule.get(i).getSDiscountValue();
                            }
                        }

                    }else {
                        isFreeNotApplied = true;
                    }
                }else  if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("V"))
                {
                    // Eligibility BasedOn VALUE
                    double totalPrice = IPOSApplication.totalAmount;
                    double slabFrom = rule.get(i).getSlabFrom();
                    double slabTo = rule.get(i).getSlabTO();
                    if(totalPrice>=slabFrom && totalPrice <= slabTo){
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (totalPrice*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = totalPrice-rule.get(i).getSDiscountValue();
                            }
                        }

                    }else {
                        isFreeNotApplied = true;
                    }
                }else {
                    isFreeNotApplied = true;
                }
            }else if(rule.get(i).getPackSize()>0) {

                // Eligibility BasedOn Value
                // Qty in range of SLAB from - SLAB to
                int productCartItem = 0;
                int mFreeOfPackSize = 0;
                int totalQty1 = 0;

                int totalFreePackSize = 0;
                int cartCount = IPOSApplication.mProductListResult.size();
                discountbeforeSorting = new ArrayList<>();

                if (IPOSApplication.datumSameCode.size() > 0)
                    discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get(this.datum.getProductCode()));
                int totalFreeItems = 0;
                ArrayList<ProductSearchResult.Datum> mTotalFreeArr = new ArrayList<>();
                for (int h = 0; h < IPOSApplication.mProductListResult.size(); h++) {

                    if (this.datum.getProductCode().equals(IPOSApplication.mProductListResult.get(h).getProductCode())) {
                        productCartItem++;
                        if(!IPOSApplication.mProductListResult.get(h).isFreeItem()){
                            totalQty1 = totalQty1 + IPOSApplication.mProductListResult.get(h).getQty();
                        }


                        if(IPOSApplication.mProductListResult.get(h).isFreeItem()){
                            totalFreeItems++;
                            mTotalFreeArr.add( IPOSApplication.mProductListResult.get(h));
                        }

                    }

                }
                AppLog.e(DiscountListAdapter.class.getSimpleName(), "productCartItem: " + productCartItem);
//                isFreeNotApplied = true;
                if (totalQty1 > 1) {
                    mFreeOfPackSize = totalQty1 / (rule.get(i).getSlabFrom() + rule.get(i).getPackSize());

                    if (mFreeOfPackSize > 0) {
                        totalFreePackSize = mFreeOfPackSize * rule.get(i).getPackSize();
                        // Qty in range of SLAB from - SLAB to
                        if(totalFreePackSize>totalFreeItems) {
                            totalFreeItems = totalFreePackSize-totalFreeItems;
                            if (rule.get(i).getOpsCriteria().equalsIgnoreCase("L")) {
//                            IPOSApplication.minDiscount.clear();
                                value = getLowestValue(rule.get(i), totalFreeItems, holder);
                                isFreeNotApplied = false;
                                mDiscount.setFreeItem(true);
                            } else if (rule.get(i).getOpsCriteria().equalsIgnoreCase("H")) {
                                getHighestValue(rule.get(i));
                                isFreeNotApplied = false;
                                value = getDiscountTypeBaseOn(i) * totalFreeItems;
                                mDiscount.setFreeItem(true);
                            }
                        }else {
                            isFreeNotApplied = false;
                            mDiscount.setFreeItem(true);
                            if(mTotalFreeArr.size()>0) {
                                for (int k = 0; k < totalFreePackSize; k++) {
                                    IPOSApplication.mProductListResult.remove(mTotalFreeArr.get(k));


                                }
                                adapterListener.onRowClicked(-1);
                            }
                        }

                    }else {
                        isFreeNotApplied = true;
                        mDiscount.setFreeItem(false);
                        if(mTotalFreeArr.size()>0){
                            //  for (int k = 0 ; k < mTotalFreeArr.size(); k++){
                            IPOSApplication.mProductListResult.removeAll(mTotalFreeArr);
                            adapterListener.onRowClicked(-1);

                            //  }
                            //   adapterListener.onRowClicked(-1);
                        }

                    }
                }else {
                    isFreeNotApplied = true;
                }
            }



        }else {
            isFreeNotApplied = true;
        }

        if(!isFreeNotApplied) {
            mDiscount.setDiscountTotal(value);
            this.mDataset.set(holder.getAdapterPosition(), mDiscount);
            ProductSearchResult.Rule mRule = rule.get(i);
            mRule.setApplied(true);
            this.rule.set(i, mRule);
            mDiscount.setRule(rule);
            mDataset.set(((UserViewHolder) holder).getAdapterPosition(), mDiscount);
//        datum.setDiscount(mDataset);
//        IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);

       /* int quantityCheck=this.datum.getQty();
        if (rule.get(i).getPackSize()>0) {
            if (quantityCheck >= rule.get(i).getSlabFrom() && quantityCheck <= rule.get(i).getSlabTO() || this.datum.getQty() > rule.get(i).getSlabTO()) {
                this.datum.setQty((quantityCheck * rule.get(i).getPackSize())-1);
            }
        }*/
            this.datum.setDiscount(this.mDataset);
            IPOSApplication.mProductListResult.set(retailAdapterPosition, this.datum);
        }
        return value;
    }

    boolean isFreeNotApplied=false;
    private double getDiscountType(double lowestDiscountValue,int i) {
        double value=0.0;
        if (rule.get(i).getSDiscountType().equalsIgnoreCase("P")) {
            value = (lowestDiscountValue * rule.get(i).getSDiscountValue()) / 100;
        } else if (rule.get(i).getSDiscountType().equalsIgnoreCase("V")) {
            value = lowestDiscountValue - rule.get(i).getSDiscountValue();
        }
        return value;
    }
    private Double getDiscountTypeBaseOn(int i) {
        double value=0.0;
        double iPriceValue = 0.0;
        switch (rule.get(i).getSDiscountBasedOn())
        {
            case "MRP":
                iPriceValue =  datum.getMrp() * datum.getQty();
                break;
            case "GPL":
                iPriceValue =  datum.getGpl() * datum.getQty();
                break;
            case "NRV":
                iPriceValue =  datum.getNrv() * datum.getQty();
                break;
            case "SP":
                iPriceValue =  datum.getSalesPrice() * datum.getQty();
                break;
        }
        value = getDiscountType(iPriceValue,i);
//        value = (iPriceValue * rule.get(i).getSDiscountValue()) / 100;

//        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")) {
//            if (rule.get(i).getSDiscountType().equalsIgnoreCase("P")) {
//                value = (datum.getMrp() * rule.get(i).getSDiscountValue()) / 100;
//            } else if (rule.get(i).getSDiscountType().equalsIgnoreCase("V")) {
//                value = datum.getMrp() - rule.get(i).getSDiscountValue();
//            }
//        }else  if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")) {
//            if (rule.get(i).getSDiscountType().equalsIgnoreCase("P")) {
//                value = (datum.getNrv() * rule.get(i).getSDiscountValue()) / 100;
//            } else if (rule.get(i).getSDiscountType().equalsIgnoreCase("V")) {
//                value = datum.getNrv() - rule.get(i).getSDiscountValue();
//            }
//        }else  if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")) {
//            if (rule.get(i).getSDiscountType().equalsIgnoreCase("P")) {
//                value = (datum.getSalesPrice() * rule.get(i).getSDiscountValue()) / 100;
//            } else if (rule.get(i).getSDiscountType().equalsIgnoreCase("V")) {
//                value = datum.getSalesPrice() - rule.get(i).getSDiscountValue();
//            }
//        }else  if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")) {
//            if (rule.get(i).getSDiscountType().equalsIgnoreCase("P")) {
//                value = (datum.getGpl() * rule.get(i).getSDiscountValue()) / 100;
//            } else if (rule.get(i).getSDiscountType().equalsIgnoreCase("V")) {
//                value = datum.getGpl() - rule.get(i).getSDiscountValue();
//            }
//        }
        return value;
    }

    double lowestDiscountValue=0.0;
    double highestDiscountValue=0.0;
    ArrayList<ProductSearchResult.Datum> discountbeforeSorting = new ArrayList<>();

    private void getHighestValue(final ProductSearchResult.Rule rule) {
        discountbeforeSorting = new ArrayList<>();
        discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get(datum.getProductCode()));
        for (int k=0;k<IPOSApplication.mProductListResult.size();k++){
            for (int m=0;m<discountbeforeSorting.size();m++){

                if (!discountbeforeSorting.get(m).getIProductModalId().equalsIgnoreCase(IPOSApplication. mProductListResult.get(k).getIProductModalId())){
                    discountbeforeSorting.remove(m);
                    m--;
                }
            }

        }
        Collections.sort(discountbeforeSorting, new Comparator<ProductSearchResult.Datum>() {
            @Override
            public int compare(ProductSearchResult.Datum lhs, ProductSearchResult.Datum rhs) {
                int valueSort = 0;
                if (rule.getSDiscountBasedOn().equalsIgnoreCase("MRP")) {
                    valueSort = Double.valueOf(rhs.getMrp()).compareTo(lhs.getMrp());
                    highestDiscountValue = discountbeforeSorting.get(0).getMrp();
                } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("NRV")) {
                    valueSort = Double.valueOf(rhs.getNrv()).compareTo(lhs.getNrv());
                    highestDiscountValue = discountbeforeSorting.get(0).getNrv();
                } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("SP")) {
                    valueSort = Double.valueOf(rhs.getSalesPrice()).compareTo(lhs.getSalesPrice());
                    highestDiscountValue = discountbeforeSorting.get(0).getSalesPrice();
                } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("GPL")) {
                    valueSort = Double.valueOf(rhs.getGpl()).compareTo(lhs.getGpl());
                    highestDiscountValue = discountbeforeSorting.get(0).getGpl();
                }
                return valueSort;
            }
        });
    }

    private void getOPSLowestValue(final String discountBasedOn) {
        discountbeforeSorting = new ArrayList<>();
        discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get(datum.getProductCode()));
        ArrayList<ProductSearchResult.Datum> data=new ArrayList<>();
        for (int k=0;k<IPOSApplication.mProductListResult.size();k++){
            for (int m=0;m<discountbeforeSorting.size();m++){

                if (discountbeforeSorting.get(m).getIProductModalId().equalsIgnoreCase(IPOSApplication. mProductListResult.get(k).getIProductModalId())){
                    data.add(IPOSApplication.mProductListResult.get(k));
                }
            }

        }
        discountbeforeSorting = new ArrayList<>();
        discountbeforeSorting.addAll(data);
        final int[] valueSort = {0};
        Collections.sort(discountbeforeSorting, new Comparator<ProductSearchResult.Datum>() {
            @Override
            public int compare(ProductSearchResult.Datum lhs, ProductSearchResult.Datum rhs) {

                if (discountBasedOn.equalsIgnoreCase("MRP")) {
                    valueSort[0] = Double.valueOf(lhs.getMrp()).compareTo(rhs.getMrp());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getMrp();
                    //  getLowestArray(totalFreePackSize,"MRP");
                } else if (discountBasedOn.equalsIgnoreCase("NRV")) {
                    valueSort[0] = Double.valueOf(lhs.getNrv()).compareTo(rhs.getNrv());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getNrv();
                    //  getLowestArray(totalFreePackSize,"NRV");
                } else if (discountBasedOn.equalsIgnoreCase("SP")) {
                    valueSort[0] = Double.valueOf(lhs.getSalesPrice()).compareTo(rhs.getSalesPrice());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getSalesPrice();
                    //  getLowestArray(totalFreePackSize,"SP");
                } else if (discountBasedOn.equalsIgnoreCase("GPL")) {
                    valueSort[0] = Double.valueOf(lhs.getGpl()).compareTo(rhs.getGpl());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getGpl();
                    // getLowestArray(totalFreePackSize,"GPL");
                }
                return valueSort[0];
            }
        });
        //  return valueSort[0];
    }

    private double getLowestValue(final ProductSearchResult.Rule rule, final int totalFreePackSize, final RecyclerView.ViewHolder holder) {
        final double[] value = {0.0};

        discountbeforeSorting = new ArrayList<>();
        discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get(datum.getProductCode()));
        ArrayList<ProductSearchResult.Datum> data = new ArrayList<>();
        for (int k = 0; k < IPOSApplication.mProductListResult.size(); k++) {
            for (int m = 0; m < discountbeforeSorting.size(); m++) {

                if (discountbeforeSorting.get(m).getIProductModalId().equalsIgnoreCase(IPOSApplication.mProductListResult.get(k).getIProductModalId())) {
                    data.add(IPOSApplication.mProductListResult.get(k));
                }
            }

        }
        discountbeforeSorting = new ArrayList<>();
        discountbeforeSorting.addAll(data);

        if (discountbeforeSorting.size() == 1) {
            switch (rule.getSDiscountBasedOn()) {
                case "MRP":
                    return    getLowestArray(totalFreePackSize, datum.getMrp(),holder);
//                    break;

                case "NRV":
                    return getLowestArray(totalFreePackSize, datum.getNrv(), holder);
//                    break;

                case "SP":
                    return getLowestArray(totalFreePackSize, datum.getSalesPrice(), holder);
//                    break;

                case "GPL":
                    return  getLowestArray(totalFreePackSize, datum.getGpl(), holder);
//                    break;
            }
        }else{
            int totalFree=totalFreePackSize;
            Collections.sort(discountbeforeSorting, new Comparator<ProductSearchResult.Datum>() {
                @Override
                public int compare(ProductSearchResult.Datum lhs, ProductSearchResult.Datum rhs) {
                    int valueSort = 0;

                    if (rule.getSDiscountBasedOn().equalsIgnoreCase("MRP")) {
                        valueSort = Double.valueOf(lhs.getMrp()).compareTo(rhs.getMrp());
                        value[0] = datum.getMrp();
                    } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("NRV")) {
                        valueSort = Double.valueOf(lhs.getNrv()).compareTo(rhs.getNrv());
                        value[0] = datum.getNrv();
                    } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("SP")) {
                        valueSort = Double.valueOf(lhs.getSalesPrice()).compareTo(rhs.getSalesPrice());
                        value[0] = datum.getSalesPrice();
                    } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("GPL")) {
                        valueSort = Double.valueOf(lhs.getGpl()).compareTo(rhs.getGpl());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getGpl();
                        value[0] = datum.getGpl();
                    }

                    return valueSort;
                }
            });
            value[0] = getLowestArray(totalFree, datum.getMrp(), holder);
            totalFree=0;
        }
        return value[0];
    }

//    private void getFirstIndexAfterSorting(String mPriceSelected) {
//        if (discountbeforeSorting.size()>0){
//            discountbeforeSorting.get(0).getMrp();
//        }
//        for (int f = 0 ; f < discountbeforeSorting.size() ; f++){
//            ProductSearchResult.Datum datum = discountbeforeSorting.get(f);
//            datum.setPriceSelected(mPriceSelected);
//            minDiscount.add(datum);
//        }
//
//    }

    private double getLowestArray(int totalFreePackSize, double mPriceSelected, RecyclerView.ViewHolder holder) {
//        ArrayList<ProductSearchResult.Datum> data = new ArrayList<>();
//        for (int k = 0; k < IPOSApplication.mProductListResult.size(); k++) {
//            for (int m = 0; m < discountbeforeSorting.size(); m++) {
//
//                if (discountbeforeSorting.get(m).getIProductModalId().equalsIgnoreCase(IPOSApplication.mProductListResult.get(k).getIProductModalId())) {
//                    data.add(IPOSApplication.mProductListResult.get(k));
//                }
//            }
//
//        }
//        discountbeforeSorting = new ArrayList<>();
//        discountbeforeSorting.addAll(data);
        int total=totalFreePackSize;
        ArrayList<ProductSearchResult.Discount> discounts = new ArrayList<>();
        ArrayList<ProductSearchResult.Rule> rules = new ArrayList<>();
        if (discountbeforeSorting.size()==1){
            for(int k = 0 ; k <total; k++) {
                ProductSearchResult.Datum datum1 = discountbeforeSorting.get(0);
                minDiscount.add(datum1);
//                break;
            }
        }else {
            for (int i=0;i<discountbeforeSorting.size();i++){
                int qtyType =discountbeforeSorting.get(i).getQty();
                if (qtyType>=total){

//                if (total==i+1){
//                    total=0;
//                    break;
//                }
//                  if (i<=total-1){
                    for(int k = 0 ; k <total; k++) {
                        ProductSearchResult.Datum datum1 = discountbeforeSorting.get(i);
                        minDiscount.add(datum1);
                        break;
//                break;
                    }
//                total--;
                }else {
                    if (qtyType<total){
//                    for(int k = 0 ; k <=total; k++) {
                        for(int k = 0 ; k <total; k++) {
                            ProductSearchResult.Datum datum1 = discountbeforeSorting.get(i);
                            minDiscount.add(datum1);
                            break;
//                break;
                        }

                    }
                }
            }
        }

//        adapterListener.onRowClicked(-1);
//        if(discountbeforeSorting.size() > totalFreePackSize) {
//            for (int f = 0; f < totalFreePackSize; f++) {
////                ProductSearchResult.Datum datum = discountbeforeSorting.get(f);
////                datum.setPriceSelected(mPriceSelected);
//
//                minDiscount.add(discountbeforeSorting.get(f));
//            }
//        }else {
//            for (int f = 0; f < totalFreePackSize; f++) {
////                ProductSearchResult.Datum datum = discountbeforeSorting.get(0);
////                datum.setPriceSelected(mPriceSelected);
//                minDiscount.add(discountbeforeSorting.get(0));
//            }
//
//
//        }

//        ProductSearchResult.Discount discount = new ProductSearchResult().new Discount();


        if(minDiscount.size()>0) {
            for (int i = 0; i < minDiscount.size(); i++) {
                discounts.clear();
                rules.clear();
                ProductSearchResult.Discount discount = new ProductSearchResult().new Discount();
                discount.setFreeItem(true);
                discount.setSDiscountName(mDataset.get(holder.getAdapterPosition()).getSDiscountName());

                discount.setDiscountTotal(mPriceSelected);
                discount.setRule(rules);
                discount.setParentID(minDiscount.get(i).getIProductModalId());
                discounts.add(discount);
                ProductSearchResult.Datum datum1 =new ProductSearchResult().new Datum();
                //    AppLog.e("IPOSApplication.mProductListResult: minDiscount 1-- ",Util.getCustomGson().toJson(minDiscount.get(i)));
                //  AppLog.e("IPOSApplication.mProductListResult: datum 2--",Util.getCustomGson().toJson(datum));
                datum1.setSProductName(minDiscount.get(i).getSProductName());
                datum1.setParentProductID(minDiscount.get(i).getIProductModalId());
                datum1.setBarCodeNumber(minDiscount.get(i).getBarCodeNumber());
                datum1.setCgst(minDiscount.get(i).getCgst());
                datum1.setSgst(minDiscount.get(i).getSgst());
                datum1.setGstPerc(minDiscount.get(i).getGstPerc());
                datum1.setMrp(minDiscount.get(i).getMrp());
                datum1.setIProductModalId(minDiscount.get(i).getIProductModalId());
                datum1.setGpl(minDiscount.get(i).getGpl());
                datum1.setNrv(minDiscount.get(i).getNrv());
                datum1.setSalesPrice(minDiscount.get(i).getSalesPrice());
                datum1.setSProductWeight(minDiscount.get(i).getSProductWeight());
                datum1.setSProductStock(minDiscount.get(i).getSProductStock());
                datum1.setIsDiscount(minDiscount.get(i).getIsDiscount());
                datum1.setProductImage(minDiscount.get(i).getProductImage());
                datum1.setProductCode(minDiscount.get(i).getProductCode());
                datum1.setGstPerc(minDiscount.get(i).getGstPerc());
                datum1.setGstPerc(minDiscount.get(i).getGstPerc());
                datum1.setGstPerc(minDiscount.get(i).getGstPerc());
                datum1.setGstPerc(minDiscount.get(i).getGstPerc());
                datum1.setDiscount(discounts);
                datum1.setQty(1);
                datum1.setSalesPrice(mPriceSelected);
                datum1.setFreeItem(true);
                datum1.setSProductPrice(mPriceSelected);

                minDiscount.set(i,datum1);
//                IPOSApplication.mProductListResult.add( datum1);
            }
            IPOSApplication.mProductListResult.addAll(minDiscount);
//            SharedPrefUtil.putString(Constants.DISCOUNT+"",Util.getCustomGson().toJson(minDiscount),mContext);
//            notifyDataSetChanged();

        }
        minDiscount.clear();
        return 0.0;
    }

    private void getLowestValue(final ProductSearchResult.Rule rule) {
        discountbeforeSorting = new ArrayList<>();
        discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
        Collections.sort(discountbeforeSorting, new Comparator<ProductSearchResult.Datum>()
        {
            @Override
            public int compare(ProductSearchResult.Datum lhs, ProductSearchResult.Datum rhs) {
                int valueSort=0;
                if(rule.getSDiscountBasedOn().equalsIgnoreCase("MRP")) {
                    valueSort = Double.valueOf(lhs.getMrp()).compareTo(rhs.getMrp());
                    lowestDiscountValue = discountbeforeSorting.get(0).getMrp();
                }else if(rule.getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                    valueSort= Double.valueOf(lhs.getNrv()).compareTo(rhs.getNrv());
                    lowestDiscountValue = discountbeforeSorting.get(0).getNrv();
                }else if(rule.getSDiscountBasedOn().equalsIgnoreCase("SP")){
                    valueSort= Double.valueOf(lhs.getSalesPrice()).compareTo(rhs.getSalesPrice());
                    lowestDiscountValue = discountbeforeSorting.get(0).getSalesPrice();
                }else if(rule.getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                    valueSort= Double.valueOf(lhs.getGpl()).compareTo(rhs.getGpl());
                    lowestDiscountValue = discountbeforeSorting.get(0).getGpl();
                }
                return valueSort;
            }
        });



//        for (int i = 0 ; i < IPOSApplication.mProductListResult.size(); i ++){
//            for (int j = 0 ; j < IPOSApplication.datumSameCode.size();j++){
//                if(datum.getProductCode().equalsIgnoreCase(IPOSApplication.datumSameCode.get( datum.getProductCode()).get(j).getProductCode())){

//                    Collections.sort(discountbeforeSorting, new Comparator< ProductSearchResult.Datum >() {
//                        @Override public int compare(ProductSearchResult.Datum  p1, ProductSearchResult.Datum  p2) {
//                            int p=0;
//                            if(rule.getSDiscountBasedOn().equalsIgnoreCase("MRP")) {
//                                p= p2.getMrp() - p1.getMrp(); // Ascending
//                                discountbeforeSorting.sort((p1.getMrp(),  p2.getMrp()) -> Double.compare(p2.getMrp(), p1.getMrp()));
//                            }else if(rule.getSDiscountBasedOn().equalsIgnoreCase("NRV")){
//
//                            }
//return p;
//
//                        }
//                    });

//                Collections.sort(discountbeforeSorting, new Comparator() {
//                    @Override
//                    public int compare(Object o1, Object o2) {
//                        ProductSearchResult.Datum p1 = (ProductSearchResult.Datum) o1;
//                        ProductSearchResult.Datum p2 = (ProductSearchResult.Datum) o2;
//                        return p1.getMrp().compareToIgnoreCase(p2.getMrp());
//                    }
//                });



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