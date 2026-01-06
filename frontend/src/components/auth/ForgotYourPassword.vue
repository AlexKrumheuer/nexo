<script setup>
    import {ref} from 'vue'
    import '../../style/auth.css'
    let email = ref('')
    let error = ref('')
</script>
<template>
    <div class="container-auth--center">
        <form class="container-auth">
        <h2 class="auth-title">Forgot your password?</h2>
        <div class="container-input">
            <label for="email">E-mail</label>
            <input name="email" type="email" placeholder="Type your e-mail" v-model="email">
        </div>
        <button class="auth-submit--button" type="submit">Send E-mail</button>
        <p class="auth-little__text">Don't have an account? <router-link to="/register" class="sign-text">Sign Up</router-link></p>
        <p class="auth-little__text">Already have an account? <router-link to="/login" class="sign-text">Sign In</router-link></p>
        <p class="error" v-if="error">{{ error }}</p>    
    </form>
    </div>
</template>
