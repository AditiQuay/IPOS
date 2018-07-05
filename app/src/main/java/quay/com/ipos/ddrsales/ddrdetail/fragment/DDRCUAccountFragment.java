package quay.com.ipos.ddrsales.ddrdetail.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.PartnerConnectMain;
import quay.com.ipos.partnerConnect.model.Account;
import quay.com.ipos.partnerConnect.model.Cheques;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.AccountAdapter;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.EqualSpacingItemDecoration;

public class DDRCUAccountFragment extends Fragment implements InitInterface, View.OnClickListener {
    private static final String TAG = DDRCUAccountFragment.class.getSimpleName();
//    private TextView textViewLastUpdated, textViewAccountInfoHeading, textViewMadatory;
    private RecyclerView recyclerViewAccountInfo;

    private View view;
    private Context mContext;
    private AccountAdapter adapter;

    private EditText editAccountHolderName, editAccountNo;
    private MaterialSpinner spinnerAccountType;
    private EditText editIFSCCode, editBankName, editBranchAddress;
    private TextView textViewLastUpdated;

    private View btnAdd;
    private PCModel mpcModel;
    private String accountTypeArray[] = {"Saving", "Current"};///RECURRING, FIXED DEPOSITS


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ddrcu_account_info_fragment, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();


        return view;
    }

    @Override
    public void findViewById() {
        btnAdd = view.findViewById(R.id.btnAdd);
        textViewLastUpdated = view.findViewById(R.id.textViewLastUpdated);
//        textViewMadatory = view.findViewById(R.id.textViewMadatory);
//        textViewAccountInfoHeading = view.findViewById(R.id.textViewAccountInfoHeading);
//        textViewLastUpdated = view.findViewById(R.id.textViewLastUpdated);
        recyclerViewAccountInfo = view.findViewById(R.id.recyclerViewAccountInfo);

        editAccountHolderName = view.findViewById(R.id.editAccountHolderName);
        editAccountNo = view.findViewById(R.id.editAccountNo);
        spinnerAccountType = view.findViewById(R.id.spinnerAccountType);
        editBranchAddress = view.findViewById(R.id.editBranchAddress);
        editIFSCCode = view.findViewById(R.id.editIFSCCode);
        editBankName = view.findViewById(R.id.editBankName);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewField();
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }

        PartnerConnectMain mainActivity = (PartnerConnectMain) getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do what you want
                addNewField();
                // Toast.makeText(getActivity(), "Add Account Fragment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void applyInitValues() {


        recyclerViewAccountInfo.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewAccountInfo.addItemDecoration(new EqualSpacingItemDecoration(16)); // 16px. In practice, you'll want to use getDimensionPixelSize
        adapter = new AccountAdapter(mContext);
        recyclerViewAccountInfo.setAdapter(adapter);
    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        PartnerConnectMain partnerConnectMain = (PartnerConnectMain) getActivity();
        if (partnerConnectMain != null) {
            partnerConnectMain.getPcModelData().observe(this, new Observer<PCModel>() {
                @Override
                public void onChanged(@Nullable PCModel pcModel) {
                    mpcModel = pcModel;
                    setData(pcModel);

                }
            });
        }
    }

    Account account;

    private void setData(PCModel pcModel) {
        if (pcModel == null && pcModel.Business == null) {
            Log.i(TAG, "pcModel or pcModel.Business is null");
            return;
        }
        textViewLastUpdated.setText(DateAndTimeUtil.getMyDateAndTime("Last Updated :" , mpcModel.psslastUpdated));

        account = pcModel.Account.get(0);
        if (account != null) {
            //  spinnerAccountType.setText(account.mAccountType);
            editAccountNo.setText(account.mAccountNo);
            editAccountHolderName.setText(account.mAccountHolderName);
            editBranchAddress.setText(account.mBranchAdddres);
            editBankName.setText(account.mBankName);
            editIFSCCode.setText(account.mIFSCCode);

            //setListner
            editAccountHolderName.addTextChangedListener(generalTextWatcher);
            editAccountNo.addTextChangedListener(generalTextWatcher);
            //  spinnerAccountType.addTextChangedListener(generalTextWatcher);
            editBranchAddress.addTextChangedListener(generalTextWatcher);
            editBankName.addTextChangedListener(generalTextWatcher);
            editIFSCCode.addTextChangedListener(generalTextWatcher);

            final List<String> acList = Arrays.asList(accountTypeArray);

            ArrayAdapter partnerTypeHeading = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, accountTypeArray);
            partnerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAccountType.setAdapter(partnerTypeHeading);

            if (account.mAccountType != null) {

                if (acList.contains(account.mAccountType)) {
                    int index = acList.indexOf(account.mAccountType);
                    spinnerAccountType.setSelection(index + 1);
                }
            }


            spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == -1) {
                        account.mAccountType = "";
                        return;
                    }
                    try {

                        String partnerType = acList.get(i);
                        Log.i("mAccountType", partnerType);
                        account.mAccountType = partnerType;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            List<Cheques> chequesList = account.cheques;
            if (chequesList != null)
                adapter.loadData(chequesList);
        }
    }

    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before,
                                  int count) {
            if (account == null) {
                return;
            }

          /*  if (spinnerAccountType.getText().hashCode() == charSequence.hashCode()) {
                account.mAccountType = charSequence.toString();
            } else */
            if (editAccountHolderName.getText().hashCode() == charSequence.hashCode()) {
                account.mAccountHolderName = charSequence.toString();

            } else if (editAccountNo.getText().hashCode() == charSequence.hashCode()) {
                account.mAccountNo = charSequence.toString();

            } else if (editBankName.getText().hashCode() == charSequence.hashCode()) {
                account.mBankName = charSequence.toString();

            } else if (editBranchAddress.getText().hashCode() == charSequence.hashCode()) {
                account.mBranchAdddres = charSequence.toString();

            } else if (editIFSCCode.getText().hashCode() == charSequence.hashCode()) {
                account.mIFSCCode = charSequence.toString();

            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    private void addNewField() {
        if (mpcModel != null && mpcModel.Account != null) {
            if (mpcModel.Account.size() > 0) {
                if (mpcModel.Account.get(0) != null) {
                    Account account = mpcModel.Account.get(0);
                    if (account.cheques != null) {
                        Cheques cheques = new Cheques();
                        cheques.mSecurityCheque = "Yes";
                        account.cheques.add(cheques);

                        PartnerConnectMain connectMain = (PartnerConnectMain) getActivity();
                        if (connectMain != null) {
                            connectMain.getPcModelData().setValue(mpcModel);
                        }

                    }
                }
            }

        }
    }
}
