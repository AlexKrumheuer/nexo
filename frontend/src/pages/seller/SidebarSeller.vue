<script setup>
    import '../../style/main_content/sidebar.css'
</script>
<template>
    <nav class="sidebar">
        <ul class="sidebar-container">
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/seller">
                    <fa class="item-icon" icon="chart-line"/>
                    <p>Dashboard</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/seller/product">
                    <fa class="item-icon" icon="users"/>
                    <p>Products</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/seller/order">
                    <fa class="item-icon" icon="store-alt"/>
                    <p>Orders</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/seller/financial">
                    <fa class="item-icon" icon="tags"/>
                    <p>Financial</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/seller/configuration">
                    <fa class="item-icon" icon="gavel"/>
                    <p>Configuration</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router">
                    <fa class="item-icon" icon="angle-left"/>
                    <p>Resume</p>
                </router-link>
            </li>
        </ul>
    </nav>
</template>
