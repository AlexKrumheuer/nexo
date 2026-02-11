<script setup>
import { ref } from 'vue'
import '../../style/admin/admin-global.css'

import ProductList from './product_catalog/ProductList.vue'
import CatalogReview from './product_catalog/CatalogReview.vue'
import Categories from './product_catalog/Categories.vue'

const controlPage = ref('catalog')
</script>

<template>
    <section class="admin-dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Product & Catalog Management</h1>
            <p class="dashboard-subtitle">Manage products, categories and catalog reviews.</p>
        </div>

        <div class="admin-dashboard--content">
            <div class="main-info">
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Total Products</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">500.000</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> +120% this week
                        </p>
                    </div>
                </div>

                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Out of stock</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">320</h2>
                        <p class="stat-trend warning">
                            <fa icon="arrow-up" /> Needs attention
                        </p>
                    </div>
                </div>

                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Categories</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">48</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> 5 main, 43 sub
                        </p>
                    </div>
                </div>
            </div>

            <div class="card-table-container">
                <div class="card-header card-product__catalog">
                    <h2 
                        :class="{ 'active-tab': controlPage === 'catalog' }" 
                        @click="controlPage = 'catalog'">
                        Catalog Review
                    </h2>
                    <h2 
                        :class="{ 'active-tab': controlPage === 'product' }" 
                        @click="controlPage = 'product'">
                        Product List
                    </h2>
                    <h2 
                        :class="{ 'active-tab': controlPage === 'category' }" 
                        @click="controlPage = 'category'">
                        Categories
                    </h2>
                </div>
                
                <div style="padding: 0;"> 
                    <CatalogReview v-if="controlPage === 'catalog'" />
                    <ProductList v-else-if="controlPage === 'product'" />
                    <Categories v-else />
                </div>
            </div>
        </div>

        <div class="pagination-area">
            <p class="pagination-info">
                Showing <span class="highlight">1</span> to <span class="highlight">10</span> of <span class="highlight">500</span> results
            </p>
            
            <div class="pagination-controls">
                <button class="pagination-btn disabled" disabled>
                    <fa icon="angle-left"></fa>
                </button>
                <button class="pagination-btn active">1</button>
                <button class="pagination-btn">2</button>
                <button class="pagination-btn">3</button>
                <span class="pagination-dots">...</span>
                <button class="pagination-btn">50</button>
                <button class="pagination-btn">
                    <fa icon="angle-right"></fa>
                </button>
            </div>
        </div>
    </section>
</template>