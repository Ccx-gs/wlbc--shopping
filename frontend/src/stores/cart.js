import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import http from '../api/http.js'

const cartItems = ref([])

function emitCartUpdated(detail = {}) {
  window.dispatchEvent(new CustomEvent('cart:updated', {
    detail: {
      count: cartItems.value.reduce((sum, item) => sum + item.quantity, 0),
      ...detail,
    },
  }))
}

export const useCartStore = defineStore('cart', () => {
  async function fetchCart() {
    const { data } = await http.get('/cart')
    cartItems.value = Array.isArray(data) ? data : []
    return cartItems.value
  }

  async function persistCart(nextCart, detail = {}) {
    const { data } = await http.post('/cart', nextCart)
    cartItems.value = Array.isArray(data) ? data : []
    emitCartUpdated(detail)
  }

  async function addToCart(product, quantity = 1) {
    const key = product.cartKey || `${product.id}-${product.skuId || 'default'}`
    const nextCart = cartItems.value.map((item) => ({ ...item }))
    const existing = nextCart.find((item) => item.cartKey === key)

    if (existing) {
      existing.quantity += quantity
    } else {
      nextCart.push({ ...product, cartKey: key, quantity })
    }

    await persistCart(nextCart, { action: 'add', name: product.name, quantity })
  }

  async function removeFromCart(itemKey) {
    const nextCart = cartItems.value.filter((item) => item.cartKey !== itemKey && item.id !== itemKey)
    await persistCart(nextCart, { action: 'remove' })
  }

  async function updateQuantity(itemKey, quantity) {
    const nextCart = cartItems.value.map((item) => ({ ...item }))
    const item = nextCart.find((it) => it.cartKey === itemKey || it.id === itemKey)

    if (!item) return

    item.quantity = Math.max(1, quantity)
    await persistCart(nextCart, { action: 'update' })
  }

  async function clearCart() {
    await persistCart([], { action: 'clear' })
  }

  async function submitOrder() {
    const { data } = await http.post('/orders')
    cartItems.value = []
    emitCartUpdated({ action: 'clear' })
    return data
  }

  const cartCount = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
  )

  const cartTotal = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
  )

  return {
    cartItems,
    cartCount,
    cartTotal,
    fetchCart,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    submitOrder,
  }
})
