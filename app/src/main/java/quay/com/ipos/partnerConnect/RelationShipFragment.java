package quay.com.ipos.partnerConnect;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import quay.com.ipos.R;
import quay.com.ipos.partnerConnect.adapter.RelOneAdapter;
import quay.com.ipos.partnerConnect.adapter.RelThreeAdapter;
import quay.com.ipos.partnerConnect.adapter.RelTwoAdapter;
import quay.com.ipos.partnerConnect.model.PCModel;


public class RelationShipFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewOne;
    private RecyclerView recyclerViewTwo;
    private RecyclerView recyclerViewThree;
    private PCModel pcModel;

    private TextView mRelationShipName;
    private TextView mtxtPssEntityName;

    private RelOneAdapter relOneAdapter;
    private RelTwoAdapter relTwoAdapter;
    private RelThreeAdapter relThreeAdapter;


    public RelationShipFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RelationShipFragment newInstance(String param1, String param2) {
        RelationShipFragment fragment = new RelationShipFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_relation_ship, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRelationShipName = view.findViewById(R.id.mRelationShipName);
        mtxtPssEntityName = view.findViewById(R.id.mtxtPssEntityName);
        recyclerViewOne = view.findViewById(R.id.recyclerViewOne);
        recyclerViewTwo = view.findViewById(R.id.recyclerViewTwo);
        recyclerViewThree = view.findViewById(R.id.recyclerViewThree);


        loadData();


    }

   private void loadData() {
        PartnerConnectMain partnerConnectMain = (PartnerConnectMain) getActivity();
        if (partnerConnectMain != null) {
            partnerConnectMain.getPcModelData().observe(this, new Observer<PCModel>() {
                @Override
                public void onChanged(@Nullable PCModel pcModel) {
                    pcModel = pcModel;
                    setData(pcModel);

                }
            });
        }
    }

    private void setData(PCModel pcModel) {
        if (pcModel == null) {
            Log.i("pcModel", "is null");
            return;
        }
        Toast.makeText(getActivity(), "I am here", Toast.LENGTH_SHORT).show();
        mRelationShipName.setText(pcModel.getRelationShipName());
        mtxtPssEntityName.setText(pcModel.Relationship.pssEntityName);
        recyclerViewOne.setAdapter(new RelOneAdapter(pcModel.Relationship.pssLOBS));
        recyclerViewTwo.setAdapter(new RelTwoAdapter(pcModel.Relationship.pssPrincipleContact));
        recyclerViewThree.setAdapter(new RelThreeAdapter(pcModel.Relationship.pssPrincipleBankpaymentTo));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
