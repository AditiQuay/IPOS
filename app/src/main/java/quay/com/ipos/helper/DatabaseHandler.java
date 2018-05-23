package quay.com.ipos.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.enums.CustomerEnum;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.utility.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "IPOS_MANAGER";

	// Retail table name
	public static final String TABLE_RETAIL = "RetailTable";

	// OpnionTable table name
//	public static final String TABLE_OPINION = "OpnionTable";

	// TestTable name
//	public static final String TABLE_TEST = "TestTable";

	// Retail Table Columns names
	private static final String KEY_ID = "iId";
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


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	ProductSearchResult mProductSearchResult = new ProductSearchResult();
//	OpinionPollListResult mOpinionPollListResult = new OpinionPollListResult();
//	LearnTestResult mLearnTestResult = new LearnTestResult();

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_RETAIL_TABLE = "CREATE TABLE " + TABLE_RETAIL + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+KEY_iProductModalId + " TEXT," + KEY_sProductName	+ " TEXT," + KEY_sProductFeature + " TEXT,"+KEY_sProductImage + " TEXT,"+KEY_sProductPrice + " REAL,"+KEY_sProductStock + " INTEGER,"+KEY_sProductWeight + " INTEGER,"+KEY_isDiscount + " INTEGER,"+KEY_gstPerc + " REAL,"+KEY_cgst + " REAL," +KEY_sgst + " REAL,"+KEY_salesPrice + " REAL,"+KEY_nrv + " REAL,"+KEY_gpl + " REAL,"+KEY_mrp + " REAL,"+KEY_barCodeNumber + " TEXT,"+KEY_discount + " TEXT"+ ")";
		db.execSQL(CREATE_RETAIL_TABLE);

		// create notes table
		db.execSQL(CustomerModel.CREATE_TABLE);

//		String CREATE_OPINION_TABLE = "CREATE TABLE " + TABLE_OPINION + "(" + KEY_OPINION_ID + " INTEGER,"
//				+ KEY_OPINION_QUESTION + " TEXT," + KEY_OPINION_ANSWER + " INTEGER" + ")";

//		db.execSQL(CREATE_OPINION_TABLE);

//		String CREATE_TEST_TABLE = "CREATE TABLE " + TABLE_TEST + "(" + KEY_TEST_ID + " INTEGER," + KEY_TEST_QUESTION
//				+ " TEXT," + KEY_TEST_ANSWER + " INTEGER" + ")";

