package quay.com.ipos.productCatalogue;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.ProductCatalogueModal;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductCatalogueMainFragmentAdapter;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class ProductCatalogueMainFragment extends BaseFragment implements InitInterface, MyListener {
    private Context mContext;
    private View rootView;
    private SearchView searchViewCatalogue;
    private TextView textViewHeader1, textViewHeader2, textViewHeader3;
    private TextView textViewViewAll1, textViewViewAll2, textViewViewAll3;
    private ArrayList<ProductCatalogueModal> productCatalogueModalsSet = new ArrayList<>();
    private RecyclerView recyclerviewCategory1, recyclerviewCategory2, recyclerviewCategory3;
    private ProductCatalogueMainFragmentAdapter productCatalogueMainFragmentAdapter;
    private MyListener myListener;
    Dialog myDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.product_catalogue_fragment, container, false);
        mContext = getActivity();
        myDialog = new Dialog(mContext);
        setHasOptionsMenu(true);
        myListener = ProductCatalogueMainFragment.this;
        findViewById();
        applyInitValues();
//        showPopup(rootView);
        return rootView;
    }

    //    public void showPopup(View v) {
//        TextView txtclose;
//        myDialog.setContentView(R.layout.custompopup);
//        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
//        txtclose.setText("X");
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void findViewById() {
        searchViewCatalogue = rootView.findViewById(R.id.searchViewCatalogue);
        textViewHeader1 = rootView.findViewById(R.id.textViewHeader1);
        textViewHeader2 = rootView.findViewById(R.id.textViewHeader2);
        textViewHeader3 = rootView.findViewById(R.id.textViewHeader3);

        textViewViewAll1 = rootView.findViewById(R.id.textViewViewAll1);
        textViewViewAll2 = rootView.findViewById(R.id.textViewViewAll2);
        textViewViewAll3 = rootView.findViewById(R.id.textViewViewAll3);

        recyclerviewCategory1 = rootView.findViewById(R.id.recyclerviewCategory1);
        recyclerviewCategory2 = rootView.findViewById(R.id.recyclerviewCategory2);
        recyclerviewCategory3 = rootView.findViewById(R.id.recyclerviewCategory3);
    }

    private void getServerData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "product_catalogue.Json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProductCatalogueModal productCatalogueModal = new ProductCatalogueModal();
                productCatalogueModal.productName = jsonObject.getString("sProductName");
                productCatalogueModalsSet.add(productCatalogueModal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyInitValues() {
        getServerData();

        recyclerviewCategory1.setHasFixedSize(true);
        recyclerviewCategory1.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.HORIZONTAL, false));
        productCatalogueMainFragmentAdapter = new ProductCatalogueMainFragmentAdapter(mContext, myListener, productCatalogueModalsSet);
        recyclerviewCategory1.setAdapter(productCatalogueMainFragmentAdapter);

        recyclerviewCategory2.setHasFixedSize(true);
        recyclerviewCategory2.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.HORIZONTAL, false));
        productCatalogueMainFragmentAdapter = new ProductCatalogueMainFragmentAdapter(mContext, myListener, productCatalogueModalsSet);
        recyclerviewCategory2.setAdapter(productCatalogueMainFragmentAdapter);

        recyclerviewCategory3.setHasFixedSize(true);
        recyclerviewCategory3.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.HORIZONTAL, false));
        productCatalogueMainFragmentAdapter = new ProductCatalogueMainFragmentAdapter(mContext, myListener, productCatalogueModalsSet);
        recyclerviewCategory3.setAdapter(productCatalogueMainFragmentAdapter);


    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewHeader1, FontUtil.getTypceFaceRobotoMedium_0(mContext));
        FontUtil.applyTypeface(textViewHeader2, FontUtil.getTypceFaceRobotoMedium_0(mContext));
        FontUtil.applyTypeface(textViewHeader3, FontUtil.getTypceFaceRobotoMedium_0(mContext));

        FontUtil.applyTypeface(textViewViewAll1, FontUtil.getTypceFaceRobotoRegular(mContext));
        FontUtil.applyTypeface(textViewViewAll2, FontUtil.getTypceFaceRobotoRegular(mContext));
        FontUtil.applyTypeface(textViewViewAll3, FontUtil.getTypceFaceRobotoRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onRowClicked(int position) {
        ProductCatalogueModal productCatalogueModal = productCatalogueModalsSet.get(position);
//        Intent i = new Intent(getActivity(), CatalogueSubProduct.class);
//        i.putExtra("Prodcut Name", productCatalogueModal.productName);
//        startActivity(i);

        // Create new fragment and transaction
        Fragment newFragment = new CatalogueSubProduct();
        Bundle args = new Bundle();
        args.putString("Product Name", productCatalogueModal.productName);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

}
