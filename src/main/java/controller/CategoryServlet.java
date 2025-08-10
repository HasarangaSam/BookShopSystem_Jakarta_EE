package controller;

import dao.CategoryDAO;
import model.Category;
import util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
    "/stockkeeper/categories",
    "/stockkeeper/add_category",
    "/stockkeeper/edit_category",
    "/stockkeeper/delete_category"
})
public class CategoryServlet extends HttpServlet {

    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/stockkeeper/categories".equals(path)) {
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/stockkeeper/manage_categories.jsp").forward(request, response);
        }

        else if ("/stockkeeper/edit_category".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Category category = categoryDAO.getCategoryById(id);

                if (category != null) {
                    request.setAttribute("category", category);
                    request.getRequestDispatcher("/stockkeeper/edit_category.jsp").forward(request, response);
                } else {
                    response.sendRedirect("categories?error=Category+not+found");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("categories?error=Invalid+ID");
            }
        }

        else if ("/stockkeeper/delete_category".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                categoryDAO.deleteCategory(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("categories?msg=Category+deleted+successfully");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/stockkeeper/add_category".equals(path)) {
            String name = request.getParameter("category_name");

            // create a new category
            Category category = new Category(0, name.trim());
            categoryDAO.addCategory(category);

            response.sendRedirect("categories?msg=Category+added+successfully");
        }

        else if ("/stockkeeper/edit_category".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("category_name");

                Category category = new Category(id, name.trim()); 
                categoryDAO.updateCategory(category);

                response.sendRedirect("categories?msg=Category+updated+successfully");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("categories?error=Update+failed");
            }
        }
    }
}
