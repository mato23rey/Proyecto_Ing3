package haversine;

/**
 * @author Seba
 *Clase que almacena las coordenadas de una posición.
 */
public class Coordinate {

	private double longitude, latitude;


	/** Contructuro de la clase.
	 * @param longitude
	 * @param latitude
	 */
	public Coordinate(final double longitude, final double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public double getLatitude() {
		return latitude;
	}
	
	

}
