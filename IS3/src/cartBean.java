import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import cart.CartItem;
import cart.OrderEmitter;
import hibernate.Product;

public class cartBean {
	List<CartItem> cart;
	int cant = 1;
	float total;

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		System.out.println("new cant");
		this.cant = cant;
	}

	public List<CartItem> getCart() {
		return cart;
	}

	public float getTotal() {
		return total;
	}

	public cartBean() {
		System.out.println("Creating cart");
		cart = new ArrayList<CartItem>();
	}

	public void addItem(ActionEvent event){
		Product p = (Product)event.getComponent().getAttributes().get("item");

		CartItem check = checkItem(p);
		if(check != null){
			int newCant = check.getCant()+cant;
			check.setCant(newCant);
		}else{

			CartItem cI = new CartItem();
			cI.setProduct(p);
			cI.setCant(cant);
			cart.add(cI);
		}
		calculateTotal();
		cant = 1;
	}

	public void removeItem(ActionEvent event){
		System.out.println("REMOVE");
		CartItem item = (CartItem)event.getComponent().getAttributes().get("item");
		cart.remove(item);
		calculateTotal();
	}

	public void requestOrder(ActionEvent event){
		System.out.println("ASKING");
		FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Pedido realizado");
		boolean requested = false;

		boolean isLogued = (boolean) event.getComponent().getAttributes().get("logued");
		if(isLogued){
			if(cart.size() != 0){

				FacesContext faceContext=  FacesContext.getCurrentInstance();
				HttpServletRequest httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
				HttpSession session = httpServletRequest.getSession();

				String address = session.getAttribute("search_address").toString();
				int user_id = Integer.parseInt(httpServletRequest.getSession().getAttribute("user_session").toString());

				if(OrderEmitter.emitOrder(address, user_id, cart)){
					cart = new ArrayList<CartItem>();
					requested = true;
				}

			}else{
				msg  = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Carrito vacio");
			}

		}else{
			msg  = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debes loguearte primero");
		}
		FacesContext.getCurrentInstance().addMessage("orderMessages", msg);
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("requested", requested);


	}

	public void cancelOrder(ActionEvent event){
		System.out.println("CANCEL");
		clearCart();
	}

	private void clearCart(){
		cart.clear();
		total = 0;
	}

	private void calculateTotal(){
		float total = 0;
		for(CartItem item : cart){
			total += item.getProductPrice()*item.getCant();
		}
		this.total = total;
	}

	private CartItem checkItem(Product p){
		for(CartItem cI : cart){
			if(cI.getProduct().getId() == p.getId()){
				return cI;
			}
		}
		return null;
	}

}
