package quay.com.ipos.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerSpinner;
import quay.com.ipos.enums.CustomerEnum;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.Util;

import static quay.com.ipos.customerInfo.customerInfoModal.CustomerModel.TABLE_NAME;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "IPOS_MANAGER";

    // Retail table name
    public static final String TABLE_RETAIL = "RetailTable";
    public static final String TABLE_RETAIL_CART = "RetailTableCart";

    // OpnionTable table name
//	public static final String TABLE_OPINION = "OpnionTable";

    // TestTable name
//	public static final String TABLE_TEST = "TestTable";

    // Retail Table Columns names
    private static final String KEY_ID = "iId";
    private static final String KEY_productCode = "productCode";
    private static final String KEY_iProductModalId = "iProductModalId";
    private static final String KEY_sProductName = "sProductName";
    private static final String KEY_sProductFeature = "sProductFeature";
    private static final String KEY_sProductImage = "productImage";
    private static final String KEY_sProductPrice = "sProductPrice";
    private static final String KEY_sProductStock = "sProductStock";
    private static final String KEY_sProductWeight = "sProductWeight";
    private static final String KEY_isDiscount = "isDiscount";
    private static final String KEY_gstPerc = "gstPerc";
    private static final String KEY_cgst = "cgst";
    private static final String KEY_sgst = "sgst";
    private static final String KEY_salesPrice = "salesPrice";
    private static final String KEY_nrv = "nrv";
    private static final String KEY_gpl = "gpl";
    private static final String KEY_mrp = "mrp";
    private static final String KEY_barCodeNumber = "barCodeNumber";
    private static final String KEY_discount = "discount";
    private static final String KEY_points = "points";
    private static final String KEY_pointsBasedOn = "pointsBasedOn";
    private static final String KEY_valueFrom = "valueFrom";
    private static final String KEY_valueTo = "valueTo";
    private static final String KEY_pointsPer = "pointsPer";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    ProductSearchResult mProductSearchResult = new ProductSearchResult();
//	OpinionPollListResult mOpinionPollListResult = new OpinionPollListResult();
//	LearnTestResult mLearnTestResult = new LearnTestResult();

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RETAIL_TABLE = "CREATE TABLE " + TABLE_RETAIL + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_productCode + " TEXT," + KEY_iProductModalId + " TEXT," + KEY_sProductName + " TEXT," + KEY_sProductFeature + " TEXT," + KEY_sProductImage + " TEXT," + KEY_sProductPrice + " REAL," + KEY_sProductStock + " INTEGER," + KEY_sProductWeight + " INTEGER," + KEY_isDiscount + " INTEGER," + KEY_gstPerc + " REAL," + KEY_cgst + " REAL," + KEY_sgst + " REAL," + KEY_salesPrice + " REAL," + KEY_nrv + " REAL," + KEY_gpl + " REAL," + KEY_mrp + " REAL," + KEY_barCodeNumber + " TEXT," + KEY_discount + " TEXT," + KEY_points+ " INTEGER,"+KEY_pointsBasedOn+ " TEXT,"+KEY_pointsPer+ " INTEGER,"+KEY_valueFrom+ " INTEGER,"+KEY_valueTo+ " INTEGER"+")";
        db.execSQL(CREATE_RETAIL_TABLE);

        // create notes table
        db.execSQL(CustomerModel.CREATE_TABLE);
        db.execSQL(CustomerModel.SPINNER_TABLE_CREATE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RETAIL);

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CustomerModel.TABLE_SPINNER);


        // Create tables again
        // onCreate(db);

//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPINION);

        // Create tables again
        // onCreate(db);

//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

//	// Adding new contact
    public void addProduct(ProductSearchResult.Datum datum) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_productCode, datum.getProductCode());
        values.put(KEY_iProductModalId, datum.getIProductModalId());
        values.put(KEY_sProductName, datum.getSProductName()); //
        values.put(KEY_sProductFeature, Util.getCustomGson().toJson(datum.getSProductFeature())); //
        values.put(KEY_sProductImage, datum.getProductImage()); //
        values.put(KEY_sProductPrice, datum.getSProductPrice()); //
        values.put(KEY_sProductStock, datum.getSProductStock()); //
        values.put(KEY_sProductWeight, datum.getSProductWeight()); //
        if (datum.getIsDiscount())
            values.put(KEY_isDiscount, 1); //
        else
            values.put(KEY_isDiscount, 0); //
        values.put(KEY_gstPerc, datum.getGstPerc()); //
        values.put(KEY_cgst, datum.getCgst()); //
        values.put(KEY_sgst, datum.getSgst()); //
        values.put(KEY_salesPrice, datum.getSalesPrice()); //
        values.put(KEY_nrv, datum.getNrv()); //
        values.put(KEY_gpl, datum.getGpl()); //
        values.put(KEY_mrp, datum.getMrp()); //
        values.put(KEY_barCodeNumber, datum.getBarCodeNumber()); //
        values.put(KEY_discount, Util.getCustomGson().toJson(datum.getDiscount())); //
        values.put(KEY_points, datum.getPoints()); //
        values.put(KEY_pointsBasedOn,datum.getPointsBasedOn()); //
        values.put(KEY_pointsPer, datum.getPointsPer()); //
        values.put(KEY_valueTo, datum.getValueTo()); //
        values.put(KEY_valueFrom, datum.getValueFrom()); //
        // Inserting Row
        db.insert(TABLE_RETAIL, null, values);
        db.close(); // Closing database connection
    }

    public void addProductinCart(ProductSearchResult.Datum datum) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_productCode, datum.getProductCode());
        values.put(KEY_iProductModalId, datum.getIProductModalId());
        values.put(KEY_sProductName, datum.getSProductName()); //
        values.put(KEY_sProductFeature, Util.getCustomGson().toJson(datum.getSProductFeature())); //
        values.put(KEY_sProductImage, datum.getProductImage()); //
        values.put(KEY_sProductPrice, datum.getSProductPrice()); //
        values.put(KEY_sProductStock, datum.getSProductStock()); //
        values.put(KEY_sProductWeight, datum.getSProductWeight()); //
        if (datum.getIsDiscount())
            values.put(KEY_isDiscount, 1); //
        else
            values.put(KEY_isDiscount, 0); //
        values.put(KEY_gstPerc, datum.getGstPerc()); //
        values.put(KEY_cgst, datum.getCgst()); //
        values.put(KEY_sgst, datum.getSgst()); //
        values.put(KEY_salesPrice, datum.getSalesPrice()); //
        values.put(KEY_nrv, datum.getNrv()); //
        values.put(KEY_gpl, datum.getGpl()); //
        values.put(KEY_mrp, datum.getMrp()); //
        values.put(KEY_barCodeNumber, datum.getBarCodeNumber()); //
        values.put(KEY_discount, Util.getCustomGson().toJson(datum.getDiscount())); //
        values.put(KEY_points, datum.getPoints()); //
        values.put(KEY_pointsBasedOn,datum.getPointsBasedOn()); //
        values.put(KEY_pointsPer, datum.getPointsPer()); //
        values.put(KEY_valueTo, datum.getValueTo()); //
        values.put(KEY_valueFrom, datum.getValueFrom()); //
        // Inserting Row
        db.insert(TABLE_RETAIL_CART, null, values);
        db.close(); // Closing database connection
    }

    //
