<script setup>
    import '../../style/main_content/sidebar.css'
</script>
<template>
    <nav class="sidebar">
        <ul class="sidebar-container">
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/me">
                    <fa class="item-icon" icon="id-card"/>
                    <p>Personal Info</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/me/addresses">
                    <fa class="item-icon" icon="map-marker-alt"/>
                    <p>Addresses</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/me/payments">
                    <fa class="item-icon" icon="lock"/>
                    <p>Payments</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/me/security">
                    <fa class="item-icon" icon="store"/>
                    <p>Security</p>
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
