/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import domain.Subscription;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

///**
// *
// * @author trbay
// */
public class SubscriptionJdbcDAO implements SubscriptionDAO {

    
    private String url = "jdbc:h2:tcp://localhost/info310proj";

    public SubscriptionJdbcDAO() {
    }

    public SubscriptionJdbcDAO(String url) {
        this.url = url;
    }
    @Override
    public void saveSubscription(Subscription subscription) {
   String sql = "merge into subscription (Subscription_ID, Name, Paid, Category,"
           + " Subscription_Price, Description, Company_Name, Due_Date, "
           + "Issue_Date, Customer_ID) "
           + "values (?,?,?,?,?,?,?,?,?,?)";

  
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, subscription.getSubscriptionId().toString()); //could use set INTEGER and remove toString
            stmt.setString(2, subscription.getName());
            stmt.setBoolean(3, subscription.getPaid());
            stmt.setString(4, subscription.getCategory());
            stmt.setBigDecimal(5, subscription.getSubscriptionPrice());
            stmt.setString(6, subscription.getDescription());
            stmt.setString(7, subscription.getCompanyName());
            stmt.setString(8, subscription.getDueDate().toString());
            stmt.setString(9, subscription.getIssueDate().toString());
            stmt.setInt(10,subscription.getCustomer().getCustomerId()); //Unsure od setObject
            

            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Subscription> getSubscriptionsByUsername(String username) {
         String sql = "select * from Subscription "
                 + "inner join Customer using (Customer_ID) "
                 + "where Username = ?";
         
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            // Using a List to preserve the order in which the data was returned 
            // from the query.
            List<Subscription> subs = new ArrayList<>();

            while (rs.next()) {
                Integer id = rs.getInt("Subscription_ID");
                String name = rs.getString("Name");
                Boolean paid = rs.getBoolean("Paid");
                String category = rs.getString("Category");
                BigDecimal subPrice = rs.getBigDecimal("Subscription_Price");
                String companyName = rs.getString("Company_Name");
                String description = rs.getString("Description");
                Date x = rs.getDate("Issue_Date");
                LocalDate issueDate = x.toLocalDate(); // conversion line
                Date y = rs.getDate("Due_Date");
                LocalDate dueDate = y.toLocalDate(); //conversion line
                
                // construct a customer using details
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("Customer_ID"));
                customer.setUsername(rs.getString("Username"));
                customer.setFirstName(rs.getString("Firstname"));
                customer.setLastName(rs.getString("Lastname"));
                customer.setPassword(rs.getString("Password"));
                customer.setPhoneNumber(rs.getString("Phone_Number"));
                customer.setEmailAddress(rs.getString("Email_Address"));
                
                //commented out to keep file integrity 
                Subscription sub = new Subscription();
                sub.setSubscriptionId(id);
                sub.setName(name);
                sub.setPaid(paid);
                sub.setCategory(category);
                sub.setSubscriptionPrice(subPrice);
                sub.setCompanyName(companyName);
                sub.setDescription(description);
                sub.setIssueDate(issueDate.toString());
                sub.setDueDate(dueDate.toString());
                sub.setCustomer(customer);
                
                subs.add(sub);
               // return sub1;
            }
            return subs;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSubscription(Subscription subscription) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "delete from subscription where Subscription_ID = ?";
        try(
            // get a connection to the database
            Connection dbCon = DbConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            stmt.setInt(1, subscription.getSubscriptionId());
            stmt.executeUpdate();  // execute the statement
            
        }catch(SQLException ex){
            throw new DAOException(ex.getMessage(), ex);
        }
    
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        saveSubscription(subscription); // essentially performs an update
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
