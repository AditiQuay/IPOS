package quay.com.ipos.offerdiscount.adapter;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.offerdiscount.Model.RuleModel;
import quay.com.ipos.retailsales.adapter.ItemDetailAdapter;

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
    static Context mContext;
    ArrayList<RuleModel> mDataset;
    View.OnClickListener onClickListener;

    public RuleAdapter(Context ctx,
                       ArrayList<RuleModel> questionList, View.OnClickListener onClickListener) {
        this.mContext = ctx;
        this.mDataset = questionList;
        this.onClickListener = onClickListener;
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSchemeType,tvProduct,tvSKU,tvSlabDetails,tvSchemePeriod,tvNotes,tvSelectedTo,tvSelectedFrom;
        private RecyclerView rvProduct,rvSKU;
        private RecyclerView rvRule;
        private TextInputLayout tilNotes;
        private ImageView imvRemove;
        private LinearLayout llSchemePeriod,llSelectTo,llSelectFrom,llSchemeType;
        private RadioGroup rgSchemeType;
        private RadioButton rbProduct,rbValue,rbOthers;

        public UserViewHolder(View rootView) {
            super(rootView);
            tvSchemeType = rootView.findViewById(R.id.tvSchemeType);
            tvProduct = rootView.findViewById(R.id.tvProduct);
            tvSKU = rootView.findViewById(R.id.tvSKU);
            tvSlabDetails = rootView.findViewById(R.id.tvSlabDetails);
            tvSchemePeriod = rootView.findViewById(R.id.tvSchemePeriod);
            tvNotes = rootView.findViewById(R.id.tvNotes);
            llSchemeType = rootView.findViewById(R.id.llSchemeType);
            rvProduct = rootView.findViewById(R.id.rvProduct);
            rvSKU = rootView.findViewById(R.id.rvSKU);
            llSchemePeriod = rootView.findViewById(R.id.llSchemePeriod);
            llSelectTo = rootView.findViewById(R.id.llSelectTo);
            llSelectFrom = rootView.findViewById(R.id.llSelectFrom);
            tvSelectedTo = rootView.findViewById(R.id.tvSelectedTo);
            tvSelectedFrom = rootView.findViewById(R.id.tvSelectedFrom);
            llSchemePeriod = rootView.findViewById(R.id.llSchemePeriod);
            tilNotes = rootView.findViewById(R.id.tilNotes);
            rgSchemeType = rootView.findViewById(R.id.rgSchemeType);
            rbOthers = rootView.findViewById(R.id.rbOthers);
            rbProduct = rootView.findViewById(R.id.rbProduct);
            rbValue = rootView.findViewById(R.id.rbValue);
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_rule_item, parent, false);
            return new RuleAdapter.UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view =
                    LayoutInflater.from(mContext).inflate(R.layout.load_more_footer,
                            parent, false); return new RuleAdapter.LoadingViewHolder(view); }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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

            userViewHolder.tvSchemeType.setText(str.getsSchemeType());
            onBind = false;
//            tvSchemeType.setOnClickListener(this);
//            tvProduct.setOnClickListener(this);
//            tvSKU.setOnClickListener(this);
//            tvSlabDetails.setOnClickListener(this);
//            tvSchemePeriod.setOnClickListener(this);
//            tvNotes.setOnClickListener(this);
            userViewHolder.rbProduct.setOnCheckedChangeListener(onCheckedChangeListener);
            userViewHolder.rbOthers.setOnCheckedChangeListener(onCheckedChangeListener);
            userViewHolder.rbValue.setOnCheckedChangeListener(onCheckedChangeListener);
        }
        else if (holder instanceof RuleAdapter.LoadingViewHolder) {
            RuleAdapter.LoadingViewHolder loadingViewHolder = (RuleAdapter.LoadingViewHolder) holder;
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


    RadioButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            switch (compoundButton.getId()) {
                case R.id.rbProduct:

                    break;
                case R.id.rbValue:

                    break;
                case R.id.rbOthers:

                    break;
            }
        }
    };
}