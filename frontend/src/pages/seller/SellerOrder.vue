<script setup>
import Pagination from '../admin/Pagination.vue'
import { onMounted, ref, watch } from 'vue'
import { useToast } from 'vue-toastification'
import api from '../../services/api'

const toast = useToast()

const loading = ref(false)
const currentPage = ref(0)
const totalPages = ref(0)
const totalItems = ref(0)
const size = ref(30)

const categories = ref([])
const sellerOrders = ref([])

const formFilters = ref({
    categoryId: null,
    active: null,
    stock: null,
})

const searchInput = ref('')

const isModalOpen = ref(false)
const selectedOrder = ref(null)

const fetchSellerOrders = async () => {
    loading.value = true
    try {
        const params = {
            page: currentPage.value,
            size: size.value,
            categoryId: formFilters.value.categoryId,
            stock: formFilters.value.stock,
            search: searchInput.value
        }
        const response = await api.get("/api/orders/seller", { params })
        sellerOrders.value = response.data.content
        console.log(sellerOrders.value)
    } catch (e) {
        console.error("Error fetching seller orders: " + (e.message || e))
        toast.error("Error fetching seller orders, try reloading the page")
    } finally {
        loading.value = false
    }
}

const fetchCategories = async () => {
    try {
        const response = await api.get('/api/categories?size=1000');
        categories.value = response.data.content.filter(c => c.active)
    } catch (e) {
        console.error("Error getting categories: " + (e.message || e))
        toast.error("Error getting categories, try again later...")
    } finally {
        loading.value = true
    }
}

const viewOrderDetails = (sellerOrder) => {
    selectedOrder.value = sellerOrder
    isModalOpen.value = true
}

const closeModal = () => {
    isModalOpen.value = false
    selectedOrder.value = null
}

const acceptOrder = async (sellerOrder) => {
    try {
        console.log(sellerOrder.id)
        await api.put(`/api/orders/seller/${sellerOrder.id}/accept`)
        toast.success("Order accepted successfully!")
        fetchSellerOrders()
    } catch (e) {
        console.error("Error accepting order: " + (e.message || e))
        toast.error("Error accepting order, try again later...")
    }
}

const declineOrder = async (sellerOrder) => {
    try {
        await api.put(`/api/orders/seller/${sellerOrder.id}/decline`)
        toast.success("Order declined successfully!")
        fetchSellerOrders()
    } catch (e) {
        console.error("Error declining order: " + (e.message || e))
        toast.error("Error declining order, try again later...")
    }
}

const shipOrder = async (sellerOrder) => {
    try {
        await api.put(`/api/orders/seller/${sellerOrder.id}/ship`)
        toast.success("Order marked as shipped successfully!")
        fetchSellerOrders()
    } catch (e) {
        console.error("Error marking order as shipped: " + (e.message || e))
        toast.error("Error marking order as shipped, try again later...")
    }
}

onMounted(() => {
    fetchCategories()
    fetchSellerOrders()
})

watch(formFilters, () => {
    currentPage.value = 0
    fetchSellerOrders()
}, { deep: true })

const formatCurrency = (value) => {
    if (value === undefined || value === null) return 'R$ 0,00'
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}

const formatDate = (dateString) => {
    if (!dateString) return ''
    return new Date(dateString).toLocaleDateString('US', {
        day: '2-digit', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit'
    })
}
</script>

