package quay.com.ipos.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.modal.DrawerModal;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class DrawerItemCustomAdapter extends ArrayAdapter<DrawerModal> {

    private Context mContext;
    private int layoutResourceId;
    private DrawerModal data[] = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, DrawerModal[] data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);
        final View vGrp = listItem.findViewById(R.id.vGrp);

        DrawerModal folder = data[position];

        imageViewIcon.setImageResource(folder.icon);
        textViewName.setText(folder.name);

        FontUtil.applyTypeface(textViewName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        return listItem;
    }
}
