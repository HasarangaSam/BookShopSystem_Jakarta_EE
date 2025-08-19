package dao;

import model.Customer;
import java.util.List;

public interface CustomerDAOInterface {

    void addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int id);

    void updateCustomer(Customer customer);

    void deleteCustomer(int customerId);

    List<Customer> searchCustomersByFirstName(String firstName);
}


