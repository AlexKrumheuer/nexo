<script setup>
import { userUserStore } from '../../services/userStore'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

const userStore = userUserStore()


const { userData, loading } = storeToRefs(userStore)
const { fetchUser } = userStore

console.log(userData)

onMounted(() => {
    fetchUser()
})
</script>
<template>
    <nav class="sidebar" v-if="!loading">
        <ul class="sidebar-container">
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/">
                    <fa class="item-icon" icon="home" />
                    <p>Home</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/offers">
                    <fa class="item-icon" icon="percent" />
                    <p>Today's offers</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/trending">
                    <fa class="item-icon" icon="fire" />
                    <p>Trending</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/official-sellers">
                    <fa class="item-icon" icon="store" />
                    <p>Official sellers</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/categories">
                    <fa class="item-icon" icon="layer-group" />
                    <p>Categories</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/my-orders">
                    <fa class="item-icon" icon="box" />
                    <p>My orders</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/favorites">
                    <fa class="item-icon" icon="heart" />
                    <p>Favorites</p>
                </router-link>
            </li>
            <li class="sidebar-item" v-if="userData?.userRole === 'SELLER' || userData?.userRole === 'ADMIN'">
                <router-link class="sidebar-item__router" to="/seller">
                    <fa class="item-icon" icon="signal" />
                    <p>Seller</p>
                </router-link>
            </li>
            <li class="sidebar-item" v-if="userData?.userRole === 'ADMIN'">
                <router-link class="sidebar-item__router" to="/admin">
                    <fa class="item-icon" icon="user-tie" />
                    <p>Admin Panel</p>
                </router-link>
            </li>
            <li class="sidebar-item">
                <router-link class="sidebar-item__router" to="/resume">
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