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

/**
 * @author Seba
 *
 */
public class validationBean {

	/**
	 * Codigo de verificaci�n de la cuenta a validar
	 */
	/**
	 * Email de la cuenta a validar
	 */
	String validationCode,email;

	boolean validated = false;

	/**
	 * @return Email de la cuenta a validar
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email Email de la cuenta a validar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Codigo de verificaci�n de la cuenta a validar
	 */
	public String getValidationCode() {
		return validationCode;
	}

	/**
	 * @param validationCode Codigo de verificaci�n de la cuenta a validar
	 */
	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	/**
	 * M�todo que indica si una cuenta esta validada o no
	 * @return Estado de validaci�n de la cuenta
	 */
	public boolean isValidated() {
		return validated;
	}


	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	/**
	 * M�todo de acceso para realizar la validaci�n
	 * @param actionEvent Evento del sistema
	 */
	public void validate(ActionEvent actionEvent){
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;

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
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "EXITO",
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


	/**
	 * M�todo encargado de la validaci�n de la cuenta
	 * 
	 * @param email Email de la cuenta a validar
	 * @param code C�digo de verificaci�n de la cuenta a validar
	 * @return Usuario que coincida con el email y el c�digo de validaci�n
	 */
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
