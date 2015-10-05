package hibernate;

import java.io.Serializable;

public class Sale_Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int sale_id,product_id,cant;

	public int getSale_id() {
		return sale_id;
	}

	public void setSale_id(int sale_id) {
		this.sale_id = sale_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		this.cant = cant;
	}


}
