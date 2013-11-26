package gergonzalezg.tienda.clases;

import java.io.Serializable;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3607451723936061762L;
	private String comment;
	
	public Comment() {
		super();
	}

	
	
	public Comment(String comentario) {
		super();
		this.comment = comentario;
	}



	public String getComentario() {
		return comment;
	}

	public void setComentario(String comentario) {
		this.comment = comentario;
	}

}
