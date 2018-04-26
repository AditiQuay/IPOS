package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.productCatalogue.CatalogueSubProduct;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/24/2018.
 */

public class ProductCatalogueViewAllAdapter extends RecyclerView.Adapter<ProductCatalogueViewAllAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<ProductItemModal> productItemModals;

    public ProductCatalogueViewAllAdapter(Context mContext, ArrayList<ProductItemModal> productItemModals) {
        this.mContext = mContext;
        this.productItemModals = productItemModals;

    }

    @Override
    public ProductCatalogueViewAllAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_catalogue_view_all_items, parent, false);

        return new ProductCatalogueViewAllAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductCatalogueViewAllAdapter.MyViewHolder holder, final int position) {
        final ProductItemModal productItemModal = productItemModals.get(position);
        holder.textViewName.setText(productItemModal.getProductName());
        holder.textViewProductDetails.setText(productItemModal.getProductDescription());
        holder.textViewViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                Intent i = new Intent(mContext, CatalogueSubProduct.class);
                i.putExtra("Product Name", productItemModal.getProductName());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemModals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewName, textViewProductDetails;
        private TextView textViewViewAll;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewProductDetails = itemView.findViewById(R.id.textViewProductDetails);
            textViewViewAll = itemView.findViewById(R.id.textViewViewAll);

            FontUtil.applyTypeface(textViewName, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));
            FontUtil.applyTypeface(textViewViewAll, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));
            FontUtil.applyTypeface(textViewProductDetails, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        }
    }
}
