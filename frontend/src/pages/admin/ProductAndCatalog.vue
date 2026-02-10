<script setup>
// Aqui viria a lógica de script (imports, props, data fetching)
</script>

<template>
    <section class="admin-dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Product & Catalog Management</h1>
            <p class="dashboard-subtitle">Manage system users, access levels and restrictions.</p>
        </div>

        <div class="admin-dashboard--content">
            <div class="main-info">
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Total Products</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">500.000</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> +120% this week
                        </p>
                    </div>
                </div>

                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Out of stock</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">320</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> Needs attention
                        </p>
                    </div>
                </div>

                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Catgories</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">48</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> 5 main, 43 sub
                        </p>
                    </div>
                </div>
            </div>

            <div class="grid-item card-table-container">
                <div class="card-header">
                    <h2>User List</h2>
                    <a href="#" class="view-all">View All</a>
                </div>
                
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
                                    <div class="id-cell__img">
                                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZsbDUaOj73epjVaDWhej5fPrNuFtfZSz0rw&s" alt="User Avatar">
                                    </div>
                                    <p>Lewandowski</p>
                                </td>
                                <td>
                                    <div class="user-cell">
                                        <span>lewa@email.com</span>
                                    </div>
                                </td>
                                <td><span class="status-badge seller">Seller</span></td>
                                <td><span class="status-badge success">Active</span></td>
                                <td class="actions"> 
                                    <div class="action-btn edit" title="Edit User">
                                        <fa icon="edit"/>
                                        <span>edit</span>
                                    </div>
                                    <div class="action-btn ban" title="Ban User">
                                        <fa icon="trash"/>
                                        <span>ban</span>
                                    </div>
                                    <p class="view-details">view details</p>
                                </td>
                            </tr>
                            <tr class="table-item">
                                <td class="id-cell">
                                    <div class="id-cell__img">
                                        <div class="avatar-placeholder">JD</div>
                                    </div>
                                    <p>John Doe</p>
                                </td>
                                <td>
                                    <div class="user-cell">
                                        <span>john.doe@email.com</span>
                                    </div>
                                </td>
                                <td><span class="status-badge user">User</span></td>
                                <td><span class="status-badge pending">Pending</span></td>
                                <td class="actions"> 
                                    <div class="action-btn edit">
                                        <fa icon="edit"/>
                                        <span>edit</span>
                                    </div>
                                    <div class="action-btn ban">
                                        <fa icon="trash"/>
                                        <span>ban</span>
                                    </div>
                                    <p class="view-details">view details</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="pagination-area">
            <p class="pagination-info">
                Showing <span class="highlight">1</span> to <span class="highlight">10</span> of <span class="highlight">500</span> results
            </p>
            
            <div class="pagination-controls">
                <button class="pagination-btn disabled" disabled>
                    <fa icon="angle-left"></fa>
                </button>

                <button class="pagination-btn active">1</button>
                <button class="pagination-btn">2</button>
                <button class="pagination-btn">3</button>
                <button class="pagination-btn">4</button>
                <span class="pagination-dots">...</span>
                <button class="pagination-btn">50</button>

                <button class="pagination-btn">
                    <fa icon="angle-right"></fa>
                </button>
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
    color: #1f2937;
}

.dashboard-header {
    margin-bottom: 2rem;
}

.dashboard-title {
    font-size: 1.8rem;
    color: #111827;
    font-weight: 700;
}

.dashboard-subtitle {
    color: #6b7280;
    font-size: 0.95rem;
    margin-top: 0.2rem;
}

.admin-dashboard--content {
    display: flex;
    flex-direction: column;
    gap: 2rem;
}

.main-info {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); 
    gap: 1.5rem;
}

.grid-item {
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
    overflow: hidden;
    border: 1px solid #e5e7eb;
    display: flex;
    flex-direction: column;
}

.card-header {
    padding: 1rem 1.5rem;
    border-bottom: 1px solid #f3f4f6;
    background-color: #0369a1;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.card-header h2 {
    font-size: 1rem;
    color: #fff;
    font-weight: 600;
    margin: 0;
}

