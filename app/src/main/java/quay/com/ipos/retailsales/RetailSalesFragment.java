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

import quay.com.ipos.R;
import quay.com.ipos.ui.FontManager;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */

public class RetailSalesFragment extends Fragment {
    private TextView tvUserAdd,tvPin,tvRedeem;
    private ImageView imvQRCode,imvGlobe;
    private SwitchCompat switchButton;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_product_list_item, container, false);
        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View rootView) {
//        tvUserAdd = rootView.findViewById(R.id.tvUserAdd);
//        tvPin = rootView.findViewById(R.id.tvPin);
//        tvRedeem = rootView.findViewById(R.id.tvRedeem);
//        imvGlobe = rootView.findViewById(R.id.imvGlobe);
//        imvQRCode = rootView.findViewById(R.id.imvQRCode);
//
//        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
//        FontManager.markAsIconContainer(tvUserAdd, iconFont);
//        FontManager.markAsIconContainer(tvPin, iconFont);
//        FontManager.markAsIconContainer(tvRedeem, iconFont);

    }
}
