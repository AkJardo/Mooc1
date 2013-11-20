package gergonzalezg.tienda.actividades;



import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.clases.AdaptadorImagen;
import gergonzalezg.tienda.clases.Photo;
import gergonzalezg.tienda.fragmentos.ComunidadFragment;
import gergonzalezg.tienda.fragmentos.ListadoFotosFragment;
import gergonzalezg.tienda.fragmentos.TiendasFragment;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;




public class MainActivity extends ActionBarActivity {

	
	public static RequestQueue requestQueue;
	private ListView drawerList;
	private String[] drawerOptions;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	Fragment[] fragments = new Fragment[]{new TiendasFragment(), 
			new ListadoFotosFragment(),
			new ComunidadFragment()};
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_principal);

		requestQueue = Volley.newRequestQueue(this);
	    
		ActionBar actionBar = getSupportActionBar();

		Parse.initialize(this, "9294wWt5A27wRU2QAaaUgqhXsTymYYM8oFixqunP", "7Lv0g3mpQJjYiqGRDYixtPixf54pmXPF0nijQ0zk");

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		drawerOptions = getResources().getStringArray(R.array.opcionesMenu);
		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.list_menu_item, 
				drawerOptions));
		
		drawerList.setItemChecked(0, true);
		drawerList.setOnItemClickListener(new DrawerItemClickListener());

		drawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.msg_open, R.string.msg_close){

			@Override
			public void onDrawerClosed(View drawerView) {
				
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
			
		};
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle(drawerOptions[0]);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		drawerLayout.setDrawerListener(drawerToggle);
		
		getSupportFragmentManager().beginTransaction()
		.add(R.id.contentFrame, fragments[0])
		.add(R.id.contentFrame, fragments[1])   
		.add(R.id.contentFrame, fragments[2])        		        	   
		.commit();

		setContent(0);		

	}		
		
	
	public void setContent(int index) {
		Fragment toHide = null;
		Fragment toHide2 = null;
		Fragment toShow = null;
		final ActionBar actionBar = getSupportActionBar();
		switch (index) {
			case 0:
				toHide = fragments[1];
				toHide2 = fragments[2];
				toShow = fragments[0];				
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				break;
			case 1:
				toHide = fragments[0];
				toHide2 = fragments[2];
				toShow = fragments[1];
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);				
				break;
			case 2:
				toHide = fragments[0];
				toShow = fragments[2];
				toHide2 = fragments[1];
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);				
				break;
		}
		
		actionBar.setTitle(drawerOptions[index]);
		
		FragmentManager manager = getSupportFragmentManager();
		
		manager.beginTransaction()
				.hide(toHide)
				.hide(toHide2)
				.show(toShow)
				.commit();		
		

		/*FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		
		
		switch (index) {
		case 0:
			if (fragments[0] == null){
				fragments[0]= new TiendasFragment();
			}
			transaction.replace(R.id.contentFrame, fragments[0]);
			break;
		case 1:
			if (fragments[1] == null){
				fragments[1]= new ListadoFotosFragment();
			}
			transaction.replace(R.id.contentFrame, fragments[1]);
			break;				
			
		case 2:
			if (fragments[2] == null){
				fragments[2]= new ComunidadFragment();
			}
			transaction.replace(R.id.contentFrame, fragments[2]);
			break;	
	}
		
		transaction.commit();*/
		
        drawerList.setItemChecked(index, true);
        drawerLayout.closeDrawer(drawerList);	
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	setContent(position);
        }
    }	
	

    
    
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			if (drawerLayout.isDrawerOpen(drawerList)){
				drawerLayout.closeDrawer(drawerList);
			}else {
				drawerLayout.openDrawer(drawerList);
			}

		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
