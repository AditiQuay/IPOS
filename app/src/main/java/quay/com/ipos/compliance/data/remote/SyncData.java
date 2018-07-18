package quay.com.ipos.compliance.data.remote;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;


import java.util.List;

import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.remote.model.SynResponse;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.data.remote.RestService;
import retrofit2.Call;
import retrofit2.Response;

public class SyncData extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = SyncData.class.getSimpleName();
    private List<SubTask> subTaskList;

    public SyncData(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //Perform pre-adding operation here.
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {


           // List<SubTask> subTaskList = AppDatabase.getAppDatabase(IPOSApplication.getContext()).subtaskDao().getAllUnSyncSubTask(true);
          /*  Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();*/

            Log.i("data", new Gson().toJson(subTaskList));
            Call<List<SynResponse>> call = RestService.getApiServiceSimple().syncData(subTaskList);
            Response<List<SynResponse>> response = call.execute();
            Log.i(TAG, "Message" + response.code() + "," + response.message());
            Log.i(TAG, "body" + new Gson().toJson(response.body()));
            if (response.body() != null) {
                for (SynResponse synResponse : response.body()) {
                    int localKey = Integer.parseInt(synResponse.PrevId);
                    int serverKey = Integer.parseInt(synResponse.NewId);
                    //    List<SubTask> subTask = IPOSApplication.getDatabase().subtaskDao().getSyncSubTaskById(localKey);
                    // if (subTask != null) {
                    for (SubTask task : subTaskList) {
                        // if (task.id == localKey) {
                        //  task.isSync = false;
                        task.setServerId(serverKey);
                        task.taskTrId = synResponse.taskTransactionID;
                        IPOSApplication.getDatabase().subtaskDao().saveSubTask(task);
                        return true;
                    }
                }
                    Log.e(TAG, "SubTask is not null");
                    } else {
                        Log.e(TAG, "SubTask is null");
                    }



            Log.i("subTaskList size", subTaskList.size() + "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean isSync) {
        super.onPostExecute(isSync);
        if (listener != null) {
            listener.onDataSync(isSync);

        }
    }

    public void setListener(OnDataSyncListener listener) {
        this.listener = listener;
    }

    private OnDataSyncListener listener;

    public interface OnDataSyncListener {
        void onDataSync(boolean isSync);
    }
}
