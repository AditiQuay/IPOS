package quay.com.ipos.compliance.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import quay.com.ipos.R;
import quay.com.ipos.compliance.constants.AnnotationComplianceType;

public class ComplianceFragmentHeader extends Fragment {

    private static String TAG = ComplianceFragmentHeader.class.getSimpleName();
    private static final String ARG_TILTE = "title";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private String mParam2;


    private TextView btnStoreSelection;
    private TextView tvCategorySelectedFilter;
    private View btnCT_All, btnCT_Business, btnCT_Statutory;

    private OnComplianeFilterListener mListener;


    public ComplianceFragmentHeader() {
        // Required empty public constructor

    }


    public static ComplianceFragmentHeader newInstance(String param1, String param2) {
        ComplianceFragmentHeader fragment = new ComplianceFragmentHeader();
        Bundle args = new Bundle();
        args.putString(ARG_TILTE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TILTE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.c_fragment_compliance_header, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvCategorySelectedFilter = view.findViewById(R.id.tvCategorySelectedFilter);
        btnCT_All = view.findViewById(R.id.btnCT_All);
        btnCT_Business = view.findViewById(R.id.btnCT_Business);
        btnCT_Statutory = view.findViewById(R.id.btnCT_Statutory);

        btnStoreSelection = view.findViewById(R.id.btnStoreSelection);
        btnStoreSelection.setText(mTitle + "");

        btnStoreSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // openStoreDialog();
                openDynamicStoreDialog();
            }
        });

        btnCT_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(AnnotationComplianceType.ALL);
                }
            }
        });
        btnCT_Business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(AnnotationComplianceType.BUSINESS);
                }
            }
        });
        btnCT_Statutory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(AnnotationComplianceType.STATUTORY);
                }
            }
        });


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnComplianeFilterListener) {
            mListener = (OnComplianeFilterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnComplianeFilterListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateView(@AnnotationComplianceType.ComplianceType int complianceType) {
        setAllCircleUnselected();
        if (complianceType == AnnotationComplianceType.ALL) {
            tvCategorySelectedFilter.setText("ALL");
            // if (binding != null) {
            btnCT_All.setSelected(true);
            // }
        }
        if (complianceType == AnnotationComplianceType.BUSINESS) {
            tvCategorySelectedFilter.setText("BUSINESS");
            //if (binding != null) {
            btnCT_Business.setSelected(true);
            // }

        }
        if (complianceType == AnnotationComplianceType.STATUTORY) {
            tvCategorySelectedFilter.setText("STATUTORY");
            //  if (binding != null) {
            btnCT_Statutory.setSelected(true);
            //  }
        }
    }

    private void setAllCircleUnselected() {
        // if (binding != null) {
        btnCT_All.setSelected(false);
        btnCT_Business.setSelected(false);
        btnCT_Statutory.setSelected(false);
        // }
    }

    public void hideStoreSelectionView() {
        if (btnStoreSelection != null)
            btnStoreSelection.setVisibility(View.INVISIBLE);
    }


    private void openDynamicStoreDialog() {
        final StoreListDialogFragment dialogFragment = StoreListDialogFragment.newInstance("", 0);
        dialogFragment.show(getChildFragmentManager(), TAG);

    }
   /* private void openStoreDialog() {

        final CharSequence colors[] = new CharSequence[]{"My Store 1", "My Store 2", "My Store 3"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a store");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]


            }
        });

        builder.show();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public interface OnComplianeFilterListener {
        void onClick(@AnnotationComplianceType.ComplianceType int complianceType);
    }
}
