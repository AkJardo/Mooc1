package gergonzalezg.tienda.fragmentos;

import java.io.Serializable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import es.gergonzalezg.tarea4.R;

public class DialogFotoSeleccionFragment extends DialogFragment {

	public final static int CAMERA=1;
	public final static int GALLERY=2;
	
	
	NoticeDialogListener listener;
	
	
	
	public static DialogFotoSeleccionFragment getInstance(NoticeDialogListener dialogInterface) {
		
		DialogFotoSeleccionFragment fragmentDialog = new DialogFotoSeleccionFragment();

	    // set fragment arguments
	    Bundle args = new Bundle();
	    args.putSerializable("dialogInterface", (Serializable) dialogInterface);
	    fragmentDialog.setArguments(args);

	    return fragmentDialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
	
		listener = (NoticeDialogListener) this.getArguments().getSerializable("dialogInterface");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
		
		adaptador.add(getResources().getString(R.string.msg_foto_camera));
		adaptador.add(getResources().getString(R.string.msg_foto_gallery));
		
		builder.setTitle(getResources().getString(R.string.msg_foto))
				.setAdapter(adaptador, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
									
						//Le decimos a nuestro listener "ComunidadFragment" que opción escogió el usuario
						
						 if (which==0)
							 listener.onItemSelected(CAMERA);
						 else
							 listener.onItemSelected(GALLERY);
					}
				});
				
		
		return builder.create();
	}
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listener = (NoticeDialogListener) savedInstanceState.getSerializable("dialogInterface");
		return super.onCreateView(inflater, container, savedInstanceState);
	}*/

	public interface NoticeDialogListener {
        public void onItemSelected(int source); 
    }  

}
