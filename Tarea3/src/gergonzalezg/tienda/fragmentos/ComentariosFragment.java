package gergonzalezg.tienda.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import es.gergonzalezg.tarea3.R;

public class ComentariosFragment extends Fragment implements OnClickListener {

	
	ImageButton botonAdd;
	TextView txtComentarios;
	EditText nuevoComentario;
	ScrollView scvComentarios;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View comentarios = inflater.inflate(R.layout.fragment_comentarios,container,false);
		botonAdd= (ImageButton) comentarios.findViewById(R.id.btnNuevoComentario);
		scvComentarios=(ScrollView) comentarios.findViewById(R.id.scvComentarios);
		txtComentarios= (TextView) scvComentarios.findViewById(R.id.txtComentarios);
		nuevoComentario= (EditText) comentarios.findViewById(R.id.editComentario);
		txtComentarios.setText("- Me encantó la tienda. Volveré!\n");
		
		botonAdd.setOnClickListener(this);
		
		return comentarios;
	}

	@Override
	public void onClick(View v) {
		
		if (!(nuevoComentario.getText().toString() == "")){
			txtComentarios.setText(txtComentarios.getText().toString() + "- " + nuevoComentario.getText().toString() + '\n');
		}
	}
	

}
