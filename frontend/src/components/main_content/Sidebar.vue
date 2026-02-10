<script setup>
    import '../../style/main_content/sidebar.css'
</script>
<template>
    <nav class="sidebar">
        <ul class="sidebar-container">
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/">
                    <fa class="item-icon" icon="home"/>
                    <p>Home</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/offers">
                    <fa class="item-icon" icon="percent"/>
                    <p>Today's offers</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/trending">
                    <fa class="item-icon" icon="fire"/>
                    <p>Trending</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/official-sellers">
                    <fa class="item-icon" icon="store"/>
                    <p>Official sellers</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/categories">
                    <fa class="item-icon" icon="layer-group"/>
                    <p>Categories</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/my-orders">
                    <fa class="item-icon" icon="box"/>
                    <p>My orders</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/favorites">
                    <fa class="item-icon" icon="heart"/>
                    <p>Favorites</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/seller">
                    <fa class="item-icon" icon="signal"/>
                    <p>Seller</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/admin">
                    <fa class="item-icon" icon="user-tie"/>
                    <p>Admin Panel</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/resume">
                    <fa class="item-icon" icon="angle-left"/>
                    <p>Resume</p>
                </router-link>
            </li>
        </ul>
    </nav>
</template>
