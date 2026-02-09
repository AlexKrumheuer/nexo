import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/auth/Login.vue'
import Register from '../components/auth/Register.vue'
import ForgotYourPassword from '../components/auth/ForgotYourPassword.vue'
import Dashboard from '../pages/Dashboard.vue'
import Product from '../pages/Product.vue'

const routes = [
  { path: '/login', component: Login, meta: { hideNavigation: true } },
  { path: '/register', component: Register, meta: { hideNavigation: true } },
  { path: '/forgot-password', component: ForgotYourPassword, meta: { hideNavigation: true } },
  { path: '/', name: 'Dashboard', component: Dashboard, meta: {requiresAuth: true}},
  { path: '/product/:slug', name: "Product", component: Product, meta: {requiresAuth: true}},
  { path: '/cart', name: 'Cart', component: () => import('../pages/Cart.vue'), meta: {requiresAuth: true} },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  
  const token = localStorage.getItem('token')

  if (requiresAuth && !token) {
    next('/login')
  } else if ((to.path === '/login' || to.path === '/register') && token) {
    next('/') 
  } else {
    next() 
  }
})

export default router