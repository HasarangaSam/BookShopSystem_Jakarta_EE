<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Bill, model.BillItem" %>

<%
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    Bill bill = (Bill) request.getAttribute("bill");
    List<BillItem> items = (List<BillItem>) request.getAttribute("items");
%>

<%@ include file="../sidebars/admin_sidebar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Admin - View Bill</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
    <style>
        body {
            background-color: #f8fafc;
        }
        .container {
            margin-left: 250px;
            padding: 2rem;
        }
        .section-title {
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 1rem;
            color: #0f172a;
        }
        .info-box {
            background-color: #ffffff;
            padding: 1.5rem;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            margin-bottom: 2rem;
        }
        .info-box strong {
            display: inline-block;
            width: 140px;
        }
        .table th {
            background-color: #f1f5f9;
        }
        .btn-back {
            margin-top: 1.5rem;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="section-title">🧾 Bill Details (ID: <%= bill.getBillId() %>)</h2>

    <div class="info-box">
        <p><strong>Customer:</strong> <%= bill.getCustomerName() %></p>
        <p><strong>Cashier:</strong> <%= bill.getCashierName() %></p>
        <p><strong>Bill Date:</strong> <%= bill.getBillDate().toString().replace("T", " ") %></p>
        <p><strong>Total Amount:</strong> Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></p>
    </div>

    <h4 class="mb-3">🛒 Purchased Items</h4>
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Unit Price (Rs.)</th>
                <th>Subtotal (Rs.)</th>
            </tr>
        </thead>
        <tbody>
            <% for (BillItem item : items) { %>
                <tr>
                    <td><%= item.getItemName() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td>Rs. <%= String.format("%.2f", item.getUnitPrice()) %></td>
                    <td>Rs. <%= String.format("%.2f", item.getSubtotal()) %></td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <a href="bill_history" class="btn btn-secondary btn-back">⬅ Back to Bill History</a>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
