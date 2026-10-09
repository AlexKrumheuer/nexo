<script setup>
import { ref, onMounted } from 'vue'
import api from '../services/api';
import LoadingOverlay from './LoadingOverlay.vue';

const orders = ref([])
const page = ref("all")
const loading = ref(false)

onMounted(() => {
    fetchOrders()
})

const fetchOrders = async () => {
    loading.value = true;
    try {
        const response = await api.get('/api/orders')
        orders.value = response.data
    } catch (error) {
        console.error('Error fetching orders:', error)
    } finally {
        loading.value = false;
    }
}

const fetchOrderByStatus = async (status) => {
    loading.value = true;
    page.value = status
    try {
        const response = await api.get(`/api/orders/status?status=${status.toUpperCase()}`)
        orders.value = response.data
    } catch (error) {
        console.error(`Error fetching ${status} orders:`, error)
    } finally {
        loading.value = false;
    }
}

const fetchOrderByDeliveryStatus = async (status) => {
    loading.value = true;
    page.value = status
    try {
        const response = await api.get(`/api/orders/delivery/status?status=${status.toUpperCase()}`)
        orders.value = response.data
    } catch (error) {
        console.error(`Error fetching ${status} orders:`, error)
    } finally {
        loading.value = false;
    }
}

const formatCurrency = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value || 0)
}

const payOrder = async (orderId) => {
    loading.value = true;
    try {
        await api.post(`/api/orders/${orderId}/pay`)
        await fetchOrders()
    } catch (error) {
        console.error(`Error paying order ${orderId}:`, error)
    } finally {
        loading.value = false;
    }
}

const cancelItem = async (orderId) => {
    if (!confirm('Are you sure you want to cancel this item?')) return;
    console.log(orderId)
    loading.value = true;
    try {
        await api.put(`/api/orders/${orderId}/cancel`);
        await fetchOrders();
    } catch (error) {
    } finally {
        loading.value = false;
    }
}
</script>

<template>
    <loading-overlay v-if="loading"></loading-overlay>
    <div class="orders-wrapper">
        <div class="orders-container">

            <h2 class="page-title">
                <fa icon="box-open" class="title-icon" /> My Orders
            </h2>

            <!-- Status Filters -->
            <div class="status-filters">
                <button :class="{ active: page === 'all' }" @click="fetchOrderByStatus('all')">All</button>
                <button :class="{ active: page === 'awaiting_payment' }" @click="fetchOrderByStatus('awaiting_payment')">Pending Payment</button>
                <button :class="{ active: page === 'in_progress' }" @click="fetchOrderByDeliveryStatus('in_progress')">In Progress</button>
                <button :class="{ active: page === 'confirmed' }" @click="fetchOrderByDeliveryStatus('confirmed')">Finished</button>
                <button :class="{ active: page === 'cancelled' }" @click="fetchOrderByDeliveryStatus('cancelled')">Cancelled</button>
            </div>

            <!-- Orders List -->
            <div class="orders-list">
                <div v-for="order in orders" :key="order.id" class="order-card">
                    
                    <!-- Order Header -->
                    <div class="order-header">
                        <div class="order-info">
                            <h3>Order: {{ order.orderCode }}</h3>
                            <span class="order-date">
                                Placed on {{ new Date(order.createdAt).toLocaleDateString('pt-BR') }}
                            </span>
                        </div>
                        <div :class="`order-status status-${order.status?.toLowerCase()}`">
                            {{ order.status }}
                        </div>
                    </div>

                    <!-- Order Items -->
                    <div class="order-body">
                        <div v-for="item in order.items" :key="item.id" class="order-item-wrapper">
                            <div class="order-item">
                                <div class="item-image">
                                    <img :src="item.product?.images?.[0]?.url" :alt="item.product?.title">
                                </div>
                                <div class="item-details">
                                    <router-link :to="`/product/${item.product?.slug}`" class="item-title-link">
                                        <h4>{{ item.product?.title }}</h4>
                                    </router-link>
                                    <p>Shop Name: <strong>{{ item.seller?.companyName }}</strong></p>
                                    <span class="item-qty">Qtd: {{ item.quantity }}</span>
                                </div>
                                
                                <div class="item-status-price">
                                    <span 
                                        v-if="order.status === 'PAID'" 
                                        :class="`item-status-badge status-${(item.shippingStatus || 'PENDING').toLowerCase()}`"
                                    >
                                        {{ item.shippingStatus || 'PENDING' }}
                                    </span>
                                    <div class="item-price">
                                        {{ formatCurrency(item.product?.price) }}
                                    </div>
                                </div>
                            </div>

                            <!-- Individual Item Actions -->
                            <div class="item-actions" v-if="order.status === 'PAID'">
                                <button 
                                    v-if="['PENDING_SELLER', 'AWAITING_SHIPMENT'].includes(item.shippingStatus)" 
                                    class="btn-item-cancel"
                                    @click="cancelItem(item.order)"
                                >
                                    Cancel Item
                                </button>

                                <button 
                                    v-if="item.shippingStatus === 'SHIPPED'" 
                                    class="btn-item-secondary"
                                >
                                    Track Item
                                </button>

                                <button 
                                    v-if="item.shippingStatus === 'DELIVERED'" 
                                    class="btn-item-secondary"
                                >
                                    Buy Again
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- Order Footer -->
                    <div class="order-footer">
                        <div class="order-total">
                            Shipping: <span>{{ formatCurrency(order.shippingPrice) }}</span><br>
                            Total: <span>{{ formatCurrency(order.totalPrice) }}</span>
                        </div>
                        <div class="order-actions">
                            <router-link :to="`/my-orders/${order.orderCode}`">
                                <button class="btn-outline">See Details</button>
                            </router-link>
                            
                            <button 
                                v-if="order.status === 'AWAITING_PAYMENT'" 
                                class="btn-primary" 
                                @click="payOrder(order.id || order.orderCode)"
                            >
                                Pay Now
                            </button>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>
