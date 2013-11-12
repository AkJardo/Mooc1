package es.gergonzalezg.tarea2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.gergonzalezg.tarea2.R;

public class ComentariosFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View comentarios = inflater.inflate(R.layout.fragment_comentarios,container,false);
		return comentarios;
	}
	

}
