package quay.com.ipos.ddrsales.ddrdetail.fragment;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.InputStream;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.ddrdetail.DDRCUActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.DocumentVoults;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.Base64Util;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.PictureManager;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.ShareWorldUtil;

import static android.app.Activity.RESULT_OK;

/**
 * Created by niraj.kumar on 6/8/2018.
 */

public class DDRCUDocumentsFragment extends Fragment implements InitInterface, View.OnClickListener {
    private static final String TAG = DDRCUDocumentsFragment.class.getSimpleName();
    private static final int REQ_GALLERY_Photo = 301;
    private static final int REQ_GALLERY_Pan = 302;
    private static final int REQ_GALLERY_Appointment = 303;
    private static final int REQ_GALLERY_Annexure = 304;
    private static final int REQ_GALLERY_Compliance = 305;
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

    private String mEntityId;

    private PictureManager pictureManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        mDrawableApproved = resources.getDrawable(mApprovedResId);
        mDrawableUnApproved = resources.getDrawable(mUnApprovedResId);
        mEntityId = Prefs.getIntegerPrefs(Constants.entityCode.trim())+"";


        pictureManager = new PictureManager(getContext());
        pictureManager.setFragment(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.ddrcu_documents_fragment, container, false);
        mContext = getActivity();
        myDialog = new Dialog(mContext);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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


        imageViewphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private Dialog myDialog;

    private void openImageDialog(String docFileBase64) {
        ImageView ImvClose, imgDocumentPreview;

        myDialog.setContentView(R.layout.view_dialog);
        ImvClose = myDialog.findViewById(R.id.ImvClose);
        imgDocumentPreview = myDialog.findViewById(R.id.imgDocumentPreview);
        new ConvertToBitmap(docFileBase64, imgDocumentPreview).execute();

        ImvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


    }

    private void openImageDialogNew(String picUrl) {
        ImageView ImvClose, imgDocumentPreview;

        myDialog.setContentView(R.layout.view_dialog);
        ImvClose = myDialog.findViewById(R.id.ImvClose);
        imgDocumentPreview = myDialog.findViewById(R.id.imgDocumentPreview);
        //  new ConvertToBitmap(docFileBase64, imgDocumentPreview).execute();
        Glide.with(getActivity()).load(picUrl).into(imgDocumentPreview);
        ImvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


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

        switch (v.getId()) {
            case R.id.btnCamContact:
                //   gotToCamera2(imageViewphoto, "PrimaryContactPhoto", 1);
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
                // gotToCamera2(imageViewphoto, "PrimaryContactPhoto", 1);


                //   onAttachFileClicked();
                onClickGallery(REQ_GALLERY_Photo);
                break;
            case R.id.btnPanUpload:
                onClickGallery(REQ_GALLERY_Pan);

                break;
            case R.id.btnAppointmentUpload:
                onClickGallery(REQ_GALLERY_Appointment);

                break;
            case R.id.btnCompilanceUpload:
                onClickGallery(REQ_GALLERY_Compliance);

                break;
            case R.id.btnAnnexureUpload:
                onClickGallery(REQ_GALLERY_Annexure);
                break;
            default:
                break;
        }
    }

    private void gotToCamera(View v, int reqCode) {
        ShareWorldUtil.dispatchTakePictureIntent(getActivity(), this, reqCode);
    }

    private void gotToCamera2(final ImageView v, String name, int reqCode) {
        Log.i(TAG, "gotToCamera2");
        pictureManager.onClickCamera("PICTURE" + "_" + name, reqCode, new PictureManager.GetPicURLListener() {
            @Override
            public void onGetPicURL(String picUrl, int reqFor) {
                //  imageViewTowerPath = picUrl ;
                Log.i(TAG, "picUrl" + picUrl.toString());
                Glide.with(getActivity()).load(picUrl).override(v.getWidth(), v.getHeight()).into(v);
                openImageDialogNew(picUrl);
            }
        });
    }

