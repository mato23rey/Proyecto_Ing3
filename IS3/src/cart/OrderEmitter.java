package cart;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import hibernate.Product;
import hibernate.Sale;
import hibernate.Sale_Product;

public class OrderEmitter {

	List<Product> products;

	public static boolean emitOrder(String address,int user_id,List<CartItem> products){

		Sale sale = new Sale();
		sale.setAddress(address);
		sale.setDate(Date.from(Instant.now()));
		sale.setUser_id(user_id);

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();

		Transaction tx = session.beginTransaction();

		int saleId = Integer.parseInt(session.save(sale).toString());

		System.out.println("SALE ID: "+saleId);

		System.out.println("Sale saved successfully.....!!");

		tx.commit();

		session.close();

		for(CartItem cI : products){
			session = factory.openSession();
			Sale_Product sP = new Sale_Product();
			sP.setProduct_id(cI.getProduct().getId());
			sP.setSale_id(saleId);
			sP.setCant(cI.getCant());

			tx = session.beginTransaction();
			session.save(sP);
			System.out.println("Product saved successfully.....!!");
			tx.commit();
			session.close();
		}
		if(session.isOpen()){
			session.close();
		}
		factory.close();

		return true;
	}

}
