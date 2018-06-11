package quay.com.ipos.realmbean;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
//import quay.com.ipos.modal.PinnedResult;


public class RealmController {


    public void clearRealm(Context context) {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();

           /* Prefs.clearValue(AppConstants.UserId);
            Prefs.clearValue(AppConstants.Login_Status);
            Prefs.clearValue(AppConstants.USERNAME);
            Prefs.clearValue(AppConstants.EMPLOYEE_NAME);
            Prefs.clearValue(AppConstants.employee_name);
            Prefs.clearValue(AppConstants.name);*/
       /*     realm.delete(RealmAnswers.class);
            realm.delete(RealmCategory.class);
            realm.delete(RealmCategoryAnswers.class);
            realm.delete(RealmClient.class);
            realm.delete(RealmCustomer.class);
            realm.delete(RealmRoles.class);

            realm.delete(RealmQuestion.class);
            realm.delete(RealmQuestions.class);
            realm.delete(RealmSurveys.class);
            realm.delete(RealmWorkFlow.class);
            realm.delete(RealmSurveysList.class);
            realm.delete(RealmUser.class);
            realm.delete(RealmSurveyQuestion.class);*/


        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }


    public void saveFormNewRetailerSubmit(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject jsonResponse = new JSONObject(responseData);
            if (jsonResponse != null) {
                realm.beginTransaction();

                realm.createOrUpdateObjectFromJson(CatalogueModal.class, jsonResponse);

            }
        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }


    public void saveUserDetail(String responseData) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
              realm.createOrUpdateObjectFromJson(RealmUserDetail.class, responseData);
        } catch (Exception e) {
            if (realm.isInTransaction())
                realm.cancelTransaction();
            e.printStackTrace();
        } finally {
            if (realm.isInTransaction())
                realm.commitTransaction();
            realm.close();
        }

    }
    public void saveBusinessPlaces(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createOrUpdateAllFromJson(RealmBusinessPlaces.class, new JSONArray(responseData));
        } catch (Exception e) {
            if (realm.isInTransaction())
                realm.cancelTransaction();
            e.printStackTrace();
        } finally {
            if (realm.isInTransaction())
                realm.commitTransaction();
            realm.close();
        }

    }
    public void saveCustomers(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createOrUpdateAllFromJson(RealmCustomerInfoModal.class, new JSONArray(responseData));
        } catch (Exception e) {
            if (realm.isInTransaction())
                realm.cancelTransaction();
            e.printStackTrace();
        } finally {
            if (realm.isInTransaction())
                realm.commitTransaction();
            realm.close();
        }

    }



    //    public void saveQuestions(String responseData) {
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        try {
//            realm.createOrUpdateAllFromJson(ProductList.class, new JSONArray(responseData));
//        } catch (Exception e) {
//            if (realm.isInTransaction())
//                realm.cancelTransaction();
//            e.printStackTrace();
//        } finally {
//            if (realm.isInTransaction())
//                realm.commitTransaction();
//            realm.close();
//        }
//
//    }
//
//    public ArrayList<ProductList> allData(){
//        ArrayList<ProductList> datumArrayList=new ArrayList<>();
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults<ProductList> productLists=realm.where(ProductList.class).findAll();
//        datumArrayList = (ArrayList<ProductList>) realm.copyFromRealm(productLists);
//        return datumArrayList;
//    }
//
//    @SuppressLint("NewApi")
//    public void savePin(RealmPinnedResults responseData) {
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        try {
//            realm.createOrUpdateAllFromJson(RealmPinnedResults.class, new JSONArray(responseData));
//        } catch (Exception e) {
//            if (realm.isInTransaction())
//                realm.cancelTransaction();
//            e.printStackTrace();
//        } finally {
//            if (realm.isInTransaction())
//                realm.commitTransaction();
//            realm.close();
//        }
//
//    }
//
//    public RealmPinnedResults getAllPinnedData(){
////        ArrayList<RealmPinnedResults> datumArrayList=new ArrayList<>();
//        Realm realm = Realm.getDefaultInstance();
//        RealmPinnedResults productLists=realm.where(RealmPinnedResults.class).findFirst();
////        datumArrayList = (ArrayList<RealmPinnedResults>) realm.copyFromRealm(productLists);
//        return productLists;
//    }
    public void saveQuestions(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createOrUpdateAllFromJson(CatalogueModal.class, new JSONArray(responseData));
        } catch (Exception e) {
            if (realm.isInTransaction())
                realm.cancelTransaction();
            e.printStackTrace();
        } finally {
            if (realm.isInTransaction())
                realm.commitTransaction();
            realm.close();
        }

    }

    public void saveOrderCentreSummary(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createOrUpdateAllFromJson(RealmOrderCentreSummary.class, new JSONArray(responseData));
        } catch (Exception e) {
            if (realm.isInTransaction())
                realm.cancelTransaction();
            e.printStackTrace();
        } finally {
            if (realm.isInTransaction())
                realm.commitTransaction();
            realm.close();
        }

    }
}

