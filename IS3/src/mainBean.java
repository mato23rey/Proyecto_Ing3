import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

/**
 * @author Seba
 *Clase que se utiliza para manejar la pantalla principal de la aplicación
 */
public class mainBean {

	/**
	 * Dirección del usuario
	 */
	/**
	 * Texto de la búsqueda
	 */
	/**
	 * Coordenadas de la dirección
	 */
	String address,data,cords;

	/**
	 * @return Dirección ingresada
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address Dirección desde donde búscar
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Texto de la búsqueda
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data Texto para realizar la búsqueda
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return Coordenadas de la dirección
	 */
	public String getCords() {
		return cords;
	}

	/**
	 * @param cords Coordenadas desde donde realizar la búsqueda
	 */
	public void setCords(String cords) {
		this.cords = cords;
	}

	/**Método de acceso para realizar la búsqueda
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
	 *Método encargado de lanzar la búsqueda en el sistema y redireccionar a la pantalla de resultados
	 * @param data Texto de la búsqueda
	 * @param cords Coordenadas de la dirección donde realizar la búsqueda
	 */
	private void searchData(String data,String cords){
		System.out.println("Searching: "+data);
		FacesContext  faceContext=FacesContext.getCurrentInstance();
		HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
		httpServletRequest.getSession().setAttribute("search_data", data);
		httpServletRequest.getSession().setAttribute("cords", cords);
	}


	/**
	 * No implementado aún
	 * Método que se encargará de encontrar las farmacias más cercanas en base a las distancias entre coordenadas
	 */
	private void locateClosePharmacy(){

	}

	/**
	 * Método encargado de invocar una función JS que trabajando con una API de geocodificación traduce la dirección dada en coordenadas
	 * @param address Dirección a traducir
	 */
	private void locateAddress(String address){
		RequestContext.getCurrentInstance().execute("find('"+address+"')");
	}
}
