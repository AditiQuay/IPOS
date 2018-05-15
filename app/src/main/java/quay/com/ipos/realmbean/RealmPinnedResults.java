package quay.com.ipos.realmbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.modal.ProductListResult;

/**
 * Created by aditi.bhuranda on 23-04-2018.
 */
public class RealmPinnedResults {
    private ArrayList<Info> info = new ArrayList<>();

    public ArrayList<Info> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Info> info) {
        this.info = info;
    }
    public class Info  {
        @PrimaryKey
        private String key;

        private ArrayList<ProductListResult.Datum> data = null;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public ArrayList<ProductListResult.Datum> getData() {
            return data;
        }

        public void setData(ArrayList<ProductListResult.Datum> data) {
            this.data = data;
        }

    }
}
