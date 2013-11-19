package gergonzalezg.tienda.clases;

import java.io.Serializable;

public class Shop implements Serializable {

private static final long serialVersionUID = 5695682506102853998L;

private int id;

	private String name;
	private String activity;
	private String address;
	private String hoursOfOperaion;
	private String phone;
	private String url;
	private String email;
	
	private Comment[] comments;
	private Location location;
	private int favorites;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return name;
	}
	public void setNombre(String nombre) {
		this.name = nombre;
	}
	public String getActividad() {
		return activity;
	}
	public void setActividad(String actividad) {
		this.activity = actividad;
	}
	public String getDireccion() {
		return address;
	}
	public void setDireccion(String direccion) {
		this.address = direccion;
	}
	public String getTelefono() {
		return phone;
	}
	public void setTelefono(String telefono) {
		this.phone = telefono;
	}
	public String getWeb() {
		return url;
	}
	public void setWeb(String web) {
		this.url = web;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHorario() {
		return hoursOfOperaion;
	}
	public void setHorario(String horario) {
		this.hoursOfOperaion = horario;
	}
		
	public Comment[] getComentarios() {
		return comments;
	}
	public void setComentarios(Comment[] comentarios) {
		this.comments = comentarios;
	}
	public Location getLocalizacion() {
		return location;
	}
	public void setLocalizacion(Location localizacion) {
		this.location = localizacion;
	}
	
	public Shop() {
		super();
	}
	
	public Shop(int id, String nombre, String actividad, String direccion,
			String telefono, String web, String email, String horario) {
		super();
		this.id = id;
		this.name = nombre;
		this.activity = actividad;
		this.address = direccion;
		this.phone = telefono;
		this.url = web;
		this.email = email;
		this.hoursOfOperaion = horario;
	}
	
	
	
}
