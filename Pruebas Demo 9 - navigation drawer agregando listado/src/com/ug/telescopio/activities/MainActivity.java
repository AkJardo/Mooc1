package com.ug.telescopio.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ug.telescopio.R;
import com.ug.telescopio.fragments.AboutFragment;
import com.ug.telescopio.fragments.CountriesContentFragment;

public class MainActivity extends ActionBarActivity {
    private ListView drawerList;
    private String[] drawerOptions;
    private DrawerLayout drawerLayout;
    
    Fragment[] fragments = new Fragment[]{new CountriesContentFragment(),
            new AboutFragment()};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                                                       R.layout.drawer_list_item, 
                                                       drawerOptions));
        drawerList.setItemChecked(0, true);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        
        
       
        setContent(0);	
       
        Log.d(this.getClass().getName(), "onCreate");
	}
    
	public void setContent(int index) {
		
		Fragment newFragment=new Fragment();
		
		FragmentManager manager = getSupportFragmentManager();
		final ActionBar actionBar = getSupportActionBar();
		switch (index) {
			case 0:
				
				
				newFragment = new CountriesContentFragment();
		      		        	
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				break;
			case 1:
				newFragment = new AboutFragment();
		       
		       	
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);				
				break;
		}
		
				
		 manager.beginTransaction()
 	    .replace(R.id.contentFrame, newFragment)
 	    .addToBackStack(null)
 	    .commit();		
		
        drawerList.setItemChecked(index, true);
        drawerLayout.closeDrawer(drawerList);	
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	setContent(position);
        }
    }	
}
