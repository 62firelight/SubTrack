/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/*import dao.CustomerCollectionsDAO;
import dao.CustomerDAO;
import dao.CustomerJdbcDAO;
import dao.ProductDAO;
import dao.ProductJdbcDAO;
import dao.SaleDAO;
import dao.SaleJdbcDAO;*/
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author yeah2
 */
public class Server extends Jooby{
   /* ProductDAO productDao = new ProductJdbcDAO();
    CustomerDAO customerDao = new CustomerJdbcDAO();
    SaleDAO saleDao = new SaleJdbcDAO();*/
    public Server(){
        port(8081);
        use(new Gzon());
        use(new AssetModule());
       /* use(new ProductModule(productDao));
        use(new CustomerModule(customerDao));
        
        use (new SaleModule(saleDao));*/
    }
    public static void main(String[] args) throws Exception {
   System.out.println("\nStarting Server.");

   Server server = new Server();

   CompletableFuture.runAsync(() -> {
      server.start();
   });

   server.onStarted(() -> {
      System.out.println("\nPress Enter to stop the server.");
   });

   // wait for user to hit the Enter key
   System.in.read();
   System.exit(0);
}
}
