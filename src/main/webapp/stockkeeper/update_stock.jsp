<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Item" %>
<%
    Item item = (Item) request.getAttribute("item");

    if (session == null || !"stockkeeper".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Stock - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../sidebars/stockkeeper_sidebar.jsp" %>

<div class="main-content d-flex align-items-center justify-content-center" style="margin-left: 240px; height: 100vh; background-color: #f8fafc;">
    <div class="container" style="max-width: 500px;">
        <h3 class="mb-4 text-center">📦 Update Stock</h3>

        <form action="update_stock" method="post" class="border p-4 rounded bg-light shadow-sm">
            <input type="hidden" name="id" value="<%= item.getItemId() %>">

            <div class="mb-3">
                <label class="form-label">Item Name</label>
                <input type="text" class="form-control" value="<%= item.getName() %>" disabled>
            </div>

            <div class="mb-3">
                <label class="form-label">Current Stock</label>
                <input type="number" class="form-control" value="<%= item.getStockQuantity() %>" disabled>
            </div>

            <div class="mb-3">
                <label for="stock_quantity" class="form-label">New Stock Quantity</label>
                <input type="number" name="stock_quantity" id="stock_quantity" class="form-control" required min="0">
            </div>

            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-danger"><%= request.getParameter("error") %></div>
            <% } %>

            <div class="d-grid gap-2 mt-3">
                <button type="submit" class="btn btn-success">✅ Update Stock</button>
                <a href="items" class="btn btn-secondary">🔙 Cancel</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
