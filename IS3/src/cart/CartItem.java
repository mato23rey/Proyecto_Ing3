package cart;

import hibernate.Product;

public class CartItem {

	Product product;
	int cant,index;

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductName(){
		return product.getName();
	}

	public float getProductPrice(){
		return product.getPrice();
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		this.cant = cant;
	}

	public float getTotal(){
		return product.getPrice()*cant;
	}

}
