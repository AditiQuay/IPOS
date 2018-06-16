package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.POAttachments;
import quay.com.ipos.inventory.modal.POIncoTerms;
import quay.com.ipos.realmbean.RealmBusinessPlaces;

/**
 * Created by niraj.kumar on 6/14/2018.
 */

public class CustomGrnTermsExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public CustomGrnTermsExpandableListAdapter(Context context, List<String> expandableListTitle,
                                               HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.inventory_group_item, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.textViewGroupName);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        final String expandedListText = (String) getChild(listPosition, childPosition);
        String listTitle = (String) getGroup(listPosition);
        convertView = null;
        if (listTitle.contains("GRN")) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grn_qty, null);


        } else if (listTitle.contains("Transporter")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.transporter, null);


            }
        } else if (listTitle.equalsIgnoreCase("Item details")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.items_details, null);


            }
        } else if (listTitle.equalsIgnoreCase("INCO Terms")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_inco_terms_item, null);
                ArrayList<POIncoTerms> discounts = new ArrayList<>();
                RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Loading");

                RecyclerView recycler_view = convertView.findViewById(R.id.recycler_view);
                recycler_view.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(mLayoutManager);
                INCOTermsPOListAdapter itemListDataAdapter = new INCOTermsPOListAdapter(context, discounts);
                recycler_view.setAdapter(itemListDataAdapter);
            }
        } else if (listTitle.equalsIgnoreCase("Attachments")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_po_items, null);

                ArrayList<POAttachments> discounts = new ArrayList<>();
                RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("PO Copy");
             /*   discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Batch Details");
                discounts.add(realmBusinessPlaces1);*/
                RecyclerView recycler_view = convertView.findViewById(R.id.recycler_view);
                recycler_view.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(mLayoutManager);
                AttachmentsPOListAdapter itemListDataAdapter = new AttachmentsPOListAdapter(context, discounts);
                recycler_view.setAdapter(itemListDataAdapter);
            }
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
