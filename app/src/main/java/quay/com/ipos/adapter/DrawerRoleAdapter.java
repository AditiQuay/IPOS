package quay.com.ipos.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.DrawerModal;
import quay.com.ipos.modal.DrawerRoleModal;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 5/15/2018.
 */

public class DrawerRoleAdapter extends ArrayAdapter<DrawerRoleModal> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<DrawerRoleModal> drawerRoleModals;

    public DrawerRoleAdapter(Context mContext, int layoutResourceId, ArrayList<DrawerRoleModal> drawerRoleModals) {
        super(mContext, layoutResourceId, drawerRoleModals);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.drawerRoleModals = drawerRoleModals;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        final View vGrp = listItem.findViewById(R.id.viewP);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewP);


        DrawerRoleModal folder = drawerRoleModals.get(position);
        int UnSelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
        int SelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());

        if (folder.isSelected()) {
            textViewName.setText(folder.name);
            vGrp.setBackgroundColor(mContext.getResources().getColor(R.color.menu_strip));
            textViewName.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
            textViewName.setBackgroundResource(R.drawable.menu_background_select);
            folder.setSelected(true);


        } else {
            textViewName.setText(folder.name);
            vGrp.setBackgroundColor(mContext.getResources().getColor(R.color.transparent_color));
            textViewName.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewName.setBackgroundResource(R.drawable.menu_background_unselect);
            folder.setSelected(false);
        }


        FontUtil.applyTypeface(textViewName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        return listItem;
    }
}

