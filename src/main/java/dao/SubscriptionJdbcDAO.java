/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Subscription;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            stmt.setString(8, subscription.getDueDate().toLocalDate().toString());
            stmt.setString(9, subscription.getIssueDate().toLocalDate().toString());
            stmt.setInt(10,subscription.getCustomer().getCustomerId()); //Unsure od setObject
            

            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Subscription> getSubscriptionsByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
