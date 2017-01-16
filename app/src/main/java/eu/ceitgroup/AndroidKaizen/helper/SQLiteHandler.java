/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package eu.ceitgroup.AndroidKaizen.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_USER = "user";
	private static final String TABLE_IDEA = "idea";

	// Login Table Columns names
	//private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_SURNAME = "surname";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_COMPANY = "company";


	private static final String KEY_USER_TYPE = "user_type";

	private static final String KEY_SHORT_NAME = "short_name";
	private static final String KEY_ACTUAL_STATE = "actual_state";
	private static final String KEY_IMPROVED_STATE = "improved_state";
	private static final String KEY_ADVANTAGES = "advantages";
	private static final String KEY_COSTS = "costs";
	private static final String KEY_FILE_BEFORE = "file_before";
	private static final String KEY_FILE_AFTER = "file_after";
	private static final String KEY_CREATOR = "creator";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_NAME + " TEXT,"
				+ KEY_SURNAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_COMPANY + " TEXT," + KEY_UID + " TEXT,"
				+ KEY_USER_TYPE + " INTEGER" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		String CREATE_IDEA_TABLE = "CREATE TABLE " + TABLE_IDEA + "("
				+ KEY_SHORT_NAME + " TEXT,"
				+ KEY_ACTUAL_STATE + " TEXT,"
				+ KEY_IMPROVED_STATE + " TEXT,"
				+ KEY_ADVANTAGES + " TEXT,"
				+ KEY_COSTS + " TEXT,"
				+ KEY_CREATOR + " TEXT,"
				+ KEY_FILE_BEFORE + " TEXT, "
				+ KEY_FILE_AFTER + " TEXT" + ")";
		db.execSQL(CREATE_IDEA_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String email, String uid, String surname, String company, String type) {
		SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_NAME, name); // Name
	values.put(KEY_EMAIL, email); // Email
	values.put(KEY_UID, uid); // Email
	//values.put(KEY_CREATED_AT, created_at); // Created At
	values.put(KEY_SURNAME, surname);
	values.put(KEY_COMPANY, company);
	values.put(KEY_USER_TYPE, type);


	//Log.d("");
	// Inserting Row
	long id = db.insert(TABLE_USER, null, values);
	db.close(); // Closing database connection

	Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Storing idea details in database
	 * */
	public void addIdea(String name, String actual_state, String improved_state, String advantages, String costs,String file_b, String file_a, String creator) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SHORT_NAME, name); // Name of idea
		values.put(KEY_ACTUAL_STATE, actual_state);
		values.put(KEY_IMPROVED_STATE, improved_state);
		values.put(KEY_ADVANTAGES, advantages);
		values.put(KEY_COSTS, costs);
		values.put(KEY_CREATOR, creator);
		values.put(KEY_FILE_BEFORE, file_b);
		values.put(KEY_FILE_AFTER, file_a);


		// Inserting Row
		long id = db.insert(TABLE_IDEA, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New idea inserted into sqlite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(0));
			user.put("surname", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(4));
			user.put("company", cursor.getString(3));
			//user.put("phone", cursor.getString(5));
			//user.put("age", cursor.getString(6));
//			user.put("user_type", cursor.getString(7));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getIdeaDetails() {
		HashMap<String, String> idea = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_IDEA;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			idea.put("short_name", cursor.getString(1));
			idea.put("actual_state", cursor.getString(2));
			idea.put("improved_state", cursor.getString(3));
			idea.put("advantages", cursor.getString(4));
			idea.put("costs", cursor.getString(5));
			idea.put("creator", cursor.getString(6));
			idea.put("time", cursor.getString(7));
			idea.put("location", cursor.getString(8));
		}
		cursor.close();
		db.close();
		Log.d("AAAAAAAAAAAAAAAA", "SELECT FROM IDEA TABLE: " + idea.get("short_name"));
		Log.d("AAAAAAAAAAAAAAAA", "SELECT FROM IDEA TABLE: " + idea.get("actual_state"));
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + idea.toString());

		return idea;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}
/*
	public List<IdeaItem> getAllItems() {
		List<IdeaItem> itemsList = new ArrayList<IdeaItem>();

		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT short_name, actual_state, improved_state  FROM " + TABLE_IDEA;

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				IdeaItem item = new IdeaItem();
				//item.set_id(Integer.parseInt(cursor.getString(0)));
				item.setTask(cursor.getString(0));
				item.setCath(cursor.getString(1));
				item.setDate(cursor.getString(2));
				//item.setPrio(cursor.getString(4));
				//item.setDesc(cursor.getString(5));
				itemsList.add(item);
			} while (cursor.moveToNext());
		}
		return itemsList;
	}*/

}
