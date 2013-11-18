package gergonzalezg.tienda.clases;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.fragmentos.FotoTiendaFragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapterTienda extends FragmentPagerAdapter {

	List<Fragment> fragments;

	
	public PagerAdapterTienda(FragmentManager fm) {
		super(fm);
		
		/*fragments = new ArrayList<Fragment>();
		Fragment fr1 = FotoTiendaFragment.newInstance(R.drawable.shop);
		Fragment fr2 = FotoTiendaFragment.newInstance(R.drawable.shop2); 
		Fragment fr3 = FotoTiendaFragment.newInstance(R.drawable.shop3);
		Fragment fr4 = FotoTiendaFragment.newInstance(R.drawable.shop4); 
		Fragment fr5 = FotoTiendaFragment.newInstance(R.drawable.shop5);
		
		
		fragments.add(fr1);
		fragments.add(fr2);
		fragments.add(fr3);
		fragments.add(fr4);
		fragments.add(fr5);*/
		
		
	}

	@Override
	public Fragment getItem(int index) {
		
		Fragment fr = new FotoTiendaFragment();
		
		switch (index){
		
		case 0:
			fr = FotoTiendaFragment.newInstance(R.drawable.shop);
			break;
		case 1:
			fr = FotoTiendaFragment.newInstance(R.drawable.shop2);
			break;
		case 2:
			fr = FotoTiendaFragment.newInstance(R.drawable.shop3);
			break;
		case 3:
			fr = FotoTiendaFragment.newInstance(R.drawable.shop4);
			break;
		case 4:
			fr = FotoTiendaFragment.newInstance(R.drawable.shop5);
			break;
		}
		
		
		return fr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

}
