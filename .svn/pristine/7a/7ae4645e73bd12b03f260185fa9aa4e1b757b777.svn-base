package quay.com.ipos.compliance;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import quay.com.ipos.OnStoreSelectionListener;
import quay.com.ipos.R;
import quay.com.ipos.compliance.constants.AnnotationComplianceType;
import quay.com.ipos.compliance.constants.AnnotationStoreType;
import quay.com.ipos.compliance.fragment.AllStorewiseComplianceFragment;
import quay.com.ipos.compliance.fragment.ComplianceFragmentHeader;
import quay.com.ipos.compliance.fragment.ComplianceFragmentMain;
import quay.com.ipos.compliance.fragment.ComplianceFragmentSingleStoreDetail_ABS;


public class StorewiseComplianceActivity extends AppCompatActivity implements OnStoreSelectionListener, ComplianceFragmentMain.ComplianceTypeListener
        , ComplianceFragmentSingleStoreDetail_ABS.OnFragmentInteractionListener ,ComplianceFragmentHeader.OnComplianeFilterListener{

    private static final String TAG = StorewiseComplianceActivity.class.getSimpleName();
    private TextView mTextMessage;
    private Activity activity;
    private String storeid;
    private ComplianceFragmentHeader complianceFragmentHeader;
    private ComplianceFragmentMain complianceFragmentMain;
    private String strTitle;
    private int parent_curr_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_storewise_compliance);
        initUI(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent()" + intent.getStringExtra("title") + "," + getIntent().getStringExtra("title"));
        initUI(intent);
    }

    private void initUI(Intent intent) {
        strTitle = intent.getStringExtra("title");
        storeid = intent.getStringExtra("storeid");
        parent_curr_pos = intent.getIntExtra("curr_pos", 0);
        setUpToolbar(strTitle);
        setUpBody();
    }

    private void setUpToolbar(String strTitle) {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(strTitle);
    }

    private void setUpBody() {
        //getSupportActionBar().setTitle(getResources().getString(R.string.compliance_tracking));

        //for header
        complianceFragmentHeader = ComplianceFragmentHeader.newInstance(strTitle + "", "" + storeid);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentPanelHeader, complianceFragmentHeader).commit();
        complianceFragmentHeader.hideStoreSelectionView();

        //for detail
       /* ComplianceFragmentA complianceFragmentA = ComplianceFragmentA.newInstance("", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.contentPanel, complianceFragmentA).commit();
       */

          complianceFragmentMain = ComplianceFragmentMain.newInstance("" + storeid, AnnotationStoreType.SINGLE,parent_curr_pos);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentPanel, complianceFragmentMain).commit();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onPageSelected(int complianceType) {
        if (complianceFragmentHeader != null) {
            complianceFragmentHeader.updateView(complianceType);


        }
    }

    @Override
    public void onStoreSelected(String strStoreName, String storeId) {
        Intent intent = new Intent(this, StorewiseComplianceActivity.class);
        intent.putExtra("title", strStoreName);
        intent.putExtra("storeid", storeId);
        Log.i(TAG, "strStoreName" + strStoreName + ", storeid" + storeId);
        startActivity(intent);
    }

    @Override
    public void onClick(@AnnotationComplianceType.ComplianceType int complianceType) {
        if (complianceFragmentMain != null) {
            complianceFragmentMain.updateFilter(complianceType);
        }
    }
}
