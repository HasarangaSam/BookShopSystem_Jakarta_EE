package controller;

import dao.ItemDAO;
import dao.ItemDAOInterface;
import dao.CategoryDAO;
import model.Item;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
    "/stockkeeper/items",
    "/stockkeeper/add_item",
    "/stockkeeper/edit_item",
    "/stockkeeper/delete_item",
    "/stockkeeper/update_stock",
    "/admin/view_items"
})
public class ItemServlet extends HttpServlet {

    private ItemDAOInterface itemDAO;

    @Override
    public void init() {
        itemDAO = new ItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/stockkeeper/items".equals(path)) {
            String search = request.getParameter("search");
            String categoryIdParam = request.getParameter("categoryId");

            List<Item> itemList;
            List<Category> categories = new CategoryDAO().getAllCategories();
            request.setAttribute("categories", categories);

            boolean hasSearch = search != null && !search.trim().isEmpty();
            boolean hasCategory = categoryIdParam != null && !categoryIdParam.trim().isEmpty();

            try {
                if (hasSearch && hasCategory) {
                    int categoryId = Integer.parseInt(categoryIdParam);
                    itemList = itemDAO.searchItemsByNameAndCategory(search.trim(), categoryId);
                } else if (hasSearch) {
                    itemList = itemDAO.searchItemsByName(search.trim());
                } else if (hasCategory) {
                    int categoryId = Integer.parseInt(categoryIdParam);
                    itemList = itemDAO.getItemsByCategory(categoryId);
                } else {
                    itemList = itemDAO.getAllItems();
                }

                request.setAttribute("items", itemList);
                request.getRequestDispatcher("/stockkeeper/manage_items.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("items?error=Invalid+category");
            }
        }

        else if ("/stockkeeper/add_item".equals(path)) {
            try {
                List<Category> categories = new CategoryDAO().getAllCategories();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/stockkeeper/add_item.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("items?error=Unable+to+load+add+item+form");
            }
        }

        else if ("/stockkeeper/edit_item".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Item item = itemDAO.getItemById(id);
                if (item != null) {
                    List<Category> categories = new CategoryDAO().getAllCategories();
                    request.setAttribute("item", item);
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/stockkeeper/edit_item.jsp").forward(request, response);
                } else {
                    response.sendRedirect("items?error=Item+not+found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("items?error=Invalid+item+ID");
            }
        }

        else if ("/stockkeeper/delete_item".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                itemDAO.deleteItem(id);
                response.sendRedirect("items?msg=Item+deleted+successfully");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("items?error=Failed+to+delete+item");
            }
        }

        else if ("/stockkeeper/update_stock".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Item item = itemDAO.getItemById(id);
                if (item != null) {
                    request.setAttribute("item", item);
                    request.getRequestDispatcher("/stockkeeper/update_stock.jsp").forward(request, response);
                } else {
                    response.sendRedirect("items?error=Item+not+found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("items?error=Invalid+item+ID");
            }
        }

        else if ("/admin/view_items".equals(path)) {
            List<Item> itemList = itemDAO.getAllItems();
            request.setAttribute("items", itemList);
            request.getRequestDispatcher("/admin/view_items.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/stockkeeper/add_item".equals(path)) {
            try {
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                String name = request.getParameter("name");
                String brand = request.getParameter("brand");
                double unitPrice = Double.parseDouble(request.getParameter("unit_price"));
                int stockQuantity = Integer.parseInt(request.getParameter("stock_quantity"));

                if (name == null || name.trim().isEmpty() || brand == null || brand.trim().isEmpty()
                        || unitPrice < 0 || stockQuantity < 0) {
                    response.sendRedirect("add_item.jsp?error=Invalid+input");
                    return;
                }

                Item item = new Item(0, categoryId, name.trim(), brand.trim(), unitPrice, stockQuantity);
                itemDAO.addItem(item);
                response.sendRedirect("items?msg=Item+added+successfully");

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("add_item.jsp?error=Failed+to+add+item");
            }
        }

        else if ("/stockkeeper/edit_item".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                String name = request.getParameter("name");
                String brand = request.getParameter("brand");
                double unitPrice = Double.parseDouble(request.getParameter("unit_price"));
                int stockQuantity = Integer.parseInt(request.getParameter("stock_quantity"));

                if (name == null || name.trim().isEmpty() || brand == null || brand.trim().isEmpty()
                        || unitPrice < 0 || stockQuantity < 0) {
                    response.sendRedirect("edit_item?id=" + id + "&error=Invalid+input");
                    return;
                }

                Item item = new Item(id, categoryId, name.trim(), brand.trim(), unitPrice, stockQuantity);
                itemDAO.updateItem(item);
                response.sendRedirect("items?msg=Item+updated+successfully");

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("edit_item?id=" + request.getParameter("id") + "&error=Failed+to+update+item");
            }
        }

        else if ("/stockkeeper/update_stock".equals(path)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int newStock = Integer.parseInt(request.getParameter("stock_quantity"));

                if (newStock < 0) {
                    response.sendRedirect("update_stock?id=" + id + "&error=Stock+cannot+be+negative");
                    return;
                }

                itemDAO.updateStock(id, newStock);
                response.sendRedirect("items?msg=Stock+updated+successfully");

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("items?error=Failed+to+update+stock");
            }
        }
    }
}
