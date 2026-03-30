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
      <p v-if="product.description" class="product-description">{{ product.description }}</p>
      <p class="product-price">{{ formatPrice(product.price) }}</p>
    </div>
  </article>
</template>

<style scoped>
.product-card {
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.95), #ffffff 40%);
  border: 1px solid #e5ebf3;
  border-radius: 18px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;
  box-shadow: 0 2px 8px rgba(15, 35, 60, 0.04);
  display: flex;
  flex-direction: column;
  min-height: 100%;
}

.product-card:hover {
  box-shadow: 0 18px 34px rgba(28, 62, 104, 0.14);
  border-color: #c7d7e9;
  transform: translateY(-4px);
}

.product-image {
  height: 190px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  position: relative;
}

.product-icon {
  font-size: 3.4rem;
}

.category-tag {
  font-size: 0.68rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.08em;
}

.product-info {
  padding: 16px 16px 18px;
  border-top: 1px solid rgba(96, 125, 156, 0.12);
  display: flex;
  flex-direction: column;
  gap: 10px;
  flex: 1;
}

.product-name {
  font-size: 1rem;
  font-weight: 700;
  color: #12233a;
  line-height: 1.4;
}

.product-description {
  color: #64748b;
  font-size: 0.83rem;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.product-price {
  font-size: 1.1rem;
  font-weight: 700;
  color: #1f4f86;
  margin-top: auto;
}
</style>
