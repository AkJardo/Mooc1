package gergonzalezg.tienda.app;

import gergonzalezg.tienda.clases.Photo;
import gergonzalezg.tienda.clases.Shop;
import gergonzalezg.tienda.data.DBAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class App extends Application {

	private List<Shop> tiendas = new ArrayList<Shop>() ;
	private ArrayList<Photo> imagesArray;
	private DBAdapter db;
	
	@Override
	public void onCreate() {
		super.onCreate();
		db = new DBAdapter(getApplicationContext());	
		setTiendas(new ArrayList<Shop>()) ;
		setImagesArray(new ArrayList<Photo>()) ;
	}

	public List<Shop> getTiendas() {
		return this.tiendas;
	}

	public void setTiendas(List<Shop> tiendas) {
		this.tiendas = tiendas;
	}
	
	public DBAdapter getDB() {
		return db;
	}

	public ArrayList<Photo> getImagesArray() {
		return imagesArray;
	}

	public void setImagesArray(ArrayList<Photo> imagesArray) {
		this.imagesArray = imagesArray;
	}	
}
