package quay.com.ipos.productCatalogue;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.listeners.DataSheetDownloadListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.login.LoginActivity;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.CatalogueSubCatalogueFragmentAdapter;
import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueSubProduct extends RunTimePermissionActivity implements InitInterface, MyListener, DataSheetDownloadListener {
    private TextView textViewProductName, textViewProductCountTitle, textViewProductCount;
    private RecyclerView recyclerviewProduct;
    private Context mContext;
    private ArrayList<quay.com.ipos.productCatalogue.productModal.CatalogueModal> catalogueModalsSet = new ArrayList<>();
    private CatalogueSubCatalogueFragmentAdapter catalogueSubCatalogueFragmentAdapter;
    private LinearLayoutManager layoutManager;
    private String productName;
    private Toolbar toolbar;
    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    private static final int REQUEST_PERMISSIONS = 20;
    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private int clickedPosition;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_sub_product);
        Intent i = getIntent();
        //Retrieve the value
        productName = i.getStringExtra("Product Name");
        mContext = CatalogueSubProduct.this;
        findViewById();
        applyInitValues();
        applyTypeFace();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQUEST_PERMISSIONS) {
            CatalogueModal catalogueModal = catalogueModalsSet.get(clickedPosition);
            // starting new Async Task
            new DownloadFileFromURL().execute(catalogueModal.sDataSheet.trim());
        }

    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbarCatalogueSubProduct);
        textViewProductName = findViewById(R.id.textViewProductName);
        recyclerviewProduct = findViewById(R.id.recyclerviewProduct);
        textViewProductCountTitle = findViewById(R.id.textViewProductCountTitle);
        textViewProductCount = findViewById(R.id.textViewProductCount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.toolbar_title_catalogue_product_details));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        textViewProductName.setText(productName);

        layoutManager = new LinearLayoutManager(mContext);
        recyclerviewProduct.setLayoutManager(layoutManager);
        catalogueSubCatalogueFragmentAdapter = new CatalogueSubCatalogueFragmentAdapter(mContext, catalogueModalsSet, this, this);
        recyclerviewProduct.setAdapter(catalogueSubCatalogueFragmentAdapter);

        catalogueModalsSet.clear();
        getServerData();

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbar, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewProductCountTitle, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewProductCount, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

    }

    private void getServerData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "catalogue_product_details.Json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CatalogueModal catalogueModal = new CatalogueModal();
                catalogueModal.sProductName = jsonObject.getString("sProductName");
                catalogueModal.sProductFeature = jsonObject.getString("sProductFeature");
                catalogueModal.sProductPrice = jsonObject.getString("sProductPrice");
                catalogueModal.sDataSheet = jsonObject.getString("sDataSheet");
                catalogueModalsSet.add(catalogueModal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }


    @Override
    public void onRowClicked(int position) {

        CatalogueModal catalogueModal = catalogueModalsSet.get(position);
        Intent gotToProductDetail = new Intent(mContext, ProductDetails.class);
        gotToProductDetail.putExtra("ProductName", catalogueModal.sProductName);
        startActivity(gotToProductDetail);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    public void onDataSheetDownload(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CatalogueSubProduct.super.requestAppPermissions(ALL_PERMISSIONS, R.string.runtime_permissions_txt, REQUEST_PERMISSIONS,position);
            this.clickedPosition=position;
        } else {
            CatalogueModal catalogueModal = catalogueModalsSet.get(position);
            // starting new Async Task
            new DownloadFileFromURL().execute(catalogueModal.sDataSheet.trim());
        }



    }

    /**
     * Showing Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    /**
     * Background Async Task to download file
     */
    private class DownloadFileFromURL extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream("/sdcard/download/List_Top%20100_2018.db");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/List_Top%20100_2018.db");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

    }
}
