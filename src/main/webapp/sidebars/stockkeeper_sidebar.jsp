<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Block unauthorized access if session is missing or role is not stockkeeper
    if (session == null || !"stockkeeper".equals(((model.User) session.getAttribute("user")).getRole())) {
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
        background-color: #1e40af; 
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
        background-color: #374fc7;
        color: #ffffff;
    }

    .main-content {
        margin-left: 240px;
        padding: 2rem;
    }
</style>

<!-- Stockkeeper Sidebar -->
<div class="sidebar">
    <h4>📦 Pahana Edu</h4>
    <a href="../stockkeeper/dashboard.jsp">📊 Dashboard</a>
    <a href="categories">🗂️ Manage Categories</a>
    <a href="items">📦 Manage Items</a>
    <a href="../common/help.jsp">❓ Help</a>
    <a href="../logout.jsp">🚪 Logout</a>
</div>
