package quay.com.ipos.partnerConnect.partnerConnectAdapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.partnerConnect.model.NewContact;
import quay.com.ipos.utility.FontUtil;

public class ContactInfoAdapter extends RecyclerView.Adapter<ContactInfoAdapter.MyView>  {
    private Context mContext;
    private List<NewContact> list = new ArrayList<>();


    private String roleTypeText;
    private List<String> listPosition = new ArrayList<>();

   /* 1 SuperAdmin NULL
2 Admin NULL
3 User NULL
4 Approver NULL
5 Management NULL*/
    private int[] roleID = {1,2,3,4,5};
    private String[] roleArray = {"SuperAdmin", "Admin", "User","Approver","Management"};
    private ArrayAdapter partnerTypeHeading;


    public ContactInfoAdapter(Context mContext, List<NewContact> contactModels) {
        this.mContext = mContext;
        listPosition = Arrays.asList(roleArray);
        this.list = contactModels;

        //Creating the ArrayAdapter instance having the mPartnerType list
        partnerTypeHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, roleArray);
        partnerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_info_item, parent, false);

        return new MyView(itemView, new MyCustomEditTextListener(), new MyCustomSpinnerListener());
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        NewContact contactModel = list.get(position);





       // magic code for editText
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
        holder.editName.setText(list.get(holder.getAdapterPosition()).Name);
        holder.editMobile.setText(list.get(holder.getAdapterPosition()).PrimaryMobile);
        holder.editMobile2.setText(list.get(holder.getAdapterPosition()).SecondaryMobile);

        // magic code for spinner
        holder.myCustomSpinnerListener.updatePosition(holder.getAdapterPosition());
        if (contactModel.Role != null) {
            String strRole = list.get(holder.getAdapterPosition()).Role;
            if (listPosition.contains(strRole)) {
                int index = listPosition.indexOf(contactModel.Role);
                holder.roleTypeSpinner.setSelection(index + 1);
            }
        }
      //  holder.roleTypeSpinner.setSelection(list.get(holder.getAdapterPosition()).Name);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyView extends RecyclerView.ViewHolder {
        private MaterialSpinner roleTypeSpinner;
        private TextInputLayout editNameInput, editMobileInput, editMobile2Input;
        private TextInputEditText editName, editMobile, editMobile2;

        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomSpinnerListener myCustomSpinnerListener;

        public MyView(View itemView, MyCustomEditTextListener myCustomEditTextListener, MyCustomSpinnerListener myCustomSpinnerListener) {
            super(itemView);
            roleTypeSpinner = itemView.findViewById(R.id.roleTypeSpinner);
            roleTypeSpinner.setAdapter(partnerTypeHeading);

            editNameInput = itemView.findViewById(R.id.editNameInput);
            editMobileInput = itemView.findViewById(R.id.editMobileInput);
            editMobile2Input = itemView.findViewById(R.id.editMobile2Input);

            editName = itemView.findViewById(R.id.editName);
            editMobile = itemView.findViewById(R.id.editMobile);
            editMobile2 = itemView.findViewById(R.id.editMobile2);

            //magic code for editText
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.editName.addTextChangedListener(myCustomEditTextListener);
            this.editMobile.addTextChangedListener(myCustomEditTextListener);
            this.editMobile2.addTextChangedListener(myCustomEditTextListener);

            //magic code for spinner
            this.myCustomSpinnerListener = myCustomSpinnerListener;
            this.roleTypeSpinner.setOnItemSelectedListener(myCustomSpinnerListener);


            //Personal
            FontUtil.applyTypeface(roleTypeSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(editNameInput, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(editMobileInput, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(editMobile2Input, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(editName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(editMobile, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
            FontUtil.applyTypeface(editMobile2, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
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

                if (holder.editName != null) {
                    if (holder.editName.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).Name = charSequence.toString();
                    }
                }
                if (holder.editMobile != null) {
                    if (holder.editMobile.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).PrimaryMobile = charSequence.toString();
                    }
                }
                if (holder.editMobile2 != null) {
                    if (holder.editMobile2.getText().hashCode() == charSequence.hashCode()) {
                        list.get(position).SecondaryMobile = charSequence.toString();
                    }
                }
             /*if (holder.editName.getText().hashCode() == charSequence.hashCode()) {
                list.get(position).Name = charSequence.toString();
             }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

            // no op
        }
    }

    private class MyCustomSpinnerListener implements AdapterView.OnItemSelectedListener {
        private int position;
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch(adapterView.getId()) {
                case R.id.roleTypeSpinner:
                    if(i!=-1) {
                        list.get(position).Role = listPosition.get(i);
                        list.get(position).RoleID = roleID[i]+"";
                    }else {
                        list.get(position).RoleID =  "";
                        list.get(position).Role = "";
                    }

                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        public void updatePosition(int position) {
            this.position = position;
        }
    }
}
