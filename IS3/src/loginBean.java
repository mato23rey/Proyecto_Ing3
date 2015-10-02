import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.context.RequestContext;
import hibernate.User;
import security.MD5Generator;

/**
 * @author Seba
 *	Clase que se utiliza para manejar el sistema de login de la aplicación
 */
public class loginBean {

	String redirectUrl;

	/**
	 * ID del usuario que se encuentra logueado, -1 para indicar que no hay nadie logueado. Sirve para renderizar correctamente la página
	 */
	int userId = -1;

	/**
	 * Email del usuario que desea loguearse
	 */
	/**
	 * Passsword del usuario que desea loguarse
	 */
	String email,pass;

	/**
	 * Variable de control para uso de JS
	 */
	boolean logueado = false;

	/**
	 * @return ID del usuario logueado
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @return Email del usuario a loguear
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email Email del usuario a loguear
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Password del usuario a loguear
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass Password del usuario a loguear
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * Función que permite determinar si hay un usuario logueado o no. Sirve para renderizar correctamente la página principal
	 * @return Booleano indicando si existe un usuario logueado o no
	 */
	public boolean isLogued(){
		return userId != -1;
	}


	private final HttpServletRequest httpServletRequest;
	private final FacesContext faceContext;

	/**
	 * Constructor de la clase. Se establece el valor de sesión del usuario
	 */
	public loginBean(){
		faceContext=FacesContext.getCurrentInstance();
		httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
		if(httpServletRequest.getSession().getAttribute("user_session")!=null){
			userId=Integer.parseInt(httpServletRequest.getSession().getAttribute("user_session").toString());
		}
	}

	/**
	 * Método de acceso para realizar el login
	 * @param actionEvent evento del sistema
	 */
	public void login(ActionEvent actionEvent) {
		logueado = false;

		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
				"Credenciales no válidas");

		if (email != null && pass != null) {
			User user = searchInDB(email);

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

	/**Método de acceso para realizar el logout
	 * @return String indicando a donde redireccionar la página
	 */
	public String logout(){
		System.out.println("LOGOUT");
		userId = -1;
		if(httpServletRequest.getSession() != null && httpServletRequest.getSession().getAttribute("user_session")!=null){
			httpServletRequest.getSession().removeAttribute("user_session");
		}

		return "index";
	}

	/**Método que sirve para encontrar un usuario en base a su dirección de email
	 * @param email Email del usuario
	 * @return Usuario en caso de encontrarse un usuario con el email ingresado
	 */
	private User searchInDB(String email){
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
