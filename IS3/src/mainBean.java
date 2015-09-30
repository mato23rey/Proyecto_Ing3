import javax.faces.event.ActionEvent;

public class mainBean {

	String address,data;
	
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

	public void login(ActionEvent actionEvent) {
		
	}
	
	public void register(ActionEvent actionEvent) {
		
	}
	
	public void search(ActionEvent actionEvent) {
		if(address != null){
			
			if(data != null){
				searchData(data);
			}else{
				locateClosePharmacy();
			}
			
		}
	}

	private void searchData(String data){
		
	}
	
	
	private void locateClosePharmacy(){
		
	}
}
