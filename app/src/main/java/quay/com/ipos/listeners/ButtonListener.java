package quay.com.ipos.listeners;

/**
 * Created by niraj.kumar on 6/1/2018.
 */

public interface ButtonListener {
    void onAdd(int position,String firstName,String lastName,String childGender,String childDOB);
    void onPartnerAdd(int position,String distributerType,String companyName,String cinNumber,String panNumber,String contactPerson,String contactPosition,String partnerState,String partnerCity,String partnerPin,String partnerZone);
    void onContactAdd(int position,String role,String name,String primaryMobileNum,String secondaryMobileNum);

}
