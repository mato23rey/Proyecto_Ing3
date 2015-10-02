import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

import cart.CartItem;
import hibernate.Product;

public class cartBean {

	List<CartItem> cart;
	float total;

	public List<CartItem> getCart() {
		return cart;
	}

	public float getTotal() {
		return total;
	}

	public cartBean() {
		System.out.println("Creating cart!!!!!!!!!!!!!!!!!");
		cart = new ArrayList<CartItem>();
	}

	public void buttonAction(ActionEvent actionEvent) {
		System.out.println("ADD ITEM!!");

	}
	
	public void addItem(ActionEvent event){
		System.out.println("ADD ITEM");
		Product p = (Product)event.getComponent().getAttributes().get("item");
		System.out.println(p);
		System.out.println("NEW PRODUCT: "+p.getName());

	}



	public void add2Item(CartItem item){
		cart.add(item);
		calculateTotal();
	}

	public void removeItem(CartItem item){
		cart.remove(item);
		calculateTotal();
	}

	private void calculateTotal(){
		float total = 0;
		for(CartItem item : cart){
			total += item.getProductPrice()*item.getCant();
		}
		this.total = total;
	}

}
