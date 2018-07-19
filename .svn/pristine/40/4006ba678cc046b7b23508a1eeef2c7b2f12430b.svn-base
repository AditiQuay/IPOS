package quay.com.ipos.kycPartnerConnect.kycAdapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.partnerConnect.model.PssPrincipleContact;
import quay.com.ipos.utility.ShareWorldUtil;

public class KycRelTwoAdapter extends RecyclerView.Adapter<KycRelTwoAdapter.VH> {
    List<PssPrincipleContact> contactList = new ArrayList<>();

    public KycRelTwoAdapter(List<PssPrincipleContact> pssPrincipleContact) {
        this.contactList = pssPrincipleContact;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_pc_rel_customerdetails, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final PssPrincipleContact contact = contactList.get(position);
        holder.mtxt1.setText(contact.pssContactPersonName);
        holder.mtxt2.setText(contact.pssDesignation);

        holder.btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                /*
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "" + contact.keyEntityEmpMobile1));
                    if (ActivityCompat.checkSelfPermission(((Activity) view.getContext()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ((Activity) view.getContext()).startActivity(intent);
                        return;
                    }*/
                    ShareWorldUtil.dialNumber(IPOSApplication.getAppInstance(), "" + contact.keyEntityEmpMobile1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        holder.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.keyEntityEmpEmail});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    ((Activity) view.getContext()).startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(((Activity) view.getContext()), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

   /* public void setData(List<PssPrincipleContact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }*/

    public class VH extends RecyclerView.ViewHolder {
        TextView mtxt1, mtxt2;
        View btnPhone;
        View btnEmail;

        public VH(View itemView) {
            super(itemView);
            mtxt1 = itemView.findViewById(R.id.mTxt1);
            mtxt2 = itemView.findViewById(R.id.mTxt2);
            btnPhone = itemView.findViewById(R.id.btnPhone);
            btnEmail = itemView.findViewById(R.id.btnEmail);
        }
    }
}
