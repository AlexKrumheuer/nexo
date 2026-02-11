<script setup>
import '../../style/admin/admin-global.css'
import AdminFilter from './product_catalog/AdminFilter.vue'
</script>

<template>
    <section class="admin-dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Sellers Management</h1>
            <p class="dashboard-subtitle">Manage approved sellers, revenues and platform shops.</p>
        </div>

        <div class="admin-dashboard--content">
            <div class="main-info">
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Total active Sellers</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">1,000</h2>
                        <p class="stat-trend positive"><fa icon="arrow-up" /> +12% from last month</p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Pending Approvals</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">18</h2>
                        <p class="stat-trend warning"><fa icon="clock" /> Needs attention</p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Total Sellers Revenue</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">R$ 4.5M</h2>
                        <p class="stat-trend positive"><fa icon="chart-line" /> Lifetime sales</p>
                    </div>
                </div>
            </div>

            <div class="card-table-container">
                <div class="card-header">
                    <h2>Seller List</h2>
                </div>

                <AdminFilter placeholder="Search for store name..." btnText="Add Seller">
                    <template #filters>
                        <select class="filter-select">
                            <option value="">Filter by Store Name</option>
                            <option value="">Filter by Owner Name</option>
                            <option value="">Filter by Sales Asc</option>
                            <option value="">Filter by Sales Desc</option>
                            <option value="">Filter by Status</option>
                        </select>
                    </template>
                </AdminFilter>
                
                <div class="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>Store name</th>
                                <th>Owner</th>
                                <th>Status</th>
                                <th>Total Sales</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="table-item">
                                <td class="id-cell">
                                    <div class="flex-center-cell">
                                        <div class="id-cell__img">
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZsbDUaOj73epjVaDWhej5fPrNuFtfZSz0rw&s" alt="Avatar">
                                        </div>
                                        <p>Lewandowski</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="flex-center-cell">
                                        <span>Mbappe dono dessa loja</span>
                                    </div>
                                </td>
                                <td><span class="status-badge seller">Active</span></td>                                
                                <td>
                                    <div class="flex-center-cell">
                                        <span>R$ 200.000</span>
                                    </div>
                                </td>
                                
                                <td class="actions-cell"> 
                                    <div class="flex-center-cell">
                                        <button class="action-btn edit"><fa icon="edit"/> <span>edit</span></button>
                                        <button class="action-btn ban"><fa icon="trash"/> <span>ban</span></button>
                                        <p class="view-details">view details</p>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="pagination-area">
            <p class="pagination-info">Showing <span class="highlight">1</span> to <span class="highlight">10</span> of <span class="highlight">1,000</span> results</p>
            <div class="pagination-controls">
                <button class="pagination-btn disabled" disabled><fa icon="angle-left"></fa></button>
                <button class="pagination-btn active">1</button>
                <button class="pagination-btn">2</button>
                <button class="pagination-btn">3</button>
                <span class="pagination-dots">...</span>
                <button class="pagination-btn">100</button>
                <button class="pagination-btn"><fa icon="angle-right"></fa></button>
            </div>
        </div>
    </section>
</template>