package gergonzalezg.tienda.actividades;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.clases.Tienda;
import gergonzalezg.tienda.fragmentos.ListadoTiendaFragment;
import gergonzalezg.tienda.fragmentos.MapasTiendaFragment;

public class MainActivity extends ActionBarActivity implements TabListener {

	Fragment[] fragments = new Fragment[]{new ListadoTiendaFragment(), 
		      								new MapasTiendaFragment()};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		ActionBar actionBar = getSupportActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab()
						.setText("Nuestras tiendas")
						.setTabListener(this));
		
		actionBar.addTab(actionBar.newTab()
				.setText("Mapas")
				.setTabListener(this));
				
		FragmentManager manager = getSupportFragmentManager();
		
        manager.beginTransaction()
        	    .add(R.id.mainContent, fragments[0])
        		.add(R.id.mainContent, fragments[1])        		        	   
        	    .commit();
		
	}		
		
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fg) {
		cargarFragmentTab(tab.getPosition());
		
	}



	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	private void cargarFragmentTab(int tab){
		
		Fragment frMostrar=	new Fragment();
		Fragment frOcultar= new Fragment();
		
		switch (tab){
		
			case 0:
				frMostrar = fragments[0];
				frOcultar= fragments[1];
				break;
			case 1:
				frMostrar = fragments[1];
				frOcultar= fragments[0];
				break;
		}
		
		FragmentManager fr = getSupportFragmentManager();
		
		fr.beginTransaction()
							.show(frMostrar)
							.hide(frOcultar)
							.commit();
		
		
	}



}
