package quay.com.ipos.partnerConnect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 6/8/2018.
 */

public class DocumentsFragment extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private View main;
    private Context mContext;
    //Photo variables
    private TextView textViewPhotoHeading, textViewDocumentsVaultHeading, textViewPanCardHeading, textViewAppointmentHeading, textViewAnnexureHeading, textViewCompilanceHeading;
    private TextView textViewPhotoStatus, textViewPanStatus, textViewAppointmentStatus, textViewAnnexureStatus, textViewCompilanceStatus;
    private ImageView imageViewphoto, imageViewPan, imageViewAppointment, imageViewAnnexure, imageViewCompilance;
    private Button btnPhotoCamera, btnPhotoUpload, btnPanCamera, btnPanUpload, btnAppointmentCamera, btnAppointmentUpload, btnAnnexureCamera, btnCompilanceCamera, btnCompilanceUpload, btnAnnexureUpload;
    private ImageView imageViewPhotoStatus, imageViewPanStatus, imageViewAppointmentStatus, imageViewAnnexureStatus, imageViewCompilanceStatus;
    private RelativeLayout rLayoutValidCompilanceDocument, rLayoutValidAppointmentDocument, rLayoutValidDocument, rLayoutValidPanDocument, rLayoutValidPhotoDocument;
    private TextView textViewValidCompilanceDocument, textViewValidAppointmentDocument, textViewValidAnnexureDocument, textViewValidPanDocument, textViewValidPhotoDocument;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents_fragment);
        mContext = DocumentsFragment.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    //    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        main = inflater.inflate(R.layout.documents_fragment, container, false);
