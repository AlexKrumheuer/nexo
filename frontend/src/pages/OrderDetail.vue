<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '../services/api'

const route = useRoute()
const router = useRouter()
const order = ref(null)
const loading = ref(true)

const orderCode = route.params.orderCode

onMounted(() => {
    fetchOrderDetails()
})

const fetchOrderDetails = async () => {
    try {
        loading.value = true
        const response = await api.get(`/api/orders/${orderCode}`)
        order.value = response.data
    } catch (error) {
        console.error('Error searching for product details:', error)
        router.push('/my-orders') 
    } finally {
        loading.value = false
    }
}

const formatCurrency = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value || 0)
}

const formatDate = (dateString) => {
    if (!dateString) return ''
    return new Date(dateString).toLocaleDateString('en-US', {
        day: '2-digit', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit'
    })
}

const goBack = () => {
    router.push('/my-orders')
}
</script>

<template>
    <div class="order-detail-wrapper" v-if="!loading && order">
        <div class="order-detail-container">
            
            <div class="header-navigation">
                <button class="btn-back" @click="goBack">
                    <fa icon="arrow-left" /> Back to Orders
                </button>
            </div>

            <div class="detail-card main-summary">
                <div class="summary-info">
                    <h2>Order Details #{{ order.orderCode }}</h2>
                    <p class="order-date">Ordered in {{ formatDate(order.createdAt) }}</p>
                </div>
                <div :class="`order-status status-${order.status.toLowerCase()}`">
                    {{ order.status }}
                </div>
            </div>

            <div class="details-grid">
                <div class="left-column">
                    
                    <div class="detail-card tracking-card">
                        <h3><fa icon="truck" /> Delivery Status</h3>
                        <p class="tracking-code">Tracking Code: <strong>BR987654321NX</strong></p>
                        
                        <div class="timeline">
                            <div class="timeline-item" :class="{ completed: order.status !== 'CANCELLED' }">
                                <div class="timeline-dot"></div>
                                <div class="timeline-content">
                                    <h4>Order Placed</h4>
                                    <span>{{ formatDate(order.createdAt) }}</span>
                                </div>
                            </div>
                            <div class="timeline-item" :class=" { completed: order.status == 'CONFIRMED' || order.status == 'SHIPPED' || order.status == 'DELIVERED' }">
                                <div class="timeline-dot"></div>
                                <div class="timeline-content">
                                    <h4>Payment Approved</h4>
                                    <span>The system confirmed the payment.</span>
                                </div>
                            </div>
                            <div class="timeline-item" :class="{ completed: order.status === 'SHIPPED' || order.status === 'DELIVERED' }">
                                <div class="timeline-dot"></div>
                                <div class="timeline-content">
                                    <h4>Order Shipped</h4>
                                    <span>The package is with the carrier.</span>
                                </div>
                            </div>
                            <div class="timeline-item" :class="{ completed: order.status === 'DELIVERED' }">
                                <div class="timeline-dot"></div>
                                <div class="timeline-content">
                                    <h4>Delivered</h4>
                                    <span>The package was delivered to the recipient.</span>
                                </div>
                            </div>
                            <div v-if="order.status == 'CANCELLED'" class="timeline-item" :class="{ completed: order.status === 'CANCELLED' }">
                                <div class="timeline-dot-cancelled"></div>
                                <div class="timeline-content">
                                    <h4>Cancelled</h4>
                                    <span>The order was cancelled.</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="detail-card items-card">
                        <h3><fa icon="box" /> Products</h3>
                        <div v-for="item in order.items" :key="item.id" class="order-item">
                            <div class="item-image">
                                <img v-if="item.product.images?.length" :src="item.product.images[0].url" alt="">
                                <fa v-else icon="image" class="placeholder-icon" />
                            </div>
                            <div class="item-details">
                                <router-link :to="`/product/${item.product.slug}`" class="product-link">
                                    <h4>{{ item.product.title }}</h4>
                                </router-link>
                                <p>Seller: <strong>{{ item.seller?.companyName || 'Nexo' }}</strong></p>
                                <span class="item-qty">Quantity: {{ item.quantity }}</span>
                            </div>
                            <div class="item-price">
                                {{ formatCurrency(item.product.price) }}
                            </div>
                        </div>
                    </div>

                </div>

                <div class="right-column">
                    
                    <div class="detail-card address-card">
                        <h3><fa icon="map-marker-alt" /> Delivery Address</h3>
                        <p class="address-text">
                            <strong>{{ order.user?.name || 'Customer' }}</strong><br>
                            {{ order.shippingStreet }}, {{ order.shippingNumber }} - {{ order.shippingComplement || 'No complement' }}<br>
                            {{ order.shippingNeighborhood }}<br>
                            {{ order.shippingCity }}, {{ order.shippingState }} - {{ order.shippingZipCode }}<br>
                        </p>
                    </div>

                    <div class="detail-card financial-card">
                        <h3><fa icon="receipt" /> Payment Summary</h3>
                        
                        <div class="financial-row">
                            <span>Subtotal:</span>
                            <span>{{ formatCurrency(order.totalPrice - order.shippingPrice) }}</span>
                        </div>
                        <div class="financial-row">
                            <span>Shipping:</span>
                            <span>{{ formatCurrency(order.shippingPrice) }}</span>
                        </div>
                        <div class="financial-row total-row">
                            <span>Total Paid:</span>
                            <span>{{ formatCurrency(order.totalPrice) }}</span>
                        </div>

                        <div class="payment-method mt-3">
                            <p><strong>Payment Method:</strong> {{ order.paymentMethod }}</p>
                        </div>

                    </div>

                    <div class="detail-card support-card">
                        <h3>Need help?</h3>
                        <p>Did you have any issues with this order?</p>
                        <button class="btn-outline-full mt-2">Contact Support</button>
                        <button v-if="order.status === 'DELIVERED'" class="btn-cancel-full mt-2">Request Return</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    
    <div v-else class="loading-wrapper">
        <p>Loading order details...</p>
    </div>
