package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.productCatalogue.CatalogueSubProduct;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.productCatalogue.productModal.SearchedItemModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/27/2018.
 */

public class SearchedItemsAdapter extends RecyclerView.Adapter<SearchedItemsAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<ProductItemModal> searchedItemModals;
    private ArrayList<ProductItemModal> mFilteredList;

    public SearchedItemsAdapter(Context mContext, ArrayList<ProductItemModal> searchedItemModals) {
        this.mContext = mContext;
        this.searchedItemModals = searchedItemModals;
        mFilteredList = searchedItemModals;

    }

    @Override
    public SearchedItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_catalogue_view_all_items, parent, false);

        return new SearchedItemsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchedItemsAdapter.MyViewHolder holder, final int position) {
        final ProductItemModal searchedItemModal = mFilteredList.get(position);
        holder.textViewProductName.setText(searchedItemModal.getProductName());
        holder.textViewProductCount.setText(searchedItemModal.getCount());
        if (NetUtil.isNetworkAvailable(mContext)) {
            Picasso.get().load(searchedItemModal.getProductUrl()).placeholder(R.drawable.product_placeholder).into(holder.imageViewProduct);

        } else {
            Picasso.get()
                    .load(searchedItemModal.getProductUrl())
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
                Intent i = new Intent(mContext, CatalogueSubProduct.class);
                i.putExtra("ProductName", searchedItemModal.getProductName());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {

                    mFilteredList = searchedItemModals;
                } else {
                    ArrayList<ProductItemModal> filteredList = new ArrayList<>();
                    for (ProductItemModal searchedItemModal : searchedItemModals) {

                        if (searchedItemModal.getProductName().equalsIgnoreCase(charString.toLowerCase())|| searchedItemModal.getProductName().equalsIgnoreCase(charString.toUpperCase()) || searchedItemModal.getProductName().toLowerCase().contains(charString)||searchedItemModal.getProductName().toUpperCase().contains(charString)) {

                            filteredList.add(searchedItemModal);
                        }
                    }
                    mFilteredList = filteredList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<ProductItemModal>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName;
        private TextView textViewViewAll,textViewProductCount;
        private CardView cardViewProduct;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            cardViewProduct = itemView.findViewById(R.id.cardViewProduct);
            textViewProductCount = itemView.findViewById(R.id.textViewProductCount);
            FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));

        }
    }
}
