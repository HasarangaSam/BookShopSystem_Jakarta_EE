<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Bill" %>

<%
    if (session == null) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    String role = (String) ((model.User) session.getAttribute("user")).getRole();
    if (!"cashier".equals(role)) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    List<Bill> bills = (List<Bill>) request.getAttribute("bills");

    String successMsg = request.getParameter("success");
    String errorMsg = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Bill History - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        body {
            background: #f1f5f9;
        }
        .container {
            margin-top: 40px;
            max-width: 900px;
        }
        .card {
            padding: 25px;
        }
    </style>
</head>
<body>

<% if ("cashier".equals(role)) { %>
    <%@ include file="../sidebars/cashier_sidebar.jsp" %>
<% } else if ("admin".equals(role)) { %>
    <%@ include file="../sidebars/admin_sidebar.jsp" %>
<% } %>

<div class="container">
    <div class="card shadow">
        <h3 class="mb-4 text-center">📄 Bill History</h3>

        <% if (successMsg != null) { %>
            <div class="alert alert-success"><%= successMsg %></div>
        <% } %>

        <% if (errorMsg != null) { %>
            <div class="alert alert-danger"><%= errorMsg %></div>
        <% } %>

        <table class="table table-striped table-bordered align-middle">
            <thead class="table-dark">
			    <tr>
			        <th>Bill ID</th>
			        <th>Customer</th>
			        <th>Cashier</th>
			        <th>Bill Date</th>
			        <th>Total Amount (Rs.)</th>
			        <th>Actions</th>
			    </tr>
			</thead>
			<tbody>
			    <% if (bills == null || bills.isEmpty()) { %>
			        <tr>
			            <td colspan="6" class="text-center">No bills found.</td>
			        </tr>
			    <% } else {
			        for (Bill bill : bills) {
			    %>
			        <tr>
			            <td><%= bill.getBillId() %></td>
			            <td><%= bill.getCustomerName() %></td>
			            <td><%= bill.getCashierName() %></td>
			            <td><%= bill.getBillDate().toLocalDate() %></td>
			            <td><%= String.format("%.2f", bill.getTotalAmount()) %></td>
			            <td>
			                <a href="<%= request.getContextPath() %>/cashier/view_bill?id=<%= bill.getBillId() %>" class="btn btn-primary btn-sm">View</a>
			            </td>
			        </tr>
			    <%  }
			    } %>
			</tbody>

        </table>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
