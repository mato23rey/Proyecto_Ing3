import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

/**
 * @author Seba
 *Clase que se utiliza para manejar la pantalla principal de la aplicaci�n
 */
public class mainBean {

	/**
	 * Direcci�n del usuario
	 */
	/**
	 * Texto de la b�squeda
	 */
	/**
	 * Coordenadas de la direcci�n
	 */
	String address,data,cords;

	/**
	 * @return Direcci�n ingresada
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address Direcci�n desde donde b�scar
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Texto de la b�squeda
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data Texto para realizar la b�squeda
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return Coordenadas de la direcci�n
	 */
	public String getCords() {
		return cords;
	}

	/**
	 * @param cords Coordenadas desde donde realizar la b�squeda
	 */
	public void setCords(String cords) {
		this.cords = cords;
	}

	/**M�todo de acceso para realizar la b�squeda
	 * @param actionEvent Evento
	 */
	public void search(ActionEvent actionEvent) {	
		if(address != null){

			locateAddress(address);

			if(data != null){
				searchData(data,cords);
			}else{
				locateClosePharmacy();	//FUCK YOU!!
			}

		}
	}

	/** 
	 *M�todo encargado de lanzar la b�squeda en el sistema y redireccionar a la pantalla de resultados
	 * @param data Texto de la b�squeda
	 * @param cords Coordenadas de la direcci�n donde realizar la b�squeda
	 */
	private void searchData(String data,String cords){
		System.out.println("Searching: "+data);
		FacesContext  faceContext=FacesContext.getCurrentInstance();
		HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
		httpServletRequest.getSession().setAttribute("search_data", data);
		httpServletRequest.getSession().setAttribute("cords", cords);
	}


	/**
	 * No implementado a�n
	 * M�todo que se encargar� de encontrar las farmacias m�s cercanas en base a las distancias entre coordenadas
	 */
	private void locateClosePharmacy(){

	}

	/**
	 * M�todo encargado de invocar una funci�n JS que trabajando con una API de geocodificaci�n traduce la direcci�n dada en coordenadas
	 * @param address Direcci�n a traducir
	 */
	private void locateAddress(String address){
		RequestContext.getCurrentInstance().execute("find('"+address+"')");
	}
}
