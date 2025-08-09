package controller;

import dao.CustomerDAO;
import model.Customer;
import util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
    "/admin/customers", "/cashier/customers",
    "/admin/add_customer", "/cashier/add_customer",
    "/admin/edit_customer", "/cashier/edit_customer",
    "/admin/delete_customer", "/cashier/delete_customer"
})
public class CustomerServlet extends HttpServlet {

    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        model.User user = (model.User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Session+expired");
            return;
        }

        String role = user.getRole();

        // View/Search customers
        if (path.endsWith("/customers")) {
            String search = request.getParameter("search");

            List<Customer> customerList = (search != null && !search.trim().isEmpty())
                    ? customerDAO.searchCustomersByFirstName(search)
                    : customerDAO.getAllCustomers();

            request.setAttribute("customers", customerList);

            if ("cashier".equals(role)) {
                request.getRequestDispatcher("/cashier/manage_customers.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/admin/manage_customers.jsp").forward(request, response);
            }
        }

        // Show Edit Customer Form
        else if (path.endsWith("/edit_customer")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Customer customer = customerDAO.getCustomerById(id);

                if (customer != null) {
                    request.setAttribute("customer", customer);
                    if ("cashier".equals(role)) {
                        request.getRequestDispatcher("/cashier/edit_customer.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/admin/edit_customer.jsp").forward(request, response);
                    }
                } else {
                    response.sendRedirect(getRedirectPath(request, role, "customers?error=Customer+not+found"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(getRedirectPath(request, role, "customers?error=Invalid+customer+ID"));
            }
        }

        // Delete customer
        else if (path.endsWith("/delete_customer")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                customerDAO.deleteCustomer(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect(getRedirectPath(request, role, "customers?msg=Customer+deleted"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        model.User user = (model.User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Session+expired");
            return;
        }

        String role = user.getRole();

        // Add Customer
        if (path.endsWith("/add_customer")) {
            Customer customer = extractCustomerFromRequest(request, false);

            if (customer != null) {
                customerDAO.addCustomer(customer);
                response.sendRedirect(getRedirectPath(request, role, "customers?msg=Customer+added+successfully"));
            } else {
                response.sendRedirect(getRedirectPath(request, role, "add_customer.jsp?error=Invalid+input"));
            }
        }

        // Update Customer
        else if (path.endsWith("/edit_customer")) {
            Customer customer = extractCustomerFromRequest(request, true);

            if (customer != null) {
                customerDAO.updateCustomer(customer);
                response.sendRedirect(getRedirectPath(request, role, "customers?msg=Customer+updated"));
            } else {
                String id = request.getParameter("id");
                response.sendRedirect(getRedirectPath(request, role, "edit_customer?id=" + id + "&error=Invalid+input"));
            }
        }
    }

    // Helper to extract form input
    private Customer extractCustomerFromRequest(HttpServletRequest request, boolean includeId) {
        try {
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String address = request.getParameter("address");
            String phone = request.getParameter("telephone");
            String email = request.getParameter("email");

            if (!ValidationUtil.isValidName(firstName) || !ValidationUtil.isValidName(lastName)
                    || !ValidationUtil.isValidPhone(phone) || !ValidationUtil.isValidEmail(email)) {
                return null;
            }

            // Create Customer using constructor
            Customer customer;

            if (includeId) {
                int id = Integer.parseInt(request.getParameter("id"));
                customer = new Customer(id, firstName.trim(), lastName.trim(), address.trim(), phone.trim(), email.trim());
            } else {
                customer = new Customer(0, firstName.trim(), lastName.trim(), address.trim(), phone.trim(), email.trim());
            }

            return customer;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper to redirect based on role + contextPath
    private String getRedirectPath(HttpServletRequest request, String role, String path) {
        String base = role.equals("cashier") ? "/cashier/" : "/admin/";
        return request.getContextPath() + base + path;
    }
}
