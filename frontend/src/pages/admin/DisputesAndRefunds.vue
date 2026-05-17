<script setup>
import '../../style/admin/admin-global.css'
import AdminFilter from './product_catalog/AdminFilter.vue'
import { ref } from 'vue'



</script>

<template>
    <section class="admin-dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Dispute & Refunds</h1>
            <p class="dashboard-subtitle">Manage chargebacks, refunds, and customer disputes.</p>
        </div>

        <div class="admin-dashboard--content">
            <div class="main-info">
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Total Disputes</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">145</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> +12% this week
                        </p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Open Disputes</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">18</h2>
                        <p class="stat-trend warning">
                            <fa icon="clock" /> Needs attention
                        </p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Refunded Amount (YTD)</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">R$ 12.450</h2>
                        <p class="stat-trend positive">
                            <fa icon="chart-line" /> +2% from last month
                        </p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Avg. Resolution Time</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">3.5 days</h2>
                    </div>
                </div>
            </div>

            <div class="card-table-container">
                <div class="card-header card-product__catalog">
                    <h2 :style="{ color: '#fff' }">
                        Dispute Queue
                    </h2>

                </div>

                <AdminFilter placeholder="Search Order ID or Customer">
                    <template #filters>
                        <select class="filter-select">
                            <option value="">Status: All Open</option>
                            <option value="">Status: Pending Info</option>
                            <option value="">Status: Solved</option>
                        </select>
                    </template>
                </AdminFilter>

                <div class="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>Dispute ID</th>
                                <th>Order ID</th>
                                <th>Customer</th>
                                <th>Type</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Opened On</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="table-item">
                                <td class="id-cell">
                                    <div class="flex-center-cell">
                                        <p>#DSP-1023</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="flex-center-cell">
                                        <p>#ORDER-9982</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="flex-center-cell">
                                        <p>Alice Smith</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="flex-center-cell">
                                        <p>Item Not Received</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="flex-center-cell">
                                        <p>R$ 120.00</p>
                                    </div>
                                </td>
                                <td><span class="status-badge seller">Active</span></td>
                                <td>
                                    <div class="flex-center-cell">
                                        <p>OCT 15, 2025</p>
                                    </div>
                                </td>

                                <td class="actions-cell">
                                    <div class="flex-center-cell">
                                        <button class="action-btn edit">
                                            <fa icon="edit" /> <span>edit</span>
                                        </button>
                                        <button class="action-btn ban">
                                            <fa icon="trash" /> <span>ban</span>
                                        </button>
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
            <p class="pagination-info">Showing <span class="highlight">1</span> to <span class="highlight">10</span> of
                <span class="highlight">1,000</span> results</p>
            <div class="pagination-controls">
                <button class="pagination-btn disabled" disabled>
                    <fa icon="angle-left"></fa>
                </button>
                <button class="pagination-btn active">1</button>
                <button class="pagination-btn">2</button>
                <button class="pagination-btn">3</button>
                <span class="pagination-dots">...</span>
                <button class="pagination-btn">100</button>
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

.stat-trend.positive {
    color: #10b981;
}

.stat-trend.negative {
    color: #ef4444;
}

.stat-trend.warning {
    color: #f59e0b;
}

.card-product__catalog {
    display: flex;
    gap: 2rem;
    align-items: flex-end;
    padding-bottom: 0;
}

.card-product__catalog h2 {
    font-size: 1rem;
    color: #9ca3af;
    padding: 0.8rem 1rem;
    cursor: pointer;
    transition: all 0.2s;
    border-bottom: 3px solid transparent;
    margin-bottom: 0;
    opacity: 0.8;
}

.card-product__catalog h2:hover {
    color: #fff;
    opacity: 1;
}

.card-product__catalog h2.active-tab {
    color: #fff;
    opacity: 1;
    border-bottom: 3px solid #fff;
}

.card-table-container {
    background: #fff;
    border-radius: 12px;
    border: 1px solid #e5e7eb;
    overflow: hidden;
}

.filter-table {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 1.5rem;
    gap: 1rem;
    flex-wrap: wrap;
    background-color: #fff;
    border-bottom: 1px solid #f3f4f6;
}

.search-container {
    display: flex;
    align-items: center;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    padding: 0.5rem 0.8rem;
    background-color: #fff;
    width: 300px;
    gap: 0.5rem;
}

.filter-search-icon {
    color: #9ca3af;
}

.filter-input {
    border: none;
    outline: none;
    width: 100%;
    font-size: 0.9rem;
    color: #374151;
}

.filter-table-right {
    display: flex;
    gap: 1rem;
    align-items: center;
}

.filter-select {
    padding: 0.6rem 1rem;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    background-color: #fff;
    color: #374151;
    cursor: pointer;
    outline: none;
    font-size: 0.9rem;
}

.filter-table--add {
    padding: 0.6rem 1.2rem;
    background-color: #0369a1;
    color: #fff;
    font-weight: 600;
    border-radius: 8px;
    border: 1px solid #0369a1;
    cursor: pointer;
    transition: all 0.2s;
    font-size: 0.9rem;
}

.filter-table--add:hover {
    background-color: #fff;
    color: #0369a1;
}


.table-wrapper {
    overflow-x: auto;
    width: 100%;
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
    border-bottom: 1px solid #e5e7eb;
}

.admin-table td {
    padding: 1rem 1.5rem;
    border-bottom: 1px solid #f3f4f6;
    color: #374151;
    vertical-align: middle;
}

.admin-table tr:last-child td {
    border-bottom: none;
}

.admin-table tbody tr:hover {
    background-color: #f9fafb;
}


.flex-center-cell {
    display: flex;
    align-items: center;
    gap: 1rem;
    height: 100%;
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
    width: 100%;
    height: 100%;
    background-color: #e5e7eb;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 0.8rem;
    font-weight: bold;
    color: #6b7280;
}


.status-badge {
    padding: 0.25rem 0.6rem;
    border-radius: 99px;
    font-size: 0.75rem;
    font-weight: 600;
    display: inline-block;
    white-space: nowrap;
}

.status-badge.success,
.status-badge.active {
    background-color: #dcfce7;
    color: #166534;
}

.status-badge.pending {
    background-color: #fef9c3;
    color: #854d0e;
}

.status-badge.seller {
    background-color: #e0f2fe;
    color: #075985;
}

.status-badge.user {
    background-color: #f3f4f6;
    color: #374151;
}

.action-btn {
    display: flex;
    align-items: center;
    gap: 0.4rem;
    cursor: pointer;
    font-size: 0.9rem;
    font-weight: 500;
    transition: opacity 0.2s;
    background: none;
    border: none;
}

.action-btn:hover {
    opacity: 0.8;
}

.action-btn.edit {
    color: #0369a1;
}

.action-btn.ban {
    color: #991b1b;
}

.action-btn.approve {
    color: #166534;
}

.view-details {
    color: #3b82f6;
    font-size: 0.9rem;
    cursor: pointer;
    font-weight: 500;
    margin: 0;
    white-space: nowrap;
}

.view-details:hover {
    text-decoration: underline;
}

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

.pagination-btn:disabled,
.pagination-btn.disabled {
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