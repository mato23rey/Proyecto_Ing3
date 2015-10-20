//<<<<<<< HEAD
//import java.util.Random;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent;
//
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.primefaces.context.RequestContext;
//
//import email.Emailer;
//import hibernate.User;
//import security.MD5Generator;
//
///**
// * @author Seba
// *Clase que se utiliza para manejar el registro de la aplicaciÃ³n
// */
//public class registerBean {
//
//	/**
//	 * Email del usuario a registrar
//	 */
//	/**
//	 * Password del usuario a registrar
//	 */
//	/**
//	 * Verificaciï¿½n de la password
//	 */
//	String email,pass,passVer;
//
//	/**
//	 * @return Email del usuario a registrar
//	 */
//	public String getEmail() {
//		return email;
//	}
//
//	/**
//	 * @param email Email del usuario a registrar
//	 */
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	/**
//	 * @return Password del usuario a registrar
//	 */
//	public String getPass() {
//		return pass;
//	}
//
//	/**
//	 * @param pass Password del usuario a registrar
//	 */
//	public void setPass(String pass) {
//		this.pass = pass;
//	}
//
//	/**
//	 * @return Verificaciï¿½n de la password
//	 */
//	public String getPassVer() {
//		return passVer;
//	}
//
//	/**
//	 * @param passVer Verificaciï¿½n de la password
//	 */
//	public void setPassVer(String passVer) {
//		this.passVer = passVer;
//	}
//
//	/**
//	 * MÃ©todo de acceso para realizar el registro. Si el email no se encuentra registrado entonces se genera una nueva cuenta.
//	 * @param actionEvent Evento
//	 */
//	public void register(ActionEvent actionEvent){
//
//		if(email != null && pass != null && passVer != null){
//			FacesMessage msg = null;
//
//			if(pass.equals(passVer)){
//				String codedPass = MD5Generator.generate(pass);
//
//				String validationCode = generateValidationCode();
//
//				if(!checkIfExists(email)){
//					User user = new User();
//					user.setEmail(email);
//					user.setPassword(codedPass);
//					user.setActivated(false);
//					user.setValidationCode(validationCode);
//
//					SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//					Session session = sessionFactory.getCurrentSession();
//
//					try {
//						session.beginTransaction();
//						session.save(user);
//						session.getTransaction().commit();
//					}catch (HibernateException e) {
//						e.printStackTrace();
//						session.getTransaction().rollback();
//
//					}
//					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro",
//							"Se ha enviado un email de confirmacion a "+email);
//					RequestContext context = RequestContext.getCurrentInstance();
//					context.addCallbackParam("register", true);
//					context.addCallbackParam("target", "index.xhtml");
//
//					String message = "Bienvenid@ "+email+"<br>"+"Para validar la cuenta sigue el siguiente link: http://localhost:8080/IS3/faces/validate.xhtml?code="+validationCode+"&user="+email;
//
//					Emailer.send(email,"Bienvenido",message);
//				}else{
//					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
//							"El email ya esta registrado");
//				}
//			}else{
//				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
//						"Las contraseï¿½as no coinciden");
//			}
//			FacesContext.getCurrentInstance().addMessage(null, msg);
//		}
//	}
//
//	/**MÃ©todo encargado de la bÃºsqueda de un usuario dado un email
//	 * @param email Email del usuario a buscar
//	 * @return True si el usuario existe o False si no existe
//	 */
//	private boolean checkIfExists(String email){
//		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
//
//		try {
//			session.beginTransaction();
//			Query query = session.createQuery("from User where email = :email ");
//			query.setParameter("email", email);
//			User user = (User) query.uniqueResult();
//			session.getTransaction().commit();
//			return user != null;
//
//		}catch (Exception e) {
//			e.printStackTrace();
//			session.getTransaction().rollback();
//			return true;
//		}
//
//	}
//
//	/**MÃ©todo encargado de generar un cï¿½digo de 44 caracteres que se utilizarÃ¡ en la validaciÃ³n de cuentas
//	 * @return CÃ³digo de validaciÃ³n para la nueva cuenta
//	 */
//	private String generateValidationCode(){
//		String code = "";
//
//		String chars = "qwertyuiopasdfghjklzxcvbnm1234567890";
//
//		for(int i = 0;i<44;i++){
//
//			Random r = new Random();
//			int pos = r.nextInt(chars.length());
//
//			code += chars.substring(pos,pos+1);
//		}
//
//		return code;
//	}
//
//}
//=======
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.context.RequestContext;

import email.Emailer;
import hibernate.User;
import security.MD5Generator;

/**
 * @author Seba
 *Clase que se utiliza para manejar el registro de la aplicación
 */
public class registerBean {

	/**
	 * Email del usuario a registrar
	 */
	/**
	 * Password del usuario a registrar
	 */
	/**
	 * Verificación de la password
	 */
	String email,pass,passVer;

	/**
	 * @return Email del usuario a registrar
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email Email del usuario a registrar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Password del usuario a registrar
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass Password del usuario a registrar
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return Verificación de la password
	 */
	public String getPassVer() {
		return passVer;
	}

	/**
	 * @param passVer Verificación de la password
	 */
	public void setPassVer(String passVer) {
		this.passVer = passVer;
	}

	/**
	 * Método de acceso para realizar el registro. Si el email no se encuentra registrado entonces se genera una nueva cuenta.
	 * @param actionEvent Evento
	 */
	public void register(ActionEvent actionEvent){

		if(email != null && pass != null && passVer != null){
			FacesMessage msg = null;

			if(pass.equals(passVer)){
				String codedPass = MD5Generator.generate(pass);

				String validationCode = generateValidationCode();

				if(!checkIfExists(email)){
					User user = new User();
					user.setEmail(email);
					user.setPassword(codedPass);
					user.setActivated(false);
					user.setValidationCode(validationCode);

					SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
					Session session = sessionFactory.getCurrentSession();

					try {
						session.beginTransaction();
						session.save(user);
						session.getTransaction().commit();
					}catch (HibernateException e) {
						e.printStackTrace();
						session.getTransaction().rollback();

					}
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro",
							"Se ha enviado un email de confirmacion a "+email);
					RequestContext context = RequestContext.getCurrentInstance();
					context.addCallbackParam("register", true);
					context.addCallbackParam("target", "index.xhtml");

					String message = "Bienvenid@ "+email+"<br>"+"Para validar la cuenta sigue el siguiente link: http://ec2-54-173-155-213.compute-1.amazonaws.com:8080/farmaya/faces/validate.xhtml?code="+validationCode+"&user="+email;

					Emailer.send(email,"Bienvenido",message);
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
							"El email ya esta registrado");
				}
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
						"Las contraseñas no coinciden");
			}
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	/**Método encargado de la búsqueda de un usuario dado un email
	 * @param email Email del usuario a buscar
	 * @return True si el usuario existe o False si no existe
	 */
	private boolean checkIfExists(String email){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery("from User where email = :email ");
			query.setParameter("email", email);
			User user = (User) query.uniqueResult();
			session.getTransaction().commit();
			return user != null;

		}catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return true;
		}

	}

	/**Método encargado de generar un código de 44 caracteres que se utilizará en la validación de cuentas
	 * @return Código de validación para la nueva cuenta
	 */
	private String generateValidationCode(){
		String code = "";

		String chars = "qwertyuiopasdfghjklzxcvbnm1234567890";

		for(int i = 0;i<44;i++){

			Random r = new Random();
			int pos = r.nextInt(chars.length());

			code += chars.substring(pos,pos+1);
		}

		return code;
	}

}
//>>>>>>> seba
