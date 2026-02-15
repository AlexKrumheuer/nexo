<script setup>
import '../../../style/admin/admin-global.css'
import AdminFilter from './AdminFilter.vue'
import api from '../../../services/api';
import {onMounted, ref} from 'vue'
import Pagination from '../Pagination.vue';


const formatPrice = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}

const productList = ref([])
const loading = ref(true)

const currentPage = ref(0)
const size = ref(10)
const totalItems = ref(0)
const totalPages = ref(0)

const fetchProductList = async () => {
    loading.value = true
    try {
        const response = await api.get(`/api/products?page=${currentPage.value}&size=${size.value}`)
        productList.value = response.data.content

        totalItems.value = response.data.totalElements
        totalPages.value = response.data.totalPages
    } catch(error) {
        console.error("Error finding categories: " + error.message || error)
    } finally {
        loading.value = false
    }
}


onMounted(() => {
    fetchProductList()
})

const changePage = (newPage) => {
    currentPage.value = newPage
    fetchProductList()
    
}

const changeSize = (newSize) => {
    size.value = newSize
    currentPage.value = 0
    fetchProductList()
}



</script>

<template>
    <AdminFilter placeholder="Search for product..." btnText="Add Product">
        <template #filters>
            <select class="filter-select">
                <option value="">Category</option>
                <option value="">Price (Desc)</option>
                <option value="">Price (Asc)</option>
                <option value="">Status</option>
                <option value="">Stock (Desc)</option>
                <option value="">Stock (Asc)</option>
            </select>
        </template>
    </AdminFilter>

    <p v-if="loading" style="text-align: center;">Loading...</p>


    <div v-else class="table-wrapper">
        <table class="admin-table">
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr class="table-item" v-for="product in productList" :key="product.id">
                    <td>
                        <div class="flex-center-cell">
                            <div class="id-cell__img">
                                <img :src=" product.images[0]" alt="Product">
                            </div>
                            <p>{{ product.title }}</p>
                        </div>
                    </td>
                    <td>Electronics</td>
                    <td>{{ formatPrice(product.price) }}</td>
                    <td>{{product.stockQuantity}}</td>
                    <td><span class="status-badge success">active</span></td>
                    <td>
                        <div class="flex-center-cell">
                            <button class="action-btn edit" title="Edit">
                                <fa icon="edit" /> <span>Edit</span>
                            </button>
                            <button class="action-btn ban" title="Delete">
                                <fa icon="trash" /> <span>Delete</span>
                            </button>
                            <p class="view-details">Details</p>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
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