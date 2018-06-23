package quay.com.ipos.utility;

import java.util.regex.Pattern;

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
}
