/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerDAO;
import dao.DAOException;
import domain.Customer;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/**
 *
 * @author yeah2
 */
public class CustomerModule extends Jooby {

    public CustomerModule(CustomerDAO customerDao) {

        get("/api/customers/{username}", ctx -> {
            String username = ctx.path("username").value();
            if (customerDao.getCustomer(username) == null) {
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return customerDao.getCustomer(username);
            }
        });

        post("/api/register", ctx -> {
            Customer customer = ctx.body().to(Customer.class);

            try {
                customerDao.saveCustomer(customer);
            } catch (DAOException ex) {
                if (ex.getMessage().equals("23505")) {
                    // 23505 is the H2 error code for a unique constraint violation
                    return ctx.setResponseCode(StatusCode.UNPROCESSABLE_ENTITY)
                            .send("Username '" + customer.getUsername()
                                    + "' already exists! Please enter another "
                                    + "username.");
                } else {
                    return ctx.setResponseCode(StatusCode.UNPROCESSABLE_ENTITY).send(ex.getMessage());
                }
            }

            return ctx.send(StatusCode.CREATED);
        });

        put("/api/customers/{username}", ctx -> {
            String username = ctx.path("username").value();
            Customer customer = ctx.body().to(Customer.class);

            customerDao.updateCustomer(customer);
            return ctx.send(StatusCode.NO_CONTENT);
        });

        delete("/api/customers/{username}", ctx -> {
            String username = ctx.path("username").value();
            Customer customer = customerDao.getCustomer(username);

            customerDao.deleteCustomer(customer);
            return ctx.send(StatusCode.NO_CONTENT);
        });
    }
}
