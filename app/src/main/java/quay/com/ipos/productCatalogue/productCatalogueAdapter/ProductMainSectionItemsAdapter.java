package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.productCatalogue.CatalogueSubProduct;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductMainSectionItemsAdapter extends RecyclerView.Adapter<ProductMainSectionItemsAdapter.SingleItemRowHolder> {
    private ArrayList<ProductItemModal> productItemModalArrayList;
    private Context mContext;

    public ProductMainSectionItemsAdapter(Context context, ArrayList<ProductItemModal> productItemModalArrayList) {
        this.mContext = context;
        this.productItemModalArrayList = productItemModalArrayList;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_section_items, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int i) {
        ProductItemModal singleItem = productItemModalArrayList.get(i);
        holder.textViewProductName.setText(singleItem.getProductName());

        holder.imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                ProductItemModal productCatalogueModal = productItemModalArrayList.get(i);

                Intent i = new Intent(mContext,CatalogueSubProduct.class);
                i.putExtra("Product Name",productCatalogueModal.getProductName());
                mContext.startActivity(i);

            }
        });
        holder.textViewProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                ProductItemModal productCatalogueModal = productItemModalArrayList.get(i);

                Intent i = new Intent(mContext,CatalogueSubProduct.class);
                i.putExtra("Product Name",productCatalogueModal.getProductName());
                mContext.startActivity(i);

            }
        });
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != productItemModalArrayList ? productItemModalArrayList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView textViewProductName;

        protected ImageView imageViewProduct;


        public SingleItemRowHolder(View view) {
            super(view);
            this.textViewProductName = (TextView) view.findViewById(R.id.textViewProductName);
            this.imageViewProduct = (ImageView) view.findViewById(R.id.imageViewProduct);


            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypceFaceRobotoRegular(mContext));

        }

    }
}
