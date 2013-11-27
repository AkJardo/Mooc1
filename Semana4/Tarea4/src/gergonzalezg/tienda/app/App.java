package gergonzalezg.tienda.app;

import gergonzalezg.tienda.clases.Shop;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class App extends Application {

	private List<Shop> tiendas = new ArrayList<Shop>() ;

	@Override
	public void onCreate() {
		super.onCreate();
		setTiendas(new ArrayList<Shop>()) ;
	}

	public List<Shop> getTiendas() {
		return tiendas;
	}

	public void setTiendas(List<Shop> tiendas) {
		this.tiendas = tiendas;
	}
	
	
}