    private void gotToGallery(final ImageView v, String name, int reqCode) {
        Log.i(TAG, "gotToCamera2");
       /* pictureManager.onClickCamera("PICTURE" + "_" + name, reqCode, new PictureManager.GetPicURLListener() {
            @Override
            public void onGetPicURL(String picUrl, int reqFor) {
                //  imageViewTowerPath = picUrl ;
                Log.i(TAG, "picUrl" + picUrl.toString());
                Glide.with(getActivity()).load(picUrl).override(v.getWidth(), v.getHeight()).into(v);
                openImageDialogNew(picUrl);
            }
        });*/
        // pictureManager.;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResultGallery(requestCode, resultCode, data);
        if (requestCode == PictureManager.IMAGE_CAPTURE) {
            pictureManager.onActivityResult(requestCode, resultCode, data);
            return;
        }


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
        DDRCUActivity DDRCUActivity = (DDRCUActivity) getActivity();
        if (DDRCUActivity != null) {
            DDRCUActivity.getPcModelData().observe(this, new Observer<PCModel>() {
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


        if (pcModel.DocumentVoults.size() == 0) {
            docPhoto = new DocumentVoults(mEntityId, "IDPHOTO");
            docPan = new DocumentVoults(mEntityId, "PAN");
            docAppointment = new DocumentVoults(mEntityId, "AppointmentForm");
            docAnnexure = new DocumentVoults(mEntityId, "Annexure");
            docCompliance = new DocumentVoults(mEntityId, "compliance");

            pcModel.DocumentVoults.add(docPhoto);
            pcModel.DocumentVoults.add(docPan);
            pcModel.DocumentVoults.add(docAppointment);
            pcModel.DocumentVoults.add(docAnnexure);
            pcModel.DocumentVoults.add(docCompliance);


        } else {


            for (DocumentVoults documentVoult : pcModel.DocumentVoults) {
                if (documentVoult.Doctype.contains("IDPHOTO")) {
                    docPhoto = documentVoult;

                }
                if (documentVoult.Doctype.contains("PAN")) {
                    docPan = documentVoult;

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


    private String getBase64StringNew(Uri uri, int filelength) {
        String imageStr = null;
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

           /* InputStream finput = new FileInputStream(file);
            byte[] imageBytes = new byte[(int)file.length()];
            finput.read(imageBytes, 0, imageBytes.length);
            finput.close();
            String imageStr = Base64.encodeBase64String(imageBytes);*/

            //InputStream finput = new FileInputStream(file);
            byte[] byteFileArray = new byte[filelength];
            inputStream.read(byteFileArray, 0, byteFileArray.length);
            inputStream.close();
            imageStr = android.util.Base64.encodeToString(byteFileArray, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageStr;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onClickGallery(int REQ_GALLERY_CODE) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_GALLERY_CODE);

    }

    public void onActivityResultGallery(int requestCode, int resultCode, Intent data) {
        if (requestCode > 300) {
            try {

                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Cursor returnCursor =
                            getActivity().getContentResolver().query(uri, null, null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                    returnCursor.moveToFirst();

                    String fileName = returnCursor.getString(nameIndex);
                    long fileSize = returnCursor.getLong(sizeIndex);
                    String mimeType = getActivity().getContentResolver().getType(uri);
                    Log.i("Type", mimeType);
                    Log.i("fileSize", fileSize + "");
                    long twoMb = 1024 * 1024 * 2;

                    if (fileSize <= twoMb) {
                           /* AttachFileModel fileModel = new AttachFileModel();
                            fileModel.fileName = fileName;
                            fileModel.mimeType = mimeType;
                            fileModel.uri = uri;

                            attachFileModels.add(fileModel);
                          //  updateSize();*/

                        String FilePath = data.getData().getPath();
                        ImageView imageView = null;
                        DocumentVoults documentVoults = null;
                        if (requestCode == REQ_GALLERY_Photo) {
                            imageView = imageViewphoto;
                            documentVoults = docPhoto;
                        }
                        if (requestCode == REQ_GALLERY_Annexure) {
                            imageView = imageViewAnnexure;
                            documentVoults = docAnnexure;

                        }
                        if (requestCode == REQ_GALLERY_Appointment) {
                            imageView = imageViewAppointment;
                            documentVoults = docAppointment;

                        }
                        if (requestCode == REQ_GALLERY_Compliance) {
                            imageView = imageViewCompilance;
                            documentVoults = docCompliance;

                        }
                        if (requestCode == REQ_GALLERY_Pan) {
                            imageView = imageViewPan;
                            documentVoults = docPan;

                        }
                        if (imageView != null)
                            Glide.with(getActivity()).load(uri).override(imageView.getWidth(), imageView.getHeight()).into(imageView);
                        if (documentVoults != null) {
                            documentVoults.DocFileBase64 = getBase64StringNew(uri, (int) fileSize);
                        }


                    } else {
                        Toast.makeText(getActivity(), "Oops! File Size must be less than 2 MB", Toast.LENGTH_SHORT).show();
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
