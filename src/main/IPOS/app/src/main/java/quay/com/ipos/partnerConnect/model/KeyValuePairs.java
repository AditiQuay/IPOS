package quay.com.ipos.partnerConnect.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class KeyValuePairs {
    public List<KeyValueModel> partnerTypes;
    public List<KeyValueModel> accountTypes;
    public List<KeyValueModel> roleTypes;
    public List<KeyValueModel> addressTypes;
    public List<KeyValueModel> businessPlaceTypes;
    public List<KeyValueModel> designationTypes;

    //partnerTypes
    public static final String partnerTypesStringKEY[] = {"DL", "D", "R"};
    public static final String partnerTypesStringVALUE[] = {"Dealer", "Distributor", "Retailer"};
    //accountTypes
    public static final String accountTypesStringKEY[] = {"SAVING", "CURRENT"};
    public static final String accountTypesStringVALUE[] = {"Saving", "Current"};
    //roleTypes
    public static final String roleTypesStringKEY[] = {"1", "2", "3", "4", "5"};
    public static final String roleTypesStringVALUE[] = {"SuperAdmin", "Admin", "User", "Approver", "Management"};
    //addressTypes
    public static final String addressTypesStringKEY[] = {"1"};
    public static final String addressTypesStringVALUE[] = {"Bill & Deliver"};
    //roleTypes
    public static final String businessPlaceTypesStringKEY[] = {"1", "2"};
    public static final String businessPlaceTypesStringVALUE[] = {"Shop/Store", "Warehouse"};
    //roleTypes
    public static final String designationTypesStringKEY[] = {"1", "2", "3"};
    public static final String designationTypesStringVALUE[] = {"Director", "Manager", "Executive"};

    /**
     * Set Default Value to key if no data found from server
     */
    public void setDefaultValue() {

        setPartnerDefaultValue();
        setAccountDefaultValue();
        setRoleDefaultValue();
        setAddressDefaultValue();
        setDesignationDefaultValue();
        setBusinessPlaceDefaultValue();


    }

    public void setPartnerDefaultValue() {
        if (partnerTypes == null || partnerTypes.isEmpty()) {
            Log.i("Sorry", "PartnerType Value is present");
            if (partnerTypesStringKEY.length == partnerTypesStringVALUE.length) {
                partnerTypes = new ArrayList<>();
                for (int i = 0; i < partnerTypesStringKEY.length; i++) {
                    partnerTypes.add(new KeyValueModel(partnerTypesStringKEY[i], partnerTypesStringVALUE[i]));
                }
            }
        } else {
            Log.i("Smile", "PartnerType Value is present");
        }
    }

    public void setAccountDefaultValue() {
        if (accountTypes == null || accountTypes.isEmpty()) {
            Log.i("Sorry", "accountType Value is present");
            if (accountTypesStringKEY.length == accountTypesStringVALUE.length) {
                accountTypes = new ArrayList<>();
                for (int i = 0; i < accountTypesStringKEY.length; i++) {
                    accountTypes.add(new KeyValueModel(accountTypesStringKEY[i], accountTypesStringVALUE[i]));
                }
            }
        } else {
            Log.i("Smile", "accountType Value is present");
        }
    }

    public void setRoleDefaultValue() {
        if (roleTypes == null || roleTypes.isEmpty()) {
            Log.i("Sorry", "roleType Value is present");
            if (roleTypesStringKEY.length == roleTypesStringVALUE.length) {
                roleTypes = new ArrayList<>();
                for (int i = 0; i < roleTypesStringKEY.length; i++) {
                    roleTypes.add(new KeyValueModel(roleTypesStringKEY[i], roleTypesStringVALUE[i]));
                }
            }
        } else {
            Log.i("Smile", "roleType Value is present");
        }
    }

    public void setAddressDefaultValue() {
        if (addressTypes == null || addressTypes.isEmpty()) {
            Log.i("Sorry", "addressType Value is present");
            if (addressTypesStringKEY.length == addressTypesStringVALUE.length) {
                addressTypes = new ArrayList<>();
                for (int i = 0; i < addressTypesStringKEY.length; i++) {
                    addressTypes.add(new KeyValueModel(addressTypesStringKEY[i], addressTypesStringVALUE[i]));
                }
            }
        } else {
            Log.i("Smile", "addressType Value is present");
        }
    }

    public void setBusinessPlaceDefaultValue() {
        if (businessPlaceTypes == null || businessPlaceTypes.isEmpty()) {
            Log.i("Sorry", "businessPlaceType Value is present");
            if (businessPlaceTypesStringKEY.length == businessPlaceTypesStringVALUE.length) {
                businessPlaceTypes = new ArrayList<>();
                for (int i = 0; i < businessPlaceTypesStringKEY.length; i++) {
                    businessPlaceTypes.add(new KeyValueModel(businessPlaceTypesStringKEY[i], businessPlaceTypesStringVALUE[i]));
                }
            }
        } else {
            Log.i("Smile", "businessPlaceType Value is present");
        }
    }

    public void setDesignationDefaultValue() {
        if (designationTypes == null || designationTypes.isEmpty()) {
            Log.i("Sorry", "designationType Value is present");
            if (designationTypesStringKEY.length == designationTypesStringVALUE.length) {
                designationTypes = new ArrayList<>();
                for (int i = 0; i < designationTypesStringKEY.length; i++) {
                    designationTypes.add(new KeyValueModel(designationTypesStringKEY[i], designationTypesStringVALUE[i]));
                }
            }
        } else {
            Log.i("Smile", "designationType Value is present");
        }
    }


}
