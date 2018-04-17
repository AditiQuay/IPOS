package quay.com.ipos.retailsales;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.ui.FontManager;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */

public class RetailSalesFragment extends Fragment implements View.OnClickListener {
    private TextView tvUserAdd,tvPin,tvRedeem;
    private ImageView imvDicount,imvGlobe,imvQRCode;
    private ToggleButton chkBarCode;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.retail_dashboard, container, false);
        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        tvUserAdd = rootView.findViewById(R.id.tvUserAdd);
        tvPin = rootView.findViewById(R.id.tvPin);
        tvRedeem = rootView.findViewById(R.id.tvRedeem);
        imvGlobe = rootView.findViewById(R.id.imvGlobe);
        imvDicount = rootView.findViewById(R.id.imvDicount);
        imvQRCode = rootView.findViewById(R.id.imvQRCode);
        chkBarCode = rootView.findViewById(R.id.chkBarCode);

        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(tvUserAdd, iconFont);
        FontManager.markAsIconContainer(tvPin, iconFont);
        FontManager.markAsIconContainer(tvRedeem, iconFont);
        imvQRCode.setOnClickListener(this);
        chkBarCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chkBarCode.isChecked()) {
                    imvQRCode.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "ON", Toast.LENGTH_SHORT).show();
                } else {
                    imvQRCode.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.imvQRCode:
                ((MainActivity) getActivity()).launchActivity(FullScannerActivity.class);
                break;
        }
    }
}