<template>
    <div class="products-page">
        <div class="page-header">
            <div class="titles">
                <h1>My Orders</h1>
                <p>Manage your orders and track their status.</p>
            </div>
        </div>

        <div class="toolbar">
            <div class="search-box">
                <fa icon="search" class="search-icon-seller" />
                <input type="text" placeholder="Search for product name..." v-model="searchInput" />
            </div>
            <div>
                <select v-model="formFilters.categoryId">
                    <option :value="null" disabled selected>Select a category to filter</option>
                    <option :value="category.id" v-for="category in categories" :key="category.id">{{ category.name }}</option>
                </select>
            </div>
            <div>
                <select v-model="formFilters.active">
                    <option :value="null" disabled selected>Filter by product status</option>
                    <option :value="true">Active</option>
                    <option :value="false">Inactive</option>
                    <option :value="null">Both</option>
                </select>
            </div>
            <div>
                <select v-model="formFilters.stock">
                    <option :value="null" disabled selected>Filter by product stock</option>
                    <option :value="'IN_STOCK'">In Stock</option>
                    <option :value="'LOW_STOCK'">Low Stock</option>
                    <option :value="'OUT_OF_STOCK'">Out of Stock</option>
                    <option :value="null">All Stock Status</option>
                </select>
            </div>
        </div>

        <div class="table-card">
            <table>
                <thead>
                    <tr>
                        <th>Order Code</th>
                        <th>Date</th>
                        <th width="80" class="text-center">Image</th>
                        <th>
                            <div class="th-content">
                                Product <fa icon="angle-down" />
                            </div>
                        </th>
                        <th>
                            <div class="th-content">
                                Category <fa icon="angle-down" />
                            </div>
                        </th>
                        <th class="text-right">
                            <div class="th-content end">
                                Price <fa icon="angle-down" />
                            </div>
                        </th>
                        <th>
                            <div class="th-content">
                                Stock <fa icon="angle-down" />
                            </div>
                        </th>
                        <th class="text-center">
                            <div class="th-content center">
                                Status <fa icon="angle-down" />
                            </div>
                        </th>
                        <th class="text-center">
                            <div class="th-content center">
                                QNT <fa icon="angle-down" />
                            </div>
                        </th>
                        <th width="140" class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="sellerOrder in sellerOrders" :key="sellerOrder.id">
                        <td class="first-row">
                            {{ sellerOrder.orderCode }}
                        </td>
                        <td>
                            <span>{{ formatDate(sellerOrder.createdAt) }}</span>
                        </td>
                        <td class="text-center">
                            <div class="img-wrapper">
                                <img :src="sellerOrder.product.images[0]?.url" alt="Product Image" />
                            </div>
                        </td>
                        <td>
                            <div class="product-info">
                                <span class="name">{{ sellerOrder.product.title }}</span>
                                <span class="sku">{{ sellerOrder.product.sku }}</span>
                            </div>
                        </td>
                        <td>{{ sellerOrder.product.category?.name }}</td>
                        <td class="price text-right">{{ formatCurrency(sellerOrder.product.price) }}</td>
                        <td>
                            <span v-if="sellerOrder.product.categoryId === 1 && sellerOrder.product.stockQuantity === 1"
                                class="badge-exclusive">
                                Last Unity
                            </span>

                            <span v-else-if="sellerOrder.product.stockQuantity <= 5" class="low-stock">
                                {{ sellerOrder.product.stockQuantity }} uni. (low)
                            </span>

                            <span v-else>
                                {{ sellerOrder.product.stockQuantity }} uni.
                            </span>
                        </td>
                        <td class="text-center">
                            <span  class="status-badge active">{{ sellerOrder.shippingStatus === 'PENDING_SELLER' ? sellerOrder.status : sellerOrder.shippingStatus }}</span>
                        </td>
                        <td class="text-center">
                            <span>{{ sellerOrder.quantity }} item</span>
                        </td>
                        <td class="text-center">
                            <div class="actions">
                                <button class="action-btn view" title="Customer & Order Details" @click="viewOrderDetails(sellerOrder)">
                                    <fa icon="eye" />
                                </button>
                                <button v-if="sellerOrder.status == 'PAID' && sellerOrder.shippingStatus == 'PENDING_SELLER'" class="action-btn edit" title="Accept order" @click="acceptOrder(sellerOrder)">
                                    <fa icon="check" />
                                </button>
                                <button v-else-if="sellerOrder.shippingStatus == 'AWAITING_SHIPMENT'" class="action-btn edit" title="SHIP ORDER" @click="shipOrder(sellerOrder)">
                                    <fa icon="truck" />
                                </button>        
                                <button v-if="sellerOrder.shippingStatus == AWAITING_SHIPMENT || sellerOrder.shippingStatus == PENDING_SELLER" class="action-btn delete" title="Decline order" @click="declineOrder(sellerOrder)">
                                    <fa icon="times" />
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div v-if="isModalOpen && selectedOrder" class="modal-backdrop" @click.self="closeModal">
        <div class="modal-card">
            <div class="modal-header">
                <div>
                    <h2>Customer & Order Details</h2>
                    <span class="order-code">Order Code: #{{ selectedOrder.orderCode }}</span>
                </div>
                <button class="modal-close" @click="closeModal">&times;</button>
            </div>

            <div class="modal-body">
                <div class="modal-section">
                    <h3><fa icon="user" class="section-icon" /> Customer Information</h3>
                    <div class="info-card-grid">
                        <div class="info-item">
                            <span class="label">Name</span>
                            <span class="value">{{ selectedOrder.user?.username ||  'N/A' }}</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Email</span>
                            <span class="value">{{ selectedOrder.user?.email || 'N/A' }}</span>
                        </div>
                    </div>
                </div>

                <div class="modal-section">
                    <h3><fa icon="map-marker-alt" class="section-icon" /> Shipping Address</h3>
                    <div class="address-card">
                        <p class="address-line">
                            <strong>{{ selectedOrder.shippingStreet || 'Main Street' }}</strong>, 
                            {{ selectedOrder.shippingNumber || '123' }}
                            <span v-if="selectedOrder.shippingComplement"> - {{ selectedOrder.shippingComplement }}</span>
                        </p>
                        <p class="address-subline">
                            {{ selectedOrder.shippingNeighborhood || 'District' }} — 
                            {{ selectedOrder.shippingCity || 'City' }} / {{ selectedOrder.shippingState || 'ST' }}
                        </p>
                        <span class="cep-badge">CEP: {{ selectedOrder.shippingZipCode || '00000-000' }}</span>
                    </div>
                </div>

                <div class="modal-section">
                    <h3><fa icon="box" class="section-icon" /> Purchased Item</h3>
                    <div class="product-item-row">
                        <img :src="selectedOrder.product.images[0]?.url" alt="Product" class="item-img" />
                        <div class="item-info">
                            <span class="item-title">{{ selectedOrder.product.title }}</span>
                            <span class="item-meta">SKU: {{ selectedOrder.product.sku }} | Qty: <strong>{{ selectedOrder.quantity }}</strong></span>
                        </div>
                        <div class="item-price">
                            {{ formatCurrency(selectedOrder.product.price * selectedOrder.quantity) }}
                        </div>
                    </div>
                </div>

                <!-- Summary -->
                <div class="summary-footer-box">
                    <div class="summary-col">
                        <span class="summary-label">Order Date</span>
                        <span class="summary-val">{{ formatDate(selectedOrder.createdAt) }}</span>
                    </div>
                    <div class="summary-col">
                        <span class="summary-label">Status</span>
                        <span class="status-badge active">{{ selectedOrder.status }}</span>
                    </div>
                    <div class="summary-col text-right">
                        <span class="summary-label">Total Amount</span>
                        <span class="total-price-large">{{ formatCurrency(selectedOrder.product.price * selectedOrder.quantity) }}</span>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button class="btn-close-modal" @click="closeModal">Close</button>
            </div>
        </div>
    </div>

    <Pagination v-if="!loading" :page="currentPage" :totalPages="totalPages" :totalItems="totalItems" :size="size"
        @change-page="changePage" @change-size="changeSize">
    </Pagination>
