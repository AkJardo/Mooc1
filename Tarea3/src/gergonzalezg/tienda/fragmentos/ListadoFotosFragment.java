package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.clases.PagerAdapterTienda;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListadoFotosFragment extends Fragment {

	
	ViewPager pager;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		FragmentManager fg = getChildFragmentManager();
		
		//Instanciamos el adaptador del ViewPager
		PagerAdapterTienda adaptador = new PagerAdapterTienda(fg);
		
		pager.setAdapter(adaptador);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View vista= inflater.inflate(R.layout.fragment_list_photo, null);
		pager = (ViewPager) vista.findViewById(R.id.viewPager);
		pager.setPageTransformer(true, new ZoomOutPageTransformer());
		
		return vista;
	}
	
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
	    private float MIN_SCALE = 0.85f;
	    private float MIN_ALPHA = 0.5f;

	    public void transformPage(View view, float position) {
	        int pageWidth = view.getWidth();
	        int pageHeight = view.getHeight();

	        if (position < -1) { // [-Infinity,-1)
	            // This page is way off-screen to the left.
	            view.setAlpha(0);

	        } else if (position <= 1) { // [-1,1]
	            // Modify the default slide transition to shrink the page as well
	            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
	            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
	            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
	            if (position < 0) {
	                view.setTranslationX(horzMargin - vertMargin / 2);
	            } else {
	                view.setTranslationX(-horzMargin + vertMargin / 2);
	            }

	            // Scale the page down (between MIN_SCALE and 1)
	            view.setScaleX(scaleFactor);
	            view.setScaleY(scaleFactor);

	            // Fade the page relative to its size.
	            view.setAlpha(MIN_ALPHA +
	                    (scaleFactor - MIN_SCALE) /
	                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

	        } else { // (1,+Infinity]
	            // This page is way off-screen to the right.
	            view.setAlpha(0);
	        }
	    }
	}

}
