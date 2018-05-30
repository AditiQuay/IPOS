package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.AppLog;
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
    static Context mContext;
    ProductSearchResult.Datum datum;
    ArrayList<ProductSearchResult.Discount> mDataset;
    private ArrayList<ProductSearchResult.Rule> rule = new            ArrayList<>();
    RecyclerView mRecyclerView;
    DatabaseHandler databaseHandler;
    int retailAdapterPosition;

    public DiscountListAdapter(Context ctx, RecyclerView mRecycler,
                               ArrayList<ProductSearchResult.Discount> questionList, ProductSearchResult.Datum str, int adapterPosition,AdapterListener adapterListener) {
        retailAdapterPosition = adapterPosition;
        mContext = ctx;
        mDataset = questionList;
        mRecyclerView = mRecycler;
        datum = str;
        databaseHandler = new DatabaseHandler(ctx);
        this.adapterListener=adapterListener;


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

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DiscountListAdapter.UserViewHolder) {
            onBind = true;
            ProductSearchResult.Discount str = mDataset.get(position);
            AppLog.e(DiscountListAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            final DiscountListAdapter.UserViewHolder userViewHolder = (DiscountListAdapter.UserViewHolder) holder;


            if(str.getRule()!=null && str.getRule().size()>0){
                rule = str.getRule();
                double value=0.0;
              //  for (int i = 0 ; i < rule.size() ; i++){
//                    getLowestValue(rule.get(i));

                        userViewHolder.tvDiscount.setText(str.getSDiscountName());

                        if (((UserViewHolder) holder).chkDiscount.isChecked()){

                            for (int i = 0 ; i < rule.size() ; i++) {
                                if(rule.get(i).getRuleType().equalsIgnoreCase("I")) {
                                    value = value + setOPS(i, rule, datum, mDataset,holder);
                                }else {
                                    if(IPOSApplication.isClicked){
                                        int predecessor=rule.get(i).getRuleProdecessors();
                                        for (int k=0;k<rule.size();k++){
                                            if (predecessor==rule.get(k).getRuleID()){
                                                if(rule.get(k).getRuleType().equalsIgnoreCase("I")) {
                                                    value = value + setOPS(k, rule, datum, mDataset,holder);
                                                }else {
                                                    if(IPOSApplication.isClicked){

                                                        if(datum.getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getProductCode())){
//                                discountbeforeSorting = new ArrayList<>();
//                                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                                                            //   userViewHolder.tvDiscount.setText(str.getSDiscountName());
                                                            //    value =setOPS(i,rule,datum,mDataset);
                                                            //    userViewHolder.tvDiscountPrice.setText(value+"");
                                                            value = value + setOPS(i, rule, datum, mDataset, holder);
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        if(datum.getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getProductCode())){
//                                discountbeforeSorting = new ArrayList<>();
//                                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                                            //   userViewHolder.tvDiscount.setText(str.getSDiscountName());
                                            //    value =setOPS(i,rule,datum,mDataset);
                                            //    userViewHolder.tvDiscountPrice.setText(value+"");
                                            value = value + setOPS(i, rule, datum, mDataset, holder);
                                        }
                                    }
                                }
                            }
                            ProductSearchResult.Discount mDiscount = mDataset.get(((UserViewHolder) holder).getAdapterPosition());
                            mDiscount.setDiscountTotal(value);
                            mDataset.set(((UserViewHolder) holder).getAdapterPosition(),mDiscount);

                            datum.setDiscount(mDataset);
                            IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);
                            ((UserViewHolder) holder).tvDiscountPrice.setText(value+"");
                        }else {
                            ProductSearchResult.Discount mDiscount = mDataset.get(((UserViewHolder) holder).getAdapterPosition());
                            mDiscount.setDiscountTotal(0.00);
                            mDataset.set(((UserViewHolder) holder).getAdapterPosition(),mDiscount);

                            datum.setDiscount(mDataset);
                            IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);
                            ((UserViewHolder) holder).tvDiscountPrice.setText(0.00+"");
                        }
                      /*  for (int i = 0 ; i < rule.size() ; i++) {
                            if(rule.get(i).getRuleType().equalsIgnoreCase("I")) {
                                value = value + setOPS(i, rule, datum, mDataset,holder);
                            }else {
                                if(IPOSApplication.isClicked){
                                    int predecessor=rule.get(i).getRuleProdecessors();
                                    for (int k=0;k<rule.size();k++){
                                       if (predecessor==rule.get(k).getRuleID()){
                                           if(rule.get(k).getRuleType().equalsIgnoreCase("I")) {
                                               value = value + setOPS(k, rule, datum, mDataset,holder);
                                           }else {
                                               if(IPOSApplication.isClicked){

                                                   if(datum.getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getProductCode())){
//                                discountbeforeSorting = new ArrayList<>();
//                                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                                                       //   userViewHolder.tvDiscount.setText(str.getSDiscountName());
                                                       //    value =setOPS(i,rule,datum,mDataset);
                                                       //    userViewHolder.tvDiscountPrice.setText(value+"");
                                                       value = value + setOPS(i, rule, datum, mDataset, holder);
                                                   }
                                               }
                                           }

                                       }
                                    }
                                    if(datum.getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getProductCode())){
//                                discountbeforeSorting = new ArrayList<>();
//                                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                                        //   userViewHolder.tvDiscount.setText(str.getSDiscountName());
                                        //    value =setOPS(i,rule,datum,mDataset);
                                        //    userViewHolder.tvDiscountPrice.setText(value+"");
                                        value = value + setOPS(i, rule, datum, mDataset, holder);
                                    }
                                }
                            }
                        }*/
                onBind = false;
                        ((UserViewHolder) holder).chkDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (!onBind) {
                                    if (b) {
                                        double value = 0.00;
                                        ArrayList<ProductSearchResult.Rule> rule=mDataset.get(position).getRule();
                                        for (int i = 0; i < mDataset.get(position).getRule().size(); i++) {
                                            if (mDataset.get(position).getRule().get(i).getRuleType().equalsIgnoreCase("I")) {
                                                value = value + setOPS(i, mDataset.get(position).getRule(), datum, mDataset, userViewHolder);
                                            } else {
                                                if (IPOSApplication.isClicked) {
                                                    int predecessor = rule.get(i).getRuleProdecessors();
                                                    for (int k = 0; k < rule.size(); k++) {
                                                        if (predecessor == rule.get(k).getRuleID()) {
                                                            if (rule.get(k).getRuleType().equalsIgnoreCase("I")) {
                                                                value = value + setOPS(k, rule, datum, mDataset, userViewHolder);
                                                            } else {
                                                                if (IPOSApplication.isClicked) {

                                                                    if (datum.getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getProductCode())) {
//                                discountbeforeSorting = new ArrayList<>();
//                                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                                                                        //   userViewHolder.tvDiscount.setText(str.getSDiscountName());
                                                                        //    value =setOPS(i,rule,datum,mDataset);
                                                                        //    userViewHolder.tvDiscountPrice.setText(value+"");
                                                                        value = value + setOPS(i, rule, datum, mDataset, holder);
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                    if (datum.getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getProductCode())) {
//                                discountbeforeSorting = new ArrayList<>();
//                                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                                                        //   userViewHolder.tvDiscount.setText(str.getSDiscountName());
                                                        //    value =setOPS(i,rule,datum,mDataset);
                                                        //    userViewHolder.tvDiscountPrice.setText(value+"");
                                                        value = value + setOPS(i, rule, datum, mDataset, userViewHolder);
                                                    }
                                                }
                                            }
                                        }
                                        ProductSearchResult.Discount mDiscount = mDataset.get(((UserViewHolder) holder).getAdapterPosition());
                                        mDiscount.setDiscountTotal(value);
                                        mDataset.set(((UserViewHolder) holder).getAdapterPosition(), mDiscount);

                                        datum.setDiscount(mDataset);
                                        IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);
                                        ((UserViewHolder) holder).tvDiscountPrice.setText(value + "");
                                    } else {
                                        ProductSearchResult.Discount mDiscount = mDataset.get(((UserViewHolder) holder).getAdapterPosition());
                                        mDiscount.setDiscountTotal(0.00);
                                        mDataset.set(((UserViewHolder) holder).getAdapterPosition(), mDiscount);

                                        datum.setDiscount(mDataset);
                                        IPOSApplication.mProductListResult.set(retailAdapterPosition, datum);
                                        ((UserViewHolder) holder).tvDiscountPrice.setText(0.00 + "");
                                    }

                                    adapterListener.onRowClicked(((UserViewHolder) holder).getAdapterPosition());
                                }
                            }
                        });

                        if (position==mDataset.size()-1)
                        adapterListener.onRowClicked(position);
                    /*else {
                        if(IPOSApplication.isClicked){
                            if(datum.getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getProductCode())){
//                                discountbeforeSorting = new ArrayList<>();
//                                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                             //   userViewHolder.tvDiscount.setText(str.getSDiscountName());
                            //    value =setOPS(i,rule,datum,mDataset);
                            //    userViewHolder.tvDiscountPrice.setText(value+"");

                            }
                        }
                    }*/
             //   }
                if(value==0.0){
                    userViewHolder.llDiscount.setVisibility(View.GONE);
                }else {
                    userViewHolder.llDiscount.setVisibility(View.VISIBLE);
                }
            }



        }
        else if (holder instanceof DiscountListAdapter.LoadingViewHolder) {
            DiscountListAdapter.LoadingViewHolder loadingViewHolder = (DiscountListAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true); }

    }

    private double setOPS(int i, ArrayList<ProductSearchResult.Rule> rule, ProductSearchResult.Datum datum, ArrayList<ProductSearchResult.Discount> mDataset, RecyclerView.ViewHolder holder) {
        Double value =0.0;
        if(rule.get(i).getOpsType().equalsIgnoreCase("P"))
        {
            // OPS TYPE if Product
            if(rule.get(i).getPackSize()==0)
            {
                if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("Q")){
                    // Eligibility BasedOn QUANTITY
                    if(this.datum.getQty()>=rule.get(i).getSlabFrom() && this.datum.getQty() <= rule.get(i).getSlabTO() ||this.datum.getQty()>rule.get(i).getSlabTO()){
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getMrp()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getNrv()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getGpl()-rule.get(i).getSDiscountValue();
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
                    if(totalPrice>=slabFrom && totalPrice <= slabTo){
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getMrp()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getNrv()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getGpl()-rule.get(i).getSDiscountValue();
                            }
                        }

                    }
                }
            }else if(rule.get(i).getPackSize()>0) {
               int quantityCheck=this.datum.getQty();
                if(quantityCheck>=rule.get(i).getSlabFrom() && quantityCheck <= rule.get(i).getSlabTO()||this.datum.getQty()>rule.get(i).getSlabTO()){
                    String discountBasedOn=rule.get(i).getSDiscountBasedOn();


                    discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( this.datum.getProductCode()));
                    if (rule.get(i).getOpsCriteria().equalsIgnoreCase("L")) {
                        getOPSLowestValue(discountBasedOn);
                        int qty=0;
                        for (int k=0;k<discountbeforeSorting.size();k++){
                            qty=qty+discountbeforeSorting.get(k).getQty();
                        }
                        double lowestValue=0.0;
                        if (discountBasedOn.equalsIgnoreCase("MRP")) {

                            for (int p=0;p<discountbeforeSorting.size();p++) {

                                if (p < rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();
                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getMrp(), i)*totalQty;
                                  //  lowestValue = lowestValue + discountbeforeSorting.get(p).getMrp();
                                }
                            }



                        } else if (discountBasedOn.equalsIgnoreCase("NRV")) {
                            for (int p=0;p<discountbeforeSorting.size();p++) {
                                if (p < rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();
                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getNrv(), i)*totalQty;
                           //         lowestValue = getDiscountType(lowestValue, i);
                                 //   lowestValue = lowestValue + discountbeforeSorting.get(p).getNrv();
                                }
                            }
                       //     lowestValue = discountbeforeSorting.get(0).getNrv();
                        } else if (discountBasedOn.equalsIgnoreCase("SP")) {
                            for (int p=0;p<discountbeforeSorting.size();p++) {
                                if (p < rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();
                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getSalesPrice(), i)*totalQty;
                                 //   lowestValue = getDiscountType(lowestValue, i);
                                 //   lowestValue = lowestValue + discountbeforeSorting.get(p).getSalesPrice();
                                }
                            }
                          //  lowestValue = discountbeforeSorting.get(0).getSalesPrice();
                        } else if (discountBasedOn.equalsIgnoreCase("GPL")) {
                            for (int p=0;p<discountbeforeSorting.size();p++){
                                if (p<rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();

                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getGpl(), i)*totalQty;
                                   // lowestValue = getDiscountType(lowestValue, i);
                                  //  lowestValue = lowestValue + discountbeforeSorting.get(p).getGpl();
                                }
                            }
                         //   lowestValue = discountbeforeSorting.get(0).getGpl();
                        }
                        int QtyPer=qty/rule.get(i).getPackSize();
                        value=lowestValue*QtyPer;
                     //   value=lowestValue+totalQty;

                    }else {
                        getHighestValue(rule.get(i));
                        double lowestValue=0.0;
                        int qty=0;
                        for (int k=0;k<discountbeforeSorting.size();k++){
                            qty=qty+discountbeforeSorting.get(k).getQty();
                        }
                        if (discountBasedOn.equalsIgnoreCase("MRP")) {

                            for (int p=0;p<discountbeforeSorting.size();p++) {
                                if (p < rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();
                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getMrp(), i)*totalQty;
                                    //  lowestValue = lowestValue + discountbeforeSorting.get(p).getMrp();
                                }
                            }


                        } else if (discountBasedOn.equalsIgnoreCase("NRV")) {
                            for (int p=0;p<discountbeforeSorting.size();p++) {
                                if (p < rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();
                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getNrv(), i)*totalQty;
                                    //         lowestValue = getDiscountType(lowestValue, i);
                                    //   lowestValue = lowestValue + discountbeforeSorting.get(p).getNrv();
                                }
                            }
                            //     lowestValue = discountbeforeSorting.get(0).getNrv();
                        } else if (discountBasedOn.equalsIgnoreCase("SP")) {
                            for (int p=0;p<discountbeforeSorting.size();p++) {
                                if (p < rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();
                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getSalesPrice(), i)*totalQty;
                                    //   lowestValue = getDiscountType(lowestValue, i);
                                    //   lowestValue = lowestValue + discountbeforeSorting.get(p).getSalesPrice();
                                }
                            }
                            //  lowestValue = discountbeforeSorting.get(0).getSalesPrice();
                        } else if (discountBasedOn.equalsIgnoreCase("GPL")) {
                            for (int p=0;p<discountbeforeSorting.size();p++){
                                if (p<rule.get(i).getPackSize()) {
                                    int totalQty=rule.get(i).getPackSize();

                                    lowestValue = lowestValue+getDiscountType(discountbeforeSorting.get(p).getGpl(), i)*totalQty;
                                    // lowestValue = getDiscountType(lowestValue, i);
                                    //  lowestValue = lowestValue + discountbeforeSorting.get(p).getGpl();
                                }
                            }
                            //   lowestValue = discountbeforeSorting.get(0).getGpl();
                        }
                        int QtyPer=qty/rule.get(i).getPackSize();
                        value=lowestValue*QtyPer;
                     //   value=getDiscountType(lowestValue,i);
                    }

                }
