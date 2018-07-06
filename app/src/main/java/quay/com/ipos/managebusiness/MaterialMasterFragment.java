package quay.com.ipos.managebusiness;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.managebusiness.adapter.SpinnerDropDownAdapter;
import quay.com.ipos.modal.CustomerPointsRedeemResult;
import quay.com.ipos.modal.FieldModel;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.modal.ManageMaterialMasterModel;
import quay.com.ipos.modal.RetailOrderCenterListResult;
import quay.com.ipos.modal.RetailOrderCentreRequest;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Base64Util;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 05-07-2018.
 */

public class MaterialMasterFragment extends BaseFragment implements InitInterface , View.OnClickListener, MessageDialog.MessageDialogListener, ServiceTask.ServiceResultListener{


    private View rootView;
    private TextInputEditText tieMaterialCode,tieDescription,tieDisplayName,tieHSNCode,tieBasicPrice,tieNRV,tieGPL,tieMRP,tieGrossWt,tieNetWt,tieDimensionL,tieDimensionB,tieDimensionH,tieBarCodeNumber,tieShelfLifeInDays;
    private TextInputLayout tilMaterialCode,tilDescription,tilDisplayName,tilHSNCode,tilBasicPrice,tilNRV,tilGPL,tilMRP,tilGrossWt,tilNetWt,tilDimensionL,tilDimensionB,tilDimensionH,tilBarCodeNumber,tilShelfLifeInDays;
    private TextView tvUploadName,tvUpload;
    private Button btnFullFragmentCancel,btnFullFragmentSubmit;
    private MaterialSpinner spProductCode,spMaterialTypeID,spBasicUOM,spSaleUOM,spIsRedeemAllow,spIsShelfLife;
    private ArrayList<FieldModel> materialCategory = new ArrayList<>();
    private ArrayList<FieldModel> basicUOM = new ArrayList<>();
    private ArrayList<FieldModel> saleUOM = new ArrayList<>();
    private ArrayList<FieldModel> productCode = new ArrayList<>();
    Context mContext;
    ManageMaterialMasterModel manageMaterialMasterModel;
    String MaterialCode, Description, DisplayName,BasicUOM,SaleUOM,HSNCode,ProductCode,MaterialTypeID,LoyaltyTableRef="ProductBaseLoyalty",BarCodeNumber,ShelfLifeInDays,MaterialClass="1",PurchaseUOM="null";
    String imageBaseString="" ,IsActive="1",IsMaterialSerial="0",IsMaterialBatch="1";
    String imageType ="";
    String  employeeCode ="";
    String businessPlaceCode ="";
    double GrossWt=0,NetWt=0,ConversionMultiplier=0,DimensionL=0,DimensionB=0,DimensionH=0,basicPrice=0,nrv=0,gpl=0,mrp=0;
    boolean IsRedeemAllow=true,IsShelfLife=false;

