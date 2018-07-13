package quay.com.ipos.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.modal.DrawerModal;
import quay.com.ipos.modal.DrawerRoleModal;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 5/15/2018.
 */

public class DrawerRoleAdapter extends RecyclerView.Adapter<DrawerRoleAdapter.ViewHolder> {

    private Context mContext;
    private List<DrawerRoleModal> drawerRoleModals = new ArrayList<>();
    private LayoutInflater layoutInflater;
    int UnSelectSize;
    int SelectSize;
    private OnRoleSelectionListener listener;

    public DrawerRoleAdapter(Context mContext, List<DrawerRoleModal> drawerRoleModals,OnRoleSelectionListener listener) {
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.drawerRoleModals = drawerRoleModals;
        UnSelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
        SelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());
        this.listener = listener;

    }

  /*  @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        final View vGrp = listItem.findViewById(R.id.viewP);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewP);


        DrawerRoleModal drawerRoleModal = drawerRoleModals.get(position);
        int UnSelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
        int SelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());

        if (drawerRoleModal.isSelected()) {
            textViewName.setText(drawerRoleModal.name);
            vGrp.setBackgroundColor(mContext.getResources().getColor(R.color.menu_strip));
            textViewName.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
            textViewName.setBackgroundResource(R.drawable.menu_background_select);
            drawerRoleModal.setSelected(true);


        } else {
            textViewName.setText(drawerRoleModal.name);
            vGrp.setBackgroundColor(mContext.getResources().getColor(R.color.transparent_color));
            textViewName.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewName.setBackgroundResource(R.drawable.menu_background_unselect);
            drawerRoleModal.setSelected(false);
        }


        FontUtil.applyTypeface(textViewName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        return listItem;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.drawer_role_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DrawerRoleModal drawerRoleModal = drawerRoleModals.get(position);
        holder.textViewName.setText(drawerRoleModal.name);


        if (drawerRoleModal.isSelected()) {
            holder.vGrp.setBackgroundColor(mContext.getResources().getColor(R.color.menu_strip));
           // holder.textViewName.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
            holder.textViewName.setBackgroundResource(R.drawable.menu_background_select);


        } else {
            holder.vGrp.setBackgroundColor(mContext.getResources().getColor(R.color.transparent_color));
          //  holder.textViewName.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            holder.textViewName.setBackgroundResource(R.drawable.menu_background_unselect);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerRoleModal.isSelected()) {
                    if (listener != null) {
                        listener.onRoleSelected(drawerRoleModal,position);
                    }
                    return;
                }else {
                    setAllFalse();
                    drawerRoleModal.setSelected(!drawerRoleModal.isSelected());
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onRoleSelected(drawerRoleModal, position);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return drawerRoleModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        View vGrp;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewP);
            vGrp = itemView.findViewById(R.id.viewP);

        }
    }

    private void setAllFalse() {
        for (DrawerRoleModal drawerRoleModal : drawerRoleModals) {
            drawerRoleModal.setSelected(false);
        }
    }
    public interface OnRoleSelectionListener{
        void onRoleSelected(DrawerRoleModal drawerRoleModal,int position);
    }
}

