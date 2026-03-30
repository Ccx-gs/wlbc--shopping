import { ref, computed } from 'vue'

const cartItems = ref(JSON.parse(localStorage.getItem('cartItems')) || [])

function saveCart() {
  localStorage.setItem('cartItems', JSON.stringify(cartItems.value))
}

export function useCart() {
  function addToCart(product, quantity = 1) {
    const existing = cartItems.value.find(item => item.id === product.id)
    if (existing) {
      existing.quantity += quantity
    } else {
      cartItems.value.push({ ...product, quantity })
    }
    saveCart()
  }

  function removeFromCart(productId) {
    cartItems.value = cartItems.value.filter(item => item.id !== productId)
    saveCart()
  }

  function updateQuantity(productId, quantity) {
    const item = cartItems.value.find(item => item.id === productId)
    if (item) {
      item.quantity = Math.max(1, quantity)
      saveCart()
    }
  }

  function clearCart() {
    cartItems.value = []
    saveCart()
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
