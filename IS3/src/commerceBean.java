import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import facesTools.FaceTools;
import hibernate.Product;
import hibernate.Product_Sucursal;
import search.SearchResult;

public class commerceBean implements Serializable{


	/**ID de serialización*/
	private static final long serialVersionUID = 1L;

	List<Product> result;
	int comId;
	SearchResult sucursal;

	public int getComId() {
		return comId;
	}

	public void setComId(int comId) {
		this.comId = comId;
		System.out.println("Commerce loaded: "+comId);
		loadProducts(comId);
	}

	public List<Product> getResult() {
		return result;
	}

	public boolean isWithResult(){
		if(result != null){
			return result.size() != 0;
		}else{
			return false;
		}
	}

	public commerceBean(){
		HttpSession session = FaceTools.getHttpServletRequestSession();
		if (session.getAttribute("commerce") != null) {
			sucursal = (SearchResult) session.getAttribute("commerce");
		}
	}


	public SearchResult getSucursal() {
		return sucursal;
	}


	private void loadProducts(int comId){
		result = new ArrayList<Product>();

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Query queryAllProducts = session.createQuery("from Product_Sucursal where sucursal_id = :sucId");
		queryAllProducts.setParameter("sucId", comId);
		for (Object o : queryAllProducts.list()) {
			Product_Sucursal ps = (Product_Sucursal) o;

			Query queryProduct = session.createQuery("from Product where id = :prodId");
			queryProduct.setParameter("prodId", ps.getProduct_id());

			Product prod = (Product) queryProduct.uniqueResult();

			result.add(prod);

		}

		session.getTransaction().commit();

		if (!isWithResult()) {
			Severity severity = FacesMessage.SEVERITY_WARN;
			String msgText = "Error al obtener los productos";
			FacesMessage msg = new FacesMessage(severity, msgText, "");
			FaceTools.getFacesContext().addMessage(null, msg);
		}
	}

}