//	// Adding new test
//	public void addTestQuestionaire(
//			QuestionList mQuestionListData) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_TEST_ID, mQuestionListData.getQuestionId());
//		values.put(KEY_TEST_QUESTION, mQuestionListData.getQuestion()); //
//		// Questionnaire
//		// Question
//		values.put(KEY_TEST_ANSWER, mQuestionListData.getAnswer()); //
//		// Questionnaire
//		// Answer
//		// Inserting Row
//		db.insert(TABLE_TEST, null, values);
//		db.close(); // Closing database connection
//	}
//
//	// Adding new contact
//	public void addOptionQuestionaire(OpinionList mOpinionList) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_OPINION_ID, mOpinionList.getOpinionId());
//		values.put(KEY_OPINION_QUESTION, mOpinionList.getOpinionQuestion()); //
//		// Questionnaire
//		// Question
//		values.put(KEY_OPINION_ANSWER, mOpinionList.getAnswer()); //
//		// Questionnaire
//		// Answer
//		// Inserting Row
//		db.insert(TABLE_OPINION, null, values);
//		db.close(); // Closing database connection
//	}
//
//	// Getting single QUESTION
//	public LearnTestResult.QuestionList getTestQuestionaire(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_TEST, new String[] { KEY_TEST_ID, KEY_TEST_QUESTION, KEY_TEST_ANSWER },
//				KEY_TEST_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		LearnTestResult.QuestionList questionaire = mLearnTestResult.new QuestionList(
//				Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getInt(2));
//		// return questionnaire
//		return questionaire;
//	}
//
//	public ContestQuestionResult.QuestionList getQuestionaire(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_QUESTION, new String[] { KEY_ID, KEY_QUESTION, KEY_OPTION_ANSWER },
//				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		ContestQuestionResult.QuestionList questionaire = mContestQuestionResult.new QuestionList(Integer.parseInt(cursor.getString(0)),
//				cursor.getString(1), cursor.getInt(2));
//		// return questionnaire
//		return questionaire;
//	}
//
//	// Getting single QUESTION
//	public OpinionList getOpinionQuestionaire(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_OPINION,
//				new String[] { KEY_OPINION_ID, KEY_OPINION_QUESTION, KEY_OPINION_ANSWER }, KEY_OPINION_ID + "=?",
//				new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		OpinionList mOpinionList = mOpinionPollListResult.new OpinionList(Integer.parseInt(cursor.getString(0)),
//				cursor.getString(1), cursor.getInt(2));
//		// return questionnaire
//		return mOpinionList;
//	}
//
//	public ContestQuestionResult.QuestionList findQuestionByQuestionID(int questionId) {
//		String query = "Select * FROM " + TABLE_QUESTION + " WHERE " + KEY_ID + " = \"" + questionId + "\"";
//
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		Cursor cursor = db.rawQuery(query, null);
//
//		ContestQuestionResult.QuestionList questionaire = mContestQuestionResult.new QuestionList();
//
//		if (cursor.moveToFirst()) {
//			cursor.moveToFirst();
//			questionaire.setQuestionId(cursor.getString(0));
//			questionaire.setQuestion(cursor.getString(1));
//			questionaire.setAnswer(Integer.parseInt(cursor.getString(2)));
//			cursor.close();
//		} else {
//			questionaire = null;
//		}
//		db.close();
//		return questionaire;
//	}
//
//	public LearnTestResult.QuestionList findTestByQuestionID(int questionId) {
//		String query = "Select * FROM " + TABLE_TEST + " WHERE " + KEY_TEST_ID + " = \"" + questionId + "\"";
//
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		Cursor cursor = db.rawQuery(query, null);
//
//		LearnTestResult.QuestionList questionaire = mLearnTestResult.new QuestionList();
//
//		if (cursor.moveToFirst()) {
//			cursor.moveToFirst();
//			questionaire.setQuestionId(cursor.getString(0));
//			questionaire.setQuestion(cursor.getString(1));
//			questionaire.setAnswer(Integer.parseInt(cursor.getString(2)));
//			cursor.close();
//		} else {
//			questionaire = null;
//		}
//		db.close();
//		return questionaire;
//	}
//

    public boolean isRetailMasterEmpty() {

        boolean flag;
        String quString = "select exists(select * from " + TABLE_RETAIL + ");";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(quString, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 1) {
            flag = false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }

    ArrayList<ProductSearchResult.Discount> searchResult = new ArrayList<>();
    ArrayList<ProductSearchResult.SProductFeature> productFeatures = new
            ArrayList<>();

    //	// Getting All Questionaire
    public ArrayList<ProductSearchResult.Datum> getAllQuestionaIdByQuestionId(String questionId) {
        ArrayList<ProductSearchResult.Datum> questionList = new ArrayList<ProductSearchResult.Datum>();
        // Select All Query
        String selectQuery = "Select * FROM " + TABLE_RETAIL + " WHERE " + KEY_iProductModalId + " = \"" + questionId + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductSearchResult.Datum datum = mProductSearchResult.new Datum();
                datum.setProductCode(cursor.getString(1));
                datum.setIProductModalId(cursor.getString(2));

                datum.setSProductName(cursor.getString(3));
                productFeatures = Util.getCustomGson().fromJson(cursor.getString(4), new TypeToken<ArrayList<ProductSearchResult.SProductFeature>>() {
                }.getType());
                datum.setDiscount(searchResult);
                datum.setSProductFeature(productFeatures);
                datum.setProductImage(cursor.getString(5));
                datum.setSProductPrice(cursor.getDouble(6));
                datum.setSProductStock(cursor.getInt(7));
                datum.setSProductWeight(cursor.getInt(8));
                if (cursor.getInt(9) == 0)
                    datum.setIsDiscount(false);
                else
                    datum.setIsDiscount(true);
                datum.setGstPerc(cursor.getDouble(10));
                datum.setCgst(cursor.getDouble(11));
                datum.setSgst(cursor.getDouble(12));
                datum.setSalesPrice(cursor.getDouble(13));
                datum.setNrv(cursor.getDouble(14));
                datum.setGpl(cursor.getDouble(15));
                datum.setMrp(cursor.getDouble(16));
                datum.setBarCodeNumber(cursor.getString(17));
                searchResult = Util.getCustomGson().fromJson(cursor.getString(18), new TypeToken<ArrayList<ProductSearchResult.Discount>>() {
                }.getType());
                datum.setDiscount(searchResult);
                datum.setPoints(cursor.getInt(19)); //
                datum.setPointsBasedOn(cursor.getString(20)); //
                datum.setPointsPer(cursor.getInt(21)); //
                datum.setValueTo(cursor.getInt(22)); //
                datum.setValueFrom(cursor.getInt(23)); //
                // Adding question to List
                questionList.add(datum);
            } while (cursor.moveToNext());
        }

        // return question List
        return questionList;
    }

    //
