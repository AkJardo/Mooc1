package gergonzalezg.tienda.app;

import gergonzalezg.tienda.clases.Shop;

import java.util.ArrayList;
import java.util.List;

import gergonzalezg.tienda.data.DBAdapter;

import android.app.Application;

public class App extends Application {

	private List<Shop> tiendas = new ArrayList<Shop>() ;
	private DBAdapter db;
	
	@Override
	public void onCreate() {
		super.onCreate();
		db = new DBAdapter(getApplicationContext());	
		setTiendas(new ArrayList<Shop>()) ;
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
}
