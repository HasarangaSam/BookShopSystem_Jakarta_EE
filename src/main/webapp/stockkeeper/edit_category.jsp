<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Restrict access to storekeeper role
    if (session == null || !"stockkeeper".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    model.Category editCategory = (model.Category) request.getAttribute("category");
    if (editCategory == null) {
        response.sendRedirect("manage_categories.jsp?error=Category+not+found");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Category - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="../sidebars/stockkeeper_sidebar.jsp" %>

<div class="main-content" style="margin-left: 240px; padding: 2rem;">
    <div class="container" style="max-width: 600px;">
        <h3 class="mb-4">✏️ Edit Category</h3>

        <form action="edit_category" method="post">
            <input type="hidden" name="id" value="<%= editCategory.getCategoryId() %>">

            <div class="mb-3">
                <label for="category_name" class="form-label">📁 Category Name</label>
                <input type="text" class="form-control" name="category_name" id="category_name" value="<%= editCategory.getCategoryName() %>" required>
            </div>

            <div class="d-flex justify-content-between">
                <a href="manage_categories.jsp" class="btn btn-secondary">← Back</a>
                <button type="submit" class="btn btn-success">Update Category</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
