package quay.com.ipos.inventory.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.compliance.constants.Constant;
import quay.com.ipos.inventory.adapter.CustomAdapterAddress;
import quay.com.ipos.inventory.adapter.InventorPOInccoAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOEditListAdapter;
import quay.com.ipos.inventory.adapter.PaymentTermsPOListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.attachments.AttachFileModel;
import quay.com.ipos.inventory.modal.CommonModal;
import quay.com.ipos.inventory.modal.GrnAttachment;
import quay.com.ipos.inventory.modal.GrnInccoTermsModel;
import quay.com.ipos.inventory.modal.InventoryPOModal;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.listeners.AttachmentListener;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.MyListenerOnitemClick;
import quay.com.ipos.listeners.MyListenerPaymentTerms;
import quay.com.ipos.modal.MenuModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.realmbean.RealmCustomerInfoModal;
import quay.com.ipos.realmbean.RealmInventoryProducts;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderCentreSummary;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.realmbean.RealmUserDetail;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;


public class EditExpandablePODetailsActivity extends BaseActivity implements MyListenerOnitemClick,MyListenerPaymentTerms,MyListener,InventorPOInccoAdapter.OnCalculateTotalIncoTermsListener,AttachmentListener {
    private Dialog myDialog;
    final ArrayList<String> strings1Payment=new ArrayList<>();
    ExpandableListView expandableListView;
    ArrayList<AttachFileModel> attachFileModels = new ArrayList<>();
    //CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<MenuModal, List<InventoryPOModal>> expandableListDetail = new LinkedHashMap<MenuModal, List<InventoryPOModal>>();
    LinearLayout llTermsC,llAttachments,llPODetails,llItemsDetails,llIncoTerms,llPaymentTerms;
    RelativeLayout POHashitems,POitemsDetails,POIncoTerms,POPaymentTerms,POTermsandCondition,POAttachment;
    EditText edtPoNumber,edtPoDate,edtPoValDate,edtPoValue,edtPoGST,edtSupGSTIN;
    private RecyclerView recycler_viewItemDetail,recycler_viewInco,recycler_viewPayment,recycler_viewTerms,recycler_viewAttachment;
    ItemsDetailsPOEditListAdapter itemListDataAdapter;
    InventorPOInccoAdapter incoTermsPOListAdapter;
    PaymentTermsPOListAdapter milestonePOListAdapter;
    TermsPOListAdapter termsPOListAdapter;
    AttachFileAdapter attachmentsPOListAdapter;
    Context context;
    ArrayList<POItemDetail> poItemDetails=new ArrayList<>();
    ArrayList<GrnInccoTermsModel> poIncoTerms=new ArrayList<>();
    ArrayList<POPaymentTerms> poPaymentTerms=new ArrayList<>();
    ArrayList<POTermsCondition> poTermsConditions=new ArrayList<>();
    ArrayList<GrnAttachment> poAttachments=new ArrayList<>();
    String poNumber,businessPlaceId;
    private TextView tvHeaderPoNumber,tvHeaderPOItemDetail;
    private Spinner spnMilestone;
    private TextView tvAddTerms;
    private View ImvClose;
    private TextView textTotalIncoTerms;
    private TextView tvAddAttach;
    private quay.com.ipos.ui.SearchableSpinner edtSupplierName,edtSupAddress,edtBillingAddress,edtDeliveryAddress;
    private Button btnSave;
    private String poDate="",poValidate="";
    private TextView tvAddItems;
    private LinearLayout llPoDate,llPoVal,llSupplierInfo,llSuppierOthers,llSuppierNameOthers,llSuppierDeliveryOthers,llSuppierBillingOthers;
    private List<CommonModal> noGetEntityBuisnessPlacesModals=new ArrayList<>();
    private List<CommonModal> supplierName=new ArrayList<>();
    private List<CommonModal> supplierAddress=new ArrayList<>();

    private View deliveryView,billingView,supplierAddressView,NameView;
    private String msupplierName="",msupplierAddress="",billingAddress="",deliveryAddress="",supName="";
    private CheckBox chkBilling;
    private int postionBilling;
    private EditText edtSupDeliveryOther,edtSupBillingOther,edtSupNameOther,edtSupOther;
    private Spinner spnDetails;
    private TextView textViewPrice,tvQty;
    private Spinner spnOptions;
    private String requestJson;
    private boolean isPOHeader,isItemDetails,isInco,isPayment,isTerms,isAttachments;
    private ImageView imgri,imgRight,imgPaymentTerms,imgIncoTerms,imgItems,arrowPO;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.po_expandable_edit_details);
        context=EditExpandablePODetailsActivity.this;
        setHeader();
        Intent i=getIntent();
        if (i!=null){
            requestJson = i.getStringExtra("request");
            businessPlaceId=i.getStringExtra("businessPlaceId");
        }
        clearRealm();
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inializeViews();
        setLisner();
        getPODetails();
        setPaymentsTerms(0);
        setIncotermsData();
        spnDetails.setVisibility(View.VISIBLE);
        final ArrayList<String> strings=new ArrayList<>();
//        list.add("One Time with Recurring");
        strings.add("Details");
        strings.add("Advance");
        strings.add("On Delivery");
        strings.add("After Delivery");
        //   list.add("On Invoice Based");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,strings);
        spnDetails.setAdapter(stringArrayAdapter);


        spnDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                    if (poPaymentTerms.size() < 3 && !strings1Payment.contains(strings.get(i))) {
                        POPaymentTerms poIncoTerms1 = new POPaymentTerms();
                        poIncoTerms1.setPoPaymentTermsDetail(strings.get(i));
                        if (i == 2 || i == 3)
                            poIncoTerms1.setPoPaymentTermsInvoiceDue("0 Days");
                        else
                            poIncoTerms1.setPoPaymentTermsInvoiceDue("Immediate");
                        poIncoTerms1.setPoPaymentTermsPer(0);
                        strings1Payment.add(strings.get(i));
                        poPaymentTerms.add(poIncoTerms1);
                        setPaymentsTerms(0);
                    } else {
                        Util.showToast("Already Added");
                    }
                }
                spnDetails.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayList<String> list=new ArrayList<>();
