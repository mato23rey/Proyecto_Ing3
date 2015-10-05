package cart;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import email.Emailer;
import hibernate.Product;
import hibernate.Sale;
import hibernate.Sale_Product;

public class OrderEmitter {

	List<Product> products;

	public static boolean emitOrder(String address,int user_id,List<CartItem> products,float total,String userEmail){

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

		tx.commit();

		session.close();

		String emailContent = "Se ha generado un pedido con identificador <b>#"+saleId + "</b><br><br>";
		emailContent += "Has solicitado los siguientes productos: <br><br>";

		for(CartItem cI : products){
			emailContent += cI.getProductName()+" ($"+cI.getProductPrice()+") "+" x "+cI.getCant()+"<br>";
			session = factory.openSession();
			Sale_Product sP = new Sale_Product();
			sP.setProduct_id(cI.getProduct().getId());
			sP.setSale_id(saleId);
			sP.setCant(cI.getCant());

			tx = session.beginTransaction();
			session.save(sP);
			tx.commit();
			session.close();
		}

		emailContent += "<b>TOTAL: $"+total+"<b>";

		emailContent += "<br><br><br>Una vez que recibas el pedido por favor sigue el siguiente link para dejar tus comentarios sobre el servicio:<br><br>";
		emailContent += "http://localhost:8080/IS3/faces/saleComplete.xhtml?sale="+saleId;

		Emailer.send(userEmail, "Pedido realizado", emailContent);

		if(session.isOpen()){
			session.close();
		}
		factory.close();

		return true;
	}

}
