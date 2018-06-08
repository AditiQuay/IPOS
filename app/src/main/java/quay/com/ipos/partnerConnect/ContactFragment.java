package quay.com.ipos.partnerConnect;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.BusinessAdapter;
import quay.com.ipos.partnerConnect.partnerConnectAdapter.ContactInfoAdapter;
import quay.com.ipos.partnerConnect.partnerConnectModel.ContactModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class ContactFragment extends Fragment implements InitInterface, ButtonListener, View.OnClickListener {
    private static final String TAG = ContactFragment.class.getSimpleName();
    private View main;
    private TextView textViewLastUpdated, textViewMadatory, textViewContactInfoHeading;

    private MaterialSpinner keyPositionSpinner;
    private TextInputLayout tilContactPersonName, tilContactMobileNumName, tilContactSecondaryMobileNumName, tilkeyEmailName, tilkeyNoteName;
    private TextInputEditText tieContactPersonNameField, tieContactMobileNumField, tieSecondaryMobileNumField, tiekeyEmailField, tiekeyNoteField;
    private RecyclerView recyclerViewContactInfo;
    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private Context mContext;
    private Button btnCancel, btnsubmit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.contact_fragment, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        return main;
    }

    @Override
    public void findViewById() {
        textViewMadatory = main.findViewById(R.id.textViewMadatory);
        textViewLastUpdated = main.findViewById(R.id.textViewLastUpdated);
        keyPositionSpinner = main.findViewById(R.id.keyPositionSpinner);
        tilContactPersonName = main.findViewById(R.id.tilContactPersonName);
        tilContactMobileNumName = main.findViewById(R.id.tilContactMobileNumName);
        tilContactSecondaryMobileNumName = main.findViewById(R.id.tilContactSecondaryMobileNumName);
        tilkeyEmailName = main.findViewById(R.id.tilkeyEmailName);
        tilkeyNoteName = main.findViewById(R.id.tilkeyNoteName);
        tieContactPersonNameField = main.findViewById(R.id.tieContactPersonNameField);
        tieContactMobileNumField = main.findViewById(R.id.tieContactMobileNumField);
        tieSecondaryMobileNumField = main.findViewById(R.id.tieSecondaryMobileNumField);
        tiekeyEmailField = main.findViewById(R.id.tiekeyEmailField);
        tiekeyNoteField = main.findViewById(R.id.tiekeyNoteField);
        btnCancel = getActivity().findViewById(R.id.btnCancel);
        btnsubmit = getActivity().findViewById(R.id.btnsubmit);
        textViewContactInfoHeading = main.findViewById(R.id.textViewContactInfoHeading);

        recyclerViewContactInfo = main.findViewById(R.id.recyclerViewContactInfo);

        btnCancel.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
    }

    @Override
    public void applyInitValues() {
        contactModels.clear();

        ContactModel contactModel = new ContactModel();
        contactModel.setRole("");
        contactModel.setName("");
        contactModel.setPrimaryMobile("");
        contactModel.setSecondaryMobile("");
        contactModels.add(contactModel);

     //   recyclerViewContactInfo.setHasFixedSize(true);
        recyclerViewContactInfo.setLayoutManager(new LinearLayoutManager(mContext));
       // ContactInfoAdapter contactInfoAdapter = new ContactInfoAdapter(mContext, contactModels, this);
     //   recyclerViewContactInfo.setAdapter(contactInfoAdapter);
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
        FontUtil.applyTypeface(tieContactPersonNameField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactPersonNameField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactMobileNumField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieSecondaryMobileNumField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tiekeyEmailField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tiekeyNoteField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(recyclerViewContactInfo, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewContactInfoHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onAdd(int position, String firstName, String lastName, String childGender, String childDOB) {

    }

    @Override
    public void onPartnerAdd(int position, String distributerType, String companyName, String cinNumber, String panNumber, String contactPerson, String contactPosition, String partnerState, String partnerCity, String partnerPin, String partnerZone) {

    }

    @Override
    public void onContactAdd(int position, String role, String name, String primaryMobileNum, String secondaryMobileNum) {

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
                    pcModel = pcModel;
                    setData(pcModel);

                }
            });
        }
    }
    private void setData(PCModel pcModel) {
        if (pcModel == null && pcModel.Business == null) {
            Log.i(TAG, "pcModel or pcModel.Business is null");
            return;
        }


        recyclerViewContactInfo.setAdapter(new ContactInfoAdapter(getActivity(), pcModel.Contact.KeyBusinessContactInfo, ContactFragment.this));
    }
}