//		db.execSQL(CREATE_TEST_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RETAIL);
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + CustomerModel.TABLE_NAME);

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
		values.put(KEY_iProductModalId,datum.getIProductModalId());
		values.put(KEY_sProductName, datum.getSProductName()); //
		values.put(KEY_sProductFeature,Util.getCustomGson().toJson(datum.getSProductFeature())); //
		values.put(KEY_sProductImage, datum.getProductImage()); //
		values.put(KEY_sProductPrice, datum.getSProductPrice()); //
		values.put(KEY_sProductStock, datum.getSProductStock()); //
		values.put(KEY_sProductWeight, datum.getSProductWeight()); //
		if(datum.getIsDiscount())
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
		// Inserting Row
		db.insert(TABLE_RETAIL, null, values);
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
//	public in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList getTestQuestionaire(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_TEST, new String[] { KEY_TEST_ID, KEY_TEST_QUESTION, KEY_TEST_ANSWER },
//				KEY_TEST_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList questionaire = mLearnTestResult.new QuestionList(
//				Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getInt(2));
//		// return questionnaire
//		return questionaire;
//	}
//
//	public in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList getQuestionaire(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_QUESTION, new String[] { KEY_ID, KEY_QUESTION, KEY_OPTION_ANSWER },
//				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList questionaire = mContestQuestionResult.new QuestionList(Integer.parseInt(cursor.getString(0)),
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
//	public in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList findQuestionByQuestionID(int questionId) {
//		String query = "Select * FROM " + TABLE_QUESTION + " WHERE " + KEY_ID + " = \"" + questionId + "\"";
//
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		Cursor cursor = db.rawQuery(query, null);
//
//		in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList questionaire = mContestQuestionResult.new QuestionList();
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
//	public in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList findTestByQuestionID(int questionId) {
//		String query = "Select * FROM " + TABLE_TEST + " WHERE " + KEY_TEST_ID + " = \"" + questionId + "\"";
//
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		Cursor cursor = db.rawQuery(query, null);
//
//		in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList questionaire = mLearnTestResult.new QuestionList();
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

	public boolean  isRetailMasterEmpty() {

		boolean flag;
		String quString = "select exists(select * from " + TABLE_RETAIL  + ");";

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(quString, null);
		cursor.moveToFirst();
		int count= cursor.getInt(0);
		if (count ==1) {
			flag =  false;
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
				datum.setIProductModalId(cursor.getString(1));
				datum.setSProductName(cursor.getString(2));
				productFeatures = Util.getCustomGson().fromJson(cursor.getString(3), new TypeToken<ArrayList<ProductSearchResult.SProductFeature>>(){}.getType());
				datum.setDiscount(searchResult);
				datum.setSProductFeature(productFeatures);
				datum.setProductImage(cursor.getString(4));
				datum.setSProductPrice(cursor.getDouble(5));
				datum.setSProductStock(cursor.getInt(6));
				datum.setSProductWeight(cursor.getInt(7));
				if(cursor.getInt(8)==0)
					datum.setIsDiscount(false);
				else
					datum.setIsDiscount(true);
				datum.setGstPerc(cursor.getDouble(9));
				datum.setCgst(cursor.getDouble(10));
				datum.setSgst(cursor.getDouble(11));
				datum.setSalesPrice(cursor.getDouble(12));
				datum.setNrv(cursor.getDouble(13));
				datum.setGpl(cursor.getDouble(14));
				datum.setMrp(cursor.getDouble(15));
				datum.setBarCodeNumber(cursor.getString(16));
				searchResult = Util.getCustomGson().fromJson(cursor.getString(17), new TypeToken<ArrayList<ProductSearchResult.Discount>>(){}.getType());
				datum.setDiscount(searchResult);
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
				datum.setIProductModalId(cursor.getString(1));
				datum.setSProductName(cursor.getString(2));
				productFeatures = Util.getCustomGson().fromJson(cursor.getString(3), new TypeToken<ArrayList<ProductSearchResult.SProductFeature>>(){}.getType());
				datum.setDiscount(searchResult);
				datum.setSProductFeature(productFeatures);
				datum.setProductImage(cursor.getString(4));
				datum.setSProductPrice(cursor.getDouble(5));
				datum.setSProductStock(cursor.getInt(6));
				datum.setSProductWeight(cursor.getInt(7));
				if(cursor.getInt(8)==0)
					datum.setIsDiscount(false);
				else
					datum.setIsDiscount(true);
				datum.setGstPerc(cursor.getDouble(9));
				datum.setCgst(cursor.getDouble(10));
				datum.setSgst(cursor.getDouble(11));
				datum.setSalesPrice(cursor.getDouble(12));
				datum.setNrv(cursor.getDouble(13));
				datum.setGpl(cursor.getDouble(14));
				datum.setMrp(cursor.getDouble(15));
				datum.setBarCodeNumber(cursor.getString(16));
				searchResult = Util.getCustomGson().fromJson(cursor.getString(17), new TypeToken<ArrayList<ProductSearchResult.Discount>>(){}.getType());
				datum.setDiscount(searchResult);
				// Adding question to List
				questionList.add(datum);
			} while (cursor.moveToNext());
		}

		// return contact list
		return questionList;
	}
//
//	public ArrayList<in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList> getAllTestQuestionaire() {
//		ArrayList<in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList> contactList = new ArrayList<in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_TEST;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList mQuestionListData = mLearnTestResult.new QuestionList();
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
//	public ArrayList<in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList> getResultQuestionaire() {
//		ArrayList<in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList> contactList = new ArrayList<in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_QUESTION;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList mQuestionListData = mContestQuestionResult.new QuestionList();
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
//	public ArrayList<in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList> getTestResultQuestionaire() {
//		ArrayList<in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList> contactList = new ArrayList<in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList>();
//		// Select All Query
//		String selectQuery = "SELECT * FROM " + TABLE_TEST;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList mQuestionListData = mLearnTestResult.new QuestionList();
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
//	public int updateAnswer(in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList questionaire, int questionId) {
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
//	public int updateTestAnswer(in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList questionaire, int questionId) {
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
//	public void deleteQuestionaire(in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList questionaire) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_QUESTION, KEY_ID + " = ?", new String[] { String.valueOf(questionaire.getQuestionId()) });
//		db.close();
//	}
//
//	public void deleteTestQuestionaire(in.android.sharekhan.vision2020.datatype.LearnTestResult.QuestionList questionaire) {
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
//	public void deleteTable(String TABLE_NAME) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.execSQL("delete from " + TABLE_NAME);
//	}
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

	//Insert Customer Data
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

	//Delete Customer Data
	public void deleteCustomerData(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(CustomerModel.TABLE_NAME, CustomerEnum.ColoumnCustomerID.toString() + " = ?",
				new String[]{String.valueOf(id)});
		db.close();
	}

	//Update Customer Data
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
	//Get Customer Record
	public long getRecordsCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		long count = DatabaseUtils.queryNumEntries(db, CustomerModel.TABLE_NAME);
		db.close();
		return count;
	}
	//Get customer details
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
	//Getting Offline customer
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
	//Get All records from customer Database
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
