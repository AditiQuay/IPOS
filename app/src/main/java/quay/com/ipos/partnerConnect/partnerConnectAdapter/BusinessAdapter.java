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
import android.widget.TextView;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.partnerConnect.partnerConnectModel.BusinessModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MyView> implements AdapterView.OnItemSelectedListener {
    private Context context;
    private ButtonListener buttonListener;
    private ArrayList<BusinessModel> businessModels;
    private String[] partnerType = {"Retailer","Distributor", "Wholeseller"};
    private String[] partnerKeyPosition = {"Director", "Manager", "Executive"};
    private String partnerTypeText, contactText;

    public BusinessAdapter(Context context, ArrayList<BusinessModel> businessModels, ButtonListener buttonListener) {
        this.context = context;
        this.buttonListener = buttonListener;
        this.businessModels = businessModels;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_item, parent, false);

        return new BusinessAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        BusinessModel businessModel = businessModels.get(position);

        //Creating the ArrayAdapter instance having the partnerType list
        ArrayAdapter partnerTypeHeading = new ArrayAdapter(context, android.R.layout.simple_spinner_item, partnerType);
        partnerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.partnerTypeSpinner.setAdapter(partnerTypeHeading);
        holder.partnerTypeSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the partnerType list
        ArrayAdapter contactPosition = new ArrayAdapter(context, android.R.layout.simple_spinner_item, partnerKeyPosition);
        contactPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.keyContactSpinner.setAdapter(contactPosition);
        holder.keyContactSpinner.setOnItemSelectedListener(this);


        holder.tieCompanyField.setText(businessModel.getPartnerCompanyName());
        holder.tieCinField.setText(businessModel.getPartnerCinNumber());
        holder.tiePanField.setText(businessModel.getPartnerPanNumber());
        holder.tiekeyContactField.setText(businessModel.getPartnerKeyContact());
        holder.tieStateField.setText(businessModel.getPartnerState());
        holder.tieCityField.setText(businessModel.getPartnerCity());
        holder.tiePinCodeField.setText(businessModel.getPartnerPinCode());
        holder.tieZoneField.setText(businessModel.getPartnerZone());


    }

    @Override
    public int getItemCount() {
        return businessModels.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());
        if (materialSpinner.getId() == R.id.partnerTypeSpinner) {
            partnerTypeText = selectedSpinner;
        } else if (materialSpinner.getId() == R.id.keyContactSpinner) {
            contactText = selectedSpinner;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class MyView extends RecyclerView.ViewHolder {
        private MaterialSpinner partnerTypeSpinner, keyContactSpinner;

        private TextInputLayout tilCompanyName, tilCinName, tilPanName, tilkeyContactName, tilState, tilCityName, tilPinCodeName, tilZoneName;
        private TextInputEditText tieCompanyField, tieCinField, tiePanField, tiekeyContactField, tieStateField, tieCityField, tiePinCodeField, tieZoneField;

        private TextView textViewBusinessInfoHeading;

        public MyView(View itemView) {
            super(itemView);
            partnerTypeSpinner = itemView.findViewById(R.id.partnerTypeSpinner);
            keyContactSpinner = itemView.findViewById(R.id.keyContactSpinner);
            textViewBusinessInfoHeading = itemView.findViewById(R.id.textViewBusinessInfoHeading);
            //TextInputLayout
            tilCompanyName = itemView.findViewById(R.id.tilCompanyName);
            tilCinName = itemView.findViewById(R.id.tilCinName);
            tilPanName = itemView.findViewById(R.id.tilPanName);
            tilkeyContactName = itemView.findViewById(R.id.tilkeyContactName);
            tilState = itemView.findViewById(R.id.tilState);
            tilCityName = itemView.findViewById(R.id.tilCityName);
            tilPinCodeName = itemView.findViewById(R.id.tilPinCodeName);
            tilZoneName = itemView.findViewById(R.id.tilZoneName);

            //TextInputEdiText
            tieCompanyField = itemView.findViewById(R.id.tieCompanyField);
            tieCinField = itemView.findViewById(R.id.tieCinField);
            tiePanField = itemView.findViewById(R.id.tiePanField);
            tiekeyContactField = itemView.findViewById(R.id.tiekeyContactField);
            tieStateField = itemView.findViewById(R.id.tieStateField);
            tieCityField = itemView.findViewById(R.id.tieCityField);
            tiePinCodeField = itemView.findViewById(R.id.tiePinCodeField);
            tieZoneField = itemView.findViewById(R.id.tieZoneField);


            //Personal
            FontUtil.applyTypeface(partnerTypeSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(keyContactSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilCompanyName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilCinName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilPanName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilkeyContactName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tieCompanyField, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tieCinField, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tiePanField, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tiekeyContactField, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(textViewBusinessInfoHeading, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilState, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilCityName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilPinCodeName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
            FontUtil.applyTypeface(tilZoneName, FontUtil.getTypeFaceRobotTiteliumRegular(context));
        }
    }


}