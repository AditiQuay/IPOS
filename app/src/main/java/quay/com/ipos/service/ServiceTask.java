package quay.com.ipos.service;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.modal.GlobalSettings;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SharedPrefUtil;


/**
 * Created by aditi.bhuranda on 15-04-2018.
 */

/**
 * The Class ServiceTask.
 */
public class ServiceTask extends AsyncTask<Void, Void, Void> {
    /**
     * The listener interface for receiving serviceResult events. The class that
     * is interested in processing a serviceResult event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's
     * <code>addServiceResultListener<code> method. When
     * the serviceResult event occurs, that object's appropriate
     * method is invoked.
     *
     * @see
     */

    public interface ServiceResultListener {
        /**
         * On result.
         *
         * @param serviceUrl    the service url
         * @param serviceMethod the method url
         * @param resultType    the result type
         * @param resultObj     the result obj
         */
        public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse);
    }

    /**
     * The Constant TAG.
     */
    private static final String TAG = ServiceTask.class.getSimpleName();

    /**
     * The _listener.
     */
    private ServiceResultListener listener = null;

    /**
     * The _service url.
     */
    private String apiUrl = null;

    /**
     * The _apiCall Type.
     */
    private int apiCallType = -1;

    /**
     * The apiToken.
     */
    private String apiToken = "";

    /**
     * The _result obj.
     */
    private Object resultObj = null;
    private String serverResponse = null;

    /**
     * The _param obj.
     */
    private Object paramObj = null;

    /**
     * The _result type.
     */
    private Type resultType = null;

    /**
     * The Api Method
     **/
    private String apiMethod = null;

    byte[] resultInBytes = null;

    /**
     * HTTP Status code
     */

    private int statusCode = 0;

    /**
     * The is set header.
     */
    private boolean isSetHeader = true;

    private  okhttp3.RequestBody fileToUpload = null;

    private String getParameters;

    public static final  okhttp3.MediaType JSON = okhttp3. MediaType.parse("application/json; charset=utf-8");

    /**
     * Sets the listener.
     *
     * @param listener the new listener
     */
    public void setListener(ServiceResultListener listener) {
        this.listener = listener;
    }

    public void setPhotoEntity( okhttp3.RequestBody fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public void setApiCallType(int apiCallType) {
        this.apiCallType = apiCallType;
    }

    /**
     * Sets the result type.
     *
     * @param resultType the new result type
     */
    public void setResultType(Type resultType) {
        this.resultType = resultType;
    }

    public void setGetParameters(String getParameters) {
        this.getParameters = getParameters;
    }

    /**
     * @param apiUrl the apiUrl to set
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }


    /**
     * @param apiMethod the apiMethod to set
     */
    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }


    /**
     * Sets the result obj.
     *
     * @param resultObj the new result obj
     */
    public void setResultObj(Object resultObj) {
        this.resultObj = resultObj;
    }

    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }

    /**
     * @param paramObj the paramObj to set
     */
    public void setParamObj(Object paramObj) {
        this.paramObj = paramObj;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        AppLog.e(TAG, "onPreExecute++");
        super.onPreExecute();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Void doInBackground(Void... arg) {
        AppLog.e(TAG, "doInBackground: serviceUrl: " + apiUrl);

        do {
            if (isCancelled()) {
                break;
            }

            try {
                String methodUrl = apiUrl;
                if (null != apiMethod && false == apiMethod.isEmpty()) {
                    methodUrl = apiUrl + apiMethod;
                }
                AppLog.e(TAG, "methodUrl: " + methodUrl);

                okhttp3.OkHttpClient.Builder clientBuilder = new  okhttp3.OkHttpClient().newBuilder();
                clientBuilder.addInterceptor(new RequestTokenInterceptor());

                okhttp3.OkHttpClient client = clientBuilder.build();
                okhttp3.Request request = null;

                Gson gson = new GsonBuilder().create();

                if (apiMethod.equals("upload.php")) {
//                    RequestBody requestBody = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("title", "Square Logo")
//                            .addFormDataPart("image", "logo-square.png",
//                                    RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                            .build();
//
//                    request = new Request.Builder()
//                            .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
//                            .url("https://api.imgur.com/3/image")
//                            .post(requestBody)
//                            .build();



                    request = new  okhttp3.Request.Builder()
                           // .header("Authorization", accessToken)
                           // .addHeader("GlobalSettings", new Gson().toJson(globalSettings))//new change for header

                            .url(methodUrl)
                            .post(fileToUpload)
                            .build();

                } else {
                    if (apiCallType == Constants.API_METHOD_GET) {
                        request = new  okhttp3.Request.Builder()
                              //  .header("Authorization", apiToken)
                              //  .addHeader("GlobalSettings", new Gson().toJson(globalSettings))//new change for header
                                .url(methodUrl + getParameters)
                                .get()
                                .build();
                    } else {
                        if (null != paramObj) {
                            if (isCancelled()) {
                                break;
                            }
                            String requestJson = gson.toJson(paramObj);
                            AppLog.e(TAG, "requestJson: " + methodUrl + requestJson);
//                            AppLog.e(TAG, "requestJson: " + methodUrl + requestJson);
                            okhttp3.RequestBody body =  okhttp3.RequestBody.create(JSON, requestJson);
                            request = new  okhttp3.Request.Builder()
                                   // .header("Authorization", apiToken)
                                   // .addHeader("GlobalSettings", new Gson().toJson(globalSettings))//new change for header
                                    .url(methodUrl)
                                    .post(body)
                                    .build();

                            requestJson = null;
                        }
                    }
                }


                // Send request to WCF service
//                if (isCancelled()) {
//                    break;
//                }


                okhttp3.Response response = client.newCall(request).execute();
                AppLog.e(TAG, "StatusCode : " + response.code());
                statusCode = response.code();
                if (200 == response.code()) {
                    String responseJson = response.body().string();

                    AppLog.e(TAG, "responseJson: " + methodUrl + responseJson);
                    AppLog.e(TAG, responseJson);

                    if (false == responseJson.isEmpty() && null != resultType) {
                        if (isCancelled()) {
                            break;
                        }
                        serverResponse = responseJson;
                        resultObj = gson.fromJson(responseJson, resultType);
                        responseJson = null;
                    } else {
                        AppLog.e(TAG, "Got success but responseJson is empty or no result expected");
                    }
                    AppLog.e(TAG, "Got success!!");
                } else {
                    String responseJson = response.body().string();
                    AppLog.e(TAG, "responseJson: " + methodUrl + responseJson);
                    responseJson = null;
                    AppLog.e(TAG, "Got success but InputStream is null");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                statusCode = Constants.INTERNAL_SERVER_ERROR;
            }

        }
        while (false);
        return null;
    }


    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(Void result) {
        AppLog.e(TAG, "onPostExecute++");
        super.onPostExecute(result);
        if (null != listener && false == isCancelled()) {
            listener.onResult(apiUrl, apiMethod, statusCode, resultType, resultObj, serverResponse);
        } else {
            AppLog.e(TAG, "listener is null!!!");
        }
    }

    /**
     * Execute.
     */
    public void execute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
        } else {
            execute((Void[]) null);
        }
    }


}