/*



//                if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("Q")){
                // Eligibility BasedOn QUANTITY
//                    if(QuantityCheck>=rule.get(i).getSlabFrom() && QuantityCheck <= rule.get(i).getSlabTO()){
                // Qty in range of SLAB from - SLAB to
                int productCartItem = 0;
                int mFreeOfPackSize =0 ;
                int totalQty =0 ;
                int totalFreePackSize =0 ;
                int cartCount = IPOSApplication.mProductListResult.size();
                discountbeforeSorting = new ArrayList<>();
//                if(productCartItem==1){
//                    productCartItem = IPOSApplication.mProductListResult.get(0).getQty();
//                }else {

                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( this.datum.getProductCode()));
//                for (int l = 0 ; l < discountbeforeSorting.size() ; l++){
                for (int h = 0 ; h < IPOSApplication.mProductListResult.size(); h++){

                        if(datum.getProductCode().equals(IPOSApplication.mProductListResult.get(h).getProductCode())) {
                            productCartItem++;
                            totalQty = totalQty+datum.getQty();

                        }

                }
                AppLog.e(DiscountListAdapter.class.getSimpleName(),"productCartItem: " + productCartItem);
//                }
//                int totalItem =rule.get(i).getSlabFrom() + rule.get(i).getPackSize();
//                }
                if(totalQty >= 1) {
                    mFreeOfPackSize = totalQty / ( rule.get(i).getPackSize());

                    if(mFreeOfPackSize>0) {
                        totalFreePackSize = mFreeOfPackSize*rule.get(i).getPackSize();
//                        if (mFreeOfPackSize >= IPOSApplication.mProductListResult.get(cartCount).getTotalQty()) {
//                    int QuantityCheck =  this.datum.getQty() /rule.get(i).getPackSize() ;
//                    if(QuantityCheck>=rule.get(i).getSlabFrom() && QuantityCheck <= rule.get(i).getSlabTO())
//                    {
                        // Qty in range of SLAB from - SLAB to
                        if (rule.get(i).getOpsCriteria().equalsIgnoreCase("L")) {
//                            IPOSApplication.minDiscount.clear();
                           // getLowestValue(rule.get(i),totalFreePackSize);


                            for(int p = 0 ; p < IPOSApplication.minDiscount.size(); p++){
                                if(IPOSApplication.minDiscount.get(i).getIProductModalId().equalsIgnoreCase(datum.getIProductModalId())){
                                    value = getDiscountTypeBaseOn( i) ;
                                }
                            }



                        } else if (rule.get(i).getOpsCriteria().equalsIgnoreCase("H")) {
                            getHighestValue(rule.get(i));

                            value = getDiscountTypeBaseOn( i) * totalFreePackSize;
                        }
                    }
//                    }

//                        }
                }else{
                    if (rule.get(i).getOpsCriteria().equalsIgnoreCase("L")) {
//                            IPOSApplication.minDiscount.clear();
                        getLowestValue(rule.get(i),totalFreePackSize);

                        for(int p = 0 ; p < IPOSApplication.minDiscount.size(); p++){
                            if(IPOSApplication.minDiscount.get(i).getIProductModalId().equalsIgnoreCase(datum.getIProductModalId())){
                                value = getDiscountTypeBaseOn( i) ;
                            }
                        }



                    } else if (rule.get(i).getOpsCriteria().equalsIgnoreCase("H")) {
                        getHighestValue(rule.get(i));

                        value = getDiscountTypeBaseOn( i);
                    }
                }
//                    }
//                }else  if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("V")){
//                    // Eligibility BasedOn VALUE
//                    double totalPrice = this.datum.getSalesPrice()* this.datum.getQty();
//                    double slabFrom = rule.get(i).getSlabFrom();
//                    double slabTo = rule.get(i).getSlabTO();
//                    if(totalPrice>=slabFrom && totalPrice <= slabTo){
//                        // Qty in range of SLAB from - SLAB to
//                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
//                            // Discount Based on MRP
//                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
//                                value = (this.datum.getMrp()*rule.get(i).getSDiscountValue())/100;
//                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
//                                value = this.datum.getMrp()-rule.get(i).getSDiscountValue();
//                            }
//                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
//                            // Discount Based on SP
//                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
//                                value = (this.datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
//                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
//                                value = this.datum.getSalesPrice()-rule.get(i).getSDiscountValue();
//                            }
//                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
//                            // Discount Based on NRV
//                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
//                                value = (this.datum.getNrv()*rule.get(i).getSDiscountValue())/100;
//                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
//                                value = this.datum.getNrv()-rule.get(i).getSDiscountValue();
//                            }
//                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
//                            // Discount Based on GPL
//                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
//                                value = (this.datum.getGpl()*rule.get(i).getSDiscountValue())/100;
//                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
//                                value = this.datum.getGpl()-rule.get(i).getSDiscountValue();
//                            }
//                        }
//
//                    }
//                }


                */
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
                                value = (this.datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getMrp()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getNrv()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getGpl()-rule.get(i).getSDiscountValue();
                            }
                        }

                    }
                }else  if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("V")){
                    // Eligibility BasedOn VALUE
                    double totalPrice = IPOSApplication.totalAmount;
                    double slabFrom = rule.get(i).getSlabFrom();
                    double slabTo = rule.get(i).getSlabTO();
                    if(totalPrice>=slabFrom && totalPrice <= slabTo){
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getMrp()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getNrv()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (this.datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = this.datum.getGpl()-rule.get(i).getSDiscountValue();
                            }
                        }

                    }
                }
            }else if(rule.get(i).getPackSize()>0) {
                int productCartItem = 0;
                int totalItem =0 ;
                int cartCount = IPOSApplication.mProductListResult.size();
//                discountbeforeSorting = new ArrayList<>();
//                if(productCartItem==1){
//                    productCartItem = IPOSApplication.mProductListResult.get(0).getQty();
//                }else {
                discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get( datum.getProductCode()));
                for (int l = 0 ; l < discountbeforeSorting.size() ; l++){
                    for (int h = 0 ; h < IPOSApplication.mProductListResult.size(); h++){
                        if(discountbeforeSorting.get(l).getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(h).getProductCode()))
                            productCartItem ++;
                    }
                }
