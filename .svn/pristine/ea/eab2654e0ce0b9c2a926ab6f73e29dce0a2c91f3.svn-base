package quay.com.ipos.compliance;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.compliance.data.local.entity.AttachmentEntity;
import quay.com.ipos.compliance.data.remote.model.SpendRequestAttachment;

public class BaseTaskActivity extends AppCompatActivity {
    private List<AttachmentEntity> attachFileModels = new ArrayList<>();
    private RecyclerView recyclerViewFiles;
    private View btnAttachFile;
    private TextView textViewAttachmentSize;
    private static final int PICKFILE_RESULT_CODE = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public List<AttachmentEntity> getAttachFileModels() {
        return attachFileModels;
    }

    public void setAttachFileModels(List<AttachmentEntity> attachFileModels) {
        this.attachFileModels = attachFileModels;
        updateSize();
    }

    public void findViewById() {
        recyclerViewFiles = findViewById(R.id.recyclerViewFiles);
        btnAttachFile = findViewById(R.id.btnAttachFile);
        btnAttachFile.setOnClickListener(new onAttachFileClicked());
        textViewAttachmentSize = findViewById(R.id.textViewAttachmentSize);


    }


    private class onAttachFileClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, PICKFILE_RESULT_CODE);
        }
    }

    private void updateSize() {
        int attachFileSize = attachFileModels.size();
        int totalSize = attachFileSize;
        textViewAttachmentSize.setText("(" + totalSize + ")");
        for (int i = 0; i < attachFileModels.size(); i++) {
            Log.v("attachFileModels", "attachFileModels" + attachFileModels.get(i));
        }
        if (attachFileSize > 0) {
            recyclerViewFiles.setAdapter(new AttachFileAdapter(attachFileModels));

        }
    }

    private class AttachFileAdapter extends RecyclerView.Adapter<AttachVH> {
        private List<AttachmentEntity> spendRequestAttachment;

        public AttachFileAdapter(List<AttachmentEntity> spendRequestAttachment) {
            this.spendRequestAttachment = spendRequestAttachment;
        }

        @NonNull
        @Override
        public AttachVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attachfile_item, parent, false);
            return new AttachVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AttachVH holder, final int position) {
            final AttachmentEntity fileModel = spendRequestAttachment.get(position);

            final String fileName = fileModel.name;
            holder.textView.setText(fileName);
            Log.v("path", "---------------------" + fileName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("data", new Gson().toJson(fileModel));

                    try {
                        final Intent shareIntent = new Intent(Intent.ACTION_VIEW);
                        shareIntent.setDataAndType(Uri.parse(fileModel.base.toString()), fileModel.type);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "View file using"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spendRequestAttachment.remove(fileModel);
                    notifyDataSetChanged();
                    int attachFileSize = attachFileModels.size();
                    textViewAttachmentSize.setText("(" + attachFileSize + ")");
                }
            });
        }

        @Override
        public int getItemCount() {
            return spendRequestAttachment.size();
        }
    }


    private class AttachVH extends RecyclerView.ViewHolder {
        public TextView textView;
        public View btnClear;

        public AttachVH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            btnClear = itemView.findViewById(R.id.btnClear);
            btnClear.setVisibility(View.VISIBLE);
        }
    }

    private String getBase64StringNew(Uri uri, int filelength) {
        String imageStr = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] byteFileArray = new byte[filelength];
            inputStream.read(byteFileArray, 0, byteFileArray.length);
            inputStream.close();
            imageStr = android.util.Base64.encodeToString(byteFileArray, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageStr;
    }


    private void onAttachmentRequestSubmit() {


        List<SpendRequestAttachment> spendRequestAttachmentList = new ArrayList<>();

        for (AttachmentEntity fileModel : attachFileModels) {

            Uri returnUri = Uri.parse(fileModel.base);
            Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
            /*
             * Get the column indexes of the data in the Cursor,
             * move to the first row in the Cursor, get the data,
             * and display it.
             */
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);

            returnCursor.moveToFirst();

            String fileName = returnCursor.getString(nameIndex);
            String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
            String mimeType = getContentResolver().getType(returnUri);
            Log.i("Type", mimeType);

            SpendRequestAttachment spendRequestAttachment = new SpendRequestAttachment();
            spendRequestAttachment.AttachmentBase = getBase64StringNew(returnUri, Integer.parseInt(fileSize));
            spendRequestAttachment.AttachmentExtension = "No Info";
            spendRequestAttachment.AttachmentName = fileName;
            spendRequestAttachment.AttachmentType = mimeType;

            spendRequestAttachmentList.add(spendRequestAttachment);

        }
        //  createSpendRequest.SpendRequestAttachmentList = spendRequestAttachmentList;

        // ProgressUtils.showSimpleProgressDialog(getActivity(), "", "Loading...", false);

      /*  CreateSpendRequestWS.getCreateSpendRequest(createSpendRequest,
                getActivity(), this);*/
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            switch (requestCode) {
                case PICKFILE_RESULT_CODE:
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        Cursor returnCursor =
                                getContentResolver().query(uri, null, null, null, null);
                        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                        returnCursor.moveToFirst();

                        String fileName = returnCursor.getString(nameIndex);
                        long fileSize = returnCursor.getLong(sizeIndex);
                        String mimeType = getContentResolver().getType(uri);
                        Log.i("Type", mimeType);
                        Log.i("fileSize", fileSize + "");
                        long twoMb = 1024 * 1024 * 2;

                        if (fileSize <= twoMb) {
                            AttachmentEntity fileModel = new AttachmentEntity();
                            fileModel.name = fileName;
                            fileModel.type = mimeType;
                            fileModel.base = uri.toString();

                            attachFileModels.add(fileModel);
                            updateSize();
                            String FilePath = data.getData().getPath();
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! File Size must be less than 2 MB", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
