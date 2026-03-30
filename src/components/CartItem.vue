<script setup>
import { useCart } from '../composables/useCart.js'

const props = defineProps({
  item: {
    type: Object,
    required: true,
  },
})

const { removeFromCart, updateQuantity } = useCart()

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
</script>

<template>
  <div class="cart-item">
    <div
      class="item-image"
      :style="{ backgroundColor: getCategoryStyle(item.category).bg }"
    >
      <span class="item-icon">{{ getCategoryStyle(item.category).icon }}</span>
    </div>
    <div class="item-details">
      <h4 class="item-name">{{ item.name }}</h4>
      <p class="item-unit-price">{{ formatPrice(item.price) }} each</p>
    </div>
    <div class="item-controls">
      <div class="qty-row">
        <button
          class="qty-btn"
          @click="updateQuantity(item.id, item.quantity - 1)"
          :disabled="item.quantity <= 1"
        >−</button>
        <span class="qty-value">{{ item.quantity }}</span>
        <button
          class="qty-btn"
          @click="updateQuantity(item.id, item.quantity + 1)"
        >+</button>
      </div>
      <p class="item-total">{{ formatPrice(item.price * item.quantity) }}</p>
    </div>
    <button class="remove-btn" @click="removeFromCart(item.id)" title="Remove">✕</button>
  </div>
</template>

<style scoped>
.cart-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px;
  background: #ffffff;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

.item-image {
  width: 72px;
  height: 72px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.item-icon {
  font-size: 2rem;
}

.item-details {
  flex: 1;
  min-width: 0;
}

.item-name {
  font-size: 0.95rem;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.item-unit-price {
  font-size: 0.82rem;
  color: #888;
}

.item-controls {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 6px;
}

.qty-row {
  display: flex;
  align-items: center;
  gap: 0;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.qty-btn {
  width: 32px;
  height: 32px;
  background: #f5f5f5;
  border: none;
  font-size: 1.1rem;
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

.qty-value {
  width: 36px;
  text-align: center;
  font-size: 0.9rem;
  font-weight: 600;
  background: #fff;
  border-left: 1px solid #e0e0e0;
  border-right: 1px solid #e0e0e0;
  height: 32px;
  line-height: 32px;
}

.item-total {
  font-size: 1rem;
  font-weight: 700;
  color: #0066cc;
}

.remove-btn {
  background: none;
  border: none;
  color: #aaa;
  font-size: 0.9rem;
  cursor: pointer;
  padding: 6px;
  border-radius: 4px;
  transition: color 0.12s, background 0.12s;
  flex-shrink: 0;
}

.remove-btn:hover {
  color: #cc2200;
  background: #fff0ee;
}
</style>
