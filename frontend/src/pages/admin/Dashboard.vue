<script setup></script>
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
.admin-dashboard {
    padding: 2rem;
    background-color: #f5f7fb;
    min-height: 100vh;
    font-family: 'Inter', 'Segoe UI', sans-serif;
}

.dashboard-header {
    margin-bottom: 2rem;
}

.dashboard-title {
    font-size: 1.8rem;
    color: #1a1a1a;
    font-weight: 700;
}

.dashboard-subtitle {
    color: #6b7280;
    font-size: 0.95rem;
    margin-top: 0.2rem;
}

.admin-dashboard-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(450px, 1fr));
    gap: 1.5rem;
}

.grid-item {
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
    overflow: hidden;
    border: 1px solid #e5e7eb;
    display: flex;
    flex-direction: column;
}

.card-header {
    padding: 1.2rem 1.5rem;
    border-bottom: 1px solid #f3f4f6;
    background-color: #0369a1;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.card-header h2 {
    font-size: 1.1rem;
    color: #fff;
    font-weight: 600;
    margin: 0;
}

.card-content {
    padding: 1.5rem;
}

.period-badge {
    background-color: #e0f2fe;
    color: #0369a1;
    font-size: 0.75rem;
    padding: 0.2rem 0.6rem;
    border-radius: 99px;
    font-weight: 600;
}

.stat-value {
    font-size: 2.5rem;
    font-weight: 700;
    color: #111827;
    margin-bottom: 0.5rem;
}

.stat-trend {
    font-size: 0.9rem;
    color: #6b7280;
}
.stat-trend.positive { color: #10b981; }

.table-wrapper {
    overflow-x: auto; 
}

.admin-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 0.95rem;
}

.admin-table th {
    background-color: #f9fafb;
    color: #6b7280;
    font-weight: 600;
    text-transform: uppercase;
    font-size: 0.75rem;
    letter-spacing: 0.05em;
    padding: 1rem 1.5rem;
    text-align: left;
}

.admin-table td {
    padding: 1rem 1.5rem;
    border-bottom: 1px solid #f3f4f6;
    color: #374151;
}

.admin-table tr:last-child td {
    border-bottom: none;
}

.admin-table tbody tr:hover {
    background-color: #f9fafb;
}

.user-cell { display: flex; align-items: center; gap: 0.8rem; font-weight: 500;}
.avatar { 
    width: 32px; height: 32px; 
    background: #3b82f6; color: white; 
    border-radius: 50%; display: flex; 
    align-items: center; justify-content: center; font-size: 0.7rem;
}
.id-cell { color: #6b7280; font-family: monospace; }
.amount-cell { font-weight: 600; }

.status-badge {
    padding: 0.25rem 0.6rem;
    border-radius: 99px;
    font-size: 0.75rem;
    font-weight: 600;
}
.status-badge.success { background-color: #dcfce7; color: #166534; }
.status-badge.pending { background-color: #fef9c3; color: #854d0e; }

.admin-product-list {
    max-height: 350px;
    overflow-y: auto;
}

.admin-product-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 1.5rem;
    border-bottom: 1px solid #f3f4f6;
    transition: background 0.2s;
}

.admin-product-item:hover {
    background-color: #f8fafc;
}

.product-info {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.product-info img {
    width: 48px;
    height: 48px;
    border-radius: 8px;
    object-fit: cover;
    border: 1px solid #e5e7eb;
}

.product-name {
    font-weight: 600;
    color: #1f2937;
    margin: 0;
}

.product-seller {
    font-size: 0.85rem;
    color: #6b7280;
    margin: 0;
}

.action-buttons {
    display: flex;
    gap: 0.5rem;
}

.btn {
    width: 36px;
    height: 36px;
    border-radius: 8px;
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.2s;
    font-size: 1rem;
}

.btn-approve {
    background-color: #dcfce7;
    color: #166534;
}
.btn-approve:hover {
    background-color: #166534;
    color: white;
}

.btn-decline {
    background-color: #fee2e2;
    color: #991b1b;
}
.btn-decline:hover {
    background-color: #991b1b;
    color: white;
}

.view-all {
    font-size: 0.85rem;
    color: #fff;
    text-decoration: none;
    font-weight: 600;
}

.notification-count {
    color: #fff;
    font-size: 1rem;
}
</style>