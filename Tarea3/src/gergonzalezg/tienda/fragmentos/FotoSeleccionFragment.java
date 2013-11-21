package gergonzalezg.tienda.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import es.gergonzalezg.tarea2.R;

public class FotoSeleccionFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
		
		adaptador.add(getResources().getString(R.string.msg_foto_camera));
		adaptador.add(getResources().getString(R.string.msg_foto_gallery));
		
		builder.setTitle(getResources().getString(R.string.msg_foto))
				.setAdapter(adaptador, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						 String opcionSeleccionada = adaptador.getItem(which);
						
						 if (which==0)
							 Toast.makeText(getActivity(), "Fotito reshuuulona", Toast.LENGTH_SHORT).show();
						 else
							 Toast.makeText(getActivity(), "Esa galeria ahí", Toast.LENGTH_SHORT).show();
					}
				});
				
		
		return builder.create();
	}

	

}
