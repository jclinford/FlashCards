package sjsu.cs286.proj2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelp extends SQLiteOpenHelper {

	public DatabaseHelp(Context context, String dbName, int version) 
	{
		super(context, DATABASE_NAME, null, db_version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS L" +
				"ist1 (word VARCHAR(20) PRIMARY KEY,"
				+ "descp VARCHAR(150) TEXT NOT NULL;");
		db.execSQL("INSERT INTO List1 VALUES ('android', 'a robot resembling a human being');");

	}

	public void writeDb(String word, String descp) 
	{

		ContentValues cv = new ContentValues();
		cv.put(KEY_WORD, word);
		cv.put(KEY_DESC, descp);
		dbHelp.getWritableDatabase().insert("List1", KEY_WORD, cv);

	}

	public String readDb()
	{
		
		Cursor c = dbHelp.getReadableDatabase().rawQuery(
				"SELECT word,descp FROM List1 ORDER BY word", null);
		for (int i = 0; i < c.getCount(); i++) 
		{
			c.moveToNext();
			//my_list[i] = c.getString(0) +" : "+ c.getString(1);
			tmpFlashCard = new FlashCards(c.getString(0), c.getString(1));
			// TODO add to list
		}

		// db.close();
		return null;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS List1 ");
		this.onCreate(db);
	}

	public void close() {
		dbHelp.close();
	}

	private DatabaseHelp dbHelp;

	public FlashCards tmpFlashCard;
	private static final int db_version = 3;
	public static String KEY_WORD = "word";
	public static String KEY_DESC = "descp";
	private static final String DATABASE_NAME = "dbFlash_cards";
	protected static final String Table_Name = "List1";
	String my_list[];
}