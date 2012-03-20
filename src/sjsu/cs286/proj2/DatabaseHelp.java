/**
 * 	Title: DatabaseHelp
 *  Description: This Class aids in read,write and delete operations in the table
 *  Date: 03/12/2012
 *  COPYRIGHT (C) 2012
 *  @author John Linford,Shailesh Benake
 *  @version 1.0
 */

package sjsu.cs286.proj2;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHelp {
	/**
	 * Constructor to create Db
	 *
	 * @param context
	 */
	public DatabaseHelp(Context context) {
		dbHelper = new MySql(context);
	}

	/**
	 * This method will open the table or initiate the table creation process for the first time
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	
	public void open1() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	/**
	 * This method will close the table 
	 */
	public void close() {
		dbHelper.close();

	}

	/**
	 * This method will write the contents into the table
	 * @param newWord
	 * 				The word entered by the user
	 * @param newDescp
	 * 				The Description entered by the user for the word
	 */
	public void writeDb(String newWord, String newDescp) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_WORD, newWord);
		cv.put(KEY_DESC, newDescp);
		dbHelper.getWritableDatabase().insert(Table_Name, KEY_WORD, cv);
		dbHelper.close();

	}
	
	public void updateDb(int index){
		ContentValues cv = new ContentValues();
		cv.put(KEY_ID, index);
		dbHelper.getWritableDatabase().execSQL("Update "+Table_Name1+" SET "+KEY_ID+" = "+index+" ;");
		//dbHelper.getWritableDatabase().update(Table_Name1, KEY_ID, cv);
	}
	
	public int readPersistence(){
		int state = 0;
		Cursor c = dbHelper.getReadableDatabase().rawQuery("Select "+KEY_ID+" FROM "+Table_Name1, null);
		for (int i = 0; i < c.getCount(); i++) {
			c.moveToNext();
			state = c.getInt(0);
		}
		return state;
		
	}

	/**
	 * This method fetch the contents of the table in portrait mode
	 * 
	 */
	public ArrayList<String> readDb() {

		ArrayList<String> flash = new ArrayList<String>();

		//cursor fetches the rows of the table returned by the query
		Cursor c = dbHelper.getReadableDatabase().rawQuery(
				"SELECT word,descp FROM List1 ORDER BY word", null);
		String tempString;
		for (int i = 0; i < c.getCount(); i++) {
			c.moveToNext();
			tempString = (c.getString(0).concat(" : ").concat(c.getString(1)));
			if ((tempString.length() > 28)) {
				flash.add(tempString.substring(0, 28));
				Log.d(tempString.substring(0, 28), "truncated word printed");

			} else
				flash.add(tempString);
			Log.d(tempString, "original word");

		}
		c.close();
		db.close();
		return flash;
	}

	/**
	 * This method fetch the contents of the table in landscape mode
	 * 
	 */
	public ArrayList<FlashCards> lreadDb() {
		ArrayList<FlashCards> lflash = new ArrayList<FlashCards>();
		//cursor fetches the rows of the table returned by the query
		Cursor c = dbHelper.getReadableDatabase().rawQuery(
				"SELECT word,descp FROM List1 ORDER BY word", null);

		for (int i = 0; i < c.getCount(); i++) {
			c.moveToNext();
			lflash.add(new FlashCards(c.getString(0), c.getString(1)));
			Log.d("", "Word displayed");

		}
		c.close();
		db.close();
		return lflash;
	}

	/**
	 * This method deletes the contents of the table.
	 * 
	 */
	public void deleteFlash() {
		Log.i(Table_Name, "inside method");
		try {
			dbHelper.getWritableDatabase().execSQL(
					"DELETE FROM " + Table_Name + ";");
			Log.i(Table_Name, "Table deleted Successfully");
			db.close();
		} catch (Exception e) {
			Log.e(Table_Name, "Cannot delete", e);

		}
	}

	public FlashCards tmpFlashCard;                         // Flash card object to hold the word and its description
	DatabaseHelp Write;										// Write the contents into the DB
	public static final String KEY_ID = "id";				// Local variable for ID
	DatabaseHelp Read;										// To Read the contents from the DB
	private SQLiteDatabase db;								// To create,update,delete and read the contents from the DB
	private MySql dbHelper;	
	protected static final String Table_Name1 = "Persist";  // Local variable for Persistence table
	public static final String KEY_WORD = "word";			// Local variable for primary Key "word" of the table List1
	public static final String KEY_DESC = "descp";			// Local variable for description
	protected static final String Table_Name = "List1";		// Local variable for table name
	String my_list[];
	static Context context;
}