import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.primefaces.context.RequestContext;

import hibernate.User;

public class validationBean {

	String validationCode,email;
	boolean validated = false;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public void validate(ActionEvent actionEvent){
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;

		System.out.println("Validating: "+email+" "+validationCode);

		if(validationCode != null && email != null){
			User user = validateAccount(email,validationCode);
			if(user != null){
				//Validacion exitosa
				Session session = getSession();
				Transaction t = session.beginTransaction();
				user.setActivated(true);
				session.merge(user);
				session.flush();
				t.commit();
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error",
						"Cuenta validada!");
				validated = true;
			}else{
				//Display Error
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
						"Error en la validacion");
			}

			context.addCallbackParam("target", "index.xhtml");
			context.addCallbackParam("validated", validated);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}


	private User validateAccount(String email,String code){
		Session session = getSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from User where email = :email and validationCode = :valCode");
			query.setParameter("email", email);
			query.setParameter("valCode", code);
			User user = (User) query.uniqueResult();
			session.getTransaction().commit();
			return user;

		}catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}

	private Session getSession(){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

}
