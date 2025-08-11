package controller;

import dao.BillDAO;
import model.Bill;
import model.BillItem;
import util.EmailUtil;
import util.PDFGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/cashier/invoice")
public class InvoiceServlet extends HttpServlet {

    private BillDAO billDAO;

    @Override
    public void init() {
        billDAO = new BillDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if (idParam == null || action == null) {
            response.sendRedirect("bill_history?error=Missing+parameters");
            return;
        }

        int billId;
        try {
            billId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("bill_history?error=Invalid+bill+ID");
            return;
        }

        Bill bill = billDAO.getBillById(billId);
        if (bill == null) {
            response.sendRedirect("bill_history?error=Bill+not+found");
            return;
        }

        List<BillItem> items = billDAO.getBillItemsByBillId(billId);

        // Prepare file path - e.g., in /tmp or webapp/bills folder
        String fileName = "invoice_bill_" + billId + ".pdf";
        String outputPath = getServletContext().getRealPath("/bills/" + fileName);

        // Generate PDF (overwrite if exists)
        String generatedPath = PDFGenerator.generateInvoicePDF(bill, items, outputPath);
        if (generatedPath == null) {
            response.sendRedirect("view_bill?id=" + billId + "&error=Failed+to+generate+PDF");
            return;
        }

        if ("download".equalsIgnoreCase(action)) {
            // Send PDF as response to download
            File pdfFile = new File(generatedPath);
            if (!pdfFile.exists()) {
                response.sendRedirect("view_bill?id=" + billId + "&error=PDF+not+found");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentLengthLong(pdfFile.length());

            try (FileInputStream in = new FileInputStream(pdfFile);
                 OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

        } else if ("email".equalsIgnoreCase(action)) {
            // Send email with attachment to customer
            String customerEmail = getCustomerEmail(bill.getCustomerId());
            if (customerEmail == null || customerEmail.isEmpty()) {
                response.sendRedirect("view_bill?id=" + billId + "&error=Customer+email+not+found");
                return;
            }

            String subject = "Invoice from Pahana Edu - Bill #" + billId;
            String message = "Dear Customer,\n\nPlease find attached the invoice for your recent purchase.\n\nThank you for shopping with us.";

            boolean emailSent = EmailUtil.sendInvoiceEmail(customerEmail, subject, message, generatedPath);

            if (emailSent) {
                response.sendRedirect("view_bill?id=" + billId + "&msg=Invoice+emailed+successfully");
            } else {
                response.sendRedirect("view_bill?id=" + billId + "&error=Failed+to+send+email");
            }

        } else {
            response.sendRedirect("view_bill?id=" + billId + "&error=Invalid+action");
        }
    }

    // Utility method to get customer email - you can optimize this in DAO if preferred
    private String getCustomerEmail(int customerId) {
        // Simple DAO call or JDBC query to get email by customer ID
        // For demo, we can do a quick direct query here, or implement a CustomerDAO method
        try {
            return new dao.CustomerDAO().getCustomerById(customerId).getEmail();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}