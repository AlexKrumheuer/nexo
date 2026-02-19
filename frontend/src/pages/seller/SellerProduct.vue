<script setup>
import Pagination from '../admin/Pagination.vue'
import {onMounted, ref, watch} from 'vue'
import { useToast } from 'vue-toastification'
import api from '../../services/api'

const toast = useToast()

const loading = ref(false)
const currentPage = ref(0)
const totalPages = ref(0)
const totalItems = ref(0)
const size = ref(30)

const categories = ref([])
const products = ref([])

const formFilters = ref({
    categoryId: null,
    active: null,
    stock: null,
})

const searchInput = ref('')

const fetchProducts = async () => {
    loading.value = true
    try {
        const params = {
            page: currentPage.value,
            size: size.value,
            search: searchInput.value,
            categoryId: formFilters.value.categoryId,
            active: formFilters.value.active,
            stock: formFilters.value.stock
        }

        const response = await api.get("/api/products", {params})
        products.value = response.data.content
    } catch(e) {
        console.error("Error fetching products: " + e.message || e)
        toast.error("Error fetching products, try realoding the page")
    } finally {
        loading.value = false
    }
}

const fetchCategories = async () => {
    try {
        const response = await api.get('/api/categories?size=1000');
        categories.value = response.data.content.filter(c => c.active)
    }catch(e) {
        console.error("Error getting categories: " + e.message || e)
        toast.error("Error getting categories, try again later...")
    }finally {
        loading.value = true
    }
}

onMounted(() => {
    fetchCategories()
})

watch(formFilters, ()=> {
    currentPage.value = 0
    fetchProducts()
}, { deep: true })

</script>
<template>
    <div class="products-page">
        <div class="page-header">
            <div class="titles">
                <h1>My Products</h1>
                <p>Manage your catalog, prices and stock.</p>
            </div>
            <router-link to="/seller/product/new" class="btn-primary">
                <fa icon="plus" /> New Product
            </router-link>
        </div>

        <div class="toolbar">
            <div class="search-box">
                <fa icon="search" class="search-icon-seller" />
                <input type="text" placeholder="Search for product name..." v-model="searchInput"/>
            </div>
            <div>
                <select v-model="formFilters.categoryId">
                    <option :value="null" disabled="true" selected>Select a category to filter</option>
                    <option :value="category.id" v-for="category in categories" :key="category.id">{{ category.name }}</option>
                </select>
            </div>
            <div>
                <select v-model="formFilters.active">
                    <option :value="null" disabled="true" selected>Filter by product status</option>
                    <option :value="true">Active</option>
                    <option :value="false">Inactive</option>
                    <option :value="null">Both</option>
                </select>
            </div>
            <div>
                <select v-model="formFilters.stock">
                    <option :value="null" disabled="true" selected>Filter by product stock</option>
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
                        <th width="80">Image</th>
                        <th>Product <fa icon="angle-down"/></th>
                        <th>Category <fa icon="angle-down"/></th>
                        <th>Price <fa icon="angle-down"/></th>
                        <th>Stock <fa icon="angle-down"/></th>
                        <th>Status <fa icon="angle-down"/></th>
                        <th width="100">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="product in products" :key="product.id">
                        <td>
                            <div class="img-wrapper">
                                <img :src="product.images[0].url" alt="Product Image" />
                            </div>
                        </td>
                        <td>
                            <div class="product-info">
                                <span class="name">{{ product.title }}</span>
                                <span class="sku">{{ product.sku }}</span>
                            </div>
                        </td>
                        <td>{{ product.category.name }}</td>
                        <td class="price">{{product.price}}</td>
                        <td>
                            <span v-if="product.categoryId === 1 && product.stockQuantity === 1" class="badge-exclusive">
                                Last Unity
                            </span>

                            <span v-else-if="product.stockQuantity <= 5" class="low-stock">
                                {{ product.stockQuantity }} uni. (low)
                            </span>

                            <span v-else>
                                {{ product.stockQuantity }} uni.
                            </span>
                        </td>
                        <td>
                            <span class="status-badge active">{{product.status}}</span>
                        </td>
                        <td>
                            <div class="actions">
                                <button class="action-btn edit">
                                    <fa icon="pen" />
                                </button>
                                <button class="action-btn delete">
                                    <fa icon="trash" />
                                </button>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <div class="img-wrapper">
                                <img src="https://placehold.co/50" alt="Prod" />
                            </div>
                        </td>
                        <td>
                            <div class="product-info">
                                <span class="name">Notebook Gamer Dell</span>
                                <span class="sku">ID: #1025</span>
                            </div>
                        </td>
                        <td>Informática</td>
                        <td class="price">R$ 6.200,00</td>
                        <td>
                            <span class="low-stock">2 unid.</span>
                        </td>
                        <td>
                            <span class="status-badge active">Ativo</span>
                        </td>
                        <td>
                            <div class="actions">
                                <button class="action-btn edit">
                                    <fa icon="pen" />
                                </button>
                                <button class="action-btn delete">
                                    <fa icon="trash" />
                                </button>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <div class="img-wrapper">
                                <img src="https://placehold.co/50" alt="Prod" />
                            </div>
                        </td>
                        <td>
                            <div class="product-info">
                                <span class="name">Fone Bluetooth JBL</span>
                                <span class="sku">ID: #1026</span>
                            </div>
                        </td>
                        <td>Acessórios</td>
                        <td class="price">R$ 250,00</td>
                        <td>
                            <span>0 unid.</span>
                        </td>
                        <td>
                            <span class="status-badge inactive">Inativo</span>
                        </td>
                        <td>
                            <div class="actions">
                                <button class="action-btn edit">
                                    <fa icon="pen" />
                                </button>
                                <button class="action-btn delete">
                                    <fa icon="trash" />
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <Pagination 
        v-if="!loading"
        :page="currentPage"
        :totalPages="totalPages"
        :totalItems="totalItems"
        :size="size"
        @change-page="changePage"
        @change-size="changeSize"
    >
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

.btn-primary {
    background-color: #3b82f6;
    color: white;
    padding: 10px 20px;
    border-radius: 8px;
    text-decoration: none;
    font-weight: 500;
    display: flex;
    align-items: center;
    gap: 8px;
    transition: background 0.2s;
    border: none;
    cursor: pointer;
}

.btn-primary:hover {
    background-color: #2563eb;
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
    box-shadow: 0 1px 3px rgba(0,0,0,0.05);
    overflow: hidden;
    border: 1px solid #e2e8f0;
}

table {
    width: 100%;
    border-collapse: collapse;
}

thead {
    background-color: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
}

th {
    text-align: left;
    padding: 15px;
    font-size: 0.85rem;
    font-weight: 600;
    color: #64748b;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    cursor: pointer;
    transition: 0.25s;
}

th:hover {
    color: #0ea5e9;
}

td {
    padding: 15px;
    border-bottom: 1px solid #f1f5f9;
    color: #334155;
    vertical-align: middle;
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
}

.product-info .sku {
    font-size: 0.75rem;
    color: #94a3b8;
}

.price {
    font-weight: 600;
    color: #0f172a;
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
}

.status-badge.active {
    background-color: #dcfce7;
    color: #166534;
}

.status-badge.inactive {
    background-color: #f1f5f9;
    color: #64748b;
}

.actions {
    display: flex;
    gap: 8px;
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

input:focus, select:focus, textarea:focus {
    border-color: #3b82f6;
    box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
}
</style>