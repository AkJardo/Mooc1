package com.ug.telescopio.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ug.telescopio.R;
import com.ug.telescopio.activities.MainActivity;

public class CountriesContentFragment extends Fragment  {
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//fragments[0].setHasOptionsMenu(true);

		final ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

		Log.d(this.getClass().getName(), "onActivityCreated");



		if (savedInstanceState!=null){
			return;
		}
		
		
		Fragment fr1=new CountriesListFragment(); 
		Fragment fr2=new CountriesFlagsFragment();
		
		actionBar.addTab(
				actionBar.newTab()
				.setText(getResources().getString(R.string.list_title))
				.setTabListener(new MyTabsListener(fr1)));

		actionBar.addTab(
				actionBar.newTab()
				.setText(getResources().getString(R.string.flags_title))
				.setTabListener(new MyTabsListener(fr2)));  
		
		//setContent(0);
        
       
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		Log.d(this.getClass().getName(), "onCreateView");
		return inflater.inflate(R.layout.fragment_countries_content, container, false);
	}	
	
	/*@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		setContent(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	} */

	public void setContent(int tab) {		
		Fragment newFragment = new Fragment();
		switch (tab) {
			case 0:
				newFragment = new CountriesListFragment();
				break;
			case 1:
				newFragment = new CountriesFlagsFragment();
				break;
		}
		
		FragmentManager manager = getChildFragmentManager();
		
		manager.beginTransaction()
				.replace(R.id.mainContent,newFragment).commit();
				
	}	
	
	
	class MyTabsListener implements ActionBar.TabListener {
        public Fragment fragment;
        public Context context;

        public MyTabsListener(Fragment fragment) {
                    this.fragment = fragment;
                    

        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
                    //Toast.makeText(context, "Reselected!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
                    //Toast.makeText(context, "Selected!", Toast.LENGTH_SHORT).show();
        			ft=getChildFragmentManager().beginTransaction();
        			
        			if( fragment instanceof CountriesFlagsFragment ){
        	            fragment = new CountriesFlagsFragment();
        	        }
        			
                    ft.replace(R.id.mainContent, fragment).commit();;
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                    //Toast.makeText(context, "Unselected!", Toast.LENGTH_SHORT).show();
        			ft=getChildFragmentManager().beginTransaction();
                    ft.remove(fragment).commit();
        }
        
}
}
