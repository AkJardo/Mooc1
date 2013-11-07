package es.gergonzalezg.tarea1;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.text.util.Linkify;
import android.view.Menu;
import android.widget.TextView;

public class DetailActivity extends Activity {

	private TextView txtNombre;
	private TextView txtActividad;
	private TextView txtTelefono;
	private TextView txtDireccion;
	private TextView txtWeb;
	private TextView txtEmail;
	private TextView txtHorario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_shop);
		
		HashMap<String, String> tienda = new HashMap<String, String>();
		
		tienda=(HashMap<String, String>) getIntent().getSerializableExtra("tiendaseleccionada");
		
		txtNombre = (TextView) findViewById(R.id.txtNombre);
		txtActividad = (TextView) findViewById(R.id.txtActividad);
		txtTelefono = (TextView) findViewById(R.id.txtTelefono);
		txtDireccion = (TextView) findViewById(R.id.txtDireccion);
		txtWeb = (TextView) findViewById(R.id.txtWeb);
		txtEmail = (TextView) findViewById(R.id.txtCorreo);
		txtHorario = (TextView) findViewById(R.id.txtHorario);
		
		txtNombre.setText(tienda.get("nombre").toString());
		txtActividad.setText(tienda.get("actividad").toString());
		txtTelefono.setText(tienda.get("telefono").toString());
		txtDireccion.setText(tienda.get("direccion").toString());
		txtWeb.setText(tienda.get("website").toString());
		txtEmail.setText(tienda.get("email").toString());
		txtHorario.setText(tienda.get("horario").toString());
		
		Linkify.addLinks(txtEmail,Linkify.ALL);
		Linkify.addLinks(txtTelefono,Linkify.ALL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
