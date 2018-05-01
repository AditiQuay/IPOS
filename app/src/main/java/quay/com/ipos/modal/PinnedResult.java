//package quay.com.ipos.modal;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//
//public class PinnedResult extends RealmObject {
//
//@SerializedName("info")
//@Expose
//private ArrayList<Info> info = new ArrayList<>();
//
//public ArrayList<Info> getInfo() {
//return info;
//}
//
//public void setInfo(ArrayList<Info> info) {
//this.info = info;
//}
//    public class Info {
//
//        @SerializedName("key")
//        @Expose
//        private String key;
//        @SerializedName("data")
//        @Expose
//        private ArrayList<ProductList.Datum> data = null;
//
//        public String getKey() {
//            return key;
//        }
//
//        public void setKey(String key) {
//            this.key = key;
//        }
//
//        public ArrayList<ProductList.Datum> getData() {
//            return data;
//        }
//
//        public void setData(ArrayList<ProductList.Datum> data) {
//            this.data = data;
//        }
//
//    }
//}