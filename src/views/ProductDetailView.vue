<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { products } from '../data/products.js'
import { useCart } from '../composables/useCart.js'

const route = useRoute()
const router = useRouter()
const { addToCart } = useCart()

const product = computed(() =>
  products.find(p => p.id === Number(route.params.id))
)

const quantity = ref(1)
const addedFeedback = ref(false)

const categoryColors = {
  smartphone: { bg: '#e8f0fe', icon: '📱' },
  laptop: { bg: '#e6f4ea', icon: '💻' },
  tablet: { bg: '#fce8e6', icon: '📟' },
  accessory: { bg: '#fef7e0', icon: '🎧' },
}

function getCategoryStyle(category) {
  return categoryColors[category] || { bg: '#f1f3f4', icon: '📦' }
}

function formatPrice(price) {
  return '¥' + price.toLocaleString('zh-CN')
}

function decrement() {
  if (quantity.value > 1) quantity.value--
}

function increment() {
  if (product.value && quantity.value < product.value.stock) quantity.value++
}

function handleAddToCart() {
  if (!product.value) return
  addToCart(product.value, quantity.value)
  addedFeedback.value = true
  setTimeout(() => { addedFeedback.value = false }, 2500)
}
</script>

<template>
  <div v-if="product" class="detail-view">
    <button class="back-btn" @click="router.back()">← Back</button>

    <div class="detail-grid">
      <div
        class="detail-image"
        :style="{ backgroundColor: getCategoryStyle(product.category).bg }"
      >
        <span class="detail-icon">{{ getCategoryStyle(product.category).icon }}</span>
        <span class="detail-category">{{ product.category }}</span>
      </div>

      <div class="detail-info">
        <h1 class="detail-name">{{ product.name }}</h1>
        <p class="detail-price">{{ formatPrice(product.price) }}</p>
        <p class="detail-stock" :class="{ low: product.stock < 10 }">
          {{ product.stock > 0 ? `In stock: ${product.stock} units` : 'Out of stock' }}
        </p>

        <p class="detail-description">{{ product.description }}</p>

        <div class="specs-block">
          <h3 class="specs-title">Specifications</h3>
          <dl class="specs-list">
            <template v-for="(value, key) in product.specs" :key="key">
              <dt class="spec-key">{{ key.charAt(0).toUpperCase() + key.slice(1) }}</dt>
              <dd class="spec-value">{{ value }}</dd>
            </template>
          </dl>
        </div>

        <div class="add-to-cart-row">
          <div class="qty-control">
            <button class="qty-btn" @click="decrement" :disabled="quantity <= 1">−</button>
            <span class="qty-display">{{ quantity }}</span>
            <button class="qty-btn" @click="increment" :disabled="quantity >= product.stock">+</button>
          </div>
          <button
            class="add-btn"
            @click="handleAddToCart"
            :disabled="product.stock === 0"
          >
            Add to Cart
          </button>
        </div>

        <Transition name="fade">
          <div v-if="addedFeedback" class="feedback-msg">
            ✓ Added {{ quantity }} item{{ quantity > 1 ? 's' : '' }} to cart
          </div>
        </Transition>
      </div>
    </div>
  </div>

  <div v-else class="not-found">
    <p>Product not found.</p>
    <button class="back-btn" @click="router.push('/')">Go home</button>
  </div>
</template>

<style scoped>
.back-btn {
  background: none;
  border: 1px solid #e0e0e0;
  color: #444;
  font-size: 0.88rem;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 28px;
  transition: background 0.12s, border-color 0.12s;
}

.back-btn:hover {
  background: #f5f5f5;
  border-color: #ccc;
}

.detail-grid {
  display: grid;
  grid-template-columns: 1fr 1.2fr;
  gap: 40px;
  align-items: start;
}

@media (max-width: 720px) {
  .detail-grid {
    grid-template-columns: 1fr;
  }
}

.detail-image {
  border-radius: 4px;
  height: 360px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 12px;
  border: 1px solid rgba(0,0,0,0.06);
}

.detail-icon {
  font-size: 6rem;
}

.detail-category {
  font-size: 0.78rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  color: #666;
}

.detail-name {
  font-size: 1.6rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 10px;
  line-height: 1.3;
  letter-spacing: -0.01em;
}

.detail-price {
  font-size: 1.8rem;
  font-weight: 700;
  color: #0066cc;
  margin-bottom: 8px;
}

.detail-stock {
  font-size: 0.85rem;
  color: #2e7d32;
  margin-bottom: 20px;
}

.detail-stock.low {
  color: #e65100;
}

.detail-description {
  font-size: 0.93rem;
  line-height: 1.7;
  color: #444;
  margin-bottom: 24px;
  border-top: 1px solid #f0f0f0;
  padding-top: 20px;
}

.specs-block {
  margin-bottom: 28px;
}

.specs-title {
  font-size: 0.85rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: #666;
  margin-bottom: 10px;
}

.specs-list {
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 6px 16px;
  font-size: 0.88rem;
}

.spec-key {
  color: #888;
  white-space: nowrap;
}

.spec-value {
  color: #1a1a1a;
  font-weight: 500;
}

.add-to-cart-row {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 16px;
}

.qty-control {
  display: flex;
  align-items: center;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.qty-btn {
  width: 40px;
  height: 40px;
  background: #f5f5f5;
  border: none;
  font-size: 1.2rem;
  color: #333;
  cursor: pointer;
  transition: background 0.12s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.qty-btn:hover:not(:disabled) {
  background: #e8e8e8;
}

.qty-btn:disabled {
  opacity: 0.35;
  cursor: not-allowed;
}

.qty-display {
  width: 48px;
  text-align: center;
  font-size: 1rem;
  font-weight: 600;
  border-left: 1px solid #e0e0e0;
  border-right: 1px solid #e0e0e0;
  height: 40px;
  line-height: 40px;
}

.add-btn {
  flex: 1;
  padding: 10px 24px;
  background: #0066cc;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.15s;
  height: 40px;
}

.add-btn:hover:not(:disabled) {
  background: #0052a3;
}

.add-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.feedback-msg {
  padding: 10px 16px;
  background: #e6f4ea;
  color: #1e7e34;
  border: 1px solid #b7dfbf;
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: 500;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.not-found {
  text-align: center;
  padding: 80px 0;
  color: #666;
}
</style>
