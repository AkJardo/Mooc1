package es.gergonzalezg.tarea1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import es.gergonzalezg.tarea1.Tienda;

public class MainActivity extends ListActivity implements OnItemClickListener {

	private TextView textBanner;
	private List<Tienda> tiendas = new ArrayList<Tienda>() ;
	private List<HashMap<String, String>> datosTiendas= new ArrayList<HashMap<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		//Damos estilo a nuestro banner
		textBanner = (TextView) findViewById(R.id.txtBanner);
		textBanner.setBackgroundResource(R.color.bannerfondo);
		textBanner.setTextColor(getResources().getColor(R.color.banner));
		
		//cargamos las tiendas
		cargarTiendas();
	
				
		AdaptadorTienda adaptador = new AdaptadorTienda(this, R.layout.activity_principal, tiendas);
		
		setListAdapter(adaptador);
		
		ListView lista= getListView();
	
		lista.setOnItemClickListener(this);
				
	}		
		
	class AdaptadorTienda extends ArrayAdapter<Tienda>{

		Context contexto;
		List<Tienda> tiendas;
		
		public AdaptadorTienda(Context context, int resource,
				List<Tienda> objects) {
			super(context, resource, objects);
			
			this.contexto=context;
			this.tiendas=objects;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
			
			View lista=inflater.inflate(R.layout.shop_list_item, null);
			
			TextView textoNombre = (TextView) lista.findViewById(R.id.txtShopName);
			TextView textoDetalle= (TextView) lista.findViewById(R.id.txtShopDetail);
			
			textoNombre.setText(tiendas.get(position).getNombre().toString());
			textoDetalle.setText(tiendas.get(position).getActividad().toString());
			
			return lista;
		}
		
	}

	private void cargarTiendas() {
		

		
		tiendas.add(new Tienda(1, "Mi pequeño Androide", "Juguetes", "Avenida Principal,4", "918764523", "https://www.google.com", "jugar@juego.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(2, "El saber no ocupa caché", "Libros", "Avenida Principal,12", "918764523", "https://www.google.com", "libro@libreria.es.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(3, "Android Technology", "Electrónica", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(4, "Mooc Tec", "Electrónica", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(5, "A-market", "Comida", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "comida@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(6, "La biblioteca de Android", "Libros", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "libros@libro.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(7, "Cool Clothes", "Ropa y complementos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "ropaa@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(8, "Fashion Android", "Ropa y complementos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(9, "Android Games", "Videojuegos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "juego@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(10,"Play Droid", "Videojuegos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "juego@correo.com", "09:00-14:30   16:00-21:00"));
		;
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> adaptador, View lista, int posicion, long arg) {
		
		Tienda tiendaSeleccionada=(Tienda) adaptador.getItemAtPosition(posicion);		
		Intent intent=new Intent(this, DetailActivity.class);
		
		intent.putExtra("tienda",tiendaSeleccionada);
		startActivity(intent);
		
		//Toast.makeText(this, listItem.get("ID").toString(), Toast.LENGTH_SHORT).show();
	}



}