</template>

<style scoped>
.order-detail-wrapper {
    width: 100%;
    min-height: 100vh;
    background-color: #f8f9fa;
    padding: 2rem;
}

.order-detail-container {
    max-width: 1100px;
    margin: 0 auto;
    animation: fadeIn 0.4s ease-in-out;
}

/* Navegação */
.header-navigation {
    margin-bottom: 1.5rem;
}

.btn-back {
    background: transparent;
    border: none;
    color: #3b7bb9;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    transition: 0.2s;
}

.btn-back:hover {
    color: #1e4770;
}

/* Cards Genéricos */
.detail-card {
    background-color: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.04);
    border: 1px solid #eaeaea;
    padding: 1.5rem;
    margin-bottom: 1.5rem;
}

.detail-card h3 {
    color: #1e4770;
    font-size: 1.1rem;
    margin-bottom: 1rem;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    border-bottom: 1px solid #eaeaea;
    padding-bottom: 0.5rem;
}

/* Summary Header */
.main-summary {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.summary-info h2 {
    color: #1e4770;
    margin: 0 0 0.2rem 0;
}

.order-date {
    color: #718096;
    font-size: 0.9rem;
    margin: 0;
}

/* Status Colors */
.order-status {
    padding: 0.5rem 1.2rem;
    border-radius: 20px;
    font-size: 0.9rem;
    font-weight: bold;
}
.status-delivered { background-color: #c6f6d5; color: #22543d; }
.status-pending { background-color: #feebc8; color: #7b341e; }
.status-shipped { background-color: #bee3f8; color: #2a4365; }
.status-cancelled { background-color: #fed7d7; color: #742a2a; }

/* Grid de Layout */
.details-grid {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 1.5rem;
}

/* Linha do Tempo (Timeline) */
.tracking-code {
    font-size: 0.95rem;
    color: #4a5568;
    margin-bottom: 1.5rem;
}

.timeline {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding-left: 0.5rem;
}

.timeline-item {
    display: flex;
    gap: 1rem;
    position: relative;
    opacity: 0.5;
}

.timeline-item.completed {
    opacity: 1;
}

.timeline-dot {
    width: 14px;
    height: 14px;
    background-color: #cbd5e0;
    border-radius: 50%;
    position: relative;
    z-index: 2;
    margin-top: 5px;
}

.timeline-dot-cancelled {
    width: 14px;
    height: 14px;
    background-color: #c53030;
    border-radius: 50%;
    position: relative;
    z-index: 2;
    margin-top: 5px;
}

.timeline-item.completed .timeline-dot {
    background-color: #3b7bb9;
    box-shadow: 0 0 0 4px rgba(59, 123, 185, 0.2);
}

/* Linha conectando os pontos */
.timeline-item:not(:last-child)::after {
    content: '';
    position: absolute;
    left: 6px;
    top: 20px;
    bottom: -15px;
    width: 2px;
    background-color: #eaeaea;
    z-index: 1;
}
.timeline-item.completed:not(:last-child)::after {
    background-color: #3b7bb9;
}

.timeline-content h4 {
    margin: 0;
    font-size: 0.95rem;
    color: #2d3748;
}

.timeline-content span {
    font-size: 0.8rem;
    color: #718096;
}

/* Itens do Pedido */
.order-item {
    display: flex;
    align-items: center;
    gap: 1rem;
    padding-bottom: 1rem;
    border-bottom: 1px dashed #eaeaea;
    margin-bottom: 1rem;
}
.order-item:last-child {
    border-bottom: none; margin-bottom: 0; padding-bottom: 0;
}

.item-image {
    width: 60px; height: 60px;
    background-color: #f0f6fc;
    border-radius: 8px;
    display: flex; align-items: center; justify-content: center;
    overflow: hidden; flex-shrink: 0;
}
.item-image img { width: 100%; height: 100%; object-fit: cover; }
.placeholder-icon { color: #cbd5e0; font-size: 1.5rem; }

.item-details { flex-grow: 1; }
.product-link { text-decoration: none; color: inherit; }
.product-link:hover h4 { color: #3b7bb9; }
.item-details h4 { margin: 0 0 0.2rem 0; font-size: 0.95rem; }
.item-details p { margin: 0 0 0.2rem 0; font-size: 0.8rem; color: #718096; }
.item-qty { font-size: 0.8rem; color: #a0aec0; font-weight: 600; }
.item-price { font-weight: bold; color: #1e4770; font-size: 1rem; }

/* Endereço */
.address-text {
    font-size: 0.9rem;
    color: #4a5568;
    line-height: 1.5;
    margin: 0;
}

/* Financeiro */
.financial-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 0.5rem;
    color: #4a5568;
    font-size: 0.95rem;
}
.total-row {
    margin-top: 1rem;
    padding-top: 1rem;
    border-top: 1px solid #eaeaea;
    font-weight: bold;
    color: #1e4770;
    font-size: 1.1rem;
}
.mt-3 { margin-top: 1rem; }
.mt-2 { margin-top: 0.5rem; }

.payment-method {
    font-size: 0.85rem;
    color: #718096;
    background-color: #f8f9fa;
    padding: 0.8rem;
    border-radius: 6px;
}
.payment-method p { margin: 0; }

/* Botões */
.btn-outline-full {
    width: 100%;
    padding: 0.8rem;
    background-color: transparent;
    border: 1px solid #cbd5e0;
    color: #4a5568;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
    display: flex; justify-content: center; align-items: center; gap: 0.5rem;
}
.btn-outline-full:hover {
    background-color: #f0f0f0; border-color: #a0aec0;
}

.btn-cancel-full {
    width: 100%;
    padding: 0.8rem;
    background-color: #fff5f5;
    border: 1px solid #fed7d7;
    color: #c53030;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
}
.btn-cancel-full:hover {
    background-color: #fed7d7;
}

.loading-wrapper {
    display: flex; justify-content: center; align-items: center;
    height: 100vh; color: #718096; font-size: 1.2rem;
}

@keyframes fadeIn {
    from { opacity: 0; transform: translateY(-10px); }
    to { opacity: 1; transform: translateY(0); }
}

/* Responsivo */
@media (max-width: 900px) {
    .details-grid { grid-template-columns: 1fr; }
    .main-summary { flex-direction: column; align-items: flex-start; gap: 1rem; }
}
</style>