import { storeToRefs } from 'pinia'
import { useCartStore } from '../stores/cart.js'

export function useCart() {
  const store = useCartStore()
  const { cartItems, cartCount, cartTotal } = storeToRefs(store)

  return {
    cartItems,
    cartCount,
    cartTotal,
    fetchCart: store.fetchCart,
    addToCart: store.addToCart,
    removeFromCart: store.removeFromCart,
    updateQuantity: store.updateQuantity,
    clearCart: store.clearCart,
    submitOrder: store.submitOrder,
  }
}
