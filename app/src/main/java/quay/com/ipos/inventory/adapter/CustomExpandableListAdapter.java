package quay.com.ipos.inventory.adapter;

/**
 * Created by ankush.bansal on 12-06-2018.
 */

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
import quay.com.ipos.realmbean.RealmBusinessPlaces;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
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
        String listTitle = (String) getGroup(listPosition);
        convertView = null;
        if (listTitle.contains("PO#")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.po_details, null);


            }
        } else if (listTitle.equalsIgnoreCase("Items details | 4 Items| 23 Qty")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.item_detail_po, null);

                ArrayList<RealmBusinessPlaces> discounts = new ArrayList<>();
                RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("5");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("3");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("3");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("10");
                discounts.add(realmBusinessPlaces1);
                RecyclerView recycler_view = convertView.findViewById(R.id.recycler_view);
                recycler_view.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(mLayoutManager);
                ItemsDetailsPOListAdapter itemListDataAdapter = new ItemsDetailsPOListAdapter(context, discounts);
                recycler_view.setAdapter(itemListDataAdapter);
            }
        } else if (listTitle.equalsIgnoreCase("INCO Terms")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_inco_terms_item, null);
                ArrayList<RealmBusinessPlaces> discounts = new ArrayList<>();
                RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Loading");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Shipping");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Unload");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Toll");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("E-Way Bill");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Unload 1");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Total");
                discounts.add(realmBusinessPlaces1);
                RecyclerView recycler_view = convertView.findViewById(R.id.recycler_view);
                recycler_view.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(mLayoutManager);
                INCOTermsPOListAdapter itemListDataAdapter = new INCOTermsPOListAdapter(context, discounts);
                recycler_view.setAdapter(itemListDataAdapter);
            }
        } else if (listTitle.equalsIgnoreCase("Payment Terms")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.payment_terms_items, null);

                ArrayList<RealmBusinessPlaces> discounts = new ArrayList<>();
                RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Advance");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("On Delivery");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("After Delivery");
                discounts.add(realmBusinessPlaces1);
                RecyclerView recycler_view = convertView.findViewById(R.id.recycler_view);
                recycler_view.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(mLayoutManager);
                MilestonePOListAdapter itemListDataAdapter = new MilestonePOListAdapter(context, discounts);
                recycler_view.setAdapter(itemListDataAdapter);

            }
        } else if (listTitle.equalsIgnoreCase("Terms & Condition")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_po_items, null);
                ArrayList<RealmBusinessPlaces> discounts = new ArrayList<>();
                RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("1. Lorem Ipsum is simply dummy text of the printing and typesetting industry");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("2. Lorem Ipsum is simply dummy text of the printing and typesetting industry");
                discounts.add(realmBusinessPlaces1);
                RecyclerView recycler_view = convertView.findViewById(R.id.recycler_view);
                recycler_view.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(mLayoutManager);
                TermsPOListAdapter itemListDataAdapter = new TermsPOListAdapter(context, discounts);
                recycler_view.setAdapter(itemListDataAdapter);
            }
        } else if (listTitle.equalsIgnoreCase("Attachments")) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_po_items, null);

                ArrayList<RealmBusinessPlaces> discounts = new ArrayList<>();
                RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("PO Copy");
                discounts.add(realmBusinessPlaces1);
                realmBusinessPlaces1 = new RealmBusinessPlaces();
                realmBusinessPlaces1.setHeader("Batch Details");
                discounts.add(realmBusinessPlaces1);
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
            convertView = layoutInflater.inflate(R.layout.inventory_group_item, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.textViewGroupName);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
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
}