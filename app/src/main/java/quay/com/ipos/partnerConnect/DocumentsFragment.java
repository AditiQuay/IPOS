package quay.com.ipos.partnerConnect;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.DocumentVoults;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.Base64Util;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.ShareWorldUtil;

/**
 * Created by niraj.kumar on 6/8/2018.
 */

public class DocumentsFragment extends Fragment implements InitInterface, View.OnClickListener {
    private static final String TAG = DocumentsFragment.class.getSimpleName();
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
    DocumentVoults docPan;
    DocumentVoults docPhoto;
    DocumentVoults docAppointment;
    DocumentVoults docAnnexure;
    DocumentVoults docCompliance;


    private int mApprovedResId = R.drawable.green_signal;
    private int mUnApprovedResId = R.drawable.red_signal;

    private Drawable mDrawableApproved;
    private Drawable mDrawableUnApproved;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        mDrawableApproved = resources.getDrawable(mApprovedResId);
        mDrawableUnApproved = resources.getDrawable(mUnApprovedResId);
    }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
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
        btnPhotoCamera = main.findViewById(R.id.btnCamContact);
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
            case R.id.btnCamContact:
                gotToCamera(v, 1);
                break;
            case R.id.btnPanCamera:
                gotToCamera(v, 2);

                break;
            case R.id.btnAppointmentCamera:
                gotToCamera(v, 3);

                break;
            case R.id.btnAnnexureCamera:
                gotToCamera(v, 4);

                break;
            case R.id.btnCompilanceCamera:
                gotToCamera(v, 5);

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

    private void gotToCamera(View v, int reqCode) {
        ShareWorldUtil.dispatchTakePictureIntent(getActivity(), this, reqCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(mContext, "onActivityResult", Toast.LENGTH_SHORT).show();
        Bitmap bitmap1 = ShareWorldUtil.onCameraResult(requestCode, resultCode, data, 1);
        if (bitmap1 != null) {
            imageViewphoto.setImageBitmap(bitmap1);
            if (docPhoto != null) {
                new ConvertFromBitmap(bitmap1, docPhoto).execute();
            } else {
                Log.e("doc", "is null");
            }


        }
        Bitmap bitmap2 = ShareWorldUtil.onCameraResult(requestCode, resultCode, data, 2);
        if (bitmap2 != null) {
            imageViewPan.setImageBitmap(bitmap2);
            if (docPan != null) {
                new ConvertFromBitmap(bitmap2, docPan).execute();
            } else {
                Log.e("docPan", "is null");
            }

        }
        Bitmap bitmap3 = ShareWorldUtil.onCameraResult(requestCode, resultCode, data, 3);
        if (bitmap3 != null) {
            imageViewAppointment.setImageBitmap(bitmap3);
            if (docAppointment != null) {
                new ConvertFromBitmap(bitmap3, docAppointment).execute();
            } else {
                Log.e("docAppointment", "is null");
            }

        }
        Bitmap bitmap4 = ShareWorldUtil.onCameraResult(requestCode, resultCode, data, 4);
        if (bitmap4 != null) {
            imageViewAnnexure.setImageBitmap(bitmap4);
            if (docAnnexure != null) {
                new ConvertFromBitmap(bitmap4, docAnnexure).execute();
            } else {
                Log.e("docAnnexure", "is null");
            }


        }
        Bitmap bitmap5 = ShareWorldUtil.onCameraResult(requestCode, resultCode, data, 5);
        if (bitmap5 != null) {
            imageViewCompilance.setImageBitmap(bitmap5);
            if (docCompliance != null) {
                new ConvertFromBitmap(bitmap5, docCompliance).execute();
            } else {
                Log.e("docCompliance", "is null");
            }


        }
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

    /*  {
          "Doctype": "PAN",
              "DocFilename": "mypan.png",
              "DocFileBase64": null
      },
      {
          "Doctype": "IDPHOTO",
              "DocFilename": "myid.png",
              "DocFileBase64": null
      },
      {
          "Doctype": "AppointmentForm",
              "DocFilename": "aptmnt.png",
              "DocFileBase64": null
      },
      {
          "Doctype": "Annexure",
              "DocFilename": "anxure.png",
              "DocFileBase64": null
      },
      {
          "Doctype": "compliance cert",
              "DocFilename": "cmplCert.png",
              "DocFileBase64": null
      }*/
    private void setData(PCModel pcModel) {
        if (pcModel == null && pcModel.DocumentVoults == null) {
            Log.i(TAG, "pcModel or pcModel.Business is null");
            return;
        } else {
            Log.i("data", new Gson().toJson(pcModel.DocumentVoults));
        }
        for (DocumentVoults documentVoult : pcModel.DocumentVoults) {
            if (documentVoult.Doctype.contains("PAN")) {
                docPan = documentVoult;

            }
            if (documentVoult.Doctype.contains("IDPHOTO")) {
                docPhoto = documentVoult;

            }
            if (documentVoult.Doctype.contains("AppointmentForm")) {
                docAppointment = documentVoult;

            }
            if (documentVoult.Doctype.contains("Annexure")) {
                docAnnexure = documentVoult;

            }
            if (documentVoult.Doctype.contains("compliance")) {
                docCompliance = documentVoult;
            }
        }

        if (docPhoto != null) {

            if (docPhoto.isApproved) {
                imageViewPhotoStatus.setImageDrawable(mDrawableApproved);
            } else {
                imageViewPhotoStatus.setImageDrawable(mDrawableUnApproved);
            }
            if (docPhoto.DocFileBase64 != null)
                new ConvertToBitmap(docPhoto.DocFileBase64, imageViewphoto).execute();
        }
        if (docCompliance != null) {
            if (docCompliance.isApproved) {
                imageViewCompilanceStatus.setImageResource(mApprovedResId);
            } else {
                imageViewCompilanceStatus.setImageResource(mUnApprovedResId);
            }
            if (docCompliance.DocFileBase64 != null)
                new ConvertToBitmap(docCompliance.DocFileBase64, imageViewCompilance).execute();
        }
        if (docPan != null) {
            if (docPan.isApproved) {
                imageViewPanStatus.setImageResource(mApprovedResId);
            } else {
                imageViewPanStatus.setImageResource(mUnApprovedResId);
            }
            if (docPan.DocFileBase64 != null)
                new ConvertToBitmap(docPan.DocFileBase64, imageViewPan).execute();
        }
        if (docAnnexure != null) {
            if (docAnnexure.isApproved) {
                imageViewAnnexureStatus.setImageResource(mApprovedResId);
            } else {
                imageViewAnnexureStatus.setImageResource(mUnApprovedResId);
            }
            if (docAnnexure.DocFileBase64 != null)
                new ConvertToBitmap(docAnnexure.DocFileBase64, imageViewAnnexure).execute();
        }
        if (docAppointment != null) {
            if (docAppointment.isApproved) {
                imageViewAppointmentStatus.setImageResource(mApprovedResId);
            } else {
                imageViewAppointmentStatus.setImageResource(mUnApprovedResId);
            }
            if (docAppointment.DocFileBase64 != null)
                new ConvertToBitmap(docAppointment.DocFileBase64, imageViewAppointment).execute();
        }

        //  recyclerViewAccountInfo.setAdapter(new AccountAdapter(getActivity(), pcModel.KycAccount.cheques, KycAccountFragment.this));
    }

    public class ConvertToBitmap extends AsyncTask<Void, Void, Bitmap> {
        private String stringBase64;
        private ImageView imageViewPan;

        public ConvertToBitmap(String stringBase64, ImageView imageViewPan) {
            this.stringBase64 = stringBase64;
            this.imageViewPan = imageViewPan;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            return Base64Util.StringToBitMap(stringBase64);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageViewPan.setImageBitmap(bitmap);

            }
        }
    }

    public class ConvertFromBitmap extends AsyncTask<Void, Void, String> {
        private Bitmap bitmap1;
        private DocumentVoults documentVoults;

        public ConvertFromBitmap(Bitmap bitmap1, DocumentVoults voults) {
            this.bitmap1 = bitmap1;
            this.documentVoults = voults;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return Base64Util.BitMapToString(bitmap1);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                documentVoults.DocFileBase64 = s;
                Log.i("string", s);
            }
        }
    }
}
