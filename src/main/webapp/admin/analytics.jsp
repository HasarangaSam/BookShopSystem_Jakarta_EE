<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Fetch the data passed from the AnalyticsServlet
    int totalCustomers = (int) request.getAttribute("totalCustomers");
    double totalRevenue = (double) request.getAttribute("totalRevenue");
    int totalBills = (int) request.getAttribute("totalBills");

    // Fetch the JSON data for chart
    String datesJson = (String) request.getAttribute("datesJson");
    String dailyRevenuesJson = (String) request.getAttribute("dailyRevenuesJson");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Analytics- Pahana Edu</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> <!-- Chart.js for Charts -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
        }
        .container {
            padding: 2rem;
        }
        .card {
            margin-bottom: 1.5rem;
        }
        .card-body {
            text-align: center;
        }
    </style>
</head>
<body>

    <%@ include file="../sidebars/admin_sidebar.jsp" %> <!-- Sidebar Include -->

    <div class="overlay"></div>

    <div class="main-content">
        <div class="container">
            <h2>📊 Analytics</h2>
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">Total Customers</div>
                        <div class="card-body">
                            <h5 class="card-title"><%= totalCustomers %></h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">Total Revenue</div>
                        <div class="card-body">
                            <h5 class="card-title">LKR <%= totalRevenue %></h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">Total Bills</div>
                        <div class="card-body">
                            <h5 class="card-title"><%= totalBills %></h5>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Chart Section (Total Revenue Over Time) -->
            <div class="row">
                <div class="col-md-12">
                    <canvas id="revenueChart"></canvas>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %> <!-- Footer Include -->

    <script>
        // Parse the JSON data passed from the servlet into JavaScript arrays
        var dates = JSON.parse('<%= datesJson %>');  // Parse the dates JSON string
        var dailyRevenues = JSON.parse('<%= dailyRevenuesJson %>');  // Parse the daily revenues JSON string

        // Revenue Chart
        var ctx = document.getElementById('revenueChart').getContext('2d');
        var revenueChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: dates,  // Dates on X-axis
                datasets: [{
                    label: 'Daily Revenue (LKR)',
                    data: dailyRevenues,  // Revenue values on Y-axis
                    borderColor: 'rgba(75, 192, 192, 1)',
                    fill: false,
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>

</body>
</html>
