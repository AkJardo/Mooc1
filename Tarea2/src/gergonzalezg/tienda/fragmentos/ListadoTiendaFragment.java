package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.actividades.DetailActivity;
import gergonzalezg.tienda.clases.Tienda;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListadoTiendaFragment extends Fragment  {

	private ListView lista;
	private List<Tienda> tiendas = new ArrayList<Tienda>() ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
				View view = inflater.inflate(R.layout.fragment_listado,	null);
		
				lista=(ListView) view.findViewById(R.id.list);
								
				//cargamos las tiendas
				cargarTiendas();
			
						
				AdaptadorTienda adaptador = new AdaptadorTienda(getActivity(), R.layout.activity_principal, tiendas);
				
				lista.setAdapter(adaptador);
				
				
			
				lista.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View vista,
							int position, long arg3) {
						
						Tienda tiendaSeleccionada=(Tienda) adapter.getItemAtPosition(position);		
						
						Intent intent=new Intent(getActivity(), DetailActivity.class);
						
						intent.putExtra("tienda",tiendaSeleccionada);
						startActivity(intent);
						
						
					}
	
					
				});
		return view;
	}

	public class AdaptadorTienda extends ArrayAdapter<Tienda>{

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
		
		tiendas.add(new Tienda(1, "Mi peque�o Androide", "Juguetes", "Avenida Principal,4", "918764523", "https://www.google.com", "jugar@juego.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(2, "El saber no ocupa cach�", "Libros", "Avenida Principal,12", "918764523", "https://www.google.com", "libro@libreria.es.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(3, "Android Technology", "Electr�nica", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(4, "Mooc Tec", "Electr�nica", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(5, "A-market", "Comida", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "comida@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(6, "La biblioteca de Android", "Libros", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "libros@libro.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(7, "Cool Clothes", "Ropa y complementos", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "ropaa@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(8, "Fashion Android", "Ropa y complementos", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(9, "Android Games", "Videojuegos", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "juego@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Tienda(10,"Play Droid", "Videojuegos", "Calle de la concordia, 12 (Madrid, Espa�a)", "918764523", "https://www.google.com", "juego@correo.com", "09:00-14:30   16:00-21:00"));
	
	}

	
}
