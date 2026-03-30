<script setup>
import { ref, computed } from 'vue'
import ProductCard from '../components/ProductCard.vue'
import { products } from '../data/products.js'

const searchQuery = ref('')

const filteredProducts = computed(() => {
  const q = searchQuery.value.trim().toLowerCase()
  if (!q) return products
  return products.filter(p => p.name.toLowerCase().includes(q))
})
</script>

<template>
  <div class="home-view">
    <div class="hero">
      <h1 class="hero-title">TechStore</h1>
      <p class="hero-subtitle">Premium tech products, delivered to your door.</p>
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
      <p class="empty-text">No products found for "{{ searchQuery }}"</p>
      <button class="clear-btn" @click="searchQuery = ''">Clear search</button>
    </div>
  </div>
</template>

<style scoped>
.hero {
  text-align: center;
  padding: 48px 0 40px;
  border-bottom: 1px solid #e0e0e0;
  margin-bottom: 40px;
}

.hero-title {
  font-size: 2.2rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 8px;
  letter-spacing: -0.02em;
}

.hero-subtitle {
  font-size: 1rem;
  color: #666;
  margin-bottom: 28px;
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
  padding: 12px 16px 12px 44px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 0.95rem;
  background: #fff;
  color: #1a1a1a;
  outline: none;
  transition: border-color 0.15s;
}

.search-input:focus {
  border-color: #0066cc;
}

.search-input::placeholder {
  color: #aaa;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
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
  background: #0066cc;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.15s;
}

.clear-btn:hover {
  background: #0052a3;
}
</style>
