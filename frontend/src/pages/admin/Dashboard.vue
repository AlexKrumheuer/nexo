<script setup>
import '../style/admin/dashboard.css'
</script>
<template>
    <section class="admin-dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Dashboard Overview</h1>
            <p class="dashboard-subtitle">Welcome to admin panel</p>
        </div>

        <div class="admin-dashboard-grid">
            <div class="grid-item card-stats">
                <div class="card-header">
                    <h2>Total Sales</h2>
                    <span class="period-badge">This Month</span>
                </div>
                <div class="card-content">
                    <h2 class="stat-value">R$ 27.800,00</h2>
                    <p class="stat-trend positive">
                        <fa icon="arrow-up"/> +12% vs last month
                    </p>
                </div>
            </div>

            <div class="grid-item card-stats">
                <div class="card-header">
                    <h2>New Users</h2>
                    <span class="period-badge">Last 7 Days</span>
                </div>
                <div class="card-content">
                    <h2 class="stat-value">128</h2>
                    <p class="stat-trend">
                        <fa icon="user-plus"/> +5 new today
                    </p>
                </div>
            </div>

            <div class="grid-item card-table-container">
                <div class="card-header">
                    <h2>Recent Orders</h2>
                    <a href="#" class="view-all">View All</a>
                </div>
                <div class="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Customer</th>
                                <th>Date</th>
                                <th>Status</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="id-cell">#12431</td>
                                <td>
                                    <div class="user-cell">
                                        <div class="avatar">JM</div>
                                        <span>John Master</span>
                                    </div>
                                </td>
                                <td>Oct 24, 2025</td>
                                <td><span class="status-badge success">Shipped</span></td>
                                <td class="amount-cell">$120.50</td>
                            </tr>
                            <tr>
                                <td class="id-cell">#12432</td>
                                <td>
                                    <div class="user-cell">
                                        <div class="avatar icon-avatar">AD</div>
                                        <span>Adam Doe</span>
                                    </div>
                                </td>
                                <td>Oct 24, 2025</td>
                                <td><span class="status-badge pending">Pending</span></td>
                                <td class="amount-cell">$85.00</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="grid-item card-products">
                <div class="card-header">
                    <h2>Pending Product Approvals</h2>
                    <span class="notification-count">3</span>
                </div>
                <div class="admin-product-list">
                    <div class="admin-product-item">
                        <div class="product-info">
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf1fiSQO7JfDw0uv1Ae_Ye-Bo9nhGNg27dwg&s" alt="Product">
                            <div class="product-details">
                                <p class="product-name">Smart Watch Series 7</p>
                                <p class="product-seller">Seller: TechStore</p>
                            </div>
                        </div>
                        <div class="action-buttons">
                            <button class="btn btn-approve" title="Approve"><fa icon="check"/></button>
                            <button class="btn btn-decline" title="Decline"><fa icon="times"/></button>
                        </div>
                    </div>
                     <div class="admin-product-item">
                        <div class="product-info">
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf1fiSQO7JfDw0uv1Ae_Ye-Bo9nhGNg27dwg&s" alt="Product">
                            <div class="product-details">
                                <p class="product-name">Leather Wallet</p>
                                <p class="product-seller">Seller: Couros Sul</p>
                            </div>
                        </div>
                        <div class="action-buttons">
                            <button class="btn btn-approve" title="Approve"><fa icon="check"/></button>
                            <button class="btn btn-decline" title="Decline"><fa icon="times"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<style scoped>

</style>