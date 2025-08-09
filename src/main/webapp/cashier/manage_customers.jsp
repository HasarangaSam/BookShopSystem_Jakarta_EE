<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block unauthorized access
    if (session == null || !"cashier".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }
%>
<%@ include file="../sidebars/cashier_sidebar.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>📇 Manage Customers - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />

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

<div class="main-content">
		<% if (request.getParameter("msg") != null) { %>
		    <div class="alert alert-success alert-dismissible fade show" role="alert">
		        <%= request.getParameter("msg") %>
		        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		    </div>
		<% } %>
		
		<% if (request.getParameter("error") != null) { %>
		    <div class="alert alert-danger alert-dismissible fade show" role="alert">
		        <%= request.getParameter("error") %>
		        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		    </div>
		<% } %>
		    
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>📇 Manage Customers</h2>
        <a href="add_customer.jsp" class="btn btn-primary">➕ Add New Customer</a>
    </div>

    <!-- Search form with clear button -->
    <form class="d-flex mb-4" method="get" action="customers">
        <input 
            class="form-control me-2" 
            type="search" 
            placeholder="Search by first name" 
            aria-label="Search" 
            name="search"
            value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>"
        >
        <button class="btn btn-outline-primary me-2" type="submit">Search</button>
        <button class="btn btn-outline-secondary" type="button" id="clearBtn">Clear</button>
    </form>

    <div class="table-wrapper">
        <table class="table table-hover table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Address</th>
                    <th>Telephone</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    java.util.List<model.Customer> customers = (java.util.List<model.Customer>) request.getAttribute("customers");
                    if (customers == null || customers.isEmpty()) {
                %>
                    <tr>
                        <td colspan="7" class="text-center">No customers found.</td>
                    </tr>
                <%  
                    } else {
                        for (model.Customer c : customers) {
                %>
                    <tr>
                        <td><%= c.getCustomerId() %></td>
                        <td><%= c.getFirstName() %></td>
                        <td><%= c.getLastName() %></td>
                        <td><%= c.getAddress() %></td>
                        <td><%= c.getTelephone() %></td>
                        <td><%= c.getEmail() %></td>
                        <td>
                            <a href="edit_customer?id=<%= c.getCustomerId() %>" class="btn btn-sm btn-warning">✏️ Edit</a>
                            <a href="delete_customer?id=<%= c.getCustomerId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this customer?');">🗑️ Delete</a>
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

<script>
    document.getElementById('clearBtn').addEventListener('click', function() {
        const searchInput = document.querySelector('input[name="search"]');
        searchInput.value = '';
        searchInput.form.submit();
    });
</script>

<script>
    // Auto-hide alerts after 3 seconds
    setTimeout(function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            // Bootstrap fade out
            alert.classList.remove('show');
            alert.classList.add('fade');
            // Remove from DOM after transition
            setTimeout(() => alert.remove(), 300);
        });
    }, 3000); // 3 seconds
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


<%@ include file="../common/footer.jsp" %>

</body>
</html>
