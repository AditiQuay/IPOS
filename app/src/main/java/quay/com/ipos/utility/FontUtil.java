package quay.com.ipos.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Apply font for TextView, EditText, Button, CheckBox, RadioButton, ViewGroup and its child view
 */

public final class FontUtil {

    private FontUtil(){
        throw new Error("Do not need instantiate!");
    }

    /**
     * Get typeface of Roboto-Regular
     * @param context
     * @return
     */
    public static Typeface getTypceFaceRobotoRegular(Context context){
        Typeface typeface_Roboto_Regular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        return typeface_Roboto_Regular;
    }
    /**
     * Get typeface of Roboto-Regular
     * @param context
     * @return
     */

    public static Typeface getTypeFaceRobotTiteliumRegular(Context context){
        Typeface typeface_Roboto_Regular = Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWeb-Regular.ttf");
        return typeface_Roboto_Regular;
    }
    /**
     * Get typeface of Roboto-Medium_0
     * @param context
     * @return
     */
    public static Typeface getTypceFaceRobotoMedium_0(Context context){
        Typeface typeface_Roboto_Regular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        return typeface_Roboto_Regular;
    }
    public static Typeface getTypceFaceRobotoBold(Context context){
        Typeface typeface_Roboto_Regular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        return typeface_Roboto_Regular;
    }
    /**
     * Get typeface of Roboto-Regular
     * @param context
     * @return
     */
    public static Typeface getTypceFaceRobotoLight(Context context){
        Typeface typeface_Roboto_Light = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        return typeface_Roboto_Light;
    }

    /**
     * Apply typeface on control
     * @param view
     * @param typeface
     */
    public static void applyTypeface(final View view, final Typeface typeface) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    applyTypeface(child, typeface);
                }
            } else if (view instanceof TextView) {
                ((TextView)view).setTypeface(typeface);
            } else if (view instanceof EditText){
                ((EditText)view).setTypeface(typeface);
            } else if (view instanceof Button){
                ((Button)view).setTypeface(typeface);
            } else if (view instanceof CheckBox){
                ((CheckBox)view).setTypeface(typeface);
            } else if(view instanceof RadioButton){
                ((RadioButton)view).setTypeface(typeface);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
