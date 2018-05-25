package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/24/2018.
 */

public class ProductCatalogueViewAllAdapter extends RecyclerView.Adapter<ProductCatalogueViewAllAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<ProductItemModal> productItemModals;
    private MyListener listener;

    public ProductCatalogueViewAllAdapter(Context mContext, MyListener listener, ArrayList<ProductItemModal> productItemModals) {
        this.mContext = mContext;
        this.productItemModals = productItemModals;
        this.listener = listener;

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
        holder.textViewProductName.setText(productItemModal.getProductName());
        holder.textViewProductCount.setText(productItemModal.getCount());
        if (NetUtil.isNetworkAvailable(mContext)) {
            Picasso.get().load(productItemModal.getProductUrl()).placeholder(R.drawable.product_placeholder).into(holder.imageViewProduct);

        } else {
            Picasso.get()
                    .load(productItemModal.getProductUrl())
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


        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                listener.onRowClicked(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemModals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName;
        private CardView cardViewProduct;
        private TextView textViewProductCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            cardViewProduct = itemView.findViewById(R.id.cardViewProduct);
            textViewProductCount = itemView.findViewById(R.id.textViewProductCount);

            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));
//            FontUtil.applyTypeface(textViewViewAll, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));

        }
    }
}
