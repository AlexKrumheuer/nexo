<script setup>
import { ref } from 'vue'
import Validator from '../../util/Validator'
import api from '../../services/api'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import '../../style/auth.css'

const toast = useToast()
const router = useRouter()

const user = ref('')
const email = ref('')
const password = ref('')
const error = ref('')
const isSubmitting = ref(false)

const signValidator = () => {
    error.value = ''

    if (!Validator.textValidator(user.value)) {
        error.value = "Username must not be empty"
        return
    }

    if (!Validator.emailValidor(email.value)) {
        error.value = "Invalid email format. Example: test@test.com"
        return
    }

    if (!Validator.passwordValidator(password.value)) {
        error.value = "Password must contain uppercase, lowercase, numbers and special characters"
        return
    }

    createUser()
}

async function createUser() {
    isSubmitting.value = true
    try {
        await api.post('/auth/register', {
            username: user.value,
            email: email.value,
            password: password.value
        })
        toast.success("Account created successfully! Please sign in.")
        router.push("/login")
    } catch (errorCatch) {
        const apiMessage = errorCatch.response?.data?.message
        error.value = apiMessage || "Error creating account. Please try again later."
        console.error("Registration Error:", errorCatch)
    } finally {
        isSubmitting.value = false
    }
}
</script>

<template>
    <div class="auth-page">
        <div class="auth-card">
            <div class="auth-header">
                <h1 class="auth-title">Create Account</h1>
                <p class="auth-subtitle">Join Nexo marketplace today</p>
            </div>

            <form class="auth-form" @submit.prevent="signValidator">
                <div class="form-group">
                    <label for="username">Username</label>
                    <div class="input-wrapper">
                        <fa icon="user" class="input-icon" />
                        <input 
                            id="username"
                            name="username" 
                            type="text" 
                            placeholder="Choose a username" 
                            v-model="user"
                            :disabled="isSubmitting"
                        />
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">E-mail</label>
                    <div class="input-wrapper">
                        <fa icon="envelope" class="input-icon" />
                        <input 
                            id="email"
                            name="email" 
                            type="email" 
                            placeholder="Type your e-mail" 
                            v-model="email"
                            :disabled="isSubmitting"
                        />
                    </div>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="input-wrapper">
                        <fa icon="lock" class="input-icon" />
                        <input 
                            id="password"
                            name="password" 
                            type="password" 
                            placeholder="Type your password" 
                            v-model="password"
                            :disabled="isSubmitting"
                        />
                    </div>
                </div>

                <div v-if="error" class="error-banner">
                    <fa icon="exclamation-circle" />
                    <span>{{ error }}</span>
                </div>

                <button class="btn-auth-submit" type="submit" :disabled="isSubmitting">
                    <span v-if="!isSubmitting">Sign Up</span>
                    <span v-else class="spinner-text">Creating account...</span>
                </button>

                <p class="auth-footer-text">
                    Already have an account? 
                    <router-link to="/login" class="auth-link">Sign In</router-link>
                </p>
            </form>
        </div>
    </div>
</template>

<style scoped>
.auth-page {
    min-height: 80vh;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 2rem 1rem;
}

.auth-card {
    background: #ffffff;
    width: 100%;
    max-width: 440px;
    padding: 2.5rem;
    border-radius: 16px;
    box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.05), 0 8px 10px -6px rgba(0, 0, 0, 0.01);
    border: 1px solid #e2e8f0;
}

.auth-header {
    text-align: center;
    margin-bottom: 2rem;
}

.auth-title {
    font-size: 1.75rem;
    font-weight: 700;
    color: #0f172a;
    margin: 0 0 0.5rem 0;
}

.auth-subtitle {
    font-size: 0.9rem;
    color: #64748b;
    margin: 0;
}

.auth-form {
    display: flex;
    flex-direction: column;
    gap: 1.25rem;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 0.4rem;
}

.form-group label {
    font-size: 0.85rem;
    font-weight: 600;
    color: #334155;
}

.input-wrapper {
    position: relative;
    display: flex;
    align-items: center;
}

.input-icon {
    position: absolute;
    left: 14px;
    color: #94a3b8;
    font-size: 0.95rem;
}

.input-wrapper input {
    width: 100%;
    padding: 10px 14px 10px 40px;
    border: 1px solid #cbd5e1;
    border-radius: 8px;
    font-size: 0.95rem;
    color: #0f172a;
    background-color: #ffffff;
    outline: none;
    transition: all 0.2s ease;
}

.input-wrapper input:focus {
    border-color: #3b82f6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
}

.input-wrapper input:disabled {
    background-color: #f8fafc;
    cursor: not-allowed;
}

.error-banner {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 10px 14px;
    background-color: #fef2f2;
    border: 1px solid #fecaca;
    border-radius: 8px;
    color: #dc2626;
    font-size: 0.85rem;
    font-weight: 500;
}

.btn-auth-submit {
    width: 100%;
    padding: 12px;
    background-color: #2563eb;
    color: #ffffff;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.2s, transform 0.1s;
    margin-top: 0.5rem;
}

.btn-auth-submit:hover:not(:disabled) {
    background-color: #1d4ed8;
}

.btn-auth-submit:active:not(:disabled) {
    transform: scale(0.99);
}

.btn-auth-submit:disabled {
    background-color: #93c5fd;
    cursor: not-allowed;
}

.auth-footer-text {
    text-align: center;
    font-size: 0.9rem;
    color: #64748b;
    margin-top: 1rem;
}

.auth-link {
    color: #2563eb;
    font-weight: 600;
    text-decoration: none;
}

.auth-link:hover {
    text-decoration: underline;
}
</style>