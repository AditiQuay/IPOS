package quay.com.ipos.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.enums.CustomerEnum;

/**
 * Created by niraj.kumar on 5/22/2018.
 */

public class CustomerListDB extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "iPos_db";

    public CustomerListDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(CustomerModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CustomerModel.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public CustomerModel getCustomerDetails(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CustomerModel.TABLE_NAME,
                new String[]{CustomerEnum.ColoumnCustomerID.toString(), CustomerEnum.ColoumnCustomerName.toString(), CustomerEnum.ColoumnCustomerPoints.toString(), CustomerEnum.ColoumnCustomerPhone.toString(), CustomerEnum.ColoumnCustomerPhone2.toString(),
                        CustomerEnum.ColoumnCustomerPhone3.toString(), CustomerEnum.ColoumnCustomerEmail.toString(), CustomerEnum.ColoumnCustomerEmail2.toString(), CustomerEnum.ColoumnCustomerDom.toString(),
                        CustomerEnum.ColoumnCustomerBday.toString(), CustomerEnum.ColoumnCustomerGender.toString(), CustomerEnum.ColoumnCustomerFirstName.toString(), CustomerEnum.ColoumnCustomerLastName.toString(),
                        CustomerEnum.ColoumnCustoemrGstin.toString(), CustomerEnum.ColoumnCustomerStatus.toString(), CustomerEnum.ColoumnCustomerDesignation.toString(), CustomerEnum.ColoumnCustomerCompany.toString(), CustomerEnum.ColoumnCustomerRelationship.toString(), CustomerEnum.ColoumnCfactor.toString(), CustomerEnum.ColoumnCustomerAddress.toString(), CustomerEnum.ColoumnCustomerState.toString(), CustomerEnum.ColoumnCustomerCity.toString(), CustomerEnum.ColoumnCustomerPin.toString(), CustomerEnum.ColoumnCustomerImage.toString(), CustomerEnum.ColoumnLastBillingDate.toString(),
                        CustomerEnum.ColoumnLastBillingAmount.toString(), CustomerEnum.ColoumnIsSuggestion.toString(), CustomerEnum.ColoumnSuggestion.toString(), CustomerEnum.ColoumnRecentOrders.toString(), CustomerEnum.ColoumnIsSync.toString()},
                CustomerEnum.ColoumnCustomerID.toString() + "=?",
                new String[]{id}, null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        CustomerModel note = new CustomerModel(
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPoints.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDom.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustoemrGstin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCfactor.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerImage.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingDate.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingAmount.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnIsSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRecentOrders.toString())),
                cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnIsSync.toString())));


        // close the db connection
        cursor.close();

        return note;
    }

    //Insert Data
    public long insertCustomer(String customerID, String customerName,
                               String customerPoints, String customerPhone, String customerPhone2, String customerPhone3, String customerEmail, String customerEmail2, String customerDom,
                               String customerBday, String customerGender, String customerFirstName, String customerLastName, String custoemrGstin, String customerStatus, String customerDesignation, String customerCompany, String customerRelationship, String cfactor, String customerAddress, String customerState, String customerCity, String customerPin, String customerImage, String lastBillingDate, String lastBillingAmount, String issuggestion, String suggestion, String recent_orders, int sync) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(CustomerEnum.ColoumnCustomerID.toString(), customerID);
        values.put(CustomerEnum.ColoumnCustomerName.toString(), customerName);
        values.put(CustomerEnum.ColoumnCustomerPoints.toString(), customerPoints);
        values.put(CustomerEnum.ColoumnCustomerPhone.toString(), customerPhone);
        values.put(CustomerEnum.ColoumnCustomerPhone2.toString(), customerPhone2);
        values.put(CustomerEnum.ColoumnCustomerPhone3.toString(), customerPhone3);
        values.put(CustomerEnum.ColoumnCustomerEmail.toString(), customerEmail);
        values.put(CustomerEnum.ColoumnCustomerEmail2.toString(), customerEmail2);
        values.put(CustomerEnum.ColoumnCustomerDom.toString(), customerDom);
        values.put(CustomerEnum.ColoumnCustomerBday.toString(), customerBday);
        values.put(CustomerEnum.ColoumnCustomerGender.toString(), customerGender);
        values.put(CustomerEnum.ColoumnCustomerFirstName.toString(), customerFirstName);
        values.put(CustomerEnum.ColoumnCustomerLastName.toString(), customerLastName);
        values.put(CustomerEnum.ColoumnCustoemrGstin.toString(), custoemrGstin);
        values.put(CustomerEnum.ColoumnCustomerStatus.toString(), customerStatus);
        values.put(CustomerEnum.ColoumnCustomerDesignation.toString(), customerDesignation);
        values.put(CustomerEnum.ColoumnCustomerCompany.toString(), customerCompany);
        values.put(CustomerEnum.ColoumnCustomerRelationship.toString(), customerRelationship);
        values.put(CustomerEnum.ColoumnCfactor.toString(), cfactor);
        values.put(CustomerEnum.ColoumnCustomerAddress.toString(), customerAddress);
        values.put(CustomerEnum.ColoumnCustomerState.toString(), customerState);
        values.put(CustomerEnum.ColoumnCustomerCity.toString(), customerCity);
        values.put(CustomerEnum.ColoumnCustomerPin.toString(), customerPin);
        values.put(CustomerEnum.ColoumnCustomerImage.toString(), customerImage);
        values.put(CustomerEnum.ColoumnLastBillingDate.toString(), lastBillingDate);
        values.put(CustomerEnum.ColoumnLastBillingAmount.toString(), lastBillingAmount);
        values.put(CustomerEnum.ColoumnIsSuggestion.toString(), issuggestion);
        values.put(CustomerEnum.ColoumnSuggestion.toString(), suggestion);
        values.put(CustomerEnum.ColoumnRecentOrders.toString(), recent_orders);
        values.put(CustomerEnum.ColoumnIsSync.toString(), sync);
        // insert row
        long id = db.insert(CustomerModel.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    //Delete data
    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CustomerModel.TABLE_NAME, CustomerEnum.ColoumnCustomerID.toString() + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    //Update Data
    public int updateCustomer(String customerID, String customerName, String customerPoints, String customerPhone, String customerPhone2, String customerPhone3, String customerEmail, String customerEmail2, String customerDom, String customerBday,
                              String customerGender, String customerFirstName, String customerLastName, String custoemrGstin, String customerStatus, String customerDesignation, String customerCompany, String customerRelationship, String cfactor, String customerAddress,
                              String customerState, String customerCity, String customerPin, String customerImage, String lastBillingDate, String lastBillingAmount, String issuggestion, String suggestion, String recent_orders, int sync) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(CustomerEnum.ColoumnCustomerID.toString(), customerID);
        values.put(CustomerEnum.ColoumnCustomerName.toString(), customerName);
        values.put(CustomerEnum.ColoumnCustomerPoints.toString(), customerPoints);
        values.put(CustomerEnum.ColoumnCustomerPhone.toString(), customerPhone);
        values.put(CustomerEnum.ColoumnCustomerPhone2.toString(), customerPhone2);
        values.put(CustomerEnum.ColoumnCustomerPhone3.toString(), customerPhone3);
        values.put(CustomerEnum.ColoumnCustomerEmail.toString(), customerEmail);
        values.put(CustomerEnum.ColoumnCustomerEmail2.toString(), customerEmail2);
        values.put(CustomerEnum.ColoumnCustomerDom.toString(), customerDom);
        values.put(CustomerEnum.ColoumnCustomerBday.toString(), customerBday);
        values.put(CustomerEnum.ColoumnCustomerGender.toString(), customerGender);
        values.put(CustomerEnum.ColoumnCustomerFirstName.toString(), customerFirstName);
        values.put(CustomerEnum.ColoumnCustomerLastName.toString(), customerLastName);
        values.put(CustomerEnum.ColoumnCustoemrGstin.toString(), custoemrGstin);
        values.put(CustomerEnum.ColoumnCustomerStatus.toString(), customerStatus);
        values.put(CustomerEnum.ColoumnCustomerDesignation.toString(), customerDesignation);
        values.put(CustomerEnum.ColoumnCustomerCompany.toString(), customerCompany);
        values.put(CustomerEnum.ColoumnCustomerRelationship.toString(), customerRelationship);
        values.put(CustomerEnum.ColoumnCfactor.toString(), cfactor);
        values.put(CustomerEnum.ColoumnCustomerAddress.toString(), customerAddress);
        values.put(CustomerEnum.ColoumnCustomerState.toString(), customerState);
        values.put(CustomerEnum.ColoumnCustomerCity.toString(), customerCity);
        values.put(CustomerEnum.ColoumnCustomerPin.toString(), customerPin);
        values.put(CustomerEnum.ColoumnCustomerImage.toString(), customerImage);
        values.put(CustomerEnum.ColoumnLastBillingDate.toString(), lastBillingDate);
        values.put(CustomerEnum.ColoumnLastBillingAmount.toString(), lastBillingAmount);
        values.put(CustomerEnum.ColoumnIsSuggestion.toString(), issuggestion);
        values.put(CustomerEnum.ColoumnSuggestion.toString(), suggestion);
        values.put(CustomerEnum.ColoumnRecentOrders.toString(), recent_orders);
        values.put(CustomerEnum.ColoumnIsSync.toString(), sync);
        // insert row
        return db.update(CustomerModel.TABLE_NAME, values, CustomerEnum.ColoumnCustomerID.toString() + " = ?",
                new String[]{customerID});

        // return newly inserted row id
    }

    public long getRecordsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, CustomerModel.TABLE_NAME);
        db.close();
        return count;
    }

    public CustomerModel getCustomer(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CustomerModel.TABLE_NAME,
                new String[]{CustomerEnum.ColoumnCustomerID.toString(), CustomerEnum.ColoumnCustomerName.toString(), CustomerEnum.ColoumnCustomerPoints.toString(), CustomerEnum.ColoumnCustomerPhone.toString(), CustomerEnum.ColoumnCustomerPhone2.toString(),
                        CustomerEnum.ColoumnCustomerPhone3.toString(), CustomerEnum.ColoumnCustomerEmail.toString(), CustomerEnum.ColoumnCustomerEmail2.toString(), CustomerEnum.ColoumnCustomerDom.toString(),
                        CustomerEnum.ColoumnCustomerBday.toString(), CustomerEnum.ColoumnCustomerGender.toString(), CustomerEnum.ColoumnCustomerFirstName.toString(), CustomerEnum.ColoumnCustomerLastName.toString(),
                        CustomerEnum.ColoumnCustoemrGstin.toString(), CustomerEnum.ColoumnCustomerStatus.toString(), CustomerEnum.ColoumnCustomerDesignation.toString(), CustomerEnum.ColoumnCustomerCompany.toString(), CustomerEnum.ColoumnCustomerRelationship.toString(), CustomerEnum.ColoumnCfactor.toString(), CustomerEnum.ColoumnCustomerAddress.toString(), CustomerEnum.ColoumnCustomerState.toString(), CustomerEnum.ColoumnCustomerCity.toString(), CustomerEnum.ColoumnCustomerPin.toString(), CustomerEnum.ColoumnCustomerImage.toString(), CustomerEnum.ColoumnLastBillingDate.toString(),
                        CustomerEnum.ColoumnLastBillingAmount.toString(), CustomerEnum.ColoumnIsSuggestion.toString(), CustomerEnum.ColoumnSuggestion.toString(), CustomerEnum.ColoumnRecentOrders.toString(), CustomerEnum.ColoumnIsSync.toString()},
                CustomerEnum.ColoumnCustomerID.toString() + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        // prepare note object
        assert cursor != null;
        CustomerModel note = new CustomerModel(
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPoints.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDom.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustoemrGstin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCfactor.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerImage.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingDate.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingAmount.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnIsSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRecentOrders.toString())),
                cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnIsSync.toString())));

        // close the db connection
        cursor.close();

        return note;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(CustomerModel.TABLE_NAME, null, null);
    }

    public ArrayList<CustomerModel> getAllOfflineCustomer() {
        ArrayList<CustomerModel> notes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT  * FROM " + CustomerModel.TABLE_NAME + " where `" + CustomerEnum.ColoumnIsSync.toString() + "`="
                + "0", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel note = new CustomerModel();

                note.setCustomerID(String.valueOf(cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString()))));
                note.setCustomerName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())));
                note.setCustomerPoints(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPoints.toString())));
                note.setCustomerPhone(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())));
                note.setCustomerPhone2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())));
                note.setCustomerPhone3(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())));
                note.setCustomerEmail(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())));
                note.setCustomerEmail2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())));
                note.setCustomerDom(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())));
                note.setCustomerBday(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())));
                note.setCustomerGender(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())));
                note.setCustomerFirstName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())));
                note.setCustomerLastName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())));
                note.setCustoemrGstin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustoemrGstin.toString())));
                note.setCustomerStatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerStatus.toString())));
                note.setCustomerDesignation(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())));
                note.setCustomerCompany(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())));
                note.setCustomerRelationship(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())));
                note.setCfactor(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCfactor.toString())));
                note.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())));
                note.setCustomerState(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())));
                note.setCustomerCity(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())));
                note.setCustomerPin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())));
                note.setCustomerImage(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerImage.toString())));
                note.setLastBillingDate(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingDate.toString())));
                note.setLastBillingAmount(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingAmount.toString())));
                note.setIssuggestion(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnIsSuggestion.toString())));
                note.setSuggestion(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnSuggestion.toString())));
                note.setRecentOrders(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRecentOrders.toString())));
                note.setIsSync(cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnIsSync.toString())));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public ArrayList<CustomerModel> getAllNotes() {
        ArrayList<CustomerModel> notes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT  * FROM " + CustomerModel.TABLE_NAME, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel note = new CustomerModel();
                note.setCustomerID(String.valueOf(cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString()))));
                note.setCustomerName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())));
                note.setCustomerPoints(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPoints.toString())));
                note.setCustomerPhone(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())));
                note.setCustomerPhone2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())));
                note.setCustomerPhone3(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())));
                note.setCustomerEmail(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())));
                note.setCustomerEmail2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())));
                note.setCustomerDom(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())));
                note.setCustomerBday(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())));
                note.setCustomerGender(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())));
                note.setCustomerFirstName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())));
                note.setCustomerLastName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())));
                note.setCustoemrGstin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustoemrGstin.toString())));
                note.setCustomerStatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerStatus.toString())));
                note.setCustomerDesignation(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())));
                note.setCustomerCompany(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())));
                note.setCustomerRelationship(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())));
                note.setCfactor(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCfactor.toString())));
                note.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())));
                note.setCustomerState(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())));
                note.setCustomerCity(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())));
                note.setCustomerPin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())));
                note.setCustomerImage(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerImage.toString())));
                note.setLastBillingDate(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingDate.toString())));
                note.setLastBillingAmount(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingAmount.toString())));
                note.setIssuggestion(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnIsSuggestion.toString())));
                note.setSuggestion(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnSuggestion.toString())));
                note.setRecentOrders(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRecentOrders.toString())));
                note.setIsSync(cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnIsSync.toString())));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }
}
