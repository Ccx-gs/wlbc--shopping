import { ref, computed } from 'vue'

const cartItems = ref(JSON.parse(localStorage.getItem('cartItems')) || [])

function saveCart() {
  localStorage.setItem('cartItems', JSON.stringify(cartItems.value))
}

function emitCartUpdated(detail = {}) {
  window.dispatchEvent(new CustomEvent('cart:updated', {
    detail: {
      count: cartItems.value.reduce((sum, item) => sum + item.quantity, 0),
      ...detail,
    },
  }))
}

export function useCart() {
  function addToCart(product, quantity = 1) {
    const key = product.cartKey || `${product.id}-${product.skuId || 'default'}`
    const existing = cartItems.value.find(item => item.cartKey === key)
    if (existing) {
      existing.quantity += quantity
    } else {
      cartItems.value.push({ ...product, cartKey: key, quantity })
    }
    saveCart()
    emitCartUpdated({ action: 'add', name: product.name, quantity })
  }

  function removeFromCart(itemKey) {
    cartItems.value = cartItems.value.filter(item => item.cartKey !== itemKey && item.id !== itemKey)
    saveCart()
    emitCartUpdated({ action: 'remove' })
  }

  function updateQuantity(itemKey, quantity) {
    const item = cartItems.value.find(item => item.cartKey === itemKey || item.id === itemKey)
    if (item) {
      item.quantity = Math.max(1, quantity)
      saveCart()
      emitCartUpdated({ action: 'update' })
    }
  }

  function clearCart() {
    cartItems.value = []
    saveCart()
    emitCartUpdated({ action: 'clear' })
  }

  const cartCount = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
  )

  const cartTotal = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
  )

  return {
    cartItems,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    cartCount,
    cartTotal,
  }
}
