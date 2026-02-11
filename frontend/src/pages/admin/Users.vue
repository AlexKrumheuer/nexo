<script setup>
import '../../style/admin/admin-global.css'
import AdminFilter from './product_catalog/AdminFilter.vue'
</script>

<template>
    <section class="admin-dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Users Management</h1>
            <p class="dashboard-subtitle">Manage system users, access levels and restrictions.</p>
        </div>

        <div class="admin-dashboard--content">
            <div class="main-info">
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Total users</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">500.000</h2>
                        <p class="stat-trend positive"><fa icon="arrow-up" /> +12% from last month</p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Active Today</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">3.200</h2>
                        <p class="stat-trend positive"><fa icon="arrow-up" /> +10% from yesterday</p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Banned Users</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">45</h2>
                        <p class="stat-trend negative"><fa icon="arrow-up" /> -2% from last month</p>
                    </div>
                </div>
            </div>

            <div class="card-table-container">
                <div class="card-header">
                    <h2>User List</h2>
                </div>

                <AdminFilter placeholder="Search for name..." btnText="Add User">
                    <template #filters>
                        <select class="filter-select">
                            <option value="">Filter by Role</option>
                            <option value="">Filter by Name</option>
                            <option value="">Filter by Status</option>
                        </select>
                    </template>
                </AdminFilter>
                
                <div class="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Status</th>
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
                                        <span>lewa@email.com</span>
                                    </div>
                                </td>
                                <td><span class="status-badge seller">Seller</span></td>
                                <td><span class="status-badge success">Active</span></td>
                                <td class="actions"> 
                                    <div class="flex-center-cell">
                                        <button class="action-btn edit" title="Edit">
                                            <fa icon="edit"/> <span>edit</span>
                                        </button>
                                        <button class="action-btn ban" title="Ban">
                                            <fa icon="trash"/> <span>ban</span>
                                        </button>
                                        <p class="view-details">view details</p>
                                    </div>
                                </td>
                            </tr>
                            <tr class="table-item">
                                <td class="id-cell">
                                    <div class="flex-center-cell">
                                        <div class="id-cell__img">
                                            <div class="avatar-placeholder">JD</div>
                                        </div>
                                        <p>John Doe</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="flex-center-cell">
                                        <span>john.doe@email.com</span>
                                    </div>
                                </td>
                                <td><span class="status-badge user">User</span></td>
                                <td><span class="status-badge pending">Pending</span></td>
                                <td class="actions"> 
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
            <p class="pagination-info">Showing <span class="highlight">1</span> to <span class="highlight">10</span> of <span class="highlight">500</span> results</p>
            <div class="pagination-controls">
                <button class="pagination-btn disabled" disabled><fa icon="angle-left"></fa></button>
                <button class="pagination-btn active">1</button>
                <button class="pagination-btn">2</button>
                <button class="pagination-btn">3</button>
                <span class="pagination-dots">...</span>
                <button class="pagination-btn">50</button>
                <button class="pagination-btn"><fa icon="angle-right"></fa></button>
            </div>
        </div>
    </section>
</template>