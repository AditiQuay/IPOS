package quay.com.ipos.offerdiscount.adapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.offerdiscount.Model.RuleModel;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.ContactInfoAdapter;
import quay.com.ipos.retailsales.adapter.ItemDetailAdapter;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 16-07-2018.
 */

public class RuleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean onBind;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    // private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context mContext;
    private List<RuleModel> mDataset;
    View.OnClickListener onClickListener;

    public RuleAdapter(Context ctx, List<RuleModel> questionList, View.OnClickListener onClickListener) {

        Log.v("RuleAdapter", questionList.size() + "");

        this.mContext = ctx;
        this.mDataset = questionList;
        this.onClickListener = onClickListener;
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSchemeType,tvProduct,tvProductGroup,tvSKU,tvSlabDetails,tvSchemePeriod,tvNotes,tvSelectedTo,tvSelectedFrom,tvEligibilityBasedOn,tvRuleNo,tvDiscountType,tvDiscountBasedOn,tvProductCategory,tvSubCategory,tvBrand;
        private RecyclerView rvProduct,rvSKU,rvProductGroup,rvProductCategory,rvSubCategory,rvBrand;
        private TextInputLayout tilNotes,tilSlabSelectedTo,tilSlabSelectedFrom,tilSlabValue;
        private TextInputEditText tieSlabSelectedTo,tieSlabSelectedFrom,tieSlabValue;
        private ImageView imvRemove;
        private LinearLayout llSchemePeriod,llSelectTo,llSelectFrom,llSlabDetails;
        private MaterialSpinner spCrossBundle,spCriteria,spEligibilityBasedOn,spDiscountType,spDiscountBasedOn,spSchemeType;
        public MyCustomSpinnerListener mMyCustomSpinnerListener;
        public MyCustomEditTextListener mMyCustomEditTextListener;
        public onClicked mOnClicked;

        public UserViewHolder(View rootView, MyCustomEditTextListener mMyCustomEditTextListener ,onClicked mOnClicked, MyCustomSpinnerListener mMyCustomSpinnerListener) {
            super(rootView);
            this.mMyCustomEditTextListener = mMyCustomEditTextListener;
            this.mOnClicked = mOnClicked;
            this.mMyCustomSpinnerListener = mMyCustomSpinnerListener;
            llSlabDetails = rootView.findViewById(R.id.llSlabDetails);
            rvBrand = rootView.findViewById(R.id.rvBrand);
            rvSubCategory = rootView.findViewById(R.id.rvSubCategory);
            rvProductCategory = rootView.findViewById(R.id.rvProductCategory);
            rvProductGroup = rootView.findViewById(R.id.rvProductGroup);
            tvBrand = rootView.findViewById(R.id.tvBrand);
            tvProductCategory = rootView.findViewById(R.id.tvProductCategory);
            tvSubCategory = rootView.findViewById(R.id.tvSubCategory);
            tvProductGroup = rootView.findViewById(R.id.tvProductGroup);
            spSchemeType = rootView.findViewById(R.id.spSchemeType);
            spDiscountBasedOn = rootView.findViewById(R.id.spDiscountBasedOn);
            tvDiscountBasedOn = rootView.findViewById(R.id.tvDiscountBasedOn);
            spDiscountType = rootView.findViewById(R.id.spDiscountType);
            tvDiscountType = rootView.findViewById(R.id.tvDiscountType);
            spEligibilityBasedOn = rootView.findViewById(R.id.spEligibilityBasedOn);
            spCrossBundle = rootView.findViewById(R.id.spCrossBundle);
            tvEligibilityBasedOn = rootView.findViewById(R.id.tvEligibilityBasedOn);
            spCriteria = rootView.findViewById(R.id.spCriteria);
            tvRuleNo = rootView.findViewById(R.id.tvRuleNo);
            tvSchemeType = rootView.findViewById(R.id.tvSchemeType);
            tvProduct = rootView.findViewById(R.id.tvProduct);
            tvSKU = rootView.findViewById(R.id.tvSKU);
            tvSlabDetails = rootView.findViewById(R.id.tvSlabDetails);
            tvSchemePeriod = rootView.findViewById(R.id.tvSchemePeriod);
            tvNotes = rootView.findViewById(R.id.tvNotes);
            rvProduct = rootView.findViewById(R.id.rvProduct);
            rvSKU = rootView.findViewById(R.id.rvSKU);
            llSchemePeriod = rootView.findViewById(R.id.llSchemePeriod);
            llSelectTo = rootView.findViewById(R.id.llSelectTo);
            llSelectFrom = rootView.findViewById(R.id.llSelectFrom);
            tvSelectedTo = rootView.findViewById(R.id.tvSelectedTo);
            tvSelectedFrom = rootView.findViewById(R.id.tvSelectedFrom);
            llSchemePeriod = rootView.findViewById(R.id.llSchemePeriod);
            tilNotes = rootView.findViewById(R.id.tilNotes);
            tilSlabSelectedTo = rootView.findViewById(R.id.tilSlabSelectedTo);
            tieSlabSelectedTo = rootView.findViewById(R.id.tieSlabSelectedTo);
            tilSlabSelectedFrom = rootView.findViewById(R.id.tilSlabSelectedFrom);
            tieSlabSelectedFrom = rootView.findViewById(R.id.tieSlabSelectedFrom);
            tilSlabValue = rootView.findViewById(R.id.tilSlabValue);
            tieSlabValue = rootView.findViewById(R.id.tieSlabValue);
            imvRemove = rootView.findViewById(R.id.imvRemove);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_rule_item_new, parent, false);
            return new RuleAdapter.UserViewHolder(view, new MyCustomEditTextListener(), new onClicked() ,new MyCustomSpinnerListener());
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new RuleAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RuleAdapter.UserViewHolder) {
            onBind = true;
            RuleModel str = mDataset.get(position);
            RuleAdapter.UserViewHolder userViewHolder = (RuleAdapter.UserViewHolder) holder;

            if(position == 0)
            {
                userViewHolder.imvRemove.setVisibility(View.GONE);
            }else {
                userViewHolder.imvRemove.setVisibility(View.VISIBLE);
            }

//            userViewHolder.tvSchemeType.setText(str.getsSchemeType());
            userViewHolder.tvRuleNo.setText("Rule #"+(position+1 )+"");
            onBind = false;
            mDataset.get(position).setiRuleNumber(position+1);

            userViewHolder.mMyCustomEditTextListener.updatePosition(position,userViewHolder);
            userViewHolder.tieSlabSelectedTo.addTextChangedListener(userViewHolder.mMyCustomEditTextListener);
            userViewHolder.tieSlabSelectedFrom.addTextChangedListener(userViewHolder.mMyCustomEditTextListener);
            userViewHolder.tieSlabValue.addTextChangedListener(userViewHolder.mMyCustomEditTextListener);

            userViewHolder.mOnClicked.updatePosition(position,userViewHolder);
            userViewHolder.tvProduct.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvProductGroup.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvProductCategory.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvSubCategory.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvSKU.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvBrand.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.llSelectTo.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvSlabDetails.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvSchemePeriod.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.llSelectFrom.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvEligibilityBasedOn.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvDiscountType.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvDiscountBasedOn.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvSchemeType.setOnClickListener(userViewHolder.mOnClicked);
            userViewHolder.tvNotes.setOnClickListener(userViewHolder.mOnClicked);

            userViewHolder.mMyCustomSpinnerListener.updatePosition(position,userViewHolder);
            userViewHolder.spCriteria.setOnItemSelectedListener(userViewHolder.mMyCustomSpinnerListener);
            userViewHolder.spCrossBundle.setOnItemSelectedListener(userViewHolder.mMyCustomSpinnerListener);
            userViewHolder.spEligibilityBasedOn.setOnItemSelectedListener(userViewHolder.mMyCustomSpinnerListener);
            userViewHolder.spDiscountType.setOnItemSelectedListener(userViewHolder.mMyCustomSpinnerListener);
            userViewHolder.spDiscountBasedOn.setOnItemSelectedListener(userViewHolder.mMyCustomSpinnerListener);

            userViewHolder.imvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDataset.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
        else if (holder instanceof RuleAdapter.LoadingViewHolder) {
            RuleAdapter.LoadingViewHolder loadingViewHolder = (RuleAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }


    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar =  itemView.findViewById(R.id.load_more_progressBar);
        }
    }
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }


    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private RuleAdapter.UserViewHolder holder;

        public void updatePosition(int position, RuleAdapter.UserViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try {

                if (holder.tieSlabSelectedFrom != null) {
                    if (holder.tieSlabSelectedFrom.getText().hashCode() == charSequence.hashCode()) {
                        if(charSequence.toString().trim().equalsIgnoreCase(""))
                            mDataset.get(position).setiFrom(Integer.parseInt(charSequence.toString())) ;
                        else
                            mDataset.get(position).setiFrom(0);
                    }
                }

                if (holder.tieSlabSelectedTo != null) {
                    if (holder.tieSlabSelectedTo.getText().hashCode() == charSequence.hashCode()) {
                        if(charSequence.toString().trim().equalsIgnoreCase(""))
                            mDataset.get(position).setiTo(Integer.parseInt(charSequence.toString()));
                        else
                            mDataset.get(position).setiTo(0);
                    }
                }

                if (holder.tieSlabValue != null) {
                    if (holder.tieSlabValue.getText().hashCode() == charSequence.hashCode()) {
                        if(charSequence.toString().trim().equalsIgnoreCase(""))
                            mDataset.get(position).setiValue(Integer.parseInt(charSequence.toString()));
                        else
                            mDataset.get(position).setiValue(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

            // no op
        }
    }

    public class onClicked implements View.OnClickListener{
        private int position;
        private RuleAdapter.UserViewHolder holder;
        public void updatePosition(int position, RuleAdapter.UserViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.tvProduct:
                    if(holder.rvProduct.getVisibility()==View.VISIBLE){
                        holder.rvProduct.setVisibility(View.GONE);
                    }else {
                        holder.rvProduct.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvProductGroup:
                    if(holder.rvProductGroup.getVisibility()==View.VISIBLE){
                        holder.rvProductGroup.setVisibility(View.GONE);
                    }else {
                        holder.rvProductGroup.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvProductCategory:
                    if(holder.rvProductCategory.getVisibility()==View.VISIBLE){
                        holder.rvProductCategory.setVisibility(View.GONE);
                    }else {
                        holder.rvProductCategory.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvSubCategory:
                    if(holder.rvSubCategory.getVisibility()==View.VISIBLE){
                        holder.rvSubCategory.setVisibility(View.GONE);
                    }else {
                        holder.rvSubCategory.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvSKU:
                    if(holder.rvSKU.getVisibility()==View.VISIBLE){
                        holder.rvSKU.setVisibility(View.GONE);
                    }else {
                        holder.rvSKU.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvBrand:
                    if(holder.rvBrand.getVisibility()==View.VISIBLE){
                        holder.rvBrand.setVisibility(View.GONE);
                    }else {
                        holder.rvBrand.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvSchemeType:
                    if(holder.spSchemeType.getVisibility()==View.VISIBLE){
                        holder.spSchemeType.setVisibility(View.GONE);
                    }else {
                        holder.spSchemeType.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvSlabDetails:
                    if(holder.llSlabDetails.getVisibility()==View.VISIBLE){
                        holder.llSlabDetails.setVisibility(View.GONE);
                    }else {
                        holder.llSlabDetails.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvSchemePeriod:
                    if(holder.llSchemePeriod.getVisibility()==View.VISIBLE){
                        holder.llSchemePeriod.setVisibility(View.GONE);
                    }else {
                        holder.llSchemePeriod.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvEligibilityBasedOn:
                    if(holder.spEligibilityBasedOn.getVisibility()==View.VISIBLE){
                        holder.spEligibilityBasedOn.setVisibility(View.GONE);
                    }else {
                        holder.spEligibilityBasedOn.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvDiscountType:
                    if(holder.spDiscountType.getVisibility()==View.VISIBLE){
                        holder.spDiscountType.setVisibility(View.GONE);
                    }else {
                        holder.spDiscountType.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tvDiscountBasedOn:
                    if(holder.spDiscountBasedOn.getVisibility()==View.VISIBLE){
                        holder.spDiscountBasedOn.setVisibility(View.GONE);
                    }else {
                        holder.spDiscountBasedOn.setVisibility(View.VISIBLE);
                    }
                    break;

                case R.id.tvNotes:
                    if(holder.tilNotes.getVisibility()==View.VISIBLE){
                        holder.tilNotes.setVisibility(View.GONE);
                    }else {
                        holder.tilNotes.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    private List<String> listPosition = new ArrayList<>();


    private class MyCustomSpinnerListener implements AdapterView.OnItemSelectedListener {
        private int position;
        private RuleAdapter.UserViewHolder holder;
        public void updatePosition(int position, RuleAdapter.UserViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (adapterView.getId()) {
                case R.id.spSchemeType:
                    if (i != -1) {
                        listPosition = Arrays.asList(mContext.getResources().getStringArray(R.array.scheme_type));
                        mDataset.get(position).setsSchemeType(listPosition.get(i));
                    } else {
                        mDataset.get(position).setsSchemeType("");
                    }
                    break;
                case R.id.spCrossBundle:
                    if (i != -1) {
                        listPosition = Arrays.asList(mContext.getResources().getStringArray(R.array.choose));
                        if (listPosition.get(i).equalsIgnoreCase("yes"))
                            mDataset.get(position).setCrossBundle(true);
                        else
                            mDataset.get(position).setCrossBundle(false);
                    }else {
                        mDataset.get(position).setCrossBundle(false);
                    }
                    break;
                case R.id.spCriteria:
                    if (i != -1) {
                        listPosition = Arrays.asList(mContext.getResources().getStringArray(R.array.criteria));
                        mDataset.get(position).setmCriteria(listPosition.get(i));
                    } else {
                        mDataset.get(position).setmCriteria("");
                    }
                    break;
                case R.id.spEligibilityBasedOn:
                    if (i != -1) {
                        listPosition = Arrays.asList(mContext.getResources().getStringArray(R.array.eligibility_based_on));
                        mDataset.get(position).setsEligibilityBasedOn(listPosition.get(i));
                    } else {
                        mDataset.get(position).setsEligibilityBasedOn("");
                    }
                    break;
                case R.id.spDiscountType:
                    if (i != -1) {
                        listPosition = Arrays.asList(mContext.getResources().getStringArray(R.array.discount_type));
                        mDataset.get(position).setsDiscountType(listPosition.get(i));
                    } else {
                        mDataset.get(position).setsDiscountType("");
                    }
                    break;
                case R.id.spDiscountBasedOn:
                    if (i != -1) {
                        listPosition = Arrays.asList(mContext.getResources().getStringArray(R.array.discount_based_on));
                        mDataset.get(position).setsDiscountBasedOn(listPosition.get(i));
                    } else {
                        mDataset.get(position).setsDiscountBasedOn("");
                    }
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    }

}