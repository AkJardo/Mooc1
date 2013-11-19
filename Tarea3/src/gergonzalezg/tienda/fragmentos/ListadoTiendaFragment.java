package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.actividades.DetailActivity;
import gergonzalezg.tienda.clases.Shop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
	private List<Shop> tiendas = new ArrayList<Shop>() ;
	
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
						
						Shop tiendaSeleccionada=(Shop) adapter.getItemAtPosition(position);		
						
						Intent intent=new Intent(getActivity(), DetailActivity.class);
						
						intent.putExtra("tienda",tiendaSeleccionada);
						startActivity(intent);
						
						
					}
	
					
				});
		return view;
	}

	public class AdaptadorTienda extends ArrayAdapter<Shop>{

		Context contexto;
		List<Shop> tiendas;
		
		public AdaptadorTienda(Context context, int resource,
				List<Shop> objects) {
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
		
		/*tiendas.add(new Shop(1, "Mi pequeño Androide", "Juguetes", "Avenida Principal,4", "918764523", "https://www.google.com", "jugar@juego.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(2, "El saber no ocupa caché", "Libros", "Avenida Principal,12", "918764523", "https://www.google.com", "libro@libreria.es.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(3, "Android Technology", "Electrónica", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(4, "Mooc Tec", "Electrónica", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(5, "A-market", "Comida", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "comida@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(6, "La biblioteca de Android", "Libros", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "libros@libro.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(7, "Cool Clothes", "Ropa y complementos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "ropaa@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(8, "Fashion Android", "Ropa y complementos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "chip@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(9, "Android Games", "Videojuegos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "juego@correo.com", "09:00-14:30   16:00-21:00"));
		tiendas.add(new Shop(10,"Play Droid", "Videojuegos", "Calle de la concordia, 12 (Madrid, España)", "918764523", "https://www.google.com", "juego@correo.com", "09:00-14:30   16:00-21:00"));*/
		
		
		//Recuperamos los datos con JSON, como es mi api uso la libreria GSON
		
		
		JsonElement json = new JsonParser().parse(loadJSONFromAsset());
		 
		JsonArray array= json.getAsJsonArray();
		 
		Iterator iterator = array.iterator();
		 
		
		 
		while(iterator.hasNext()){
		    JsonElement json2 = (JsonElement)iterator.next();
		    Gson gson = new Gson();
		    Shop tienda = gson.fromJson(json2, Shop.class);
		    //can set some values in contact, if required 
		    tiendas.add(tienda);
		}
			
		
		
	}

	public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("document.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
