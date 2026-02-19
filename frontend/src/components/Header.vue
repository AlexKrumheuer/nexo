
<script setup>
    import {ref} from 'vue'
    import api from '../services/api'
    import {useRouter} from 'vue-router'

    const router = useRouter()

    const openMenuPerfil = ref(false)

    const handleMouseEnter = () => {
        openMenuPerfil.value = true
    }

    const handleMouseLeave = () => {
        openMenuPerfil.value = false
    }


    const logout = async () => {
        try {
            await api.post("/auth/logout")
        } catch(e) {
            console.error("Fail when trying to log out")
        } finally{
            localStorage.removeItem("token")
            router.push("/login")
        }
    }

</script>
<template>
    <header>
        <router-link to="/">
            <img class="logo" src="../../img/logo.png" alt="">
        </router-link>
        <div class="header-search">
            <div class="wrapper-header-search">
                <input type="text" placeholder="What are you looking for?">
                <fa class="search-icon" icon="search" />
            </div>
        </div>
        <div class="header-nav">
            <router-link to="/cart" class="cart">
                <fa class="header-nav-icon" icon="cart-shopping" />
            </router-link>
            
            <div class="wrapper-bell">
                <fa class="header-nav-icon" icon="bell" />
                <p class="notification">1</p>
            </div>
        </div>
        <div class="header-user" @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
            <img class="user-icon" src="../../img/default-user-pic.png" alt="">
            <p>Alex</p>
            <fa icon="angle-down" />
            <nav v-if="openMenuPerfil" class="menu-perfil">
                <router-link to="/me" class="perfil-redirect">Perfil</router-link>
                <div @click="logout">Log out</div>
            </nav>
        </div>

    </header>
</template>
<style>
header {
    display: grid;
    grid-template-columns: 20% 50% 15% 10%;
    column-gap: 1rem;
    background-color: #367cc1;
    color: #fff;
    padding: 0 2rem;
}

.logo {
    width: 8rem;
    cursor: pointer;
    transition: 0.5s;
}

.logo:hover {
    transform: scale(1.1);
}

.header-search {
    width: 100%;
    display: flex;
    align-items: center;
}

.wrapper-header-search {
    background-color: #fff;
    width: 100%;
    border-radius: 50px;
    padding: 0.4rem;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.wrapper-header-search input {
    width: 85%;
    padding: 0.5rem;
    border: none;
    border-radius: 8px;
}

.search-icon {
    width: 10%;
    padding: 0.4rem;
    border-radius: 45%;
    background-color: #f68c36;
    color: #fff;
    font-size: 1.2rem;
    text-align: right;
    cursor: pointer;
    transition: 0.5s;
    border: 1px solid transparent;
}

.search-icon:hover {
    background-color: #fff;
    color: #f68c36;
    border: 1px solid #f68c36;
}

.header-nav {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
}

.header-nav-icon {
    font-size: 1.5rem;
    cursor: pointer;
    transition: 0.25s;
}

.header-nav-icon:hover {
    color: #f68c36;
}

.wrapper-bell {
    position: relative;
    display: flex;
    justify-content: center;
    cursor: pointer;
}

.notification {
    position: absolute;
    top: -5px;
    right: -8px;
    background-color: #f68c36;
    color: #fff;
    font-size: 0.7rem;
    font-weight: bold;
    width: 18px;
    height: 18px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0;
    border: 2px solid #367cc1;
}

.header-user {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0.5rem;
    padding: 1rem 2rem;
    font-size: 1.1rem;
    font-weight: bold;
    position: relative;
    cursor: pointer;
    transition: 0.5;
}

.header-user:hover {
    background-color: #fff;
    color: #367cc1;
}

.user-icon {
    width: 2.5rem;
}

.menu-perfil {
    background-color: #ffffff; 
    color: #333333; 
    position: absolute;
    top: 100%;
    right: -10%;
    width: 150px;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    padding: 0.8rem 0;
    overflow: hidden; 
    z-index: 100; 
}

.menu-perfil div,
.perfil-redirect {
    padding: 10px 15px;
    cursor: pointer;
    transition: 0.3s;
    font-weight: normal;
}

.perfil-redirect {
    color: #333333;
    text-decoration: none;
}

.menu-perfil div:hover,
.perfil-redirect:hover {
    background-color: #f0f8ff; 
    color: #367cc1; 
}

.cart {
    color: #fff;
}

.perfil-redirect {
    
}
</style>