<script setup>
import { ref, computed } from 'vue'
import ProductCard from '../components/ProductCard.vue'
import { products } from '../data/products.js'

const searchQuery = ref('')
const activeCategory = ref('all')
const customerRating = '4.9/5'

const categories = computed(() => ['all', ...new Set(products.map(product => product.category))])

const filteredProducts = computed(() => {
  const q = searchQuery.value.trim().toLowerCase()
  return products.filter((product) => {
    const matchQuery = !q || product.name.toLowerCase().includes(q)
    const matchCategory = activeCategory.value === 'all' || product.category === activeCategory.value
    return matchQuery && matchCategory
  })
})
</script>

<template>
  <div class="home-view">
    <section class="hero">
      <p class="hero-kicker">New season collection</p>
      <h1 class="hero-title">Experience premium tech, curated for modern life.</h1>
      <p class="hero-subtitle">Discover flagship devices with a storefront crafted for speed, clarity, and delight.</p>
      <div class="search-bar">
        <svg class="search-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8" />
          <line x1="21" y1="21" x2="16.65" y2="16.65" />
        </svg>
        <input
          v-model="searchQuery"
          type="search"
          placeholder="Search products..."
          class="search-input"
        />
      </div>
      <div class="hero-metrics">
        <div class="metric-item">
          <p class="metric-value">{{ products.length }}+</p>
          <p class="metric-label">Premium products</p>
        </div>
        <div class="metric-item">
          <p class="metric-value">24h</p>
          <p class="metric-label">Fast dispatch</p>
        </div>
        <div class="metric-item">
          <p class="metric-value">{{ customerRating }}</p>
          <p class="metric-label">Customer rating</p>
        </div>
      </div>
    </section>

    <div class="category-filters">
      <button
        v-for="category in categories"
        :key="category"
        class="filter-chip"
        :class="{ active: activeCategory === category }"
        @click="activeCategory = category"
      >
        {{ category }}
      </button>
    </div>

    <div v-if="filteredProducts.length > 0" class="product-grid">
      <ProductCard
        v-for="product in filteredProducts"
        :key="product.id"
        :product="product"
      />
    </div>

    <div v-else class="empty-state">
      <p class="empty-icon">🔍</p>
      <p class="empty-text">No products found for this selection</p>
      <button class="clear-btn" @click="searchQuery = ''; activeCategory = 'all'">Reset filters</button>
    </div>
  </div>
</template>

<style scoped>
.hero {
  text-align: center;
  padding: 56px 24px 40px;
  border: 1px solid rgba(255, 255, 255, 0.7);
  background: radial-gradient(circle at 20% 10%, #ecf4ff 0%, #f6f8fc 48%, #ffffff 100%);
  border-radius: 26px;
  box-shadow: 0 18px 40px rgba(15, 40, 80, 0.08);
  margin-bottom: 40px;
}

.hero-kicker {
  font-size: 0.76rem;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  color: #3e6ea8;
  font-weight: 700;
  margin-bottom: 16px;
}

.hero-title {
  font-size: clamp(2rem, 4vw, 3.1rem);
  font-weight: 700;
  color: #101828;
  max-width: 760px;
  margin: 0 auto 14px;
  letter-spacing: -0.02em;
  line-height: 1.1;
}

.hero-subtitle {
  font-size: 1.02rem;
  color: #516178;
  margin: 0 auto 28px;
  max-width: 620px;
}

.hero-metrics {
  margin-top: 28px;
  display: flex;
  justify-content: center;
  gap: 32px;
  flex-wrap: wrap;
}

.metric-item {
  min-width: 132px;
}

.metric-value {
  font-size: 1.35rem;
  font-weight: 700;
  color: #1d3b61;
}

.metric-label {
  font-size: 0.85rem;
  color: #64748b;
}

.search-bar {
  position: relative;
  max-width: 480px;
  margin: 0 auto;
}

.search-icon {
  position: absolute;
  left: 14px;
  top: 50%;
  transform: translateY(-50%);
  width: 18px;
  height: 18px;
  color: #999;
  pointer-events: none;
}

.search-input {
  width: 100%;
  padding: 14px 16px 14px 44px;
  border: 1px solid #d7dfeb;
  border-radius: 12px;
  font-size: 0.95rem;
  background: rgba(255, 255, 255, 0.95);
  color: #1a1a1a;
  outline: none;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.search-input:focus {
  border-color: #5a8dca;
  box-shadow: 0 0 0 4px rgba(54, 117, 192, 0.12);
}

.search-input::placeholder {
  color: #aaa;
}

.category-filters {
  display: flex;
  gap: 10px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.filter-chip {
  border: 1px solid #d7dfeb;
  background: #fff;
  color: #4a5f7a;
  border-radius: 999px;
  padding: 8px 14px;
  font-size: 0.78rem;
  letter-spacing: 0.06em;
  text-transform: uppercase;
  font-weight: 700;
  transition: all 0.2s;
}

.filter-chip:hover {
  border-color: #9cbde2;
  color: #275891;
}

.filter-chip.active {
  background: #1f4f86;
  border-color: #1f4f86;
  color: #fff;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 22px;
}

.empty-state {
  text-align: center;
  padding: 80px 0;
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 12px;
}

.empty-text {
  font-size: 1rem;
  color: #666;
  margin-bottom: 20px;
}

.clear-btn {
  padding: 10px 24px;
  background: #1f4f86;
  color: #fff;
  border: none;
  border-radius: 999px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.15s;
}

.clear-btn:hover {
  background: #173f6e;
}
</style>
