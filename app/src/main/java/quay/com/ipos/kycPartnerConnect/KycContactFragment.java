package quay.com.ipos.kycPartnerConnect;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.kycPartnerConnect.kycAdapter.KycContactInfoAdapter;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.KeyBusinessContactInfo;
import quay.com.ipos.partnerConnect.model.NewContact;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.EqualSpacingItemDecoration;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class KycContactFragment extends Fragment implements InitInterface, View.OnClickListener {
    private static final String TAG = KycContactFragment.class.getSimpleName();
    private View view;
    private TextView textViewLastUpdated, textViewMadatory, textViewContactInfoHeading;

    private MaterialSpinner keyPositionSpinner;
    private TextInputLayout tilContactPersonName, tilContactMobileNumName, tilContactSecondaryMobileNumName, tilkeyEmailName, tilkeyNoteName;
    private TextInputEditText editContactPerson, editMoible, editMobile2, editEmail, editNote;
    private RecyclerView recyclerViewContactInfo;

    private Context mContext;
    private List<String> listPosition = new ArrayList<>();
    private String[] partnerKeyPosition = {"Director", "Manager", "Executive"};
//    private Button btnAdd;
    PCModel mpcModel;

    private KeyBusinessContactInfo contactInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kyc_contact_fragment, container, false);
        mContext = getActivity();
        listPosition = Arrays.asList(partnerKeyPosition);

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        return view;
    }

    @Override
    public void findViewById() {
//        btnAdd = view.findViewById(R.id.btnAdd);
        textViewMadatory = view.findViewById(R.id.textViewMadatory);
        textViewLastUpdated = view.findViewById(R.id.textViewLastUpdated);
        keyPositionSpinner = view.findViewById(R.id.keyPositionSpinner);
        keyPositionSpinner.setEnabled(false);

        tilContactPersonName = view.findViewById(R.id.tilContactPersonName);
        tilContactMobileNumName = view.findViewById(R.id.tilContactMobileNumName);
        tilContactSecondaryMobileNumName = view.findViewById(R.id.tilContactSecondaryMobileNumName);
        tilkeyEmailName = view.findViewById(R.id.tilkeyEmailName);
        tilkeyNoteName = view.findViewById(R.id.tilkeyNoteName);
        editContactPerson = view.findViewById(R.id.editContactPerson);
        editMoible = view.findViewById(R.id.editMoible);
        editMobile2 = view.findViewById(R.id.editMobile2);
        editEmail = view.findViewById(R.id.editEmail);
        editNote = view.findViewById(R.id.editNote);
        textViewContactInfoHeading = view.findViewById(R.id.textViewContactInfoHeading);

        recyclerViewContactInfo = view.findViewById(R.id.recyclerViewContactInfo);

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addNewField();
//            }
//        });

    }

    @Override
    public void applyInitValues() {

        recyclerViewContactInfo.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewContactInfo.addItemDecoration(new EqualSpacingItemDecoration(16)); // 16px. In practice, you'll want to use getDimensionPixelSize

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewLastUpdated, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(keyPositionSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilContactPersonName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilContactMobileNumName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilContactSecondaryMobileNumName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilkeyEmailName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilkeyNoteName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(editContactPerson, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(editContactPerson, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(editMoible, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(editMobile2, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(editEmail, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(editNote, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(recyclerViewContactInfo, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewContactInfoHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
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
        KYCMain partnerConnectMain = (KYCMain) getActivity();
        if (partnerConnectMain != null) {
            partnerConnectMain.getPcModelData().observe(this, new Observer<PCModel>() {
                @Override
                public void onChanged(@Nullable PCModel PCModel) {
                    mpcModel = PCModel;
                    setData(PCModel);

                }
            });
        }
    }

    private void setData(PCModel PCModel) {
        try {

            if (PCModel == null && PCModel.contactDetail == null) {
                Log.i(TAG, "PCModel or PCModel.Business is null");
                return;
            }

            contactInfo = PCModel.contactDetail.KeyBusinessContactInfo;


            ArrayAdapter partnerTypeHeading = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, partnerKeyPosition);
            partnerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            keyPositionSpinner.setAdapter(partnerTypeHeading);

            if (contactInfo.keyDesignation != null) {
                if (listPosition.contains(contactInfo.keyDesignation)) {
                    int index = listPosition.indexOf(contactInfo.keyDesignation);
                    keyPositionSpinner.setSelection(index + 1);
                }
            }


            keyPositionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == -1) {
                        contactInfo.keyDesignation = "";
                        return;
                    }
                    try {

                        String partnerType = listPosition.get(i);
                        Log.i("mPartnerType", partnerType);
                        contactInfo.keyDesignation = partnerType;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            editContactPerson.setText(contactInfo.keyEmpName);
            editMoible.setText(contactInfo.keyMobile);
            editMobile2.setText(contactInfo.keyMobile2);
            editEmail.setText(contactInfo.keyEmail);
            editNote.setText(contactInfo.keyEmpNote);


            editContactPerson.addTextChangedListener(generalTextWatcher);
            editMoible.addTextChangedListener(generalTextWatcher);
            editMobile2.addTextChangedListener(generalTextWatcher);
            editEmail.addTextChangedListener(generalTextWatcher);
            editNote.addTextChangedListener(generalTextWatcher);

            recyclerViewContactInfo.setAdapter(new KycContactInfoAdapter(getActivity(), PCModel.contactDetail.KeyBusinessContactInfo.NewContact));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before,
                                  int count) {

            if (editContactPerson.getText().hashCode() == charSequence.hashCode()) {
                contactInfo.keyEmpName = charSequence.toString();
            } else if (editMoible.getText().hashCode() == charSequence.hashCode()) {
                contactInfo.keyMobile = charSequence.toString();

            } else if (editMobile2.getText().hashCode() == charSequence.hashCode()) {
                contactInfo.keyMobile2 = charSequence.toString();

            } else if (editEmail.getText().hashCode() == charSequence.hashCode()) {
                contactInfo.keyEmail = charSequence.toString();

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
        if (mpcModel != null && mpcModel.contactDetail != null) {
            if (mpcModel.contactDetail.KeyBusinessContactInfo != null) {
                if (mpcModel.contactDetail.KeyBusinessContactInfo.NewContact != null) {
                    NewContact newContact = new NewContact();
                    newContact.ID = 0;
                    newContact.RoleID = "";
                    newContact.Name = "";
                    newContact.Role = "";
                    newContact.PrimaryMobile = "";
                    newContact.SecondaryMobile = "";
                    newContact.Email = "";
                    mpcModel.contactDetail.KeyBusinessContactInfo.NewContact.add(newContact);

                    KYCMain connectMain = (KYCMain) getActivity();
                    if (connectMain != null) {
                        connectMain.getPcModelData().setValue(mpcModel);
                    }
                }
            }
        }


    }
}
