package quay.com.ipos.retailsales.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.listeners.ScannerProductListener;
import quay.com.ipos.modal.CommonParams;
import quay.com.ipos.modal.ProductListResult;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

public class FullScannerFragment extends BaseFragment implements
        ZBarScannerView.ResultHandler, FormatSelectorDialogFragment.FormatSelectorDialogListener,
        CameraSelectorDialogFragment.CameraSelectorDialogListener,ServiceTask.ServiceResultListener {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZBarScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    ScannerProductListener mScannerProductListener;
    int REQUEST_CAMERA=22;
    private boolean found=false;

    public static FullScannerFragment newInstance() {
        return new FullScannerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mScannerView = new ZBarScannerView(getActivity());

        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }
        setupFormats();

        return mScannerView;
    }

//    @Override
//    public void onCreate(Bundle state) {
//        super.onCreate(state);
//        setHasOptionsMenu(true);
//    }

//    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        MenuItem menuItem;
//
//        if(mFlash) {
//            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
//        } else {
//            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
//        }
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//
//        if(mAutoFocus) {
//            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on);
//        } else {
//            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off);
//        }
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//        menuItem = menu.add(Menu.NONE, R.id.menu_formats, 0, R.string.formats);
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//        menuItem = menu.add(Menu.NONE, R.id.menu_camera_selector, 0, R.string.select_camera);
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//    }

    public void setListener(ScannerProductListener mScannerProductListener){
        this.mScannerProductListener = mScannerProductListener;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.menu_flash:
//                mFlash = !mFlash;
//                if(mFlash) {
//                    item.setTitle(R.string.flash_on);
//                } else {
//                    item.setTitle(R.string.flash_off);
//                }
//                mScannerView.setFlash(mFlash);
//                return true;
//            case R.id.menu_auto_focus:
//                mAutoFocus = !mAutoFocus;
//                if(mAutoFocus) {
//                    item.setTitle(R.string.auto_focus_on);
//                } else {
//                    item.setTitle(R.string.auto_focus_off);
//                }
//                mScannerView.setAutoFocus(mAutoFocus);
//                return true;
//            case R.id.menu_formats:
//                DialogFragment fragment = FormatSelectorDialogFragment.newInstance(this, mSelectedIndices);
//                fragment.show(getActivity().getSupportFragmentManager(), "format_selector");
//                return true;
//            case R.id.menu_camera_selector:
//                mScannerView.stopCamera();
//                DialogFragment cFragment = CameraSelectorDialogFragment.newInstance(this, mCameraId);
//                cFragment.show(getActivity().getSupportFragmentManager(), "camera_selector");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        String cameraPermission = Manifest.permission.CAMERA;
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), cameraPermission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera(mCameraId);
            mScannerView.setFlash(mFlash);
            mScannerView.setAutoFocus(true);
            mScannerView.setShouldScaleToFill(true);
        } else {
            requestPermissions(new String[]{cameraPermission}, REQUEST_CAMERA);
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.CAMERA)
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    mScannerView.setResultHandler(this);
                    mScannerView.startCamera(mCameraId);
                    mScannerView.setFlash(mFlash);
                    mScannerView.setAutoFocus(true);
                    mScannerView.setShouldScaleToFill(true);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, true);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }


    void callScanService(String title, Context mContext){

        mProductList = IPOSApplication.mProductListResult;
        showProgressDialog(mContext,R.string.please_wait);
        CommonParams mCommonParams = new CommonParams();
        mCommonParams.setStoreId("1");
        mCommonParams.setBarCodeNumber(title);

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_ProductDetailUsingBarCode);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(mCommonParams);
        mTask.setListener(this);
        mTask.setResultType(ProductListResult.class);
        if(Util.isConnected())
            mTask.execute();
        else
            Util.showToast(getResources().getString(R.string.no_internet_connection_warning_server_error));
    }


    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        showMessageDialog("Contents = " + rawResult.getContents() + ", Format = " + rawResult.getBarcodeFormat().getName());
        callScanService(rawResult.getContents(),getActivity());
