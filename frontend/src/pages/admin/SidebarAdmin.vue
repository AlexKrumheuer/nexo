<script setup>
    import '../../style/main_content/sidebar.css'
</script>
<template>
    <nav class="sidebar">
        <ul class="sidebar-container">
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin">
                    <fa class="item-icon" icon="chart-line"/>
                    <p>Dashboard</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/users">
                    <fa class="item-icon" icon="users"/>
                    <p>Users Management</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/sellers">
                    <fa class="item-icon" icon="store-alt"/>
                    <p>Sellers Management</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/product-catalog">
                    <fa class="item-icon" icon="tags"/>
                    <p>Products and Catalog</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/disputes-refunds">
                    <fa class="item-icon" icon="gavel"/>
                    <p>Disputes and refunds</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin/config">
                    <fa class="item-icon" icon="cogs"/>
                    <p>Plataform Configs</p>
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
