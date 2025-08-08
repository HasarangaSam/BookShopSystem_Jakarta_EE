<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block unauthorized access
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    model.User editUser = (model.User) request.getAttribute("user");
    if (editUser == null) {
        response.sendRedirect("users");
        return;
    }
%>
<%@ include file="../sidebars/admin_sidebar.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit User - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f9fa;
        }

        .main-content {
            margin-left: 240px;
            padding: 2rem;
        }

        .form-container {
            max-width: 600px;
            margin: auto;
            background-color: #ffffff;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
        }
    </style>
</head>
<body>

<div class="main-content">
    <div class="form-container">
        <h3 class="mb-4 text-center">✏️ Edit User</h3>

        <form action="edit_user" method="post">
            <input type="hidden" name="id" value="<%= editUser.getUserId() %>">

            <div class="mb-3">
                <label for="username" class="form-label">👤 Username</label>
                <input type="text" class="form-control" name="username" id="username" value="<%= editUser.getUsername() %>" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">🔒 Password</label>
                <input type="password" class="form-control" name="password" id="password">
                <small class="text-muted">Leave blank to keep the current password.</small>
            </div>

            <div class="mb-3">
                <label for="role" class="form-label">🔑 Role</label>
                <select class="form-select" name="role" id="role" required>
                    <option value="cashier" <%= "cashier".equals(editUser.getRole()) ? "selected" : "" %>>Cashier</option>
                    <option value="storekeeper" <%= "storekeeper".equals(editUser.getRole()) ? "selected" : "" %>>Store Keeper</option>
                </select>
            </div>

            <div class="d-flex justify-content-between">
                <a href="users" class="btn btn-secondary">← Back</a>
                <button type="submit" class="btn btn-primary">Update User</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
