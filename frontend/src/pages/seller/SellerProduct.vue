<script setup>
import Pagination from '../admin/Pagination.vue'
import {onMounted, ref, watch} from 'vue'
import { useToast } from 'vue-toastification'
import api from '../../services/api'
import '../../style/seller/seller_product.css'


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

        const response = await api.get("/api/products/seller", {params})
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
    fetchProducts()
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

</style>