<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
    // Block unauthorized access
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
    <title>Add New User - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f1f5f9;
        }

        .main-content {
            margin-left: 240px;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem;
        }

        .form-container {
            width: 100%;
            max-width: 600px;
            background-color: #ffffff;
            padding: 2rem 2.5rem;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
        }

        .form-container h3 {
            font-weight: 600;
            color: #1e293b;
        }
    </style>
</head>
<body>

<div class="main-content">
    <div class="form-container">
        <h3 class="mb-4 text-center">➕ Add New User</h3>

        <form action="add_user" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">👤 Username</label>
                <input type="text" class="form-control" name="username" id="username" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">🔒 Password</label>
                <input type="password" class="form-control" name="password" id="password" required>
            </div>

            <div class="mb-3">
                <label for="role" class="form-label">🔑 Role</label>
                <select class="form-select" name="role" id="role" required>
                    <option value="">-- Select Role --</option>
                    <option value="cashier">Cashier</option>
                    <option value="stockkeeper">Stock Keeper</option>
                </select>
            </div>

            <div class="d-flex justify-content-between">
                <a href="users" class="btn btn-secondary">← Back</a>
                <button type="submit" class="btn btn-primary">Save User</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
