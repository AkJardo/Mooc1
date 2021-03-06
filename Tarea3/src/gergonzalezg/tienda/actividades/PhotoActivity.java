package gergonzalezg.tienda.actividades;

import es.gergonzalezg.tarea3.R;
import gergonzalezg.tienda.clases.Shop;
import gergonzalezg.tienda.fragmentos.ComentariosFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class PhotoActivity extends FragmentActivity {

	private Shop tienda;
	private ComentariosFragment comentariosFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		tienda=(Shop) getIntent().getSerializableExtra("tienda");
		
		comentariosFragment = (ComentariosFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentComentarioDetalle);
		comentariosFragment.loadComments(tienda.getComentarios());
		comentariosFragment.loadFavorites(tienda.getFavorites());
		comentariosFragment.setNombreTienda(tienda.getNombre());
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()){
		
		case R.id.action_share:
				Intent intent = new Intent();
				
				intent.setAction(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_TEXT, "Visita nuestra tienda " + tienda.getNombre().toString() + " en la web " + tienda.getWeb().toString());
				intent.setType("text/plain");
				startActivity(Intent.createChooser(intent,getString(R.string.action_share)));
				
		default:
				return super.onMenuItemSelected(featureId, item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