//	// Getting All Questionaire
    public ArrayList<ProductSearchResult.Datum> getAllProduct() {
        ArrayList<ProductSearchResult.Datum> questionList = new ArrayList<ProductSearchResult.Datum>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RETAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductSearchResult.Datum datum = mProductSearchResult.new Datum();
                datum.setProductCode(cursor.getString(1));
                datum.setIProductModalId(cursor.getString(2));

                datum.setSProductName(cursor.getString(3));
                productFeatures = Util.getCustomGson().fromJson(cursor.getString(4), new TypeToken<ArrayList<ProductSearchResult.SProductFeature>>() {
                }.getType());
                datum.setDiscount(searchResult);
                datum.setSProductFeature(productFeatures);
                datum.setProductImage(cursor.getString(5));
                datum.setSProductPrice(cursor.getDouble(6));
                datum.setSProductStock(cursor.getInt(7));
                datum.setSProductWeight(cursor.getInt(8));
                if (cursor.getInt(9) == 0)
                    datum.setIsDiscount(false);
                else
                    datum.setIsDiscount(true);
                datum.setGstPerc(cursor.getDouble(10));
                datum.setCgst(cursor.getDouble(11));
                datum.setSgst(cursor.getDouble(12));
                datum.setSalesPrice(cursor.getDouble(13));
                datum.setNrv(cursor.getDouble(14));
                datum.setGpl(cursor.getDouble(15));
                datum.setMrp(cursor.getDouble(16));
                datum.setBarCodeNumber(cursor.getString(17));
                searchResult = Util.getCustomGson().fromJson(cursor.getString(18), new TypeToken<ArrayList<ProductSearchResult.Discount>>() {
                }.getType());
                datum.setDiscount(searchResult);
                datum.setPoints(cursor.getInt(19)); //
                datum.setPointsBasedOn(cursor.getString(20)); //
                datum.setPointsPer(cursor.getInt(21)); //
                datum.setValueTo(cursor.getInt(22)); //
                datum.setValueFrom(cursor.getInt(23)); //
                // Adding question to List
                questionList.add(datum);
            } while (cursor.moveToNext());
        }

        // return contact list
        return questionList;
    }

    //
