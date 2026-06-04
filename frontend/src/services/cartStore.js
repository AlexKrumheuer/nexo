import {defineStore} from 'pinia'
import {ref} from 'vue'

// Function to manage the shopping cart state, including items in the cart and selected items for checkout

export const useCartStore = defineStore('cart', () => {
  const cartItems = ref([])          
  const selectedToCheckout = ref([]) 

  // Prepare the selected items for checkout by updating the state with the selected items
  const prepCheckout = (selectedItems) => {
    selectedToCheckout.value = selectedItems
  }

  // Clean selected items after checkout to reset the state for the next transaction
  const cleanCheckout = () => {
    selectedToCheckout.value = []
  }

  return { cartItems, selectedToCheckout, prepCheckout, cleanCheckout }
})