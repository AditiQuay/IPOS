package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.productCatalogue.ProductCatalogueViewAll;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.utility.DividerItemDecoration;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;
import quay.com.ipos.utility.VerticalSpaceItemDecoration;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductMainSectionAdapter extends RecyclerView.Adapter<ProductMainSectionAdapter.ItemRowHolder> implements Filterable {
    private ArrayList<ProductSectionModal> productSectionModals;
    private Context mContext;
    private static final int VERTICAL_ITEM_SPACE = 48;

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
        final String sectionProduct = productSectionModals.get(i).getSectionProduct();

        ArrayList singleSectionItems = productSectionModals.get(i).getProductItemModals();
        itemRowHolder.itemTitle.setText(sectionName);
        itemRowHolder.textViewProductDetails.setText(sectionProduct);

        ProductMainSectionItemsAdapter itemListDataAdapter = new ProductMainSectionItemsAdapter(mContext, singleSectionItems);
        itemRowHolder.recyclerViewItems.setHasFixedSize(true);
        itemRowHolder.recyclerViewItems.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        itemRowHolder.recyclerViewItems.setAdapter(itemListDataAdapter);
        itemRowHolder.BtnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
                String sectionName = productSectionModals.get(i).getHeaderTitle();
                String sectionProductCatagory = productSectionModals.get(i).getSectionProduct();
                ArrayList singleSectionItems = productSectionModals.get(i).getProductItemModals();

                Intent i = new Intent(mContext, ProductCatalogueViewAll.class);
                i.putExtra("Group", sectionName);
                i.putExtra("sectionProduct", sectionProductCatagory);
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

    @Override
    public Filter getFilter() {
        return null;
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle, textViewProductDetails;
        protected RecyclerView recyclerViewItems;
        protected Button BtnViewAll;

        public ItemRowHolder(View view) {
            super(view);
            this.textViewProductDetails = view.findViewById(R.id.textViewProductDetails);
            this.itemTitle = (TextView) view.findViewById(R.id.textViewHeader);
            this.recyclerViewItems = (RecyclerView) view.findViewById(R.id.recyclerViewItems);
            this.BtnViewAll = (Button) view.findViewById(R.id.BtnViewAll);

            FontUtil.applyTypeface(itemTitle, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));
            FontUtil.applyTypeface(BtnViewAll, FontUtil.getTypeFaceRobotTiteliumSemiBold(mContext));

        }

    }
}
