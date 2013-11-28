package gergonzalezg.tienda.data;

import gergonzalezg.tienda.app.App;
import gergonzalezg.tienda.clases.Comment;
import gergonzalezg.tienda.clases.Location;
import gergonzalezg.tienda.clases.Photo;
import gergonzalezg.tienda.clases.Shop;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	
	 private DBHelper dbHelper;
	 private static final String DATABASE_NAME = "places.db";
	 private static final int DATABASE_VERSION = 7;
	 private Context context;
	  
     public DBAdapter (Context context){
         this.context=context;
    	 dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
     }
     
     public void insertPlace(Shop p){    	 
    	 ContentValues values = buildContentValuesFromShop(p);
    	 
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         try{
        	 db.insertWithOnConflict(DBHelper.SHOPS_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);                 
        	 
        	 List<Comment> comentarios = p.getComentarios();
        	 for(int i = 0; i < comentarios.size(); i++){
        		 comentarios.get(i).setId(getTotalCommentsStoreinDatabase());
        		 ContentValues valuesComment  = buildContentValuesFromComment(comentarios.get(i), p.getId());
        		 db.insertWithOnConflict(DBHelper.COMMENTS_TABLE, null, valuesComment, SQLiteDatabase.CONFLICT_IGNORE);
        	 }
        	 
        	 
         } finally {
        	 db.close();
         }
     }
     
     public void updateShop(Shop p){    	 
    	 ContentValues values = buildContentValuesFromShop(p);
    	 
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         try{
        	 db.updateWithOnConflict(DBHelper.SHOPS_TABLE, values, DBHelper.KEY_SHOP_ID + "=?",new String[]{p.getId()+""}, SQLiteDatabase.CONFLICT_IGNORE);                        	        	 
        	 
         } finally {
        	 db.close();
         }
     }
     
     public int getTotalCommentsStoreinDatabase() {
    	
    	 SQLiteDatabase db = dbHelper.getReadableDatabase();
    	 Cursor cursor = db.query(DBHelper.COMMENTS_TABLE, null, null, null, null, null, null);
    	 int total = cursor.getCount();
    	 cursor.close();
    	 return total;
	}

	public int getTotalPlacesinDatabase() {
    	 SQLiteDatabase db = dbHelper.getReadableDatabase();
    	 Cursor cursor = db.query(DBHelper.SHOPS_TABLE, null, null, null, null, null, null);
    	 int total = cursor.getCount();
    	 cursor.close();
    	 return total;
     }
     
     public List<Shop> getShops() {

    	 List<Shop> tiendas = ((App)context.getApplicationContext()).getTiendas();

    	 SQLiteDatabase db = dbHelper.getReadableDatabase();
    	 Cursor cursor = db.query(DBHelper.SHOPS_TABLE, null, null, null, null, null, null);
    	 while (cursor.moveToNext()){

    		 Shop tienda = new Shop();

    		 tienda.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SHOP_ID)));
    		 tienda.setNombre(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SHOP_NAME)));
    		 tienda.setActividad(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SHOP_ACTIVITY)));
    		 tienda.setTelefono(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SHOP_PHONE)));
    		 tienda.setDireccion(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SHOP_ADDRESS)));
    		 tienda.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SHOP_EMAIL)));
    		 tienda.setWeb(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SHOP_URL)));
    		 tienda.setHorario(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SHOP_HOURS)));
    		 tienda.setFavorites(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SHOP_FAVORITES)));

    		 Location localizacion=new Location();

    		 localizacion.setLatitude(cursor.getFloat(cursor.getColumnIndex(DBHelper.KEY_SHOP_LATITUDE)));
    		 localizacion.setLongitude(cursor.getFloat(cursor.getColumnIndex(DBHelper.KEY_SHOP_LONGITUDE)));

    		 ArrayList<Comment>comentarios=new ArrayList<Comment>();
    		 
    		 comentarios=(ArrayList<Comment>) getComments(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SHOP_ID)));

    		 tienda.setComentarios(comentarios);
    		 tienda.setLocalizacion(localizacion);

    		 tiendas.add(tienda);
    	 }
    	 cursor.close();

    	 return tiendas;
     }
     
     public ContentValues buildContentValuesFromShop (Shop p) {
    	 ContentValues values = new ContentValues();
    	 values.put(DBHelper.KEY_SHOP_ID, p.getId());
    	 values.put(DBHelper.KEY_SHOP_NAME, p.getNombre());
    	 values.put(DBHelper.KEY_SHOP_ACTIVITY, p.getActividad());
    	 values.put(DBHelper.KEY_SHOP_PHONE, p.getTelefono());
    	 values.put(DBHelper.KEY_SHOP_ADDRESS, p.getDireccion());
    	 values.put(DBHelper.KEY_SHOP_HOURS, p.getHorario());
    	 values.put(DBHelper.KEY_SHOP_URL, p.getWeb());
    	 values.put(DBHelper.KEY_SHOP_EMAIL, p.getEmail());
    	 values.put(DBHelper.KEY_SHOP_FAVORITES, p.getFavorites());
    	 values.put(DBHelper.KEY_SHOP_LATITUDE, p.getLocalizacion().getLatitude());
    	 values.put(DBHelper.KEY_SHOP_LONGITUDE, p.getLocalizacion().getLongitude());
    	 return values;
     }

     public void insertPhoto(Photo p){    	 
    	 ContentValues values = buildContentValuesFromPhoto(p);
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         try{
        	 db.insertWithOnConflict(DBHelper.PHOTOS_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);                 
         } finally {
        	 db.close();
         }
     }
     
     public ContentValues buildContentValuesFromPhoto (Photo p) {
    	 ContentValues values = new ContentValues();
    	 values.put(DBHelper.KEY_PHOTO_ID, p.getId());
    	 values.put(DBHelper.KEY_PHOTO_URI, p.getURI());
    	 values.put(DBHelper.KEY_PHOTO_URL, p.getURL());
    	 values.put(DBHelper.KEY_PHOTO_DESCRIPTION, p.getDescripcion());
    	 values.put(DBHelper.KEY_PHOTO_FAVORITES,  p.getFavoritos());
    	 values.put(DBHelper.KEY_PHOTO_USER, p.getUsuario());
    	 
    	 return values;
     }
     
     public int getTotalPhotosinDatabase() {
    	 SQLiteDatabase db = dbHelper.getReadableDatabase();
    	 Cursor cursor = db.query(DBHelper.PHOTOS_TABLE, null, null, null, null, null, null);
    	 int total = cursor.getCount();
    	 cursor.close();
    	 return total;
     }

     public List<Photo> getPhotos() {

    	 List<Photo> photos = ((App)context.getApplicationContext()).getImagesArray();

    	 SQLiteDatabase db = dbHelper.getReadableDatabase();
    	 Cursor cursor = db.query(DBHelper.PHOTOS_TABLE, null, null, null, null, null, null);
    	 while (cursor.moveToNext()){

    		 Photo photo = new Photo();

    		 photo.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_PHOTO_ID)));
    		 photo.setURI(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHOTO_URI)));
    		 photo.setURL(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHOTO_URL)));
    		 photo.setDescripcion(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHOTO_DESCRIPTION)));
    		 photo.setFavoritos(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_PHOTO_FAVORITES)));
    		 photo.setUsuario(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHOTO_USER)));
    		 
    		

    		 photos.add(photo);
    	 }
    	 cursor.close();

    	 return photos;
     }
     
     public void insertComment(Comment p,int idShop){    
    	 
    	 ContentValues values = buildContentValuesFromComment(p,idShop);
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         try{
        	 db.insertWithOnConflict(DBHelper.COMMENTS_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);                 
         } finally {
        	 db.close();
         }
     }
     
     public void deleteComment(Comment p){    
    	
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         try{
        	 db.delete(DBHelper.COMMENTS_TABLE, DBHelper.KEY_COMMENT_ID + "=?", new String[]{p.getId()+""});                 
         } finally {
        	 db.close();
         }
     }
     
     public ContentValues buildContentValuesFromComment (Comment p, int idShop) {
    	 ContentValues values = new ContentValues();
    	 values.put(DBHelper.KEY_COMMENT_ID, p.getId());
    	 values.put(DBHelper.KEY_COMMENT_ID_STORE, idShop);
    	 values.put(DBHelper.KEY_COMMENT_COMMENT, p.getComentario());
    	 
    	 
    	 return values;
     }

     public int getTotalCommentsStoreinDatabase(integer idStore) {
    	 
    	 String whereClause = "idStore =?";
    	 String[] whereArgs = new String[]{idStore.toString()};
    	 SQLiteDatabase db = dbHelper.getReadableDatabase();
    	 Cursor cursor = db.query(DBHelper.COMMENTS_TABLE, null, whereClause, whereArgs, null, null, null);
    	 int total = cursor.getCount();
    	 cursor.close();
    	 return total;
     }

     public List<Comment> getComments(int idStore) {

    	 List<Comment> comments = new ArrayList<Comment>();
    	 String whereClause = "idStore =?";
    	 String[] whereArgs = new String[]{idStore + ""};
    	 
    	 SQLiteDatabase db = dbHelper.getReadableDatabase();
    	 Cursor cursor = db.query(DBHelper.COMMENTS_TABLE, null, whereClause, whereArgs, null, null, null);
    	 while (cursor.moveToNext()){

    		 Comment comment = new Comment();

    		 comment.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COMMENT_ID)));
    		 comment.setIdShop(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COMMENT_ID_STORE)));
    		 comment.setComentario(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COMMENT_COMMENT)));
    		     		 
    		 comments.add(comment);
    	 }
    	 cursor.close();

    	 return comments;
     }

}
