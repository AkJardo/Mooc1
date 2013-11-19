package com.example.pruebaestructura;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class FlagPagerAdapter extends FragmentPagerAdapter {

	public FlagPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int item) {
		int[] arrayFlags = new int[]{R.drawable.brasil, 
				 R.drawable.mexico,
				 R.drawable.colombia,
				 R.drawable.argentina,
				 R.drawable.peru,
				 R.drawable.venezuela,
				 R.drawable.chile,
				 R.drawable.ecuador,
				 R.drawable.guatemala,
				 R.drawable.cuba};
		
        Fragment fragment = new FragmentElementoViewPager();
        
        return fragment;		
		
	}

	@Override
	public int getCount() {
		return 10;
	}

}
