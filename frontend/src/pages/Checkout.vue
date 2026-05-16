<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useCartStore } from '../services/cartStore'
import { useToast } from 'vue-toastification'
import api from '../services/api'

const cartStore = useCartStore()
const toast = useToast()

const order = ref({
  items: [],
  total: 0,
  shipping: 0,
  selectedAddress: null,
  selectedPayment: "",
})

// Estado da página
const selectedAddress = ref(0)
const selectedPayment = ref('')

// Dados fictícios vindos da sua API / Store
const userAddresses = ref([
])

const orderSummary = ref({
  subtotal: 0.00,
  shipping: 0.00, // Frete Grátis
})

watch(selectedAddress, (newIndex) => {
  if(userAddresses.value.length > 0 && userAddresses.value[newIndex]) {
    order.value.selectedAddress = userAddresses.value[newIndex]
  }
}, {immediate: true})

watch(selectedPayment, (newPayment) => {
  order.value.selectedPayment = newPayment
}, {immediate: true})

// Cálculo do Total Geral
const totalOrderPrice = computed(() => {
  return orderSummary.value.subtotal + orderSummary.value.shipping
})

const formatCurrency = (value) => {
  return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}

const handleFinishOrder = () => {
  if (order.value.selectedPayment === "") {
    toast.error("Please, select a payment method!")
    return
  }
  if (order.value.selectedAddress) {
    toast.error("Please, select a delivery address!")
    return
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

const fetchUserAddresses = async () => {
  try {
    const response = await api.get("/address")
    userAddresses.value = response.data.address || []
  } catch (e) {
    console.error("Error fetching user addresses: " + e.message || e)
  }
}
</script>

<template>
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
              <input type="radio" name="payment" value="credit" v-model="selectedPayment" />
            </div>
            <div class="payment-info">
              <span class="payment-label"><strong>Credit Card</strong></span>
              <span class="payment-subtext">Nexo accepts all major national and international credit card brands.</span>
            </div>
          </label>
          <label class="payment-row" :class="{ 'is-active': selectedPayment === 'nupay' }">
            <div class="payment-radio-wrapper">
              <input type="radio" name="payment" value="debit" v-model="selectedPayment" />
            </div>
            <div class="payment-info">
              <span class="payment-label"><strong>Debit Card</strong></span>
              <span class="payment-subtext">Nexo accepts all major national and international debit card brands.</span>
            </div>
          </label>
          <label class="payment-row" :class="{ 'is-active': selectedPayment === 'pix' }">
            <div class="payment-radio-wrapper">
              <input type="radio" name="payment" value="pix" v-model="selectedPayment" />
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
          <span class="free-shipping">Grátis</span>
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
<style scoped>
.checkout-container {
  max-width: 1150px;
  margin: 0 auto;
  padding: 30px 20px;
  display: grid;
  grid-template-columns: 1fr 340px;
  gap: 30px;
  font-family: sans-serif;
  color: #333;
}

/* Coluna Principal */
.checkout-main {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.checkout-section {
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 24px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.section-title {
  font-size: 1.3rem;
  font-weight: 700;
  margin: 0;
  color: #333;
}

.user-name {
  font-size: 0.95rem;
  margin-bottom: 4px;
  color: #111;
}

.address-text {
  font-size: 0.95rem;
  color: #555;
  line-height: 1.5;
  margin-bottom: 12px;
}

/* Links no padrão azul Nexo */
.btn-link {
  background: none;
  border: none;
  color: #0369a1;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 600;
}

.btn-link:hover {
  text-decoration: underline;
  color: #024ea6;
}

.btn-sub-link {
  background: none;
  border: none;
  color: #0369a1;
  font-size: 0.85rem;
  cursor: pointer;
  padding: 0;
}

.btn-sub-link:hover {
  text-decoration: underline;
}

/* Bloco de Opções de Pagamento */
.payment-options-card {
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  margin-top: 15px;
  overflow: hidden;
  background-color: #fff;
}

.payment-row {
  display: flex;
  padding: 18px;
  border-bottom: 1px solid #e0e0e0;
  cursor: pointer;
  transition: all 0.2s ease;
}

.payment-row:last-child {
  border-bottom: none;
}

.payment-row:hover {
  background-color: #f9f9f9;
}

/* Cor verde suave de seleção combinando com o card-address anterior */
.payment-row.is-active {
  background-color: #f0fff4;
  border-left: 4px solid #0369a1;
  padding-left: 14px;
  /* Ajuste compensatório devido à borda */
}

.payment-radio-wrapper {
  padding-right: 12px;
  display: flex;
  align-items: flex-start;
  padding-top: 2px;
}

/* Customização sutil do input radio para o padrão azul */
.payment-radio-wrapper input[type="radio"] {
  accent-color: #0369a1;
}

.payment-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.payment-label {
  font-size: 0.95rem;
  color: #2c3e50;
}

.payment-subtext {
  font-size: 0.85rem;
  color: #666;
}

/* Coluna Lateral de Resumo (Sticky) */
.checkout-sidebar {
  position: sticky;
  top: 20px;
  height: fit-content;
}

.summary-card {
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 24px;
}

/* Botão principal com a cor sólida da Nexo */
.btn-submit-order {
  width: 100%;
  background: #0369a1;
  border: none;
  color: white;
  border-radius: 8px;
  padding: 14px;
  font-size: 1rem;
  cursor: pointer;
  font-weight: bold;
  transition: background 0.2s ease;
}

.btn-submit-order:hover {
  background: #024ea6;
}

.privacy-text {
  font-size: 0.75rem;
  color: #888;
  text-align: center;
  margin-top: 12px;
  line-height: 1.4;
}

.summary-divider {
  border: 0;
  border-top: 1px solid #eee;
  margin: 16px 0;
}

.summary-title {
  font-size: 1.2rem;
  font-weight: 700;
  margin-bottom: 16px;
  color: #333;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  font-size: 0.95rem;
  margin-bottom: 12px;
  color: #666;
}

/* Verde corporativo para o Frete Grátis */
.free-shipping {
  color: #2e7d32;
  font-weight: bold;
}

.total-row {
  font-size: 1.2rem;
  font-weight: 700;
  margin-top: 10px;
  color: #000;
}

/* Preço final destacado na cor principal da marca */
.total-price {
  color: #0369a1;
}

/* Container de Cartões de Endereço */
.card-address {
  display: flex;
  gap: 15px;
  padding: 20px;
  border: 2px solid #e0e0e0;
  border-radius: 12px;
  margin-top: 16px;
  cursor: pointer;
  transition: all 0.2s ease;
  background-color: #fff;
}

.card-address:hover {
  border-color: #bdbdbd;
  background-color: #f9f9f9;
}

/* Estado Selecionado: Fundo verde suave e borda azul Nexo */
.card-address.is-selected {
  border-color: #0369a1;
  background-color: #f0fff4;
}

/* Alinhamento do Input Radio */
.radio-wrapper {
  display: flex;
  align-items: flex-start;
  padding-top: 2px;
}

.radio-wrapper input[type="radio"] {
  accent-color: #0369a1;
  transform: scale(1.1);
  cursor: pointer;
}

/* Detalhes internos do Endereço */
.address-details {
  display: flex;
  flex-direction: column;
  gap: 6px;
  width: 100%;
}

.address-type {
  font-size: 0.9rem;
  font-weight: 700;
  text-transform: uppercase;
  color: #555;
  letter-spacing: 0.5px;
  margin: 0 0 4px 0;
}

/* Se o cartão estiver ativo, destaca o tipo do endereço sutilmente */
.card-address.is-selected .address-type {
  color: #0369a1;
}

.address-text {
  font-size: 1.05rem;
  font-weight: 600;
  color: #222;
  margin: 0;
  line-height: 1.4;
}

.address-subtext {
  font-size: 0.9rem;
  color: #666;
  margin: 0;
  background: #f1f3f5;
  padding: 4px 10px;
  border-radius: 6px;
  width: fit-content;
}

/* Mantém o complemento cinza escuro mesmo se selecionado para leitura limpa */
.card-address.is-selected .address-subtext {
  background: #e2f0e6;
  color: #2e7d32;
}

.address-location {
  font-size: 0.9rem;
  color: #666;
  margin: 0;
}

/* Responsividade */
@media (max-width: 900px) {
  .checkout-container {
    grid-template-columns: 1fr;
  }

  .checkout-sidebar {
    position: relative;
    top: 0;
  }
}
</style>