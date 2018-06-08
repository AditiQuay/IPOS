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

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.partnerConnect.model.Cheques;
import quay.com.ipos.partnerConnect.partnerConnectModel.AccountsModel;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyView> implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    private List<Cheques> accountsModels;
    private String [] accountHolderName = {"KYC Traders"};
    private String[] branchList = {"New Delhi","Gurgaon"};
    private String[] securityCheck = {"Yes","No"};
    private String[] chequeNumber={"002463","983458","093234"};
    private ButtonListener buttonListener;
    String checkNumberText;
    String securityCheckText;
    String branchAddText;
    String accountHolder;
    public AccountAdapter(Context context, List<Cheques> list, ButtonListener buttonListener) {
        this.mContext = context;
        this.buttonListener = buttonListener;
        this.accountsModels = list;
    }

    @Override
    public AccountAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.accounts_item, parent, false);

        return new AccountAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        Cheques accountsModel = accountsModels.get(position);

        //Creating the ArrayAdapter instance having the accounts list
        ArrayAdapter accountAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, accountHolderName);
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.accounHolderSpinner.setAdapter(accountAdapter);
        holder.accounHolderSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the branch list
        ArrayAdapter branchAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, branchList);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.branchAddressSpinner.setAdapter(branchAdapter);
        holder.branchAddressSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the securityCheck list
        ArrayAdapter securityAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, securityCheck);
        securityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.securityCheckSpinner.setAdapter(securityAdapter);
        holder.securityCheckSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the securityCheck list
        ArrayAdapter chequeNumAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, chequeNumber);
        chequeNumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.securityCheckSpinner.setAdapter(chequeNumAdapter);
        holder.securityCheckSpinner.setOnItemSelectedListener(this);


        holder.tieAccountField.setText("");
        holder.tieaccounTypeField.setText("");
        holder.tieBankField.setText("");
        holder.tieifscField.setText("");
        holder.tieDrawanOnField.setText("");
        holder.tieMaxLimtField.setText(accountsModel.MaxLimitAmount);
    }

    @Override
    public int getItemCount() {
        return accountsModels.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());

        if (materialSpinner.getId() == R.id.chequeNumSpinner) {
            checkNumberText = selectedSpinner;
        } else if (materialSpinner.getId() == R.id.securityCheckSpinner) {
            securityCheckText = selectedSpinner;
        }else if (materialSpinner.getId()==R.id.branchAddressSpinner){
            branchAddText = selectedSpinner;
        }else if (materialSpinner.getId()==R.id.accounHolderSpinner){
            accountHolder= selectedSpinner;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyView extends RecyclerView.ViewHolder {
        private MaterialSpinner chequeNumSpinner,securityCheckSpinner,branchAddressSpinner,accounHolderSpinner;
        private TextInputLayout tilMaxLimit,tilDrawnOnName,tilifscCode,tilBankName,tilAccountType,tilAccountName;
        private TextInputEditText tieMaxLimtField,tieDrawanOnField,tieifscField,tieBankField,tieaccounTypeField,tieAccountField;
        public MyView(View itemView) {
            super(itemView);
            chequeNumSpinner = itemView.findViewById(R.id.chequeNumSpinner);
            securityCheckSpinner = itemView.findViewById(R.id.securityCheckSpinner);
            branchAddressSpinner = itemView.findViewById(R.id.branchAddressSpinner);
            accounHolderSpinner = itemView.findViewById(R.id.accounHolderSpinner);
            tilMaxLimit = itemView.findViewById(R.id.tilMaxLimit);
            tilDrawnOnName = itemView.findViewById(R.id.tilDrawnOnName);
            tilifscCode = itemView.findViewById(R.id.tilifscCode);
            tilBankName = itemView.findViewById(R.id.tilBankName);
            tilAccountType = itemView.findViewById(R.id.tilAccountType);
            tilAccountName = itemView.findViewById(R.id.tilAccountName);
            tieMaxLimtField = itemView.findViewById(R.id.tieMaxLimtField);
            tieDrawanOnField = itemView.findViewById(R.id.tieDrawanOnField);
            tieifscField = itemView.findViewById(R.id.tieifscField);
            tieBankField = itemView.findViewById(R.id.tieBankField);
            tieaccounTypeField = itemView.findViewById(R.id.tieaccounTypeField);
            tieAccountField = itemView.findViewById(R.id.tieAccountField);

            //Personal
            FontUtil.applyTypeface(chequeNumSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(securityCheckSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(branchAddressSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(accounHolderSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilMaxLimit, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilDrawnOnName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilifscCode, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilBankName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilAccountType, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tilAccountName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieMaxLimtField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieDrawanOnField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieifscField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieBankField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieaccounTypeField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(tieAccountField, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        }
    }
}
