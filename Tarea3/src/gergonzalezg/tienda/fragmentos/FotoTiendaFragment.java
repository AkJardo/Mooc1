package gergonzalezg.tienda.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import es.gergonzalezg.tarea3.R;

public class FotoTiendaFragment extends Fragment {

	private int idFoto;
	public final static String IDFOTO="idfoto";	
	
	
	public static FotoTiendaFragment newInstance(int idfoto) {
		
		FotoTiendaFragment fragment = new FotoTiendaFragment();
		
		Bundle bundle= new Bundle();
		bundle.putInt(IDFOTO, idfoto);
		
		fragment.setArguments(bundle);
		
		return fragment;
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		this.idFoto = getArguments().getInt(IDFOTO);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_shop_photo, container, false);
		
		ImageView img = (ImageView) rootView.findViewById(R.id.imageTienda);
		img.setImageResource(idFoto);
		
		return rootView;
	}
	
	

}
