package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.ProductCatalogueModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class ProductCatalogueMainFragmentAdapter extends RecyclerView.Adapter<ProductCatalogueMainFragmentAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<ProductCatalogueModal> productCatalogueModalsSet;
    private MyListener listener;

    public ProductCatalogueMainFragmentAdapter(Context mContext, MyListener myListener, ArrayList<ProductCatalogueModal> productCatalogueModalsSet) {
        this.mContext = mContext;
        this.listener = myListener;
        this.productCatalogueModalsSet = productCatalogueModalsSet;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_catalogue_main_fragment_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ProductCatalogueModal productCatalogueModal = productCatalogueModalsSet.get(position);
        holder.textViewProductName.setText(productCatalogueModal.productName);
        holder.imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                listener.onRowClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productCatalogueModalsSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);

            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypceFaceRobotoLight(mContext));


        }
    }
}
