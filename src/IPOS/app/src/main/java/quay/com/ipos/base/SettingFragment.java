package quay.com.ipos.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quay.com.ipos.R;

/**
 * Created by aditi.bhuranda on 19-06-2018.
 */

public class SettingFragment extends BaseFragment {

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.settings_fragment, container, false);

        return rootView;
    }
}
