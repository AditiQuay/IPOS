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

import java.util.HashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 4/19/2018.
 */

public class ProductDetailsExpandableListAdapter extends BaseExpandableListAdapter {
    private ExpandableListView expandableListView;
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private Button btnCheckStatus;

    public ProductDetailsExpandableListAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<String>> expandableListDetail, ExpandableListView exp) {
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
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_details_list_item, null);
        }
        RelativeLayout relativeLayout = convertView.findViewById(R.id.rLayoutMain);

        btnCheckStatus = convertView.findViewById(R.id.btnCheckStatus);
        if (expandedListText.equalsIgnoreCase("• Aerosal can 750ml")) {
            btnCheckStatus.setVisibility(View.VISIBLE);
        } else {
            btnCheckStatus.setVisibility(View.GONE);
        }

        TextView subtitle = (TextView) convertView
                .findViewById(R.id.textViewChildName);
        subtitle.setText(Html.fromHtml(expandedListText));
        try {
            relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.white_text_color));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_detail_list_group, null);
        }

        ImageView imageViewMenu = (ImageView) convertView.findViewById(R.id.imageViewGroupIcon);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewGroupName);
        LinearLayout linearLayout = convertView.findViewById(R.id.relRow);
        ImageView arrowImage = convertView.findViewById(R.id.imageViewGroupArrow);

        textViewTitle.setText(listTitle);
        imageViewMenu.setBackgroundResource(applyMenuBGImage(listTitle.toString().trim()));

        if (isExpanded){
            linearLayout.setBackgroundResource(R.drawable.rectangle_white);
            arrowImage.setImageResource(R.drawable.ic_action_arrow_down);
        }else {
            linearLayout.setBackgroundResource(R.drawable.full_rectangle);
            arrowImage.setImageResource(R.drawable.ic_action_arrow_right_blue);
        }

        FontUtil.applyTypeface(imageViewMenu, FontUtil.getTypeFaceRobotTiteliumRegular(context));
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


    public int applyMenuBGImage(String ImageName) {
        int imageId = 0;
        switch (ImageName) {
            case "Product Details":
                imageId = R.drawable.ic_action_product_detail_blue;
                break;
            case "Offers & Discount":
                imageId = R.drawable.ic_action_offer;
                break;
            case "Packaging & Availability":
                imageId = R.drawable.ic_action_packaging;
                break;
            case "How to Use":
                imageId = R.drawable.ic_action_help_blue;
                break;
        }
        return imageId;
    }


}
