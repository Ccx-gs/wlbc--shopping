<script setup>
import { useRouter } from 'vue-router'

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
})

const router = useRouter()

function formatPriceParts(price) {
  const fixed = Number(price || 0).toFixed(2)
  const [integer, decimals] = fixed.split('.')
  return { integer, decimals }
}

function goToDetail() {
  router.push(`/product/${props.product.id}`)
}
</script>

<template>
  <article class="product-card fade-up" @click="goToDetail">
    <div class="image-wrap">
      <img :src="product.image" :alt="product.name" loading="lazy" class="product-image" />
      <div class="tags">
        <span v-if="product.isNew" class="tag new">新品</span>
        <span v-if="product.freeShipping" class="tag free">包邮</span>
      </div>
    </div>

    <div class="product-info">
      <p class="brand">{{ product.brand }}</p>
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-description">{{ product.description }}</p>

      <div class="price-row">
        <p class="price sale">
          <span class="currency">¥</span>
          <span class="amount">{{ formatPriceParts(product.promoPrice).integer }}</span>
          <span class="decimals">.{{ formatPriceParts(product.promoPrice).decimals }}</span>
        </p>
        <p class="origin">¥{{ Number(product.originalPrice).toFixed(2) }}</p>
      </div>

      <div class="meta-row">
        <span>销量 {{ product.sales }}</span>
        <span>{{ product.reviewCount }} 条评价</span>
      </div>
    </div>
  </article>
</template>

<style scoped>
.product-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid #eceff4;
  box-shadow: var(--shadow-soft);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
}

.product-card:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-elevated);
}

.image-wrap {
  position: relative;
  overflow: hidden;
  aspect-ratio: 4 / 3;
  background: #f3f4f6;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.product-card:hover .product-image {
  transform: scale(1.05);
}

.tags {
  position: absolute;
  left: 10px;
  top: 10px;
  display: flex;
  gap: 6px;
}

.tag {
  font-size: 0.7rem;
  font-weight: 700;
  padding: 4px 8px;
  border-radius: 999px;
  color: #fff;
}

.tag.new {
  background: #4f46e5;
}

.tag.free {
  background: #ef4444;
}

.product-info {
  padding: 14px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.brand {
  color: #6b7280;
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 0.08em;
}

.product-name {
  font-size: 1rem;
  color: #111827;
  line-height: 1.35;
}

.product-description {
  color: #6b7280;
  font-size: 0.82rem;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  min-height: 2.4em;
}

.price-row {
  display: flex;
  align-items: baseline;
  gap: 8px;
}

.sale {
  color: #ef4444;
}

.origin {
  text-decoration: line-through;
  color: #9ca3af;
  font-size: 0.8rem;
}

.meta-row {
  display: flex;
  justify-content: space-between;
  font-size: 0.78rem;
  color: #6b7280;
}
</style>
