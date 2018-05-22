package quay.com.ipos.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "ContestManager";

	// Contacts table name
	public static final String TABLE_QUESTION = "ContestTable";

	// Contacts table name
	public static final String TABLE_OPINION = "OpnionTable";

	// Test Table name
	public static final String TABLE_TEST = "TestTable";

	// Contacts Table Columns names
	private static final String KEY_ID = "questionId";
	// private static final String KEY_QUESTIONID = "questionId";
	private static final String KEY_OPTION_ANSWER = "answer";
	private static final String KEY_QUESTION = "question";

	// OpnionTable Table Columns names
	private static final String KEY_OPINION_ID = "OpinionId";
	// private static final String KEY_QUESTIONID = "questionId";
	private static final String KEY_OPINION_ANSWER = "OpinionAnswer";
	private static final String KEY_OPINION_QUESTION = "OpinionQuestion";

	// OpnionTable Table Columns names
	private static final String KEY_TEST_ID = "TestId";
	// private static final String KEY_QUESTIONID = "questionId";
	private static final String KEY_TEST_ANSWER = "TestAnswer";
	private static final String KEY_TEST_QUESTION = "TestQuestion";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

//	ContestQuestionResult mContestQuestionResult = new ContestQuestionResult();
//	OpinionPollListResult mOpinionPollListResult = new OpinionPollListResult();
//	LearnTestResult mLearnTestResult = new LearnTestResult();

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUESTION + "(" + KEY_ID + " INTEGER," + KEY_QUESTION
				+ " TEXT," + KEY_OPTION_ANSWER + " INTEGER" + ")";
		db.execSQL(CREATE_QUESTION_TABLE);

		String CREATE_OPINION_TABLE = "CREATE TABLE " + TABLE_OPINION + "(" + KEY_OPINION_ID + " INTEGER,"
				+ KEY_OPINION_QUESTION + " TEXT," + KEY_OPINION_ANSWER + " INTEGER" + ")";

		db.execSQL(CREATE_OPINION_TABLE);

		String CREATE_TEST_TABLE = "CREATE TABLE " + TABLE_TEST + "(" + KEY_TEST_ID + " INTEGER," + KEY_TEST_QUESTION
				+ " TEXT," + KEY_TEST_ANSWER + " INTEGER" + ")";

		db.execSQL(CREATE_TEST_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);

		// Create tables again
		// onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPINION);

		// Create tables again
		// onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

//	// Adding new contact
//	public void addQuestionaire(ContestQuestionResult.QuestionList mQuestionListData) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_ID, mQuestionListData.getQuestionId());
//		values.put(KEY_QUESTION, mQuestionListData.getQuestion()); //
//		// Questionnaire
//		// Question
//		values.put(KEY_OPTION_ANSWER, mQuestionListData.getAnswer()); //
//		// Questionnaire
//		// Answer
//		// Inserting Row
//		db.insert(TABLE_QUESTION, null, values);
//		db.close(); // Closing database connection
//	}
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
//	// Getting All Questionaire
//	public ArrayList<in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList> getAllQuestionaIdByQuestionId(String questionId) {
//		ArrayList<in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList> questionList = new ArrayList<in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList>();
//		// Select All Query
//		String selectQuery = "Select * FROM " + TABLE_QUESTION + " WHERE " + KEY_ID + " = \"" + questionId + "\"";
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList mQuestionListData = mContestQuestionResult.new QuestionList();
//				mQuestionListData.setQuestionId(cursor.getString(0));
//				mQuestionListData.setQuestion(cursor.getString(1));
//				mQuestionListData.setAnswer(cursor.getInt(2));
//				// Adding question to List
//				questionList.add(mQuestionListData);
//			} while (cursor.moveToNext());
//		}
//
//		// return question List
//		return questionList;
//	}
//
//	// Getting All Questionaire
//	public ArrayList<in.android.sharekhan.vision2020.datatype.ContestQuestionResult.QuestionList> getAllQuestionaire() {
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

}
