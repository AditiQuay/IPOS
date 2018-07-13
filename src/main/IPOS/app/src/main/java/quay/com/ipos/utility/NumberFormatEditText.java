package quay.com.ipos.utility;

import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by deepak.kumar1 on 13-04-2018.
 */

public class NumberFormatEditText {

    public static void updateTextAfterTextChange(EditText editText, String string, TextWatcher textWatcher) {

        editText.removeTextChangedListener(textWatcher);

        try {
            String originalString = string.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,##,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to EditText
            editText.setText(formattedString);
            editText.setSelection(editText.getText().length());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        editText.addTextChangedListener(textWatcher);
    }

    public static void UpdateTextViewAfterTextChange(TextView textView, String string, String prefix) {
        try {
            String originalString = string.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,##,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to EditText
            textView.setText(prefix + formattedString);
            //editText.setSelection(editText.getText().length());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

    }

    public static String getText(String string) {
        try {
            String originalString = string.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = (long)Double.parseDouble(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,##,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to EditText
            return  formattedString;
            //editText.setSelection(editText.getText().length());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();

        }
        return "0";
    }

    //for test
    public static void test() {
        for (long num : new long[]{0, 27, 999, 1000, 110592,
                28991029248L, 9223372036854775807L})
            System.out.printf("%20d: %8s%n", num, withSuffix(num));
    }

    public static String withSuffix(long count) {
        //

        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp - 1));


      /*  double d = (double) count / (double) 1000;
        String str = String.format("%.2f", d);
        return str;*/
    }

    public static String withSuffix(String count) {
        return withSuffix(Long.parseLong(count));
    }

}
