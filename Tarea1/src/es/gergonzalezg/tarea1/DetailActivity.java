package es.gergonzalezg.tarea1;

import java.util.HashMap;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity implements OnClickListener {

	private TextView txtNombre;
	private TextView txtActividad;
	private TextView txtTelefono;
	private TextView txtDireccion;
	private TextView txtWeb;
	private TextView txtEmail;
	private TextView txtHorario;
	private Button btnLLamar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_shop);
		
		HashMap<String, String> tienda = new HashMap<String, String>();
		
		tienda=(HashMap<String, String>) getIntent().getSerializableExtra("tiendaseleccionada");
		
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
		
		txtNombre.setText(tienda.get("nombre").toString());
		txtActividad.setText(tienda.get("actividad").toString());
		txtTelefono.setText(tienda.get("telefono").toString());
		txtDireccion.setText(tienda.get("direccion").toString());
		txtWeb.setText(tienda.get("website").toString());
		txtEmail.setText(tienda.get("email").toString());
		txtHorario.setText(tienda.get("horario").toString());
		
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
	public void onClick(View boton) {
		//No necesito diferenciar botón, de momento solo tengo uno
		
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + txtTelefono.getText().toString()));
		startActivity(intent); 
		
	}

}
