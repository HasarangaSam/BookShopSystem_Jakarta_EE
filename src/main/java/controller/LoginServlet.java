package controller;

import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 * LoginServlet - Handles login requests for all roles.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Handles POST request from login.jsp
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get username and password from the login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Call DAO to validate user
        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateLogin(username, password);

        // If user exists and password is correct
        if (user != null) {
            // Start session and store user object
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on user role
            String role = user.getRole();

            // If role is admin, redirect to admin dashboard
            if (role.equals("admin")) {
                response.sendRedirect("admin/dashboard.jsp");
            }
            // If role is cashier, redirect to cashier dashboard
            else if (role.equals("cashier")) {
                response.sendRedirect("cashier/dashboard.jsp");
            }
            // If role is storekeeper, redirect to stockkeeper dashboard
            else if (role.equals("stockkeeper")) {
                response.sendRedirect("stockkeeper/dashboard.jsp");
            }
            // If role is unknown, redirect to error page
            else {
                response.sendRedirect("error.jsp");
            }

        } else {
            // If login fails, redirect back to login page with error message
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
