package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.actividades.DetailActivity;
import gergonzalezg.tienda.clases.Comment;
import gergonzalezg.tienda.clases.Location;
import gergonzalezg.tienda.clases.Shop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ListadoTiendaFragment extends Fragment  {

	private final static int FROM_FILE_JSON=0;
	private final static int FROM_PARSE_JSON=1;
	
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
		
		GetData(FROM_PARSE_JSON);
		
		/*JsonElement json = new JsonParser().parse();
		
		
		JsonArray array= json.getAsJsonArray();
		Iterator<JsonElement> iterator = array.iterator();
		 
			 
		while(iterator.hasNext()){
		    JsonElement json2 = (JsonElement)iterator.next();
		    Gson gson = new Gson();
		    Shop tienda = gson.fromJson(json2, Shop.class);
		    tiendas.add(tienda);
		}
			*/
		
		
	}

	private String GetData(int fuenteDatos) {
		
		String resultado="";
		
		//Podemos elegir el método para cargarlo los datos de las tiendas
		//Como se propone de bonus en la primera parte de la tarea3
		switch (fuenteDatos){
		
		case FROM_FILE_JSON:
			resultado = loadJSONFromAsset();		
			break;
		case FROM_PARSE_JSON:
			resultado = loadJSONFromParseDotCom();
			break;
		}
		
		return resultado;
		
	}

	private String loadJSONFromParseDotCom() {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
		
		//Cogemos todos, si no podriamos usar where
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> lista, ParseException arg1) {
				
				Gson gson = new Gson();
				
				Iterator<ParseObject> iterator = lista.iterator();
				while(iterator.hasNext()){
					
					Shop tienda = new Shop();
					ParseObject parseTienda = (ParseObject)iterator.next();
					
					tienda.setNombre(parseTienda.getString("name"));
					tienda.setActividad(parseTienda.getString("activity"));
					tienda.setTelefono(parseTienda.getString("phone"));
					tienda.setDireccion(parseTienda.getString("address"));
					tienda.setEmail(parseTienda.getString("email"));
					tienda.setWeb(parseTienda.getString("url"));
					tienda.setHorario(parseTienda.getString("hoursOfOperation"));
					tienda.setFavorites(parseTienda.getInt("favorites"));
					
					List<Comment> comentarios=new ArrayList<Comment>();
					
					for(int i=0; i<parseTienda.getJSONArray("comments").length();i++){
						
						Comment comentario = new Comment();
						try {
							comentario.setComentario(parseTienda.getJSONArray("comments").getJSONObject(i).getString("comment"));
						} catch (JSONException e) {
							
							Log.e("TAG", e.getMessage());
						}
						comentarios.add(comentario);
					}
								
					tienda.setComentarios((Comment[])comentarios.toArray());
					
					Location localizacion=new Location();
					
					try {
						localizacion.setLatitude((float)parseTienda.getJSONObject("location").getDouble("lat"));
						localizacion.setLatitude((float)parseTienda.getJSONObject("location").getDouble("longitude"));
					} catch (JSONException e) {
						Log.e("TAG", e.getMessage());
					}
					
					tienda.setLocalizacion(localizacion);
					
				    tiendas.add(tienda);
				}
			}
		});
		return null;
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