</template>

<style scoped>
.products-page {
    display: flex;
    flex-direction: column;
    gap: 1.3rem;
    padding: 2rem;
}

.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.page-header h1 {
    font-size: 1.5rem;
    color: #1e293b;
    margin: 0;
}

.page-header p {
    color: #64748b;
    font-size: 0.9rem;
    margin: 5px 0 0 0;
}

.toolbar {
    display: flex;
    gap: 15px;
}

.search-box {
    position: relative;
    flex: 1;
    max-width: 400px;
}

.search-icon-seller {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    color: #94a3b8;
}

.search-box input {
    width: 100%;
    padding: 10px 10px 10px 38px;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    outline: none;
    color: #334155;
}

.search-box input:focus {
    border-color: #3b82f6;
    box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
}

.table-card {
    background: white;
    border-radius: 12px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
    overflow: hidden;
    border: 1px solid #e2e8f0;
    
}

table {
    width: 100%;
    border-collapse: collapse;
    min-width: 1000px;
    border-collapse: collpase;
}

thead {
    background-color: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
    padding: 12px 15px;
    vertical-align: middle;
    white-space: nowrap;
}

th, td {
    padding: 12px 10px;
    vertical-align: middle;
}

th {
    text-align: left;
    font-size: 0.85rem;
    font-weight: 600;
    color: #64748b;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    cursor: pointer;
    transition: 0.25s;
    white-space: nowrap;
}

th:hover {
    color: #0ea5e9;
}

th .th-content {
    display: inline-flex;
    align-items: center;
    gap: 6px;
}

th .th-content.center {
    justify-content: center;
}

th .th-content.end {
    justify-content: flex-end;
}

td {
    border-bottom: 1px solid #f1f5f9;
    color: #334155;
}

.text-center {
    text-align: center;
}

.text-right {
    text-align: right;
}
.first-row {
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.img-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
}

.img-wrapper img {
    width: 45px;
    height: 45px;
    border-radius: 6px;
    object-fit: cover;
    border: 1px solid #e2e8f0;
}

.product-info {
    display: flex;
    flex-direction: column;
}

.product-info .name {
    font-weight: 500;
    color: #0f172a;
    white-space: normal;
}

.product-info .sku {
    font-size: 0.75rem;
    color: #94a3b8;
}

.price {
    font-weight: 600;
    color: #0f172a;
    font-size: 1rem;
}

.low-stock {
    color: #dc2626;
    font-weight: bold;
}

.status-badge {
    padding: 4px 10px;
    border-radius: 20px;
    font-size: 0.75rem;
    font-weight: 600;
    display: inline-block;
}

.status-badge.active {
    background-color: #dcfce7;
    color: #166534;
}

.actions {
    display: flex;
    gap: 8px;
    justify-content: center;
}

.action-btn {
    width: 32px;
    height: 32px;
    border-radius: 6px;
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.2s;
}

