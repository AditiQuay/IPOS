package quay.com.ipos.utility;

import java.util.regex.Pattern;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;

public class ValidateUtils {

    public static boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            //if(phone.length() < 6 || phone.length() > 13) {
                 if(phone.length() != 10) {
                check = false;
               // txtPhone.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }

    public static boolean isNetworkConnected() {
        if (!Util.isConnected()) {
            Util.showToast(IPOSApplication.getContext().getString(R.string.no_internet_connection_warning_server_error));
            return false;
        }
        return true;
    }
}
