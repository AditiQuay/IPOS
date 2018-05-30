package quay.com.ipos.productCatalogue.productCatalogueAdapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.productCatalogue.ProductDetails;
import quay.com.ipos.productCatalogue.productModal.ProductDetailModel;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.NetUtil;

/**
 * Created by niraj.kumar on 4/19/2018.
 */

public class ProductDetailsExpandableListAdapter extends BaseExpandableListAdapter {
    private ExpandableListView expandableListView;
    private Context context;
    private List<ProductDetailModel> expandableListTitle;
    private HashMap<ProductDetailModel, List<ProductDetailModel.ProductChild>> expandableListDetail;
    private Button btnCheckStatus;

    public ProductDetailsExpandableListAdapter(Context context, List<ProductDetailModel> expandableListTitle, HashMap<ProductDetailModel, List<ProductDetailModel.ProductChild>> expandableListDetail, ExpandableListView exp) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.expandableListView = exp;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_details_list_item, null);
        }
        String productHeading = expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition).getHeading();
        String productDescription = expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition).getDescription();
        boolean isCheckStock = expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition).isCheckStock();

        RelativeLayout relativeLayout = convertView.findViewById(R.id.rLayoutMain);
        RelativeLayout relativeLayout1 = convertView.findViewById(R.id.rLayout);
        RelativeLayout relativeLayout2 = convertView.findViewById(R.id.items);
        TextView subtitle = (TextView) convertView.findViewById(R.id.textViewChildName);
        btnCheckStatus = convertView.findViewById(R.id.btnCheckStatus);

        String productFinalChild = "<b>\u2022" + " " + productHeading + "</b><br/>" + productDescription;
        subtitle.setText(Html.fromHtml(productFinalChild));

        if (isCheckStock) {
            btnCheckStatus.setVisibility(View.VISIBLE);
        } else {
            btnCheckStatus.setVisibility(View.GONE);
        }
        if (!isLastChild) {
            relativeLayout1.setVisibility(View.GONE);
            relativeLayout2.setBackgroundResource(R.drawable.rectangle_gone);

        } else {
            relativeLayout1.setVisibility(View.VISIBLE);
            relativeLayout2.setBackgroundResource(R.drawable.rectangle_child_white);

        }
//        try {
//            relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.white_text_color));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        FontUtil.applyTypeface(subtitle, FontUtil.getTypeFaceRobotTiteliumRegular(context));
        FontUtil.applyTypeface(btnCheckStatus, FontUtil.getTypeFaceRobotTiteliumRegular(context));

      /*  TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);*/
//        expandableListView.setDividerHeight(20);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_detail_list_group, null);
        }

        String productTitle = expandableListTitle.get(listPosition).getProductTitle();
        String productIconURL = expandableListTitle.get(listPosition).getProductIcon();

        ImageView productIcon = (ImageView) convertView.findViewById(R.id.imageViewGroupIcon);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewGroupName);
        LinearLayout linearLayout = convertView.findViewById(R.id.relRow);
        ImageView arrowImage = convertView.findViewById(R.id.imageViewGroupArrow);
        textViewTitle.setText(productTitle);


        if (NetUtil.isNetworkAvailable(context)) {
            Picasso.get().load(productIconURL).into(productIcon);

        } else {
            Picasso.get()
                    .load(productIconURL)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(productIcon, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
        }
//        if (isExpanded && hasChild(listPosition) == 0) {
//            expandableListView.setChildDivider(context.getResources().getDrawable(R.color.white));
//        } else {
//            expandableListView.setChildDivider(context.getResources().getDrawable(R.color.white));
//        }

        if (isExpanded) {
            linearLayout.setBackgroundResource(R.drawable.rectangle_white);
            arrowImage.setImageResource(R.drawable.ic_action_arrow_down);
        } else {
            linearLayout.setBackgroundResource(R.drawable.full_rectangle);
            arrowImage.setImageResource(R.drawable.ic_action_arrow_right_blue);
        }

        FontUtil.applyTypeface(textViewTitle, FontUtil.getTypeFaceRobotTiteliumRegular(context));
        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


//    public int applyMenuBGImage(String ImageName) {
//        int imageId = 0;
//        switch (ImageName) {
//            case "Product Details":
//                imageId = R.drawable.ic_action_product_detail_blue;
//                break;
//            case "Offers & Discount":
//                imageId = R.drawable.ic_action_offer;
//                break;
//            case "Packaging & Availability":
//                imageId = R.drawable.ic_action_packaging;
//                break;
//            case "How to Use":
//                imageId = R.drawable.ic_action_help_blue;
//                break;
//        }
//        return imageId;
//    }


}
