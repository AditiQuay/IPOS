package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.DataSheetDownloadListener;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueSubCatalogueFragmentAdapter extends RecyclerView.Adapter<CatalogueSubCatalogueFragmentAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<CatalogueModal> catalogueModalsSet;
    private MyListener listener;
    private DataSheetDownloadListener dataSheetDownloadListener;
    public CatalogueSubCatalogueFragmentAdapter(Context mContext, ArrayList<CatalogueModal> catalogueModalsSet, MyListener listener,DataSheetDownloadListener dataSheetDownloadListener) {
        this.mContext = mContext;
        this.catalogueModalsSet = catalogueModalsSet;
        this.listener = listener;
        this.dataSheetDownloadListener=dataSheetDownloadListener;
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
        holder.textViewProductName.setText(catalogueModal.sProductName);
        holder.textViewFeature.setText(catalogueModal.sProductFeature);
        holder.textViewPrice.setText(catalogueModal.sProductPrice);
        holder.textViewOffer.setText(catalogueModal.sPoints);
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

        public MyViewHolder(View itemView) {
            super(itemView);
            cardViewProduct = itemView.findViewById(R.id.cardViewProduct);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewFeature = itemView.findViewById(R.id.textViewFeature);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewOffer = itemView.findViewById(R.id.textViewOffer);

            btnDataSheet = itemView.findViewById(R.id.btnDataSheet);
            btnCalculator = itemView.findViewById(R.id.btnCalculator);
            btnCart = itemView.findViewById(R.id.btnCart);

            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));
            FontUtil.applyTypeface(textViewFeature, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(textViewPrice, FontUtil.getTypeFaceRobotTiteliumRegularLight(mContext));
            FontUtil.applyTypeface(textViewOffer, FontUtil.getTypeFaceRobotTiteliumRegularLight(mContext));

            FontUtil.applyTypeface(btnDataSheet,FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(btnCalculator,FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(btnCart,FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        }
    }
}
