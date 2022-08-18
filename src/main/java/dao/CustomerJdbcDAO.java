/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import helpers.ScryptHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String sql = "insert into Customer (Username, "
                + "Password, Email_Address, Reminder_Threshold) "
                + "values (?,?,?,?)";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, ScryptHelper.hash(customer.getPassword()).toString());
            stmt.setString(3, customer.getEmailAddress());
            stmt.setInt(4, customer.getReminderThreshold());

            stmt.executeUpdate();
            System.out.println("Saving customer: " + customer);
        } catch (SQLException ex) {
            throw new DAOException(ex.getSQLState(), ex);
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
                Integer id = rs.getInt("Customer_ID");
                String fetchedUsername = rs.getString("Username");
                String password = rs.getString("Password");
                String emailAddress = rs.getString("Email_Address");
                Integer reminderThreshold = rs.getInt("Reminder_Threshold");

                Customer cust1 = new Customer(id, fetchedUsername, password, emailAddress, reminderThreshold);
                return cust1;
            }
            return null;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        String sql = "select Password from Customer where Username = ?";
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("password");

                // check that the password matches the hash
                return ScryptHelper.check(hash, password);
//                return true;
            } else {
                // no matching username
                return false;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        String sql1 = "delete from Subscription where Customer_ID = ?";
        String sql2 = "delete from Customer where Username = ?";
        try (
                // get a connection to the database
                Connection dbCon = DbConnection.getConnection(url);
                // create the statement for deleting associated subscriptions
                PreparedStatement stmt1 = dbCon.prepareStatement(sql1);
                // create the statement for deleting account
                PreparedStatement stmt2 = dbCon.prepareStatement(sql2);) {
            stmt1.setInt(1, customer.getCustomerId());
            stmt1.executeUpdate();
            
            stmt2.setString(1, customer.getUsername());
            stmt2.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateCustomer(String username, Customer customer) {
        String sql = "update Customer "
                + "set Username = ?, Email_Address = ?, Reminder_Threshold = ? "
                + "where Customer_ID = ?";
        try (
                // get a connection to the database
                Connection dbCon = DbConnection.getConnection(url); // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) { 
//            stmt.setString(3, ScryptHelper.hash(customer.getPassword()).toString());
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getEmailAddress());
            stmt.setInt(3, customer.getReminderThreshold());
            stmt.setInt(4, customer.getCustomerId());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            throw new DAOException(ex.getSQLState(), ex);
        }
    }
}
