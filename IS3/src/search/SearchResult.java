package search;

import java.util.Random;

public class SearchResult {
	String pharmacyName,sucursalName,address;
	int score = 0;
	int id;
	
	
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
	
}
