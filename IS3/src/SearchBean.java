import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.primefaces.context.RequestContext;

import haversine.Haversine;
import hibernate.Pharmacy;
import hibernate.Sucursal;
import search.SearchResult;

/**
 * @author Seba
 * Clase encargada de la realizaci�n de las b�squedas
 */
public class SearchBean implements Serializable {

	/** ID de serialización.*/
	private static final long serialVersionUID = 2L;




	/**Dirección desde donde buscar.*/
	/**Texto para realizar la búsqueda.*/
	/**Coordenadas de la dirección desde donde buscar.*/
	private String address, data, cords;

	/**Lista de resultados de la búsqueda.*/
	private List<SearchResult> result;

	/***/
	int comId;

	/**
	 * @return Lista de resultados de la búsqueda.
	 */
	public final List<SearchResult> getResult() {
		return result;
	}

	/**
	 * @return Dirección desde donde realizar la búsqueda
	 */
	public final String getAddress() {
		return address;
	}

	/**
	 * @param address Dirección desde donde realizar la búsqueda
	 */
	public final void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return Coordenadas de la dirección desde donde realizar la búsqueda
	 */
	public final String getCords() {
		return cords;
	}

	/**
	 * @param cords Coordenadas de la dirección desde donde realizar la búsqueda
	 */
	public final void setCords(final String cords) {
		this.cords = cords;
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		httpServletRequest.getSession().setAttribute("cords", cords);

	}




	/**
	 * @return Texto para realizar la búsqueda
	 */
	public final String getData() {
		return data;
	}


	/**
	 * @param data Texto para realizar la búsqueda
	 */
	public final void setData(final String data) {
		this.data = data;
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		httpServletRequest.getSession().setAttribute("search_data", data);
	}

	/**
	 * @return Tamaño de la lista de resultados
	 */
	public final boolean isWithResult() {
		return result.size() != 0;
	}



	/**Contructor de la clase.*/
	@SuppressWarnings("unchecked")
	public SearchBean() {
		result = new ArrayList<SearchResult>();

		HttpServletRequest httpServletRequest = getHttpServletRequest();

		HttpSession session = httpServletRequest.getSession();

		if (session.getAttribute("search_done") != null) {

			data = httpServletRequest.getSession().getAttribute("search_data").toString();
			if (httpServletRequest.getSession().getAttribute("cords") != null) {
				cords = httpServletRequest.getSession().getAttribute("cords").toString();
			}
			address = httpServletRequest.getSession().getAttribute("search_address").toString();
			result = (List<SearchResult>) httpServletRequest.getSession().getAttribute("search_result");

			orderByDistance();

			if ((cords == null || cords.equals(""))
					&& 
					(address != null || !address.equals(""))) {
				setCords(locateAddress(address));
			}

		}
	}


	/**
	 * @param actionEvent Evento del sistema
	 */
	public final void search(final ActionEvent actionEvent) {
		if (address != null) {
			setCords(locateAddress(address));

			if (data != null) {

				HttpServletRequest httpServletRequest = getHttpServletRequest();
				httpServletRequest.getSession().setAttribute("search_data", data);
				httpServletRequest.getSession().setAttribute("search_address", address);
				httpServletRequest.getSession().setAttribute("cords", cords);

				search(data);

				httpServletRequest.getSession().setAttribute("search_result", result);
				httpServletRequest.getSession().setAttribute("search_done", true);

			} else {
				//TODO
				System.out.println("Error");
			}
		}
	}


	/**Método encargado de obtener las coordenadas de una dirección.
	 * @param address Dirección objetivo
	 * @return Coordenadas <x,y> de la dirección
	 */
	private String locateAddress(final String address){
		String coords = null;
		String formatedAddress = address.replace(" ", "%20");
		try {
			URL url = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D%22" +
					formatedAddress + 
					"%2CMontevideo%2CUruguay%22&format=json");

			JSONTokener tokener = new JSONTokener(url.openStream());
			JSONObject json = new JSONObject(tokener);

			JSONObject query = json.getJSONObject("query");
			JSONObject results = query.getJSONObject("results");
			JSONObject jsonResult = results.getJSONObject("Result");

			String lat = jsonResult.getString("latitude");
			String lon = jsonResult.getString("longitude");

			coords = lat + "," + lon;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return coords;
	}



