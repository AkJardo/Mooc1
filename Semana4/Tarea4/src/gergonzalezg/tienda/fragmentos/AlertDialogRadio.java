package gergonzalezg.tienda.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertDialogRadio  extends DialogFragment{
	
	
	
	static String[] code = new String[]{
        "Mapa normal",
        "Mapa h�birdo",
        "Mapa satelite",
        "Mapa terreno",
    };
	 
    
    AlertPositiveListener alertPositiveListener;
 
    
    public interface AlertPositiveListener {
        public void onPositiveClick(int position);
    }
 
   
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListener) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " debe implementar AlertPositiveListener");
        }
    }
 
    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClick(position);
        }
    };
 
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
      
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
 
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
 
        b.setTitle("Elija tipo de mapa");
 
        b.setSingleChoiceItems(code, position, null);
 
        b.setPositiveButton("OK",positiveListener);
 
        b.setNegativeButton("Cancel", null);
 
        AlertDialog d = b.create();
 
        return d;
    }
}