    private void checkSubmit() {
        try {

            ProductCode = String.valueOf(spProductCode.getSelectedItem());
            if(!ProductCode.equalsIgnoreCase("null")){
                ProductCode = productCode.get(spProductCode.getSelectedItemPosition()-1).getDbField();
            }

            BasicUOM = String.valueOf(spBasicUOM.getSelectedItem());
            if(!BasicUOM.equalsIgnoreCase("null")){
                BasicUOM = basicUOM.get(spBasicUOM.getSelectedItemPosition()-1).getDbField();
            }

            SaleUOM = String.valueOf(spSaleUOM.getSelectedItem());
            if(!SaleUOM.equalsIgnoreCase("null")){
                SaleUOM = saleUOM.get(spSaleUOM.getSelectedItemPosition()-1).getDbField();
            }

            MaterialTypeID = String.valueOf(spMaterialTypeID.getSelectedItem());
            if(!MaterialTypeID.equalsIgnoreCase("null")){
                MaterialTypeID = materialCategory.get(spMaterialTypeID.getSelectedItemPosition()-1).getFrontField();
            }
        }catch (Exception e){

        }
        HSNCode = tieHSNCode.getText().toString().trim();
        BarCodeNumber = tieBarCodeNumber.getText().toString().trim();
        ShelfLifeInDays = tieShelfLifeInDays.getText().toString().trim();
        MaterialCode = tieMaterialCode.getText().toString().trim();
        Description = tieDescription.getText().toString().trim();
        DisplayName = tieDisplayName.getText().toString().trim();
        try {
            GrossWt = Double.parseDouble(tieGrossWt.getText().toString());
        }catch (Exception e){

        }
        try {
            NetWt = Double.parseDouble(tieNetWt.getText().toString());
        }catch (Exception e){

        }
        try {
            DimensionL = Double.parseDouble(tieDimensionL.getText().toString());
        }catch (Exception e){

        }
        try {
            DimensionB = Double.parseDouble(tieDimensionB.getText().toString());
        }catch (Exception e){

        }
        try {
            DimensionH = Double.parseDouble(tieDimensionH.getText().toString());
        }catch (Exception e){

        }
        try {
            basicPrice = Double.parseDouble(tieBasicPrice.getText().toString());
        }catch (Exception e){

        }
        try {
            nrv = Double.parseDouble(tieNRV.getText().toString());
        }catch (Exception e){

        }
        try {
            gpl = Double.parseDouble(tieGPL.getText().toString());
        }catch (Exception e){

        }
        try {
            mrp = Double.parseDouble(tieMRP.getText().toString());
        }catch (Exception e){

        }
        try {
            if(String.valueOf(spIsRedeemAllow.getSelectedItem()).equalsIgnoreCase("Yes")){
                IsRedeemAllow = true;
            }else IsRedeemAllow = false;

            if(String.valueOf(spIsShelfLife.getSelectedItem()).equalsIgnoreCase("Yes")){
                IsShelfLife = true;
            }else IsShelfLife = false;
        }catch (Exception e){

        }
        boolean isFail = false;
        if (MaterialCode.trim().equalsIgnoreCase("") || !Util.validateString(MaterialCode)) {
            isFail = true;
            tilMaterialCode.setErrorEnabled(true);
            tieMaterialCode.setError(getResources().getString(R.string.invalid_material_code));
        }
        if (ProductCode.trim().equalsIgnoreCase("null")) {
            isFail = true;
            spProductCode.setEnableErrorLabel(true);
            spProductCode.setError(getResources().getString(R.string.invalid_product_code));
        }
        if (TextUtils.isEmpty(Description)) {
            isFail = true;
            tilDescription.setErrorEnabled(true);
            tieDescription.setError(getResources().getString(R.string.invalid_description));
        }
        if (TextUtils.isEmpty(DisplayName)) {
            isFail = true;
            tilDisplayName.setErrorEnabled(true);
            tieDisplayName.setError(getResources().getString(R.string.invalid_display_name));
        }
        if (TextUtils.isEmpty(HSNCode)) {
            isFail = true;
            tilHSNCode.setErrorEnabled(true);
            tieHSNCode.setError(getResources().getString(R.string.invalid_hsn));
        }
        if (basicPrice==0) {
            isFail = true;
            tilBasicPrice.setErrorEnabled(true);
            tieBasicPrice.setError(getResources().getString(R.string.invalid_basic_price));
        }
        if (GrossWt==0) {
            isFail = true;
            tilGrossWt.setErrorEnabled(true);
            tieGrossWt.setError(getResources().getString(R.string.invalid_gross_wt));
        }
        if (BasicUOM.trim().equalsIgnoreCase("null")) {
            isFail = true;
            spBasicUOM.setEnableErrorLabel(true);
            spBasicUOM.setError(getResources().getString(R.string.invalid_basic_uom));
        }

        if (SaleUOM.trim().equalsIgnoreCase("null")) {
            isFail = true;
            spSaleUOM.setEnableErrorLabel(true);
            spSaleUOM.setError(getResources().getString(R.string.invalid_sale_uom));
        }


//        if(imageBaseString.equalsIgnoreCase("")){
//            isFail = true;
//            imageType = "";
//            Util.showToast("Upload image",mContext);
//        }

        if (!isFail) {
            spSaleUOM.setEnableErrorLabel(false);
            spBasicUOM.setEnableErrorLabel(false);
            spProductCode.setEnableErrorLabel(false);
            tilMaterialCode.setErrorEnabled(false);
            tilGrossWt.setErrorEnabled(false);
            tilBasicPrice.setErrorEnabled(false);
            tilHSNCode.setErrorEnabled(false);
            tilDisplayName.setErrorEnabled(false);
            tilDescription.setErrorEnabled(false);
            imageType = "JPEG";
            tvUploadName.setText("Image ready to upload");
            callAPI();
        } else {
            Toast.makeText(mContext, "Enter all required fields", Toast.LENGTH_SHORT).show();
        }
    }
    LoginResult loginResult;
    private void callAPI() {
        loginResult = Util.getCustomGson().fromJson(SharedPrefUtil.getString(Constants.Login_result,"",getActivity()),LoginResult.class);
        manageMaterialMasterModel = new ManageMaterialMasterModel();
        manageMaterialMasterModel.setBusinessPlaceCode(loginResult.getUserAccess().getWorklocationID()+"");
        manageMaterialMasterModel.setEmployeeCode(loginResult.getUserAccess().getEmpCode());
        manageMaterialMasterModel.setBarCodeNumber(BarCodeNumber);
        manageMaterialMasterModel.setBasicPrice(basicPrice);
        manageMaterialMasterModel.setBasicUOM(BasicUOM);
        manageMaterialMasterModel.setConversionMultiplier(ConversionMultiplier);
        manageMaterialMasterModel.setDescription(Description);
        manageMaterialMasterModel.setDimensionB(DimensionB);
        manageMaterialMasterModel.setDimensionL(DimensionL);
        manageMaterialMasterModel.setDimensionH(DimensionH);
        manageMaterialMasterModel.setDisplayName(DisplayName);
        manageMaterialMasterModel.setGpl(gpl);
        manageMaterialMasterModel.setNrv(nrv);
        manageMaterialMasterModel.setMrp(mrp);
        manageMaterialMasterModel.setGrossWt(GrossWt);
        manageMaterialMasterModel.setHSNCode(HSNCode);
        manageMaterialMasterModel.setImageBaseString(imageBaseString);
        manageMaterialMasterModel.setImageID(1);
        manageMaterialMasterModel.setImageType(imageType);
        manageMaterialMasterModel.setIsActive(IsActive);
        manageMaterialMasterModel.setIsMaterialBatch(IsMaterialBatch);
        manageMaterialMasterModel.setIsMaterialSerial(IsMaterialSerial);
        if(IsRedeemAllow)
            manageMaterialMasterModel.setIsRedeemAllow("1");
        else
            manageMaterialMasterModel.setIsRedeemAllow("0");
        if(IsShelfLife)
            manageMaterialMasterModel.setIsShelfLife("1");
        else
            manageMaterialMasterModel.setIsShelfLife("0");
        manageMaterialMasterModel.setLoyaltyTableRef(LoyaltyTableRef);
        manageMaterialMasterModel.setMaterialClass(MaterialClass);
        manageMaterialMasterModel.setMaterialCode(MaterialCode);
        manageMaterialMasterModel.setMaterialTypeID(MaterialTypeID);
        manageMaterialMasterModel.setNetWt(NetWt);
        manageMaterialMasterModel.setProductCode(ProductCode);
        manageMaterialMasterModel.setPurchaseUOM(PurchaseUOM);
        manageMaterialMasterModel.setSaleUOM(SaleUOM);
        manageMaterialMasterModel.setShelfLifeInDays(ShelfLifeInDays);
        AppLog.e(MaterialMasterFragment.class.getSimpleName(),Util.getCustomGson().toJson(manageMaterialMasterModel));
        showProgressDialog(R.string.please_wait);
        ServiceTask mServiceTask = new ServiceTask();
        mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_CreateMaterialMaster);
        mServiceTask.setParamObj(manageMaterialMasterModel);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultType(CustomerPointsRedeemResult.class);
        mServiceTask.execute();
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.material_master_layout, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyTypeFace();
        return rootView;
    }
    private void initializeComponent(View rootView) {
        tieMaterialCode = rootView.findViewById(R.id.tieMaterialCode);
        tieDescription = rootView.findViewById(R.id.tieDescription);
        tieDisplayName = rootView.findViewById(R.id.tieDisplayName);
        tieBasicPrice = rootView.findViewById(R.id.tieBasicPrice);
        tieDimensionL = rootView.findViewById(R.id.tieDimensionL);
        tieDimensionB = rootView.findViewById(R.id.tieDimensionB);
        tieDimensionH = rootView.findViewById(R.id.tieDimensionH);
        tieBarCodeNumber = rootView.findViewById(R.id.tieBarCodeNumber);
        tieShelfLifeInDays = rootView.findViewById(R.id.tieShelfLifeInDays);
        tieNRV = rootView.findViewById(R.id.tieNRV);
        tieMRP = rootView.findViewById(R.id.tieMRP);
        tieGPL = rootView.findViewById(R.id.tieGPL);
        tieHSNCode = rootView.findViewById(R.id.tieHSNCode);
        spProductCode = rootView.findViewById(R.id.spProductCode);
        spMaterialTypeID = rootView.findViewById(R.id.spMaterialTypeID);
        spBasicUOM = rootView.findViewById(R.id.spBasicUOM);
        spSaleUOM = rootView.findViewById(R.id.spSaleUOM);
        spIsShelfLife = rootView.findViewById(R.id.spIsShelfLife);
        spIsRedeemAllow = rootView.findViewById(R.id.spIsRedeemAllow);
        btnFullFragmentSubmit = rootView.findViewById(R.id.btnFullFragmentSubmit);
        btnFullFragmentCancel = rootView.findViewById(R.id.btnFullFragmentCancel);
        tvUpload = rootView.findViewById(R.id.tvUpload);
        tvUploadName = rootView.findViewById(R.id.tvUploadName);
        tilNetWt = rootView.findViewById(R.id.tilNetWt);
        tilGrossWt = rootView.findViewById(R.id.tilGrossWt);
        tieGrossWt = rootView.findViewById(R.id.tieGrossWt);
        tieNetWt = rootView.findViewById(R.id.tieNetWt);
        tilMaterialCode = rootView.findViewById(R.id.tilMaterialCode);
        tilDescription = rootView.findViewById(R.id.tilDescription);
        tilDisplayName = rootView.findViewById(R.id.tilDisplayName);
        tilBasicPrice = rootView.findViewById(R.id.tilBasicPrice);
        tilDimensionL = rootView.findViewById(R.id.tilDimensionL);
        tilDimensionB = rootView.findViewById(R.id.tilDimensionB);
        tilDimensionH = rootView.findViewById(R.id.tilDimensionH);
        tilBarCodeNumber = rootView.findViewById(R.id.tilBarCodeNumber);
        tilShelfLifeInDays = rootView.findViewById(R.id.tilShelfLifeInDays);
        tilNRV = rootView.findViewById(R.id.tilNRV);
        tilMRP = rootView.findViewById(R.id.tilMRP);
        tilGPL = rootView.findViewById(R.id.tilGPL);
        tilHSNCode = rootView.findViewById(R.id.tilHSNCode);
        tvUpload.setOnClickListener(this);
        btnFullFragmentCancel.setOnClickListener(this);
        btnFullFragmentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSubmit();
            }
        });
    }

    @Override
    public void findViewById() {
        initializeComponent(rootView);

    }

    @Override
    public void applyInitValues() {
        setAdapter();
    }

    private void setAdapter() {
        FieldModel fieldModel = new FieldModel();
        fieldModel.setDbField("EQUIP");
        fieldModel.setFrontField("Agri Equipment");
        materialCategory.add(fieldModel);
        fieldModel.setDbField("CHEALTH");
        fieldModel.setFrontField("Crop Health");
        materialCategory.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("FUNGI");
        fieldModel.setFrontField("Fungicide");
        materialCategory.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("HERBI");
        fieldModel.setFrontField("Herbicide");
        materialCategory.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("INSECT");
        fieldModel.setFrontField("Insecticide");
        materialCategory.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("SEEDS");
        fieldModel.setFrontField("Seeds");
        materialCategory.add(fieldModel);
        SpinnerDropDownAdapter mSpinnerDropDownAdapter = new SpinnerDropDownAdapter(getActivity(),materialCategory);
        spMaterialTypeID.setAdapter(mSpinnerDropDownAdapter);

        fieldModel = new FieldModel();
        fieldModel.setDbField("KG ");
        fieldModel.setFrontField("Kilogram");
        basicUOM.add(0,fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("PC");
        fieldModel.setFrontField("Piece");
        basicUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("L");
        fieldModel.setFrontField("Liter");
        basicUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("BOX");
        fieldModel.setFrontField("BOX");
        basicUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("DR");
        fieldModel.setFrontField("Drum");
        basicUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("BAG");
        fieldModel.setFrontField("Bag");
        basicUOM.add(fieldModel);
        SpinnerDropDownAdapter mSpinnerDropDownAdapter1 = new SpinnerDropDownAdapter(getActivity(),basicUOM);
        spBasicUOM.setAdapter(mSpinnerDropDownAdapter1);

        fieldModel = new FieldModel();
        fieldModel.setDbField("KG ");
        fieldModel.setFrontField("Kilogram");
        saleUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("GM");
        fieldModel.setFrontField("Gram");
        saleUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("PC");
        fieldModel.setFrontField("Piece");
        saleUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("L");
        fieldModel.setFrontField("Liter");
        saleUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("ML");
        fieldModel.setFrontField("Millilitre");
        saleUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("BOX");
        fieldModel.setFrontField("BOX");
        saleUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("DR");
        fieldModel.setFrontField("Drum");
        saleUOM.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("BAG");
        fieldModel.setFrontField("Bag");
        saleUOM.add(fieldModel);
        SpinnerDropDownAdapter mSpinnerDropDownAdapter2 = new SpinnerDropDownAdapter(getActivity(),saleUOM);
        spSaleUOM.setAdapter(mSpinnerDropDownAdapter2);

        fieldModel = new FieldModel();
        fieldModel.setDbField("ACETOXY1 ");
        fieldModel.setFrontField("Abacin");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("ACETOXY2");
        fieldModel.setFrontField("Bavistin small");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("APPLICATORS2");
        fieldModel.setFrontField("Bavistin Large");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("APPLICATORS1");
        fieldModel.setFrontField("Guard");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("BAVISTIN");
        fieldModel.setFrontField("Guard Technical");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("Billo");
        fieldModel.setFrontField("Admire");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("CUTTERTOOLS1");
        fieldModel.setFrontField("Shaked");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("FoamCleaner2");
        fieldModel.setFrontField("Weedblock small");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("FoamCleaner1");
        fieldModel.setFrontField("Agil");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("NEUTRAL1");
        fieldModel.setFrontField("Agil small");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("NEUTRAL2");
        fieldModel.setFrontField("Alika small");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("NIDAN");
        fieldModel.setFrontField("Alika");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("PROREMOVERS2");
        fieldModel.setFrontField("Cruiser");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("PROREMOVERS1");
        fieldModel.setFrontField("Ekalux");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("Soudafoam1");
        fieldModel.setFrontField("Fusiflex");
        productCode.add(fieldModel);
        fieldModel = new FieldModel();
        fieldModel.setDbField("Soudafoam2");
        fieldModel.setFrontField("Missile");
        productCode.add(fieldModel);
//        ArrayAdapter nameHeading3 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, productCode);
//        nameHeading3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spProductCode.setAdapter(nameHeading3);
        SpinnerDropDownAdapter mSpinnerDropDownAdapter3 = new SpinnerDropDownAdapter(getActivity(),productCode);
        spProductCode.setAdapter(mSpinnerDropDownAdapter3);
    }


    @Override
    public void applyTypeFace() {

        FontUtil.applyTypeface(tieShelfLifeInDays, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tvUploadName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tvUpload, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnFullFragmentCancel, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnFullFragmentSubmit, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(spIsRedeemAllow, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(spIsShelfLife, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieMaterialCode, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDescription, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDisplayName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(spProductCode, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(spMaterialTypeID, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieHSNCode, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(spBasicUOM, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(spSaleUOM, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieBasicPrice, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieGPL, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieNRV, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieMRP, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieGrossWt, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieNetWt, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDimensionL, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDimensionB, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDimensionH, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieBarCodeNumber, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    void setToNew(){
        tieMaterialCode.setText("");
        tieDescription.setText("");
        tieDisplayName.setText("");
        tieHSNCode.setText("");
        tieBasicPrice.setText("");
        tieNRV.setText("");
        tieGPL.setText("");
        tieMRP.setText("");
        tieGrossWt.setText("");
        tieNetWt.setText("");
        tieDimensionL.setText("");
        tieDimensionB.setText("");
        tieDimensionH.setText("");
        tieBarCodeNumber.setText("");
        tieShelfLifeInDays.setText("");
        tvUploadName.setText("no file attached");
        spProductCode.setSelection(0);
        spMaterialTypeID.setSelection(0);
        spSaleUOM.setSelection(0);
        spBasicUOM.setSelection(0);
        spIsRedeemAllow.setSelection(0);
        spIsShelfLife.setSelection(0);
        isGenerated = false;
        imageBaseString="";

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAccountSubmit:
                checkSubmit();
                if(isGenerated){
                    setToNew();
                }
                break;
            case R.id.btnAccountCancel:
                checkSubmit();
                if(isGenerated) {
                    getFragmentManager().popBackStack();
                    setToNew();
                }
                break;
            case R.id.tvUpload:
                Util.showMessageDialog(mContext,this,"Choose to upload image","Camera","Gallery","Cancel", Constants.App_DIALOG_UPLOAD,"",getFragmentManager());
                break;
        }
    }


    @Override
    public void onDialogPositiveClick(Dialog dialog, int mCallType) {
        if(Constants.App_DIALOG_UPLOAD==mCallType){
            // Camera
            // Checking camera availability
            if (!Util.isDeviceSupportCamera(getActivity())) {
                Toast.makeText(getActivity(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG)
                        .show();
                // will close the app if the device does't have camera
                getActivity().finish();
            } else {
                pictureActionIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // fileUri = getOutputMediaFileUri(1);
                // pictureActionIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                // fileUri);
                startActivityForResult(pictureActionIntent, Constants.CAMERA_PICTURE);

            }
        }
    }

    @Override
    public void onDialogNegetiveClick(Dialog dialog, int mCallType) {
        if(Constants.App_DIALOG_UPLOAD==mCallType){
            //Gallery
            pictureActionIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
            pictureActionIntent.setType("image/*");
            pictureActionIntent.putExtra("return-data", true);
            startActivityForResult(pictureActionIntent, Constants.GALLERY_PICTURE);
        }
    }

    @Override
    public void onDialogCancelClick(Dialog dialog, int mCallType) {
        if(Constants.App_DIALOG_UPLOAD==mCallType) {
            dialog.dismiss();
        }
    }
    private File temp_path;
    private final int COMPRESS = 100;
    private Uri fileUri;
    Intent pictureActionIntent;
    private String saveGalleryImageOnKitkat(Bitmap bitmap) {
        try {
            File cacheDir;
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//                cacheDir = new File(Environment.getExternalStorageDirectory(),
//                        getResources().getString(R.string.app_name));
//            else
//                cacheDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            cacheDir = new File(mContext.getFilesDir(), System.currentTimeMillis() + "");
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            String filename = System.currentTimeMillis() + ".png";
            File file = new File(cacheDir, filename);
            temp_path = file.getAbsoluteFile();
            // if(!file.exists())
            file.createNewFile();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS, bytes);
            out.write(bytes.toByteArray());
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == getActivity().RESULT_OK) {
                if (requestCode == Constants.CAMERA_PICTURE) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, COMPRESS, bytes);
////                File destination = new File(Environment.getExternalStorageDirectory(),
////                        System.currentTimeMillis() + ".png");
//                File destination = new File(mContext.getFilesDir(), System.currentTimeMillis() + ".png");
//                FileOutputStream fo;
//                try {
//                    destination.createNewFile();
//                    fileUri = Uri.fromFile(destination);
//                    AppLog.e("Profile", fileUri.getPath());
//
//                    fo = new FileOutputStream(destination);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                sdvPersonImage.setImageBitmap(thumbnail);
                    imageBaseString = Base64Util.BitMapToString(thumbnail);
                    imageType = "JPEG";
                    tvUploadName.setText("Image ready to upload");
                } else if (requestCode == Constants.GALLERY_PICTURE) {
                    String smallImagePath = "";

                    if (Build.VERSION.SDK_INT < 19) {
                        Uri selectedImage = data.getData();
                        // System.out.println("selectedImage "+selectedImage);
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null,
                                null);
                        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        smallImagePath = cursor.getString(columnIndex);
                        long fileSize = cursor.getLong(sizeIndex);
                        fileUri = Uri.parse(smallImagePath);

                        String mimeType = getActivity().getContentResolver().getType(fileUri);
                        Log.i("Type", mimeType);
                        Log.i("fileSize", fileSize + "");
                        long twoMb = 1024 * 1024 * 2;
                        cursor.close();
                        imageBaseString = getBase64StringNew(fileUri, (int) fileSize);
                        imageType = "JPEG";
                        tvUploadName.setText("Image ready to upload");
                        AppLog.e("smallImagePath <19", smallImagePath);
//                    sdvPersonImage.setImageBitmap(BitmapFactory.decodeFile(smallImagePath));
                        // encodeImage();
                    } else {
                        try {
                            InputStream imInputStream = getActivity().getContentResolver().openInputStream(data.getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(imInputStream);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS, bytes);
                            imageBaseString = Base64Util.BitMapToString(bitmap);
                            imageType = "JPEG";
                            tvUploadName.setText("Image ready to upload");
//                        smallImagePath = saveGalleryImageOnKitkat(bitmap);
                            AppLog.e("smallImagePath", smallImagePath);
//                        sdvPersonImage.setImageBitmap(BitmapFactory.decodeFile(smallImagePath));
                            // encodeImage();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        // finishAndSetResult(RESULT_OK, picturePath, false);
                    }
//                fileUri = Uri.parse(smallImagePath);
//                if(fileUri!=null)
//                getBase64StringNew(fileUri,)
                }
            }
        } catch (Exception e) {

        }
    }
    private String getBase64StringNew(Uri uri, int filelength) {
        String imageStr = null;
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

           /* InputStream finput = new FileInputStream(file);
            byte[] imageBytes = new byte[(int)file.length()];
            finput.read(imageBytes, 0, imageBytes.length);
            finput.close();
            String imageStr = Base64.encodeBase64String(imageBytes);*/

            //InputStream finput = new FileInputStream(file);
            byte[] byteFileArray = new byte[filelength];
            inputStream.read(byteFileArray, 0, byteFileArray.length);
            inputStream.close();
            imageStr = android.util.Base64.encodeToString(byteFileArray, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageStr;
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        hideProgressDialog();
        if (httpStatusCode == Constants.SUCCESS) {
            if (resultObj != null) {
                CustomerPointsRedeemResult customerPointsRedeemResult = (CustomerPointsRedeemResult) resultObj;
                fetchResponse(customerPointsRedeemResult);
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
    boolean isGenerated = false;
    private void fetchResponse(CustomerPointsRedeemResult serverResponse) {
        if(serverResponse !=null){
            if(serverResponse.getError()==Constants.SUCCESS){
                Util.showToast(serverResponse.getMessage());
                isGenerated = true;
            }else {
                isGenerated = false;
            }
        }
    }
}
