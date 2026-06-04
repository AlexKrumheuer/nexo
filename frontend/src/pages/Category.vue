<script setup>
import { ref, onMounted, computed } from 'vue';
import api from '../services/api';
import '../style/main_content/category.css'


const categories = ref([])
const loading = ref(true)

const categoryTree = computed(() => {
    const map = {}
    const roots = []

    categories.value.forEach(cat => {
        map[cat.id] = { ...cat, children: [] }
    })

    categories.value.forEach(cat => {
        if (cat.parentId !== null) {
            if (map[cat.parentId]) {
                map[cat.parentId].children.push(map[cat.id])
            }
        } else {
            roots.push(map[cat.id])
        }
    })
    return roots
})

onMounted(async () => {
    try {
        const response = await api.get('/api/categories?size=1000');
        categories.value = response.data.content.filter(c => c.active)
    } catch (error) {
        console.error("Error loading categories", error.message || error)
    } finally {
        loading.value = false
    }
})
</script>

<template>
    <div class="page-wrapper">
        <div class="page-container">
            <div class="section-header">
                <h1>Category</h1>
                <p>Explore Categories for buying and selling</p>
            </div>
            
            <div v-if="loading" class="loading-state">
                <div class="spinner"></div>
            </div>

            <div v-else class="grid-layout">
                <div 
                    v-for="rootCategory in categoryTree" 
                    :key="rootCategory.id" 
                    class="category-card"
                >
                    <router-link 
                        :to="`/search?category=${rootCategory.slug}`" 
                        class="card-header"
                    >
                        <div class="icon-wrapper">
                            <img v-if="rootCategory.imageUrl" :src="rootCategory.imageUrl" :alt="rootCategory.name">
                            <span v-else class="placeholder-icon">{{ rootCategory.name.charAt(0) }}</span>
                        </div>
                        <h3>{{ rootCategory.name }}</h3>
                    </router-link>

                    <div class="card-body">
                        <ul class="subcategory-list">
                            <li v-for="child in rootCategory.children.slice(0, 5)" :key="child.id">
                                <router-link :to="`/search?category=${child.slug}`">
                                    {{ child.name }}
                                </router-link>
                            </li>
                        </ul>
                    </div>

                    <div class="card-footer" v-if="rootCategory.children.length > 0">
                        <router-link :to="`/search?category=${rootCategory.slug}`" class="view-all-link">
                            See {{ rootCategory.children.length }} Categories 
                            <span class="arrow">→</span>
                        </router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>