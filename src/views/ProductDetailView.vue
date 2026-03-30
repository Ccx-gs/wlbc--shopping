<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import { products } from '../data/products.js'
import { useCart } from '../composables/useCart.js'

const route = useRoute()
const router = useRouter()
const { addToCart } = useCart()

const product = computed(() => products.find((p) => p.id === Number(route.params.id)))
const currentImage = ref(0)
const quantity = ref(1)
const selectedSkuId = ref('')

const selectedSku = computed(() => {
  if (!product.value) return null
  if (!selectedSkuId.value) return product.value.skus[0]
  return product.value.skus.find((sku) => sku.id === selectedSkuId.value) || product.value.skus[0]
})

const relatedProducts = computed(() => {
  if (!product.value) return []
  return products
    .filter((item) => item.category === product.value.category && item.id !== product.value.id)
    .slice(0, 4)
})

function formatPriceParts(price) {
  const [integer, decimals] = Number(price || 0).toFixed(2).split('.')
  return { integer, decimals }
}

function chooseSku(skuId) {
  selectedSkuId.value = skuId
}

function addItemToCart() {
  if (!product.value || !selectedSku.value) return
  const item = {
    ...product.value,
    price: selectedSku.value.price,
    skuId: selectedSku.value.id,
    skuName: selectedSku.value.name,
    cartKey: `${product.value.id}-${selectedSku.value.id}`,
    image: product.value.images[currentImage.value] || product.value.image,
  }
  addToCart(item, quantity.value)
  window.dispatchEvent(new CustomEvent('toast:show', { detail: { message: '已加入购物车' } }))
}

function increaseQty() {
  quantity.value += 1
}

function decreaseQty() {
  if (quantity.value > 1) quantity.value -= 1
}
</script>

<template>
  <div v-if="product" class="detail-view">
    <button class="back-btn" @click="router.back()">← 返回</button>

    <section class="main-card fade-up">
      <div class="gallery">
        <img :src="product.images[currentImage]" :alt="product.name" class="main-image" />
        <div class="thumbs">
          <button
            v-for="(img, idx) in product.images"
            :key="img"
            :class="['thumb', { active: currentImage === idx }]"
            @click="currentImage = idx"
          >
            <img :src="img" :alt="`${product.name}-${idx}`" loading="lazy" />
          </button>
        </div>
      </div>

      <div class="info">
        <div class="labels">
          <span v-if="product.isNew" class="label new">新品</span>
          <span v-if="product.freeShipping" class="label ship">包邮</span>
        </div>

        <h1>{{ product.name }}</h1>
        <p class="desc">{{ product.description }}</p>

        <div class="price-wrap">
          <p class="price sale">
            <span class="currency">¥</span>
            <span class="amount">{{ formatPriceParts(selectedSku.price).integer }}</span>
            <span class="decimals">.{{ formatPriceParts(selectedSku.price).decimals }}</span>
          </p>
          <p class="origin">¥{{ Number(product.originalPrice).toFixed(2) }}</p>
        </div>

        <p class="stats">销量 {{ product.sales }} · {{ product.reviewCount }} 条评价 · 评分 {{ product.rating }}</p>

        <div class="sku-group">
          <p>选择规格</p>
          <div class="sku-list">
            <button
              v-for="sku in product.skus"
              :key="sku.id"
              :class="['sku', { active: selectedSku.id === sku.id }]"
              @click="chooseSku(sku.id)"
            >
              {{ sku.name }}
            </button>
          </div>
        </div>

        <div class="buy-row">
          <div class="qty">
            <button @click="decreaseQty">-</button>
            <span>{{ quantity }}</span>
            <button @click="increaseQty">+</button>
          </div>
          <button class="add-btn" @click="addItemToCart">加入购物车</button>
        </div>
      </div>
    </section>

    <section class="detail-html fade-up" v-html="product.detailHtml"></section>

    <section class="review-section fade-up">
      <h2>用户评价</h2>
      <div class="reviews">
        <article v-for="review in product.reviews" :key="review.user + review.date" class="review-item">
          <div class="review-head">
            <strong>{{ review.user }}</strong>
            <span>{{ review.date }}</span>
          </div>
          <p class="score">评分 {{ review.score }}</p>
          <p>{{ review.content }}</p>
        </article>
      </div>
    </section>

    <section class="related fade-up">
      <h2>相关推荐</h2>
      <div class="related-grid">
        <ProductCard v-for="item in relatedProducts" :key="item.id" :product="item" />
      </div>
    </section>
  </div>

  <div v-else class="not-found">
    <p>商品不存在</p>
    <button @click="router.push('/')">返回首页</button>
  </div>
