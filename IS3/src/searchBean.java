import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.context.RequestContext;

import hibernate.Pharmacy;
import hibernate.Sucursal;
import search.SearchResult;

/**
 * @author Seba
 * Clase encargada de la realización de las búsquedas
 */
public class searchBean {

	private final HttpServletRequest httpServletRequest;
	private final FacesContext faceContext;

	/**
	 * Texto para realizar la búsqueda 
	 */
	/**
	 * Coordenadas desde donde realizar la búsqueda
	 */
	String data,cords;

	private List<SearchResult> result;

	int comId;

	public List<SearchResult> getResult() {
		return result;
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
		System.out.println("Setting: "+data);
	}

	public boolean isWithResult(){
		return result.size() != 0;
	}


	/**
	 * Método de acceso para realizar ls búsqueda
	 * @param actionEvent evento del sistema
	 */
	public searchBean(){
		System.out.println("New search result!");
		faceContext=FacesContext.getCurrentInstance();
		httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
		if(httpServletRequest.getSession().getAttribute("search_data")!=null){
			data = httpServletRequest.getSession().getAttribute("search_data").toString();
			System.out.println("Data: "+data);
		}
		if(httpServletRequest.getSession().getAttribute("cords")!=null){
			cords = httpServletRequest.getSession().getAttribute("cords").toString();
			System.out.println("Cords: "+cords);
		}

		search(data);
	}

	private void search(String data){
		System.out.println("Searching: "+data);
		result = new ArrayList<SearchResult>();

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Query querySuc = session.createQuery("from Sucursal where name like '%"+data+"%'");
		for(Object o : querySuc.list()){
			Sucursal s = (Sucursal)o;
			SearchResult sR = new SearchResult();
			sR.setAddress(s.getAddress());
			sR.setSucursalName(s.getName());

			Query queryPha = session.createQuery("from Pharmacy where id = :id");
			queryPha.setParameter("id", s.getPharmacy_id());
			Pharmacy pharmacy = (Pharmacy) queryPha.uniqueResult();
			sR.setPharmacyName(pharmacy.getName());
			result.add(sR);
		}

		session.getTransaction().commit();

		if(!isWithResult()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La busqueda no ha devuelto resultados",""));
		}
	}

	public void selectCommerce(ActionEvent event){

		comId = Integer.parseInt(event.getComponent().getAttributes().get("comId").toString());
		System.out.println("Selecting: "+comId);

		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("target", "commerce.xhtml?comId="+comId);

	}

}
