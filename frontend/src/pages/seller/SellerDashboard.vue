<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import api from '../../services/api'
import LoadingOverlay from '../LoadingOverlay.vue'

const toast = useToast()
const router = useRouter()

const loading = ref(false)
const stats = ref({
    totalSales: 0,
    pendingOrders: 0,
    totalProducts: 0,
    lowStock: 0,
    latestOrders: []
})

onMounted(() => {
    fetchDashboardData()
})

const fetchDashboardData = async () => {
    loading.value = true
    try {
        const response = await api.get('/api/orders/seller/dashboard')
        stats.value = response.data
    } catch (e) {
        console.error('Error fetching dashboard stats:', e)
        toast.error('Failed to load dashboard data')
    } finally {
        loading.value = false
    }
}

const formatCurrency = (value) => {
    if (value === undefined || value === null) return '$0.00'
    return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value)
}

const formatDate = (dateString) => {
    if (!dateString) return ''
    return new Date(dateString).toLocaleDateString('en-US', {
        day: '2-digit', month: 'short', year: 'numeric'
    })
}

const viewOrderDetails = (orderCode) => {
    router.push(`/my-orders/${orderCode}`)
}
</script>

<template>
    <LoadingOverlay v-if="loading" />
    
    <div class="dashboard-container">
        <h1>Overview</h1>
        
        <div class="stats-grid">
            <div class="stat-card">
                <div class="icon-bg blue">
                    <fa icon="dollar-sign" />
                </div>
                <div class="stat-info">
                    <span class="label">Today's Sales</span>
                    <span class="value">{{ formatCurrency(stats.totalSales) }}</span>
                </div>
            </div>

            <div class="stat-card">
                <div class="icon-bg orange">
                    <fa icon="box-open" />
                </div>
                <div class="stat-info">
                    <span class="label">Pending Orders</span>
                    <span class="value">{{ stats.pendingOrders }}</span>
                </div>
            </div>

            <div class="stat-card">
                <div class="icon-bg green">
                    <fa icon="shopping-bag" />
                </div>
                <div class="stat-info">
                    <span class="label">Total Products</span>
                    <span class="value">{{ stats.totalProducts }}</span>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="icon-bg red">
                    <fa icon="exclamation-triangle" />
                </div>
                <div class="stat-info">
                    <span class="label">Low Stock</span>
                    <span class="value">{{ stats.lowStock }}</span>
                </div>
            </div>
        </div>

        <div class="recent-orders-section">
            <div class="section-header">
                <h3>Latest Orders</h3>
                <router-link to="/seller/orders" class="view-all">View All</router-link>
            </div>
            
            <div class="table-container">
                <table v-if="stats.latestOrders && stats.latestOrders.length > 0">
                    <thead>
                        <tr>
                            <th>Order Code</th>
                            <th>Customer</th>
                            <th>Product</th>
                            <th>Date</th>
                            <th>Value</th>
                            <th class="text-center">Status</th>
                            <th class="text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="item in stats.latestOrders" :key="item.id">
                            <td class="font-semibold">#{{ item.orderCode }}</td>
                            <td>{{ item.user?.username || item.user?.name || 'Customer' }}</td>
                            <td>{{ item.product?.title || 'N/A' }}</td>
                            <td>{{ formatDate(item.createdAt) }}</td>
                            <td class="font-bold">{{ formatCurrency((item.product?.price || 0) * item.quantity) }}</td>
                            <td class="text-center">
                                <span :class="`status-badge status-${(item.shippingStatus || item.status || '').toLowerCase()}`">
                                    {{ item.shippingStatus === 'PENDING_SELLER' ? item.status : (item.shippingStatus || item.status) }}
                                </span>
                            </td>
                            <td class="text-center">
                                <button class="btn-action" @click="viewOrderDetails(item.orderCode)">View</button>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div v-else-if="!loading" class="empty-orders">
                    <p>No recent orders found.</p>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.dashboard-container {
    padding: 2rem;
}

.dashboard-container h1 {
    font-size: 1.5rem;
    color: #1e293b;
    margin-bottom: 20px;
}

.stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 20px;
    margin-bottom: 30px;
}

.stat-card {
    background: white;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.04);
    display: flex;
    align-items: center;
    gap: 15px;
}

.icon-bg {
    width: 50px;
    height: 50px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.2rem;
}
.icon-bg.blue { background: #dbeafe; color: #2563eb; }
.icon-bg.orange { background: #ffedd5; color: #ea580c; }
.icon-bg.green { background: #dcfce7; color: #16a34a; }
.icon-bg.red { background: #fee2e2; color: #dc2626; }

.stat-info {
    display: flex;
    flex-direction: column;
}

.stat-info .label { font-size: 0.85rem; color: #64748b; }
.stat-info .value { font-size: 1.25rem; font-weight: bold; color: #0f172a; }

.recent-orders-section {
    background: white;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.04);
}

.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}

.section-header h3 {
    margin: 0;
    color: #0f172a;
    font-size: 1.1rem;
}

.view-all { 
    color: #3b82f6; 
    text-decoration: none; 
    font-size: 0.9rem; 
    font-weight: 600;
}
.view-all:hover { text-decoration: underline; }

.table-container { overflow-x: auto; }

table { width: 100%; border-collapse: collapse; }

th { 
    text-align: left; 
    color: #64748b; 
    font-size: 0.85rem; 
    padding: 12px; 
    border-bottom: 1px solid #e2e8f0; 
    text-transform: uppercase; 
    letter-spacing: 0.05em; 
}
td { padding: 12px; font-size: 0.9rem; color: #334155; border-bottom: 1px solid #f1f5f9; }

.text-center { text-align: center; }
.font-semibold { font-weight: 600; color: #1e293b; }
.font-bold { font-weight: 700; color: #0f172a; }

.status-badge {
    padding: 4px 10px;
    border-radius: 20px;
    font-size: 0.75rem;
    font-weight: 600;
    display: inline-block;
    text-transform: uppercase;
}

.status-pending,
.status-awaiting_payment,
.status-pending_seller,
.status-awaiting_shipment {
    background-color: #feebc8;
    color: #7b341e;
    border: 1px solid #fbd38d;
}

.status-shipped {
    background-color: #bee3f8;
    color: #2a4365;
    border: 1px solid #90cdf4;
}

.status-paid,
.status-delivered,
.status-finished {
    background-color: #c6f6d5;
    color: #22543d;
    border: 1px solid #9ae6b4;
}

.status-cancelled,
.status-returned {
    background-color: #fed7d7;
    color: #742a2a;
    border: 1px solid #feb2b2;
}

.btn-action {
    background: none;
    border: 1px solid #cbd5e1;
    border-radius: 6px;
    padding: 5px 12px;
    cursor: pointer;
    font-size: 0.8rem;
    font-weight: 600;
    color: #475569;
    transition: all 0.2s;
}
.btn-action:hover { 
    background: #f1f5f9; 
    color: #0f172a; 
}

.empty-orders {
    padding: 2rem;
    text-align: center;
    color: #94a3b8;
}
</style>