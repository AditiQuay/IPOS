package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.BillAdapterModel;
import quay.com.ipos.ddrsales.model.DDR;


public class DDRBillPreviewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DDRBillPreviewActivity.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;
    private TextView tvDate, tvDDROrderId;

    private RecyclerView recycleView;
    private BillPreviewAdapter adapter;
    private List<BillAdapterModel> list = new ArrayList<>();
    private DDR mDdr;
    private String ddrOrderDate, ddrOrderId;

    private boolean cameFromSubmitPage = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_ddr_billpreview);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mDdr = (DDR) getIntent().getSerializableExtra("ddr");
        ddrOrderDate = getIntent().getStringExtra("ddrOrderDate");
        ddrOrderId = getIntent().getStringExtra("ddrOrderId");
        cameFromSubmitPage = getIntent().getBooleanExtra("cameFromSubmitPage", false);


        mDDRDetails = findViewById(R.id.mDDRDetails);
        mDDRDetailsIcon = findViewById(R.id.mDDRDetailsIcon);

        tvDate = findViewById(R.id.tvDate);
        tvDDROrderId = findViewById(R.id.tvDDROrderId);

        if (mDdr != null) {
            mDDRDetails.setText(mDdr.mDDRCode + " - " + mDdr.mDDRName);
            mDDRDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "" + mDdr.mDDRCode + " - " + mDdr.mDDRName, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (ddrOrderDate != null) {
            tvDate.setText(ddrOrderDate);
        }
        if (ddrOrderId != null) {
            tvDDROrderId.setText(ddrOrderId);
        }

        localBillSetUp();
        initView();
    }

    private void localBillSetUp() {
        list.add(new BillAdapterModel("1", "Performa Invoice", "Not Generated"));
        list.add(new BillAdapterModel("2", "Delivery Challan", "Not Generated"));
        list.add(new BillAdapterModel("3", "Invoice", "Not Generated"));
    }

    private void initView() {
        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BillPreviewAdapter();
        recycleView.setAdapter(adapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:


                return true;

            case R.id.action_outbox:

                break;
        }
        return false;
    }


    public void onCloseAction(View view) {
        if (cameFromSubmitPage) {
            Intent intent = new Intent(activity, DDROrderCenterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onClick(View view) {

    }

    private class BillPreviewAdapter extends RecyclerView.Adapter<ViewHolder> {
        private LayoutInflater layoutInflater;

        private BillPreviewAdapter() {
            layoutInflater = LayoutInflater.from(activity);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.ddr_adapter_bill, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            BillAdapterModel model = list.get(position);
            holder.textTitle.setText(model.strTitle);
            holder.textBody.setText(model.strBody);

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Go For Edit", Toast.LENGTH_SHORT).show();
                }
            });
            holder.btnMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Go For Mail", Toast.LENGTH_SHORT).show();
                }
            });
            holder.btnPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Go For Print", Toast.LENGTH_SHORT).show();
                }
            });
            holder.btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Go For View", Toast.LENGTH_SHORT).show();
                }
            });

        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textBody, textTitle;
        View btnMail, btnPrint, btnView, btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textBody = itemView.findViewById(R.id.textBody);
            btnMail = itemView.findViewById(R.id.btnMail);
            btnPrint = itemView.findViewById(R.id.btnPrint);
            btnView = itemView.findViewById(R.id.btnView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        /// onBackPressed();
        onCloseAction(null);
        return true;
    }


}
