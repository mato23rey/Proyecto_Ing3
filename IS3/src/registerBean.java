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

public class registerBean {

	String email,pass,passVer;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPassVer() {
		return passVer;
	}

	public void setPassVer(String passVer) {
		this.passVer = passVer;
	}

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
					
					String message = "Bienvenid@ "+email+"<br>"+"Para validar la cuenta sigue el siguiente link: http://localhost:8080/IS3/faces/validate.xhtml?code="+validationCode+"&user="+email;
					
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

	private String generateValidationCode(){
		String code = "";

		String chars = "qwertyuiopasdfghjklzxcvbnm1234567890";

		for(int i = 0;i<20;i++){

			Random r = new Random();
			int pos = r.nextInt(chars.length());

			code += chars.substring(pos,pos+1);
		}

		return code;
	}

}
