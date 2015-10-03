import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import cart.CartItem;
import hibernate.Product;

public class cartBean {
	List<CartItem> cart;
	int cant;
	float total;

	String address,coors;

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
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

	public void buttonAction(ActionEvent actionEvent) {
		System.out.println("ADD ITEM!!");

	}

	public void addItem(ActionEvent event){
		Product p = (Product)event.getComponent().getAttributes().get("item");

		CartItem cI = new CartItem();
		cI.setProduct(p);
		cI.setCant(cant);
		cart.add(cI);
		calculateTotal();
		cant = 0;
	}

	public void removeItem(ActionEvent event){
		System.out.println("REMOVE");
		CartItem item = (CartItem)event.getComponent().getAttributes().get("item");
		cart.remove(item);
		calculateTotal();
	}

	public void chooseProduct() {
		System.out.println("Choose");
		RequestContext.getCurrentInstance().openDialog("selectProduct");
	}

	public void onProductSelected(SelectEvent event) {
		//Car car = (Car) event.getObject();
		//FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Car Selected", "Id:" + car.getId());

		//FacesContext.getCurrentInstance().addMessage(null, message);
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
				msg  = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Carrito vacio");
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
