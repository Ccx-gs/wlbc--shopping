<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCart } from '../composables/useCart.js'
import { useUser } from '../composables/useUser.js'

const router = useRouter()
const { cartItems, cartTotal, cartCount, clearCart } = useCart()
const { user } = useUser()

const orderSubmitted = ref(false)
const submitting = ref(false)

function formatPrice(price) {
  return '¥' + price.toLocaleString('zh-CN')
}

function submitOrder() {
  submitting.value = true
  setTimeout(() => {
    clearCart()
    orderSubmitted.value = true
    submitting.value = false
  }, 800)
}

const categoryIcons = {
  smartphone: '📱',
  laptop: '💻',
  tablet: '📟',
  accessory: '🎧',
}

function getIcon(category) {
  return categoryIcons[category] || '📦'
}
</script>

<template>
  <div class="checkout-view">
    <!-- Success state -->
    <div v-if="orderSubmitted" class="success-state">
      <div class="success-icon">✓</div>
      <h1 class="success-title">Order Placed Successfully!</h1>
      <p class="success-sub">Thank you for your purchase. Your order is being processed.</p>
      <button class="home-btn" @click="router.push('/')">Continue Shopping</button>
    </div>

    <!-- Checkout form -->
    <div v-else-if="cartCount > 0" class="checkout-layout">
      <div class="checkout-main">
        <h1 class="page-title">Checkout</h1>

        <!-- Shipping info -->
        <section class="section-card">
          <h2 class="section-title">Shipping Address</h2>
          <div class="address-block">
            <p class="address-name">{{ user.name }}</p>
            <p class="address-line">{{ user.address }}</p>
          </div>
        </section>

        <!-- Order items -->
        <section class="section-card">
          <h2 class="section-title">Order Items</h2>
          <div class="order-items">
            <div
              v-for="item in cartItems"
              :key="item.id"
              class="order-item"
            >
              <span class="order-item-icon">{{ getIcon(item.category) }}</span>
              <div class="order-item-info">
                <span class="order-item-name">{{ item.name }}</span>
                <span class="order-item-qty">× {{ item.quantity }}</span>
              </div>
              <span class="order-item-price">{{ formatPrice(item.price * item.quantity) }}</span>
            </div>
          </div>
        </section>
      </div>

      <!-- Summary sidebar -->
      <aside class="checkout-sidebar">
        <div class="summary-card">
          <h2 class="summary-title">Payment Summary</h2>
          <div class="summary-row">
            <span>Subtotal ({{ cartCount }} items)</span>
            <span>{{ formatPrice(cartTotal) }}</span>
          </div>
          <div class="summary-row">
            <span>Shipping</span>
            <span class="free">Free</span>
          </div>
          <div class="summary-row">
            <span>Tax</span>
            <span>Included</span>
          </div>
          <div class="summary-divider"></div>
          <div class="summary-row total-row">
            <span>Total</span>
            <span class="total-amount">{{ formatPrice(cartTotal) }}</span>
          </div>
          <button
            class="submit-btn"
            @click="submitOrder"
            :disabled="submitting"
          >
            <span v-if="submitting">Processing...</span>
            <span v-else>Submit Order</span>
          </button>
          <button class="back-btn" @click="router.push('/cart')">← Back to Cart</button>
        </div>
      </aside>
    </div>

    <!-- Empty cart redirect -->
    <div v-else class="empty-checkout">
      <p>Your cart is empty.</p>
      <button class="home-btn" @click="router.push('/')">Go Shopping</button>
    </div>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 1.7rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 24px;
  letter-spacing: -0.01em;
}

.checkout-layout {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 28px;
  align-items: start;
}

@media (max-width: 800px) {
  .checkout-layout {
    grid-template-columns: 1fr;
  }
}

.checkout-main {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.section-card {
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 20px 24px;
}

.section-title {
  font-size: 0.85rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: #666;
  margin-bottom: 14px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.address-block {
  padding: 4px 0;
}

.address-name {
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 4px;
}

.address-line {
  color: #555;
  font-size: 0.92rem;
}

.order-items {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.order-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 0;
  border-bottom: 1px solid #f5f5f5;
}

.order-item:last-child {
  border-bottom: none;
}

.order-item-icon {
  font-size: 1.4rem;
  width: 32px;
  text-align: center;
}

.order-item-info {
  flex: 1;
  display: flex;
  align-items: baseline;
  gap: 8px;
  flex-wrap: wrap;
}

.order-item-name {
  font-size: 0.9rem;
  font-weight: 500;
  color: #1a1a1a;
}

.order-item-qty {
  font-size: 0.82rem;
  color: #888;
}

.order-item-price {
  font-size: 0.9rem;
  font-weight: 600;
  color: #0066cc;
  white-space: nowrap;
}

/* Sidebar */
.summary-card {
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 20px;
  position: sticky;
  top: 80px;
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
  margin-bottom: 20px;
}

.total-amount {
  color: #0066cc;
  font-size: 1.15rem;
}

.submit-btn {
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
  margin-bottom: 10px;
}

.submit-btn:hover:not(:disabled) {
  background: #0052a3;
}

.submit-btn:disabled {
  background: #7aaedb;
  cursor: not-allowed;
}

.back-btn {
  width: 100%;
  padding: 10px;
  background: none;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 0.88rem;
  color: #555;
  cursor: pointer;
  transition: background 0.12s;
}

.back-btn:hover {
  background: #f5f5f5;
}

/* Success state */
.success-state {
  text-align: center;
  padding: 80px 0;
  max-width: 480px;
  margin: 0 auto;
}

.success-icon {
  width: 72px;
  height: 72px;
  background: #0066cc;
  color: #fff;
  border-radius: 50%;
  font-size: 2rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 24px;
}

.success-title {
  font-size: 1.6rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 10px;
}

.success-sub {
  color: #666;
  margin-bottom: 32px;
  font-size: 0.95rem;
}

.home-btn {
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

.home-btn:hover {
  background: #0052a3;
}

/* Empty checkout */
.empty-checkout {
  text-align: center;
  padding: 80px 0;
  color: #666;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}
</style>
