package quay.com.ipos.ui;

import android.content.Intent;

public class MessageEvent { /* Additional fields if needed */

    public MessageEvent(int status,Intent mIntent){
        this.status = status;
        this.mIntent = mIntent;
    }
    Intent mIntent;

    public Intent getmIntent() {
        return mIntent;
    }

    public void setmIntent(Intent mIntent) {
        this.mIntent = mIntent;
    }

    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}