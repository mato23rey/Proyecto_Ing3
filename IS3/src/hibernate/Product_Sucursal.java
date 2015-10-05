package hibernate;

import java.io.Serializable;

public class Product_Sucursal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	int product_id,sucursal_id;

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getSucursal_id() {
		return sucursal_id;
	}

	public void setSucursal_id(int sucursal_id) {
		this.sucursal_id = sucursal_id;
	}
	
	
	
}
