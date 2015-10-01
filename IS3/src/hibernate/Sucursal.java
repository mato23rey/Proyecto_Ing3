package hibernate;

public class Sucursal {

	int id,pharmacy_id;
	String name,address;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPharmacy_id() {
		return pharmacy_id;
	}
	public void setPharmacy_id(int pharmacy_id) {
		this.pharmacy_id = pharmacy_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}



}