</template>

<style scoped>
.detail-view {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.back-btn {
  min-height: 44px;
  padding: 10px 14px;
  border-radius: 8px;
  border: 1px solid #d1d5db;
  background: #fff;
  width: fit-content;
}

.main-card {
  display: grid;
  grid-template-columns: 1fr 1.2fr;
  gap: 18px;
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: var(--shadow-soft);
  padding: 16px;
}

.gallery {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.main-image {
  width: 100%;
  border-radius: 12px;
  aspect-ratio: 1 / 1;
  object-fit: cover;
}

.thumbs {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 8px;
}

.thumb {
  border-radius: 8px;
  border: 2px solid transparent;
  overflow: hidden;
  background: #fff;
}

.thumb.active {
  border-color: #4f46e5;
}

.thumb img {
  width: 100%;
  height: 74px;
  object-fit: cover;
}

.info h1 {
  color: #111827;
  margin: 10px 0 8px;
  line-height: 1.2;
}

.labels {
  display: flex;
  gap: 8px;
}

.label {
  font-size: 0.72rem;
  font-weight: 700;
  color: #fff;
  border-radius: 999px;
  padding: 4px 8px;
}

.new {
  background: #4f46e5;
}

.ship {
  background: #ef4444;
}

.desc {
  color: #6b7280;
}

.price-wrap {
  display: flex;
  align-items: baseline;
  gap: 8px;
  margin: 10px 0 8px;
}

.sale {
  color: #ef4444;
}

.origin {
  color: #9ca3af;
  text-decoration: line-through;
}

.stats {
  color: #6b7280;
  font-size: 0.9rem;
}

.sku-group {
  margin-top: 14px;
}

.sku-group p {
  margin-bottom: 8px;
  font-weight: 600;
}

.sku-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.sku {
  min-height: 44px;
  border-radius: 8px;
  border: 1px solid #d1d5db;
  background: #fff;
  padding: 0 12px;
}

.sku.active {
  border-color: #4f46e5;
  color: #312e81;
  background: #eef2ff;
}

.buy-row {
  margin-top: 14px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.qty {
  display: flex;
  align-items: center;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  overflow: hidden;
}

.qty button,
.qty span {
  min-width: 44px;
  min-height: 44px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: #fff;
}

.qty button {
  background: #f9fafb;
}

.add-btn {
  min-height: 44px;
  padding: 0 18px;
  border-radius: 8px;
  background: linear-gradient(135deg, #4f46e5, #4338ca);
  color: #fff;
  font-weight: 700;
}

.detail-html,
.review-section,
.related {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: var(--shadow-soft);
  padding: 16px;
}

.detail-html :deep(h3) {
  margin-bottom: 8px;
}

.detail-html :deep(p) {
  color: #374151;
  margin-bottom: 12px;
}

.detail-html :deep(img) {
  width: 100%;
  border-radius: 8px;
  margin-bottom: 10px;
}

.reviews {
  display: grid;
  gap: 10px;
}

.review-item {
  border: 1px solid #eef2f7;
  border-radius: 8px;
  padding: 12px;
}

.review-head {
  display: flex;
  justify-content: space-between;
  margin-bottom: 4px;
}

.score {
  color: #ef4444;
  font-size: 0.88rem;
  margin-bottom: 6px;
}

.related-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 12px;
}

.not-found {
  text-align: center;
  padding: 80px 0;
}

.not-found button {
  min-height: 44px;
  margin-top: 10px;
  border-radius: 8px;
  background: #4f46e5;
  color: #fff;
  padding: 10px 14px;
}

@media (max-width: 1024px) {
  .main-card {
    grid-template-columns: 1fr;
  }

  .related-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 680px) {
  .related-grid {
    grid-template-columns: 1fr;
  }
}
</style>
