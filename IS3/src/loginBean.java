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

import hibernate.User;
import security.MD5Generator;

public class loginBean {

	int userId = -1;

	String email,pass;

	boolean logueado = false;

	public int getUserId() {
		return userId;
	}

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
	
	public boolean isLogued(){
		return userId != -1;
	}

	private final HttpServletRequest httpServletRequest;
	private final FacesContext faceContext;

	public loginBean(){
		System.out.println("Started");
		faceContext=FacesContext.getCurrentInstance();
		httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
		if(httpServletRequest.getSession().getAttribute("user_session")!=null){
			userId=Integer.parseInt(httpServletRequest.getSession().getAttribute("user_session").toString());
		}
	}

	public void login(ActionEvent actionEvent) {

		logueado = false;

		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
				"Credenciales no válidas");

		if (email != null && pass != null) {
			User user = searchInDB(email,pass);

			if(user != null && user.getPassword().equals(MD5Generator.generate(pass))){
				if(user.isActivated()){
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", email);
					logueado = true;

					FacesContext  faceContext=FacesContext.getCurrentInstance();
					HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
					userId = user.getId();
					httpServletRequest.getSession().setAttribute("user_session", userId);
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debes activar tu cuenta primero!");
				}
			}else{
				System.out.println("El usuario no existe!");
			}
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("login", logueado);
		if (logueado){
			context.addCallbackParam("target", "index.xhtml");
		}
	}

	public String logout(){
		
		if(httpServletRequest.getSession().getAttribute("user_session")!=null){
			httpServletRequest.getSession().removeAttribute("user_session");
			userId = -1;
		}
		
		return "index";
	}

	private User searchInDB(String email,String pass){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery("from User where email = :email");
			query.setParameter("email", email);
			User user = (User) query.uniqueResult();
			session.getTransaction().commit();
			return user;

		}catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}

	}

}
