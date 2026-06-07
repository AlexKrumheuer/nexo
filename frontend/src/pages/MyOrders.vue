<script setup>
import { ref, computed, onMounted, watch } from 'vue'
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
        console.log(orders.value)
    } catch (error) {
        console.error('Error fetching orders:', error)
    } finally {
        loading.value = false;
    }
}

const fetchOrderByStatus = async (status) => {
    loading.value = true;
    try {
        const response = await api.get(`/api/orders/status?status=${status.toUpperCase()}`)
        orders.value = response.data
        page.value = status
    } catch (error) {
        console.error(`Error fetching ${status} orders:`, error)
    } finally {
        loading.value = false;
    }
}
const formatCurrency = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}
</script>

<template>
    <loading-overlay v-if="loading"></loading-overlay>
    <div class="orders-wrapper">
        <div class="orders-container">

            <h2 class="page-title">
                <fa icon="box-open" class="title-icon" /> My Orders
            </h2>

            <div class="status-filters">
                <button :class="{ active: page === 'all' }" @click="fetchOrderByStatus('all')">All</button>
                <button :class="{ active: page === 'pending' }" @click="fetchOrderByStatus('pending')">Pending</button>
                <button :class="{ active: page === 'confirmed' }" @click="fetchOrderByStatus('confirmed')">Confirmed</button>
                <button :class="{ active: page === 'shipped' }" @click="fetchOrderByStatus('shipped')">Shipped</button>
                <button :class="{ active: page === 'delivered' }"
                    @click="fetchOrderByStatus('delivered')">Delivered</button>
                <button :class="{ active: page === 'cancelled' }"
                    @click="fetchOrderByStatus('cancelled')">Cancelled</button>
            </div>

            <div class="orders-list">
                <div>
                    <div v-for="order in orders" :key="order.id" class="order-card">
                        <div class="order-header">
                            <div class="order-info">
                                <h3>Order: {{ order.orderCode }}</h3>
                                <span class="order-date">Placed on {{ new Date(order.createdAt).toDateString('pt-BR')
                                }}</span>
                            </div>
                            <div :class="`order-status status-${order.status.toLowerCase()}`">
                                {{ order.status }}
                            </div>
                        </div>

                        <div v-for="item in order.items" :key="item.id" class="order-body">
                            <router-link :to="`/product/${item.product.slug}`">
                                <div class="order-item">
                                    <div class="item-image">
                                        <img :src="item.product.images[0].url" alt="">
                                    </div>
                                    <div class="item-details">
                                        <h4>{{ item.product.title }}</h4>
                                        <p>Shop Name: <strong>{{ item.seller.companyName }}</strong></p>
                                        <span class="item-qty">Qtd: {{ item.quantity }}</span>
                                    </div>
                                    <div class="item-price">
                                        R$ {{ formatCurrency(item.product.price) }}
                                    </div>
                                </div>
                            </router-link>

                        </div>

                        <div class="order-footer">
                            <div class="order-total">
                                Shipping: <span>R$ {{ formatCurrency(order.shippingPrice) }}</span><br>
                                Total: <span>R$ {{ formatCurrency(order.totalPrice) }}</span>
                            </div>
                            <div class="order-actions">
                                <router-link :to="`/my-orders/${order.orderCode}`">
                                    <button class="btn-outline">See Details</button>
                                </router-link>
                                <button v-if="order.status === 'PENDING' || order.status === 'SHIPPED' || order.status === 'CONFIRMED'" class="btn-cancel">Cancel Order</button>
                                <button v-if="order.status === 'PENDING'" class="btn-primary">Pay Now</button>
                                <button v-else-if="order.status === 'SHIPPED'" class="btn-primary">Track Order</button>
                                <button v-else-if="order.status === 'DELIVERED'" class="btn-primary">Buy Again</button>
        


                            </div>
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
}

.status-delivered {
    background-color: #c6f6d5;
    color: #22543d;
}

.status-pending {
    background-color: #feebc8;
    color: #7b341e;
}

.status-shipped {
    background-color: #bee3f8;
    color: #2a4365;
}

.status-cancelled {
    background-color: #fed7d7;
    color: #742a2a;
}

.status-confirmed {
    background-color: #c3dafe;
    color: #2a4365;
}

.order-body {
    padding: 1.5rem;
}

.order-item {
    display: flex;
    align-items: center;
    gap: 1.5rem;
    padding-bottom: 1.5rem;
    border-bottom: 1px dashed #eaeaea;
}

.order-item:last-child {
    border-bottom: none;
    padding-bottom: 0;
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

.placeholder-icon {
    font-size: 2rem;
    color: #cbd5e0;
}

.item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.item-details {
    flex-grow: 1;
}

.item-details h4 {
    margin: 0 0 0.3rem 0;
    color: #2d3748;
    font-size: 1rem;
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

.item-price {
    font-weight: bold;
    color: #1e4770;
    font-size: 1.1rem;
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

.btn-cancel {
    background-color: #e53e3e;
    border: none;
    color: #fff;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
    padding: 0.6rem 1.2rem;
}

.btn-cancel:hover {
    background-color: #c53030;
    transform: translateY(-2px);
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