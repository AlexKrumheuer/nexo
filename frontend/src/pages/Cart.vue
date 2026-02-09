<script setup>
import { onMounted, ref, computed } from 'vue'
import api from '../services/api'
import { useToast } from 'vue-toastification'
const cartItems = ref([])
const toast = useToast()

onMounted(async () => {
  try {
    const response = await api.get("/api/cart")
    cartItems.value = response.data
  } catch (e) {
    console.error("Error: " + e.message || e)
    toast.error("Erro ao encontrar itens do carrinho")
  }
})

const cartTotal = computed(() => {
  if (!cartItems.value) return 0
  return cartItems.value.reduce((total, item) => {
    const price = item.product.finalPrice || item.product.price || 0
    return total + (price * item.quantity)
  }, 0)
})


const removeCartItem = async (productId) => {
  try {
    const response = await api.delete(`/api/cart/${productId}`)
    cartItems.value = cartItems.value.filter(item => item.product.id !== productId)
    toast.success("Item removido com sucesso")
  } catch (e) {
    console.error("Error: " + e.message || e)
    toast.error("Erro ao remover seu item do carrinho")
  }
}

const cleanCart = async () => {
  try {
    await api.delete("/api/cart")
    cartItems.value = []
    toast.success("Carrinho esvaziado com sucesso")
  } catch (e) {
    console.error("Error: " + e.message || e)
    toast.error("Erro ao esvaziar carrinho, tente novamente")
  }
}

