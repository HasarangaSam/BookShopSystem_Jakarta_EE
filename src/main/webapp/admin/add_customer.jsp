<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block unauthorized access - admin only
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Add New Customer - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
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
            margin: 3rem auto;
            background-color: #fff;
            padding: 2.5rem;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.15);
        }
    </style>
</head>
<body>
<%@ include file="../sidebars/admin_sidebar.jsp" %>
<div class="main-content">
    <div class="form-container">
        <h3 class="mb-4 text-center">➕ Add New Customer</h3>

        <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getParameter("error") %>
            </div>
        <% } %>

        <form action="add_customer" method="post">
            <div class="mb-3">
                <label for="first_name" class="form-label">First Name</label>
                <input type="text" name="first_name" id="first_name" class="form-control" required />
            </div>
            <div class="mb-3">
                <label for="last_name" class="form-label">Last Name</label>
                <input type="text" name="last_name" id="last_name" class="form-control" required />
            </div>
            <div class="mb-3">
                <label for="address" class="form-label">Address</label>
                <textarea name="address" id="address" class="form-control" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <label for="telephone" class="form-label">Telephone</label>
                <input type="text" name="telephone" id="telephone" class="form-control" required pattern="0\d{9}" title="Enter a valid Sri Lankan phone number (10 digits starting with 0)" />
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" name="email" id="email" class="form-control" required />
            </div>
            <div class="d-flex justify-content-between">
                <a href="customers" class="btn btn-secondary">← Back</a>
                <button type="submit" class="btn btn-primary">Save Customer</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
