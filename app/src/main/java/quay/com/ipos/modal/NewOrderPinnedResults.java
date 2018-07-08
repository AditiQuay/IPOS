package quay.com.ipos.modal;

import java.util.ArrayList;

import io.realm.annotations.PrimaryKey;
import quay.com.ipos.realmbean.RealmPinnedResults;

/**
 * Created by aditi.bhuranda on 04-05-2018.
 */

public class NewOrderPinnedResults  {
        private ArrayList<NewOrderPinnedResults.Info> info = new ArrayList<>();

        public ArrayList<NewOrderPinnedResults.Info> getInfo() {
            return info;
        }

        public void setInfo(ArrayList<NewOrderPinnedResults.Info> info) {
            this.info = info;
        }
        public class Info  {
            @PrimaryKey
            private String key;

            private ArrayList<OrderList.Datum> data = null;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public ArrayList<OrderList.Datum> getData() {
                return data;
            }

            public void setData(ArrayList<OrderList.Datum> data) {
                this.data = data;
            }

        }
}
