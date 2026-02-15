<script setup>
import api from '../../../services/api';
import '../../../style/admin/admin-global.css'
import AdminFilter from './AdminFilter.vue'
import {onMounted, ref} from 'vue'
import Pagination from '../Pagination.vue';

const categoryList = ref([])
const loading = ref(true)

const currentPage = ref(0)
const size = ref(10)
const totalItems = ref(0)
const totalPages = ref(0)

const fetchCategory = async () => {
    loading.value = true
    try {
        const response = await api.get(`/api/categories?page=${currentPage.value}&size=${size.value}`)
        categoryList.value = response.data.content

        totalItems.value = response.data.totalElements
        totalPages.value = response.data.totalPages
    } catch(error) {
        console.error("Error finding categories: " + error.message || error)
    } finally {
        loading.value = false
    }
}


onMounted(() => {
    fetchCategory()
})

const changePage = (newPage) => {
    currentPage.value = newPage
    fetchCategory()
    
}

const changeSize = (newSize) => {
    size.value = newSize
    currentPage.value = 0
    fetchCategory()
}

</script>

<template>
    <AdminFilter placeholder="Search categories..." btnText="Add Category">
        <template #filters>
            <select class="filter-select">
                <option value="">Status: All</option>
                <option value="">Active</option>
            </select>
        </template>
    </AdminFilter>
    <p v-if="loading" style="text-align: center;">Loading...</p>

    <div v-else class="table-wrapper">
        <table class="admin-table">
            <thead>
                <tr>
                    <th>Category Name</th>
                    <th>Description</th>
                    <th>Products</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr class="table-item" v-for="category in categoryList" :key="category.id">
                    <td>
                        <div class="flex-center-cell">
                            <strong>{{ category.name}}</strong>
                        </div>
                    </td>
                    <td>{{ category.description }}</td>
                    <td>1,240</td>
                    <td><span class="status-badge success">{{category.active}}</span></td>
                    <td>
                        <div class="flex-center-cell">
                            <button class="action-btn edit">
                                <fa icon="edit" /> <span>Edit</span>
                            </button>
                            <button class="action-btn ban">
                                <fa icon="trash" /> <span>Delete</span>
                            </button>
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