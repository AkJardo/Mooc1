package gergonzalezg.tienda.clases;

import java.io.Serializable;

public class Location implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1309909376214958986L;
	private float lat;
	private float longitude;
	
		
	public Location() {
		super();
	}


	public Location(float latitude, float longitude) {
		super();
		this.lat = latitude;
		this.longitude = longitude;
	}


	public float getLatitude() {
		return lat;
	}


	public void setLatitude(float latitude) {
		this.lat = latitude;
	}


	public float getLongitude() {
		return longitude;
	}


	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	
	
	
}
