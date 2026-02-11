<script setup>
import '../../style/admin/admin-global.css'
import AdminFilter from './product_catalog/AdminFilter.vue'
import {ref} from 'vue'



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
                        <p class="stat-trend positive"><fa icon="arrow-up" /> +12% this week</p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Open Disputes</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">18</h2>
                        <p class="stat-trend warning"><fa icon="clock" /> Needs attention</p>
                    </div>
                </div>
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Refunded Amount (YTD)</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">R$ 12.450</h2>
                        <p class="stat-trend positive"><fa icon="chart-line" /> +2% from last month</p>
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
                    <h2 :style="{color: '#fff'}">
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