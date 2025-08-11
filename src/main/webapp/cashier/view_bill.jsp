<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Bill, model.BillItem" %>
<%@ page import="java.util.List" %>

<%
    // Secure session check
    model.User user = (model.User) session.getAttribute("user");
    if (session == null || user == null || !"cashier".equals(user.getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    Bill bill = (Bill) request.getAttribute("bill");
    List<BillItem> items = (List<BillItem>) request.getAttribute("items");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bill #<%= bill.getBillId() %> - Pahana Edu</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8fafc;
        }
        .container {
            margin-top: 40px;
            max-width: 900px;
        }
        .card {
            padding: 25px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
    </style>
</head>
<body>

<%@ include file="../sidebars/cashier_sidebar.jsp" %>

<div class="container">
    <div class="card shadow">

        <% if ("true".equals(request.getParameter("new"))) { %>
            <div class="alert alert-success text-center mb-4">
                ✅ Bill generated successfully!
            </div>
        <% } %>

        <h3 class="mb-4 text-center">🧾 Bill Details - #<%= bill.getBillId() %></h3>

        <div class="mb-3">
            <strong>Customer:</strong> <%= bill.getCustomerName() %><br>
            <strong>Cashier:</strong> <%= bill.getCashierName() %><br>
            <strong>Date:</strong> <%= bill.getBillDate() %><br>
            <strong>Total:</strong> Rs. <%= String.format("%.2f", bill.getTotalAmount()) %>
        </div>

        <hr>

        <h5>🛒 Items:</h5>
        <table class="table table-bordered table-striped mt-2">
            <thead class="table-light">
                <tr>
                    <th>#</th>
                    <th>Item</th>
                    <th>Brand</th>
                    <th>Quantity</th>
                    <th>Unit Price (Rs.)</th>
                    <th>Subtotal (Rs.)</th>
                </tr>
            </thead>
            <tbody>
                <% int count = 1;
                   for (BillItem item : items) { %>
                    <tr>
                        <td><%= count++ %></td>
                        <td><%= item.getItemName() %></td>
                        <td><%= item.getBrandName() %></td>
                        <td><%= item.getQuantity() %></td>
                        <td><%= String.format("%.2f", item.getUnitPrice()) %></td>
                        <td><%= String.format("%.2f", item.getSubtotal()) %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <div class="text-end mt-4">
            <a href="invoice?action=download&id=<%= bill.getBillId() %>" class="btn btn-success">
                ⬇️ Download PDF
            </a>
            <a href="invoice?action=email&id=<%= bill.getBillId() %>" class="btn btn-primary">
                📧 Email Invoice
            </a>
        </div>

    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