//                int totalItem =rule.get(i).getSlabFrom() + rule.get(i).getPackSize();
//                }
                if(productCartItem > 0)
                    totalItem  = productCartItem / (rule.get(i).getSlabFrom() + rule.get(i).getPackSize());
                if(totalItem>=IPOSApplication.mProductListResult.get(cartCount).getTotalQty()) {
//                    int QuantityCheck =  this.datum.getQty() /rule.get(i).getPackSize() ;
//                    if(QuantityCheck>=rule.get(i).getSlabFrom() && QuantityCheck <= rule.get(i).getSlabTO())
//                    {
                    // Qty in range of SLAB from - SLAB to
                    if (rule.get(i).getOpsCriteria().equalsIgnoreCase("L")) {
                        getLowestValue(rule.get(i));

                        value = getDiscountTypeBaseOn( i)*totalItem;


                    } else if (rule.get(i).getOpsCriteria().equalsIgnoreCase("H")) {
                        getHighestValue(rule.get(i));

                        value = getDiscountTypeBaseOn( i)*totalItem;
                    }
//                    }

                }
            }



        }
        ProductSearchResult.Discount mDiscount = this.mDataset.get(holder.getAdapterPosition());
        mDiscount.setDiscountTotal(value);
        this.mDataset.set(holder.getAdapterPosition(),mDiscount);
       /* int quantityCheck=this.datum.getQty();
        if (rule.get(i).getPackSize()>0) {
            if (quantityCheck >= rule.get(i).getSlabFrom() && quantityCheck <= rule.get(i).getSlabTO() || this.datum.getQty() > rule.get(i).getSlabTO()) {
                this.datum.setQty((quantityCheck * rule.get(i).getPackSize())-1);
            }
        }*/
            this.datum.setDiscount(this.mDataset);
        IPOSApplication.mProductListResult.set(retailAdapterPosition, this.datum);

        return value;
    }
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
                iPriceValue =  datum.getMrp();
                break;
            case "GPL":
                iPriceValue =  datum.getGpl();
                break;
            case "NRV":
                iPriceValue =  datum.getNrv();
                break;
            case "SP":
                iPriceValue =  datum.getSalesPrice();
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
    private ArrayList<ProductSearchResult.Datum> getLowestValue(final ProductSearchResult.Rule rule,final int totalFreePackSize) {
        discountbeforeSorting = new ArrayList<>();
        discountbeforeSorting.addAll(IPOSApplication.datumSameCode.get(datum.getProductCode()));


        Collections.sort(discountbeforeSorting, new Comparator<ProductSearchResult.Datum>() {
            @Override
            public int compare(ProductSearchResult.Datum lhs, ProductSearchResult.Datum rhs) {
                int valueSort = 0;
                if (rule.getSDiscountBasedOn().equalsIgnoreCase("MRP")) {
                    valueSort = Double.valueOf(lhs.getMrp()).compareTo(rhs.getMrp());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getMrp();
                   getLowestArray(totalFreePackSize,"MRP");
                } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("NRV")) {
                    valueSort = Double.valueOf(lhs.getNrv()).compareTo(rhs.getNrv());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getNrv();
                    getLowestArray(totalFreePackSize,"NRV");
                } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("SP")) {
                    valueSort = Double.valueOf(lhs.getSalesPrice()).compareTo(rhs.getSalesPrice());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getSalesPrice();
                    getLowestArray(totalFreePackSize,"SP");
                } else if (rule.getSDiscountBasedOn().equalsIgnoreCase("GPL")) {
                    valueSort = Double.valueOf(lhs.getGpl()).compareTo(rhs.getGpl());
//                    lowestDiscountValue = discountbeforeSorting.get(0).getGpl();
                    getLowestArray(totalFreePackSize,"GPL");
                }
                return valueSort;
            }
        });
        return IPOSApplication.minDiscount;
    }

    private void getFirstIndexAfterSorting(String mPriceSelected) {
        if (discountbeforeSorting.size()>0){
            discountbeforeSorting.get(0).getMrp();
        }
        for (int f = 0 ; f < discountbeforeSorting.size() ; f++){
            ProductSearchResult.Datum datum = discountbeforeSorting.get(f);
            datum.setPriceSelected(mPriceSelected);
            IPOSApplication.minDiscount.add(datum);
        }

    }
    private void getLowestArray(int totalFreePackSize, String mPriceSelected) {
        for (int f = 0 ; f < discountbeforeSorting.size() ; f++){
            ProductSearchResult.Datum datum = discountbeforeSorting.get(f);
            datum.setPriceSelected(mPriceSelected);
            IPOSApplication.minDiscount.add(datum);
        }

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