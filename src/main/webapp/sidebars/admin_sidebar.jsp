<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block unauthorized access if session is missing or role is not admin
    if (session == null || !"admin".equals(((model.User) session.getAttribute("user")).getRole())) {
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
        background-color: #0f172a;
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
        color: #cbd5e1;
        text-decoration: none;
        font-size: 15px;
        transition: background 0.2s ease;
    }

    .sidebar a:hover,
    .sidebar a.active {
        background-color: #334155;
        color: #ffffff;
    }

    .main-content {
        margin-left: 240px;
        padding: 2rem;
    }
</style>

<!-- Admin Sidebar -->
<div class="sidebar">
    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp">📊 Dashboard</a>
	<a href="${pageContext.request.contextPath}/admin/users">👥 Manage Users</a>
	<a href="${pageContext.request.contextPath}/admin/customers">📇 Manage Customers</a>
	<a href="${pageContext.request.contextPath}/admin/view_items">📚 View Inventory</a>
	<a href="${pageContext.request.contextPath}/admin/bill_history">📈 Billing History</a>
	<a href="${pageContext.request.contextPath}/admin/analytics">📊 Analytics</a>
	<a href="${pageContext.request.contextPath}/common/help.jsp">❓ Help</a>
	<a href="${pageContext.request.contextPath}/logout.jsp">🚪 Logout</a>
</div>
