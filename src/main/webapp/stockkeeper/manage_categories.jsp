<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%
    // Restrict access to stockkeeper role
    if (session == null || !"stockkeeper".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    List<Category> categories = (List<Category>) request.getAttribute("categories");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Categories - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="../sidebars/stockkeeper_sidebar.jsp" %>

<div class="main-content" style="margin-left: 240px; padding: 2rem;">
    <div class="container">
            <%-- Display Success Message --%>
        <%
            if (request.getParameter("msg") != null) {
        %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= request.getParameter("msg") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <%
            }
        %>

        <%-- Display Error Message --%>
        <%
            if (request.getParameter("error") != null) {
        %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= request.getParameter("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <%
            }
        %>
        
        <h3 class="mb-4">Manage Categories</h3>

        <a href="add_category.jsp" class="btn btn-success mb-3">➕ Add New Category</a>

        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Category Name</th>
                    <th style="width: 150px;">Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (categories != null && !categories.isEmpty()) {
                        for (Category c : categories) {
                %>
                    <tr>
                        <td><%= c.getCategoryId() %></td>
                        <td><%= c.getCategoryName() %></td>
                        <td>
                            <a href="edit_category?id=<%= c.getCategoryId() %>" class="btn btn-sm btn-warning">Edit</a>
                            <a href="delete_category?id=<%= c.getCategoryId() %>" class="btn btn-sm btn-danger"
                               onclick="return confirm('Are you sure you want to delete this category?');">Delete</a>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="3" class="text-center">No categories found.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<%-- Script to auto-hide alerts after 3 seconds --%>
<script>
    setTimeout(function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            // Bootstrap fade out effect
            alert.classList.remove('show');
            alert.classList.add('fade');
            // Remove from DOM after transition
            setTimeout(() => alert.remove(), 300);
        });
    }, 3000); // 3 seconds
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
