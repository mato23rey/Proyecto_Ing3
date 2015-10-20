package search;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Seba
 *Clase que almacena los resultados de la búsqueda
 * para su despliegue en la pantalla.
 */
public class SearchResult implements Serializable {

	/**ID de serialización.*/
	private static final long serialVersionUID = 1L;


	/**Nombre de la farmacia del resultado.*/
	/**Nombre de la sucursal del resultado.*/
	/**Direcciónde la sucursal del resultado.*/
	private String pharmacyName, sucursalName, address;

	/**Puntuación de la sucursal del resultado.*/
	private int score = 0;
	/**ID de la sucursal del resultado.*/
	/**ID de la farmacia del resultado.*/
	private int id, pharmacyId;

	/**Distancia de la sucursal respecto al origen.*/
	private double distance;

	/**
	 * @return  ID de la farmacia del resultado.
	 */
	public final int getPharmacyId() {
		return pharmacyId;
	}

	/**
	 * @param pharmacyId ID de la farmacia del resultado
	 */
	public final void setPharmacyId(final int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	/**
	 * @return ID de la sucursal del resultado
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param id ID de la sucursal del resultado
	 */
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return Nombre de la farmacia del resultado
	 */
	public final String getPharmacyName() {
		return pharmacyName;
	}

	/**
	 * @param pharmacyName Nombre de la farmacia del resultado
	 */
	public final void setPharmacyName(final String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	/**
	 * @return Nombre de la sucursal del resultado
	 */
	public final String getSucursalName() {
		return sucursalName;
	}

	/**
	 * @param sucursalName Nombre de la sucursal del resultado
	 */
	public final void setSucursalName(final String sucursalName) {
		this.sucursalName = sucursalName;
	}

	/**
	 * @return Dirección de la sucursal del resultado
	 */
	public final String getAddress() {
		return address;
	}

	/**
	 * @param address Dirección de la sucursal del resultado
	 */
	public final void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return Puntos de la sucursal del resultado
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * @param score Puntos de la sucursal del resultado
	 */
	public final void setScore(final int score) {
		this.score = score;
	}

	/**Constructor de la clase.*/
	public SearchResult() {
		Random r = new Random();
		score = r.nextInt(5);
	}

	/**
	 * @return Distancia de la sucursal respecto al origen
	 */
	public final double getDistance() {
		return distance;
	}

	/**
	 * @param distance Distancia de la sucursal respecto al origen
	 */
	public final void setDistance(final double distance) {
		this.distance = distance;
	}

}
