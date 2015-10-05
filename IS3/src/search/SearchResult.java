package search;

import java.io.Serializable;
import java.util.Random;

public class SearchResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	String pharmacyName,sucursalName,address;
	int score = 0;
	int id,pharmacy_id;

	double distance;

	public int getPharmacy_id() {
		return pharmacy_id;
	}

	public void setPharmacy_id(int pharmacy_id) {
		this.pharmacy_id = pharmacy_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getSucursalName() {
		return sucursalName;
	}

	public void setSucursalName(String sucursalName) {
		this.sucursalName = sucursalName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public SearchResult(){
		Random r = new Random();
		score = r.nextInt(5);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
