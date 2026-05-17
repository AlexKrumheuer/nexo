<script setup>
import '../../style/main_content/sidebar.css'
</script>
<template>
    <nav class="sidebar">
        <ul class="sidebar-container">
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin">
                    <fa class="item-icon" icon="chart-line" />
                    <p>Dashboard</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/users">
                    <fa class="item-icon" icon="users" />
                    <p>Users Management</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/sellers">
                    <fa class="item-icon" icon="store-alt" />
                    <p>Sellers Management</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/product-catalog">
                    <fa class="item-icon" icon="tags" />
                    <p>Products and Catalog</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/disputes-refunds">
                    <fa class="item-icon" icon="gavel" />
                    <p>Disputes and refunds</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/config">
                    <fa class="item-icon" icon="cogs" />
                    <p>Plataform Configs</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router">
                    <fa class="item-icon" icon="angle-left" />
                    <p>Resume</p>
                </router-link>
            </li>
        </ul>
    </nav>
</template>
<style scoped>
.sidebar {
    background-color: #fdfdfd;
    height: 100vh;
    position: sticky;
    top: 0;
    padding: 1rem;
}

.sidebar-container {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
}

.sidebar-item {
    list-style: none;


}

.sidebar-item__router {
    background-color: #3b7bb9;
    font-weight: bold;
    font-size: 1rem;
    padding: 0.8rem;
    border-radius: 8px;
    cursor: pointer;
    text-decoration: none;
    color: #fff;
    transition: 0.25s;
    display: flex;
    gap: 0.5rem;
    border: 1px solid transparent;
    align-items: center;
}

.sidebar-item__router:hover {
    color: #3b7bb9;
    background-color: #fff;
    border: 1px solid #3b7bb9;
}
</style>