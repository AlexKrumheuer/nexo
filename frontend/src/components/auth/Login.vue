<script setup>
    import {ref} from 'vue'
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
<style scoped>
.container-auth--center {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #F8FAFC;
}

.container-auth {
    min-width: 30%;
    background-color: #FFF;
    display: flex;
    flex-direction: column;
    padding: 2rem;
    gap: 1rem;
    border-radius: 8px;
}

.auth-title {
    color: #1E293B;
    text-align: center;
    font-size: 1.7rem;
}

.container-input {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.container-input label {
    color: #64748B;
    font-size: 0.9rem;
}

.container-input input {
    border: 1px solid #CBD5E1;
    padding: 0.8rem;
    border-radius: 8px;
}

.auth-submit--button {
    background-color: #4338CA;
    color: #FFFFFF;
    padding: 0.8rem;
    font-weight: bold;
    font-size: 1rem;
    cursor: pointer;
    border: none;
    border-radius: 8px;
    transition: 0.25s;
}

.auth-submit--button:hover {
    color: #4338CA;
    background-color: #fff;
    border: 1px #4338CA solid;
}

.auth-little__text {
    font-size: 0.8rem;
    text-align: center;
    color: #64748B;
}

.sign-text {
    color: #4F46E5;
    font-size: 0.8rem;
}

.sign-text:hover {
    font-weight: bold;
}

.error {
    color: red;
    font-size: 0.9rem;
}
</style>