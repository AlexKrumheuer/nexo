<script setup>
import { ref, onMounted, computed } from 'vue';
import api from '../services/api';

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
        const response = await api.get('/api/categories');
        categories.value = response.data.filter(c => c.active)
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

<style scoped>
.page-wrapper {
    background-color: #f8f9fa;
    min-height: 100vh;
    padding: 60px 0;
}

.page-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 24px;
    font-family: 'Inter', sans-serif;
}

.section-header {
    margin-bottom: 50px;
    text-align: center;
}

.section-header h1 {
    font-size: 2.5rem;
    font-weight: 800;
    color: #111;
    margin-bottom: 10px;
    letter-spacing: -0.02em;
}

.section-header p {
    color: #666;
    font-size: 1.1rem;
}

.grid-layout {
    display: grid;
    grid-template-columns: repeat(3, 1fr); 
    gap: 32px;
}

@media (max-width: 1024px) {
    .grid-layout {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 640px) {
    .grid-layout {
        grid-template-columns: 1fr;
    }
}

.category-card {
    background: #fff;
    border-radius: 16px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.03);
    transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
    display: flex;
    flex-direction: column;
    border: 1px solid rgba(0,0,0,0.04);
}

.category-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 15px 30px rgba(0,0,0,0.08);
}

.card-header {
    padding: 24px;
    display: flex;
    align-items: center;
    gap: 16px;
    text-decoration: none;
    border-bottom: 1px solid #f0f0f0;
    transition: background-color 0.2s;
}

.card-header:hover {
    background-color: #fcfcfc;
}

.card-header h3 {
    font-size: 1.1rem;
    font-weight: 700;
    color: #2c3e50;
    margin: 0;
}

.icon-wrapper {
    width: 48px;
    height: 48px;
    border-radius: 12px;
    background-color: #eef6fc;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    flex-shrink: 0;
}

.icon-wrapper img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.placeholder-icon {
    font-size: 1.5rem;
    font-weight: bold;
    color: #367cc1;
}

.card-body {
    padding: 20px 24px 10px 24px;
    flex-grow: 1;
}

.subcategory-list {
    list-style: none;
    padding: 0;
    margin: 0;
}

.subcategory-list li {
    margin-bottom: 12px;
}

.subcategory-list a {
    color: #555;
    text-decoration: none;
    font-size: 0.95rem;
    transition: color 0.2s, transform 0.2s;
    display: inline-block;
}

.subcategory-list a:hover {
    color: #367cc1;
    transform: translateX(4px);
}

.card-footer {
    padding: 16px 24px;
    background-color: #fafafa;
    border-top: 1px solid #f0f0f0;
    margin-top: auto;
}

.view-all-link {
    font-size: 0.9rem;
    font-weight: 600;
    color: #367cc1;
    text-decoration: none;
    display: flex;
    align-items: center;
    justify-content: space-between;
    transition: opacity 0.2s;
}

.view-all-link:hover {
    opacity: 0.8;
}

.arrow {
    transition: transform 0.2s;
}

.view-all-link:hover .arrow {
    transform: translateX(4px);
}

.loading-state {
    display: flex;
    justify-content: center;
    padding: 100px;
}

.spinner {
    width: 40px;
    height: 40px;
    border: 4px solid #f3f3f3;
    border-top: 4px solid #367cc1;
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
</style>