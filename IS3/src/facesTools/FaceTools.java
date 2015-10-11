package facesTools;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 * @author Seba
 * Clase auxiliar para obtener componentes faces.
 */
public class FaceTools {


	/**
	 * @return Request Context instance
	 */
	public static RequestContext getCurrentContext() {
		return  RequestContext.getCurrentInstance();
	}


	/**
	 * @return HttpServlerRequest request
	 */
	public static HttpSession getHttpServletRequestSession() {
		FacesContext faceContext = getFacesContext();
		HttpServletRequest httpServletRequest;
		Object request = faceContext.getExternalContext().getRequest();
		httpServletRequest = (HttpServletRequest) request;
		return httpServletRequest.getSession();
	}


	/**
	 * @return Faces Context instance
	 */
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
