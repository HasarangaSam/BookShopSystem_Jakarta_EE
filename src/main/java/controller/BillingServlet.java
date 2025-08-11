package controller;

import dao.BillDAO;
import dao.CustomerDAO;
import dao.ItemDAO;
import model.Bill;
import model.BillItem;
import model.Item;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {
    "/cashier/billing",             // GET: form, POST: process bill
    "/cashier/bill_history",        // View all bills for cashier
    "/admin/bill_history",          // View all bills for admin
    "/cashier/view_bill",           // View bill details (cashier)
    "/admin/view_bill"				// View bill details (cashier)
})
public class BillingServlet extends HttpServlet {

    private BillDAO billDAO;
    private ItemDAO itemDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        billDAO = new BillDAO();
        itemDAO = new ItemDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/cashier/billing".equals(path)) {
            // Load item and customer lists for billing form
            request.setAttribute("customers", customerDAO.getAllCustomers());
            request.setAttribute("items", itemDAO.getAllItems());
            request.getRequestDispatcher("/cashier/billing.jsp").forward(request, response);
        }

        else if ("/cashier/bill_history".equals(path)) {
            List<Bill> bills = billDAO.getAllBills();
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("/cashier/bill_history.jsp").forward(request, response);
        }

        else if ("/admin/bill_history".equals(path)) {
            List<Bill> bills = billDAO.getAllBills();
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("/admin/bill_history.jsp").forward(request, response);
        }

        else if ("/cashier/view_bill".equals(path)) {
            try {
                int billId = Integer.parseInt(request.getParameter("id"));
                Bill bill = billDAO.getBillById(billId);
                List<BillItem> items = billDAO.getBillItemsByBillId(billId);

                request.setAttribute("bill", bill);
                request.setAttribute("items", items);
                request.getRequestDispatcher("/cashier/view_bill.jsp").forward(request, response); // ✅ FIXED
            } catch (Exception e) {
                response.sendRedirect("bill_history?error=Invalid+bill+ID");
            }
        }
        else if ("/admin/view_bill".equals(path)) {
            try {
                int billId = Integer.parseInt(request.getParameter("id"));
                Bill bill = billDAO.getBillById(billId);
                List<BillItem> items = billDAO.getBillItemsByBillId(billId);

                request.setAttribute("bill", bill);
                request.setAttribute("items", items);
                request.getRequestDispatcher("/admin/view_bill.jsp").forward(request, response);
            } catch (Exception e) {
                response.sendRedirect("bill_history?error=Invalid+bill+ID");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/cashier/billing".equals(path)) {
            try {
                // Retrieve data
                int customerId = Integer.parseInt(request.getParameter("customer_id"));
                String[] itemIds = request.getParameterValues("item_id");
                String[] quantities = request.getParameterValues("quantity");

                HttpSession session = request.getSession(false);
                User cashier = (User) session.getAttribute("user");
                int cashierId = cashier.getUserId();

                List<BillItem> billItems = new ArrayList<>();
                double totalAmount = 0.0;

                for (int i = 0; i < itemIds.length; i++) {
                    int itemId = Integer.parseInt(itemIds[i]);
                    int qty = Integer.parseInt(quantities[i]);

                    Item item = itemDAO.getItemById(itemId);
                    double unitPrice = item.getUnitPrice();
                    double subtotal = qty * unitPrice;

                    BillItem billItem = new BillItem();
                    billItem.setItemId(itemId);
                    billItem.setQuantity(qty);
                    billItem.setUnitPrice(unitPrice);
                    billItem.setSubtotal(subtotal);

                    billItems.add(billItem);
                    totalAmount += subtotal;
                }

                // Create bill
                Bill bill = new Bill();
                bill.setCustomerId(customerId);
                bill.setCashierId(cashierId);
                bill.setBillDate(LocalDateTime.now());
                bill.setTotalAmount(totalAmount);

                int billId = billDAO.addBill(bill);
                if (billId > 0) {
                    billDAO.addBillItems(billItems, billId);
                    response.sendRedirect("view_bill?id=" + billId + "&new=true");
                } else {
                    response.sendRedirect("billing?error=Failed+to+generate+bill");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("billing?error=Invalid+input");
            }
        }
    }
} 
