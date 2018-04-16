package quay.com.ipos.realmbean;

import android.content.Context;


import org.json.JSONObject;

import io.realm.Realm;


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


    public void saveFormNewRetailerSubmit(String responseData, String isUpdate) {
        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject jsonResponse = new JSONObject(responseData);
            if (jsonResponse != null) {
                realm.beginTransaction();

                  //  realm.createOrUpdateObjectFromJson(RealmCustomer.class, jsonResponse);

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
         //   realm.createOrUpdateObjectFromJson(RealmUser.class, responseData);
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
    public void saveQuestions(String responseData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
       //     realm.createOrUpdateAllFromJson(RealmQuestions.class, new JSONArray(responseData));
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

