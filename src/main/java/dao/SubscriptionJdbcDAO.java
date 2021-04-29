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
import java.util.Collection;

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
   String sql = "merge into subscription (SubscriptionID, Name, Paid, Category, SubscriptionPrice, Description, CompanyName, DueDate, IssueDate, CustomerID) values (?,?,?,?,?,?,?,?,?,?)";

  
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
         String sql = "select * from Subscription where CustomerID = ?";
         
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("SubscriptionId");
                String name = rs.getString("Name");
                Boolean paid = rs.getBoolean("Paid");
                String category = rs.getString("Category");
                BigDecimal subPrice = rs.getBigDecimal("SubscriptionPrice");
                String companyName = rs.getString("CompanyName");
                String description = rs.getString("Description");
                Date x = rs.getDate("IssueDate");
                LocalDate issueDate = x.toLocalDate(); // conversion line
                Date y = rs.getDate("DueDate");
                LocalDate DueDate = y.toLocalDate(); //conversion line
                Integer customerId = rs.getInt("CustomerId");
                //commented out to keep file integrity 
                //Subscription sub1 = new Subscription(id, name, paid, category, subPrice, companyName, description, issueDate, dueDate, customerId);
               // return sub1;
            }
            return null;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSubscription(Subscription subscription) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "delete from subscription where SubscriptionID = ?";
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
