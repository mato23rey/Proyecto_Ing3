import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.context.RequestContext;

import hibernate.Sale;

public class saleBean {

	int sale,score;
	String comment,target;

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public saleBean(){
		FacesContext  faceContext=FacesContext.getCurrentInstance();
		HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
		HttpSession session = httpServletRequest.getSession();

		if(session.getAttribute("saleComment") != null){
			comment = session.getAttribute("saleComment").toString();
			score = Integer.parseInt(session.getAttribute("saleScore").toString());
		}
	}

	public void complete(ActionEvent actionEvent){
		boolean logued = false;
		boolean completed = false;
		target = "index.xhtml";
		boolean scored = false;

		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
				"Debes seleccionar un puntaje");

		if(score != 0){

			scored = true;

			FacesContext faceContext=  FacesContext.getCurrentInstance();
			HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
			HttpSession session = httpServletRequest.getSession();

			if(session.getAttribute("user_session") != null){
				logued = true;
				int user_id = Integer.parseInt(session.getAttribute("user_session").toString());

				Sale saleObj = checkUser(user_id,sale);

				if(saleObj != null){
					if(saleObj.getScore() == 0){
						updateSale(saleObj,comment,score);

						msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito",
								"Se ha completado correctamente la acci�n");
						completed = true;
					}else{
						msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
								"Ya has comentado este pedido");
					}

				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Tu no has realizado este pedido!");
				}

			}else{

				httpServletRequest.getSession().setAttribute("saleComment", comment);
				httpServletRequest.getSession().setAttribute("saleScore", score);

				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
						"Debes loguearte para poder realizar la acción");
			}

		}

		FacesContext.getCurrentInstance().addMessage(null, msg);

		context.addCallbackParam("logued", logued);
		context.addCallbackParam("target", target);
		context.addCallbackParam("completed", completed);
		context.addCallbackParam("scored", scored);

	}

	public String redirect(){
		System.out.println("REDIRECTING TO: "+target);
		return target;
	}

	private Sale checkUser(int userId,int saleId){
		Session session = getSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Sale where id = :saleId and user_id = :userId");
			query.setParameter("saleId", saleId);
			query.setParameter("userId", userId);

			Sale sale = (Sale) query.uniqueResult();
			session.getTransaction().commit();
			return sale;

		}catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}

	private void updateSale(Sale sale,String comment,int score){
		Session session = getSession();
		session.beginTransaction();
		sale.setComment(comment);
		sale.setScore(score);
		session.merge(sale);
		session.getTransaction().commit();
	}

	private Session getSession(){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

}
