<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block access if session missing or role not admin
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }
%>
<%@ include file="../sidebars/admin_sidebar.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Users - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
        }
        .main-content {
            margin-left: 240px;
            padding: 2rem;
            position: relative;
            z-index: 2;
        }
        .table-wrapper {
            background-color: rgba(255, 255, 255, 0.95);
            padding: 1.5rem;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.15);
        }
    </style>
</head>
<body>

<div class="overlay"></div>

<div class="main-content">
    <!-- Success Message -->
    <% if (request.getParameter("msg") != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <%= request.getParameter("msg") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <% } %>

    <!-- Error Message -->
    <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= request.getParameter("error") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <% } %>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>👥 Manage Users</h2>
        <a href="add_user.jsp" class="btn btn-primary">➕ Add New User</a>
    </div>

    <div class="table-wrapper">
        <table class="table table-hover table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<model.User> users = (java.util.List<model.User>) request.getAttribute("users");
                    if (users == null || users.isEmpty()) {
                %>
                    <tr>
                        <td colspan="4" class="text-center">No users found.</td>
                    </tr>
                <%  } else {
                        for (model.User u : users) {
                %>
                    <tr>
                        <td><%= u.getUserId() %></td>
                        <td><%= u.getUsername() %></td>
                        <td><%= u.getRole() %></td>
                        <td>
                            <a href="edit_user?id=<%= u.getUserId() %>" class="btn btn-sm btn-warning">✏️ Edit</a>
                            <a href="delete_user?id=<%= u.getUserId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this user?');">🗑️ Delete</a>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<!-- Footer -->
<%@ include file="../common/footer.jsp" %>

<!-- Scripts -->
<script>
    // Auto-hide alerts after 3 seconds
    setTimeout(function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            alert.classList.remove('show');
            alert.classList.add('fade');
            setTimeout(() => alert.remove(), 300);
        });
    }, 3000); // 3 seconds
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
