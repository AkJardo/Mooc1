package com.example.pruebaestructura;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentViewPager extends Fragment {
	
	
	ViewPager viewPager;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.d(this.getClass().getName(), "onActivityCreated");
		if (savedInstanceState!=null){
			return;
		}
		FlagPagerAdapter adapter = new FlagPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(this.getClass().getName(), "onCreateView");
		View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		return view;
	}
}
