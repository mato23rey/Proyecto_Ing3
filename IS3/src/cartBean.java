import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import cart.CartItem;
import hibernate.Product;

public class cartBean {
	List<CartItem> cart;
	float total;

	String address,coors;
	
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

	public void buttonAction(ActionEvent actionEvent) {
		System.out.println("ADD ITEM!!");

	}

	public void addItem(ActionEvent event){
		System.out.println("ADD ITEM");
		Product p = (Product)event.getComponent().getAttributes().get("item");

		CartItem cI = new CartItem();
		cI.setProduct(p);
		cI.setCant(2);
		cart.add(cI);
		calculateTotal();
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

		boolean isLogued = (boolean) event.getComponent().getAttributes().get("logued");
		if(isLogued){
			if(cart.size() != 0){
				clearCart();
				System.out.println("Pedido realizado");
			}else{
				msg  = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debes loguearte primero");
			}

		}else{
			msg  = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debes loguearte primero");
		}
		FacesContext.getCurrentInstance().addMessage("orderMessages", msg);

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



}
