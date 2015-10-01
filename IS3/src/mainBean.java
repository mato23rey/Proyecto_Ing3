import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

public class mainBean {

	String address,data,cords;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	public String getCords() {
		return cords;
	}

	public void setCords(String cords) {
		this.cords = cords;
		System.out.println("Setting: "+cords);
	}

	public void search(ActionEvent actionEvent) {	
		if(address != null){

			locateAddress(address);

			if(data != null){
				searchData(data);
			}else{
				locateClosePharmacy();
			}

		}
	}

	private void searchData(String data){
		System.out.println("Searching: "+data);
	}


	private void locateClosePharmacy(){

	}

	private void locateAddress(String address){
		RequestContext.getCurrentInstance().execute("find('"+address+"')");
	}
}
