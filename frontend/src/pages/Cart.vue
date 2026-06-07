<script setup>
import { onMounted, ref, computed } from 'vue'
import api from '../services/api'
import { useToast } from 'vue-toastification'
import { useCartStore } from '../services/cartStore'
import { useRouter } from 'vue-router'
import '../style/main_content/cart.css'
import LoadingOverlay from './LoadingOverlay.vue'
const cartItems = ref([])
const toast = useToast()
const userCartStore = useCartStore()
const loading = ref(false)

const router = useRouter()

const cartSelectedItems = ref([])

onMounted(async () => {
  loading.value = true
  try {
    const response = await api.get("/api/cart")
    cartItems.value = response.data
  } catch (e) {
    console.error("Error: " + e.message || e)
    toast.error("Erro ao encontrar itens do carrinho")
  } finally {
    loading.value = false
  }
})

const cartTotal = computed(() => {
  if (!cartItems.value) return 0
  if (cartSelectedItems.value.length > 0) {
    return cartItems.value.reduce((total, item) => {
      if (cartSelectedItems.value.includes(item.product.id)) {
        const price = item.product.finalPrice || item.product.price || 0
        return total + (price * item.quantity)
      }
      return total
    }, 0)
  }
  return cartItems.value.reduce((total, item) => {
    const price = item.product.finalPrice || item.product.price || 0
    return total + (price * item.quantity)
  }, 0)
})


const removeCartItem = async (productId) => {
  loading.value = true
  try {
    const response = await api.delete(`/api/cart/${productId}`)
    cartItems.value = cartItems.value.filter(item => item.product.id !== productId)
    toast.success("Item removido com sucesso")
  } catch (e) {
    console.error("Error: " + e.message || e)
    toast.error("Erro ao remover seu item do carrinho")
  } finally {
    loading.value = false
  }
}

const cleanCart = async () => {
  loading.value = true
  try {
    await api.delete("/api/cart")
    cartItems.value = []
    toast.success("Carrinho esvaziado com sucesso")
  } catch (e) {
    console.error("Error: " + e.message || e)
    toast.error("Erro ao esvaziar carrinho, tente novamente")
  } finally {
    loading.value = false
  }
}

const updateQuantity = async (item, newQuantity) => {
  if (newQuantity < 1) return

  const oldQuantity = item.quantity

  try {
    loading.value = true
    const body = { quantity: newQuantity }

    await api.put(`/api/cart/${item.product.id}`, body)

    item.quantity = newQuantity

  } catch (e) {
    console.error("Erro:", e.message || e)
    toast.error("Erro ao atualizar quantidade")

  } finally {
    loading.value = false
  }
}
const formatCurrency = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}


const incrementItem = (item) => {
  updateQuantity(item, item.quantity + 1)
}

const decrementItem = (item) => {
  if (item.quantity > 1) {
    updateQuantity(item, item.quantity - 1)
  } else {
    removeCartItem(item.product.id)
  }
}

const finishOrder = () => {
  if(cartSelectedItems.value.length > 0) {
    const itemsToCheckout = cartItems.value.filter(item => 
      cartSelectedItems.value.includes(item.product.id)
    )
    userCartStore.prepCheckout(itemsToCheckout)
  } else {
    userCartStore.prepCheckout(cartItems.value)
  }
  userCartStore.cartItems = cartItems.value
  router.push("/checkout")
}

</script>
<template>
  <loading-overlay v-if="loading"></loading-overlay>
  <div class="page-wrapper">
    <div class="cart-container">

      <div class="cart-header">
        <h1 class="cart-title">My Cart</h1>
        <button class="btn-empty-cart" @click="cleanCart()">
          <fa icon="trash-alt" /> Empty Cart
        </button>
      </div>

      <div class="cart-layout">

        <div class="cart-items-list">
          <div v-if="cartItems.length > 0" v-for="item in cartItems" :key="item.product.id" class="cart-item">
            <input type="checkbox" v-model="cartSelectedItems" :value="item.product.id" class="select-item-checkbox">
            <div class="item-image-wrapper">
              <img class="item-image" :src="item.product.images[0].url" :alt="item.product.title">
            </div>

            <div class="item-details">
              <div class="item-info">
                <h3 class="product-name">{{ item.product.title }}</h3>
                <p class="product-sku">SKU: {{ item.product.sku }}</p>
                <div class="item-actions-mobile">
                </div>
              </div>

              <div class="item-controls">
              <div class="quantity-selector">
                <button class="qty-btn minus" @click="decrementItem(item)">-</button>

                <input type="text" readonly :value="item.quantity" class="qty-input">

                <button class="qty-btn plus" @click="incrementItem(item)">+</button>
              </div>

              <button class="btn-remove" @click="removeCartItem(item.product.id)">
                <fa icon="trash" /> <span>Remover</span>
              </button>
            </div>

            <div class="item-price">
              <p class="price-label">Total</p>
              <h3 class="price-value">{{ formatCurrency(item.product.finalPrice) }}</h3>
            </div>
          </div>
        </div>
        <p v-else v-if="loading == false">No items in your cart...</p>
      </div>

      <div class="cart-summary-wrapper">
        <div class="cart-summary">
          <h3 class="summary-title">Order Summary</h3>

          <div class="summary-row">
            <span>Subtotal ({{ cartSelectedItems.length > 0 ? cartSelectedItems.length : cartItems.length }} <span v-if="cartItems.length == 1">item</span> <span v-else>itens</span>)</span>
            <span>{{ formatCurrency(cartTotal) }}</span>
          </div>

          <div class="summary-row">
            <span>Shipping</span>
            <span class="free-shipping">Free</span>
          </div>

          <div class="divider"></div>

          <div class="summary-row total-row">
            <span>Total</span>
            <span class="total-price">{{ formatCurrency(cartTotal) }}</span>
          </div>

          <div class="summary-actions">
            <button class="btn-primary" @click="finishOrder()">Finish Purchase</button>
            <button class="btn-secondary" @click="$router.push('/')">Continue Shopping</button>
          </div>
        </div>
      </div>

    </div>
  </div>
  </div>
</template>