//        findViewById();
//        applyInitValues();
//        applyLocalValidation();
//        applyTypeFace();
//        return main;
//    }

    @Override
    public void findViewById() {
        textViewPhotoHeading = findViewById(R.id.textViewPhotoHeading);
        textViewPanCardHeading = findViewById(R.id.textViewPanCardHeading);
        textViewAppointmentHeading = findViewById(R.id.textViewAppointmentHeading);
        textViewAnnexureHeading = findViewById(R.id.textViewAnnexureHeading);
        textViewCompilanceHeading = findViewById(R.id.textViewCompilanceHeading);
        textViewDocumentsVaultHeading = findViewById(R.id.textViewDocumentsVaultHeading);

        //Status
        textViewPhotoStatus = findViewById(R.id.textViewPhotoStatus);
        textViewPanStatus = findViewById(R.id.textViewPanStatus);
        textViewAppointmentStatus = findViewById(R.id.textViewAppointmentStatus);
        textViewAnnexureStatus = findViewById(R.id.textViewAnnexureStatus);
        textViewCompilanceStatus = findViewById(R.id.textViewCompilanceStatus);


        //Imageview
        imageViewphoto = findViewById(R.id.imageViewphoto);
        imageViewPan = findViewById(R.id.imageViewPan);
        imageViewAppointment = findViewById(R.id.imageViewAppointment);
        imageViewAnnexure = findViewById(R.id.imageViewAnnexure);
        imageViewCompilance = findViewById(R.id.imageViewCompilance);

        //Imageview Status
        imageViewPhotoStatus = findViewById(R.id.imageViewPhotoStatus);
        imageViewPanStatus = findViewById(R.id.imageViewPanStatus);
        imageViewAppointmentStatus = findViewById(R.id.imageViewAppointmentStatus);
        imageViewAnnexureStatus = findViewById(R.id.imageViewAnnexureStatus);
        imageViewCompilanceStatus = findViewById(R.id.imageViewCompilanceStatus);

        //Button camera
        btnPhotoCamera = findViewById(R.id.btnPhotoCamera);
        btnPanCamera = findViewById(R.id.btnPanCamera);
        btnAppointmentCamera = findViewById(R.id.btnAppointmentCamera);
        btnAnnexureCamera = findViewById(R.id.btnAnnexureCamera);
        btnCompilanceCamera = findViewById(R.id.btnCompilanceCamera);

        //Button upload

        btnPhotoUpload = findViewById(R.id.btnPhotoUpload);
        btnPanUpload = findViewById(R.id.btnPanUpload);
        btnAppointmentUpload = findViewById(R.id.btnAppointmentUpload);
        btnCompilanceUpload = findViewById(R.id.btnCompilanceUpload);
        btnAnnexureUpload = findViewById(R.id.btnAnnexureUpload);

        //RelativeLayout
        rLayoutValidCompilanceDocument = findViewById(R.id.rLayoutValidCompilanceDocument);
        rLayoutValidAppointmentDocument = findViewById(R.id.rLayoutValidAppointmentDocument);
        rLayoutValidDocument = findViewById(R.id.rLayoutValidDocument);
        rLayoutValidPanDocument = findViewById(R.id.rLayoutValidPanDocument);
        rLayoutValidPhotoDocument = findViewById(R.id.rLayoutValidPhotoDocument);

        //TextView
        textViewValidCompilanceDocument = findViewById(R.id.textViewValidCompilanceDocument);
        textViewValidAppointmentDocument = findViewById(R.id.textViewValidAppointmentDocument);
        textViewValidAnnexureDocument = findViewById(R.id.textViewValidAnnexureDocument);
        textViewValidPanDocument = findViewById(R.id.textViewValidPanDocument);
        textViewValidPhotoDocument = findViewById(R.id.textViewValidPhotoDocument);


        //Camera click listner
        btnPhotoCamera.setOnClickListener(this);
        btnPanCamera.setOnClickListener(this);
        btnAppointmentCamera.setOnClickListener(this);
        btnAnnexureCamera.setOnClickListener(this);
        btnCompilanceCamera.setOnClickListener(this);

        //Upload button click listner
        btnPhotoUpload.setOnClickListener(this);
        btnPanUpload.setOnClickListener(this);
        btnAppointmentUpload.setOnClickListener(this);
        btnCompilanceUpload.setOnClickListener(this);
        btnAnnexureUpload.setOnClickListener(this);


    }

    @Override
    public void applyInitValues() {

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewPhotoHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewPanCardHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewAppointmentHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewAnnexureHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewCompilanceHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewDocumentsVaultHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        FontUtil.applyTypeface(textViewPhotoStatus, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewPanStatus, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewAppointmentStatus, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewAnnexureStatus, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewCompilanceStatus, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        FontUtil.applyTypeface(btnPhotoCamera, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnPanCamera, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnAppointmentCamera, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnAnnexureCamera, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnCompilanceCamera, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

        FontUtil.applyTypeface(btnPhotoUpload, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnPanUpload, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnAppointmentUpload, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnCompilanceUpload, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(btnAnnexureUpload, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {

        btnPhotoCamera.setOnClickListener(this);
        btnPanCamera.setOnClickListener(this);
        btnAppointmentCamera.setOnClickListener(this);
        btnAnnexureCamera.setOnClickListener(this);
        btnCompilanceCamera.setOnClickListener(this);

        //Upload button click listner
        btnPhotoUpload.setOnClickListener(this);
        btnPanUpload.setOnClickListener(this);
        btnAppointmentUpload.setOnClickListener(this);
        btnCompilanceUpload.setOnClickListener(this);
        btnAnnexureUpload.setOnClickListener(this);
        switch (v.getId()) {
            case R.id.btnPhotoCamera:
                break;
            case R.id.btnPanCamera:
                break;
            case R.id.btnAppointmentCamera:
                break;
            case R.id.btnAnnexureCamera:
                break;
            case R.id.btnCompilanceCamera:
                break;
            case R.id.btnPhotoUpload:
                break;
            case R.id.btnPanUpload:
                break;
            case R.id.btnAppointmentUpload:
                break;
            case R.id.btnCompilanceUpload:
                break;
            case R.id.btnAnnexureUpload:
            default:
                break;
        }
    }
}