//        mainActivity.onUpdate(rawResult.getContents(), mainActivity);
//        Intent intent = new Intent(getActivity(), FullScannerFragment.class);
//        intent.putExtra("scancode",rawResult.getContents());
//        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
//        getFragmentManager().popBackStack();
        // mScannerProductListener.setProductOnListener("Contents = " + rawResult.getContents() + ", Format = " + rawResul.getBarcodeFormat().getName());
    }

//    public void showMessageDialog(String message) {
//        DialogFragment fragment = MessageDialogFragment.newInstance("Scan Results", message,"OK",null, this,3);
//        fragment.show(getActivity().getSupportFragmentManager(), "scan_results");
////        Util.showToast("Scan Results "+message,getActivity());
//    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

//    @Override
//    public void onDialogPositiveClick(DialogFragment dialog) {
//        // Resume the camera
//        mScannerView.resumeCameraPreview(this);
//    }

//    @Override
//    public void onDialogPositiveClick(DialogFragment dialog, int mCallType) {
//        // Resume the camera
//        mScannerView.resumeCameraPreview(this);
//    }
//
//    @Override
//    public void onDialogNegetiveClick(DialogFragment dialog, int mCallType) {
//
//    }

    @Override
    public void onFormatsSaved(ArrayList<Integer> selectedIndices) {
        mSelectedIndices = selectedIndices;
        setupFormats();
    }

    @Override
    public void onCameraSelected(int cameraId) {
        mCameraId = cameraId;
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < BarcodeFormat.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(BarcodeFormat.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mScannerView!=null)
            mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }
    public ArrayList<ProductListResult.Datum> mProductList= new ArrayList<>();
    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj,String response) {
        hideProgressDialog();
        // Resume the camera
        //   mScannerView.resumeCameraPreview(this);
        if (httpStatusCode == Constants.SUCCESS) {
            if(serviceUrl!=null && serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_ProductDetailUsingBarCode)) {
                if (resultObj != null) {

                    ProductListResult productListResult = (ProductListResult) resultObj;
                    if(productListResult.getData()!=null)
                        if(productListResult.getData()!=null && productListResult.getData().size()>0) {
                            if (IPOSApplication.mProductListResult.size() > 0){

                                for (int i = 0; i < mProductList.size(); i++) {

                                    if (productListResult.getData().get(0).getIProductModalId().equalsIgnoreCase(mProductList.get(i).getIProductModalId())) {
                                        ProductListResult.Datum mProductListResultData = mProductList.get(i);
                                        mProductListResultData.setQty(mProductListResultData.getQty() + 1);
                                        mProductListResultData.setAdded(true);
                                        IPOSApplication.mProductListResult.set(i, mProductListResultData);
                                        found=true;
                                    } else {


                                    }
                                }
                                if(!found){
                                    ProductListResult.Datum datum = productListResult.getData().get(0);
                                    datum.setAdded(true);
                                    datum.setQty(1);
                                    IPOSApplication.mProductListResult.add(0, datum);
                                }
                            }else {
                                ProductListResult.Datum datum = productListResult.getData().get(0);
                                datum.setAdded(true);
                                datum.setQty(1);
                                IPOSApplication.mProductListResult.add(0, datum);
                            }

                            IPOSApplication.isRefreshed=true;
                            Util.showToast(getString(R.string.product_added_successfully),getActivity());
                        }else {
                            Util.showToast(getString(R.string.product_not_found),getActivity());
                        }
                }

//                mainActivity.onUpdate((ProductListResult)resultObj,getActivity());
            }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(getActivity(), getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(getActivity(), getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(getActivity(), getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(getActivity(), getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(getActivity(), getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }



        mScannerView.resumeCameraPreview(this);

        Intent intent = new Intent("CUSTOM_ACTION");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        try {
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }catch (Exception e){

        }
    }

    void onResumeCamera(){
        if(mScannerView!=null)
            mScannerView.resumeCameraPreview(this);
    }

}
