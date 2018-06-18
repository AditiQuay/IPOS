package quay.com.ipos.partnerConnect.kyc.kycAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import quay.com.ipos.R;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.partnerConnect.kyc.model.KycPSSDetailsModel;

/**
 * Created by niraj.kumar on 6/15/2018.
 */

public class KycPssDetailAdapter extends RecyclerView.Adapter<KycPssDetailAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<KycPSSDetailsModel> kycPSSDetailsModels;
    private MyListener myListener;

    public KycPssDetailAdapter(Context context, ArrayList<KycPSSDetailsModel> kycPSSDetailsModels, MyListener myListener) {
        this.mContext = context;
        this.kycPSSDetailsModels = kycPSSDetailsModels;
        this.myListener = myListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kyc_status, parent, false);

        return new KycPssDetailAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        KycPSSDetailsModel kycPSSDetailsModel = kycPSSDetailsModels.get(position);
        holder.textViewKycNumber.setText(kycPSSDetailsModel.getREQUEST_CODE());
//        holder.textViewKycPendingTime.setText(kycPSSDetailsModel.getCREATED_DATE());

        StringTokenizer tokens = new StringTokenizer(kycPSSDetailsModel.getSectionChanged(), "|");
        String first = tokens.nextToken();// this will contain "Fruit"
        String second = tokens.nextToken();// this will contain " they taste good"
        holder.textViewBank.setText(first);
        holder.textViewDocumentsText.setText(second);


        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onRowClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kycPSSDetailsModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViewProduct;
        private ImageView iconiv, imBank, imDocument;
        private TextView textViewHeader, textViewKycNumber, textViewKycPendingTime, textViewKycPartner, textViewDocumentsText, textViewKycStatus, textViewKycPendingSince, textViewBank;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardViewProduct = itemView.findViewById(R.id.cardViewProduct);
            iconiv = itemView.findViewById(R.id.iconiv);
            imBank = itemView.findViewById(R.id.imBank);
            imDocument = itemView.findViewById(R.id.imDocument);

            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            textViewKycNumber = itemView.findViewById(R.id.textViewKycNumber);
            textViewKycPartner = itemView.findViewById(R.id.textViewKycPartner);
            textViewDocumentsText = itemView.findViewById(R.id.textViewDocumentsText);
            textViewKycStatus = itemView.findViewById(R.id.textViewKycStatus);
            textViewKycPendingSince = itemView.findViewById(R.id.textViewKycPendingSince);
            textViewBank = itemView.findViewById(R.id.textViewBank);
            textViewKycPendingTime = itemView.findViewById(R.id.textViewKycPendingTime);
        }
    }
}
