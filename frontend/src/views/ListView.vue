<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import http from '../api/http.js'

const route = useRoute()
const products = ref([])
const loading = ref(true)
const selectedBrand = ref('all')
const selectedRange = ref('all')
const selectedCategory = ref(route.query.category || 'all')
const keyword = ref(String(route.query.keyword || '').trim().toLowerCase())
const sortBy = ref('default')

const brands = computed(() => ['all', ...new Set(products.value.map(item => item.brand))])

const ranges = [
  { key: 'all', label: '全部价格' },
  { key: '0-3000', label: '¥0 - ¥3000' },
  { key: '3000-7000', label: '¥3000 - ¥7000' },
  { key: '7000-12000', label: '¥7000 - ¥12000' },
  { key: '12000+', label: '¥12000+' },
]

const categories = computed(() => ['all', ...new Set(products.value.map(item => item.category))])

const filteredProducts = computed(() => {
  const result = products.value.filter((item) => {
    const brandOk = selectedBrand.value === 'all' || item.brand === selectedBrand.value
    const categoryOk = selectedCategory.value === 'all' || item.category === selectedCategory.value
    const keywordOk = !keyword.value
      || item.name.toLowerCase().includes(keyword.value)
      || item.brand.toLowerCase().includes(keyword.value)
      || item.description.toLowerCase().includes(keyword.value)

    const price = item.promoPrice
    let rangeOk = true
    if (selectedRange.value === '0-3000') rangeOk = price <= 3000
    if (selectedRange.value === '3000-7000') rangeOk = price > 3000 && price <= 7000
    if (selectedRange.value === '7000-12000') rangeOk = price > 7000 && price <= 12000
    if (selectedRange.value === '12000+') rangeOk = price > 12000

    return brandOk && categoryOk && rangeOk && keywordOk
  })

  if (sortBy.value === 'sales') {
    return result.slice().sort((a, b) => b.sales - a.sales)
  }

  if (sortBy.value === 'price-asc') {
    return result.slice().sort((a, b) => a.promoPrice - b.promoPrice)
  }

  if (sortBy.value === 'price-desc') {
    return result.slice().sort((a, b) => b.promoPrice - a.promoPrice)
  }

  return result
})

watch(() => route.query.category, (value) => {
  selectedCategory.value = value || 'all'
})

watch(() => route.query.keyword, (value) => {
  keyword.value = String(value || '').trim().toLowerCase()
})

onMounted(() => {
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) entry.target.classList.add('in-view')
    })
  }, { threshold: 0.1 })
  document.querySelectorAll('.fade-up').forEach((el) => observer.observe(el))

  http.get('/products').then(({ data }) => {
    products.value = Array.isArray(data) ? data : []
    setTimeout(() => {
      loading.value = false
    }, 700)
  })
})
</script>

<template>
  <div class="list-view">
    <header class="head fade-up">
      <h1>商品列表</h1>
      <p>多维筛选 + 智能排序，快速找到适合你的装备</p>
    </header>

    <section class="filters fade-up">
      <div class="filter-group">
        <label>分类</label>
        <select v-model="selectedCategory">
          <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
        </select>
      </div>

      <div class="filter-group">
        <label>品牌</label>
        <select v-model="selectedBrand">
          <option v-for="brand in brands" :key="brand" :value="brand">{{ brand }}</option>
        </select>
      </div>

      <div class="filter-group">
        <label>价格区间</label>
        <select v-model="selectedRange">
          <option v-for="range in ranges" :key="range.key" :value="range.key">{{ range.label }}</option>
        </select>
      </div>

      <div class="filter-group">
        <label>排序</label>
        <select v-model="sortBy">
          <option value="default">综合</option>
          <option value="sales">销量</option>
          <option value="price-asc">价格从低到高</option>
          <option value="price-desc">价格从高到低</option>
        </select>
      </div>
    </section>

    <section v-if="loading" class="skeleton-grid">
      <div v-for="n in 8" :key="n" class="skeleton-card"></div>
    </section>

    <section v-else class="product-grid">
      <ProductCard v-for="product in filteredProducts" :key="product.id" :product="product" />
      <p v-if="filteredProducts.length === 0" class="empty">暂无符合筛选条件的商品</p>
    </section>
  </div>
</template>

<style scoped>
.list-view {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.head {
  background: linear-gradient(135deg, #4f46e5, #6366f1);
  color: #fff;
  border-radius: 12px;
  padding: 22px;
  box-shadow: var(--shadow-elevated);
}

.head p {
  margin-top: 4px;
  opacity: 0.95;
}

.filters {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: var(--shadow-soft);
  padding: 14px;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 12px;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.filter-group label {
  font-size: 0.8rem;
  color: #6b7280;
}

.filter-group select {
  min-height: 44px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  padding: 8px 10px;
  background: #fff;
}

.skeleton-grid,
.product-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.skeleton-card {
  border-radius: 12px;
  height: 280px;
  background: linear-gradient(110deg, #eef2f7 8%, #f7f9fc 18%, #eef2f7 33%);
  background-size: 200% 100%;
  animation: shine 1.2s linear infinite;
}

@keyframes shine {
  to { background-position-x: -200%; }
}

.empty {
  grid-column: 1 / -1;
  text-align: center;
  padding: 38px 0;
  color: #6b7280;
}

@media (max-width: 1024px) {
  .filters,
  .skeleton-grid,
  .product-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 680px) {
  .filters,
  .skeleton-grid,
  .product-grid {
    grid-template-columns: 1fr;
  }
}
</style>
