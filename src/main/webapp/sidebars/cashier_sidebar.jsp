<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block unauthorized access if session is missing or role is not cashier
    if (session == null || !"cashier".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }
%>

<style>
    .sidebar {
        position: fixed;
        top: 0;
        left: 0;
        width: 240px;
        height: 100vh;
        background-color: #14532d; /* Dark green */
        color: #ffffff;
        padding-top: 2rem;
        z-index: 100;
    }

    .sidebar h4 {
        text-align: center;
        font-weight: bold;
        margin-bottom: 2rem;
    }

    .sidebar a {
        display: block;
        padding: 12px 24px;
        color: #d1fae5;
        text-decoration: none;
        font-size: 15px;
        transition: background 0.2s ease;
    }

    .sidebar a:hover,
    .sidebar a.active {
        background-color: #166534;
        color: #ffffff;
    }

    .main-content {
        margin-left: 240px;
        padding: 2rem;
    }
</style>

<!-- Cashier Sidebar -->
<div class="sidebar">
    <h4>🧾 Pahana Edu</h4>
    <a href="dashboard.jsp">🏠 Dashboard</a>
    <a href="customers">📇 Manage Customers</a>
    <a href="billing">➕ New Bill</a>
    <a href="bill_history">🧾 Billing History</a>
    <a href="../common/help.jsp">❓ Help</a>
    <a href="../logout.jsp">🚪 Logout</a>
</div>