const updateQuantity = async (item, newQuantity) => {
  if (newQuantity < 1) return

  const oldQuantity = item.quantity

  try {

    const body = { quantity: newQuantity }

    await api.put(`/api/cart/${item.product.id}`, body)

    item.quantity = newQuantity

  } catch (e) {
    console.error("Erro:", e.message || e)
    toast.error("Erro ao atualizar quantidade")

  }
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

</script>
<template>
  <div class="page-wrapper">
    <div class="cart-container">

      <div class="cart-header">
        <h1 class="cart-title">Meu Carrinho</h1>
        <button class="btn-empty-cart" @click="cleanCart()">
          <fa icon="trash-alt" /> Esvaziar Carrinho
        </button>
      </div>

      <div class="cart-layout">

        <div class="cart-items-list">
          <div v-if="cartItems.length > 0" v-for="item in cartItems" :key="item.product.id" class="cart-item">
            <div class="item-image-wrapper">
              <img class="item-image" :src="item.product.images[0].url" :alt="item.product.title">
            </div>

            <div class="item-details">
              <div class="item-info">
                <h3 class="product-name">{{ item.product.title }}</h3>
                <p class="product-sku">SKU: IPH-15-PM-256</p>
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
              <h3 class="price-value">R$ {{ item.product.finalPrice }}</h3>
            </div>
          </div>
        </div>
        <p v-else>Sem itens no seu carrinho...</p>
      </div>

      <div class="cart-summary-wrapper">
        <div class="cart-summary">
          <h3 class="summary-title">Resumo do Pedido</h3>

          <div class="summary-row">
            <span>Subtotal ({{ cartItems.length }} <span v-if="cartItems.length == 1">item</span> <span v-else>itens</span>)</span>
            <span>R$ {{ cartTotal.toFixed(2) }}</span>
          </div>

          <div class="summary-row">
            <span>Frete</span>
            <span class="free-shipping">Grátis</span>
          </div>

          <div class="divider"></div>

          <div class="summary-row total-row">
            <span>Total</span>
            <span class="total-price">R$ {{ cartTotal.toFixed(2) }}</span>
          </div>

          <div class="summary-actions">
            <button class="btn-primary">Finalizar Compra</button>
            <button class="btn-secondary">Continuar Comprando</button>
          </div>
        </div>
      </div>

    </div>
  </div>
  </div>
</template>

<style scoped>
.page-wrapper {
  background-color: #f3f4f6;
  min-height: 100vh;
  padding: 2rem 1rem;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
}

.cart-container {
  max-width: 1200px;
  margin: 0 auto;
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.cart-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #111827;
  margin: 0;
}

.btn-empty-cart {
  background: none;
  border: none;
  color: #ef4444;
  font-size: 0.9rem;
  cursor: pointer;
  font-weight: 500;
  transition: opacity 0.2s;
}

.btn-empty-cart:hover {
  text-decoration: underline;
  opacity: 0.8;
}

.cart-layout {
  display: grid;
  grid-template-columns: 1fr 340px;
  gap: 2rem;
  align-items: start;
}

.cart-items-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.cart-item {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 1.5rem;
  display: flex;
  gap: 1.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  border: 1px solid #e5e7eb;
}

.item-image-wrapper {
  width: 100px;
  height: 100px;
  flex-shrink: 0;
  background: #f9fafb;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.item-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  mix-blend-mode: multiply;
}

.item-details {
  flex-grow: 1;
  display: grid;
  grid-template-columns: 2fr 1fr 1fr;
  align-items: center;
  gap: 1rem;
}

.product-name {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 0.25rem 0;
}

.product-sku {
  font-size: 0.85rem;
  color: #6b7280;
  margin: 0;
}

.item-controls {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.quantity-selector {
  display: flex;
  align-items: center;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  overflow: hidden;
}

.qty-btn {
  background: #f9fafb;
  border: none;
  width: 32px;
  height: 32px;
  font-size: 1.2rem;
  color: #374151;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.2s;
}

.qty-btn:hover {
  background: #e5e7eb;
}

.qty-input {
  width: 40px;
  text-align: center;
  border: none;
  border-left: 1px solid #e5e7eb;
  border-right: 1px solid #e5e7eb;
  font-weight: 600;
  color: #111827;
  outline: none;
}

.btn-remove {
  background: transparent;
  border: none;
  color: #9ca3af;
  font-size: 0.8rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.3rem;
  transition: color 0.2s;
}

.btn-remove:hover {
  color: #ef4444;
}

.item-price {
  text-align: right;
}

.price-label {
  font-size: 0.75rem;
  color: #6b7280;
  margin-bottom: 0.25rem;
}

.price-value {
  font-size: 1.2rem;
  font-weight: 700;
  color: #111827;
  margin: 0;
}

.cart-summary-wrapper {
  position: sticky;
  top: 2rem;
}

.cart-summary {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  border: 1px solid #e5e7eb;
}

.summary-title {
  font-size: 1.25rem;
  font-weight: 700;
  margin-bottom: 1.5rem;
  color: #111827;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
  font-size: 0.95rem;
  color: #4b5563;
}

.free-shipping {
  color: #10b981;
  font-weight: 600;
}

.divider {
  height: 1px;
  background-color: #e5e7eb;
  margin: 1.5rem 0;
}

.total-row {
  font-size: 1.25rem;
  font-weight: 700;
  color: #111827;
  margin-bottom: 1.5rem;
}

.summary-actions {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.btn-primary {
  background-color: #2563eb;
  color: white;
  padding: 1rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s, transform 0.1s;
}

.btn-primary:hover {
  background-color: #1d4ed8;
}

.btn-primary:active {
  transform: scale(0.98);
}

.btn-secondary {
  background-color: white;
  color: #374151;
  padding: 0.9rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-secondary:hover {
  background-color: #f9fafb;
  border-color: #9ca3af;
}

@media (max-width: 900px) {
  .cart-layout {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }

  .cart-summary-wrapper {
    position: static;
  }

  .cart-item {
    flex-direction: column;
    align-items: flex-start;
    padding: 1rem;
  }

  .item-image-wrapper {
    width: 100%;
    height: 120px;
    margin-bottom: 1rem;
  }

  .item-details {
    width: 100%;
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .item-controls {
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
    border-top: 1px solid #f3f4f6;
    border-bottom: 1px solid #f3f4f6;
    padding: 10px 0;
  }

  .item-price {
    text-align: left;
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
  }

  .price-label {
    margin-bottom: 0;
  }
}
</style>