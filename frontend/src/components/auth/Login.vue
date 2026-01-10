<script setup>
    import {ref} from 'vue'
    import '../../style/auth.css'
    import api from '../../services/api'
    import { useRouter } from 'vue-router'
    let email = ref('')
    let password = ref('')
    let error = ref('')
    let router = useRouter()

    async function verifyLogin() {
        error.value = ''
        try {
            let response = await api.post("/auth/login",
                {
                    email: email.value,
                    password: password.value
                }
            )
            localStorage.setItem('token', response.data.token)
            router.push('/')
        } catch(e) {
            error.value = "Failed when trying to sign in, try again"
            console.error("Error: ", e)
        }
    }
</script>
<template>
    <div class="container-auth--center">
    <form class="container-auth" @submit.prevent="verifyLogin">
        <h2 class="auth-title">Sign In</h2>
        <div class="container-input">
            <label for="email">E-mail</label>
            <input name="email" type="email" placeholder="Type your e-mail" v-model="email">
        </div>
        <div class="container-input">
            <label for="password">Password</label>
            <input name="password" type="password" placeholder="Type your password" v-model="password">
        </div>
        <button class="auth-submit--button" type="submit">Sign In</button>
        <p class="auth-little__text">Don't have an account? <router-link to="/register" class="sign-text">Sign Up</router-link></p>
        <router-link to="/forgot-password" class="sign-text" style="text-align: center;">Forgot your password?</router-link>
        <p class="error" v-if="error">{{ error }}</p>   
    </form>
    </div>
</template>
