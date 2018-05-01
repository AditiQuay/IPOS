package quay.com.ipos.retailsales.service;

import android.os.AsyncTask;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;

import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;


// TODO: Auto-generated Javadoc

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
        public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj);
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
     * The _service url.
     */
    private int apiCallType = -1;

    /**
     * The _result obj.
     */
    private Object resultObj = null;

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

    private RequestBody fileToUpload = null;

    private String getParameters;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Sets the listener.
     *
     * @param listener the new listener
     */
    public void setListener(ServiceResultListener listener) {
        this.listener = listener;
    }

    public void setPhotoEntity(RequestBody fileToUpload) {
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


    /**
     * @param paramObj the paramObj to set
     */
    public void setParamObj(Object paramObj) {
        this.paramObj = paramObj;
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
                    methodUrl = apiUrl + apiMethod ;
                }
                AppLog.e(TAG, "methodUrl: " + methodUrl);
                OkHttpClient client = new OkHttpClient();
                Request request = null;


                Gson gson = new GsonBuilder().create();


                if (apiMethod.equals("upload.php")) {
                    request = new Request.Builder()
                            .url(methodUrl)
                            .post(fileToUpload)
                            .build();
                } else {
                    if (apiCallType == Constants.API_METHOD_GET) {
                        request = new Request.Builder()
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
                            RequestBody body = RequestBody.create(JSON, requestJson);
                            request = new Request.Builder()
                                    .url(methodUrl)
                                    .post(body)
                                    .build();

                            requestJson = null;
                        }
                    }
                }


                // Send request to WCF service
                if (isCancelled()) {
                    break;
                }

                Response response = client.newCall(request).execute();
                AppLog.e(TAG, "StatusCode : " + response.code());

                if (200 == response.code()) {
                    String responseJson = response.body().string();
                    AppLog.e(TAG, "responseJson: " + methodUrl + responseJson);
                    AppLog.e(TAG, responseJson);

                    if (false == responseJson.isEmpty() && null != resultType) {
                        if (isCancelled()) {
                            break;
                        }

                        resultObj = gson.fromJson(responseJson, resultType);
                        responseJson = null;
                    } else {
                        AppLog.e(TAG, "Got success but responseJson is empty or no result expected");
                    }
                    AppLog.e(TAG, "Got success!!");
                } else {
                    AppLog.e(TAG, "Got success but InputStream is null");
                }
            } catch (Exception ex) {
                ex.printStackTrace();

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
            listener.onResult(apiUrl, apiMethod, statusCode, resultType, resultObj);
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
