<script setup>
import { useCart } from '../composables/useCart.js'

const props = defineProps({
  item: {
    type: Object,
    required: true,
  },
})

const { removeFromCart, updateQuantity } = useCart()

function formatPriceParts(price) {
  const [integer, decimals] = Number(price || 0).toFixed(2).split('.')
  return { integer, decimals }
}
</script>

<template>
  <div class="cart-item fade-up">
    <img :src="item.image" :alt="item.name" class="item-image" loading="lazy" />
    <div class="item-details">
      <h4>{{ item.name }}</h4>
      <p class="sku">{{ item.skuName || '默认规格' }}</p>
      <p class="price unit">
        <span class="currency">¥</span>
        <span class="amount">{{ formatPriceParts(item.price).integer }}</span>
        <span class="decimals">.{{ formatPriceParts(item.price).decimals }}</span>
      </p>
    </div>

    <div class="item-controls">
      <div class="qty-row">
        <button @click="updateQuantity(item.cartKey || item.id, item.quantity - 1)" :disabled="item.quantity <= 1">−</button>
        <span>{{ item.quantity }}</span>
        <button @click="updateQuantity(item.cartKey || item.id, item.quantity + 1)">+</button>
      </div>
      <p class="price total">
        <span class="currency">¥</span>
        <span class="amount">{{ formatPriceParts(item.price * item.quantity).integer }}</span>
        <span class="decimals">.{{ formatPriceParts(item.price * item.quantity).decimals }}</span>
      </p>
    </div>

    <button class="remove" title="删除" @click="removeFromCart(item.cartKey || item.id)">✕</button>
  </div>
</template>

<style scoped>
.cart-item {
  display: grid;
  grid-template-columns: 90px 1fr auto auto;
  gap: 12px;
  padding: 12px;
  border-radius: 12px;
  background: #fff;
  border: 1px solid #e5e7eb;
  box-shadow: var(--shadow-soft);
  align-items: center;
}

.item-image {
  width: 90px;
  height: 90px;
  border-radius: 8px;
  object-fit: cover;
}

.item-details h4 {
  color: #111827;
  margin-bottom: 4px;
}

.sku {
  color: #6b7280;
  font-size: 0.84rem;
  margin-bottom: 8px;
}

.item-controls {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
}

.qty-row {
  display: flex;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  overflow: hidden;
}

.qty-row button,
.qty-row span {
  width: 40px;
  height: 40px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.qty-row button {
  background: #f9fafb;
}

.remove {
  width: 44px;
  height: 44px;
  border-radius: 8px;
  background: #fff1f2;
  color: #ef4444;
  font-weight: 700;
}

@media (max-width: 760px) {
  .cart-item {
    grid-template-columns: 80px 1fr;
  }

  .item-controls,
  .remove {
    grid-column: span 2;
    justify-self: end;
  }
}
</style>
