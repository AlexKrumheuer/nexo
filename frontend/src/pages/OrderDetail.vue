<script setup>
import { ref, onMounted, computed } from 'vue'
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
        console.error('Error searching for order details:', error)
        router.push('/my-orders') 
    } finally {
        loading.value = false
    }
}

const confirmItemDelivery = async (itemId) => {
    if (!confirm('Do you confirm that you received this item in perfect condition?')) return

    try {
        loading.value = true
        await api.put(`/api/orders/${itemId}/confirm-delivery`)
        await fetchOrderDetails()
    } catch (error) {
        console.error('Error confirming item delivery:', error)
    } finally {
        loading.value = false
    }
}

const orderItems = computed(() => {
    return order.value?.orderList || order.value?.items || []
})

const deliveredItems = computed(() => {
    return orderItems.value.filter(i => ['DELIVERED', 'FINISHED'].includes(i.shippingStatus))
})

const cancelledItems = computed(() => {
    return orderItems.value.filter(i => i.shippingStatus === 'CANCELLED')
})

const orderDeliverySummary = computed(() => {
    const total = orderItems.value.length
    if (!total) return { text: order.value?.paymentStatus || 'PENDING', class: 'status-pending' }

    const deliveredCount = deliveredItems.value.length
    const cancelledCount = cancelledItems.value.length

    if (cancelledCount === total) {
        return { text: 'Cancelled', class: 'status-cancelled' }
    }
    if (deliveredCount === total) {
        return { text: 'Delivered', class: 'status-delivered' }
    }
    if (deliveredCount + cancelledCount === total && deliveredCount > 0) {
        return { text: 'Partially Delivered', class: 'status-partial' }
    }
    if (orderItems.value.some(i => i.shippingStatus === 'SHIPPED')) {
        return { text: 'In Transit', class: 'status-shipped' }
    }
    console.log(order.value)
    return { text: order.value?.status === 'PAID' ? 'Paid' : 'Pending Payment', class: order.value?.status === 'PAID' ? 'status-paid' : 'status-awaiting_payment' }
})

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
                    <p class="order-date">Ordered on {{ formatDate(order.createdAt) }}</p>
                </div>
                <div :class="`order-status ${orderDeliverySummary.class}`">
                    {{ orderDeliverySummary.text }}
                </div>
            </div>

            <div v-if="deliveredItems.length > 0 && cancelledItems.length > 0" class="alert-partial-order">
                <fa icon="info-circle" />
                <span>
                    This order contains <strong>{{ deliveredItems.length }} delivered item(s)</strong> and 
                    <strong>{{ cancelledItems.length }} cancelled item(s)</strong>.
                </span>
            </div>

            <div class="details-grid">
                
                <div class="left-column">
                    
                    <div class="detail-card items-card">
                        <h3><fa icon="box" /> Order Products & Shipping Status</h3>
                        
                        <div v-for="item in orderItems" :key="item.id" class="order-item-card">
                            
                            <div class="order-item-header">
                                <div class="item-image">
                                    <img v-if="item.product?.images?.length" :src="item.product.images[0].url" alt="Product Image">
                                    <fa v-else icon="image" class="placeholder-icon" />
                                </div>
                                <div class="item-details">
                                    <router-link :to="`/product/${item.product?.slug}`" class="product-link">
                                        <h4>{{ item.product?.title }}</h4>
                                    </router-link>
                                    <p>Seller: <strong>{{ item.seller?.companyName || 'Nexo Marketplace' }}</strong></p>
                                    <span class="item-qty">Quantity: {{ item.quantity }}</span>
                                    
                                    <p v-if="item.trackingCode" class="tracking-subtext">
                                        Tracking: <strong>{{ item.trackingCode }}</strong>
                                    </p>
                                </div>

                                <div class="item-price-status">
                                    <span :class="`item-status-badge status-${(item.shippingStatus || 'PENDING_SELLER').toLowerCase()}`">
                                        {{ item.shippingStatus || 'PENDING_SELLER' }}
                                    </span>
                                    <div class="item-price">
                                        {{ formatCurrency(item.priceAtPurchase || item.product?.price) }}
                                    </div>
                                </div>
                            </div>

                            <div class="item-delivery-timeline" v-if="!['CANCELLED', 'RETURNED'].includes(item.shippingStatus)">
                                <div class="mini-timeline">
                                    <div class="step active">
                                        <div class="dot"></div>
                                        <span>Order Placed</span>
                                    </div>
                                    <div class="step" :class="{ active: ['PENDING_SELLER', 'AWAITING_SHIPMENT', 'SHIPPED', 'DELIVERED', 'FINISHED'].includes(item.shippingStatus) }">
                                        <div class="dot"></div>
                                        <span>Preparing</span>
                                    </div>
                                    <div class="step" :class="{ active: ['SHIPPED', 'DELIVERED', 'FINISHED'].includes(item.shippingStatus) }">
                                        <div class="dot"></div>
                                        <span>In Transit</span>
                                    </div>
                                    <div class="step" :class="{ active: ['DELIVERED', 'FINISHED'].includes(item.shippingStatus) }">
                                        <div class="dot"></div>
                                        <span>Delivered</span>
                                    </div>
                                </div>
                            </div>

                            <div v-else-if="item.shippingStatus === 'CANCELLED'" class="cancelled-item-banner">
                                <fa icon="times-circle" /> This item was cancelled.
                            </div>

                            <div v-else-if="item.shippingStatus === 'RETURNED'" class="returned-item-banner">
                                <fa icon="undo" /> This item was returned.
                            </div>

                            <div class="item-actions" v-if="item.shippingStatus === 'DELIVERED'">
                                <button class="btn-confirm-item" @click="confirmItemDelivery(item.itemId || item.id)">
                                    <fa icon="circle-check" /> Confirm Delivery for this Item
                                </button>
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
                            <span>{{ formatCurrency(order.subtotal || (order.totalPrice - order.shippingPrice)) }}</span>
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

