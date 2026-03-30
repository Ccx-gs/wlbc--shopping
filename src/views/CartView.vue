<script setup>
import { useRouter } from 'vue-router'
import CartItem from '../components/CartItem.vue'
import { useCart } from '../composables/useCart.js'

const router = useRouter()
const { cartItems, cartTotal, cartCount } = useCart()

function formatPrice(price) {
  return '¥' + price.toLocaleString('zh-CN')
}
</script>

<template>
  <div class="cart-view">
    <h1 class="page-title">Shopping Cart</h1>

    <div v-if="cartCount > 0" class="cart-layout">
      <div class="cart-items">
        <CartItem
          v-for="item in cartItems"
          :key="item.id"
          :item="item"
        />
      </div>

      <aside class="cart-summary">
        <div class="summary-card">
          <h2 class="summary-title">Order Summary</h2>
          <div class="summary-row">
            <span>Items ({{ cartCount }})</span>
            <span>{{ formatPrice(cartTotal) }}</span>
          </div>
          <div class="summary-row">
            <span>Shipping</span>
            <span class="free">Free</span>
          </div>
          <div class="summary-divider"></div>
          <div class="summary-row total-row">
            <span>Total</span>
            <span class="total-amount">{{ formatPrice(cartTotal) }}</span>
          </div>
          <button class="checkout-btn" @click="router.push('/checkout')">
            Go to Checkout →
          </button>
        </div>
      </aside>
    </div>

    <div v-else class="empty-cart">
      <p class="empty-icon">🛒</p>
      <h2 class="empty-title">Your cart is empty</h2>
      <p class="empty-sub">Add some products to get started.</p>
      <button class="shop-btn" @click="router.push('/')">Continue Shopping</button>
    </div>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 1.7rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 28px;
  letter-spacing: -0.01em;
}

.cart-layout {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 28px;
  align-items: start;
}

@media (max-width: 800px) {
  .cart-layout {
    grid-template-columns: 1fr;
  }
}

.cart-items {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.summary-card {
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 20px;
}

.summary-title {
  font-size: 1rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: #444;
  margin-bottom: 10px;
}

.free {
  color: #2e7d32;
  font-weight: 500;
}

.summary-divider {
  height: 1px;
  background: #e0e0e0;
  margin: 12px 0;
}

.total-row {
  font-size: 1rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 16px;
}

.total-amount {
  color: #0066cc;
  font-size: 1.15rem;
}

.checkout-btn {
  width: 100%;
  padding: 12px;
  background: #0066cc;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.15s;
}

.checkout-btn:hover {
  background: #0052a3;
}

.empty-cart {
  text-align: center;
  padding: 100px 0;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 16px;
}

.empty-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 8px;
}

.empty-sub {
  color: #666;
  margin-bottom: 28px;
}

.shop-btn {
  padding: 12px 32px;
  background: #0066cc;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.15s;
}

.shop-btn:hover {
  background: #0052a3;
}
</style>
