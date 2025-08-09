<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block unauthorized access
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    model.Customer editCustomer = (model.Customer) request.getAttribute("customer");
    if (editCustomer == null) {
        response.sendRedirect("customers");
        return;
    }
%>
<%@ include file="../sidebars/admin_sidebar.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>✏️ Edit Customer - Pahana Edu</title>
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
            margin: auto;
            background-color: #fff;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.15);
        }
    </style>
</head>
<body>

<div class="main-content">
    <div class="form-container">
        <h3 class="mb-4 text-center">✏️ Edit Customer</h3>

        <form action="edit_customer" method="post">
            <input type="hidden" name="id" value="<%= editCustomer.getCustomerId() %>" />

            <div class="mb-3">
                <label for="first_name" class="form-label">First Name</label>
                <input type="text" class="form-control" id="first_name" name="first_name" 
                       value="<%= editCustomer.getFirstName() %>" required>
            </div>

            <div class="mb-3">
                <label for="last_name" class="form-label">Last Name</label>
                <input type="text" class="form-control" id="last_name" name="last_name" 
                       value="<%= editCustomer.getLastName() %>" required>
            </div>

            <div class="mb-3">
                <label for="address" class="form-label">Address</label>
                <textarea class="form-control" id="address" name="address" rows="3" required><%= editCustomer.getAddress() %></textarea>
            </div>

            <div class="mb-3">
                <label for="telephone" class="form-label">Telephone</label>
                <input type="text" class="form-control" id="telephone" name="telephone" 
                       value="<%= editCustomer.getTelephone() %>" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" 
                       value="<%= editCustomer.getEmail() %>" required>
            </div>

            <div class="d-flex justify-content-between">
                <a href="customers" class="btn btn-secondary">← Back</a>
                <button type="submit" class="btn btn-primary">Update Customer</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
