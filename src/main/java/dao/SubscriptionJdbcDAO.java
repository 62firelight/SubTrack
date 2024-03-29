/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import domain.Subscription;
import domain.Total;
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
        String sql = "insert into subscription (Name, Paid, Category,"
                + " Subscription_Price, Description, Company_Name, Due_Date, "
                + "Issue_Date, Customer_ID) "
                + "values (?,?,?,?,?,?,?,?,?)";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, subscription.getName());
            stmt.setBoolean(2, subscription.getPaid());
            stmt.setString(3, subscription.getCategory());
            stmt.setBigDecimal(4, subscription.getSubscriptionPrice());
            stmt.setString(5, subscription.getDescription());
            stmt.setString(6, subscription.getCompanyName());
            stmt.setString(7, subscription.getDueDate().toString());
            stmt.setString(8, subscription.getDueDate().toString());
            stmt.setInt(9, subscription.getCustomer().getCustomerId());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Subscription> getSubscriptions() {
        String sql = "select * from Subscription inner join Customer "
                + "using (Customer_ID) order by due_date asc";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
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
//                customer.setFirstName(rs.getString("Firstname"));
//                customer.setLastName(rs.getString("Lastname"));
                customer.setPassword(rs.getString("Password"));
//                customer.setPhoneNumber(rs.getString("Phone_Number"));
//                customer.setEmailAddress(rs.getString("Email_Address"));
                customer.setReminderThreshold(rs.getInt("Reminder_Threshold"));

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
            }
            return subs;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Subscription> getSubscriptionsByUsername(String username) {
        String sql = "select * from Subscription "
                + "inner join Customer using (Customer_ID) "
                + "where Username = ? order by due_date asc";

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
//                customer.setFirstName(rs.getString("Firstname"));
//                customer.setLastName(rs.getString("Lastname"));
                customer.setPassword(rs.getString("Password"));
//                customer.setPhoneNumber(rs.getString("Phone_Number"));
//                customer.setEmailAddress(rs.getString("Email_Address"));
                customer.setReminderThreshold(rs.getInt("Reminder_Threshold"));

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
    }

    @Override
    public Subscription getSubscriptionById(Integer id) {
        String sql = "select * from Subscription "
                + "inner join Customer using (Customer_ID) "
                + "where Subscription_ID = ?";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // Reconstruct a subscription object
            List<Subscription> subs = new ArrayList<>();

            if (rs.next()) {
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
//                customer.setFirstName(rs.getString("Firstname"));
//                customer.setLastName(rs.getString("Lastname"));
                customer.setPassword(rs.getString("Password"));
//                customer.setPhoneNumber(rs.getString("Phone_Number"));
//                customer.setEmailAddress(rs.getString("Email_Address"));
                customer.setReminderThreshold(rs.getInt("Reminder_Threshold"));

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

                return sub;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteSubscription(Subscription subscription) {
        String sql = "delete from subscription where Subscription_ID = ?";
        try (
                // get a connection to the database
                Connection dbCon = DbConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setInt(1, subscription.getSubscriptionId());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        String sql = "update Subscription "
                + "set Name = ?, Paid = ?, Category = ?, Subscription_Price = ?,"
                + "Description = ?, Company_Name = ?, Due_Date = ?, Issue_Date = ?"
                + "where Subscription_ID = ?";
        try (
                // get a connection to the database
                Connection dbCon = DbConnection.getConnection(url); // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, subscription.getName());
            stmt.setBoolean(2, subscription.getPaid());
            stmt.setString(3, subscription.getCategory());
            stmt.setBigDecimal(4, subscription.getSubscriptionPrice());
            stmt.setString(5, subscription.getDescription());
            stmt.setString(6, subscription.getCompanyName());
            stmt.setString(7, subscription.getDueDate().toString());
            stmt.setString(8, subscription.getIssueDate().toString());
            stmt.setInt(9, subscription.getSubscriptionId());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<String> getCategories(String username) {
        String sql = "select distinct Category from Subscription "
                + "inner join Customer using (Customer_ID) where username = ?";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            List<String> collection = new ArrayList<>();

            while (rs.next()) {
                String cat = rs.getString("Category");
                collection.add(cat);
            }
            return collection;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Subscription> filterByCategory(String category, String username) {
        String sql = "select * from Subscription"
                + " inner join Customer using (Customer_ID)"
                + "where category = ? and username = ?";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, category);
            stmt.setString(2, username);
            //stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            List<Subscription> subs = new ArrayList<>();

            while (rs.next()) {
                Integer id = rs.getInt("Subscription_ID");
                String name = rs.getString("Name");
                Boolean paid = rs.getBoolean("Paid");
                String cat = rs.getString("Category");
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
//                customer.setFirstName(rs.getString("Firstname"));
//                customer.setLastName(rs.getString("Lastname"));
                customer.setPassword(rs.getString("Password"));
//                customer.setPhoneNumber(rs.getString("Phone_Number"));
//                customer.setEmailAddress(rs.getString("Email_Address"));
                customer.setReminderThreshold(rs.getInt("Reminder_Threshold"));

                Subscription sub = new Subscription();
                sub.setSubscriptionId(id);
                sub.setName(name);
                sub.setPaid(paid);
                sub.setCategory(cat);
                sub.setSubscriptionPrice(subPrice);
                sub.setCompanyName(companyName);
                sub.setDescription(description);
                sub.setIssueDate(issueDate.toString());
                sub.setDueDate(dueDate.toString());
                sub.setCustomer(customer);

                subs.add(sub);
            }
            return subs;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Total getTotal(String username) {
        String sql = "SELECT subscription_price FROM SUBSCRIPTION "
                + "inner join Customer using (customer_ID) where username = ?";
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            BigDecimal totalValue = new BigDecimal(0);

            while (rs.next()) {
                BigDecimal subPrice = rs.getBigDecimal("Subscription_Price");
                totalValue = totalValue.add(subPrice);
            }

            Total total = new Total(totalValue);

            return total;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public Total getTotalForCategory(String category, String username) {
        String sql = "SELECT subscription_price FROM SUBSCRIPTION "
                + "inner join Customer using (customer_ID) where category = ? "
                + " and username = ?";
        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, category);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();

            BigDecimal totalValue = new BigDecimal(0);

            while (rs.next()) {
                BigDecimal subPrice = rs.getBigDecimal("Subscription_Price");
                totalValue = totalValue.add(subPrice);
            }

            Total total = new Total(totalValue);

            return total;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Subscription> sortAscending(String username) {
        String sql = "select * from Subscription inner join Customer using (Customer_ID) where username = ?"
                + "order by due_date asc";

        try (
                Connection dbCon = DbConnection.getConnection(url);
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
            //stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            List<Subscription> subs = new ArrayList<>();

            while (rs.next()) {
                Integer id = rs.getInt("Subscription_ID");
                String name = rs.getString("Name");
                Boolean paid = rs.getBoolean("Paid");
                String cat = rs.getString("Category");
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
//                customer.setFirstName(rs.getString("Firstname"));
//                customer.setLastName(rs.getString("Lastname"));
                customer.setPassword(rs.getString("Password"));
//                customer.setPhoneNumber(rs.getString("Phone_Number"));
//                customer.setEmailAddress(rs.getString("Email_Address"));
                customer.setReminderThreshold(rs.getInt("Reminder_Threshold"));

                Subscription sub = new Subscription();
                sub.setSubscriptionId(id);
                sub.setName(name);
                sub.setPaid(paid);
                sub.setCategory(cat);
                sub.setSubscriptionPrice(subPrice);
                sub.setCompanyName(companyName);
                sub.setDescription(description);
                sub.setIssueDate(issueDate.toString());
                sub.setDueDate(dueDate.toString());
                sub.setCustomer(customer);

                subs.add(sub);
            }
            return subs;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

}
