import {defineStore} from 'pinia'
import {ref} from 'vue'

export const useCartStore = defineStore('cart', () => {
  const cartItems = ref([])          
  const selectedToCheckout = ref([]) 

  const prepCheckout = (selectedItems) => {
    selectedToCheckout.value = selectedItems
  }

  const cleanCheckout = () => {
    selectedToCheckout.value = []
  }

  return { cartItems, selectedToCheckout, prepCheckout, cleanCheckout }
})