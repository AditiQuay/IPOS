package quay.com.ipos.partnerConnect.partnerConnectAdapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.partnerConnect.model.KeyBusinessContactInfo;
import quay.com.ipos.partnerConnect.partnerConnectModel.ContactModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class ContactInfoAdapter extends RecyclerView.Adapter<ContactInfoAdapter.MyView> implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    private List<KeyBusinessContactInfo> contactModels;
    private ButtonListener buttonListener;
    private String[] contactPosition = {"Director", "Manager", "Executive"};
    private String roleTypeText;

    public ContactInfoAdapter(Context mContext, List<KeyBusinessContactInfo> contactModels, ButtonListener buttonListener) {
        this.mContext = mContext;
        this.buttonListener = buttonListener;
        this.contactModels = contactModels;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_info_item, parent, false);

        return new ContactInfoAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        KeyBusinessContactInfo contactModel = contactModels.get(position);

        //Creating the ArrayAdapter instance having the partnerType list
        ArrayAdapter partnerTypeHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, contactPosition);
        partnerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.roleTypeSpinner.setAdapter(partnerTypeHeading);
        holder.roleTypeSpinner.setOnItemSelectedListener(this);

        holder.tieNameField.setText(contactModel.KeyContactEntityEmpperson);
        holder.tiePrimaryMobileNumber.setText(contactModel.keyEntityEmpMobile1);
        holder.tieSecondaryMobileNumField.setText(contactModel.keyEntityEmpMobile2);
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());
        if (materialSpinner.getId() == R.id.roleTypeSpinner) {
            roleTypeText = selectedSpinner;
        } else {
            roleTypeText = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyView extends RecyclerView.ViewHolder {
        private MaterialSpinner roleTypeSpinner;
        private TextInputLayout tilName, tilPrimaryMobileNo, tilSecondaryMobileNum;
        private TextInputEditText tieNameField, tiePrimaryMobileNumber, tieSecondaryMobileNumField;

        public MyView(View itemView) {
            super(itemView);
            roleTypeSpinner = itemView.findViewById(R.id.roleTypeSpinner);
            tilName = itemView.findViewById(R.id.tilName);
            tilPrimaryMobileNo = itemView.findViewById(R.id.tilPrimaryMobileNo);
            tilSecondaryMobileNum = itemView.findViewById(R.id.tilSecondaryMobileNum);
            tieNameField = itemView.findViewById(R.id.tieNameField);
            tiePrimaryMobileNumber = itemView.findViewById(R.id.tiePrimaryMobileNumber);
            tieSecondaryMobileNumField = itemView.findViewById(R.id.tieSecondaryMobileNumberField);

            //Personal
            FontUtil.applyTypeface(roleTypeSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilPrimaryMobileNo, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilSecondaryMobileNum, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieNameField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tiePrimaryMobileNumber, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieSecondaryMobileNumField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        }
    }
}
