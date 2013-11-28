package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea4.R;
import gergonzalezg.tienda.app.App;
import gergonzalezg.tienda.clases.Comment;
import gergonzalezg.tienda.clases.Shop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ComentariosFragment extends Fragment implements OnClickListener {

	
	ImageButton botonFavorito;
	TextView txtFavorito;
	ImageButton botonAdd;
	ListView lstComentarios;
	EditText nuevoComentario;

	private Shop tienda;
	private int favoritos;
	private boolean checkAsFavorite=false;
	private ArrayList<Comment> comentarios = new ArrayList<Comment>();
	private gergonzalezg.tienda.data.DBAdapter db; 
	private AdaptadorComentarios adapter;
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db=((App)getActivity().getApplicationContext()).getDB();
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//cargamos los comentarios
		adapter=new AdaptadorComentarios(getActivity(), R.layout.fragment_comentarios, tienda.getComentarios());
		lstComentarios.setAdapter(adapter);
		loadComments(tienda.getComentarios());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View vista = inflater.inflate(R.layout.fragment_comentarios,container,false);
		botonAdd= (ImageButton) vista.findViewById(R.id.btnNuevoComentario);
		//LinearLayout favoritos = (LinearLayout) vista.findViewById(R.id.layoutFavorito);
		botonFavorito= (ImageButton) vista.findViewById(R.id.btnFavorito);
		txtFavorito= (TextView) vista.findViewById(R.id.txtFavorito);
		
		lstComentarios= (ListView) vista.findViewById(R.id.lstComentarios);
		lstComentarios.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adaptador, View vista,
					int posicion, long arg3) {
				
				deleteComentario((Comment) (adaptador.getItemAtPosition(posicion)));
				return false;
			}
			
		});
			
		
		nuevoComentario= (EditText) vista.findViewById(R.id.editComentario);
		
		
		
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
			this.comentarios.add(0, newComment);
			tienda.getComentarios().add(0, newComment);
			writeComment(newComment);
			updateComentarios();
			saveComment(newComment);
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
		query.whereEqualTo("name", tienda.getNombre());
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		       
		            //Si encontramos la tienda actualizamos los favoritos
		        scoreList.get(0).put("favorites", ComentariosFragment.this.favoritos);	
		        scoreList.get(0).saveInBackground();
		        
		    }
		});
		
		//Actualizamos los favoritos en la DB
		tienda.setFavorites(ComentariosFragment.this.favoritos);
		db.updateShop(tienda);
	}
	
private void deleteComentario(Comment comentario){
	comentarios.remove(comentario);
	tienda.getComentarios().remove(comentario);
	db.deleteComment(comentario);
	adapter.notifyDataSetChanged();
}
	
private void updateComentarios(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
		query.whereEqualTo("name", tienda.getNombre());
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
		
		//(txtComentarios.setText(txtComentarios.getText().toString() + "- " + comentario.getComentario().toString() + '\n');
		adapter.notifyDataSetChanged();
			
	}
	
	private void saveComment(Comment comentario){
		int idComment=db.getTotalCommentsStoreinDatabase();
		comentario.setId(idComment);
		comentario.setIdShop(tienda.getId());
		db.insertComment(comentario, tienda.getId());
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

	public Shop getTienda() {
		return tienda;
	}

	public void setTienda(Shop tienda) {
		this.tienda = tienda;
	}
	
	
	public class AdaptadorComentarios extends ArrayAdapter<Comment>{

		Context contexto;
		List<Comment> comentarios;
		
		public AdaptadorComentarios(Context context, int resource,
				List<Comment> objects) {
			super(context, resource, objects);
			
			this.contexto=context;
			this.comentarios=objects;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
			
			View lista=inflater.inflate(android.R.layout.simple_list_item_1, null);
			
			TextView textoComentario= (TextView) lista.findViewById(android.R.id.text1);
						
			textoComentario.setText(comentarios.get(position).getComentario().toString());
			
			
			return lista;
		}
		
	}

}
