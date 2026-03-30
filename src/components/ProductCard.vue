<script setup>
import { useRouter } from 'vue-router'

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
})

const router = useRouter()

const categoryColors = {
  smartphone: { bg: '#e8f0fe', text: '#1a73e8', icon: '📱' },
  laptop: { bg: '#e6f4ea', text: '#188038', icon: '💻' },
  tablet: { bg: '#fce8e6', text: '#c5221f', icon: '📟' },
  accessory: { bg: '#fef7e0', text: '#f29900', icon: '🎧' },
}

function getCategoryStyle(category) {
  return categoryColors[category] || { bg: '#f1f3f4', text: '#5f6368', icon: '📦' }
}

function formatPrice(price) {
  return '¥' + price.toLocaleString('zh-CN')
}

function goToDetail() {
  router.push(`/product/${props.product.id}`)
}
</script>

<template>
  <article class="product-card" @click="goToDetail">
    <div
      class="product-image"
      :style="{ backgroundColor: getCategoryStyle(product.category).bg }"
    >
      <span class="product-icon">{{ getCategoryStyle(product.category).icon }}</span>
      <span
        class="category-tag"
        :style="{ color: getCategoryStyle(product.category).text }"
      >{{ product.category }}</span>
    </div>
    <div class="product-info">
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-price">{{ formatPrice(product.price) }}</p>
    </div>
  </article>
</template>

<style scoped>
.product-card {
  background: #ffffff;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
  cursor: pointer;
  transition: box-shadow 0.15s, border-color 0.15s;
}

.product-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border-color: #c0c0c0;
}

.product-image {
  height: 180px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  position: relative;
}

.product-icon {
  font-size: 3.5rem;
}

.category-tag {
  font-size: 0.72rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.04em;
}

.product-info {
  padding: 14px 16px 16px;
  border-top: 1px solid #f0f0f0;
}

.product-name {
  font-size: 0.95rem;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 8px;
  line-height: 1.4;
}

.product-price {
  font-size: 1.05rem;
  font-weight: 700;
  color: #0066cc;
}
</style>
