package quay.com.ipos.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 13-06-2018.
 */

public class InformationDialog extends Dialog
{
    Context mContext;
    ProductSearchResult.Datum datum;
    ImageView imvClose;
    TextView tvHSN,tvGST,tvMRP,tvGPL,tvNRV,tvSalesPrice,tvCategory,tvSubCategory,tvBrand,tvCGST,tvSGST;
    LinearLayout llBrand;


    public InformationDialog(Context mContext, ProductSearchResult.Datum datum)
    {
        super(mContext);
        dismiss();
        this.mContext = mContext;
        this.datum = datum;

        //     ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(mContext,);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_info, null);
        tvHSN = dialogView.findViewById(R.id.tvHSN);
        tvGST = dialogView.findViewById(R.id.tvGST);
        tvMRP = dialogView.findViewById(R.id.tvMRP);
        tvNRV = dialogView.findViewById(R.id.tvNRV);
        tvGPL = dialogView.findViewById(R.id.tvGPL);
        tvSGST = dialogView.findViewById(R.id.tvSGST);
        tvCGST = dialogView.findViewById(R.id.tvCGST);
        tvSalesPrice = dialogView.findViewById(R.id.tvSalesPrice);
        tvCategory = dialogView.findViewById(R.id.tvCategory);
        tvSubCategory = dialogView.findViewById(R.id.tvSubCategory);
        llBrand = dialogView.findViewById(R.id.llBrand);
        tvBrand = dialogView.findViewById(R.id.tvBrand);
        imvClose = dialogView.findViewById(R.id.imvClose);
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if(datum.getBrandName()!=null) {
            if (!datum.getBrandName().equalsIgnoreCase("")) {
                llBrand.setVisibility(View.VISIBLE);
                tvBrand.setText(datum.getBrandName());
            } else {
                llBrand.setVisibility(View.GONE);
            }
        }else {
            llBrand.setVisibility(View.GONE);
        }

        tvHSN.setText(datum.getHsnName() + " ( "+datum.getHsnCode()+ " ) ");
        tvGST.setText(datum.getGstPerc()+" %");
        tvSGST.setText(datum.getSgst()+" %");
        tvCGST.setText(datum.getCgst()+" %");
        tvMRP.setText(mContext.getResources().getString(R.string.Rs) + " " +datum.getMrp());
        tvGPL.setText(mContext.getResources().getString(R.string.Rs) + " " +datum.getGpl());
        tvNRV.setText(mContext.getResources().getString(R.string.Rs) + " " + datum.getNrv());
        tvSalesPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + datum.getSalesPrice());
        tvCategory.setText(datum.getCategoryName());
        tvSubCategory.setText(datum.getSubCategoryName());



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(),R.style.app_custom_dialog_theme);
        alertDialog.setView(dialogView);

        alertDialog.create();
        setCanceledOnTouchOutside(false);


        if (isShowing()) {
            dismiss();
        } else {
            show();
        }

    }

}