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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.enums.ProductCatalogueEnum;
import quay.com.ipos.listeners.DataSheetDownloadListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.CatalogueSubCatalogueFragmentAdapter;
import quay.com.ipos.productCatalogue.productCatalogueHelper.ProductCatalogueUtils;
import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
import quay.com.ipos.productCatalogue.productModal.CatalogueServerModel;
import quay.com.ipos.productCatalogue.productModal.ProductCatalogueServerModal;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueSubProduct extends RunTimePermissionActivity implements InitInterface, MyListener, DataSheetDownloadListener, ServiceTask.ServiceResultListener {
    private static final String TAG = CatalogueSubProduct.class.getSimpleName();
    private TextView textViewProductName, textViewProductCountTitle, textViewProductCount;
    private RecyclerView recyclerviewProduct;
    private Context mContext;
    private ArrayList<CatalogueModal> catalogueModalsSet = new ArrayList<>();
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
        mContext = CatalogueSubProduct.this;
        Intent i = getIntent();
        productName = i.getStringExtra("ProductName");
        findViewById();
        applyInitValues();
        applyTypeFace();
    }

    private void getProductList() {
        showProgress("Loading please wait...");

        int storeId = SharedPrefUtil.getStoreId(Constants.STORE_ID.trim(), 0, mContext);
        AppLog.e(TAG, "StoreId" + storeId);

        CatalogueModal catalogueSubProductParam = new CatalogueModal();
        catalogueSubProductParam.setCompanyName("Quay");
        catalogueSubProductParam.setProductId("1");
        catalogueSubProductParam.setStoreID(String.valueOf(storeId));

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL.trim());
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_PRODUCT_DETAIL.trim());
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(catalogueSubProductParam);
        mTask.setListener(this);
        mTask.setResultType(CatalogueServerModel.class);
        mTask.execute();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQUEST_PERMISSIONS) {
            CatalogueModal catalogueModal = catalogueModalsSet.get(clickedPosition);
            // starting new Async Task
            new DownloadFileFromURL().execute(catalogueModal.getsDataSheet().trim());
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
        if (ProductCatalogueUtils.getProductDetail(mContext) != null) {
            String productCount = SharedPrefUtil.getString(Constants.PREF_KEY_PRODUCT_COUNT.trim(), "", mContext);
            textViewProductCount.setText(productCount);

            catalogueSubCatalogueFragmentAdapter = new CatalogueSubCatalogueFragmentAdapter(mContext, ProductCatalogueUtils.getProductDetail(mContext), this, this);
            recyclerviewProduct.setAdapter(catalogueSubCatalogueFragmentAdapter);
        }
        getProductList();

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbar, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewProductCountTitle, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewProductCount, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

    }

    private void getServerData(String response) {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(response);
            int count = jsonObjMain.optInt(ProductCatalogueEnum.count.toString());
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray(ProductCatalogueEnum.productData.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CatalogueModal catalogueModal2 = new CatalogueModal();
                catalogueModal2.setProductCode(jsonObject.optString(ProductCatalogueEnum.productCode.toString()));
                catalogueModal2.setsProductName(jsonObject.optString(ProductCatalogueEnum.sProductName.toString()));
                catalogueModal2.setsProductUrl(jsonObject.optString(ProductCatalogueEnum.sProductUrl.toString()));
                catalogueModal2.setsProductFeature(jsonObject.optString(ProductCatalogueEnum.sProductFeature.toString()));
                catalogueModal2.setsProductPrice(jsonObject.optString(ProductCatalogueEnum.sProductPrice.toString()));
                catalogueModal2.setsDataSheet(jsonObject.optString(ProductCatalogueEnum.sDataSheet.toString()));
                catalogueModal2.setsPoints(jsonObject.optString(ProductCatalogueEnum.sPoints.toString()));
                catalogueModal2.setIsOnOffer(jsonObject.optBoolean(ProductCatalogueEnum.isOnOffer.toString()));
                catalogueModal2.setIsCalculator(jsonObject.optBoolean(ProductCatalogueEnum.isCalculator.toString()));
                catalogueModal2.setIsDataSheet(jsonObject.optBoolean(ProductCatalogueEnum.isDataSheet.toString()));
                catalogueModalsSet.add(catalogueModal2);
            }

            ProductCatalogueUtils.saveProductDetail(mContext, catalogueModalsSet);

            SharedPrefUtil.putString(Constants.PREF_KEY_PRODUCT_COUNT.trim(), String.valueOf(count), mContext);
            String productCount = SharedPrefUtil.getString(Constants.PREF_KEY_PRODUCT_COUNT.trim(), "", mContext);

            textViewProductCount.setText(productCount);
            catalogueSubCatalogueFragmentAdapter = new CatalogueSubCatalogueFragmentAdapter(mContext, ProductCatalogueUtils.getProductDetail(mContext), this, this);
            recyclerviewProduct.setAdapter(catalogueSubCatalogueFragmentAdapter);


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
        gotToProductDetail.putExtra("ProductName", catalogueModal.getsProductName());
        gotToProductDetail.putExtra("ProductId", catalogueModal.getProductCode());
        startActivity(gotToProductDetail);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    public void onDataSheetDownload(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CatalogueSubProduct.super.requestAppPermissions(ALL_PERMISSIONS, R.string.runtime_permissions_txt, REQUEST_PERMISSIONS, position);
            this.clickedPosition = position;
        } else {
            CatalogueModal catalogueModal = catalogueModalsSet.get(position);
            if (!TextUtils.isEmpty(catalogueModal.getsDataSheet().trim())) {
                // starting new Async Task
                new DownloadFileFromURL().execute(catalogueModal.getsDataSheet().trim());
            } else {
                Toast.makeText(mContext, "No attachment found !", Toast.LENGTH_SHORT).show();
            }

        }


    }

    @Override
    public void onCartBtnClick(int position) {
        CatalogueModal catalogueModal = catalogueModalsSet.get(position);
        Intent i = new Intent(mContext, ProductRangeActivity.class);
        i.putExtra("ProductCode", catalogueModal.getProductCode());
        startActivity(i);
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

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        dismissProgress();
        if (httpStatusCode == Constants.SUCCESS) {
            if (resultObj != null) {
                ProductCatalogueUtils.clearProductDetail(mContext);
                SharedPrefUtil.remove(Constants.PREF_KEY_PRODUCT_COUNT.trim(), mContext);
                if (catalogueModalsSet.size() > 0) {
                    catalogueModalsSet.clear();
                }
                getServerData(serverResponse);
            }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
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
