package gergonzalezg.tienda.clases;

import java.io.Serializable;

public class Tienda implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 5695682506102853998L;

private int id;

	private String nombre;
	private String actividad;
	private String direccion;
	private String telefono;
	private String web;
	private String email;
	private String horario;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public Tienda(int id, String nombre, String actividad, String direccion,
			String telefono, String web, String email, String horario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.actividad = actividad;
		this.direccion = direccion;
		this.telefono = telefono;
		this.web = web;
		this.email = email;
		this.horario = horario;
	}
	
	
	
}
