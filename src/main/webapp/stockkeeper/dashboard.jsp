<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
    // Block access if session is missing or role is not stockkeeper
    if (session == null || !"stockkeeper".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Store Keeper Dashboard - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background: url("../assets/img/backgroundNew.jpg") no-repeat center center fixed;
            background-size: cover;
        }

        .overlay {
            position: fixed;
            top: 0;
            left: 240px;
            width: calc(100% - 240px);
            height: 100vh;
            background-color: rgba(30, 64, 175, 0.2); /* blue overlay */
            z-index: 1;
        }

        .main-content {
            margin-left: 240px;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            position: relative;
            z-index: 2;
            padding: 2rem;
        }

        .welcome-box {
            background-color: rgba(255, 255, 255, 0.95);
            border-radius: 12px;
            padding: 2.5rem 3rem;
            width: 100%;
            max-width: 600px;
            text-align: center;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
        }

        .welcome-box h2 {
            font-weight: 600;
            color: #1e293b;
        }

        .welcome-box p {
            color: #475569;
            margin-bottom: 0.75rem;
        }

        .welcome-box hr {
            border-top: 1px solid #e2e8f0;
            margin: 1.5rem 0;
        }
    </style>
</head>
<body>

    <%@ include file="../sidebars/stockkeeper_sidebar.jsp" %>

    <div class="overlay"></div>

    <div class="main-content">
        <div class="welcome-box">
            <h2>👋 Welcome, Stock Keeper</h2>
            <p>This dashboard provides inventory management access to the Pahana Edu system.</p>
            <p>Use the left sidebar to manage categories, items, and get help.</p>
            <hr>
            <p><strong>System Version:</strong> 1.0.0</p>
            <p><strong>Role:</strong> Stock Keeper</p>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>

</body>
</html>
