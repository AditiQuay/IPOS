package quay.com.ipos.partnerConnect.partnerConnectAdapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.partnerConnect.model.Cheques;

/**
 * Created by niraj.kumar on 6/7/2018.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyView> {
    private Context mContext;
    private List<Cheques> list = new ArrayList<>();


    //private String[] securityCheck = {"Yes", "No"};
 //   private List<String> mSecurityChequesList = new ArrayList<>();
    String checkNumberText;
    String securityCheckText;
    String branchAddText;
    String accountHolder;

    //private ArrayAdapter securityAdapter;

    public AccountAdapter(Context context) {
        this.mContext = context;
        this.list = new ArrayList<>();
        //Creating the ArrayAdapter instance having the securityCheck list
      //  securityAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, securityCheck);
      //  securityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //mSecurityChequesList = Arrays.asList(securityCheck);
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.accounts_item, parent, false);


        return new MyView(itemView, new MyCustomEditTextListener());//, new MyCustomSpinnerListener());

    }

    @Override
    public void onBindViewHolder(MyView holder, final int position) {
        Cheques accountsModel = list.get(position);
      //  holder.spinnerSecurityCheque.setAdapter(securityAdapter);


        // magic code for editText
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
        holder.editChequeNo.setText(list.get(holder.getAdapterPosition()).mChequeNo);
        holder.editMaxLimit.setText(list.get(holder.getAdapterPosition()).mMaxLimitAmount);
        holder.editDrawnAccountNo.setText(list.get(holder.getAdapterPosition()).mDrawnAccountNo);

        // magic code for spinner
       // holder.myCustomSpinnerListener.updatePosition(holder.getAdapterPosition());

     /*   String mAddressType = list.get(holder.getAdapterPosition()).mSecurityCheque;
        if (mAddressType == null || mAddressType.isEmpty()) {
            mAddressType = "No";
        }*/

       /* if (accountsModel.mChequeNo != null && !accountsModel.mChequeNo.isEmpty()) {
            mAddressType = "Yes";
        }


        if (mAddressType != null) {
            if (mSecurityChequesList.contains(mAddressType)) {
                int index = mSecurityChequesList.indexOf(mAddressType);
                holder.spinnerSecurityCheque.setSelection(index + 1);
            }
        }*/

        if (accountsModel.ID == 0 && position != 0) {
            holder.btnRemove.setVisibility(View.VISIBLE);
        } else {
            holder.btnRemove.setVisibility(View.GONE);
        }

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


  /*  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());

        if (materialSpinner.getId() == R.id.editChequeNo) {
            checkNumberText = selectedSpinner;
        } else if (materialSpinner.getId() == R.id.spinnerSecurityCheque) {
            securityCheckText = selectedSpinner;
        } else if (materialSpinner.getId() == R.id.editBranchAddress) {
            branchAddText = selectedSpinner;
        } else if (materialSpinner.getId() == R.id.editAccountHolderName) {
            accountHolder = selectedSpinner;
        }
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    public void loadData(List<Cheques> chequesList) {
        this.list = chequesList;
        notifyDataSetChanged();
    }

    public class MyView extends RecyclerView.ViewHolder {
     //   private MaterialSpinner spinnerSecurityCheque;
        private TextInputEditText editChequeNo, editMaxLimit, editDrawnAccountNo;
        public MyCustomEditTextListener myCustomEditTextListener;
       // public MyCustomSpinnerListener myCustomSpinnerListener;
        public View btnRemove;


        public MyView(View itemView, MyCustomEditTextListener myCustomEditTextListener){//, MyCustomSpinnerListener myCustomSpinnerListener) {
            super(itemView);
          //  spinnerSecurityCheque = itemView.findViewById(R.id.spinnerSecurityCheque);
            editDrawnAccountNo = itemView.findViewById(R.id.editDrawnAccountNo);
            editChequeNo = itemView.findViewById(R.id.editChequeNo);
            editMaxLimit = itemView.findViewById(R.id.editMaxLimit);
            btnRemove = itemView.findViewById(R.id.btnRemove);

            //magic code for editText
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.editChequeNo.addTextChangedListener(myCustomEditTextListener);
            this.editMaxLimit.addTextChangedListener(myCustomEditTextListener);
            this.editDrawnAccountNo.addTextChangedListener(myCustomEditTextListener);

            //magic code for spinner
          //  this.myCustomSpinnerListener = myCustomSpinnerListener;
            //this.spinnerSecurityCheque.setOnItemSelectedListener(myCustomSpinnerListener);


        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private MyView holder;

        public void updatePosition(int position, MyView holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try {

                if (holder.editChequeNo != null) {
                    if (holder.editChequeNo.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mChequeNo = charSequence.toString();
                    }
                }
                if (holder.editDrawnAccountNo != null) {
                    if (holder.editDrawnAccountNo.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mDrawnAccountNo = charSequence.toString();
                    }
                }
                if (holder.editMaxLimit != null) {
                    if (holder.editMaxLimit.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).mMaxLimitAmount = charSequence.toString();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

            // no op
        }
    }

    /*private class MyCustomSpinnerListener implements AdapterView.OnItemSelectedListener {
        private int position;

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (adapterView.getId()) {
                case R.id.spinnerSecurityCheque:
                    if (i != -1)
                        list.get(position).mSecurityCheque = mSecurityChequesList.get(i);
                    else
                        list.get(position).mSecurityCheque = "No";

                    break;

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            switch (adapterView.getId()) {
                case R.id.spinnerSecurityCheque:
                    list.get(position).mSecurityCheque = "No";
                    break;
            }
        }

        public void updatePosition(int position) {
            this.position = position;
        }2
    }*/
}
