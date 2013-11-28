package gergonzalezg.tienda.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public final static String KEY_SHOP_ID = "id";
	public final static String KEY_SHOP_NAME = "name";
	public final static String KEY_SHOP_ACTIVITY = "activity";
	public final static String KEY_SHOP_ADDRESS = "address";
	public final static String KEY_SHOP_PHONE = "phone";
	public final static String KEY_SHOP_HOURS = "hoursOFOperation";
	public final static String KEY_SHOP_URL = "URL";
	public final static String KEY_SHOP_EMAIL = "email";
	public final static String KEY_SHOP_FAVORITES = "favorites";
	public final static String KEY_SHOP_LATITUDE = "latitude";
	public final static String KEY_SHOP_LONGITUDE = "longitude";
	
	
	public final static String KEY_PHOTO_ID = "id";
	public final static String KEY_PHOTO_URL = "URL";
	public final static String KEY_PHOTO_URI = "URI";
	public final static String KEY_PHOTO_DESCRIPTION = "description";
	public final static String KEY_PHOTO_FAVORITES = "favorites";
	public final static String KEY_PHOTO_USER = "user";

	
	public final static String KEY_COMMENT_ID = "id";
	public final static String KEY_COMMENT_ID_STORE = "idStore";
	public final static String KEY_COMMENT_COMMENT = "comment";
	
	
	
	public final static String SHOPS_TABLE = "shops";
	public final static String PHOTOS_TABLE = "photos";
	public final static String COMMENTS_TABLE = "comments";
	
	private final static String DATABASE_CREATE_SHOPS = "CREATE TABLE " + SHOPS_TABLE + 
												  "(" + KEY_SHOP_ID + " integer primary key autoincrement, " +
												  KEY_SHOP_NAME + " text, " + KEY_SHOP_ACTIVITY + " text," +
												  KEY_SHOP_ADDRESS + " text, " + 
												  KEY_SHOP_PHONE + " text, " +
												  KEY_SHOP_HOURS + " text, " +
												  KEY_SHOP_URL + " text, " +
												  KEY_SHOP_EMAIL + " text, " +
												  KEY_SHOP_FAVORITES + " integer, " +
												  KEY_SHOP_LATITUDE + " real, " +
												  KEY_SHOP_LONGITUDE + " real)";
	
	private final static String DATABASE_CREATE_PHOTOS = "CREATE TABLE " + PHOTOS_TABLE + 
			  "(" + KEY_PHOTO_ID + " integer primary key autoincrement, " +
			  KEY_SHOP_URL + " text, " + KEY_PHOTO_URI + " text," +
			  KEY_PHOTO_DESCRIPTION + " text, " + 
			  KEY_PHOTO_FAVORITES + " integer, " +
			  KEY_PHOTO_USER + " text)";
	
	private final static String DATABASE_CREATE_COMMENTS = "CREATE TABLE " + COMMENTS_TABLE + 
			  "(" + KEY_COMMENT_ID + " integer primary key autoincrement, " +
			  KEY_COMMENT_ID_STORE + " integer, " + 	 
			  KEY_COMMENT_COMMENT + " text)";
	
	

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_SHOPS);
		db.execSQL(DATABASE_CREATE_PHOTOS);
		db.execSQL(DATABASE_CREATE_COMMENTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SHOPS_TABLE);   
		db.execSQL("DROP TABLE IF EXISTS " + PHOTOS_TABLE); 
		db.execSQL("DROP TABLE IF EXISTS " + COMMENTS_TABLE); 
        onCreate(db);
	}

}