//	public ArrayList<LearnTestResult.QuestionList> getAllTestQuestionaire() {
//		ArrayList<LearnTestResult.QuestionList> contactList = new ArrayList<LearnTestResult.QuestionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_TEST;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				LearnTestResult.QuestionList mQuestionListData = mLearnTestResult.new QuestionList();
//				mQuestionListData.setQuestionId(cursor.getString(0));
//				mQuestionListData.setQuestion(cursor.getString(1));
//				mQuestionListData.setAnswer(cursor.getInt(2));
//				// Adding contact to list
//				contactList.add(mQuestionListData);
//			} while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return contactList;
//	}
//
//	// Getting All Questionaire
//	public ArrayList<OpinionList> getAllOpinionQuestionaire() {
//		ArrayList<OpinionList> mOpinionList = new ArrayList<OpinionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_OPINION;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				OpinionList mQuestionListData = mOpinionPollListResult.new OpinionList();
//				mQuestionListData.setOpinionId(cursor.getString(0));
//				mQuestionListData.setOpinionQuestion(cursor.getString(1));
//				mQuestionListData.setAnswer(cursor.getInt(2));
//				// Adding contact to list
//				mOpinionList.add(mQuestionListData);
//			} while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return mOpinionList;
//	}
//
//	public ArrayList<ContestQuestionResult.QuestionList> getResultQuestionaire() {
//		ArrayList<ContestQuestionResult.QuestionList> contactList = new ArrayList<ContestQuestionResult.QuestionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_QUESTION;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				ContestQuestionResult.QuestionList mQuestionListData = mContestQuestionResult.new QuestionList();
//				mQuestionListData.setQuestionId(cursor.getString(0));
//				mQuestionListData.setAnswer(cursor.getInt(2));
//				// Adding contact to list
//				contactList.add(mQuestionListData);
//			} while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return contactList;
//	}
//
//	public ArrayList<LearnTestResult.QuestionList> getTestResultQuestionaire() {
//		ArrayList<LearnTestResult.QuestionList> contactList = new ArrayList<LearnTestResult.QuestionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_TEST;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				.LearnTestResult.QuestionList mQuestionListData = mLearnTestResult.new QuestionList();
//				mQuestionListData.setQuestionId(cursor.getString(0));
//				mQuestionListData.setAnswer(cursor.getInt(2));
//				// Adding contact to list
//				contactList.add(mQuestionListData);
//			} while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return contactList;
//	}
//
//	public ArrayList<OpinionList> getResultOpinionQuestionaire() {
//		ArrayList<OpinionList> contactList = new ArrayList<OpinionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_OPINION;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				OpinionList mOpinionList = mOpinionPollListResult.new OpinionList();
//				mOpinionList.setOpinionId(cursor.getString(0));
//				mOpinionList.setAnswer(cursor.getInt(2));
//				// Adding contact to list
//				contactList.add(mOpinionList);
//			} while (cursor.moveToNext());
//		}
//		// return contact list
//		return contactList;
//	}
//
//	// Updating single contact
//	public int updateAnswer(ContestQuestionResult.QuestionList questionaire, int questionId) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		// values.put(KEY_QUESTION, questionaire.getQuestion()); // questionaire
//		// Question
//		// values.put(KEY_CATEGORY, questionaire.getQuestionCategory()); //
//		// questionaire Category
//		values.put(KEY_OPTION_ANSWER, questionaire.getAnswer());
//
//		// updating row
//		// return the number of rows affected
//		return db.update(TABLE_QUESTION, values, KEY_ID + " = ?", new String[] { questionId + "" });
//		// new String[] { String.valueOf(questionaire.getQuesId()) });
//	}
//
//	// Updating single contact
//	public int updateTestAnswer(LearnTestResult.QuestionList questionaire, int questionId) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		// values.put(KEY_QUESTION, questionaire.getQuestion()); // questionaire
//		// Question
//		// values.put(KEY_CATEGORY, questionaire.getQuestionCategory()); //
//		// questionaire Category
//		values.put(KEY_TEST_ANSWER, questionaire.getAnswer());
//
//		// updating row
//		// return the number of rows affected
//		return db.update(TABLE_TEST, values, KEY_TEST_ID + " = ?", new String[] { questionId + "" });
//		// new String[] { String.valueOf(questionaire.getQuesId()) });
//	}
//
//	// Updating single contact
//	public int updateOpinionAnswer(OpinionList questionaire, int questionId) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		// values.put(KEY_QUESTION, questionaire.getQuestion()); // questionaire
//		// Question
//		// values.put(KEY_CATEGORY, questionaire.getQuestionCategory()); //
//		// questionaire Category
//		values.put(KEY_OPINION_ANSWER, questionaire.getAnswer());
//
//		// updating row
//		// return the number of rows affected
//		return db.update(TABLE_OPINION, values, KEY_OPINION_ID + " = ?", new String[] { questionId + "" });
//		// new String[] { String.valueOf(questionaire.getQuesId()) });
//	}
//
//	// Deleting single question
    public void deleteRetailTable(ProductSearchResult.Datum datum) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RETAIL, KEY_iProductModalId + " = ?", new String[]{String.valueOf(datum.getIProductModalId())});
        db.close();
    }

    //
