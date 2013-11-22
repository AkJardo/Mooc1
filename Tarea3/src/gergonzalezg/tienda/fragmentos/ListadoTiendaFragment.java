package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea3.R;
import gergonzalezg.tienda.actividades.DetailActivity;
import gergonzalezg.tienda.clases.Comment;
import gergonzalezg.tienda.clases.Location;
import gergonzalezg.tienda.clases.Shop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		
		//Recuperamos los datos
		
		GetData(FROM_FILE_JSON);
		
	}

	private void GetData(int fuenteDatos) {
		
			
		//Podemos elegir el método para cargarlo los datos de las tiendas
		//Como se propone de bonus en la primera parte de la tarea3
		switch (fuenteDatos){
		
		case FROM_FILE_JSON:
			loadJSONFromAsset();		
			break;
		case FROM_PARSE_JSON:
			loadJSONFromParseDotCom();
			break;
		}
		
				
	}

	private String loadJSONFromParseDotCom() {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
		
		//Cogemos todos, si no podriamos usar where
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> lista, ParseException arg1) {
				
								
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
					tienda.setHorario(parseTienda.getString("hoursOfOperaion"));
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
								
					tienda.setComentarios((Comment[])comentarios.toArray(new Comment[comentarios.size()]));
					
					Location localizacion=new Location();
					
					try {
						
						localizacion.setLatitude((float)parseTienda.getJSONObject("location").getDouble("lat"));
						localizacion.setLongitude((float)parseTienda.getJSONObject("location").getDouble("longitude"));
					
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

	public void loadJSONFromAsset() {
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

        }


        JsonElement jsonElement = new JsonParser().parse(json);


        JsonArray array= jsonElement.getAsJsonArray();
        Iterator<JsonElement> iterator = array.iterator();


        while(iterator.hasNext()){
        	JsonElement json2 = (JsonElement)iterator.next();
        	Gson gson = new Gson();
        	Shop tienda = gson.fromJson(json2, Shop.class);
        	tiendas.add(tienda);
		}
			
        

    }
}
