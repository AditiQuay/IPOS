package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerInfoAdapter;
import quay.com.ipos.listeners.DataSheetDownloadListener;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueSubCatalogueFragmentAdapter extends RecyclerView.Adapter<CatalogueSubCatalogueFragmentAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<CatalogueModal> catalogueModalsSet;
    private MyListener listener;
    private DataSheetDownloadListener dataSheetDownloadListener;

    public CatalogueSubCatalogueFragmentAdapter(Context mContext, ArrayList<CatalogueModal> catalogueModalsSet, MyListener listener, DataSheetDownloadListener dataSheetDownloadListener) {
        this.mContext = mContext;
        this.catalogueModalsSet = catalogueModalsSet;
        this.listener = listener;
        this.dataSheetDownloadListener = dataSheetDownloadListener;
    }

    @Override
    public CatalogueSubCatalogueFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogue_sub_product_fragment_items, parent, false);

        return new CatalogueSubCatalogueFragmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CatalogueSubCatalogueFragmentAdapter.MyViewHolder holder, final int position) {
        CatalogueModal catalogueModal = catalogueModalsSet.get(position);
        holder.textViewProductName.setText(catalogueModal.getsProductName());
        holder.textViewFeature.setText(catalogueModal.getsProductFeature());
        String s1 = catalogueModal.getsProductPrice();

        if (!TextUtils.isEmpty(s1)){
            String[] words = s1.split("-");
            String part1 =words[0];
            String part2 = words[1];
            String finalPrice = "\u20B9"+" "+part1+"-"+" \u20B9"+" "+part2;
            holder.textViewPrice.setText(finalPrice);
        }else {
            holder.textViewPrice.setText("");

        }

        holder.textViewOffer.setText(catalogueModal.getsPoints() + " " + mContext.getResources().getString(R.string.text_points));
        if (catalogueModal.getIsOnOffer()){
            holder.rLayoutTopStrip.setVisibility(View.VISIBLE);
        }else {
            holder.rLayoutTopStrip.setVisibility(View.INVISIBLE);
        }

        if (catalogueModal.getIsDataSheet()) {
            holder.btnDataSheet.setVisibility(View.VISIBLE);
        } else {
            holder.btnDataSheet.setVisibility(View.INVISIBLE);
        }
        if (catalogueModal.getIsCalculator()) {
            holder.btnCalculator.setVisibility(View.VISIBLE);
        } else {
            holder.btnCalculator.setVisibility(View.INVISIBLE);
        }

        if (NetUtil.isNetworkAvailable(mContext)) {
            if (!TextUtils.isEmpty(catalogueModal.getsProductUrl())){
                Picasso.get().load(catalogueModal.getsProductUrl()).placeholder(R.drawable.product_placeholder).into(holder.imageViewProduct);
            }else {
                holder.imageViewProduct.setImageResource(R.drawable.product_placeholder);
            }

        } else {
            Picasso.get()
                    .load(catalogueModal.getsProductUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.product_placeholder)
                    .into(holder.imageViewProduct, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
        }
        applyClickEvents(holder, position);

    }

    private void applyClickEvents(final CatalogueSubCatalogueFragmentAdapter.MyViewHolder holder, final int position) {
        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                listener.onRowClicked(position);
            }
        });
        holder.btnDataSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSheetDownloadListener.onDataSheetDownload(position);
            }
        });
        holder.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSheetDownloadListener.onCartBtnClick(position);
            }
        });
        holder.btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSheetDownloadListener.onCalculatorSheetDownload(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catalogueModalsSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName, textViewFeature, textViewPrice, textViewOffer;
        private CardView cardViewProduct;
        private Button btnDataSheet, btnCalculator, btnCart;
        private RelativeLayout rLayoutTopStrip;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardViewProduct = itemView.findViewById(R.id.cardViewProduct);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewFeature = itemView.findViewById(R.id.textViewFeature);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewOffer = itemView.findViewById(R.id.textViewOffer);
            rLayoutTopStrip = itemView.findViewById(R.id.rLayoutTopStrip);
            btnDataSheet = itemView.findViewById(R.id.btnDataSheet);
            btnCalculator = itemView.findViewById(R.id.btnCalculator);
            btnCart = itemView.findViewById(R.id.btnCart);

            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));
            FontUtil.applyTypeface(textViewFeature, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewPrice, FontUtil.getTypeFaceRobotTiteliumRegularLight(mContext));
            FontUtil.applyTypeface(textViewOffer, FontUtil.getTypeFaceRobotTiteliumRegularLight(mContext));

            FontUtil.applyTypeface(btnDataSheet, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(btnCalculator, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(btnCart, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        }
    }
}
