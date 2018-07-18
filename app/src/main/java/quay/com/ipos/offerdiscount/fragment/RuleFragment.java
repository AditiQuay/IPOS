package quay.com.ipos.offerdiscount.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.offerdiscount.Model.OfferDiscountModel;
import quay.com.ipos.offerdiscount.Model.RuleModel;
import quay.com.ipos.offerdiscount.adapter.RuleAdapter;
import quay.com.ipos.partnerConnect.PartnerConnectMain;

/**
 * Created by aditi.bhuranda on 12-07-2018.
 */

public class RuleFragment extends BaseFragment  implements View.OnClickListener{

    private View rootView;
   // private ArrayList<RuleModel> ruleModels = new ArrayList<>();
    private RecyclerView rvRule;
    OfferDiscountModel mOfferDiscountModel;
    RuleAdapter ruleAdapter;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.fragment_rule, container, false);

        initializeComponent(rootView);

        //ruleAdapter= new RuleAdapter(getActivity(), ruleModels,this);
       // rvRule.setAdapter(ruleAdapter);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        rvRule = rootView.findViewById(R.id.rvRule);
    }

    public RuleFragment() {

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        OfferDiscountFragment mOfferDiscountFragment = (OfferDiscountFragment) getActivity();
        if (mOfferDiscountFragment != null) {
            mOfferDiscountFragment.getPcModelData().observe(this, new Observer<OfferDiscountModel>() {
                @Override
                public void onChanged( OfferDiscountModel pcModel) {
                    mOfferDiscountModel = pcModel;
                    Log.v("sssssnnns", mOfferDiscountModel.getRuleModels().size() + "");

                   /*
                    ruleModels.clear();
                    ruleModels.addAll(mOfferDiscountModel.getRuleModels());
                    ruleAdapter.notifyDataSetChanged();*/
                   setData(pcModel);
//                    if(mOfferDiscountModel.getRuleModels().size()>0) {
////                        ruleModels.clear();
////                        ruleModels.addAll(mOfferDiscountModel.getRuleModels());
//                    }
//                    setData(pcModel);
                 //   ruleAdapter.notifyDataSetChanged();

                    if(pcModel.getRuleModels().size()==0) {
                        addNewField1();
                    }
                }
            });
        }
    }

    private void setData(OfferDiscountModel pcModel) {
       /* if(pcModel.getRuleModels()!=null){
            if(pcModel.getRuleModels().size()>0){
                addNewField();
            }
        }*/

        rvRule.setAdapter(new RuleAdapter(getActivity(), pcModel.getRuleModels(), this));
        rvRule.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

       /* OfferDiscountFragment mainActivity = (OfferDiscountFragment)getActivity();
        //assert mainActivity != null;
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do what you want
                Toast.makeText(getActivity(), "ffmmf", Toast.LENGTH_SHORT).show();
                addNewField();
            }
        });*/


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {
                // load data here
                getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "ffmmf", Toast.LENGTH_SHORT).show();
                        addNewField();
//                    return;
                    }
                });
            } else {
                // fragment is no longer visible
            }
        }catch (Exception e){

        }
    }

    private void addNewField() {
        if(mOfferDiscountModel!=null) {
//            if(mOfferDiscountModel.getRuleModels()!=null) {
            RuleModel ruleModel = new RuleModel();
            ruleModel.setiMaterialBucketID(0);
            ruleModel.setiOPSScopeID(0);
            ruleModel.setiPredecessors(0);
            ruleModel.setiProductCategory(0);
            ruleModel.setiProductGroup(0);
            ruleModel.setiRuleID(0);
            ruleModel.setiRuleNumber(0);
            ruleModel.setiSequence(0);
            ruleModel.setiSubCategory(0);
            ruleModel.setsBrand("");
            ruleModel.setsDiscountBasedOn("");
            ruleModel.setsDiscountType("");
            ruleModel.setsEligibilityBasedOn("");
            ruleModel.setsEndDate("");
            ruleModel.setsProduct("");
            ruleModel.setsRuleNotes("");
            ruleModel.setsRuleType("");
            ruleModel.setsSchemeType("");
            ruleModel.setsSKU("");
            ruleModel.setsStartDate("");

            if (mOfferDiscountModel.getRuleModels() != null) {
                mOfferDiscountModel.getRuleModels().add(ruleModel);
            }

            Log.v("ssssss", mOfferDiscountModel.getRuleModels().size() + "");
            /*else {
                ruleModels.add(ruleModel);
                mOfferDiscountModel.setRuleModels(ruleModels);
            }*/

            OfferDiscountFragment offerDiscountFragment = (OfferDiscountFragment) getActivity();
            if (offerDiscountFragment != null) {
                offerDiscountFragment.getPcModelData().setValue(mOfferDiscountModel);
            }
//            }
        }
    }

    private void addNewField1() {
        RuleModel ruleModel = new RuleModel();
        ruleModel.setiMaterialBucketID(0);
        ruleModel.setiOPSScopeID(0);
        ruleModel.setiPredecessors(0);
        ruleModel.setiProductCategory(0);
        ruleModel.setiProductGroup(0);
        ruleModel.setiRuleID(0);
        ruleModel.setiRuleNumber(0);
        ruleModel.setiSequence(0);
        ruleModel.setiSubCategory(0);
        ruleModel.setsBrand("");
        ruleModel.setsDiscountBasedOn("");
        ruleModel.setsDiscountType("");
        ruleModel.setsEligibilityBasedOn("");
        ruleModel.setsEndDate("");
        ruleModel.setsProduct("");
        ruleModel.setsRuleNotes("");
        ruleModel.setsRuleType("");
        ruleModel.setsSchemeType("");
        ruleModel.setsSKU("");
        ruleModel.setsStartDate("");
       // ruleModels.add(ruleModel);
        if (mOfferDiscountModel.getRuleModels() != null) {
            mOfferDiscountModel.getRuleModels().add(ruleModel);
        }/*else {
                ruleModels.add(ruleModel);
                mOfferDiscountModel.setRuleModels(ruleModels);
            }*/

        OfferDiscountFragment offerDiscountFragment = (OfferDiscountFragment) getActivity();
        if (offerDiscountFragment != null) {
            offerDiscountFragment.getPcModelData().setValue(mOfferDiscountModel);
        }
    }

    @Override
    public void onClick(View view) {

    }

}