.order-status, .item-status-badge {
    text-transform: uppercase;
    font-weight: 700;
}

.order-status {
    padding: 0.5rem 1.2rem;
    border-radius: 20px;
    font-size: 0.9rem;
}

.item-status-badge {
    padding: 0.25rem 0.6rem;
    border-radius: 12px;
    font-size: 0.75rem;
}

.status-pending,
.status-awaiting_payment,
.status-pending_seller,
.status-awaiting_shipment,
.item-status-badge.status-pending,
.item-status-badge.status-awaiting_payment,
.item-status-badge.status-pending_seller,
.item-status-badge.status-awaiting_shipment {
    background-color: #feebc8;
    color: #7b341e;
    border: 1px solid #fbd38d;
}

.status-shipped,
.item-status-badge.status-shipped {
    background-color: #bee3f8;
    color: #2a4365;
    border: 1px solid #90cdf4;
}

.status-paid,
.status-delivered,
.status-finished,
.item-status-badge.status-paid,
.item-status-badge.status-delivered,
.item-status-badge.status-finished {
    background-color: #c6f6d5;
    color: #22543d;
    border: 1px solid #9ae6b4;
}

.status-cancelled,
.status-returned,
.item-status-badge.status-cancelled,
.item-status-badge.status-returned {
    background-color: #fed7d7;
    color: #742a2a;
    border: 1px solid #feb2b2;
}

.status-partial {
    background-color: #e2e8f0;
    color: #2d3748;
    border: 1px solid #cbd5e0;
}

.alert-partial-order {
    background-color: #ebf8ff;
    border-left: 4px solid #3182ce;
    color: #2b6cb0;
    padding: 1rem;
    border-radius: 8px;
    margin-bottom: 1.5rem;
    display: flex;
    align-items: center;
    gap: 0.8rem;
    font-size: 0.95rem;
}

.details-grid {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 1.5rem;
}

.order-item-card {
    background: #fcfcfc;
    border: 1px solid #eaeaea;
    border-radius: 8px;
    padding: 1.2rem;
    margin-bottom: 1.2rem;
}

.order-item-card:last-child {
    margin-bottom: 0;
}

.order-item-header {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.item-image {
    width: 65px;
    height: 65px;
    background-color: #f0f6fc;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    flex-shrink: 0;
}

.item-image img { width: 100%; height: 100%; object-fit: cover; }
.placeholder-icon { color: #cbd5e0; font-size: 1.5rem; }

.item-details { flex-grow: 1; }
.product-link { text-decoration: none; color: inherit; }
.product-link:hover h4 { color: #3b7bb9; }
.item-details h4 { margin: 0 0 0.2rem 0; font-size: 0.95rem; }
.item-details p { margin: 0 0 0.2rem 0; font-size: 0.8rem; color: #718096; }
.item-qty { font-size: 0.8rem; color: #a0aec0; font-weight: 600; }
.tracking-subtext { font-size: 0.8rem; color: #4a5568; margin-top: 0.2rem; }

.item-price-status {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 0.5rem;
}

.item-price { font-weight: bold; color: #1e4770; font-size: 1rem; }

.item-delivery-timeline {
    margin-top: 1rem;
    padding-top: 0.8rem;
    border-top: 1px dashed #eaeaea;
}

.mini-timeline {
    display: flex;
    justify-content: space-between;
    position: relative;
    margin: 0.5rem 0;
}

.mini-timeline .step {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 0.75rem;
    color: #a0aec0;
    z-index: 2;
}

.mini-timeline .step.active {
    color: #3b7bb9;
    font-weight: bold;
}

.mini-timeline .dot {
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: #cbd5e0;
    margin-bottom: 0.3rem;
}

.mini-timeline .step.active .dot {
    background-color: #3b7bb9;
    box-shadow: 0 0 0 3px rgba(59, 123, 185, 0.2);
}

.cancelled-item-banner {
    margin-top: 0.8rem;
    padding: 0.5rem;
    background-color: #fff5f5;
    color: #c53030;
    font-size: 0.8rem;
    border-radius: 6px;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.returned-item-banner {
    margin-top: 0.8rem;
    padding: 0.5rem;
    background-color: #fff5f5;
    color: #c53030;
    font-size: 0.8rem;
    border-radius: 6px;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.item-actions {
    display: flex;
    justify-content: flex-end;
    margin-top: 0.8rem;
}

.btn-confirm-item {
    background-color: #276749;
    color: #ffffff;
    border: none;
    padding: 0.4rem 0.9rem;
    font-size: 0.8rem;
    border-radius: 6px;
    font-weight: 600;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.4rem;
    transition: background 0.2s;
}

.btn-confirm-item:hover {
    background-color: #22543d;
}

.address-text {
    font-size: 0.9rem;
    color: #4a5568;
    line-height: 1.5;
    margin: 0;
}

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
}

.btn-outline-full:hover {
    background-color: #f0f0f0;
    border-color: #a0aec0;
}

.loading-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    color: #718096;
    font-size: 1.2rem;
}

@keyframes fadeIn {
    from { opacity: 0; transform: translateY(-10px); }
    to { opacity: 1; transform: translateY(0); }
}

@media (max-width: 900px) {
    .details-grid { grid-template-columns: 1fr; }
    .main-summary { flex-direction: column; align-items: flex-start; gap: 1rem; }
}
</style>