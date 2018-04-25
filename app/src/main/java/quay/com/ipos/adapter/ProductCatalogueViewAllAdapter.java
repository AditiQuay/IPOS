package quay.com.ipos.adapter;

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
import quay.com.ipos.modal.ProductCatalogueModal;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductCatalogueMainFragmentAdapter;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/24/2018.
 */

public class ProductCatalogueViewAllAdapter extends RecyclerView.Adapter<ProductCatalogueViewAllAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<ProductCatalogueModal> productCatalogueModalsSet;

    public ProductCatalogueViewAllAdapter(Context mContext, ArrayList<ProductCatalogueModal> productCatalogueModalsSet) {
        this.mContext = mContext;
        this.productCatalogueModalsSet = productCatalogueModalsSet;


    }

    @Override
    public ProductCatalogueViewAllAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_catalogue_view_all_items, parent, false);

        return new ProductCatalogueViewAllAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductCatalogueViewAllAdapter.MyViewHolder holder, final int position) {
        ProductCatalogueModal productCatalogueModal = productCatalogueModalsSet.get(position);
        holder.textViewName.setText(productCatalogueModal.productName);
    }

    @Override
    public int getItemCount() {
        return productCatalogueModalsSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewName;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewName = itemView.findViewById(R.id.textViewName);

            FontUtil.applyTypeface(textViewName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        }
    }
}
