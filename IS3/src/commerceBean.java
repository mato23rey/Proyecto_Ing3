import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hibernate.Product;
import hibernate.Product_Sucursal;

public class commerceBean {


	List<Product> result;
	int comId;


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

	}

	private void loadProducts(int comId){
		System.out.println("Loading products from: "+comId);

		result = new ArrayList<Product>();

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Query queryAllProducts = session.createQuery("from Product_Sucursal where sucursal_id = :sucId");
		queryAllProducts.setParameter("sucId", comId);
		for(Object o : queryAllProducts.list()){
			Product_Sucursal ps = (Product_Sucursal)o;

			Query queryProduct = session.createQuery("from Product where id = :prodId");
			queryProduct.setParameter("prodId", ps.getProduct_id());

			Product prod = (Product) queryProduct.uniqueResult();

			result.add(prod);

		}

		session.getTransaction().commit();

		if(!isWithResult()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La busqueda no ha devuelto resultados",""));
		}
	}

}
