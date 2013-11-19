package gergonzalezg.tienda.actividades;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.clases.Shop;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class PhotoActivity extends FragmentActivity {

	private Shop tienda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		tienda=(Shop) getIntent().getSerializableExtra("tienda");
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()){
		
		case R.id.action_share:
				Intent intent = new Intent();
				
				intent.setAction(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_TEXT, "Visita nuestra tienda " + tienda.getNombre().toString() + " en la web " + tienda.getWeb().toString());
				intent.setType("text/plain");
				startActivity(intent.createChooser(intent,getString(R.string.action_share)));
				
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
