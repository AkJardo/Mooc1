package es.gergonzalezg.tarea1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnItemClickListener {

	private TextView textBanner;
	private List<String[]> tiendas = new ArrayList<String[]>() ;
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
	
		//cargamos los datos de cada tienda
		for(int i=0;i < tiendas.size();i++){
			HashMap<String, String> tienda = new HashMap<String, String>();
			
			tienda.put("nombre", tiendas.get(i)[0].toString());
			tienda.put("actividad", tiendas.get(i)[1].toString());
			tienda.put("telefono", tiendas.get(i)[2].toString());
			tienda.put("direccion", tiendas.get(i)[3].toString());
			tienda.put("horario", tiendas.get(i)[4].toString());
			tienda.put("website", tiendas.get(i)[5].toString());
			tienda.put("email", tiendas.get(i)[6].toString());
			tienda.put("ID", tiendas.get(i)[7].toString());
			
			datosTiendas.add(tienda);
		}
		//cargamos la lista
		//LayoutInflater inflater= getLayoutInflater();
		//inflater.inflate(R.layout.shop_list_item, null);
		
		SimpleAdapter adaptador = new SimpleAdapter(this, datosTiendas,R.layout.shop_list_item, new String[]{"nombre","actividad","ID"}, new int[] {R.id.txtShopName,R.id.txtShopDetail} );
		
		setListAdapter(adaptador);
		
		ListView lista= getListView();
	
		lista.setOnItemClickListener(this);
				
	}		
		

	private void cargarTiendas() {
		
		
		tiendas.add(getResources().getStringArray(R.array.J1));
		tiendas.add(getResources().getStringArray(R.array.E1));
		tiendas.add(getResources().getStringArray(R.array.E2));
		tiendas.add(getResources().getStringArray(R.array.C1));
		tiendas.add(getResources().getStringArray(R.array.V1));
		tiendas.add(getResources().getStringArray(R.array.V2));
		tiendas.add(getResources().getStringArray(R.array.L1));
		tiendas.add(getResources().getStringArray(R.array.L2));
		tiendas.add(getResources().getStringArray(R.array.R1));
		tiendas.add(getResources().getStringArray(R.array.R2));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> adaptador, View lista, int posicion, long arg) {
		
		HashMap<String, String> listItem = (HashMap<String, String>)getListView().getItemAtPosition(posicion);

		//Recogemos el identificador de la tienda seleccionada
		
		//Creamos el intent para lanzar la actividad del detalle
		
		Intent intent=new Intent(this, DetailActivity.class);
		
		intent.putExtra("tiendaseleccionada", listItem);
		startActivity(intent);
		
		//Toast.makeText(this, listItem.get("ID").toString(), Toast.LENGTH_SHORT).show();
	}



}
