/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.jdbc.JdbcConnection;
///**
// *
// * @author trbay
// */
public class CustomerJdbcDAO implements CustomerDAO {
     private String url = "jdbc:h2:tcp://localhost/info310proj";

    public CustomerJdbcDAO() {
    }

    public CustomerJdbcDAO(String url) {

        this.url = url;
    }

    @Override
    public void saveCustomer(Customer customer) {
        String sql = "insert into Customer (CustomerID, Username, Firstname, Lastname, Password, phoneNumber, Email_Address) values(?,?,?,?,?,?,?)";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            ResultSet rs = stmt.getGeneratedKeys();

            stmt.setString(1, rs.toString());
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getPhoneNumber());
            stmt.setString(6, customer.getEmailAddress());
            

            stmt.executeUpdate();
            System.out.println("Saving customer: " + customer);

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Customer getCustomer(String username) {
         String sql = "select * from Customer where Username = ?";
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("CustomerId");
                String user_name = rs.getString("Username");
                String firstname = rs.getString("Firstname");
                String lastname = rs.getString("Lastname");
                String password = rs.getString("Password");
                String phoneNumber = rs.getString("PhoneNumber");
                String emailAddress = rs.getString("EmailAddress");
           
                Customer cust1 = new Customer(id, user_name, firstname, lastname, password, phoneNumber, emailAddress);
                return cust1;
            }
            return null;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
         String sql = "select * from customer where Username = ? and Password = ?";
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