</template>

<style scoped>
.orders-wrapper {
    width: 100%;
    min-height: 100vh;
    background-color: #f8f9fa;
    padding: 3rem;
}

.orders-container {
    max-width: 1000px;
    margin: 0 auto;
    animation: fadeIn 0.4s ease-in-out;
}

.page-title {
    color: #1e4770;
    font-size: 1.8rem;
    margin-bottom: 2rem;
    border-bottom: 2px solid #eaeaea;
    padding-bottom: 1rem;
    display: flex;
    align-items: center;
    gap: 0.8rem;
}

.title-icon {
    color: #3b7bb9;
}

.status-filters {
    display: flex;
    gap: 1rem;
    margin-bottom: 2rem;
    overflow-x: auto;
    padding-bottom: 0.5rem;
}

.status-filters button {
    padding: 0.6rem 1.2rem;
    background-color: transparent;
    color: #3b7bb9;
    font-weight: 600;
    border: 1px solid #3b7bb9;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.3s ease;
    white-space: nowrap;
}

.status-filters button:hover {
    background-color: #f0f6fc;
}

.status-filters button.active {
    background-color: #3b7bb9;
    color: #fff;
    box-shadow: 0 4px 10px rgba(59, 123, 185, 0.3);
}

.orders-list {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
}

.order-card {
    background-color: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
    border: 1px solid #eaeaea;
    overflow: hidden;
}

.order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1.5rem;
    background-color: #fcfcfc;
    border-bottom: 1px solid #eaeaea;
}

.order-info h3 {
    color: #1e4770;
    font-size: 1.1rem;
    margin: 0 0 0.2rem 0;
}

.order-date {
    font-size: 0.85rem;
    color: #718096;
}

.order-status {
    padding: 0.4rem 1rem;
    border-radius: 20px;
    font-size: 0.85rem;
    font-weight: bold;
    text-transform: uppercase;
}

