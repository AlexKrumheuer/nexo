<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useCartStore } from '../services/cartStore'
import { useToast } from 'vue-toastification'
import "../style/checkout.css"
import api from '../services/api'
import LoadingOverlay from './LoadingOverlay.vue'
import { useRouter } from 'vue-router'

const cartStore = useCartStore()
const toast = useToast()
const loading = ref(false)

const router = useRouter()

const order = ref({
  items: [],
  shippingPrice: 0,
  address: null,
  paymentMethod: "",
})

const selectedAddress = ref(0)
const selectedPayment = ref('')

const userAddresses = ref([
])

const orderSummary = ref({
  subtotal: 0.00,
  shipping: 0.00, 
})

// Watcher to update the order's address whenever the selected address changes, ensuring the order reflects the user's choice
watch(selectedAddress, (newIndex) => {
  if(userAddresses.value.length > 0 && userAddresses.value[newIndex]) {
    order.value.address = userAddresses.value[newIndex]
  }
}, {immediate: true})


// Watcher to update the order's payment method whenever the selected payment option changes, ensuring the order reflects the user's choice
watch(selectedPayment, (newPayment) => {
  order.value.paymentMethod = newPayment
}, {immediate: true})

const totalOrderPrice = computed(() => {
  return orderSummary.value.subtotal + orderSummary.value.shipping
})

const formatCurrency = (value) => {
  return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}

const handleFinishOrder = async () => {
    order.value.paymentMethod = selectedPayment.value

  if (order.value.paymentMethod === "") {
    toast.error("Please, select a payment method!")
    return
  }
  try { 
      order.value.address = userAddresses.value[selectedAddress.value].id
  } catch(e) {
      order.value.address = null
  }
  if (order.value.address === null) {
    toast.error("Please, add a delivery address!")
    router.push("me/addresses")
    return
  }

  for(const item of cartStore.selectedToCheckout) {
    order.value.items.push({
      product: item.product.id,
      quantity: item.quantity
    })
  }
  
  order.value.shippingPrice = orderSummary.value.shipping
  loading.value = true
  try {
    const response = await api.post("/api/orders", order.value)
    router.push("my-orders/" + response.data.orderCode)
  } catch(e) {
    toast.error("Error processing your order")
    console.error("Error processing order: " + e.message || e)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (cartStore.selectedToCheckout.length > 0) {
    orderSummary.value.subtotal = cartStore.selectedToCheckout.reduce((total, item) => {
      const price = item.product.finalPrice || item.product.price || 0
      return total + (price * item.quantity)
    }, 0)
  } else {
    orderSummary.value.subtotal = cartStore.cartItems.reduce((total, item) => {
      const price = item.product.finalPrice || item.product.price || 0
      return total + (price * item.quantity)
    }, 0)
  }

  fetchUserAddresses()
})

// Fetch Addresses from the API to populate the user's address options for delivery, allowing them to select from their saved addresses during checkout
const fetchUserAddresses = async () => {
  loading.value = true
  try {
    const response = await api.get("/address")
    userAddresses.value = response.data.address || []
  } catch (e) {
    console.error("Error fetching user addresses: " + e.message || e)
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <LoadingOverlay v-if="loading"/>
  <div class="checkout-container">
    <main class="checkout-main">

      <section class="checkout-section">
        <div class="section-header">
          <h2 class="section-title">1. Delivery Address</h2>
        </div>

        <label v-for="(addr, index) in userAddresses" :key="addr.id" class="card-address"
          :class="{ 'is-selected': selectedAddress === index }">

          <div class="radio-wrapper">
            <input type="radio" name="address" :value="index" v-model="selectedAddress">
          </div>

          <div class="address-details">
            <div class="address-header">
              <h2 class="address-type">{{ addr.addressType }}</h2>

            </div>

            <p class="address-text">
              <strong>{{ addr.street }}</strong>, {{ addr.number }}
            </p>

            <p v-if="addr.complement">{{ addr.complement }}</p>

            <p class="address-location">
              {{ addr.city }} - {{ addr.state }} | {{ addr.zipCode }}
            </p>

          </div>
        </label>
      </section>

      <section class="checkout-section">
        <h2 class="section-title">2. Payment Method</h2>

        <div class="payment-options-card">
          <label class="payment-row" :class="{ 'is-active': selectedPayment === 'credit' }">
            <div class="payment-radio-wrapper">
              <input type="radio" name="payment" value="CREDIT_CARD" v-model="selectedPayment" />
            </div>
            <div class="payment-info">
              <span class="payment-label"><strong>Credit Card</strong></span>
              <span class="payment-subtext">Nexo accepts all major national and international credit card brands.</span>
            </div>
          </label>
          <label class="payment-row" :class="{ 'is-active': selectedPayment === 'nupay' }">
            <div class="payment-radio-wrapper">
              <input type="radio" name="payment" value="DEBIT_CARD" v-model="selectedPayment" />
            </div>
            <div class="payment-info">
              <span class="payment-label"><strong>Debit Card</strong></span>
              <span class="payment-subtext">Nexo accepts all major national and international debit card brands.</span>
            </div>
          </label>
          <label class="payment-row" :class="{ 'is-active': selectedPayment === 'pix' }">
            <div class="payment-radio-wrapper">
              <input type="radio" name="payment" value="PIX" v-model="selectedPayment" />
            </div>
            <div class="payment-info">
              <span class="payment-label"><strong>Pix</strong></span>
              <span class="payment-subtext">The Pix code generated is valid for 30 minutes after the order is completed.</span>
            </div>
          </label>       
        </div>
      </section>
    </main>

    <aside class="checkout-sidebar">
      <div class="summary-card">
        <button class="btn-submit-order" @click="handleFinishOrder">
          Finish Order
        </button>
        <p class="privacy-text">
          Ao confirmar, você concorda com os termos de serviço e privacidade da plataforma.
        </p>

        <hr class="summary-divider" />

        <h3 class="summary-title">Resumo do pedido</h3>
        <div class="summary-row">
          <span>Itens:</span>
          <span>{{ formatCurrency(orderSummary.subtotal) }}</span>
        </div>
        <div class="summary-row">
          <span>Frete:</span>
          <span class="free-shipping">Free</span>
        </div>

        <hr class="summary-divider" />

        <div class="summary-row total-row">
          <span>Total do pedido:</span>
          <span class="total-price">{{ formatCurrency(totalOrderPrice) }}</span>
        </div>
      </div>
    </aside>
  </div>
</template>