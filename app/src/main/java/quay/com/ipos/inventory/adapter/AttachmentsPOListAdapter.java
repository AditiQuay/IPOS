package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.POAttachments;
import quay.com.ipos.realmbean.RealmBusinessPlaces;


public class AttachmentsPOListAdapter extends RecyclerView.Adapter<AttachmentsPOListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<POAttachments> stringArrayList;
    private OnItemSelecteListener mListener;

    public AttachmentsPOListAdapter(Context mContext, ArrayList<POAttachments> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.attachments_items, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {



        holder.tvtitle.setText(stringArrayList.get(position).getpOAttachmentName());




    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvtitle,textViewName;

        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tvtitle);


        }
    }
}
