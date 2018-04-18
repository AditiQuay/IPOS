package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.CatalogueModal;
import quay.com.ipos.modal.ProductCatalogueModal;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueSubCatalogueFragmentAdapter extends RecyclerView.Adapter<CatalogueSubCatalogueFragmentAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<CatalogueModal> catalogueModalsSet;

    public CatalogueSubCatalogueFragmentAdapter(Context mContext, ArrayList<CatalogueModal> catalogueModalsSet) {
        this.mContext = mContext;
        this.catalogueModalsSet = catalogueModalsSet;
    }

    @Override
    public CatalogueSubCatalogueFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogue_sub_product_fragment_items, parent, false);

        return new CatalogueSubCatalogueFragmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CatalogueSubCatalogueFragmentAdapter.MyViewHolder holder, int position) {
        CatalogueModal catalogueModal = catalogueModalsSet.get(position);
        holder.textViewProductName.setText(catalogueModal.sProductName);
        holder.textViewFeature.setText(catalogueModal.sProductFeature);
        holder.textViewPrice.setText(catalogueModal.sProductPrice);
    }

    @Override
    public int getItemCount() {
        return catalogueModalsSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName,textViewFeature,textViewPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewFeature = itemView.findViewById(R.id.textViewFeature);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypceFaceRobotoRegular(mContext));


        }
    }
}
