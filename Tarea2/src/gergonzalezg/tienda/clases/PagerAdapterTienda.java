package gergonzalezg.tienda.clases;

import java.util.ArrayList;
import java.util.List;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.fragmentos.FotoTiendaFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

public class PagerAdapterTienda extends FragmentPagerAdapter {

	List<Fragment> fragments;

	
	public PagerAdapterTienda(FragmentManager fm) {
		super(fm);
		
		fragments = new ArrayList<Fragment>();
		Fragment fr1 = FotoTiendaFragment.newInstance(R.drawable.shop);
		Fragment fr2 = FotoTiendaFragment.newInstance(R.drawable.shop2); 
		
		fragments.add(fr1);
		fragments.add(fr2);
		
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