.action-btn.view {
    background-color: #f1f5f9;
    color: #475569;
}

.action-btn.view:hover {
    background-color: #e2e8f0;
    color: #0f172a;
}

.action-btn.edit {
    background-color: #e0f2fe;
    color: #0ea5e9;
}

.action-btn.edit:hover {
    background-color: #bae6fd;
}

.action-btn.delete {
    background-color: #fee2e2;
    color: #ef4444;
}

.action-btn.delete:hover {
    background-color: #fecaca;
}

input, select, textarea {
    padding: 10px 12px;
    border: 1px solid #cbd5e1;
    border-radius: 6px;
    font-size: 0.95rem;
    outline: none;
    color: #334155;
    background-color: white;
    transition: border 0.2s;
}

/* Modal Estilos para Dados do Cliente */
.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(15, 23, 42, 0.55);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    backdrop-filter: blur(4px);
}

.modal-card {
    background: white;
    width: 100%;
    max-width: 620px;
    border-radius: 14px;
    box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
    overflow: hidden;
    animation: fadeIn 0.2s ease-out;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-8px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    padding: 1.25rem 1.5rem;
    border-bottom: 1px solid #e2e8f0;
    background-color: #f8fafc;
}

.modal-header h2 {
    margin: 0;
    font-size: 1.25rem;
    color: #0f172a;
}

.order-code {
    font-size: 0.85rem;
    color: #64748b;
}

.modal-close {
    background: transparent;
    border: none;
    font-size: 1.5rem;
    color: #94a3b8;
    cursor: pointer;
}

.modal-close:hover {
    color: #0f172a;
}

.modal-body {
    padding: 1.5rem;
    display: flex;
    flex-direction: column;
    gap: 1.25rem;
    max-height: 75vh;
    overflow-y: auto;
}

.modal-section h3 {
    font-size: 0.85rem;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    color: #475569;
    margin: 0 0 0.6rem 0;
    display: flex;
    align-items: center;
    gap: 6px;
}

.section-icon {
    color: #3b82f6;
}

.info-card-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 0.75rem;
    background-color: #f8fafc;
    padding: 1rem;
    border-radius: 8px;
    border: 1px solid #e2e8f0;
}

.info-item {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.info-item .label {
    font-size: 0.75rem;
    color: #64748b;
    text-transform: uppercase;
}

.info-item .value {
    font-size: 0.9rem;
    font-weight: 500;
    color: #1e293b;
    word-break: break-word;
}

.address-card {
    background-color: #f8fafc;
    padding: 1rem;
    border-radius: 8px;
    border: 1px solid #e2e8f0;
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.address-line {
    margin: 0;
    color: #1e293b;
    font-size: 0.95rem;
}

.address-subline {
    margin: 0;
    color: #64748b;
    font-size: 0.85rem;
}

.cep-badge {
    margin-top: 6px;
    display: inline-block;
    align-self: flex-start;
    background-color: #e2e8f0;
    color: #334155;
    font-size: 0.75rem;
    font-weight: 600;
    padding: 2px 8px;
    border-radius: 4px;
}

.product-item-row {
    display: flex;
    align-items: center;
    gap: 1rem;
    padding: 0.75rem 1rem;
    background-color: #ffffff;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
}

.item-img {
    width: 50px;
    height: 50px;
    border-radius: 6px;
    object-fit: cover;
    border: 1px solid #cbd5e1;
}

.item-info {
    flex: 1;
    display: flex;
    flex-direction: column;
}

.item-title {
    font-weight: 600;
    color: #0f172a;
    font-size: 0.95rem;
}

.item-meta {
    font-size: 0.8rem;
    color: #64748b;
}

.item-price {
    font-weight: 600;
    color: #0f172a;
    font-size: 1rem;
}

.summary-footer-box {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
    background-color: #f0f9ff;
    border: 1px solid #bae6fd;
    border-radius: 8px;
}

.summary-col {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.summary-label {
    font-size: 0.75rem;
    color: #0369a1;
    text-transform: uppercase;
    font-weight: 500;
}

.summary-val {
    font-size: 0.9rem;
    font-weight: 600;
    color: #0f172a;
}

.total-price-large {
    font-size: 1.3rem;
    font-weight: 700;
    color: #0284c7;
}

.modal-footer {
    padding: 1rem 1.5rem;
    border-top: 1px solid #e2e8f0;
    display: flex;
    justify-content: flex-end;
    background-color: #f8fafc;
}

.btn-close-modal {
    background-color: #e2e8f0;
    color: #334155;
    border: none;
    padding: 8px 18px;
    border-radius: 6px;
    font-weight: 500;
    cursor: pointer;
    transition: background 0.2s;
}

.btn-close-modal:hover {
    background-color: #cbd5e1;
}
</style>