/* Base Global Order Status Badges */
.status-delivered, .status-delivered { background-color: #c6f6d5; color: #22543d; }
.status-pending, .status-awaiting_payment { background-color: #feebc8; color: #7b341e; }
.status-shipped { background-color: #bee3f8; color: #2a4365; }
.status-cancelled { background-color: #fed7d7; color: #742a2a; }
.status-confirmed, .status-paid { background-color: #c3dafe; color: #2a4365; }

.order-body {
    padding: 1.5rem;
}

.order-item-wrapper {
    padding: 1rem 0;
    border-bottom: 1px dashed #eaeaea;
}

.order-item-wrapper:first-child {
    padding-top: 0;
}

.order-item-wrapper:last-child {
    border-bottom: none;
    padding-bottom: 0;
}

.order-item {
    display: flex;
    align-items: center;
    gap: 1.5rem;
}

.item-image {
    width: 80px;
    height: 80px;
    background-color: #f0f6fc;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    flex-shrink: 0;
}

.item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.item-details {
    flex-grow: 1;
}

.item-title-link {
    text-decoration: none;
}

.item-title-link:hover h4 {
    color: #3b7bb9;
}

.item-details h4 {
    margin: 0 0 0.3rem 0;
    color: #2d3748;
    font-size: 1rem;
    transition: color 0.2s;
}

.item-details p {
    margin: 0 0 0.3rem 0;
    font-size: 0.85rem;
    color: #718096;
}

.item-qty {
    font-size: 0.85rem;
    color: #a0aec0;
    font-weight: 600;
}

.item-status-price {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 0.5rem;
}

.item-status-badge {
    padding: 0.25rem 0.6rem;
    border-radius: 12px;
    font-size: 0.75rem;
    font-weight: 700;
    text-transform: uppercase;
}

/* Item Specific Shipping Status Badges */
.item-status-badge.status-pending_seller { background-color: #feebc8; color: #7b341e; }
.item-status-badge.status-awaiting_shipment { background-color: #feebc8; color: #7b341e; }
.item-status-badge.status-shipped { background-color: #bee3f8; color: #2a4365; }
.item-status-badge.status-delivered { background-color: #c6f6d5; color: #22543d; }
.item-status-badge.status-cancelled { background-color: #fed7d7; color: #742a2a; }

.item-price {
    font-weight: bold;
    color: #1e4770;
    font-size: 1.1rem;
}

.item-actions {
    display: flex;
    justify-content: flex-end;
    gap: 0.8rem;
    margin-top: 0.8rem;
}

.btn-item-cancel {
    background-color: transparent;
    border: 1px solid #e53e3e;
    color: #e53e3e;
    padding: 0.35rem 0.8rem;
    font-size: 0.8rem;
    border-radius: 6px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s;
}

.btn-item-cancel:hover {
    background-color: #fee2e2;
}

.btn-item-secondary {
    background-color: #f0f6fc;
    border: 1px solid #3b7bb9;
    color: #3b7bb9;
    padding: 0.35rem 0.8rem;
    font-size: 0.8rem;
    border-radius: 6px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s;
}

.btn-item-secondary:hover {
    background-color: #3b7bb9;
    color: #fff;
}

.order-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1.5rem;
    background-color: #fcfcfc;
    border-top: 1px solid #eaeaea;
}

.order-total {
    font-size: 1rem;
    color: #4a5568;
}

.order-total span {
    font-size: 1.3rem;
    font-weight: bold;
    color: #1e4770;
    margin-left: 0.5rem;
}

.order-actions {
    display: flex;
    gap: 1rem;
}

.btn-outline {
    padding: 0.6rem 1.2rem;
    background-color: transparent;
    border: 1px solid #cbd5e0;
    color: #4a5568;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
}

.btn-outline:hover {
    background-color: #f0f0f0;
    border-color: #a0aec0;
}

.btn-primary {
    padding: 0.6rem 1.2rem;
    background-color: #1e4770;
    border: none;
    color: #fff;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
}

.btn-primary:hover {
    background-color: #153250;
    transform: translateY(-2px);
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@media (max-width: 768px) {
    .orders-wrapper {
        padding: 1.5rem;
    }

    .order-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 1rem;
    }

    .order-footer {
        flex-direction: column;
        gap: 1.5rem;
        align-items: stretch;
    }

    .order-actions {
        flex-direction: column;
    }

    .order-actions button {
        width: 100%;
    }

    .item-price {
        display: none;
    }
}
</style>