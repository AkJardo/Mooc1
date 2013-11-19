package gergonzalezg.tienda.clases;

public class Photo {

	private String URL;
	private String Descripcion;
	private Comment[] comentarios;
	private int favoritos;
	
	public Photo() {
		super();
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public Comment[] getComentarios() {
		return comentarios;
	}

	public void setComentarios(Comment[] comentarios) {
		this.comentarios = comentarios;
	}

	public int getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(int favoritos) {
		this.favoritos = favoritos;
	}

}