.card-content {
    padding: 1.5rem;
}

.stat-value {
    font-size: 2.2rem;
    font-weight: 700;
    color: #111827;
    margin-bottom: 0.2rem;
}

.stat-trend {
    font-size: 0.85rem;
    font-weight: 500;
    color: #6b7280;
}

.stat-trend.positive { color: #10b981; }
.stat-trend.negative { color: #ef4444; }

.card-table-container {
    background: #fff;
}

.view-all {
    font-size: 0.85rem;
    color: #fff;
    text-decoration: none;
    font-weight: 600;
    opacity: 0.9;
    transition: opacity 0.2s;
}
.view-all:hover { opacity: 1; }

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
    vertical-align: middle;
}

.admin-table tbody tr:hover {
    background-color: #f9fafb;
}

.admin-table tr:last-child td {
    border-bottom: none;
}

.id-cell {
    display: flex;
    align-items: center;
    gap: 1rem;
    font-weight: 500;
    color: #111827;
}

.id-cell__img {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
}

.id-cell__img img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    object-fit: cover;
    border: 1px solid #e5e7eb;
}

.avatar-placeholder {
    width: 100%; height: 100%;
    background-color: #e5e7eb;
    border-radius: 50%;
    display: flex; align-items: center; justify-content: center;
    font-size: 0.8rem; font-weight: bold; color: #6b7280;
}

.user-cell span {
    color: #6b7280;
}

.status-badge {
    padding: 0.25rem 0.6rem;
    border-radius: 99px;
    font-size: 0.75rem;
    font-weight: 600;
    display: inline-block;
}

.status-badge.success { background-color: #dcfce7; color: #166534; }
.status-badge.pending { background-color: #fef9c3; color: #854d0e; }
.status-badge.seller { background-color: #e0f2fe; color: #075985; }
.status-badge.user { background-color: #f3f4f6; color: #374151; }

.actions {
    display: flex;
    align-items: center;
    gap: 1.2rem;
}

.action-btn {
    display: flex;
    align-items: center;
    gap: 0.4rem;
    cursor: pointer;
    font-size: 0.9rem;
    font-weight: 500;
    transition: color 0.2s;
}

.action-btn.edit { color: #0369a1; }
.action-btn.edit:hover { color: #0284c7; }

.action-btn.ban { color: #991b1b; }
.action-btn.ban:hover { color: #dc2626; }

.view-details {
    color: #3b82f6;
    font-size: 0.9rem;
    cursor: pointer;
    font-weight: 500;
    margin: 0;
}
.view-details:hover { text-decoration: underline; }

.pagination-area {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1.5rem 0;
    margin-top: 1rem;
    border-top: 1px solid #e5e7eb;
}

.pagination-info {
    color: #6b7280;
    font-size: 0.9rem;
    font-weight: 500;
}

.pagination-info .highlight {
    color: #111827;
    font-weight: 700;
}

.pagination-controls {
    display: flex;
    gap: 0.4rem;
    align-items: center;
}

.pagination-btn {
    min-width: 36px;
    height: 36px;
    padding: 0 0.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    color: #374151;
    font-size: 0.9rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s ease;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.pagination-btn:hover:not(.active):not(:disabled) {
    background-color: #f9fafb;
    border-color: #d1d5db;
    color: #111827;
}

.pagination-btn.active {
    background-color: #0369a1;
    border-color: #0369a1;
    color: #ffffff;
    box-shadow: 0 4px 6px -1px rgba(3, 105, 161, 0.3);
}

.pagination-btn:disabled, .pagination-btn.disabled {
    background-color: #f3f4f6;
    border-color: #e5e7eb;
    color: #9ca3af;
    cursor: not-allowed;
    box-shadow: none;
}

.pagination-dots {
    color: #6b7280;
    padding: 0 0.2rem;
    font-weight: 600;
}
</style>