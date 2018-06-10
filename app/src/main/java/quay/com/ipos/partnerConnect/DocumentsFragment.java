package quay.com.ipos.partnerConnect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DocumentsFragment extends Fragment implements InitInterface, View.OnClickListener {
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



        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.documents_fragment, container, false);
        mContext = getActivity();
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        return main;
    }

    @Override
    public void findViewById() {
        textViewPhotoHeading = main.findViewById(R.id.textViewPhotoHeading);
        textViewPanCardHeading = main.findViewById(R.id.textViewPanCardHeading);
        textViewAppointmentHeading = main.findViewById(R.id.textViewAppointmentHeading);
        textViewAnnexureHeading = main.findViewById(R.id.textViewAnnexureHeading);
        textViewCompilanceHeading = main.findViewById(R.id.textViewCompilanceHeading);
        textViewDocumentsVaultHeading = main.findViewById(R.id.textViewDocumentsVaultHeading);

        //Status
        textViewPhotoStatus = main.findViewById(R.id.textViewPhotoStatus);
        textViewPanStatus = main.findViewById(R.id.textViewPanStatus);
        textViewAppointmentStatus = main.findViewById(R.id.textViewAppointmentStatus);
        textViewAnnexureStatus = main.findViewById(R.id.textViewAnnexureStatus);
        textViewCompilanceStatus = main.findViewById(R.id.textViewCompilanceStatus);


        //Imageview
        imageViewphoto = main.findViewById(R.id.imageViewphoto);
        imageViewPan = main.findViewById(R.id.imageViewPan);
        imageViewAppointment = main.findViewById(R.id.imageViewAppointment);
        imageViewAnnexure = main.findViewById(R.id.imageViewAnnexure);
        imageViewCompilance = main.findViewById(R.id.imageViewCompilance);

        //Imageview Status
        imageViewPhotoStatus = main.findViewById(R.id.imageViewPhotoStatus);
        imageViewPanStatus = main.findViewById(R.id.imageViewPanStatus);
        imageViewAppointmentStatus = main.findViewById(R.id.imageViewAppointmentStatus);
        imageViewAnnexureStatus = main.findViewById(R.id.imageViewAnnexureStatus);
        imageViewCompilanceStatus = main.findViewById(R.id.imageViewCompilanceStatus);

        //Button camera
        btnPhotoCamera = main.findViewById(R.id.btnPhotoCamera);
        btnPanCamera = main.findViewById(R.id.btnPanCamera);
        btnAppointmentCamera = main.findViewById(R.id.btnAppointmentCamera);
        btnAnnexureCamera = main.findViewById(R.id.btnAnnexureCamera);
        btnCompilanceCamera = main.findViewById(R.id.btnCompilanceCamera);

        //Button upload

        btnPhotoUpload = main.findViewById(R.id.btnPhotoUpload);
        btnPanUpload = main.findViewById(R.id.btnPanUpload);
        btnAppointmentUpload = main.findViewById(R.id.btnAppointmentUpload);
        btnCompilanceUpload = main.findViewById(R.id.btnCompilanceUpload);
        btnAnnexureUpload = main.findViewById(R.id.btnAnnexureUpload);

        //RelativeLayout
        rLayoutValidCompilanceDocument = main.findViewById(R.id.rLayoutValidCompilanceDocument);
        rLayoutValidAppointmentDocument = main.findViewById(R.id.rLayoutValidAppointmentDocument);
        rLayoutValidDocument = main.findViewById(R.id.rLayoutValidDocument);
        rLayoutValidPanDocument = main.findViewById(R.id.rLayoutValidPanDocument);
        rLayoutValidPhotoDocument = main.findViewById(R.id.rLayoutValidPhotoDocument);

        //TextView
        textViewValidCompilanceDocument = main.findViewById(R.id.textViewValidCompilanceDocument);
        textViewValidAppointmentDocument = main.findViewById(R.id.textViewValidAppointmentDocument);
        textViewValidAnnexureDocument = main.findViewById(R.id.textViewValidAnnexureDocument);
        textViewValidPanDocument = main.findViewById(R.id.textViewValidPanDocument);
        textViewValidPhotoDocument = main.findViewById(R.id.textViewValidPhotoDocument);


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
