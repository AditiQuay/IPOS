package quay.com.ipos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class NavigationViewExpeListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public NavigationViewExpeListViewAdapter(Context context, List<String> expandableListTitle,
                                             HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
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
            convertView = layoutInflater.inflate(R.layout.list_items, null);
        }
        RelativeLayout relativeLayout = convertView.findViewById(R.id.rLayoutMain);

        TextView subtitle = (TextView) convertView
                .findViewById(R.id.textViewChildName);
        subtitle.setText(expandedListText);

        try {
            relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.white_text_color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        FontUtil.applyTypeface(subtitle, FontUtil.getTypceFaceRobotoRegular(context));

      /*  TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);*/
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
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        convertView.setBackgroundResource(R.drawable.drawer_item_background);
        ImageView imageViewMenu = (ImageView) convertView.findViewById(R.id.imageViewGroupIcon);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewGroupName);
        textViewTitle.setText(listTitle);

        imageViewMenu.setBackgroundResource(applyMenuBGImage(listTitle.toString().trim()));
        FontUtil.applyTypeface(imageViewMenu, FontUtil.getTypceFaceRobotoRegular(context));

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
            case "Mostly Used":
                imageId = R.drawable.favorite_border;
                break;
            case "Billing & Cash":
                imageId = R.drawable.biling;
                break;
            case "Manage Store":
                imageId = R.drawable.store;
                break;
            case "Manage Business":
                imageId = R.drawable.business;
                break;
            case "Insights & Analytics":
                imageId = R.drawable.insights;
                break;
            case "Settings":
                imageId = R.drawable.settings;
                break;
        }


        return imageId;
    }



}
