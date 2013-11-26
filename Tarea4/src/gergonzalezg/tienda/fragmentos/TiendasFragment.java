package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea4.R;
import gergonzalezg.tienda.actividades.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;



public class TiendasFragment extends Fragment implements TabListener {

	Fragment[] fragments = new Fragment[]{new ListadoTiendaFragment(), 
				new StoreMapFragment()};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {	
		super.onActivityCreated(savedInstanceState);
		
		
		ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
		
		actionBar.addTab(actionBar.newTab()
						.setText("Nuestras tiendas")
						.setTabListener(this));
		
		actionBar.addTab(actionBar.newTab()
				.setText("Mapas")
				.setTabListener(this));
				
		FragmentManager manager = getActivity().getSupportFragmentManager();
		
        manager.beginTransaction()
        	    .add(R.id.tiendasFragment, fragments[0])
        		.add(R.id.tiendasFragment, fragments[1])        		        	   
        	    .commit();
		
        
	}

		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_tiendas, null);
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
		
		FragmentManager fr = getActivity().getSupportFragmentManager();
		
		fr.beginTransaction()
							.show(frMostrar)
							.hide(frOcultar)
							.commit();
		
		
	}
	
}
