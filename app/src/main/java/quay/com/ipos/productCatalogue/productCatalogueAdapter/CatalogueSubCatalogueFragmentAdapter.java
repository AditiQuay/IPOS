package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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
    private MyListener listener;
    public CatalogueSubCatalogueFragmentAdapter(Context mContext, ArrayList<CatalogueModal> catalogueModalsSet,MyListener listener) {
        this.mContext = mContext;
        this.catalogueModalsSet = catalogueModalsSet;
        this.listener = listener;
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

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRowClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catalogueModalsSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName,textViewFeature,textViewPrice;
        private CardView card_view;
        public MyViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewFeature = itemView.findViewById(R.id.textViewFeature);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypceFaceRobotoRegular(mContext));


        }
    }
}
