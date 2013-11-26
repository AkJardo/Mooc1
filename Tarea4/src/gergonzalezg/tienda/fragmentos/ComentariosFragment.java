package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea4.R;
import gergonzalezg.tienda.clases.Comment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ComentariosFragment extends Fragment implements OnClickListener {

	
	ImageButton botonFavorito;
	TextView txtFavorito;
	ImageButton botonAdd;
	TextView txtComentarios;
	EditText nuevoComentario;
	ScrollView scvComentarios;
	private String nombreTienda="";
	private int favoritos;
	private boolean checkAsFavorite=false;
	private ArrayList<Comment> comentarios = new ArrayList<Comment>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View vista = inflater.inflate(R.layout.fragment_comentarios,container,false);
		botonAdd= (ImageButton) vista.findViewById(R.id.btnNuevoComentario);
		//LinearLayout favoritos = (LinearLayout) vista.findViewById(R.id.layoutFavorito);
		botonFavorito= (ImageButton) vista.findViewById(R.id.btnFavorito);
		txtFavorito= (TextView) vista.findViewById(R.id.txtFavorito);
		scvComentarios=(ScrollView) vista.findViewById(R.id.scvComentarios);
		txtComentarios= (TextView) scvComentarios.findViewById(R.id.txtComentarios);
		nuevoComentario= (EditText) vista.findViewById(R.id.editComentario);
				
		txtComentarios.setText("");
		txtFavorito.setText(String.valueOf(favoritos));
		botonAdd.setOnClickListener(this);
		botonFavorito.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (v.getId() == R.id.btnFavorito ){
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (checkAsFavorite){
							uncheckAsFavorite(v);
						}else{							
							checkAsFavorite(v);
						}
					}
				}
				return false;
			}
		});
		
		return vista;
	}

	@Override
	public void onClick(View v) {
		
		if (!(nuevoComentario.getText().toString() == "")){
			Comment newComment = new Comment();
			newComment.setComentario(nuevoComentario.getText().toString());
			this.comentarios.add(newComment);
			writeComment(newComment);
			updateComentarios();
		}
	}
	
	private void checkAsFavorite(View v){
		((ImageView) v).setColorFilter(0xfffeac25);
		txtFavorito.setText(String.valueOf((++favoritos)));
		checkAsFavorite=true;
		updateFavorites();
	}
	
	private void uncheckAsFavorite(View v){
		((ImageView) v).setColorFilter(null);
		txtFavorito.setText(String.valueOf((--favoritos)));
		checkAsFavorite=false;
		updateFavorites();
	}
	
	
	
	private void updateFavorites(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
		query.whereEqualTo("name", nombreTienda);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		       
		            //Si encontramos la tienda actualizamos los favoritos
		        scoreList.get(0).put("favorites", ComentariosFragment.this.favoritos);	
		        scoreList.get(0).saveInBackground();
		        
		    }
		});
	}
	
private void updateComentarios(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
		query.whereEqualTo("name", nombreTienda);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		       
		            //Si encontramos la tienda actualizamos los comentarios
		    	
		    	JSONArray listaComentarios=new JSONArray();
		    	
		    	for (int i=0; i < ComentariosFragment.this.comentarios.size();i++){
		    		JSONObject comentario = new JSONObject();
		    		
		    		try {
						comentario.put("comment", ComentariosFragment.this.comentarios.get(i).getComentario());
						listaComentarios.put(comentario);
						
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	}

		    	
		        scoreList.get(0).put("comments", listaComentarios);	
		        scoreList.get(0).saveInBackground();
		        
		    }
		});
	}
	
	private void writeComment(Comment comentario){
		
		txtComentarios.setText(txtComentarios.getText().toString() + "- " + comentario.getComentario().toString() + '\n');
	}
	
	public void loadComments(ArrayList<Comment> comentarios){
		
		this.comentarios=comentarios;
	
		for(int i=0; i<comentarios.size();i++){
			//this.comentarios.add(comentarios.get(i));
			writeComment(comentarios.get(i));
		}
	}
	
	public void loadFavorites(int favoritos){
		
		this.favoritos=favoritos;
		txtFavorito.setText(String.valueOf(favoritos));	
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}
	

}
