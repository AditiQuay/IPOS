package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.modal.CustomerList;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.ui.CircleTransform;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 26-04-2018.
 */

public class DiscountListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    // private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    View.OnClickListener mOnClickListener;
    static Context mContext;
    ProductSearchResult.Datum datum;
    ArrayList<ProductSearchResult.Discount> mDataset;
    private ArrayList<ProductSearchResult.Rule> rule = new            ArrayList<>();
    RecyclerView mRecyclerView;

    public DiscountListAdapter(Context ctx, RecyclerView mRecycler,
                               ArrayList<ProductSearchResult.Discount> questionList, ProductSearchResult.Datum str) {

        mContext = ctx;
        mDataset = questionList;
        mRecyclerView = mRecycler;
        datum = str;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDiscountPrice,tvDiscount;
        public LinearLayout llDiscount;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvDiscountPrice =  itemView.findViewById(R.id.tvDiscountPrice);
            tvDiscount=itemView.findViewById(R.id.tvDiscount);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DiscountListAdapter.UserViewHolder) {
            ProductSearchResult.Discount str = mDataset.get(position);
            AppLog.e(DiscountListAdapter.class.getSimpleName(), Util.getCustomGson().toJson(str));
            DiscountListAdapter.UserViewHolder userViewHolder = (DiscountListAdapter.UserViewHolder) holder;
            userViewHolder.tvDiscount.setText(str.getSDiscountName());

            if(str.getRule()!=null && str.getRule().size()>0){
                rule = str.getRule();

                for (int i = 0 ; i < rule.size() ; i++){
                    if(rule.get(i).getRuleType().equalsIgnoreCase("I")){
                        userViewHolder.llDiscount.setVisibility(View.VISIBLE);
                        userViewHolder.tvDiscountPrice.setText(setOPS(i)+"");
                    }else {
                        userViewHolder.llDiscount.setVisibility(View.GONE);
                    }
                }
            }



        }
        else if (holder instanceof DiscountListAdapter.LoadingViewHolder) {
            DiscountListAdapter.LoadingViewHolder loadingViewHolder = (DiscountListAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true); }

    }

    private double setOPS(int i) {
        Double value =0.0;
        if(rule.get(i).getOpsType().equalsIgnoreCase("P")){
            // OPS TYPE if Product
            if(rule.get(i).getPackSize()==0)
            {
                if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("Q")){
                    // Eligibility BasedOn QUANTITY
                    if(datum.getQty()>rule.get(i).getSlabFrom() && datum.getQty() < rule.get(i).getSlabTO()){
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getMrp()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getNrv()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getGpl()-rule.get(i).getSDiscountValue();
                            }
                        }

                    }
                }else  if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("V")){
                    // Eligibility BasedOn VALUE
                    double totalPrice = datum.getSalesPrice()*datum.getQty();
                    double slabFrom = rule.get(i).getSlabFrom();
                    double slabTo = rule.get(i).getSlabTO();
                    if(totalPrice>=slabFrom && totalPrice <= slabTo){
                        // Qty in range of SLAB from - SLAB to
                        if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                            // Discount Based on MRP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getMrp()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                            // Discount Based on SP
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                            // Discount Based on NRV
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getNrv()-rule.get(i).getSDiscountValue();
                            }
                        }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                            // Discount Based on GPL
                            if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                value = (datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                            }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                value = datum.getGpl()-rule.get(i).getSDiscountValue();
                            }
                        }

                    }
                }
            }else if(rule.get(i).getPackSize()>0) {

            }


        }else if(rule.get(i).getOpsType().equalsIgnoreCase("V")){
            // OPS TYPE if Value
                if(rule.get(i).getPackSize()==0)
                {
                    if(rule.get(i).getSEligibilityBasedOn().equalsIgnoreCase("Q")){
                        // Eligibility BasedOn QUANTITY
                        if(datum.getQty()>rule.get(i).getSlabFrom() && datum.getQty() < rule.get(i).getSlabTO()){
                            // Qty in range of SLAB from - SLAB to
                            if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("MRP")){
                                // Discount Based on MRP
                                if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                    value = (datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getMrp()-rule.get(i).getSDiscountValue();
                                }
                            }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                                // Discount Based on SP
                                if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                    value = (datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                                }
                            }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                                // Discount Based on NRV
                                if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                    value = (datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getNrv()-rule.get(i).getSDiscountValue();
                                }
                            }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                                // Discount Based on GPL
                                if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                    value = (datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getGpl()-rule.get(i).getSDiscountValue();
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
                                    value = (datum.getMrp()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getMrp()-rule.get(i).getSDiscountValue();
                                }
                            }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("SP")){
                                // Discount Based on SP
                                if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                    value = (datum.getSalesPrice()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getSalesPrice()-rule.get(i).getSDiscountValue();
                                }
                            }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("NRV")){
                                // Discount Based on NRV
                                if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                    value = (datum.getNrv()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getNrv()-rule.get(i).getSDiscountValue();
                                }
                            }else if(rule.get(i).getSDiscountBasedOn().equalsIgnoreCase("GPL")){
                                // Discount Based on GPL
                                if(rule.get(i).getSDiscountType().equalsIgnoreCase("P")){
                                    value = (datum.getGpl()*rule.get(i).getSDiscountValue())/100;
                                }else if(rule.get(i).getSDiscountType().equalsIgnoreCase("V")){
                                    value = datum.getGpl()-rule.get(i).getSDiscountValue();
                                }
                            }

                        }
                    }
                }else if(rule.get(i).getPackSize()>0) {

                }



            }
        return value;
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