package quay.com.ipos.compliance.data.remote;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.List;

import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.remote.model.SynResponse;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.data.remote.RestService;
import retrofit2.Call;
import retrofit2.Response;

public class SyncData extends AsyncTask<Void, Void, Long> {

    private static final String TAG = SyncData.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //Perform pre-adding operation here.
    }

    @Override
    protected Long doInBackground(Void... voids) {
        try {


            List<SubTask> subTaskList = AppDatabase.getAppDatabase(IPOSApplication.getContext()).subtaskDao().getAllUnSyncSubTask(true);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();

            Log.i("data", gson.toJson(subTaskList));
            Call<List<SynResponse>> call = RestService.getApiServiceSimple().syncData(subTaskList);
            Response<List<SynResponse>> response =  call.execute();
            Log.i(TAG, "Message" + response.code() + "," + response.message());
            Log.i(TAG, "body" +   gson.toJson(response.body()));
            if (response.body() != null) {

                for (SynResponse synResponse : response.body()) {
                    int localKey = Integer.parseInt(synResponse.PrevId);
                    int serverKey = Integer.parseInt(synResponse.NewId);
                    SubTask subTask = AppDatabase.getAppDatabase(IPOSApplication.getContext()).subtaskDao().getSyncSubTaskById(localKey);
                   // AppController.getDatabase().subtaskDao().deleteSubTask(subTask);
                    subTask.isSync = false;
                    subTask.setServerId(serverKey);
                    AppDatabase.getAppDatabase(IPOSApplication.getContext()).subtaskDao().saveSubTask(subTask);

                }
            }
      /*  RestService.getApiService(AppController.context).syncData(subTaskList).enqueue(new Callback<List<SynResponse>>() {
            @Override
            public void onResponse(Call<List<SynResponse>> call, Response<List<SynResponse>> response) {
                Log.i(TAG, "Message" + response.code() + "," + response.message());
                if (response.body() != null) {

                    for (SynResponse synResponse : response.body()) {
                        int localKey = Integer.parseInt(synResponse.PrevId);
                        int serverKey = Integer.parseInt(synResponse.NewId);
                        SubTask subTask = AppController.getDatabase().subtaskDao().getSyncSubTaskById(localKey);
                        AppController.getDatabase().subtaskDao().deleteSubTask(subTask);
                        subTask.isSync = false;
                        subTask.setSub_task_id(serverKey);
                        AppController.getDatabase().subtaskDao().saveSubTask(subTask);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<SynResponse>> call, Throwable t) {

            }
        });*/
            Log.i("subTaskList size", subTaskList.size() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long subtaskSavedId) {
        super.onPostExecute(subtaskSavedId);

    }
}
