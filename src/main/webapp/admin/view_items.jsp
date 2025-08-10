<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>

<%
    // Check session and role
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    List<Item> items = (List<Item>) request.getAttribute("items");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Inventory - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .table thead th {
            background-color: #0f172a;
            color: white;
        }
        .main-content {
            margin-left: 240px;
            padding: 2rem;
        }
    </style>
</head>
<body>

<%@ include file="../sidebars/admin_sidebar.jsp" %>

<div class="main-content">
    <div class="container">
        <h3 class="mb-4">📚 Inventory List</h3>

        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>Item ID</th>
                    <th>Category</th>
                    <th>Name</th>
                    <th>Brand</th>
                    <th>Unit Price</th>
                    <th>Stock Qty</th>
                </tr>
            </thead>
            <tbody>
                <% if (items != null && !items.isEmpty()) {
                    for (Item item : items) { %>
                        <tr>
                            <td><%= item.getItemId() %></td>
                            <td><%= item.getCategoryName() %></td>
                            <td><%= item.getName() %></td>
                            <td><%= item.getBrand() %></td>
                            <td>Rs. <%= String.format("%.2f", item.getUnitPrice()) %></td>
                            <td><%= item.getStockQuantity() %></td>
                        </tr>
                <%  }
                   } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">No items found.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
