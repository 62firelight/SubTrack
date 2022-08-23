/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/*import dao.CustomerCollectionsDAO;
import dao.CustomerDAO;
import dao.ProductDAO;
import dao.ProductJdbcDAO;
import dao.SaleDAO;
import dao.SaleJdbcDAO;*/
import dao.CustomerJdbcDAO;
import dao.CustomerDAO;
import dao.SubscriptionDAO;
import dao.SubscriptionJdbcDAO;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.json.GsonModule;
import io.jooby.quartz.QuartzModule;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import web.auth.BasicHttpAuthenticator;

/**
 *
 * @author yeah2
 */
public class Server extends Jooby {

    // use H2 database server (default)
    CustomerDAO customerDao = new CustomerJdbcDAO();
    SubscriptionDAO subscriptionDao = new SubscriptionJdbcDAO();

    // use embedded database file (SubTrack.mv.db in project root directory)
//    CustomerDAO customerDao = new CustomerJdbcDAO("jdbc:h2:./SubTrackDatabase");
//    SubscriptionDAO subscriptionDao = new SubscriptionJdbcDAO("jdbc:h2:./SubTrackDatabase");
    
    public Server() {
        setServerOptions(new ServerOptions().setPort(8081));
        mount(new AssetModule());
        install(new GsonModule());
        
        Scheduler scheduler = QuartzModule.newScheduler(this);     
        install(new QuartzModule(CheckReminderJob.class));
        try {
            scheduler.setJobFactory(new ReminderJobFactory(subscriptionDao));
        } catch (SchedulerException e) {
            System.out.println("Failed to set job factory. Exiting...");
            System.exit(1);
        }
        
        mount(new CustomerModule(customerDao));
        mount(new SubscriptionModule(subscriptionDao));
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\nStarting Server.");

        new Server().start();
        System.out.println();

//        CompletableFuture.runAsync(() -> {
//            server.start();
//        });
//
//        server.onStarted(() -> {
//            System.out.println("\nPress Enter to stop the server.");
//        });
        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
    }
}
