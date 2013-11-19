package gergonzalezg.tienda.actividades;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.clases.Shop;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends FragmentActivity implements OnClickListener {

	private TextView txtNombre;
	private TextView txtActividad;
	private TextView txtTelefono;
	private TextView txtDireccion;
	private TextView txtWeb;
	private TextView txtEmail;
	private TextView txtHorario;
	private TextView txtFoto;
	private Button btnLLamar;
	private Shop tienda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_shop);
		
		
		
		tienda= (Shop) getIntent().getSerializableExtra("tienda");
		
		btnLLamar=(Button) findViewById(R.id.button1);
		btnLLamar.setBackgroundColor(getResources().getColor(R.color.callButton));
		btnLLamar.setOnClickListener(this);
		
		txtNombre = (TextView) findViewById(R.id.txtNombre);
		txtActividad = (TextView) findViewById(R.id.txtActividad);
		txtTelefono = (TextView) findViewById(R.id.txtTelefono);
		txtDireccion = (TextView) findViewById(R.id.txtDireccion);
		txtWeb = (TextView) findViewById(R.id.txtWeb);
		txtEmail = (TextView) findViewById(R.id.txtCorreo);
		txtHorario = (TextView) findViewById(R.id.txtHorario);
		txtFoto= (TextView) findViewById(R.id.txtFoto);
		txtFoto.setOnClickListener(this);
		
		txtNombre.setText(tienda.getNombre().toString());
		txtActividad.setText(tienda.getActividad().toString());
		txtTelefono.setText(tienda.getTelefono().toString());
		txtDireccion.setText(tienda.getDireccion().toString());
		txtWeb.setText(tienda.getWeb().toString());
		txtEmail.setText(tienda.getEmail().toString());
		txtHorario.setText(tienda.getHorario().toString());
		
		Linkify.addLinks(txtWeb, Linkify.WEB_URLS);
		Linkify.addLinks(txtEmail,Linkify.ALL);
		
		//Esto no funciona, preguntar por qué
		//Linkify.addLinks(txtTelefono,Linkify.ALL,"tel:");
		//txtTelefono.setAutoLinkMask(Linkify.PHONE_NUMBERS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()){
		
		case R.id.action_share:
				Intent intent = new Intent();
				
				intent.setAction(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_TEXT, "Visita nuestra tienda " + txtNombre.getText() + " en la web " + txtWeb.getText());
				intent.setType("text/plain");
				startActivity(intent.createChooser(intent,getString(R.string.action_share)));
				
		default:
				return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	public void onClick(View boton) {
		Intent intent;
		
		switch(boton.getId()){
			case R.id.button1:
				intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + txtTelefono.getText().toString()));
				startActivity(intent); 
				break;
			case R.id.txtFoto:
				intent = new Intent(this,PhotoActivity.class);
				//Para otro ejemplo quiza pasarle el ID para saber que foto
				//En el ejercicio no se especifica
				intent.putExtra("tienda", tienda);
				startActivity(intent); 
				
		}
	
		
	}

}