//	public void deleteTestQuestionaire(LearnTestResult.QuestionList questionaire) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_TEST, KEY_TEST_ID + " = ?", new String[] { String.valueOf(questionaire.getQuestionId()) });
//		db.close();
//	}
//
//	public void deleteOpinionQuestionaire(OpinionList questionaire) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_OPINION, KEY_OPINION_ID + " = ?", new String[] { String.valueOf(questionaire.getOpinionId()) });
//		db.close();
//	}
//
    public void deleteTable(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
//
//	// Getting Questionaire Count
//	public int getQuestionaireCount() {
//		String countQuery = "SELECT * FROM " + TABLE_QUESTION;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery(countQuery, null);
//		cursor.close();
//
//		// return count
//		return cursor.getCount();
//	}


    /*
    * Custoemr List and details CRUD operation
    *
    * */
    //Getting customer detail
    public CustomerModel getCustomerDetails(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{CustomerEnum.ColoumnLocalID.toString(), CustomerEnum.ColoumnCustomerID.toString(), CustomerEnum.ColoumnCustomerTitle.toString(), CustomerEnum.ColoumnCustomerName.toString(), CustomerEnum.ColoumnCustomerFirstName.toString(), CustomerEnum.ColoumnCustomerLastName.toString(),
                        CustomerEnum.ColoumnCustomerGender.toString(), CustomerEnum.ColoumnCustomerBday.toString(), CustomerEnum.ColoumnCustomerMaritalStatus.toString(), CustomerEnum.ColoumnCustomerSpouseFirstName.toString(),
                        CustomerEnum.ColoumnCustomerSpouseLastName.toString(), CustomerEnum.ColoumnCustomerSpouseDob.toString(), CustomerEnum.ColoumnCustomerChildStatus.toString(), CustomerEnum.ColoumnCustomerChild.toString(),
                        CustomerEnum.ColoumnCustomerEmail.toString(), CustomerEnum.ColoumnCustomerEmail2.toString(), CustomerEnum.ColoumnCustomerPhone.toString(), CustomerEnum.ColoumnCustomerPhone2.toString(), CustomerEnum.ColoumnCustomerPhone3.toString(), CustomerEnum.ColoumnCustomerAddress.toString(), CustomerEnum.ColoumnCustomerState.toString(), CustomerEnum.ColoumnCustomerCity.toString(), CustomerEnum.ColoumnCustomerPin.toString(), CustomerEnum.ColoumnCustomerCountry.toString(), CustomerEnum.ColoumnCustomerDesignation.toString(), CustomerEnum.ColoumnCustomerCompany.toString(),
                        CustomerEnum.ColoumnCustomerGstin.toString(), CustomerEnum.ColoumnCustomer.toString(), CustomerEnum.ColoumnCustomerRelationship.toString(), CustomerEnum.ColoumnCustomerImage.toString(), CustomerEnum.ColoumnLastBillingDate.toString(), CustomerEnum.ColoumnLastBillingAmount.toString(), CustomerEnum.ColoumnIsSuggestion.toString(), CustomerEnum.ColoumnSuggestion.toString(),
                        CustomerEnum.ColoumnCustomerPoint.toString(), CustomerEnum.ColoumnRecentOrders.toString(), CustomerEnum.ColoumnCustomerCustomerStatus.toString(), CustomerEnum.ColoumncFactor.toString(), CustomerEnum.ColoumncType.toString(), CustomerEnum.ColoumncCustomerDOM.toString(), CustomerEnum.ColoumnIsSync.toString()},
                CustomerEnum.ColoumnCustomerID.toString() + "=?",
                new String[]{id}, null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        CustomerModel note = new CustomerModel(
                cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnLocalID.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerTitle.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerMaritalStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseFirstName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseLastName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseDob.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChildStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChild.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCountry.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGstin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomer.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerImage.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingDate.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingAmount.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnIsSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPoint.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRecentOrders.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCustomerStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncFactor.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncType.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncCustomerDOM.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCode.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRegisteredBusinessPlace.toString())),
                cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnIsSync.toString())));


        // close the db connection
        cursor.close();

        return note;
    }

    //Insert Customer Data
    public long insertCustomer(String customerID,
                               String customerTitle,
                               String customerName,
                               String customerFirstName,
                               String customerLastName,
                               String customerGender,
                               String customerBday,
                               String customerMaritalStatus,
                               String customerSpouseFirstName,
                               String customerSpouseLastName,
                               String customerSpouseDob,
                               String customerChildSatus,
                               String customerChild,
                               String customerEmail,
                               String customerEmail2,
                               String customerPhone,
                               String customerPhone2,
                               String customerPhone3,
                               String customerAddress,
                               String customerState,
                               String customerCity,
                               String customerPin,
                               String customerCountry,
                               String customerDesignation,
                               String customerCompany,
                               String customerGstin,
                               String customer,
                               String customerRelationship,
                               String customerImage,
                               String lastBillingDate,
                               String lastBillingAmount,
                               String issuggestion,
                               String suggestion,
                               String customerPoints,
                               String recent_orders,
                               String customerStatus,
                               String cfactor,
                               String customerType,
                               String customerDom,
                               String customerCode,
                               String registeredBusinessPlaceID,
                               int sync) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(CustomerEnum.ColoumnCustomerID.toString(), customerID);
        values.put(CustomerEnum.ColoumnCustomerTitle.toString(), customerTitle);
        values.put(CustomerEnum.ColoumnCustomerName.toString(), customerName);
        values.put(CustomerEnum.ColoumnCustomerFirstName.toString(), customerFirstName);
        values.put(CustomerEnum.ColoumnCustomerLastName.toString(), customerLastName);
        values.put(CustomerEnum.ColoumnCustomerGender.toString(), customerGender);
        values.put(CustomerEnum.ColoumnCustomerBday.toString(), customerBday);
        values.put(CustomerEnum.ColoumnCustomerMaritalStatus.toString(), customerMaritalStatus);
        values.put(CustomerEnum.ColoumnCustomerSpouseFirstName.toString(), customerSpouseFirstName);
        values.put(CustomerEnum.ColoumnCustomerSpouseLastName.toString(), customerSpouseLastName);
        values.put(CustomerEnum.ColoumnCustomerSpouseDob.toString(), customerSpouseDob);
        values.put(CustomerEnum.ColoumnCustomerChildStatus.toString(), customerChildSatus);
        values.put(CustomerEnum.ColoumnCustomerChild.toString(), customerChild);
        values.put(CustomerEnum.ColoumnCustomerEmail.toString(), customerEmail);
        values.put(CustomerEnum.ColoumnCustomerEmail2.toString(), customerEmail2);
        values.put(CustomerEnum.ColoumnCustomerPhone.toString(), customerPhone);
        values.put(CustomerEnum.ColoumnCustomerPhone2.toString(), customerPhone2);
        values.put(CustomerEnum.ColoumnCustomerPhone3.toString(), customerPhone3);
        values.put(CustomerEnum.ColoumnCustomerAddress.toString(), customerAddress);
        values.put(CustomerEnum.ColoumnCustomerState.toString(), customerState);
        values.put(CustomerEnum.ColoumnCustomerCity.toString(), customerCity);
        values.put(CustomerEnum.ColoumnCustomerPin.toString(), customerPin);
        values.put(CustomerEnum.ColoumnCustomerCountry.toString(), customerCountry);
        values.put(CustomerEnum.ColoumnCustomerDesignation.toString(), customerDesignation);
        values.put(CustomerEnum.ColoumnCustomerCompany.toString(), customerCompany);
        values.put(CustomerEnum.ColoumnCustomerGstin.toString(), customerGstin);
        values.put(CustomerEnum.ColoumnCustomer.toString(), customer);
        values.put(CustomerEnum.ColoumnCustomerRelationship.toString(), customerRelationship);
        values.put(CustomerEnum.ColoumnCustomerImage.toString(), customerImage);
        values.put(CustomerEnum.ColoumnLastBillingDate.toString(), lastBillingDate);
        values.put(CustomerEnum.ColoumnLastBillingAmount.toString(), lastBillingAmount);
        values.put(CustomerEnum.ColoumnIsSuggestion.toString(), issuggestion);
        values.put(CustomerEnum.ColoumnSuggestion.toString(), suggestion);
        values.put(CustomerEnum.ColoumnCustomerPoint.toString(), customerPoints);
        values.put(CustomerEnum.ColoumnRecentOrders.toString(), recent_orders);
        values.put(CustomerEnum.ColoumnCustomerCustomerStatus.toString(), customerStatus);
        values.put(CustomerEnum.ColoumncFactor.toString(), cfactor);
        values.put(CustomerEnum.ColoumncType.toString(), customerType);
        values.put(CustomerEnum.ColoumncCustomerDOM.toString(), customerDom);
        values.put(CustomerEnum.ColoumnCustomerCode.toString(), customerCode);
        values.put(CustomerEnum.ColoumnRegisteredBusinessPlace.toString(), registeredBusinessPlaceID);
        values.put(CustomerEnum.ColoumnIsSync.toString(), sync);

        // insert row
        long id = db.insert(TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    //Delete Customer Data
    public void deleteCustomerData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, CustomerEnum.ColoumnCustomerID.toString() + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int updateServerId(int localId, String serverId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CustomerEnum.ColoumnCustomerID.toString(), serverId);
        values.put(CustomerEnum.ColoumnIsSync.toString(), 1);
        // insert row
        return db.update(TABLE_NAME, values, CustomerEnum.ColoumnLocalID.toString() + " = ?", new String[]{String.valueOf(localId)});

    }

    //Update Customer Data
    public int updateCustomer(String customerID,
                              String customerTitle,
                              String customerName,
                              String customerFirstName,
                              String customerLastName,
                              String customerGender,
                              String customerBday,
                              String customerMaritalStatus,
                              String customerSpouseFirstName,
                              String customerSpouseLastName,
                              String customerSpouseDob,
                              String customerChildSatus,
                              String customerChild,
                              String customerEmail,
                              String customerEmail2,
                              String customerPhone,
                              String customerPhone2,
                              String customerPhone3,
                              String customerAddress,
                              String customerState,
                              String customerCity,
                              String customerPin,
                              String customerCountry,
                              String customerDesignation,
                              String customerCompany,
                              String customerGstin,
                              String customer,
                              String customerRelationship,
                              String customerImage,
                              String lastBillingDate,
                              String lastBillingAmount,
                              String issuggestion,
                              String suggestion,
                              String customerPoints,
                              String recent_orders,
                              String customerStatus,
                              String cfactor,
                              String customerType,
                              String customerDom,
                              String customerCode,
                              String registeredBusinessPlaceID,
                              int sync) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(CustomerEnum.ColoumnCustomerID.toString(), customerID);
        values.put(CustomerEnum.ColoumnCustomerTitle.toString(), customerTitle);
        values.put(CustomerEnum.ColoumnCustomerName.toString(), customerName);
        values.put(CustomerEnum.ColoumnCustomerFirstName.toString(), customerFirstName);
        values.put(CustomerEnum.ColoumnCustomerLastName.toString(), customerLastName);
        values.put(CustomerEnum.ColoumnCustomerGender.toString(), customerGender);
        values.put(CustomerEnum.ColoumnCustomerBday.toString(), customerBday);
        values.put(CustomerEnum.ColoumnCustomerMaritalStatus.toString(), customerMaritalStatus);
        values.put(CustomerEnum.ColoumnCustomerSpouseFirstName.toString(), customerSpouseFirstName);
        values.put(CustomerEnum.ColoumnCustomerSpouseLastName.toString(), customerSpouseLastName);
        values.put(CustomerEnum.ColoumnCustomerSpouseDob.toString(), customerSpouseDob);
        values.put(CustomerEnum.ColoumnCustomerChildStatus.toString(), customerChildSatus);
        values.put(CustomerEnum.ColoumnCustomerChild.toString(), customerChild);
        values.put(CustomerEnum.ColoumnCustomerEmail.toString(), customerEmail);
        values.put(CustomerEnum.ColoumnCustomerEmail2.toString(), customerEmail2);
        values.put(CustomerEnum.ColoumnCustomerPhone.toString(), customerPhone);
        values.put(CustomerEnum.ColoumnCustomerPhone2.toString(), customerPhone2);
        values.put(CustomerEnum.ColoumnCustomerPhone3.toString(), customerPhone3);
        values.put(CustomerEnum.ColoumnCustomerAddress.toString(), customerAddress);
        values.put(CustomerEnum.ColoumnCustomerState.toString(), customerState);
        values.put(CustomerEnum.ColoumnCustomerCity.toString(), customerCity);
        values.put(CustomerEnum.ColoumnCustomerPin.toString(), customerPin);
        values.put(CustomerEnum.ColoumnCustomerCountry.toString(), customerCountry);
        values.put(CustomerEnum.ColoumnCustomerDesignation.toString(), customerDesignation);
        values.put(CustomerEnum.ColoumnCustomerCompany.toString(), customerCompany);
        values.put(CustomerEnum.ColoumnCustomerGstin.toString(), customerGstin);
        values.put(CustomerEnum.ColoumnCustomer.toString(), customer);
        values.put(CustomerEnum.ColoumnCustomerRelationship.toString(), customerRelationship);
        values.put(CustomerEnum.ColoumnCustomerImage.toString(), customerImage);
        values.put(CustomerEnum.ColoumnLastBillingDate.toString(), lastBillingDate);
        values.put(CustomerEnum.ColoumnLastBillingAmount.toString(), lastBillingAmount);
        values.put(CustomerEnum.ColoumnIsSuggestion.toString(), issuggestion);
        values.put(CustomerEnum.ColoumnSuggestion.toString(), suggestion);
        values.put(CustomerEnum.ColoumnCustomerPoint.toString(), customerPoints);
        values.put(CustomerEnum.ColoumnRecentOrders.toString(), recent_orders);
        values.put(CustomerEnum.ColoumnCustomerCustomerStatus.toString(), customerStatus);
        values.put(CustomerEnum.ColoumncFactor.toString(), cfactor);
        values.put(CustomerEnum.ColoumncType.toString(), customerType);
        values.put(CustomerEnum.ColoumncCustomerDOM.toString(), customerDom);
        values.put(CustomerEnum.ColoumnCustomerCode.toString(), customerCode);
        values.put(CustomerEnum.ColoumnRegisteredBusinessPlace.toString(), registeredBusinessPlaceID);
        values.put(CustomerEnum.ColoumnIsSync.toString(), sync);
        // insert row
        return db.update(TABLE_NAME, values, CustomerEnum.ColoumnCustomerID.toString() + " = ?",
                new String[]{customerID});

        // return newly inserted row id
    }

    //Get Customer Record
    public long getRecordsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }

    //Get customer details
    public CustomerModel getCustomer(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{
                        CustomerEnum.ColoumnLocalID.toString(),
                        CustomerEnum.ColoumnCustomerID.toString(),
                        CustomerEnum.ColoumnCustomerTitle.toString(),
                        CustomerEnum.ColoumnCustomerName.toString(),
                        CustomerEnum.ColoumnCustomerFirstName.toString(),
                        CustomerEnum.ColoumnCustomerLastName.toString(),
                        CustomerEnum.ColoumnCustomerGender.toString(),
                        CustomerEnum.ColoumnCustomerBday.toString(),
                        CustomerEnum.ColoumnCustomerMaritalStatus.toString(),
                        CustomerEnum.ColoumnCustomerSpouseFirstName.toString(),
                        CustomerEnum.ColoumnCustomerSpouseLastName.toString(),
                        CustomerEnum.ColoumnCustomerSpouseDob.toString(),
                        CustomerEnum.ColoumnCustomerChildStatus.toString(),
                        CustomerEnum.ColoumnCustomerChild.toString(),
                        CustomerEnum.ColoumnCustomerEmail.toString(),
                        CustomerEnum.ColoumnCustomerEmail2.toString(),
                        CustomerEnum.ColoumnCustomerPhone.toString(),
                        CustomerEnum.ColoumnCustomerPhone2.toString(),
                        CustomerEnum.ColoumnCustomerPhone3.toString(),
                        CustomerEnum.ColoumnCustomerAddress.toString(),
                        CustomerEnum.ColoumnCustomerState.toString(),
                        CustomerEnum.ColoumnCustomerCity.toString(),
                        CustomerEnum.ColoumnCustomerPin.toString(),
                        CustomerEnum.ColoumnCustomerCountry.toString(),
                        CustomerEnum.ColoumnCustomerDesignation.toString(),
                        CustomerEnum.ColoumnCustomerCompany.toString(),
                        CustomerEnum.ColoumnCustomerGstin.toString(),
                        CustomerEnum.ColoumnCustomer.toString(),
                        CustomerEnum.ColoumnCustomerRelationship.toString(),
                        CustomerEnum.ColoumnCustomerImage.toString(),
                        CustomerEnum.ColoumnLastBillingDate.toString(),
                        CustomerEnum.ColoumnLastBillingAmount.toString(),
                        CustomerEnum.ColoumnIsSuggestion.toString(),
                        CustomerEnum.ColoumnSuggestion.toString(),
                        CustomerEnum.ColoumnCustomerPoint.toString(),
                        CustomerEnum.ColoumnRecentOrders.toString(),
                        CustomerEnum.ColoumnCustomerCustomerStatus.toString(),
                        CustomerEnum.ColoumncFactor.toString(),
                        CustomerEnum.ColoumncType.toString(),
                        CustomerEnum.ColoumncCustomerDOM.toString(),
                        CustomerEnum.ColoumnCustomerCode.toString(),
                        CustomerEnum.ColoumnRegisteredBusinessPlace.toString(),
                        CustomerEnum.ColoumnIsSync.toString()},
                CustomerEnum.ColoumnCustomerID.toString() + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        // prepare note object
        assert cursor != null;
        CustomerModel note = new CustomerModel(
                cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnLocalID.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerTitle.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerMaritalStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseFirstName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseLastName.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseDob.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChildStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChild.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCountry.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGstin.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomer.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerImage.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingDate.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingAmount.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnIsSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnSuggestion.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPoint.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRecentOrders.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCustomerStatus.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncFactor.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncType.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncCustomerDOM.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCode.toString())),
                cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRegisteredBusinessPlace.toString())),
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
        db.delete(TABLE_NAME, null, null);
    }

    //Getting Offline customer
    public ArrayList<CustomerModel> getAllOfflineCustomer() {
        ArrayList<CustomerModel> notes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME + " WHERE " + CustomerEnum.ColoumnIsSync.toString() + " = "
                + 0, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel note = new CustomerModel();
                note.setCustomerID(String.valueOf(cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString()))));
                note.setCustomerTitle(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerTitle.toString())));
                note.setCustomerName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())));
                note.setCustomerFirstName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())));
                note.setCustomerLastName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())));
                note.setCustomerGender(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())));
                note.setCustomerBday(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())));
                note.setCustomerMaritalStatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerMaritalStatus.toString())));
                note.setCustomerSpouseFirstName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseFirstName.toString())));
                note.setCustomerSpouseLastName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseLastName.toString())));
                note.setCustomerSpouseDob(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseDob.toString())));
                note.setCustomerChildSatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChildStatus.toString())));
                note.setCustomerChild(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChild.toString())));
                note.setCustomerEmail(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())));
                note.setCustomerEmail2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())));
                note.setCustomerPhone(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())));
                note.setCustomerPhone2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())));
                note.setCustomerPhone3(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())));
                note.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())));
                note.setCustomerState(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())));
                note.setCustomerCity(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())));
                note.setCustomerPin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())));
                note.setCustomerCountry(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCountry.toString())));
                note.setCustomerDesignation(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())));
                note.setCustomerCompany(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())));
                note.setCustoemrGstin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGstin.toString())));
                note.setCustomerRelationship(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())));
                note.setCustomerStatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCustomerStatus.toString())));
                note.setCfactor(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncFactor.toString())));
                note.setCustomerType(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncType.toString())));
                note.setCustomerDom(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncCustomerDOM.toString())));
                note.setCustomerCode(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCode.toString())));
                note.setRegisteredBusinessPlaceID(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRegisteredBusinessPlace.toString())));

                note.setCustomerChild(new Gson().toJson(note.getCustomerChild()));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }



    //Get All records from customer Database
    public ArrayList<CustomerModel> getAllNotes() {
        ArrayList<CustomerModel> notes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel note = new CustomerModel();
                note.setCustomerID(String.valueOf(cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerID.toString()))));
                note.setCustomerTitle(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerTitle.toString())));
                note.setCustomerName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerName.toString())));
                note.setCustomerFirstName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerFirstName.toString())));
                note.setCustomerLastName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerLastName.toString())));
                note.setCustomerGender(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGender.toString())));
                note.setCustomerBday(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerBday.toString())));
                note.setCustomerMaritalStatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerMaritalStatus.toString())));
                note.setCustomerSpouseFirstName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseFirstName.toString())));
                note.setCustomerSpouseLastName(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseLastName.toString())));
                note.setCustomerSpouseDob(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerSpouseDob.toString())));
                note.setCustomerChildSatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChildStatus.toString())));
                note.setCustomerChild(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerChild.toString())));
                note.setCustomerEmail(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail.toString())));
                note.setCustomerEmail2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerEmail2.toString())));
                note.setCustomerPhone(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone.toString())));
                note.setCustomerPhone2(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone2.toString())));
                note.setCustomerPhone3(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPhone3.toString())));
                note.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerAddress.toString())));
                note.setCustomerState(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerState.toString())));
                note.setCustomerCity(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCity.toString())));
                note.setCustomerPin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPin.toString())));
                note.setCustomerCountry(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCountry.toString())));
                note.setCustomerDesignation(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerDesignation.toString())));
                note.setCustomerCompany(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCompany.toString())));
                note.setCustoemrGstin(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerGstin.toString())));
                note.setCustomer(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomer.toString())));
                note.setCustomerRelationship(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerRelationship.toString())));
                note.setCustomerImage(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerImage.toString())));
                note.setLastBillingDate(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingDate.toString())));
                note.setLastBillingAmount(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnLastBillingAmount.toString())));
                note.setIssuggestion(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnIsSuggestion.toString())));
                note.setSuggestion(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnSuggestion.toString())));
                note.setCustomerPoints(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerPoint.toString())));
                note.setRecentOrders(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRecentOrders.toString())));
                note.setCustomerStatus(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCustomerStatus.toString())));
                note.setCfactor(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncFactor.toString())));
                note.setCustomerType(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncType.toString())));
                note.setCustomerDom(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumncCustomerDOM.toString())));
                note.setCustomerCode(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCustomerCode.toString())));
                note.setRegisteredBusinessPlaceID(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRegisteredBusinessPlace.toString())));
                note.setIsSync(cursor.getInt(cursor.getColumnIndex(CustomerEnum.ColoumnIsSync.toString())));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    //Insert Customer Data
    public long insertSpinnerItems(String cityList, String stateList, String countryList, String designationList, String companyList, String relationshipList, String customerType) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(CustomerEnum.ColoumnCityList.toString(), cityList);
        values.put(CustomerEnum.ColoumnStateList.toString(), stateList);
        values.put(CustomerEnum.ColoumnCountryList.toString(), countryList);
        values.put(CustomerEnum.ColoumnDesignationList.toString(), designationList);
        values.put(CustomerEnum.ColoumnCompanyList.toString(), companyList);
        values.put(CustomerEnum.ColoumnRelationShipList.toString(), relationshipList);
        values.put(CustomerEnum.ColoumnTypeList.toString(), customerType);


        // insert row
        long id = db.insert(CustomerModel.TABLE_SPINNER, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    //Get All records from customer Database
    public ArrayList<CustomerSpinner> getCustomerSpinner() {
        ArrayList<CustomerSpinner> notes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT  * FROM " + CustomerModel.TABLE_SPINNER, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerSpinner note = new CustomerSpinner();
                note.setCityList(String.valueOf(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCityList.toString()))));
                note.setStateList(String.valueOf(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnStateList.toString()))));
                note.setCountryList(String.valueOf(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCountryList.toString()))));
                note.setDesignationList(String.valueOf(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnDesignationList.toString()))));
                note.setCompanyList(String.valueOf(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnCompanyList.toString()))));
                note.setRelationshipList(String.valueOf(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnRelationShipList.toString()))));
                note.setCustomerTypeList(String.valueOf(cursor.getString(cursor.getColumnIndex(CustomerEnum.ColoumnTypeList.toString()))));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    /**
     * Remove all users and groups from database.
     */
    public void removeSpinnerList() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(CustomerModel.TABLE_SPINNER, null, null);
    }
    public boolean isCustomerDataEmpty() {

        boolean flag;
        String quString = "select exists(select * from " + TABLE_NAME + ");";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(quString, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 1) {
            flag = false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }
}
