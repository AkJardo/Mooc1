package gergonzalezg.tienda.clases;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.parse.ParseObject;

public class Photo {

	private String URL="";
	private String URI="";
	private String Descripcion;
	private ArrayList<Comment> comentarios=new ArrayList<Comment>();
	private int favoritos=0;
	private String usuario="";
	//uso esta propiedad para determinar si es una imagen local (URI) o remota (URL)
	private boolean isLocal=false;
	
	public Photo() {
		super();
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
		//indicamos que la imagen no es local (vendrá de una URL)
		isLocal=false;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public ArrayList<Comment> getComentarios() {
		return comentarios;
	}

	public void setComentarios(ArrayList<Comment> comentarios) {
		this.comentarios = comentarios;
	}

	public int getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(int favoritos) {
		this.favoritos = favoritos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
		//indicamos que la imagen es local
		isLocal=true;
	}

	public boolean isLocal() {
		return isLocal;
	}

	
	public boolean enviarParse(){
		
		Gson gson= new Gson();
		
		//Método que envía a través del API de Parse mi foto a Parse.com
		ParseObject shopToParse = new ParseObject("Photo");
		
		shopToParse.put("URI", URI);
		shopToParse.put("URL", URL);
		shopToParse.put("Descripcion", Descripcion);
		shopToParse.put("usuario", usuario);
		shopToParse.put("favoritos", favoritos);
		shopToParse.put("local", isLocal);
		
		comentarios=new ArrayList<Comment>();
		
		comentarios.add(new Comment());
		comentarios.get(0).setComentario("asdfasdfasdfasdf");
		
		shopToParse.put("comentarios", gson.toJson(comentarios,ArrayList.class));
		
		shopToParse.saveInBackground();
		
		return false;
	}
	
}
