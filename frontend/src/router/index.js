import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/auth/Login.vue'
import Register from '../components/auth/Register.vue'
import ForgotYourPassword from '../components/auth/ForgotYourPassword.vue'
import Dashboard from '../pages/Dashboard.vue'
import Product from '../pages/Product.vue'
import Category from '../pages/Category.vue'

const routes = [
  { path: '/login', component: Login, meta: { hideNavigation: true, hideSidebar: true } },
  { path: '/register', component: Register, meta: { hideNavigation: true, hideSidebar: true } },
  { path: '/forgot-password', component: ForgotYourPassword, meta: { hideNavigation: true, hideSidebar: true } },
  { path: '/', name: 'Dashboard', component: Dashboard },
  { path: '/categories', name: 'Category', component: Category, meta: { hideNavigation: false}},
  { path: '/product/:slug', name: "Product", component: Product, meta: {requiresAuth: true}},
  { path: '/cart', name: 'Cart', component: () => import('../pages/Cart.vue'), meta: {requiresAuth: true} },
  { 
    path: '/me', 
    name: 'User', 
    component: () => import('../pages/user/User.vue'), 
    meta: {requiresAuth: true, hideSidebar: true, requiredRole: 'USER'},
    children: [
      {
        path: '', 
        name: 'PersonalInfo',
        component: () => import('../pages/user/PersonalInfo.vue') 
      },
      {
        path: 'addresses', 
        name: 'Addresses',
        component: () => import('../pages/user/Addresses.vue')
      },
      {
        path: 'payments', 
        name: 'Payments',
        component: () => import('../pages/user/Payments.vue')
      },
      {
        path: 'security', 
        name: 'Security',
        component: () => import('../pages/user/Security.vue')
      }
    ] 
  },,
  { 
    path: '/admin', 
    name: 'Admin', 
    component: () => import('../pages/admin/Admin.vue'), 
    meta: {requiresAuth: true, hideSidebar: true, requiredRole: 'ADMIN'},
    children: [
      {
        path: '', 
        name: 'AdminDashboard',
        component: () => import('../pages/admin/Dashboard.vue') 
      },
      {
        path: 'users', 
        name: 'AdminUsers',
        component: () => import('../pages/admin/Users.vue')
      },
      {
        path: 'sellers', 
        name: 'AdminSellers',
        component: () => import('../pages/admin/Sellers.vue')
      },
      {
        path: 'product-catalog', 
        name: 'ProductAndCatalog',
        component: () => import('../pages/admin/ProductAndCatalog.vue')
      },
      {
        path: 'disputes-refunds', 
        name: 'DisputsAndRefunds',
        component: () => import('../pages/admin/DisputesAndRefunds.vue')
      },
      {
        path: 'config', 
        name: 'PlataformConfigs',
        component: () => import('../pages/admin/PlataformConfig.vue')
      },
    ] 
  },{ 
    path: '/seller', 
    name: 'Seller', 
    component: () => import('../pages/seller/Seller.vue'), 
    meta: {requiresAuth: true, hideSidebar: true, requiredRole: 'SELLER'},
    children: [
      {
        path: '', 
        name: 'SellerDashboard',
        component: () => import('../pages/seller/SellerDashboard.vue') 
      },
      {
        path: 'product', 
        name: 'SellerProduct',
        component: () => import('../pages/seller/SellerProduct.vue')
      },
      {
        path: 'product/new', 
        name: 'SellerNewProduct',
        component: () => import('../pages/seller/ProductNewSeller.vue')
      },
      {
        path: 'order', 
        name: 'SellerOrder',
        component: () => import('../pages/seller/SellerOrder.vue')
      },
      {
        path: 'financial', 
        name: 'SellerFinancial',
        component: () => import('../pages/seller/SellerFinancial.vue')
      },
      {
        path: 'configuration', 
        name: 'SellerConfiguration',
        component: () => import('../pages/seller/SellerConfiguration.vue')
      },
    ] 
  },
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