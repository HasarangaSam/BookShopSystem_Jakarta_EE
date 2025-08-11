<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Bill" %>

<%
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
%>

<%@ include file="../sidebars/admin_sidebar.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Admin - Bill History</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>

<!-- ✅ Shift content to the right with direct inline style -->
<div class="container mt-4" style="margin-left: 240px;">
    <h2>Bill History</h2>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Bill ID</th>
                <th>Customer</th>
                <th>Cashier</th>
                <th>Bill Date</th>
                <th>Total Amount (Rs.)</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <% for (Bill bill : bills) { %>
            <tr>
                <td><%= bill.getBillId() %></td>
                <td><%= bill.getCustomerName() %></td>
                <td><%= bill.getCashierName() %></td>
                <td><%= bill.getBillDate().toString().replace("T", " ") %></td>
                <td><%= String.format("%.2f", bill.getTotalAmount()) %></td>
                <td>
                    <a href="view_bill?id=<%= bill.getBillId() %>" class="btn btn-sm btn-info">View Details</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>