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
 * Clase encargada de la realización de las búsquedas
 */
public class searchBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private final HttpServletRequest httpServletRequest;
	private final FacesContext faceContext;

	/**
	 * Texto para realizar la búsqueda 
	 */
	/**
	 * Coordenadas desde donde realizar la búsqueda
	 */
	String address,data,cords;

	private List<SearchResult> result;

	int comId;

	public List<SearchResult> getResult() {
		return result;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCords() {
		return cords;
	}

	public void setCords(String cords) {
		this.cords = cords;
		FacesContext  faceContext=FacesContext.getCurrentInstance();
		HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
		httpServletRequest.getSession().setAttribute("cords", cords);

	}



	/**
	 * @return Texto para realizar la búsqueda
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data Texto para realizar la búsqueda
	 */
	public void setData(String data) {
		this.data = data;
		HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
		httpServletRequest.getSession().setAttribute("search_data", data);
	}

	public boolean isWithResult(){
		return result.size() != 0;
	}


	/**
	 * Método de acceso para realizar ls búsqueda
	 * @param actionEvent evento del sistema
	 */
	@SuppressWarnings("unchecked")
	public searchBean(){
		result = new ArrayList<SearchResult>();

		faceContext=FacesContext.getCurrentInstance();
		httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();

		HttpSession session = httpServletRequest.getSession();

		if(session.getAttribute("search_done") !=null){

			data = httpServletRequest.getSession().getAttribute("search_data").toString();
			if(httpServletRequest.getSession().getAttribute("cords") != null){
				cords = httpServletRequest.getSession().getAttribute("cords").toString();
			}
			address = httpServletRequest.getSession().getAttribute("search_address").toString();
			result = (List<SearchResult>)httpServletRequest.getSession().getAttribute("search_result");

			orderByDistance();

			if((cords == null || cords.equals("")) && (address != null || !address.equals(""))){
				setCords(locateAddress(address));
			}

		}
	}

	/**Método de acceso para realizar la búsqueda
	 * @param actionEvent Evento
	 */
	public void search(ActionEvent actionEvent) {
		if(address != null){
			setCords(locateAddress(address));

			if(data != null){

				FacesContext  faceContext=FacesContext.getCurrentInstance();
				HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
				httpServletRequest.getSession().setAttribute("search_data", data);
				httpServletRequest.getSession().setAttribute("search_address", address);
				httpServletRequest.getSession().setAttribute("cords", cords);

				search(data);

				httpServletRequest.getSession().setAttribute("search_result", result);
				httpServletRequest.getSession().setAttribute("search_done", true);

			}else{
				//FUCK YOU!!
			}
		}
	}

	/**
	 * Método encargado de invocar una API de geocodificación que traduce la dirección dada en coordenadas
	 * @param address Dirección a traducir
	 */
	private String locateAddress(String address){
		String coords = null;
		String formatedAddress = address.replace(" ","%20");
		try {
			URL url = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D%22"+formatedAddress+"%2CMontevideo%2CUruguay%22&format=json".replace(" ","%20"));
			JSONTokener tokener = new JSONTokener(url.openStream());
			JSONObject json = new JSONObject(tokener);

			JSONObject query = json.getJSONObject("query");
			JSONObject results = query.getJSONObject("results");
			JSONObject result = results.getJSONObject("Result");

			String lat = result.getString("latitude");
			String lon = result.getString("longitude");

			coords = lat+","+lon;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return coords;
	}

	/** 
	 *Método encargado de lanzar la búsqueda en el sistema y redireccionar a la pantalla de resultados
	 * @param data Texto de la búsqueda
	 */

	private void search(String data){
		result = new ArrayList<SearchResult>();

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Query querySuc = session.createQuery("from Sucursal where name like '%"+data+"%' or pharmacy_id in (from Pharmacy where name like '%"+data+"%')");
		for(Object o : querySuc.list()){
			Sucursal s = (Sucursal)o;
			SearchResult sR = new SearchResult();
			sR.setId(s.getId());
			sR.setAddress(s.getAddress());
			sR.setSucursalName(s.getName());

			Query queryPha = session.createQuery("from Pharmacy where id = :id");
			queryPha.setParameter("id", s.getPharmacy_id());
			Pharmacy pharmacy = (Pharmacy) queryPha.uniqueResult();
			sR.setPharmacyName(pharmacy.getName());
			result.add(sR);
			Flash theFlashScope = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			theFlashScope.put("searchResults",result);
		}

		session.getTransaction().commit();

		if(!isWithResult()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La busqueda no ha devuelto resultados",""));
		}

	}

	private void orderByDistance(){
		List<SearchResult> auxResult = new ArrayList<SearchResult>();

		double myX,myY;
		myX = Double.parseDouble(cords.split(",")[0]);
		myY = Double.parseDouble(cords.split(",")[1]);


		for(SearchResult sR : result){
			String resultPos = locateAddress(sR.getAddress());
			double comX,comY;
			comX = Double.parseDouble(resultPos.split(",")[0]);
			comY = Double.parseDouble(resultPos.split(",")[1]);

			/*double quad = (comX-myX)+(comY-myY);
			if(quad<0){
				quad*=-1;
			}

			//double distance = Math.sqrt(quad);*/
			double distance = Haversine.calculateDistance(myX, myY, comX, comY);

			sR.setDistance(distance);

			if(auxResult.isEmpty()){
				auxResult.add(sR);
			}else{
				for(int iPos = 0;iPos<auxResult.size();iPos++){
					SearchResult sRA = auxResult.get(iPos);
					if(sR.getDistance() <= sRA.getDistance()){
						auxResult.add(iPos, sR);
						break;
					}
				}
				if(!auxResult.contains(sR)){
					auxResult.add(sR);
				}
			}
		}
		result = auxResult;

	}

	public void selectCommerce(ActionEvent event){

		comId = Integer.parseInt(event.getComponent().getAttributes().get("comId").toString());

		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("target", "commerce.xhtml?comId="+comId);

	}

}
