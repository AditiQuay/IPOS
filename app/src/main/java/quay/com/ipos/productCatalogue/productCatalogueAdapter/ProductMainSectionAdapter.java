package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.productCatalogue.ProductCatalogueViewAll;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductMainSectionAdapter extends RecyclerView.Adapter<ProductMainSectionAdapter.ItemRowHolder> {
    private ArrayList<ProductSectionModal> productSectionModals;
    private Context mContext;


    public ProductMainSectionAdapter(Context context, ArrayList<ProductSectionModal> productSectionModals) {
        this.productSectionModals = productSectionModals;
        this.mContext = context;

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_main_adapter_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {

        final String sectionName = productSectionModals.get(i).getHeaderTitle();

        ArrayList singleSectionItems = productSectionModals.get(i).getProductItemModals();

        itemRowHolder.itemTitle.setText(sectionName);

        ProductMainSectionItemsAdapter itemListDataAdapter = new ProductMainSectionItemsAdapter(mContext, singleSectionItems);

        itemRowHolder.recyclerViewItems.setHasFixedSize(true);
        itemRowHolder.recyclerViewItems.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recyclerViewItems.setAdapter(itemListDataAdapter);

        itemRowHolder.textViewViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                String sectionName = productSectionModals.get(i).getHeaderTitle();
                ArrayList singleSectionItems = productSectionModals.get(i).getProductItemModals();

                Intent i = new Intent(mContext, ProductCatalogueViewAll.class);
                i.putExtra("Group", sectionName);
                i.putExtra("Products", singleSectionItems);
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
        return (null != productSectionModals ? productSectionModals.size() : 0);
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected RecyclerView recyclerViewItems;
        protected TextView textViewViewAll;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.textViewHeader);
            this.recyclerViewItems = (RecyclerView) view.findViewById(R.id.recyclerViewItems);
            this.textViewViewAll = (TextView) view.findViewById(R.id.textViewViewAll);

            FontUtil.applyTypeface(itemTitle, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));
            FontUtil.applyTypeface(textViewViewAll, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));

        }

    }
}