//        list.add("One Time with Recurring");
        list.add("Milestone Based");
        //   list.add("On Invoice Based");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,list);
        spnMilestone.setAdapter(adapter);

        spnMilestone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPaymentsTerms(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        llPoVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialogfromVal(0);
            }
        });
        llPoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialogfrom(1);
            }
        });
    }

    private void setIncotermsData(){
        final ArrayList<String> strings=new ArrayList<>();
//        list.add("One Time with Recurring");
        strings.add("Options");
        strings.add("Loading");
        strings.add("Shipping");
        strings.add("Unload");
        strings.add("Toll");
        strings.add("E-Way Bill");
        //   list.add("On Invoice Based");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,strings);
        spnOptions.setAdapter(stringArrayAdapter);
        final ArrayList<String> strings1inco=new ArrayList<>();

        spnOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i!=0) {
                    int count = 0;
                    for (int k = 0; k < strings1inco.size(); k++) {
                        if (strings1inco.get(k).equalsIgnoreCase(strings.get(i)))
                            count = count + 1;
                    }
                    if (!strings1inco.contains(strings.get(i))) {
                        GrnInccoTermsModel poIncoTerms1 = new GrnInccoTermsModel();
                        poIncoTerms1.grnIncoDetail = strings.get(i);
                        poIncoTerms1.grnPayAmount = 0;
                        poIncoTerms1.grnPayByReceiver = true;
                        poIncoTerms1.grnPayBySender = false;
                        strings1inco.add(strings.get(i));
                        poIncoTerms.add(poIncoTerms1);
                    } else {
                        GrnInccoTermsModel poIncoTerms1 = new GrnInccoTermsModel();
                        poIncoTerms1.grnIncoDetail = strings.get(i) + " " + (count);
                        poIncoTerms1.grnPayAmount = 0;
                        poIncoTerms1.grnPayByReceiver = true;
                        poIncoTerms1.grnPayBySender = false;
                        strings1inco.add(strings.get(i));
                        poIncoTerms.add(poIncoTerms1);
                    }
                    setIncoTerms();
                }
                spnOptions.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public  void dateDialogfromVal(final int date) {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String erg = year+"";
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + String.valueOf(dayOfMonth);


                        poValidate=erg;
                        edtPoValDate.setText(Util.getFormattedDates(erg,Constants.format6,Constants.format2));






                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.show();

        dp.getDatePicker().setMinDate(System.currentTimeMillis()-1000);


    }
    public  void dateDialogfrom(final int date) {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String erg = year+"";
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + String.valueOf(dayOfMonth);


                        poDate=erg;
                        edtPoDate.setText(Util.getFormattedDates(erg,Constants.format6,Constants.format2));







                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.show();

        dp.getDatePicker().setMinDate(System.currentTimeMillis()-1000);


    }


    private void setPaymentsTerms(int s){
        //  poPaymentTerms.clear();
     /*   JSONArray jsonArray1= null;

        if (s==1){
            try {
                jsonArray1 = new JSONArray(Util.getAssetJsonResponse(EditExpandablePODetailsActivity.this,"paymentterms_recurring.json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (s==0){
            try {
                jsonArray1 = new JSONArray(Util.getAssetJsonResponse(EditExpandablePODetailsActivity.this,"paymentterms.json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                jsonArray1 = new JSONArray(Util.getAssetJsonResponse(EditExpandablePODetailsActivity.this,"paymentterms_oninvoice.json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        for (int j = 0; j < jsonArray1.length(); j++) {

            JSONObject jsonObject1 = jsonArray1.optJSONObject(j);

            POPaymentTerms poIncoTerms1=new POPaymentTerms();
            poIncoTerms1.setPoPaymentTermsDetail(jsonObject1.optString("poPaymentTermsDetail"));
            poIncoTerms1.setPoPaymentTermsInvoiceDue(jsonObject1.optString("poPaymentTermsInvoiceDue"));
            poIncoTerms1.setPoPaymentTermsPer(jsonObject1.optDouble("poPaymentTermsPer"));

            poPaymentTerms.add(poIncoTerms1);


        }*/

        setPaymentTerms();
    }
    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDialog();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void inializeViews(){



        imgri=findViewById(R.id.imgri);
        imgRight=findViewById(R.id.imgRight);
        imgPaymentTerms=findViewById(R.id.imgPaymentTerms);
        imgIncoTerms=findViewById(R.id.imgIncoTerms);
        imgItems=findViewById(R.id.imgItems);
        arrowPO=findViewById(R.id.arrowPO);

        imgri.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgRight.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgIncoTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgItems.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);


        tvQty=findViewById(R.id.tvQtyDetail);
        tvQty.setVisibility(View.GONE);
        edtSupOther=findViewById(R.id.edtSupOther);
        edtSupNameOther=findViewById(R.id.edtSupNameOther);
        spnOptions=findViewById(R.id.spnOptions);
        spnOptions.setVisibility(View.VISIBLE);
        textViewPrice=findViewById(R.id.priceDetails);
        textViewPrice.setVisibility(View.GONE);
        spnDetails=findViewById(R.id.spnDetails);
        edtSupBillingOther=findViewById(R.id.edtSupBillingOther);
        edtSupDeliveryOther=findViewById(R.id.edtSupDeliveryOther);
        chkBilling=findViewById(R.id.chkBilling);
        deliveryView=findViewById(R.id.deliveryView);
        billingView=findViewById(R.id.billingView);
        NameView=findViewById(R.id.NameView);
        supplierAddressView=findViewById(R.id.supplierAddressView);


        llSuppierBillingOthers=findViewById(R.id.llSuppierBillingOthers);
        llSuppierDeliveryOthers=findViewById(R.id.llSuppierDeliveryOthers);
        llSuppierNameOthers=findViewById(R.id.llSuppierNameOthers);
        llSuppierOthers=findViewById(R.id.llSuppierOthers);
        llSupplierInfo=findViewById(R.id.llSupplierInfo);
        llSupplierInfo.setVisibility(View.GONE);
        llPoDate=findViewById(R.id.llPoDate);
        llPoVal=findViewById(R.id.llPoVal);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setVisibility(View.VISIBLE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    submitGRNDetails();
                else {

                }
            }
        });
        tvAddItems=findViewById(R.id.tvAddItems);
        tvAddItems.setVisibility(View.VISIBLE);
        tvAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EditExpandablePODetailsActivity.this,InventoryItemAddNewOrderActivity.class);
                startActivityForResult(i,600);
            }
        });
        tvAddAttach = findViewById(R.id.tvAddAttach);
        tvAddAttach.setVisibility(View.VISIBLE);
        tvAddAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAttachFileClicked();
            }
        });
        textTotalIncoTerms = findViewById(R.id.textTotalIncoTerms);
        tvAddTerms=findViewById(R.id.tvAddTerms);
        tvAddTerms.setVisibility(View.VISIBLE);
        spnMilestone=findViewById(R.id.spnMilestone);
        tvHeaderPoNumber=findViewById(R.id.tvHeaderPoNumber);
        tvHeaderPOItemDetail=findViewById(R.id.tvHeaderPOItemDetail);
        llPODetails=findViewById(R.id.llPODetails);
        llItemsDetails=findViewById(R.id.llItemsDetails);
        llIncoTerms=findViewById(R.id.llIncoTerms);
        llPaymentTerms=findViewById(R.id.llPaymentTerms);
        llTermsC=findViewById(R.id.llTermsC);
        llAttachments=findViewById(R.id.llAttachments);

        POHashitems=findViewById(R.id.POHashitems);
        POitemsDetails=findViewById(R.id.POitemsDetails);
        POIncoTerms=findViewById(R.id.POIncoTerms);
        POPaymentTerms=findViewById(R.id.POPaymentTerms);
        POTermsandCondition=findViewById(R.id.POTermsandCondition);
        POAttachment=findViewById(R.id.POAttachment);

        edtPoNumber=findViewById(R.id.edtPoNumber);

        edtBillingAddress=findViewById(R.id.edtBillingAddress);
        edtDeliveryAddress=findViewById(R.id.edtDeliveryAddress);
        edtPoDate=findViewById(R.id.edtPoDate);
        edtPoGST=findViewById(R.id.edtPoGST);
        edtPoValDate=findViewById(R.id.edtPoValDate);
        edtPoValue=findViewById(R.id.edtPoValue);
        edtSupAddress=findViewById(R.id.edtSupAddress);
        edtSupGSTIN=findViewById(R.id.edtSupGSTIN);
        edtSupplierName=findViewById(R.id.edtSupplierName);
        edtPoValDate.setEnabled(true);
        edtPoDate.setEnabled(true);
        edtPoNumber.setEnabled(true);
        edtBillingAddress.setEnabled(true);
        edtDeliveryAddress.setEnabled(true);
        edtPoGST.setEnabled(true);
        edtPoValue.setEnabled(true);
        edtSupAddress.setEnabled(true);
        edtSupGSTIN.setEnabled(true);
        edtSupplierName.setEnabled(true);




        tvAddTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextInputLayout tilMessage;
                final TextInputEditText tieMessage;
                Button btnSubmit;

                myDialog.setContentView(R.layout.view_note_dialog);
                ImvClose = myDialog.findViewById(R.id.ImvClose);
                ImvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                tilMessage = myDialog.findViewById(R.id.tilMessage);
                tieMessage = myDialog.findViewById(R.id.tieMessage);
                tieMessage.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        tilMessage.setErrorEnabled(false);
                        tilMessage.setError(null);
                    }
                });
                btnSubmit = myDialog.findViewById(R.id.btnSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(tieMessage.getText().toString())) {
                            tilMessage.setErrorEnabled(true);
                            tilMessage.setError("Please write a Note.");
                        } else {
                            POTermsCondition termsCondition=new POTermsCondition();
                            termsCondition.setpOTermsAndConditionDetail(tieMessage.getText().toString());
                            termsCondition.setpOTermsAndConditionSrNo(1);

                            poTermsConditions.add(termsCondition);

                            setTermsCondition();
                            myDialog.dismiss();


                        }
                    }
                });


                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

            }
        });

        setIncosTerms();

        edtSupAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                    msupplierAddress = supplierAddress.get(i).getId();
                    if (i == supplierAddress.size() - 1) {
                        supplierAddressView.setVisibility(View.GONE);
                        llSuppierOthers.setVisibility(View.VISIBLE);
                    } else {
                        supplierAddressView.setVisibility(View.GONE);
                        llSuppierOthers.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtSupplierName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                    msupplierName = supplierName.get(i).getId();
                    supName = supplierName.get(i).getAddress();
                    if (i == supplierName.size() - 1) {
                        edtSupGSTIN.setText("");
                        edtSupOther.setText("");
                        NameView.setVisibility(View.VISIBLE);
                        llSuppierNameOthers.setVisibility(View.VISIBLE);

                    } else {
                        edtSupOther.setText(supplierName.get(i).getNameAddress());
                        edtSupGSTIN.setText(supplierName.get(i).getSupplierGSTIN());
                        NameView.setVisibility(View.GONE);
                        llSuppierNameOthers.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtBillingAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i!=0) {
                    billingAddress = noGetEntityBuisnessPlacesModals.get(i).getId();
                    if (i == noGetEntityBuisnessPlacesModals.size() - 1) {
                        billingView.setVisibility(View.VISIBLE);
                        llSuppierBillingOthers.setVisibility(View.VISIBLE);
                    } else {
                        billingView.setVisibility(View.GONE);
                        llSuppierBillingOthers.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtDeliveryAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i!=0) {
                    postionBilling = i;
                    deliveryAddress = noGetEntityBuisnessPlacesModals.get(i).getId();
                    if (i == noGetEntityBuisnessPlacesModals.size() - 1) {
                        deliveryView.setVisibility(View.VISIBLE);
                        llSuppierDeliveryOthers.setVisibility(View.VISIBLE);
                    } else {
                        deliveryView.setVisibility(View.GONE);
                        llSuppierDeliveryOthers.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chkBilling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    if (Util.validateString(deliveryAddress)){

                        edtBillingAddress.setSelection(postionBilling);

                        if (postionBilling==noGetEntityBuisnessPlacesModals.size()-1){
                            llSuppierBillingOthers.setVisibility(View.VISIBLE);
                            edtSupBillingOther.setText(edtSupDeliveryOther.getText().toString());
                        }else {
                            edtSupBillingOther.setText("");
                            llSuppierBillingOthers.setVisibility(View.GONE);
                        }

                    }else {
                        Util.showToast("Please choose delivery address");
                    }
                }else {
                    if (noGetEntityBuisnessPlacesModals.size()>0)
                        edtBillingAddress.setSelection(0);
                    edtSupBillingOther.setText("");
                    llSuppierBillingOthers.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setIncosTerms(){
    /*    JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(Util.getAssetJsonResponse(EditExpandablePODetailsActivity.this,"inco.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        double total=0;
        for (int j = 0; j < jsonArray.length(); j++) {

            JSONObject jsonObject1 = jsonArray.optJSONObject(j);

            GrnInccoTermsModel poIncoTerms1=new GrnInccoTermsModel();
            poIncoTerms1.grnIncoDetail=jsonObject1.optString("poIncoDetail");
            poIncoTerms1.grnPayAmount=jsonObject1.optDouble("poPayAmount");
            poIncoTerms1.grnPayByReceiver=jsonObject1.optBoolean("poPayByReceiver");
            poIncoTerms1.grnPayBySender=jsonObject1.optBoolean("poPayBySender");
            poIncoTerms.add(poIncoTerms1);

            total=total+jsonObject1.optDouble("poPayAmount");
*/
        setIncoTerms();

        //  }
    }

    private void getProducts(){
        poItemDetails.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmInventoryProducts> realmNewOrderCarts1 = realm.where(RealmInventoryProducts.class).findAll();
        //   poItemDetails.clear();



        int qty=0;
        for (RealmInventoryProducts realmNewOrderCart : realmNewOrderCarts1) {
            RealmInventoryProducts realmNewOrderCarts = realm.copyFromRealm(realmNewOrderCart);
            POItemDetail poItemDetail=new POItemDetail();
            boolean isAdded=false;
            if (poItemDetails.size()>0){

                for (int i=0;i<poItemDetails.size();i++){

                    if (poItemDetails.get(i).getMaterialName().equalsIgnoreCase(realmNewOrderCarts.getsProductName())){
                        isAdded=true;
                        poItemDetail.setPoItemQty(poItemDetails.get(i).getPoItemQty());
                    }else {
                        poItemDetail.setPoItemQty(1);
                    }
                }
            }else {
                poItemDetail.setPoItemQty(1);
            }

            poItemDetail.setTitle(realmNewOrderCarts.getsProductName());
            poItemDetail.setPoItemUnitPrice(realmNewOrderCarts.getsProductPrice());
            poItemDetail.setPoItemAmount(poItemDetail.getPoItemQty()*realmNewOrderCarts.getsProductPrice());
            poItemDetail.setPoItemIGSTValue(((realmNewOrderCarts.getSgst()+realmNewOrderCarts.getCgst())*poItemDetail.getPoItemQty()*realmNewOrderCarts.getsProductPrice())/100);
            poItemDetail.setPoItemCGSTPer(realmNewOrderCarts.getCgst());
            poItemDetail.setPoItemSGSTPer(realmNewOrderCarts.getSgst());
            poItemDetail.setMaterialCode(realmNewOrderCarts.getiProductModalId());
            poItemDetail.setMaterialName(realmNewOrderCarts.getsProductName());


            qty=qty+1;
            if (!isAdded)
                poItemDetails.add(poItemDetail);
        }
        tvHeaderPOItemDetail.setText("Item Details * | "+ poItemDetails.size()+" Items | Qty "+qty );
        //  mList.addAll(realmNewOrderCarts1);

        setItemDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProducts();
    }

    private String createJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray poDetails = new JSONArray();

        try {


            JSONArray jsonArray1=new JSONArray();

            double percentage=0;
            for (int j = 0; j < poPaymentTerms.size(); j++) {
                Gson gson= new GsonBuilder().create();
                JSONObject jsonObject1 = jsonArray1.optJSONObject(j);

                POPaymentTerms poIncoTerms1=new POPaymentTerms();
                poIncoTerms1.setPoPaymentTermsDetail(poPaymentTerms.get(j).getPoPaymentTermsDetail());
                if (poPaymentTerms.get(j).getPoPaymentTermsInvoiceDue().equalsIgnoreCase("Immediate"))
                poIncoTerms1.setPoPaymentTermsInvoiceDue(poPaymentTerms.get(j).getPoPaymentTermsInvoiceDue());
                else {
                    poIncoTerms1.setPoPaymentTermsInvoiceDue(poPaymentTerms.get(j).getPoPaymentTermsInvoiceDue()+" days");
                }
                poIncoTerms1.setPoPaymentTermsPer((int) poPaymentTerms.get(j).getPoPaymentTermsPer());
                percentage+=poPaymentTerms.get(j).getPoPaymentTermsPer();
                String json=gson.toJson(poIncoTerms1);
                JSONObject jsonObject2 =new JSONObject(json);
                jsonArray1.put(jsonObject2);


            }




            double poValue=0;
            double poGST=0;
            JSONArray arrayPoDetails=new JSONArray();
            for (int j = 0; j < poItemDetails.size(); j++) {

                Gson gson= new GsonBuilder().create();


                POItemDetail poItemDetail=new POItemDetail();
                poItemDetail.setTitle(poItemDetails.get(j).getTitle());
                poItemDetail.setPoItemUnitPrice(poItemDetails.get(j).getPoItemUnitPrice());
                poItemDetail.setPoItemAmount(poItemDetails.get(j).getPoItemAmount());
                poItemDetail.setPoItemIGSTValue(poItemDetails.get(j).getPoItemIGSTValue());
                poItemDetail.setPoItemCGSTPer(poItemDetails.get(j).getPoItemCGSTValue());
                poItemDetail.setPoItemSGSTPer(poItemDetails.get(j).getPoItemSGSTValue());
                poItemDetail.setPoItemQty(poItemDetails.get(j).getPoItemQty());
                poValue+=poItemDetails.get(j).getPoItemAmount();
                poGST+=poItemDetails.get(j).getPoItemIGSTValue();

                String json=gson.toJson(poItemDetail);

                JSONObject jsonObject1 =new JSONObject(json);
                jsonObject1.remove("title");
                jsonObject1.put("materialName",poItemDetails.get(j).getMaterialName());
                if(Util.validateString(poItemDetails.get(j).getMaterialCode()))
                    jsonObject1.put("materialCode",poItemDetails.get(j).getMaterialCode().replace("free",""));
                arrayPoDetails.put(jsonObject1);


            }


            JSONArray arrayTerms=new JSONArray();

            for (int j = 0; j < poTermsConditions.size(); j++) {

                JSONObject jsonObject1 = new JSONObject();


                jsonObject1.put("pOTermsAndConditionDetail",poTermsConditions.get(j).getpOTermsAndConditionDetail());
                jsonObject1.put("pOTermsAndConditionSrNo",j+1);

                arrayTerms.put(jsonObject1);


            }

            JSONArray IncoTermsArray = new JSONArray();
            for (int j = 0; j < poIncoTerms.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("poIncoDetail", poIncoTerms.get(j).grnIncoDetail);
                jsonObject1.put("poPayBySender", poIncoTerms.get(j).grnPayBySender);
                jsonObject1.put("poPayByReceiver", poIncoTerms.get(j).grnPayByReceiver);
                jsonObject1.put("poPayAmount", poIncoTerms.get(j).grnPayAmount);

                IncoTermsArray.put(jsonObject1);
            }

            JSONArray jsonArrayAttachments = new JSONArray();
          /*  for (int j = 0; j < poAttachments.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnAttachmentName", grnAttachments.get(j).getGrnAttachmentName());
                jsonObject1.put("grnAttachmentUrl", grnAttachments.get(j).getGrnAttachmentUrl());
                jsonObject1.put("grnAttachmentType", grnAttachments.get(j).getGrnAttachmentType());
                jsonArrayAttachments.put(jsonObject1);
            }
*/
            //attach new

            for (int i = 0; i < attachFileModels.size(); i++) {
                AttachFileModel fileModel = attachFileModels.get(i);
                Uri returnUri = fileModel.uri;
                Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                String fileName = returnCursor.getString(nameIndex);
                String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
                String mimeType = getContentResolver().getType(returnUri);
                Log.i("Type", mimeType);


                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("pOAttachmentName", fileName);
                jsonObject1.put("pOAttachmentUrl", getBase64StringNew(returnUri, Integer.parseInt(fileSize)));
                jsonObject1.put("pOAttachmentType",  mimeType);
                jsonArrayAttachments.put(jsonObject1);


            }
            //attach end






            jsonObject.put("poNumber", edtPoNumber.getText().toString());
            jsonObject.put("poDate", "na");
            jsonObject.put("poValidityDate", "na");
            jsonObject.put("poValue", poValue);
            jsonObject.put("poIGSTValue",poGST);
            jsonObject.put("poCGSTValue",0);
            jsonObject.put("poSGSTValue", 0);
            jsonObject.put("supplierCode", msupplierName);
            if (Util.validateString(supName)) {
                jsonObject.put("supplierName", supName);
                jsonObject.put("supplierNameOther", "");
            } else {
                jsonObject.put("supplierNameOther", edtSupNameOther.getText().toString().trim());
                jsonObject.put("supplierName", "Other");
            }
            if (Util.validateString(edtSupOther.getText().toString().trim())) {
                jsonObject.put("supplierAddress", edtSupOther.getText().toString());
                jsonObject.put("supplierAddressOther","" );
            } else {
                jsonObject.put("supplierAddressOther",  edtSupOther.getText().toString().trim());
                jsonObject.put("supplierAddress", "Other");
            }

            jsonObject.put("supplierGSTIN", edtSupGSTIN.getText().toString().trim());
            if (Util.validateString(billingAddress)) {
                jsonObject.put("billingAddress", billingAddress);
                jsonObject.put("billingAddressOther", "");
            } else {
                jsonObject.put("billingAddressOther", edtSupBillingOther.getText().toString().trim());
                jsonObject.put("billingAddress", "Other");
            }

            if (Util.validateString(deliveryAddress)) {
                jsonObject.put("deliveryAddress", deliveryAddress);
                jsonObject.put("deliveryAddressOther","" );
            } else {
                jsonObject.put("deliveryAddressOther", edtSupDeliveryOther.getText().toString().trim());
                jsonObject.put("deliveryAddress", "Other");
            }


            jsonObject.put("poItemDetails", arrayPoDetails);
            jsonObject.put("poIncoTerms", IncoTermsArray);
            jsonObject.put("poPaymentTerms",jsonArray1);
            jsonObject.put("poPaymentTermsType", "Milestone based");
            jsonObject.put("poTermsAndConditions",arrayTerms);
            jsonObject.put("poAttachments", jsonArrayAttachments);
            jsonObject.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject.put("businessPlaceId",businessPlaceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();

        //  new RealmController().saveGRNDetails(jsonObject.toString());

    }

    private void setItemDetails(){


        recycler_viewItemDetail=findViewById(R.id.recycler_viewItemDetail);
        recycler_viewItemDetail.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewItemDetail.setLayoutManager(mLayoutManager);
        itemListDataAdapter = new ItemsDetailsPOEditListAdapter(EditExpandablePODetailsActivity.this, poItemDetails,this);
        recycler_viewItemDetail.setAdapter(itemListDataAdapter);
    }

    private void setIncoTerms(){


        recycler_viewInco=findViewById(R.id.recycler_viewInco);
        recycler_viewInco.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewInco.setLayoutManager(mLayoutManager);
        incoTermsPOListAdapter = new InventorPOInccoAdapter(EditExpandablePODetailsActivity.this, poIncoTerms,this);
        recycler_viewInco.setAdapter(incoTermsPOListAdapter);
    }

    private void setPaymentTerms(){


        recycler_viewPayment=findViewById(R.id.recycler_viewPayment);
        recycler_viewPayment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewPayment.setLayoutManager(mLayoutManager);
        milestonePOListAdapter = new PaymentTermsPOListAdapter(EditExpandablePODetailsActivity.this, poPaymentTerms,this);
        recycler_viewPayment.setAdapter(milestonePOListAdapter);
    }

    private void setTermsCondition(){


        recycler_viewTerms=findViewById(R.id.recycler_viewTerms);
        recycler_viewTerms.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewTerms.setLayoutManager(mLayoutManager);
        termsPOListAdapter = new TermsPOListAdapter(EditExpandablePODetailsActivity.this, poTermsConditions);
        recycler_viewTerms.setAdapter(termsPOListAdapter);
    }


    private void setAttahcments(){


        recycler_viewAttachment=findViewById(R.id.recycler_viewAttachment);
        recycler_viewAttachment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewAttachment.setLayoutManager(mLayoutManager);
        attachmentsPOListAdapter = new AttachFileAdapter(attachFileModels);
        recycler_viewAttachment.setAdapter(attachmentsPOListAdapter);
    }

    private void setLisner(){

        POHashitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isPOHeader){
                    llPODetails.setVisibility(View.VISIBLE);
                    isPOHeader=true;
                    arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llPODetails.setVisibility(View.GONE);
                    isPOHeader=false;
                }
            /*    llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });


        POitemsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isItemDetails){
                    llItemsDetails.setVisibility(View.VISIBLE);
                    isItemDetails=true;
                    imgItems.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgItems.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llItemsDetails.setVisibility(View.GONE);
                    isItemDetails=false;
                }
                /*llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.VISIBLE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });

        POIncoTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInco){
                    llIncoTerms.setVisibility(View.VISIBLE);
                    isInco=true;
                    imgIncoTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgIncoTerms.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llIncoTerms.setVisibility(View.GONE);
                    isInco=false;
                }
               /* llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.VISIBLE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });
        POPaymentTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPayment){
                    llPaymentTerms.setVisibility(View.VISIBLE);
                    isPayment=true;
                    imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llPaymentTerms.setVisibility(View.GONE);
                    isPayment=false;
                }
          /*      llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.VISIBLE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });
        POTermsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTerms){
                    llTermsC.setVisibility(View.VISIBLE);
                    isTerms=true;
                    imgRight.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgRight.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llTermsC.setVisibility(View.GONE);
                    isTerms=false;
                }
           /*     llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.VISIBLE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });
        POAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAttachments){
                    llAttachments.setVisibility(View.VISIBLE);
                    isAttachments=true;
                    imgri.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgri.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llAttachments.setVisibility(View.GONE);
                    isAttachments=false;
                }
               /* llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.VISIBLE);*/
            }
        });



    }


    private void getExpandableData(String response){
        noGetEntityBuisnessPlacesModals.clear();
        try {
            JSONObject jsonObject=new JSONObject(response);
            CommonModal modal=new CommonModal();
            modal.setAddress("Select Name");
            modal.setId("0");
            modal.setSupplierGSTIN("");
            supplierName.add(modal);

            JSONArray jsonArray=jsonObject.optJSONArray("supplierDetails");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.optJSONObject(i);
                CommonModal noGetEntityBuisnessPlacesModal=new CommonModal();
                noGetEntityBuisnessPlacesModal.setAddress(jsonObject1.optString("supplierName"));
                noGetEntityBuisnessPlacesModal.setId(jsonObject1.optString("supplierId"));
                noGetEntityBuisnessPlacesModal.setSupplierGSTIN(jsonObject1.optString("supplierGSTIN"));
                noGetEntityBuisnessPlacesModal.setNameAddress(jsonObject1.optString("supplierAddress"));
                supplierName.add(noGetEntityBuisnessPlacesModal);
            }

            CommonModal commonModal=new CommonModal();
            commonModal.setAddress("Select Address");
            commonModal.setId("0");

            noGetEntityBuisnessPlacesModals.add(commonModal);
            supplierAddress.add(commonModal);
            JSONArray jsonArray4=jsonObject.optJSONArray("address");
            for (int i=0;i<jsonArray4.length();i++){
                JSONObject jsonObject1=jsonArray4.optJSONObject(i);
                CommonModal noGetEntityBuisnessPlacesModal=new CommonModal();
                noGetEntityBuisnessPlacesModal.setAddress(jsonObject1.optString("buisnessPlaceName"));
                noGetEntityBuisnessPlacesModal.setId(jsonObject1.optString("buisnessPlaceId"));

                noGetEntityBuisnessPlacesModals.add(noGetEntityBuisnessPlacesModal);
                supplierAddress.add(noGetEntityBuisnessPlacesModal);
            }

            CommonModal noGetEntityBuisnessPlacesModal=new CommonModal();
            noGetEntityBuisnessPlacesModal.setAddress("Others");
            noGetEntityBuisnessPlacesModal.setId("Other");

            noGetEntityBuisnessPlacesModals.add(noGetEntityBuisnessPlacesModal);
            supplierAddress.add(noGetEntityBuisnessPlacesModal);
            supplierName.add(noGetEntityBuisnessPlacesModal);

          /*  POIncoTerms poIncoTerms2=new POIncoTerms();
            poIncoTerms2.setPoIncoDetail("Total");
            poIncoTerms2.setPoPayAmount(total);
            poIncoTerms2.setPoPayByReceiver(false);
            poIncoTerms2.setPoPayBySender(false);
            poIncoTerms.add(poIncoTerms2);*/





            setSpinnerData(edtBillingAddress);
            setSpinnerData(edtDeliveryAddress);
            setSpinnerNameData(edtSupplierName);
            setSpinnerSupplierAddressData(edtSupAddress);




        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    public void getPODetails() {
        final ProgressDialog progressDialog=new ProgressDialog(EditExpandablePODetailsActivity.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("empCode",Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId",businessPlaceId);
            jsonObject1.put("poNumber","na");
            jsonObject1.put("isGRN",false);
            jsonObject1.put("isGRNOrQC","na");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_GetPODETAILS;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                progressDialog.dismiss();
                //  dismissProgress();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // dismissProgress();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                try {
                    if (response != null && response.isSuccessful()) {

                        final String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonObject=new JSONObject(responseData);

                            // saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getExpandableData(responseData);
                                }
                            });


                        }


                    } else if (response.code() == Constants.BAD_REQUEST) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.URL_NOT_FOUND) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.CONNECTION_OUT) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();



                }
            }
        });
    }


    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onRowClicked(int position, int value) {


    }



    @Override
    public void funIncoTermsTotalCount(double totalIncoTerms) {
        textTotalIncoTerms.setText(totalIncoTerms + "");

    }

    private static final int PICKFILE_RESULT_CODE = 101;

    @Override
    public void onAttachmentClicked(int position) {

    }

    public void onAttachFileClicked() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==600){
            getProducts();
        }else
            onActivityResultAttachment(requestCode,resultCode,data);


    }

    public void onActivityResultAttachment(int requestCode, int resultCode, Intent data) {

        try {
            switch (requestCode) {
                case PICKFILE_RESULT_CODE:
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        Cursor returnCursor =
                                getContentResolver().query(uri, null, null, null, null);
                        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                        returnCursor.moveToFirst();

                        String fileName = returnCursor.getString(nameIndex);
                        long fileSize = returnCursor.getLong(sizeIndex);
                        String mimeType = getContentResolver().getType(uri);
                        Log.i("Type", mimeType);
                        Log.i("fileSize", fileSize+"");
                        long twoMb = 1024 * 1024 * 2;

                        if(fileSize <= twoMb) {
                            AttachFileModel fileModel = new AttachFileModel();
                            fileModel.fileName = fileName;
                            fileModel.mimeType = mimeType;
                            fileModel.uri = uri;

                            attachFileModels.add(fileModel);
                            updateSize();
                            String FilePath = data.getData().getPath();
                        }else {
                            Toast.makeText(getApplicationContext(), "Oops! File Size must be less than 2 MB", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSize() {
        int attachFileSize = attachFileModels.size();
        int attachVoiceSize = 0;
        int totalSize = attachFileSize;
        // textViewAttachmentSize.setText("(" + totalSize + ")");
        //    Toast.makeText(getActivity(), "attachFileModels" + attachFileModels.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < attachFileModels.size(); i++) {
            Log.v("attachFileModels", "attachFileModels" + attachFileModels.get(i));
        }
        if (attachFileSize > 0) {
            setAttahcments();

        }
    }

    @Override
    public void onRowClickedPaymentTerms(int position, int percent, String days) {

        if (days.equalsIgnoreCase("payment")){
            poPaymentTerms.remove(position);
            strings1Payment.remove(position);
            milestonePOListAdapter.notifyItemRemoved(position);
            milestonePOListAdapter.notifyItemRangeChanged(position,poPaymentTerms.size());
        }else {
            POPaymentTerms poIncoTerms1 = new POPaymentTerms();
            poIncoTerms1.setPoPaymentTermsDetail(poPaymentTerms.get(position).getPoPaymentTermsDetail());
            if (!poPaymentTerms.get(position).getPoPaymentTermsInvoiceDue().equalsIgnoreCase("Immediate")) {
                if (days != null && days.contains("days"))
                    poIncoTerms1.setPoPaymentTermsInvoiceDue(days.replaceAll("days", "").replaceAll("day", "").replaceAll("da", "").replaceAll("d", ""));
                else {
                    poIncoTerms1.setPoPaymentTermsInvoiceDue(days.replaceAll("days", "").replaceAll("day", "").replaceAll("da", "").replaceAll("d", ""));
                }
            }else {
                poIncoTerms1.setPoPaymentTermsInvoiceDue("Immediate");

            }
            poIncoTerms1.setPoPaymentTermsPer(percent);

            poPaymentTerms.set(position, poIncoTerms1);

            milestonePOListAdapter.notifyItemChanged(position);
        }

    }

    @Override
    public void onRowClickedOnItem(final int position, final int percent,final double value) {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                double valuetotal=value;
                if (value==0){
                    //     valuetotal=poItemDetails.get(position).getPoItemUnitPrice();
                }
                POItemDetail poItemDetail=new POItemDetail();
                poItemDetail.setTitle(poItemDetails.get(position).getTitle());

                poItemDetail.setPoItemUnitPrice(valuetotal);
                poItemDetail.setPoItemCGSTPer(poItemDetails.get(position).getPoItemCGSTPer());
                poItemDetail.setPoItemIGSTPer(poItemDetails.get(position).getPoItemIGSTPer());
                poItemDetail.setMaterialCode(poItemDetails.get(position).getMaterialCode());
                poItemDetail.setMaterialName(poItemDetails.get(position).getMaterialName());
                poItemDetail.setPoItemAmount(valuetotal*percent);
                poItemDetail.setPoItemIGSTValue(((poItemDetails.get(position).getPoItemSGSTPer()+poItemDetails.get(position).getPoItemCGSTPer())*percent*valuetotal)/100);
                poItemDetail.setPoItemQty(percent);
                poItemDetails.set(position,poItemDetail);

                itemListDataAdapter.notifyItemChanged(position);
                int qty=0;
                for (int l=0;l<poItemDetails.size();l++) {
                    qty+=poItemDetails.get(l).getPoItemQty();

                }
                tvHeaderPOItemDetail.setText("Item Details | " + poItemDetails.size() + " Items | Qty " +qty);
            }
        });

    }

    private class AttachVH extends RecyclerView.ViewHolder {
        public TextView textView;
        public View btnClear;

        public AttachVH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            btnClear = itemView.findViewById(R.id.btnClear);
            btnClear.setVisibility(View.VISIBLE);
        }
    }

    private class AttachFileAdapter extends RecyclerView.Adapter<AttachVH> {
        private List<AttachFileModel> spendRequestAttachment;

        public AttachFileAdapter(List<AttachFileModel> spendRequestAttachment) {
            this.spendRequestAttachment = spendRequestAttachment;
        }

        @NonNull
        @Override
        public AttachVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attachfile_item, parent, false);
            return new AttachVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AttachVH holder, final int position) {
            final AttachFileModel fileModel = spendRequestAttachment.get(position);
            final String fileName = fileModel.fileName;
            // String name = fileName.substring(fileName.lastIndexOf("/"));
            //SpannableString content = new SpannableString("" + name);
            //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            // textView.setText(content);
            //holder.textView.setText(name);
            holder.textView.setText(fileName);
            Log.v("path", "---------------------" + fileName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Uri path = Uri.parse(attachment);
                    String type = attachment;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment));*/
                    // intent.setDataAndType(spendRequestAttachment.get(position),"*/*");
                    //   intent.setDataAndType(path, type);
                    //intent.addFlag(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    //Toast.makeText(getActivity(), "In Progress!", Toast.LENGTH_SHORT).show();
                 /*   startActivity(intent);*/
                    final Intent shareIntent = new Intent(Intent.ACTION_VIEW);
                    //   shareIntent.setType("*/*");
                    //  shareIntent.setDataAndType(Uri.parse(fileModel.uri.toString()), "image/*");
                    shareIntent.setDataAndType(Uri.parse(fileModel.uri.toString()),fileModel.mimeType);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //final File photoFile = new File(getFilesDir(), "foo.jpg");

                    startActivity(Intent.createChooser(shareIntent, "View file using"));
                }
            });
            holder.btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spendRequestAttachment.remove(fileModel);
                    notifyDataSetChanged();

                    int attachFileSize = attachFileModels.size();
                    // textViewAttachmentSize.setText("(" + attachFileSize + ")");
                }
            });
        }

        @Override
        public int getItemCount() {
            return spendRequestAttachment.size();
        }
    }
    private String getBase64StringNew(Uri uri, int filelength) {
        String imageStr = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

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

    public void submitGRNDetails() {


        final ProgressDialog progressDialog = new ProgressDialog(EditExpandablePODetailsActivity.this);
        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, createJson());
        String url = IPOSAPI.WEB_SERVICE_GET_PO_CREATE;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                //  dismissProgress();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // dismissProgress();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                try {
                    if (response != null && response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });

                        final String responseData = response.body().string();
                        if (responseData != null) {

                            final JSONObject jsonObject=new JSONObject(responseData);
                            //saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.showToast(jsonObject.optString("code")+" PO created Successfully");
                                    JSONObject jsonObject1=null;
                                    try {
                                        jsonObject1=new JSONObject(requestJson);
                                        jsonObject1.put("poNumber",jsonObject.optString("code"));
                                        jsonObject1.put("poDate",Util.getCurrentDate());
                                        jsonObject1.put("edtSupplier",msupplierName);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Intent i = new Intent(EditExpandablePODetailsActivity.this, InventoryGRNStepsActivity.class);
                                    assert jsonObject1 != null;
                                    i.putExtra("request", jsonObject1.toString());
                                    i.putExtra("businessPlaceId", businessPlaceId + "");
                                    i.putExtra("poNumber",jsonObject.optString("code"));
                                    i.putExtra("isGrn","1");
                                    startActivity(i);
                                    finish();
                                }
                            });

                        }

                    } else if (response.code() == Constants.BAD_REQUEST) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.URL_NOT_FOUND) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.CONNECTION_OUT) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });
    }


    private void setSpinnerData(Spinner spinner){

        String[] address={"Testing","Other"};


        CustomAdapterAddress adapter = new CustomAdapterAddress(mContext, R.layout.spinner_item_pss,R.id.text1,noGetEntityBuisnessPlacesModals);
        // adapter.setDropDownViewResource(R.layout.spinner_item_pss);

        spinner.setAdapter(adapter);
    }

    private void setSpinnerNameData(Spinner spinner){
        String[] address={"Testing","Other"};


        CustomAdapterAddress adapter = new CustomAdapterAddress(mContext, R.layout.spinner_item_pss,R.id.text1,supplierName);
        //  adapter.setDropDownViewResource(R.layout.spinner_item_pss);
        spinner.setAdapter(adapter);
    }

    private void setSpinnerSupplierAddressData(Spinner spinner){
        String[] address={"Testing","Other"};


        CustomAdapterAddress adapter = new CustomAdapterAddress(mContext, R.layout.spinner_item_pss,R.id.text1,supplierAddress);
        //adapter.set(R.layout.spinner_item_pss);
        spinner.setAdapter(adapter);
    }

    private boolean validate(){

        boolean valid=true;

        if (!Util.validateString(supName)){
            valid=false;
        }
        if (!Util.validateString(edtSupOther.getText().toString())){
            valid=false;
        }
        if (!Util.validateString(edtSupGSTIN.getText().toString().trim()) || edtSupGSTIN.getText().toString().length()<15){
            valid=false;
        }
        if (!Util.validateString(billingAddress)){
            valid=false;
        }
        if (!Util.validateString(deliveryAddress)){
            valid=false;
        }
        if (poItemDetails.isEmpty()){
            valid=false;
        }
        if (poPaymentTerms.isEmpty()){
            valid=false;
        }

        if (msupplierName.equalsIgnoreCase("Other")){
            if (!Util.validateString(edtSupNameOther.getText().toString().trim())){
                valid=false;
            }
        }
       /* if (msupplierAddress.equalsIgnoreCase("Other")){
            if (!Util.validateString(edtSupOther.getText().toString().trim())){
                valid=false;
            }
        }*/
        if (deliveryAddress.equalsIgnoreCase("Other")){
            if (!Util.validateString(edtSupDeliveryOther.getText().toString().trim())){
                valid=false;
            }
        }
        if (billingAddress.equalsIgnoreCase("Other")){
            if (!Util.validateString(edtSupBillingOther.getText().toString().trim())){
                valid=false;
            }
        }

        if (valid){
            double percentage=0;
            for (int j = 0; j < poPaymentTerms.size(); j++) {

                percentage+=poPaymentTerms.get(j).getPoPaymentTermsPer();


            }

            if (percentage==100) {

                valid=true;

            }else {
                Util.showToast("Payment Terms total percentage should not be greater or less than 100%");
                valid=false;

            }
        }else {
            Util.showToast("Please fill all required (*) fields");
        }

        return valid;
    }

    public void clearRealm() {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(RealmInventoryProducts.class);


           /* Prefs.clearValue(AppConstants.UserId);
            Prefs.clearValue(AppConstants.Login_Status);
            Prefs.clearValue(AppConstants.USERNAME);
            Prefs.clearValue(AppConstants.EMPLOYEE_NAME);
            Prefs.clearValue(AppConstants.employee_name);
            Prefs.clearValue(AppConstants.name);*/
       /*     realm.delete(RealmAnswers.class);
            realm.delete(RealmCategory.class);
            realm.delete(RealmCategoryAnswers.class);
            realm.delete(RealmClient.class);
            realm.delete(RealmCustomer.class);
            realm.delete(RealmRoles.class);

            realm.delete(RealmQuestion.class);
            realm.delete(RealmQuestions.class);
            realm.delete(RealmSurveys.class);
            realm.delete(RealmWorkFlow.class);
            realm.delete(RealmSurveysList.class);
            realm.delete(RealmUser.class);
            realm.delete(RealmSurveyQuestion.class);*/


        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }
    private void confirmationDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(EditExpandablePODetailsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(EditExpandablePODetailsActivity.this);
        }
        builder.setTitle("Alert")
                .setMessage("Are you sure you want to exit the screen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onBackPressed() {
        //    super.onBackPressed();
        confirmationDialog();
    }
}
