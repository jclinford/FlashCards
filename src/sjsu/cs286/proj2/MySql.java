/**
 * 	Title: MySql
 *  Description: This Class manages the creation and update of tables and Database
 *  Date: 03/12/2012
 *  COPYRIGHT (C) 2012
 *  @author John Linford,Shailesh Benake
 *  @version 1.0
 */
package sjsu.cs286.proj2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySql extends SQLiteOpenHelper {
	// Variable declaration for DB creation and upgrade

	private static final int db_version = 3;                      // Variable for setting the Database Version
	public static final String KEY_WORD = "word";                 // Local variable for primary Key "word" of the table List1
	public static final String KEY_DESC = "descp";                // Local variable for description
	public static final String KEY_ID = "id";                     // Local variable for persistence ID
	private static final String DATABASE_NAME = "dbFlash_cards";  // Local variable for DbName
	protected static final String Table_Name = "List1";           // Local variable for table name
	protected static final String Table_Name1 = "Persist";		  // Local variable for table name

	/** Called when the DB setup is to be done
	 * @param context 
	 * 			the context of the activity class  
	 */
	public MySql(Context context) {
		super(context, DATABASE_NAME, null, db_version);
	}
	
	/**
     * This method will create the table 
     * @param SQLiteDatabase
     * 			Assists to create table in the database
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DbCreate);
		db.execSQL(DbCreate1);
	}

	// Database creation sql statement
	private static final String DbCreate = " CREATE TABLE IF NOT EXISTS "
			+ Table_Name + " ( " + KEY_WORD + " TEXT PRIMARY KEY," + KEY_DESC
			+ " TEXT NOT NULL);";
	
	private static final String DbCreate1 = " CREATE TABLE IF NOT EXISTS "
			+ Table_Name1 + " ( " + KEY_ID + "TEXT PRIMARY KEY);";
	

	/**
     * This method allows you to update the database schema
     * @param SQLiteDatabase
     * 			Assists to update the database version
     * @param oldVersion
     * 			provides the details of the current version of the dB
     * @param newVersion
     * 			provides the details of the new version of the dB
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS List1 ");
		db.execSQL("DROP TABLE IF EXISTS Persist ");
		
		this.onCreate(db);
	}

}