	/**
	 * @param data Texto con el cual realizar la búsqueda
	 */
	private void search(final String data){
		result = new ArrayList<SearchResult>();

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Query querySuc = session.createQuery("from Sucursal where name like '%"+data+"%'" 
				+ " or pharmacy_id in (select id from Pharmacy where name like '%"+data+"%')" 
				+ " or id in (select sucursal_id from Product_Sucursal where product_id in " 
				+ " (select id from Product where name like '%" + data + "%'))");

		for (Object o : querySuc.list()) {
			Sucursal s = (Sucursal) o;
			SearchResult sR = new SearchResult();
			sR.setId(s.getId());
			sR.setAddress(s.getAddress());
			sR.setSucursalName(s.getName());

			Query queryPha = session.createQuery("from Pharmacy where id = :id");
			queryPha.setParameter("id", s.getPharmacy_id());

			Pharmacy pharmacy = (Pharmacy) queryPha.uniqueResult();
			sR.setPharmacyName(pharmacy.getName());
			sR.setPharmacyId(pharmacy.getId());

			result.add(sR);
			Flash theFlashScope = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			theFlashScope.put("searchResults", result);
		}

		session.getTransaction().commit();

		if (!isWithResult()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"La busqueda no ha devuelto resultados", "");
			getFacesContext().addMessage(null, msg);
		}

	}

	/**Nétodo que ordena las sucursales en base a su distancia con el origen.*/
	private void orderByDistance() {
		List<SearchResult> auxResult = new ArrayList<SearchResult>();

		double myX, myY;
		myX = Double.parseDouble(cords.split(",")[0]);
		myY = Double.parseDouble(cords.split(",")[1]);


		for (SearchResult sR : result) {
			String resultPos = locateAddress(sR.getAddress());
			double comX, comY;
			comX = Double.parseDouble(resultPos.split(",")[0]);
			comY = Double.parseDouble(resultPos.split(",")[1]);

			double distance = Haversine.calculateDistance(myX, myY, comX, comY);

			sR.setDistance(distance);

			if (auxResult.isEmpty()) {
				auxResult.add(sR);
			} else {
				for (int iPos = 0; iPos < auxResult.size(); iPos++) {
					SearchResult sRA = auxResult.get(iPos);
					if (sR.getDistance() <= sRA.getDistance()) {
						auxResult.add(iPos, sR);
						break;
					}
				}
				if (!auxResult.contains(sR)) {
					auxResult.add(sR);
				}
			}
		}
		result = auxResult;

	}

	/**
	 * @param event Evento del sistema
	 */
	public final void selectCommerce(final ActionEvent event){

		//comId = Integer.parseInt(event.getComponent().getAttributes().get("comId").toString());
		SearchResult result = (SearchResult) event.getComponent().getAttributes().get("com");
		comId = result.getId();
		FacesContext faceContext = getFacesContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
		httpServletRequest.getSession().setAttribute("commerce", result);

		RequestContext context = getCurrentContext();
		context.addCallbackParam("target", "commerce.xhtml?comId="+comId);

	}


	/**
	 * @return Request Context instance
	 */
	private RequestContext getCurrentContext() {
		return  RequestContext.getCurrentInstance();
	}


	/**
	 * @param faceContext Face Context intance
	 * @return HttpServlerRequest request
	 */
	private HttpServletRequest getHttpServletRequest() {
		FacesContext faceContext = getFacesContext();
		return (HttpServletRequest) faceContext.getExternalContext().getRequest();
	}


	private final FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}

}
